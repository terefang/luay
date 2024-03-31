package luay.lib.ext.more;

import lombok.SneakyThrows;
import luay.lib.ext.AbstractLibrary;
import luay.vm.LuaFunction;
import luay.vm.LuaString;
import luay.vm.LuaValue;
import luay.vm.Varargs;
import luay.vm.lib.java.CoerceJavaToLua;
import luay.lib.LuayLibraryFactory;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

public class HashLib extends AbstractLibrary implements LuayLibraryFactory
{
	@Override
	public String getLibraryName() {
		return "hash";
	}

	public HashLib() {
		super();
	}

	@Override
	public List<LuaFunction> getLibraryFunctions() {
		return toList(
				_twoArgFunctionWrapper.from("to_xxid", HashLib::_lua_toxxid),
				_oneArgFunctionWrapper.from("to_xid", HashLib::_lua_toxid),
				_varArgFunctionWrapper.from("to_uuid", HashLib::_lua_touuid),
				_oneArgFunctionWrapper.from("to_hex", HashLib::_lua_tohex),
				_oneArgFunctionWrapper.from("to_b32", HashLib::_lua_tob32),
				_oneArgFunctionWrapper.from("to_b26", HashLib::_lua_tob26),
				_oneArgFunctionWrapper.from("to_b36", HashLib::_lua_tob36),
				_oneArgFunctionWrapper.from("to_b64", HashLib::_lua_tob64),
				_oneArgFunctionWrapper.from("to_b64a", HashLib::_lua_tob64a),
				_oneArgFunctionWrapper.from("to_b62", HashLib::_lua_tob62),
				_oneArgFunctionWrapper.from("from_hex", HashLib::_lua_fromhex),
				_oneArgFunctionWrapper.from("from_b32", HashLib::_lua_fromb32),
				_oneArgFunctionWrapper.from("from_b64", HashLib::_lua_fromb64),
				_oneArgFunctionWrapper.from("from_b64a", HashLib::_lua_fromb64a),
				_varArgFunctionWrapper.from("hash", HashLib::_lua_hash),
				_varArgFunctionWrapper.from("hash_hex", HashLib::_lua_hash_hex),
				_varArgFunctionWrapper.from("hashmac", HashLib::_lua_hashmac),
				_varArgFunctionWrapper.from("hashmac_hex", HashLib::_lua_hashmac_hex),
				_varArgFunctionWrapper.from("random_string", HashLib::_lua_random_string),
				_varArgFunctionWrapper.from("md5", HashLib::_lua_md5),
				_varArgFunctionWrapper.from("sha1", HashLib::_lua_sha1),
				_varArgFunctionWrapper.from("sha256", HashLib::_lua_sha256),
				_varArgFunctionWrapper.from("sha512", HashLib::_lua_sha512),
				_varArgFunctionWrapper.from("sha3", HashLib::_lua_sha3),
				_varArgFunctionWrapper.from("md5mac", HashLib::_lua_md5mac),
				_varArgFunctionWrapper.from("sha1mac", HashLib::_lua_sha1mac),
				_varArgFunctionWrapper.from("sha256mac", HashLib::_lua_sha256mac),
				_varArgFunctionWrapper.from("sha512mac", HashLib::_lua_sha512mac),
				_varArgFunctionWrapper.from("md5sslmac", HashLib::_lua_md5sslmac),
				_varArgFunctionWrapper.from("sha1sslmac", HashLib::_lua_sha1sslmac),
				_oneArgFunctionWrapper.from("new_hash", HashLib::_lua_newhash),
				_twoArgFunctionWrapper.from("new_mac", HashLib::_lua_newmac)
		);
	}

	private static Varargs _lua_random_string(Varargs args) {
		StringBuilder _sb = new StringBuilder();
		try {
			int _len = args.checkint(1);
			List<String> _hay = new Vector<>();
			_hay.add("oqjKJhmszCVcFlnEDaeIXkGZuORLTvwHpStPyWBixgNYMbUQrAdf");
			_hay.add("&1$/06+%2874*35.9");
			if(args.narg()>1) {
				_hay.clear();
				for(int _i = 2;_i<=args.narg(); _i++) {
					String _h = args.checkjstring(_i);
					if(_h.length()>0) {
						_hay.add(_h);
					}
				}
			}

			byte[] _salt = randomSalt((_len*2)+1);
			int _last = -1;
			int _idx = 0;
			for(int _i=0; _i<_len; _i++) {
				String _hs = (_i==0) ? _hay.get(0)
						: _hay.get((_salt[(_i*2)%_salt.length]&0xff)%_hay.size());
				_idx = _salt[((_i*2)+1)%_salt.length]&0xff;
				_idx = _idx % _hs.length();
				if(_idx == _last) {
					_len++;
					continue;
				}
				_sb.append((char)_hs.charAt(_idx));
				_last = _idx;
			}
		} catch (Exception _xe) {
			_xe.printStackTrace(System.err);
		}
		return LuaString.valueOf(_sb.toString());
	}

	private static Varargs _lua_hash(Varargs args) {
		return _lua_md(args.checkjstring(1), args.subargs(2));
	}

	private static Varargs _lua_hash_hex(Varargs args) {
		return _lua_md_hex(args.checkjstring(1), args.subargs(2));
	}

	private static Varargs _lua_hashmac(Varargs args) {
		return _lua_mac(args.checkjstring(1), args.subargs(2));
	}

	private static Varargs _lua_hashmac_hex(Varargs args) {
		return _lua_mac_hex(args.checkjstring(1), args.subargs(2));
	}

	private static Varargs _lua_md(String _algo, Varargs args) {
		try {
			_hash _h = _hash.from(_algo);
			for(int _i = 1; _i<=args.narg(); _i++ ) {
				_h.update(args.arg(_i));
			}
			return _h.finish();
		} catch (NoSuchAlgorithmException e) {
			return varargsOf(LuaValue.NIL, LuaString.valueOf(e.getMessage()));
		}
	}

	private static Varargs _lua_md_hex(String _algo, Varargs args) {
		try {
			_hash _h = _hash.from(_algo);
			for(int _i = 1; _i<=args.narg(); _i++ ) {
				_h.update(args.arg(_i));
			}
			return _h.finish_hex();
		} catch (NoSuchAlgorithmException e) {
			return varargsOf(LuaValue.NIL, LuaString.valueOf(e.getMessage()));
		}
	}

	private static Varargs _lua_mac(String _algo, Varargs args) {
		try {
			_mac _h = _mac.from(_algo, args.checkstring(1).getBytes());
			for(int _i = 2; _i<=args.narg(); _i++ ) {
				_h.update(args.arg(_i));
			}
			return _h.finish();
		} catch (NoSuchAlgorithmException e) {
			return varargsOf(LuaValue.NIL, LuaString.valueOf(e.getMessage()));
		} catch (InvalidKeyException e) {
			return varargsOf(LuaValue.NIL, LuaString.valueOf(e.getMessage()));
		}
	}

	private static Varargs _lua_mac_hex(String _algo, Varargs args) {
		try {
			_mac _h = _mac.from(_algo, args.checkstring(1).getBytes());
			for(int _i = 2; _i<=args.narg(); _i++ ) {
				_h.update(args.arg(_i));
			}
			return _h.finish_hex();
		} catch (NoSuchAlgorithmException e) {
			return varargsOf(LuaValue.NIL, LuaString.valueOf(e.getMessage()));
		} catch (InvalidKeyException e) {
			return varargsOf(LuaValue.NIL, LuaString.valueOf(e.getMessage()));
		}
	}

	private static Varargs _lua_md5(Varargs args){
		return _lua_md("MD5", args);
	}

	private static Varargs _lua_sha1(Varargs args){
		return _lua_md("SHA-1", args);
	}

	private static Varargs _lua_sha256(Varargs args){
		return _lua_md("SHA-256", args);
	}

	private static Varargs _lua_sha512(Varargs args){
		return _lua_md("SHA-512", args);
	}

	private static Varargs _lua_sha3(Varargs args){
		return _lua_md("SHA3-512", args);
	}

	private static Varargs _lua_md5sslmac(Varargs args){
		return _lua_mac("SslMacMD5", args);
	}

	private static Varargs _lua_sha1sslmac(Varargs args){
		return _lua_mac("SslMacSHA1", args);
	}

	private static Varargs _lua_md5mac(Varargs args){
		return _lua_mac("HMACMD5", args);
	}

	private static Varargs _lua_sha1mac(Varargs args){
		return _lua_mac("HMACSHA1", args);
	}

	private static Varargs _lua_sha256mac(Varargs args){
		return _lua_mac("HMACSHA256", args);
	}

	private static Varargs _lua_sha512mac(Varargs args){
		return _lua_mac("HMACSHA512", args);
	}

	private static LuaValue _lua_tob26(LuaValue arg1) {
		return LuaString.valueOf(HashLib.toBase26(arg1.checkstring().getBytes()));
	}

	private static LuaValue _lua_tob32(LuaValue arg1) {
		return LuaString.valueOf(HashLib.toBase32(arg1.checkstring().getBytes()));
	}

	private static LuaValue _lua_tob36(LuaValue arg1) {
		return LuaString.valueOf(HashLib.toBase36(arg1.checkstring().getBytes()));
	}

	private static LuaValue _lua_tob64(LuaValue arg1) {
		return LuaString.valueOf(HashLib.toBase64Np(arg1.checkstring().getBytes()));
	}

	private static LuaValue _lua_tob64a(LuaValue arg1) {
		return LuaString.valueOf(HashLib.toBase64aNp(arg1.checkstring().getBytes()));
	}

	private static LuaValue _lua_tob62(LuaValue arg1) {
		return LuaString.valueOf(HashLib.toBase62(arg1.checkstring().getBytes()));
	}

	private static LuaValue _lua_tohex(LuaValue arg1) {
		return LuaString.valueOf(HashLib.toHex(arg1.checkstring().getBytes()));
	}


	private static LuaValue _lua_fromb32(LuaValue arg1) {
		return LuaString.valueOf(HashLib.fromBase32(arg1.checkjstring()));
	}

	private static LuaValue _lua_fromb64(LuaValue arg1) {
		return LuaString.valueOf(HashLib.fromBase64(arg1.checkjstring()));
	}

	private static LuaValue _lua_fromb64a(LuaValue arg1) {
		return LuaString.valueOf(HashLib.fromBase64a(arg1.checkjstring()));
	}

	private static LuaValue _lua_fromhex(LuaValue arg1) {
		return LuaString.valueOf(HashLib.fromHex(arg1.checkjstring()));
	}

	private static LuaValue _lua_touuid(Varargs args) {
		if(args.narg()==0) {
			return LuaString.valueOf(HashLib.toUUID(null));
		}
		return LuaString.valueOf(HashLib.toUUID(args.checkstring(1).getBytes()));
	}

	private static LuaValue _lua_toxid(LuaValue arg1) {
		return LuaString.valueOf(HashLib.toXID(arg1.checkstring().getBytes()));
	}

	private static LuaValue _lua_toxxid(LuaValue arg1, LuaValue arg2) {
		return LuaString.valueOf(HashLib.toXXID(arg1.checkstring().getBytes(), arg2.checkstring().getBytes()));
	}

	private static Varargs _lua_newhash(LuaValue arg1) {
		return HashLib._hash.of(arg1.checkjstring());
	}

	private static Varargs _lua_newmac(LuaValue arg1, LuaValue arg2) {
		return HashLib._mac.of(arg1.checkjstring(), arg2.checkstring().getBytes());
	}

	public static byte[] hash(String _name, byte[] _buffer) {
		try {
			MessageDigest _md = MessageDigest.getInstance(_name);
			return _md.digest(_buffer);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	public static String hashHex(String _name, byte[] _buffer) {
		try {
			MessageDigest _md = MessageDigest.getInstance(_name);
			return toHex(_md.digest(_buffer));
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	static char[] HEXDIGITS = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	static int[] HEXREV= {
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			0,  1,  2,  3,  4,  5,  6,  7,  8,  9, -1, -1, -1, -1, -1, -1,
			-1, 10, 11, 12, 13, 14, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, 10, 11, 12, 13, 14, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, };

	public static String toHex(byte[] _buf) {
		char[] _out = new char[_buf.length*2];
		for(int _i=0; _i<_buf.length; _i++) {
			_out[_i*2] = HEXDIGITS[(_buf[_i]>>>4) & 0xf];
			_out[_i*2+1] = HEXDIGITS[_buf[_i] & 0xf];
		}
		return new String(_out);
	}

	public static byte[] fromHex(String _data) {
		byte[] _out = new byte[_data.length()/2];
		for(int _i=0; _i<_data.length(); _i+=2) {
			int _c1 = HEXREV[_data.charAt(_i) & 0xff];
			int _c2 = HEXREV[_data.charAt(_i+1) & 0xff];
			if(_c1>=0) {
				_out[_i/2] = (byte) (((_c1<<4) | _c2) & 0xff);
			}
		}
		return _out;
	}
	public static String toBase64(byte[] binaryData) {
		return Base64.getEncoder().encodeToString(binaryData);
	}

	public static String toBase64Np(byte[] binaryData) {
		return Base64.getEncoder().withoutPadding().encodeToString(binaryData);
	}

	public static String toBase64aNp(byte[] binaryData) {
		return Base64.getEncoder().withoutPadding().encodeToString(binaryData).replace('+', '.');
	}

	public static byte[] fromBase64(String _b64) {
		return Base64.getDecoder().decode(_b64);
	}

	public static byte[] fromBase64a(String _b64) {
		return Base64.getDecoder().decode(_b64.replace('.', '+'));
	}

	@SneakyThrows
	public static byte[] randomSalt(int _l) {
		return SecureRandom.getInstanceStrong().generateSeed(_l);
	}

	public static final String B64T_STRING = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	/**
	 * Table with characters for Base64 transformation.
	 */
	public static final char[] B64T_ARRAY = B64T_STRING.toCharArray();

	public static void b64tfrom24bit(final byte b2, final byte b1, final byte b0, final int outLen,
									final StringBuilder buffer) {
		// The bit masking is necessary because the JVM byte type is signed!
		int w = ((b2 << 16) & 0x00ffffff) | ((b1 << 8) & 0x00ffff) | (b0 & 0xff);
		// It's effectively a "for" loop but kept to resemble the original C code.
		int n = outLen;
		while (n-- > 0) {
			buffer.append(B64T_ARRAY[w & 0x3f]);
			w >>= 6;
		}
	}

	public static String getB64TRandomSalt(final int num) {
		return getB64TRandomSalt(num, new SecureRandom());
	}

	public static String getB64TRandomSalt(final int num, final Random random) {
		final StringBuilder saltString = new StringBuilder(num);
		for (int i = 1; i <= num; i++) {
			saltString.append(B64T_STRING.charAt(random.nextInt(B64T_STRING.length())));
		}
		return saltString.toString();
	}
	static char[] B26DIGITS = {'Q','W','E','R','T','Z','U','I','O','P',
			'A','S','D','F','G','H','J','K','L',
			'Y','X','C','V','B','N','M'};
	static int[] B26REV = {
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, 10, 23, 21, 12,  2, 13, 14, 15,  7, 16, 17, 18, 25, 24,  8,
			9,  0,  3, 11,  4,  6, 22,  1, 20, 19,  5, -1, -1, -1, -1, -1,
			-1, 10, 23, 21, 12,  2, 13, 14, 15,  7, 16, 17, 18, 25, 24,  8,
			9,  0,  3, 11,  4,  6, 22,  1, 20, 19,  5, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
	};
	static BigInteger B26L = BigInteger.valueOf(B26DIGITS.length);

	public static String toBase26(byte[] binaryData) {
		StringBuffer _sb = new StringBuffer();
		BigInteger _bi = new BigInteger(1,binaryData);
		while(_bi.compareTo(BigInteger.ZERO) > 0) {
			_sb.append((char)B26DIGITS[_bi.mod(B26L).intValue()]);
			_bi = _bi.divide(B26L);
		}
		return _sb.toString();
	}

	static char[] B62DIGITS = {'Q','W','E','R','T','Z','U','I','O','P',
			'A','S','D','F','G','H','J','K','L',
			'Y','X','C','V','B','N','M',
			'0','1','2','3','4','5','6','7','8','9',
			'q','w','e','r','t','z','u','i','o','p',
			'a','s','d','f','g','h','j','k','l',
			'y','x','c','v','b','n','m'};
	static BigInteger B62L = BigInteger.valueOf(B62DIGITS.length);

	public static String toBase62(byte[] binaryData) {
		StringBuffer _sb = new StringBuffer();
		BigInteger _bi = new BigInteger(1,binaryData);
		while(_bi.compareTo(BigInteger.ZERO) > 0) {
			_sb.append((char)B62DIGITS[_bi.mod(B62L).intValue()]);
			_bi = _bi.divide(B62L);
		}
		return _sb.toString();
	}

	public static String toBase36(byte[] binaryData) {
		return new BigInteger(1,binaryData).toString(36).toUpperCase();
	}

	private static final char[] B32DIGITS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".toCharArray();
	private static final byte[] B32REV = {
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, 26, 27, 28, 29, 30, 31, -1, -1, -1, -1, -1, -1, -1, -1,
			-1,  0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14,
			15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,
			-1,  0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14,
			15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
	};
	static BigInteger B32L = BigInteger.valueOf(B32DIGITS.length);

	public static String toBase32(final byte[] bytes) {
		int _len = (((bytes.length * 8) + 4) / 5);
		StringBuffer _sb = new StringBuffer(_len);
		BigInteger _bi = new BigInteger(1,bytes);
		while(_bi.compareTo(BigInteger.ZERO) > 0) {
			_sb.append((char)B32DIGITS[_bi.mod(B32L).intValue()]);
			_bi = _bi.divide(B32L);
			_len--;
		}
		while(_len>0) {
			_sb.append((char)B32DIGITS[0]);
			_len--;
		}
		return _sb.reverse().toString();
	}

	public static byte[] fromBase32(final String _data) {
		int _len = (((_data.length()*5)+4)/8);
		BigInteger _bi = BigInteger.valueOf(0);
		for(int _i=0; _i<_data.length(); _i++) {
			int _c = B32REV[_data.charAt(_i) & 0xff];
			if(_c>=0) {
				_bi = _bi.multiply(B32L).add(BigInteger.valueOf(_c));
			}
		}
		byte[] _tmp = _bi.toByteArray();
		if(_len == _tmp.length-1) {
			byte[] _out = new byte[_len];
			System.arraycopy(_tmp, 1, _out, 0, _len);
			return _out;
		}
		return _tmp;
	}

	public static String toUUID(byte[] binaryData) {
		if(binaryData==null) {
			return UUID.randomUUID().toString().toUpperCase();
		}
		return UUID.nameUUIDFromBytes(binaryData).toString().toUpperCase();
	}

	public static String toXID(byte[] binaryData) {
		UUID _id = UUID.nameUUIDFromBytes(binaryData);
		return (BigInteger.valueOf(_id.getLeastSignificantBits()>>>32).toString(36)
				+"-"+
				BigInteger.valueOf(_id.getLeastSignificantBits() & 0x7ffffff).toString(36)
				+"-"+
				BigInteger.valueOf(_id.getMostSignificantBits()>>>32).toString(36)
				+"-"+
				BigInteger.valueOf(_id.getMostSignificantBits() & 0x7ffffff).toString(36)).toUpperCase();
	}

	public static String toXXID(byte[] binaryData1, byte[] binaryData2) {
		UUID _id1 = UUID.nameUUIDFromBytes(binaryData1);
		UUID _id2 = UUID.nameUUIDFromBytes(binaryData2);
		return (
				BigInteger.valueOf(_id1.getLeastSignificantBits()>>>32).toString(36)
						+"-"+
						BigInteger.valueOf(_id1.getLeastSignificantBits() & 0x7ffffff).toString(36)
						+"-"+
						BigInteger.valueOf((_id2.getLeastSignificantBits() & 0x7ffffff)^(_id1.getMostSignificantBits()>>>32)).toString(36)
						+"-"+
						BigInteger.valueOf((_id2.getLeastSignificantBits()>>>32)^(_id1.getMostSignificantBits() & 0x7ffffff)).toString(36)
						+"-"+
						BigInteger.valueOf(_id2.getMostSignificantBits()>>>32).toString(36)
						+"-"+
						BigInteger.valueOf(_id2.getMostSignificantBits() & 0x7ffffff).toString(36)
		).toUpperCase();
	}

	public static class _hash {
		private MessageDigest _md;

		public static _hash from(String _algo) throws NoSuchAlgorithmException {
			_hash _h = new _hash();
			_h._md = MessageDigest.getInstance(_algo);
			return _h;
		}

		public static Varargs of(String _algo) {
			_hash _h = null;
			try {
				_h = from(_algo);
			} catch (NoSuchAlgorithmException e) {
				return varargsOf(LuaValue.NIL, LuaString.valueOf(e.getMessage()));
			}
			return CoerceJavaToLua.coerce(_h);
		}

		public void update(Varargs args) {
			for(int _i=1; _i<=args.narg(); _i++) {
				this._md.update(args.checkstring(_i).getBytes());
			}
		}

		public LuaValue finish() {
			return LuaString.valueOf(this._md.digest());
		}

		public LuaValue finish_hex() {
			return LuaString.valueOf(toHex(this._md.digest()));
		}

		public LuaValue finish_b64() {
			return LuaString.valueOf(toBase64Np(this._md.digest()));
		}

		public LuaValue finish_b32() {
			return LuaString.valueOf(toBase32(this._md.digest()));
		}
	}

	public static class _mac {
		private static final String KEY_ALGO = "PBE";
		private static final String PROVIDER = "SunJCE";
		private Mac _mac;
		boolean pbe;
		private SecretKey _skey;

		public static _mac from(String _algo, byte[] _key) throws NoSuchAlgorithmException, InvalidKeyException {
			_mac _h = new _mac();
			if(_algo.contains(KEY_ALGO))
			{
				try {
					PBEKeySpec keySpec = new PBEKeySpec(new String(_key).toCharArray());
					SecretKeyFactory kf =
							SecretKeyFactory.getInstance(KEY_ALGO, PROVIDER);
					_h._skey = kf.generateSecret(keySpec);
					_h._mac = Mac.getInstance(_algo, PROVIDER);
					_h.pbe=true;

				} catch (NoSuchAlgorithmException
						 | InvalidKeySpecException
						 | NoSuchProviderException e)
				{
					//log.warn(e.getMessage(), e);
					return null;
				}
			}
			else
			{
				try {
					final SecretKeySpec _keySpec = new SecretKeySpec(_key, _algo);
					_h._mac = Mac.getInstance(_algo);
					_h._mac.init(_keySpec);
				} catch (NoSuchAlgorithmException | InvalidKeyException e) {
					//log.warn(e.getMessage(), e);
					return null;
				}
			}
			return _h;
		}

		public static Varargs of(String _algo, byte[] _key) {
			_mac _h = null;
			try {
				_h=from(_algo,_key);
			} catch (NoSuchAlgorithmException | InvalidKeyException e) {
				return varargsOf(LuaValue.NIL, LuaString.valueOf(e.getMessage()));
			}
			return CoerceJavaToLua.coerce(_h);
		}

		@SneakyThrows
		public void update(Varargs args)
		{
			int _start = 1;
			if(this.pbe)
			{
				PBEParameterSpec _spec = new PBEParameterSpec(args.checkstring(_start).getBytes(), 1024);
				this._mac.init(this._skey, _spec);
				_start++;
			}
			for(int _i=_start; _i<=args.narg(); _i++) {
				this._mac.update(args.checkstring(_i).getBytes());
			}
		}

		public LuaValue finish() {
			return LuaString.valueOf(this._mac.doFinal());
		}

		public LuaValue finish_hex() {
			return LuaString.valueOf(toHex(this._mac.doFinal()));
		}

		public LuaValue finish_b64() {
			return LuaString.valueOf(toBase64Np(this._mac.doFinal()));
		}

		public LuaValue finish_b32() {
			return LuaString.valueOf(toBase32(this._mac.doFinal()));
		}
	}

	public static void main(String[] args) {
		Provider[] providers = Security.getProviders();
		for (Provider provider : providers) {
			for (Provider.Service service : provider.getServices()) {
				System.err.println(String.format("%s - %s", service.getType(),service.getAlgorithm()));
			}
			for (Object _key : provider.keySet()) {
				System.err.println(_key.toString());
			}
		}
	}
}

/*
%!begin MARKDOWN

## hash Library

```lua
local hash = require('hash');
```
*   `hash.md5 (bytes [,bytes]) -> bytes`
*   `hash.sha1 (bytes [,bytes]) -> bytes`
*   `hash.sha256 (bytes [,bytes]) -> bytes`
*   `hash.sha512 (bytes [,bytes]) -> bytes`
*   `hash.sha3 (bytes [,bytes]) -> bytes`
*   `hash.hash (algo, bytes [,bytes]) -> bytes`
*   `hash.hash_hex (algo, bytes [,bytes]) -> bytes`

*   `hash.md5mac (salt,bytes [,bytes]) -> bytes`
*   `hash.sha1mac (salt,bytes [,bytes]) -> bytes`
*   `hash.sha256mac (salt,bytes [,bytes]) -> bytes`
*   `hash.sha512mac (salt,bytes [,bytes]) -> bytes`
*   `hash.hashmac (algo,salt,bytes [,bytes]) -> bytes`
*   `hash.hashmac_hex (algo,salt,bytes [,bytes]) -> bytes`

*   `hash.to_hex (bytes) -> string`
*   `hash.from_hex (string) -> bytes`
*   `hash.to_b32 (bytes) -> string`
*   `hash.from_b32 (string) -> bytes`
*   `hash.to_b64 (bytes) -> string`
*   `hash.from_b64 (string) -> bytes`
*   `hash.to_b26 (bytes) -> string`
*   `hash.to_b36 (bytes) -> string`
*   `hash.to_b62 (bytes) -> string`
*   `hash.to_uuid (bytes) -> string`
*   `hash.to_xid (bytes) -> string`
*   `hash.to_xxid (bytes, bytes) -> string`
*   `hash.random_string (int [, string[, string]]) -> string`

### hash object

```lua
local _md = hash.new_hash('MD5');
_md:update('this');
_md:update('hello','world');
print('md5 hex =',hash.to_hex(_md:finish()))
print('md5 hex =',_md:finish_hex())
print('md5 b64 =',_md:finish_b64())
print('md5 b32 =',_md:finish_b32())
```

### mac object

```lua
local _mh = hash.new_mac('HMACMD5', 's3cr3t');
_mh:update('this');
_mh:update('hello','world');
print('hmacmd5 hex =',hash.to_hex(_mh:finish()))
print('hmacmd5 hex =',_mh:finish_hex())
print('hmacmd5 b64 =',_mh:finish_b64())
print('hmacmd5 b32 =',_mh:finish_b32())
```

\pagebreak

%!end MARKDOWN

 */
