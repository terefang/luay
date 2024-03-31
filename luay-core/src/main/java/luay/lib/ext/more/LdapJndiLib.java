package luay.lib.ext.more;

import lombok.Data;
import lombok.SneakyThrows;
import luay.lib.LuayLibraryFactory;
import luay.lib.ext.AbstractLibrary;
import luay.vm.*;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.StartTlsRequest;
import javax.naming.ldap.StartTlsResponse;
import javax.net.ssl.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/*
%!begin MARKDOWN

## ldapjndi

Read-only Ldap access via JNDI.

### Functions

*   `ldapjndi.connect(uri, admindn, adminpw[, mode, starttls]) -> session` \
    uri = "ldap://ldap.forumsys.com:port/" or "ldaps://ldap.forumsys.com:port/"
*   `ldapjndi.disconnect(session)`
*   `ldapjndi.close(session)`
*   `ldapjndi.get(session. dn [, a1, ..., aN]) -> map`
*   `ldapjndi.search(session. basedn, filter [, scope, a1, ..., aN]) -> list of maps`
*   `ldapjndi.authdn(session. dn, pw [, mode]) -> bool`

\pagebreak

%!end MARKDOWN
 */


public class LdapJndiLib extends AbstractLibrary implements LuayLibraryFactory
{
	@Override
	public String getLibraryName() {
		return "ldapjndi";
	}

	@Override
	public List<LuaFunction> getLibraryFunctions() {
		return toList(
				_varArgFunctionWrapper.from("connect", LdapJndiLib::_connect),
				_varArgFunctionWrapper.from("disconnect", LdapJndiLib::_disconnect),
				_varArgFunctionWrapper.from("close", LdapJndiLib::_disconnect),
				_varArgFunctionWrapper.from("get", LdapJndiLib::_get),
				_varArgFunctionWrapper.from("search", LdapJndiLib::_search),
				_varArgFunctionWrapper.from("authdn", LdapJndiLib::_authenticateDN)
		);
	}

	// ldapjndi.connect(uri, admindn, adminpw[, mode, starttls])
	// "ldap://ldap.forumsys.com/"
	// "simple" "cn=read-only-admin,dc=example,dc=com" "password"
	@SneakyThrows
	public static LuaValue _connect(Varargs args) {
		String _uri = args.checkjstring(1);
		String _usr = args.checkjstring(2);
		String _pwd = args.checkjstring(3);
		String _mode = args.optjstring(4, "simple");
		boolean _stls = args.optboolean(5, false);

		Hashtable env = new Hashtable<>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

		//env.put("com.sun.jndi.ldap.connect.timeout", "1000");
		//env.put("com.sun.jndi.ldap.read.timeout", "1000");

		env.put(Context.PROVIDER_URL, _uri);

		// Create initial context
		LdapContext ctx = new InitialLdapContext(env, null);

		if(_uri.startsWith("ldaps:")) {
			env.put("java.naming.ldap.factory.socket", ClientSSLSocketFactory.class.getCanonicalName());
		} else if(_stls) {
			StartTlsResponse tls = (StartTlsResponse) ctx.extendedOperation(new StartTlsRequest());
			tls.setHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});
			tls.negotiate(ClientSSLSocketFactory.create());
		}
		// Perform simple client authentication
		ctx.addToEnvironment(Context.SECURITY_AUTHENTICATION, _mode);
		ctx.addToEnvironment(Context.SECURITY_PRINCIPAL, _usr);
		ctx.addToEnvironment(Context.SECURITY_CREDENTIALS, _pwd);
		return LuaValue.userdataOf(ctx);
	}

	// ldapjndi.disconnect(session)
	// ldapjndi.close(session)
	@SneakyThrows
	public static LuaValue _disconnect(Varargs args) {
		LdapContext _ctx = (LdapContext)args.checkuserdata(1);
		_ctx.close();
		return LuaValue.NONE;
	}

	// ldapjndi.get(session. dn [, a1, ..., aN]) -> map
	@SneakyThrows
	public static LuaValue _get(Varargs args) {
		LdapContext _ctx = (LdapContext)args.checkuserdata(1);
		String _dn = args.checkjstring(2);
		Attributes _attr = null;

		if(args.narg()>2) {
			String[] _an = new String[args.narg()-2];
			for(int _i =0; _i< _an.length; _i++) {
				_an[_i] = args.checkjstring(_i+2);
			}
			_attr = _ctx.getAttributes(_dn, _an);
		} else {
			_attr = _ctx.getAttributes(_dn);
		}

		return attributesToLuaTable(_attr);
	}

	@SneakyThrows
	private static LuaTable attributesToLuaTable(Attributes _attr) {
		LuaTable _t = new LuaTable();
		NamingEnumeration<? extends Attribute> _en = _attr.getAll();
		while(_en.hasMore()) {
			Attribute _at = _en.next();
			LuaList _a = new LuaList();
			String _key = _at.getID();
			_t.set(LuaValue.valueOf(_key), _a);
			NamingEnumeration<?> _ena = _at.getAll();
			while(_ena.hasMore()) {
				_a.insert(LuaValue.valueOf(_ena.next().toString()));
			}
		}
		return _t;
	}

	// ldapjndi.search(session. basedn, filter [, scope, a1, ..., aN]) -> list of maps
	@SneakyThrows
	public static LuaValue _search(Varargs args) {
		LdapContext _ctx = (LdapContext)args.checkuserdata(1);
		String _base = args.checkjstring(2);
		String _filter = args.checkjstring(3);
		String _scope = args.optjstring(4, "sub");

		SearchControls _sc = new SearchControls();
		if("sub".equalsIgnoreCase(_scope)) {
			_sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
		} else if("one".equalsIgnoreCase(_scope)) {
			_sc.setSearchScope(SearchControls.ONELEVEL_SCOPE);
		} else if("base".equalsIgnoreCase(_scope)) {
			_sc.setSearchScope(SearchControls.OBJECT_SCOPE);
		}

		if(args.narg()>4) {
			String[] _an = new String[args.narg()-4];
			for(int _i =0; _i< _an.length; _i++) {
				_an[_i] = args.checkjstring(_i+5);
			}
			_sc.setReturningAttributes(_an);
		}

		NamingEnumeration<SearchResult> _list = _ctx.search(_base, _filter, _sc );
		LuaList _llist = new LuaList();
		while(_list.hasMore()) {
			SearchResult _entry = _list.next();
			//System.out.println(_entry.getNameInNamespace());
			LuaTable _a = attributesToLuaTable(_entry.getAttributes());
			_a.set(LuaValue.valueOf("DN"), LuaValue.valueOf(_entry.getNameInNamespace()));
			_llist.insert(0, _a);
		}
		return _llist;
	}

	// ldapjndi.authdn(session. dn, pw [, mode]) -> bool
	@SneakyThrows
	public static LuaValue _authenticateDN(Varargs args) {
		LdapContext _ctx = (LdapContext)args.checkuserdata(1);
		String _dn = args.checkjstring(2);
		String _pw = args.checkjstring(3);
		String _mode = args.optjstring(4, "simple");
		try {
			_ctx.addToEnvironment(Context.SECURITY_AUTHENTICATION, _mode);
			_ctx.addToEnvironment(Context.SECURITY_PRINCIPAL, _dn);
			_ctx.addToEnvironment(Context.SECURITY_CREDENTIALS, _pw);
			_ctx.getAttributes("");
			return LuaValue.TRUE;
		} catch (Exception _xe) {}
		return LuaValue.FALSE;
	}

	@Data
	public static class ClientSSLSocketFactory
			extends SSLSocketFactory {
		SSLContext sslCtx = null;
		Set<String> sslProtocols = new HashSet();
		Set<String> sslCiphers = new HashSet();

		@SneakyThrows
		public static ClientSSLSocketFactory create() {
			ClientSSLSocketFactory _csf = new ClientSSLSocketFactory();
			return _csf;
		}

		static String[] _PROTOCOLS = { "TLSv1.1", "TLSv1.3", "TLSv1.2" };

		@SneakyThrows
		public ClientSSLSocketFactory()
		{
			super();
			for(String _p : _PROTOCOLS)
			{
				Security.setProperty("jdk.tls.disabledAlgorithms",
						Security.getProperty("jdk.tls.disabledAlgorithms")
								.replace(_p+", ", ""));
				this.sslProtocols.add(_p);
			}
			this.sslCtx = SSLContext.getInstance(_PROTOCOLS[0]);
			this.sslCiphers.add("TLS_RSA_WITH_AES_256_CBC_SHA256");
			for(String _cs : ((SSLServerSocketFactory)SSLServerSocketFactory.getDefault()).getSupportedCipherSuites())
			{
				this.sslCiphers.add(_cs);
			}
			this.sslCtx.init(new KeyManager[0], new TrustManager[] { new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkServerTrusted(X509Certificate[] arg0, String arg1)
						throws CertificateException {
					// TODO Auto-generated method stub
				}

				public void checkClientTrusted(X509Certificate[] arg0, String arg1)
						throws CertificateException {
					// TODO Auto-generated method stub
				}
			}}, null);
		}

		@Override
		public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort)
				throws IOException {
			SSLSocket socket = (SSLSocket) sslCtx.getSocketFactory().createSocket(address, port, localAddress, localPort);
			setSocketOptions(socket);
			return socket;
		}

		@Override
		public Socket createSocket(String host, int port, InetAddress localHost, int localPort)
				throws IOException, UnknownHostException {
			SSLSocket socket = (SSLSocket) sslCtx.getSocketFactory().createSocket(host, port, localHost, localPort);
			setSocketOptions(socket);
			return socket;
		}

		@Override
		public Socket createSocket(InetAddress host, int port)
				throws IOException {
			SSLSocket socket = (SSLSocket) sslCtx.getSocketFactory().createSocket(host, port);
			setSocketOptions(socket);
			return socket;
		}

		@Override
		public Socket createSocket(String host, int port)
				throws IOException, UnknownHostException {
			SSLSocket socket = (SSLSocket) sslCtx.getSocketFactory().createSocket(host, port);
			setSocketOptions(socket);
			return socket;
		}

		@Override
		public String[] getSupportedCipherSuites() {
			return sslCiphers.toArray(new String[0]);
		}

		@Override
		public String[] getDefaultCipherSuites() {
			return sslCiphers.toArray(new String[0]);
		}

		@Override
		public Socket createSocket(Socket s, String host, int port, boolean autoClose)
				throws IOException {
			SSLSocket socket = (SSLSocket) sslCtx.getSocketFactory().createSocket(s, host, port, autoClose);
			setSocketOptions(socket);
			return socket;
		}

		void setSocketOptions(SSLSocket socket)
		{
			SSLParameters _p = new SSLParameters(sslCiphers.toArray(new String[0]), sslProtocols.toArray(new String[0]));
			socket.setSSLParameters(_p);
		}
	}

}
