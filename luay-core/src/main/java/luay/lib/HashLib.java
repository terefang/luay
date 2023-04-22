package luay.lib;

import lombok.SneakyThrows;
import luay.vm.LuaFunction;
import luay.vm.LuaString;
import luay.vm.LuaValue;
import luay.vm.Varargs;
import luay.vm.lib.java.CoerceJavaToLua;
import luay.vm.luay.AbstractLibrary;
import luay.vm.luay.LuayLibraryFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class HashLib extends AbstractLibrary  implements LuayLibraryFactory
{
	@Override
	public String getLibraryName() {
		return "hash";
	}

	public HashLib()
	{
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
				_oneArgFunctionWrapper.from("to_b62", HashLib::_lua_tob62),
				_oneArgFunctionWrapper.from("from_hex", HashLib::_lua_fromhex),
				_oneArgFunctionWrapper.from("from_b32", HashLib::_lua_fromb32),
				_oneArgFunctionWrapper.from("from_b64", HashLib::_lua_fromb64),
				_twoArgFunctionWrapper.from("hash", HashLib::_lua_hash),
				_twoArgFunctionWrapper.from("hash_hex", HashLib::_lua_hash_hex),
				_oneArgFunctionWrapper.from("md5", HashLib::_lua_md5),
				_oneArgFunctionWrapper.from("sha1", HashLib::_lua_sha1),
				_oneArgFunctionWrapper.from("sha256", HashLib::_lua_sha256),
				_oneArgFunctionWrapper.from("sha512", HashLib::_lua_sha512),
				_oneArgFunctionWrapper.from("new_hash", HashLib::_lua_newhash),
				_twoArgFunctionWrapper.from("new_mac", HashLib::_lua_newmac)
		);
	}

	private static LuaValue _lua_hash(LuaValue arg1, LuaValue arg2) {
		return LuaString.valueOf(HashLib.hash(arg1.checkjstring(), arg2.checkstring().getBytes()));
	}

	private static LuaValue _lua_hash_hex(LuaValue arg1, LuaValue arg2) {
		return LuaString.valueOf(HashLib.hashHex(arg1.checkjstring(), arg2.checkstring().getBytes()));
	}

	private static LuaValue _lua_md5(LuaValue arg1) {
		return LuaString.valueOf(HashLib.hash("MD5", arg1.checkstring().getBytes()));
	}

	private static LuaValue _lua_sha1(LuaValue arg1) {
		return LuaString.valueOf(HashLib.hash("SHA-1", arg1.checkstring().getBytes()));
	}

	private static LuaValue _lua_sha256(LuaValue arg1) {
		return LuaString.valueOf(HashLib.hash("SHA-256", arg1.checkstring().getBytes()));
	}

	private static LuaValue _lua_sha512(LuaValue arg1) {
		return LuaString.valueOf(HashLib.hash("SHA-512", arg1.checkstring().getBytes()));
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

	private static LuaValue _lua_fromhex(LuaValue arg1) {
		return LuaString.valueOf(HashLib.fromHex(arg1.checkjstring()));
	}

	private static LuaValue _lua_touuid(Varargs args)
	{
		if(args.narg()==0)
		{
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

	public static byte[] hash(String _name, byte[] _buffer)
	{
		try {
			MessageDigest _md = MessageDigest.getInstance(_name);
			return _md.digest(_buffer);
		}
		catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	public static String hashHex(String _name, byte[] _buffer)
	{
		try {
			MessageDigest _md = MessageDigest.getInstance(_name);
			return toHex(_md.digest(_buffer));
		}
		catch (NoSuchAlgorithmException e) {
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

	public static String toHex(byte[] _buf)
	{
		char[] _out = new char[_buf.length*2];
		for(int _i=0; _i<_buf.length; _i++)
		{
			_out[_i*2] = HEXDIGITS[(_buf[_i]>>>4) & 0xf];
			_out[_i*2+1] = HEXDIGITS[_buf[_i] & 0xf];
		}
		return new String(_out);
	}

	public static byte[] fromHex(String _data)
	{
		byte[] _out = new byte[_data.length()/2];
		for(int _i=0; _i<_data.length(); _i+=2)
		{
			int _c1 = HEXREV[_data.charAt(_i) & 0xff];
			int _c2 = HEXREV[_data.charAt(_i+1) & 0xff];
			if(_c1>=0)
			{
				_out[_i/2] = (byte) (((_c1<<4) | _c2) & 0xff);
			}
		}
		return _out;
	}

	public static String hashMacHex(String _name, byte[] _key, byte[]... _buffer)
	{
		return toHex(hashMac(_name, _key, _buffer));
	}

	public static byte[] hashMac(String _name, byte[] _key, byte[]... _buffer)
	{
		try {
			final SecretKeySpec _keySpec = new SecretKeySpec(_key, _name);
			final Mac _mac = Mac.getInstance(_name);
			_mac.init(_keySpec);
			for(byte[] _b : _buffer)
			{
				_mac.update(_b);
			}
			return _mac.doFinal();
		}
		catch (NoSuchAlgorithmException | InvalidKeyException e) {
			//log.warn(e.getMessage(), e);
			return null;
		}
	}

	public static String toBase64(byte[] binaryData) {
		return Base64.getEncoder().encodeToString(binaryData);
	}

	public static String toBase64Np(byte[] binaryData) {
		return Base64.getEncoder().withoutPadding().encodeToString(binaryData);
	}

	public static byte[] fromBase64(String _b64) {
		return Base64.getDecoder().decode(_b64);
	}

	@SneakyThrows
	public static byte[] randomSalt(int _l)
	{
		return SecureRandom.getInstanceStrong().generateSeed(_l);
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

	public static String toBase26(byte[] binaryData)
	{
		StringBuffer _sb = new StringBuffer();
		BigInteger _bi = new BigInteger(1,binaryData);
		while(_bi.compareTo(BigInteger.ZERO) > 0)
		{
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

	public static String toBase62(byte[] binaryData)
	{
		StringBuffer _sb = new StringBuffer();
		BigInteger _bi = new BigInteger(1,binaryData);
		while(_bi.compareTo(BigInteger.ZERO) > 0)
		{
			_sb.append((char)B62DIGITS[_bi.mod(B62L).intValue()]);
			_bi = _bi.divide(B62L);
		}
		return _sb.toString();
	}

	public static String toBase36(byte[] binaryData)
	{
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

	public static String toBase32(final byte[] bytes)
	{
		int _len = (((bytes.length * 8) + 4) / 5);
		StringBuffer _sb = new StringBuffer(_len);
		BigInteger _bi = new BigInteger(1,bytes);
		while(_bi.compareTo(BigInteger.ZERO) > 0)
		{
			_sb.append((char)B32DIGITS[_bi.mod(B32L).intValue()]);
			_bi = _bi.divide(B32L);
			_len--;
		}
		while(_len>0)
		{
			_sb.append((char)B32DIGITS[0]);
			_len--;
		}
		return _sb.reverse().toString();
	}

	public static byte[] fromBase32(final String _data)
	{
		int _len = (((_data.length()*5)+4)/8);
		BigInteger _bi = BigInteger.valueOf(0);
		for(int _i=0; _i<_data.length(); _i++)
		{
			int _c = B32REV[_data.charAt(_i) & 0xff];
			if(_c>=0)
			{
				_bi = _bi.multiply(B32L).add(BigInteger.valueOf(_c));
			}
		}
		byte[] _tmp = _bi.toByteArray();
		if(_len == _tmp.length-1)
		{
			byte[] _out = new byte[_len];
			System.arraycopy(_tmp, 1, _out, 0, _len);
			return _out;
		}
		return _tmp;
	}

	public static String toUUID(byte[] binaryData)
	{
		if(binaryData==null)
		{
			return UUID.randomUUID().toString().toUpperCase();
		}
		return UUID.nameUUIDFromBytes(binaryData).toString().toUpperCase();
	}

	public static String toXID(byte[] binaryData)
	{
		UUID _id = UUID.nameUUIDFromBytes(binaryData);
		return (BigInteger.valueOf(_id.getLeastSignificantBits()>>>32).toString(36)
				+"-"+
				BigInteger.valueOf(_id.getLeastSignificantBits() & 0x7ffffff).toString(36)
				+"-"+
				BigInteger.valueOf(_id.getMostSignificantBits()>>>32).toString(36)
				+"-"+
				BigInteger.valueOf(_id.getMostSignificantBits() & 0x7ffffff).toString(36)).toUpperCase();
	}

	public static String toXXID(byte[] binaryData1, byte[] binaryData2)
	{
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

	public static class _hash
	{
		private MessageDigest _md;

		public static Varargs of(String _algo)
		{
			_hash _h = new _hash();
			try {
				_h._md = MessageDigest.getInstance(_algo);
			}
			catch (NoSuchAlgorithmException e) {
				return varargsOf(LuaValue.NIL, LuaString.valueOf(e.getMessage()));
			}
			return CoerceJavaToLua.coerce(_h);
		}

		public void update(Varargs args)
		{
			for(int _i=1; _i<=args.narg(); _i++)
			{
				this._md.update(args.checkstring(_i).getBytes());
			}
		}

		public LuaValue finish()
		{
			return LuaString.valueOf(this._md.digest());
		}
	}

	public static class _mac
	{
		private Mac _mac;

		public static Varargs of(String _algo, byte[] _key)
		{
			_mac _h = new _mac();
			try {
				final SecretKeySpec _keySpec = new SecretKeySpec(_key, _algo);
				_h._mac = Mac.getInstance(_algo);
				_h._mac.init(_keySpec);
			}
			catch (NoSuchAlgorithmException | InvalidKeyException e) {
				return varargsOf(LuaValue.NIL, LuaString.valueOf(e.getMessage()));
			}
			return CoerceJavaToLua.coerce(_h);
		}

		public void update(Varargs args)
		{
			for(int _i=1; _i<=args.narg(); _i++)
			{
				this._mac.update(args.checkstring(_i).getBytes());
			}
		}

		public LuaValue finish()
		{
			return LuaString.valueOf(this._mac.doFinal());
		}
	}
}
