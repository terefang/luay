package luay.lib.ext;

import lombok.Data;
import lombok.SneakyThrows;
import luay.lib.LuayLibraryFactory;
import luay.main.LuayHelper;
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

public class GetoptLib extends AbstractLibrary  implements LuayLibraryFactory
{
	@Override
	public String getLibraryName() {
		return "getopt";
	}

	@Override
	public List<LuaFunction> getLibraryFunctions() {
		return toList(
				_varArgFunctionWrapper.from("simple", GetoptLib::_simple),
				_varArgFunctionWrapper.from("simplext", GetoptLib::_simple_ext),
				_varArgFunctionWrapper.from("std", GetoptLib::_std),
				_varArgFunctionWrapper.from("stdext", GetoptLib::_std_ext),
				_varArgFunctionWrapper.from("long", GetoptLib::_long),
				_varArgFunctionWrapper.from("longext", GetoptLib::_long_ext)
		);
	}

	// getopt.simple(arglist) -> table
	@SneakyThrows
	public static Varargs _simple(Varargs args)
	{
		return _simplex(args, false);
	}

	@SneakyThrows
	public static Varargs _simple_ext(Varargs args)
	{
		return _simplex(args, true);
	}

	@SneakyThrows
	public static Varargs _simplex(Varargs args, boolean _ext)
	{
		List<String> _args = LuayHelper.tableToJavaStringList(args.checktable(1));
		List<Pair<String,Object>> _argopts = new ArrayList<>();

		while(!_args.isEmpty()) {
			String _opt = _args.get(0);
			if((!_opt.startsWith("-")) && (!_opt.startsWith("+"))) {
				break;
			}

			_args.remove(0);

			if(_opt.startsWith("--")) {
				int _ofs = -1;
				if(_opt.length()==2) {
					// end arg processing
					break;
				} else if((_ofs = _opt.indexOf('='))>0) {
					_argopts.add(Pair.from(_opt.substring(2, _ofs),_opt.substring(_ofs+1)));
				} else {
					_argopts.add(Pair.from(_opt.substring(2),Boolean.TRUE));
				}
			} else if(_opt.startsWith("-")) {
				int _ofs = -1;
				if(_opt.length()==1) {
					// end arg processing
					_args.add(0, _opt);
					break;
				} else if(_opt.length()==2) {
					_argopts.add(Pair.from(_opt.substring(1,2),Boolean.TRUE));
				} else if((_ofs = _opt.indexOf('='))>0) {
					_argopts.add(Pair.from(_opt.substring(1, _ofs),_opt.substring(_ofs+1)));
				} else {
					_argopts.add(Pair.from(_opt.substring(1,2),_opt.substring(2)));
				}
			} else if(_opt.startsWith("+")) {
				if(_opt.length()==1) {
					// end arg processing
					_args.add(0, _opt);
					break;
				} else if(_opt.length()==2) {
					_argopts.add(Pair.from(_opt.substring(1,2),Boolean.FALSE));
				} else {
					_argopts.add(Pair.from(_opt.substring(1,2),_opt.substring(2)));
				}
			}
		}

		return toReturn(_argopts, _args, _ext);
	}

	@SneakyThrows
	public static Varargs _std(Varargs args) {
		LuaValue.assert_(args.narg()==2, "invalid number of arguments");
		return _ext(args, false, false, false);
	}

	@SneakyThrows
	public static Varargs _std_ext(Varargs args) {
		LuaValue.assert_(args.narg()==2, "invalid number of arguments");
		return _ext(args, false, false, true);
	}

	@SneakyThrows
	public static Varargs _long(Varargs args) {
		if(args.narg()==2) {
			return _ext(args, true, true, false);
		} else {
			return _ext(args, true, false, false);
		}
	}

	@SneakyThrows
	public static Varargs _long_ext(Varargs args) {
		if(args.narg()==2) {
			return _ext(args, true, true, true);
		} else {
			return _ext(args, true, false, true);
		}
	}

	@SneakyThrows
	public static Varargs _ext(Varargs args, boolean _with_long_opts, boolean _long_opts_only, boolean _ext)
	{
		List<String> _args = LuayHelper.tableToJavaStringList(args.checktable(args.narg()));

		List<Pair<String,Object>> _argopts = new ArrayList<>();

		if(_with_long_opts) {
			Map<String, Integer> _loptspec = new LinkedHashMap();
			LuaTable _lopts = null;
			if(args.narg()==2) {
				_lopts = args.checktable(1);
			} else {
				_lopts = args.checktable(2);
			}

			for(Map.Entry<String, Object> _entry : LuayHelper.tableToJavaMap(_lopts).entrySet()) {
				_loptspec.put(_entry.getKey(), Integer.valueOf(_entry.getValue().toString()));
			}

			for(int _i=0; _i< _args.size(); _i++) {
				String _opt = _args.get(_i);

				if(!_opt.startsWith("--")) continue;

				if (_opt.equalsIgnoreCase("--")) break;

				_args.remove(_i);
				String _val = null;

				_opt = _opt.substring(2);
				int _ofs = -1;
				if((_ofs = _opt.indexOf('=')) > 0)
				{
					_val = _opt.substring(_ofs+1);
					_opt = _opt.substring(0, _ofs);
					if(_loptspec.containsKey(_opt))
					{
						_argopts.add(Pair.from(_opt, _val));
					}
					_i--;
					continue;
				}

				if(_loptspec.containsKey(_opt))
				{
					int _aopt = _loptspec.get(_opt);
					if(_aopt==0)
					{
						_argopts.add(Pair.from(_opt, Boolean.TRUE));
					}
					else
					{
						_argopts.add(Pair.from(_opt, _args.get(_i)));
						_args.remove(_i);
					}
					_i--;
					continue;
				}

				LuaValue.error(_opt + " is no valid option");
			}
		}

		if(!_long_opts_only) {
			String _short = args.checkjstring(1);

			Map<String,Boolean> _optspec = new LinkedHashMap<>();

			boolean _proc_invalid_opt = false;
			boolean _ignore_invalid_opt = false;
			boolean _first_nonarg_ends = false;
			for(int _i=0; _i<_short.length(); _i++) {
				char _c = _short.charAt(_i);

				if(_i==0 && _c==':') {
					_proc_invalid_opt = true;
					continue;
				} else if(_c=='-') {
					_ignore_invalid_opt = true;
					continue;
				} else if(_c=='+') {
					_first_nonarg_ends=true;
					continue;
				}

				//check for arg
				boolean _check_arg = (_i+1<_short.length());
				if(!_check_arg) {
					_optspec.put(Character.toString(_c), Boolean.FALSE);
				} else if(_short.charAt(_i+1)==':') {
					_optspec.put(Character.toString(_c), Boolean.TRUE);
					_i++;
				} else {
					_optspec.put(Character.toString(_c), Boolean.FALSE);
				}
			}

			for(int _i=0; _i< _args.size(); _i++) {
				String _opt = _args.get(_i);

				if (_opt.equalsIgnoreCase("--")) {
					_args.remove(_i);
					break;
				} else if (_opt.equalsIgnoreCase("-")) {
					if(_first_nonarg_ends) break;
					continue;
				} else if (_with_long_opts && _opt.startsWith("--")) {
					continue;
				} else if (_opt.startsWith("-")) {
					_args.remove(_i);

					for (int _j = 1; _j < _opt.length(); _j++) {
						String _c = _opt.substring(_j, _j + 1);
						if (_optspec.containsKey(_c)) {
							if (_optspec.get(_c)) {
								_argopts.add(Pair.from(_c, _args.get(_i)));
								_args.remove(_i);
							} else {
								_argopts.add(Pair.from(_c, Boolean.TRUE));
							}
						} else if(_proc_invalid_opt) {
							_argopts.add(Pair.from(_c, Boolean.TRUE));
						} else if(!_ignore_invalid_opt) {
							LuaValue.error( _opt + " is no valid option");
						}
					}
					_i--;
					continue;
				}

				if(_first_nonarg_ends) break;
			}
		}

		return toReturn(_argopts, _args, _ext);
	}

	public static final class Pair<A,B>
	{
		A val0;
		B val1;

		public static <A,B> Pair<A,B> from(A v0, B v1)
		{
			Pair _p = new Pair();
			_p.val0 = v0;
			_p.val1 = v1;
			return _p;
		}

		public A get0()
		{
			return this.val0;
		}
		public B get1()
		{
			return this.val1;
		}

		public void set0(A v)
		{
			this.val0 = v;
		}
		public void set1(B v)
		{
			this.val1 = v;
		}
	}


	public static Varargs toReturn(List<Pair<String,Object>> _argopts, List<String> _args, boolean _ext)
	{
		if(_ext)
		{
			LuaList _l = new LuaList();
			for(Pair<String, Object> _pair : _argopts)
			{
				_l.insert(LuayHelper.toList(_pair.get0(), _pair.get1()));
			}
			return LuaValue.varargsOf(_l, LuayHelper.toList(_args));
		}
		else
		{
			LuaTable _t = new LuaTable();
			for(Pair<String, Object> _pair : _argopts)
			{
				_t.set(_pair.get0(), LuayHelper.toValue(_pair.get1()));
			}
			return LuaValue.varargsOf(_t, LuayHelper.toList(_args));
		}
	}
}
