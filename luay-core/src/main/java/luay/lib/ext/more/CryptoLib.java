package luay.lib.ext.more;

import lombok.SneakyThrows;
import luay.lib.LuayLibraryFactory;
import luay.lib.ext.AbstractLibrary;
import luay.vm.LuaFunction;
import luay.vm.LuaString;
import luay.vm.LuaValue;
import luay.vm.Varargs;
import luay.vm.lib.VarArgFunction;
import luay.vm.lib.java.CoerceJavaToLua;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

public class CryptoLib extends AbstractLibrary implements LuayLibraryFactory
{
	static {
		try {
			Security.addProvider((Provider) Class.forName("org.bouncycastle.jce.provider.BouncyCastleProvider").newInstance());
		} catch (Exception _xe)
		{
			System.err.println(_xe.getMessage());
		}
	}
	@Override
	public String getLibraryName() {
		return "crypto";
	}

	public CryptoLib() {
		super();
	}

	@Override
	public List<LuaFunction> getLibraryFunctions() {
		return toList(
				_varArgFunctionWrapper.from("blowfish_cbc_pkcs5", CryptoLib::_lua_blowfish),
				_varArgFunctionWrapper.from("cipher", CryptoLib::_lua_cipher)
		);
	}

	private static Varargs _lua_blowfish(Varargs args){
		return _lua_mkcipher("Blowfish/CBC/PKCS5Padding", args);
	}

	private static Varargs _lua_cipher(Varargs args){
		return _lua_mkcipher(args.checkjstring(1), args.subargs(2));
	}

	private static Varargs _lua_mkcipher(String _algo, Varargs _args){
		return _cipher.from(_algo, _args);
	}

	static class _cipher extends VarArgFunction
	{
		private SecretKeySpec _keySpec;
		private Cipher _cypher;
		private IvParameterSpec _iv;

		@SneakyThrows
		static _cipher from(String _algo, Varargs _args)
		{
			_cipher _c = new _cipher();
			boolean encrypt = _args.checkboolean(1);
			String _keyType = _algo;
			int _ofs;
			if((_ofs=_keyType.indexOf('/'))>0)
			{
				_keyType = _keyType.substring(0,_ofs);
			}
			byte[] _key = _args.checkstring(2).getBytes();
			_c._keySpec = new SecretKeySpec(_key, _keyType);
			_c._cypher = Cipher.getInstance(_algo);
			if(_args.narg()>2)
			{
				_c._iv = new IvParameterSpec(_args.checkstring(3).getBytes());
				_c._cypher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE , _c._keySpec, _c._iv);
			}
			else
			{
				_c._cypher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE , _c._keySpec);
			}
			return _c;
		}

		@Override @SneakyThrows
		public Varargs invoke(Varargs _args)
		{
			boolean _final = false;
			if(_args.narg()==2)
			{
				_final = _args.checkboolean(2);
			}

			if(_args.narg()==0)
			{
				return LuaValue.valueOf(this._cypher.doFinal());
			}
			else if(_final)
			{
				byte[] _plaintext = _args.checkstring(1).getBytes();
				return LuaValue.valueOf(this._cypher.doFinal(_plaintext));
			}
			else
			{
				byte[] _plaintext = _args.checkstring(1).getBytes();
				return LuaValue.valueOf(this._cypher.update(_plaintext));
			}
		}
	}
}

/*
%!begin MARKDOWN

## crypto Library

```lua
local crypto = require('crypto');
```
*   `crypto.blowfish_cbc_pkcs5(encrypt_flag, key_bytes [,initvec_bytes]) -> function` \
	function(bytes [, final_flag]) -> bytes – does blowfish in CBC mode

*   `crypto.cipher(algorithm, encrypt_flag, key_bytes [,initvec_bytes]) -> function` \
	function(bytes [, final_flag]) -> bytes – does blowfish in CBC mode

\pagebreak

%!end MARKDOWN

 */
