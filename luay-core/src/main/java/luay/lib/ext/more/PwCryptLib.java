package luay.lib.ext.more;

import lombok.SneakyThrows;
import luay.lib.LuayLibraryFactory;
import luay.lib.ext.AbstractLibrary;
import luay.vm.LuaFunction;
import luay.vm.LuaValue;
import luay.vm.Varargs;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PwCryptLib extends AbstractLibrary implements LuayLibraryFactory
{
	@Override
	public String getLibraryName() {
		return "pwcrypt";
	}

	public PwCryptLib() {
		super();
	}

	@Override
	public List<LuaFunction> getLibraryFunctions() {
		return toList(
				_varArgFunctionWrapper.from("b64crypt", PwCryptLib::_lua_b64crypt),
				_varArgFunctionWrapper.from("md5crypt", PwCryptLib::_lua_md5crypt),
				_varArgFunctionWrapper.from("apr1crypt", PwCryptLib::_lua_apr1crypt),
				_varArgFunctionWrapper.from("sha512crypt", PwCryptLib::_lua_sha512crypt),
				_varArgFunctionWrapper.from("sha256crypt", PwCryptLib::_lua_sha256crypt),
				_varArgFunctionWrapper.from("sha1crypt", PwCryptLib::_lua_sha1crypt),
				_varArgFunctionWrapper.from("shacrypt", PwCryptLib::_lua_shacrypt),
				_varArgFunctionWrapper.from("sha1_b64", PwCryptLib::_lua_sha1_b64),
				_varArgFunctionWrapper.from("sha1_hex", PwCryptLib::_lua_sha1_hex),
				_varArgFunctionWrapper.from("checkpw", PwCryptLib::_lua_checkpw)
		);
	}

	private static Varargs _lua_checkpw(Varargs args) {
		String _pw = args.checkjstring(1);
		String _salt = args.checkjstring(2);

		if(_salt.startsWith(Sha1Crypt.SHA1_SIMPLE_PREFIX)) {
			return LuaValue.valueOf(Sha1Crypt.sha1SimpleCheckpw(_pw,_salt));
		} else if(_salt.startsWith(Sha1Crypt.SHA1_PREFIX)) {
			return LuaValue.valueOf(Sha1Crypt.sha1_checkpw(_pw,_salt));
		} else if(_salt.startsWith(Sha2Crypt.SHA256_PREFIX)) {
			return LuaValue.valueOf(Sha2Crypt.sha256Crypt(_pw.getBytes(StandardCharsets.UTF_8),_salt)
					.equalsIgnoreCase(_salt));
		} else if(_salt.startsWith(Sha2Crypt.SHA512_PREFIX)) {
			return LuaValue.valueOf(Sha2Crypt.sha512Crypt(_pw.getBytes(StandardCharsets.UTF_8),_salt)
					.equalsIgnoreCase(_salt));
		} else if(_salt.startsWith(Md5Crypt.APR1_PREFIX)) {
			return LuaValue.valueOf(Md5Crypt.apr1Crypt(_pw.getBytes(StandardCharsets.UTF_8),_salt)
					.equalsIgnoreCase(_salt));
		} else if(_salt.startsWith(Md5Crypt.MD5_PREFIX)) {
			return LuaValue.valueOf(Md5Crypt.md5Crypt(_pw.getBytes(StandardCharsets.UTF_8),_salt)
					.equalsIgnoreCase(_salt));
		} else if(_salt.startsWith(B64Crypt.B64_PREFIX1)) {
			return LuaValue.valueOf(B64Crypt.b641Crypt(_pw.getBytes(StandardCharsets.UTF_8))
					.equalsIgnoreCase(_salt));
		} else if(_salt.startsWith(B64Crypt.B64_PREFIX2)) {
			return LuaValue.valueOf(B64Crypt.b642Crypt(_pw.getBytes(StandardCharsets.UTF_8))
					.equalsIgnoreCase(_salt));
		}

		return LuaValue.FALSE;
	}

	private static Varargs _lua_md5crypt(Varargs args) {
		String _pw = args.checkjstring(1);

		if(args.narg()==1) return LuaValue.valueOf(Md5Crypt.md5Crypt(_pw.getBytes(StandardCharsets.UTF_8)));

		String _salt = args.checkjstring(2);

		return LuaValue.valueOf(Md5Crypt.md5Crypt(_pw.getBytes(StandardCharsets.UTF_8), _salt));
	}

	private static Varargs _lua_b64crypt(Varargs args) {
		String _pw = args.checkjstring(1);

		return LuaValue.valueOf(B64Crypt.b642Crypt(_pw.getBytes(StandardCharsets.UTF_8)));
	}

	private static Varargs _lua_apr1crypt(Varargs args) {
		String _pw = args.checkjstring(1);

		if(args.narg()==1) return LuaValue.valueOf(Md5Crypt.apr1Crypt(_pw.getBytes(StandardCharsets.UTF_8)));

		String _salt = args.checkjstring(2);

		return LuaValue.valueOf(Md5Crypt.apr1Crypt(_pw.getBytes(StandardCharsets.UTF_8), _salt));
	}

	private static Varargs _lua_shacrypt(Varargs args) {
		String _pw = args.checkjstring(1);

		return LuaValue.valueOf(Sha1Crypt.sha1SimpleCrypt(_pw));
	}

	private static Varargs _lua_sha1_b64(Varargs args) {
		String _pw = args.checkjstring(1);

		if(args.narg()==1) return LuaValue.valueOf(Sha1Crypt.sha1_base64(_pw, ""));

		String _salt = args.checkjstring(2);

		return LuaValue.valueOf(Sha1Crypt.sha1_base64(_pw, _salt));
	}

	private static Varargs _lua_sha1_hex(Varargs args) {
		String _pw = args.checkjstring(1);

		if(args.narg()==1) return LuaValue.valueOf(Sha1Crypt.sha1_hex(_pw, ""));

		String _salt = args.checkjstring(2);

		return LuaValue.valueOf(Sha1Crypt.sha1_hex(_pw, _salt));
	}

	private static Varargs _lua_sha1crypt(Varargs args) {
		String _pw = args.checkjstring(1);

		if(args.narg()==1) return LuaValue.valueOf(Sha1Crypt.sha1_crypt(_pw));

		String _salt = args.checkjstring(2);

		return LuaValue.valueOf(Sha1Crypt.sha1_crypt(_pw, _salt, false));
	}

	private static Varargs _lua_sha256crypt(Varargs args) {
		String _pw = args.checkjstring(1);

		if(args.narg()==1) return LuaValue.valueOf(Sha2Crypt.sha256Crypt(_pw.getBytes(StandardCharsets.UTF_8)));

		String _salt = args.checkjstring(2);

		return LuaValue.valueOf(Sha2Crypt.sha256Crypt(_pw.getBytes(StandardCharsets.UTF_8), _salt));
	}

	private static Varargs _lua_sha512crypt(Varargs args) {
		String _pw = args.checkjstring(1);

		if(args.narg()==1) return LuaValue.valueOf(Sha2Crypt.sha512Crypt(_pw.getBytes(StandardCharsets.UTF_8)));

		String _salt = args.checkjstring(2);

		return LuaValue.valueOf(Sha2Crypt.sha512Crypt(_pw.getBytes(StandardCharsets.UTF_8), _salt));
	}

	static class B64Crypt {
		public static final String B64_PREFIX1 = "$base64$";
		public static final String B64_PREFIX2 = "{b64}";

		public static String b641Crypt(final byte[] keyBytes) {
			return B64_PREFIX1+HashLib.toBase64Np(keyBytes);
		}

		public static String b642Crypt(final byte[] keyBytes) {
			return B64_PREFIX2+HashLib.toBase64Np(keyBytes);
		}
	}

	static class Md5Crypt {
		/** The Identifier of the Apache variant. */
		public static final String APR1_PREFIX = "$apr1$";

		/** The number of bytes of the final hash. */
		private static final int BLOCKSIZE = 16;

		/** The Identifier of this crypt() variant. */
		public static final String MD5_PREFIX = "$1$";

		/** The number of rounds of the big loop. */
		private static final int ROUNDS = 1000;

		/**
		 * See {@link #apr1Crypt(byte[], String)} for details.
		 * <p>
		 * A salt is generated for you using {@link SecureRandom}; your own {@link Random} in
		 * {@link #apr1Crypt(byte[], Random)}.
		 * </p>
		 *
		 * @param keyBytes plaintext string to hash.
		 * @return the hash value
		 * @throws IllegalArgumentException when a {@link java.security.NoSuchAlgorithmException} is caught. *
		 * @see #apr1Crypt(byte[], String)
		 */
		public static String apr1Crypt(final byte[] keyBytes) {
			return apr1Crypt(keyBytes, APR1_PREFIX + HashLib.getB64TRandomSalt(8));
		}

		/**
		 * See {@link #apr1Crypt(byte[], String)} for details.
		 * <p>
		 * A salt is generated for you using the user provided {@link Random}.
		 * </p>
		 *
		 * @param keyBytes plaintext string to hash.
		 * @param random the instance of {@link Random} to use for generating the salt. Consider using {@link SecureRandom}
		 *            or {@link ThreadLocalRandom}.
		 * @return the hash value
		 * @throws IllegalArgumentException when a {@link java.security.NoSuchAlgorithmException} is caught. *
		 * @see #apr1Crypt(byte[], String)
		 * @since 1.12
		 */
		public static String apr1Crypt(final byte[] keyBytes, final Random random) {
			return apr1Crypt(keyBytes, APR1_PREFIX + HashLib.getB64TRandomSalt(8, random));
		}

		/**
		 * See {@link #apr1Crypt(String, String)} for details.
		 * <p>
		 * A salt is generated for you using {@link SecureRandom}
		 * </p>
		 *
		 * @param keyBytes
		 *            plaintext string to hash.
		 * @param salt
		 *            An APR1 salt. The salt may be null, in which case a salt is generated for you using
		 *            {@link ThreadLocalRandom}; for more secure salts consider using {@link SecureRandom} to generate your
		 *            own salts.
		 * @return the hash value
		 * @throws IllegalArgumentException
		 *             if the salt does not match the allowed pattern
		 * @throws IllegalArgumentException
		 *             when a {@link java.security.NoSuchAlgorithmException} is caught.
		 */
		public static String apr1Crypt(final byte[] keyBytes, String salt) {
			// to make the md5Crypt regex happy
			if (salt != null && !salt.startsWith(APR1_PREFIX)) {
				salt = APR1_PREFIX + salt;
			}
			return Md5Crypt.md5Crypt(keyBytes, salt, APR1_PREFIX);
		}

		/**
		 * See {@link #apr1Crypt(String, String)} for details.
		 * <p>
		 * A salt is generated for you using {@link ThreadLocalRandom}; for more secure salts consider using
		 * {@link SecureRandom} to generate your own salts and calling {@link #apr1Crypt(byte[], String)}.
		 * </p>
		 *
		 * @param keyBytes
		 *            plaintext string to hash.
		 * @return the hash value
		 * @throws IllegalArgumentException
		 *             when a {@link java.security.NoSuchAlgorithmException} is caught.
		 * @see #apr1Crypt(byte[], String)
		 */
		public static String apr1Crypt(final String keyBytes) {
			return apr1Crypt(keyBytes.getBytes(StandardCharsets.UTF_8));
		}

		/**
		 * Generates an Apache htpasswd compatible "$apr1$" MD5 based hash value.
		 * <p>
		 * The algorithm is identical to the crypt(3) "$1$" one but produces different outputs due to the different salt
		 * prefix.
		 *
		 * @param keyBytes
		 *            plaintext string to hash.
		 * @param salt
		 *            salt string including the prefix and optionally garbage at the end. The salt may be null, in which
		 *            case a salt is generated for you using {@link ThreadLocalRandom}; for more secure salts consider using
		 *            {@link SecureRandom} to generate your own salts.
		 * @return the hash value
		 * @throws IllegalArgumentException
		 *             if the salt does not match the allowed pattern
		 * @throws IllegalArgumentException
		 *             when a {@link java.security.NoSuchAlgorithmException} is caught.
		 */
		public static String apr1Crypt(final String keyBytes, final String salt) {
			return apr1Crypt(keyBytes.getBytes(StandardCharsets.UTF_8), salt);
		}

		/**
		 * Generates a libc6 crypt() compatible "$1$" hash value.
		 * <p>
		 * See {@link #md5Crypt(byte[], String)} for details.
		 *</p>
		 * <p>
		 * A salt is generated for you using {@link ThreadLocalRandom}; for more secure salts consider using
		 * {@link SecureRandom} to generate your own salts and calling {@link #md5Crypt(byte[], String)}.
		 * </p>
		 * @param keyBytes
		 *            plaintext string to hash.
		 * @return the hash value
		 * @throws IllegalArgumentException
		 *             when a {@link java.security.NoSuchAlgorithmException} is caught.
		 * @see #md5Crypt(byte[], String)
		 */
		public static String md5Crypt(final byte[] keyBytes) {
			return md5Crypt(keyBytes, MD5_PREFIX + HashLib.getB64TRandomSalt(8));
		}

		/**
		 * Generates a libc6 crypt() compatible "$1$" hash value.
		 * <p>
		 * See {@link #md5Crypt(byte[], String)} for details.
		 *</p>
		 * <p>
		 * A salt is generated for you using the instance of {@link Random} you supply.
		 * </p>
		 * @param keyBytes
		 *            plaintext string to hash.
		 * @param random
		 *            the instance of {@link Random} to use for generating the salt. Consider using {@link SecureRandom}
		 *            or {@link ThreadLocalRandom}.
		 * @return the hash value
		 * @throws IllegalArgumentException
		 *             when a {@link java.security.NoSuchAlgorithmException} is caught.
		 * @see #md5Crypt(byte[], String)
		 * @since 1.12
		 */
		public static String md5Crypt(final byte[] keyBytes, final Random random) {
			return md5Crypt(keyBytes, MD5_PREFIX + HashLib.getB64TRandomSalt(8, random));
		}

		/**
		 * Generates a libc crypt() compatible "$1$" MD5 based hash value.
		 *
		 * @param keyBytes
		 *            plaintext string to hash.
		 * @param salt
		 *            salt string including the prefix and optionally garbage at the end. The salt may be null, in which
		 *            case a salt is generated for you using {@link ThreadLocalRandom}; for more secure salts consider using
		 *            {@link SecureRandom} to generate your own salts.
		 * @return the hash value
		 * @throws IllegalArgumentException
		 *             if the salt does not match the allowed pattern
		 * @throws IllegalArgumentException
		 *             when a {@link java.security.NoSuchAlgorithmException} is caught.
		 */
		public static String md5Crypt(final byte[] keyBytes, final String salt) {
			return md5Crypt(keyBytes, salt, MD5_PREFIX);
		}

		/**
		 * Generates a libc6 crypt() "$1$" or Apache htpasswd "$apr1$" hash value.
		 *
		 * @param keyBytes
		 *            plaintext string to hash.
		 * @param salt
		 *            real salt value without prefix or "rounds=". The salt may be null, in which case a salt
		 *            is generated for you using {@link ThreadLocalRandom}; for more secure salts consider
		 *            using {@link SecureRandom} to generate your own salts.
		 * @param prefix
		 *            salt prefix
		 * @return the hash value
		 * @throws IllegalArgumentException
		 *             if the salt does not match the allowed pattern
		 * @throws IllegalArgumentException
		 *             when a {@link java.security.NoSuchAlgorithmException} is caught.
		 */
		public static String md5Crypt(final byte[] keyBytes, final String salt, final String prefix) {
			return md5Crypt(keyBytes, salt, prefix, new SecureRandom());
		}

		/**
		 * Generates a libc6 crypt() "$1$" or Apache htpasswd "$apr1$" hash value.
		 *
		 * @param keyBytes
		 *            plaintext string to hash.
		 * @param salt
		 *            real salt value without prefix or "rounds=". The salt may be null, in which case a salt
		 *            is generated for you using {@link ThreadLocalRandom}; for more secure salts consider
		 *            using {@link SecureRandom} to generate your own salts.
		 * @param prefix
		 *            salt prefix
		 * @param random
		 *            the instance of {@link Random} to use for generating the salt. Consider using {@link SecureRandom}
		 *            or {@link ThreadLocalRandom}.
		 * @return the hash value
		 * @throws IllegalArgumentException
		 *             if the salt does not match the allowed pattern
		 * @throws IllegalArgumentException
		 *             when a {@link java.security.NoSuchAlgorithmException} is caught.
		 * @since 1.12
		 */
		@SneakyThrows
		public static String md5Crypt(final byte[] keyBytes, final String salt, final String prefix, final Random random) {
			final int keyLen = keyBytes.length;

			// Extract the real salt from the given string which can be a complete hash string.
			final String saltString;
			if (salt == null) {
				saltString = HashLib.getB64TRandomSalt(8, random);
			} else {
				final Pattern p = Pattern.compile("^" + prefix.replace("$", "\\$") + "([\\.\\/a-zA-Z0-9]{1,8}).*");
				final Matcher m = p.matcher(salt);
				if (!m.find()) {
					throw new IllegalArgumentException("Invalid salt value: " + salt);
				}
				saltString = m.group(1);
			}
			final byte[] saltBytes = saltString.getBytes(StandardCharsets.UTF_8);

			final MessageDigest ctx = MessageDigest.getInstance("MD5");

			/*
			 * The password first, since that is what is most unknown
			 */
			ctx.update(keyBytes);

			/*
			 * Then our magic string
			 */
			ctx.update(prefix.getBytes(StandardCharsets.UTF_8));

			/*
			 * Then the raw salt
			 */
			ctx.update(saltBytes);

			/*
			 * Then just as many characters of the MD5(pw,salt,pw)
			 */
			MessageDigest ctx1 = MessageDigest.getInstance("MD5");
			ctx1.update(keyBytes);
			ctx1.update(saltBytes);
			ctx1.update(keyBytes);
			byte[] finalb = ctx1.digest();
			int ii = keyLen;
			while (ii > 0) {
				ctx.update(finalb, 0, ii > 16 ? 16 : ii);
				ii -= 16;
			}

			/*
			 * Don't leave anything around in vm they could use.
			 */
			Arrays.fill(finalb, (byte) 0);

			/*
			 * Then something really weird...
			 */
			ii = keyLen;
			final int j = 0;
			while (ii > 0) {
				if ((ii & 1) == 1) {
					ctx.update(finalb[j]);
				} else {
					ctx.update(keyBytes[j]);
				}
				ii >>= 1;
			}

			/*
			 * Now make the output string
			 */
			final StringBuilder passwd = new StringBuilder(prefix + saltString + "$");
			finalb = ctx.digest();

			/*
			 * and now, just to make sure things don't run too fast On a 60 Mhz Pentium this takes 34 msec, so you would
			 * need 30 seconds to build a 1000 entry dictionary...
			 */
			for (int i = 0; i < ROUNDS; i++) {
				ctx1 = MessageDigest.getInstance("MD5");
				if ((i & 1) != 0) {
					ctx1.update(keyBytes);
				} else {
					ctx1.update(finalb, 0, BLOCKSIZE);
				}

				if (i % 3 != 0) {
					ctx1.update(saltBytes);
				}

				if (i % 7 != 0) {
					ctx1.update(keyBytes);
				}

				if ((i & 1) != 0) {
					ctx1.update(finalb, 0, BLOCKSIZE);
				} else {
					ctx1.update(keyBytes);
				}
				finalb = ctx1.digest();
			}

			// The following was nearly identical to the Sha2Crypt code.
			// Again, the buflen is not really needed.
			// int buflen = MD5_PREFIX.length() - 1 + salt_string.length() + 1 + BLOCKSIZE + 1;
			HashLib.b64tfrom24bit(finalb[0], finalb[6], finalb[12], 4, passwd);
			HashLib.b64tfrom24bit(finalb[1], finalb[7], finalb[13], 4, passwd);
			HashLib.b64tfrom24bit(finalb[2], finalb[8], finalb[14], 4, passwd);
			HashLib.b64tfrom24bit(finalb[3], finalb[9], finalb[15], 4, passwd);
			HashLib.b64tfrom24bit(finalb[4], finalb[10], finalb[5], 4, passwd);
			HashLib.b64tfrom24bit((byte) 0, (byte) 0, finalb[11], 2, passwd);

			/*
			 * Don't leave anything around in vm they could use.
			 */
			// Is there a better way to do this with the JVM?
			ctx.reset();
			ctx1.reset();
			Arrays.fill(keyBytes, (byte) 0);
			Arrays.fill(saltBytes, (byte) 0);
			Arrays.fill(finalb, (byte) 0);

			return passwd.toString();
		}
	}

	static class Sha2Crypt {
		/** Default number of rounds if not explicitly specified. */
		private static final int ROUNDS_DEFAULT = 5000;

		/** Maximum number of rounds. */
		private static final int ROUNDS_MAX = 999999999;

		/** Minimum number of rounds. */
		private static final int ROUNDS_MIN = 1000;

		/** Prefix for optional rounds specification. */
		private static final String ROUNDS_PREFIX = "rounds=";

		/** The number of bytes the final hash value will have (SHA-256 variant). */
		private static final int SHA256_BLOCKSIZE = 32;

		/** The prefixes that can be used to identify this crypt() variant (SHA-256). */
		public static final String SHA256_PREFIX = "$5$";

		/** The number of bytes the final hash value will have (SHA-512 variant). */
		private static final int SHA512_BLOCKSIZE = 64;

		/** The prefixes that can be used to identify this crypt() variant (SHA-512). */
		public static final String SHA512_PREFIX = "$6$";

		/** The pattern to match valid salt values. */
		private static final Pattern SALT_PATTERN = Pattern
				.compile("^\\$([56])\\$(rounds=(\\d+)\\$)?([\\.\\/a-zA-Z0-9]{1,16}).*");

		public static String sha256Crypt(final byte[] keyBytes) {
			return sha256Crypt(keyBytes, null);
		}

		public static String sha256Crypt(final byte[] keyBytes, String salt) {
			if (salt == null) {
				salt = SHA256_PREFIX + HashLib.getB64TRandomSalt(8);
			}
			return sha2Crypt(keyBytes, salt, SHA256_PREFIX, SHA256_BLOCKSIZE, "SHA-256");
		}

		public static String sha256Crypt(final byte[] keyBytes, String salt, final Random random) {
			if (salt == null) {
				salt = SHA256_PREFIX + HashLib.getB64TRandomSalt(8, random);
			}
			return sha2Crypt(keyBytes, salt, SHA256_PREFIX, SHA256_BLOCKSIZE, "SHA-256");
		}

		@SneakyThrows
		private static String sha2Crypt(final byte[] keyBytes, final String salt, final String saltPrefix,
										final int blocksize, final String algorithm) {

			final int keyLen = keyBytes.length;

			// Extracts effective salt and the number of rounds from the given salt.
			int rounds = ROUNDS_DEFAULT;
			boolean roundsCustom = false;
			if (salt == null) {
				throw new IllegalArgumentException("Salt must not be null");
			}

			final Matcher m = SALT_PATTERN.matcher(salt);
			if (!m.find()) {
				throw new IllegalArgumentException("Invalid salt value: " + salt);
			}
			if (m.group(3) != null) {
				rounds = Integer.parseInt(m.group(3));
				rounds = Math.max(ROUNDS_MIN, Math.min(ROUNDS_MAX, rounds));
				roundsCustom = true;
			}
			final String saltString = m.group(4);
			final byte[] saltBytes = saltString.getBytes(StandardCharsets.UTF_8);
			final int saltLen = saltBytes.length;

			// 1. start digest A
			// Prepare for the real work.
			MessageDigest ctx = MessageDigest.getInstance(algorithm);

			// 2. the password string is added to digest A
			/*
			 * Add the key string.
			 */
			ctx.update(keyBytes);

			// 3. the salt string is added to digest A. This is just the salt string
			// itself without the enclosing '$', without the magic salt_prefix $5$ and
			// $6$ respectively and without the rounds=<N> specification.
			//
			// NB: the MD5 algorithm did add the $1$ salt_prefix. This is not deemed
			// necessary since it is a constant string and does not add security
			// and /possibly/ allows a plain text attack. Since the rounds=<N>
			// specification should never be added this would also create an
			// inconsistency.
			/*
			 * The last part is the salt string. This must be at most 16 characters and it ends at the first `$' character
			 * (for compatibility with existing implementations).
			 */
			ctx.update(saltBytes);

			// 4. start digest B
			/*
			 * Compute alternate sha512 sum with input KEY, SALT, and KEY. The final result will be added to the first
			 * context.
			 */
			MessageDigest altCtx = MessageDigest.getInstance(algorithm);

			// 5. add the password to digest B
			/*
			 * Add key.
			 */
			altCtx.update(keyBytes);

			// 6. add the salt string to digest B
			/*
			 * Add salt.
			 */
			altCtx.update(saltBytes);

			// 7. add the password again to digest B
			/*
			 * Add key again.
			 */
			altCtx.update(keyBytes);

			// 8. finish digest B
			/*
			 * Now get result of this (32 bytes) and add it to the other context.
			 */
			byte[] altResult = altCtx.digest();

			// 9. For each block of 32 or 64 bytes in the password string (excluding
			// the terminating NUL in the C representation), add digest B to digest A
			/*
			 * Add for any character in the key one byte of the alternate sum.
			 */
			/*
			 * (Remark: the C code comment seems wrong for key length > 32!)
			 */
			int cnt = keyBytes.length;
			while (cnt > blocksize) {
				ctx.update(altResult, 0, blocksize);
				cnt -= blocksize;
			}

			// 10. For the remaining N bytes of the password string add the first
			// N bytes of digest B to digest A
			ctx.update(altResult, 0, cnt);

			// 11. For each bit of the binary representation of the length of the
			// password string up to and including the highest 1-digit, starting
			// from to lowest bit position (numeric value 1):
			//
			// a) for a 1-digit add digest B to digest A
			//
			// b) for a 0-digit add the password string
			//
			// NB: this step differs significantly from the MD5 algorithm. It
			// adds more randomness.
			/*
			 * Take the binary representation of the length of the key and for every 1 add the alternate sum, for every 0
			 * the key.
			 */
			cnt = keyBytes.length;
			while (cnt > 0) {
				if ((cnt & 1) != 0) {
					ctx.update(altResult, 0, blocksize);
				} else {
					ctx.update(keyBytes);
				}
				cnt >>= 1;
			}

			// 12. finish digest A
			/*
			 * Create intermediate result.
			 */
			altResult = ctx.digest();

			// 13. start digest DP
			/*
			 * Start computation of P byte sequence.
			 */
			altCtx = MessageDigest.getInstance(algorithm);

			// 14. for every byte in the password (excluding the terminating NUL byte
			// in the C representation of the string)
			//
			// add the password to digest DP
			/*
			 * For every character in the password add the entire password.
			 */
			for (int i = 1; i <= keyLen; i++) {
				altCtx.update(keyBytes);
			}

			// 15. finish digest DP
			/*
			 * Finish the digest.
			 */
			byte[] tempResult = altCtx.digest();

			// 16. produce byte sequence P of the same length as the password where
			//
			// a) for each block of 32 or 64 bytes of length of the password string
			// the entire digest DP is used
			//
			// b) for the remaining N (up to 31 or 63) bytes use the first N
			// bytes of digest DP
			/*
			 * Create byte sequence P.
			 */
			final byte[] pBytes = new byte[keyLen];
			int cp = 0;
			while (cp < keyLen - blocksize) {
				System.arraycopy(tempResult, 0, pBytes, cp, blocksize);
				cp += blocksize;
			}
			System.arraycopy(tempResult, 0, pBytes, cp, keyLen - cp);

			// 17. start digest DS
			/*
			 * Start computation of S byte sequence.
			 */
			altCtx = MessageDigest.getInstance(algorithm);

			// 18. repeast the following 16+A[0] times, where A[0] represents the first
			// byte in digest A interpreted as an 8-bit unsigned value
			//
			// add the salt to digest DS
			/*
			 * For every character in the password add the entire password.
			 */
			for (int i = 1; i <= 16 + (altResult[0] & 0xff); i++) {
				altCtx.update(saltBytes);
			}

			// 19. finish digest DS
			/*
			 * Finish the digest.
			 */
			tempResult = altCtx.digest();

			// 20. produce byte sequence S of the same length as the salt string where
			//
			// a) for each block of 32 or 64 bytes of length of the salt string
			// the entire digest DS is used
			//
			// b) for the remaining N (up to 31 or 63) bytes use the first N
			// bytes of digest DS
			/*
			 * Create byte sequence S.
			 */
			// Remark: The salt is limited to 16 chars, how does this make sense?
			final byte[] sBytes = new byte[saltLen];
			cp = 0;
			while (cp < saltLen - blocksize) {
				System.arraycopy(tempResult, 0, sBytes, cp, blocksize);
				cp += blocksize;
			}
			System.arraycopy(tempResult, 0, sBytes, cp, saltLen - cp);

			// 21. repeat a loop according to the number specified in the rounds=<N>
			// specification in the salt (or the default value if none is
			// present). Each round is numbered, starting with 0 and up to N-1.
			//
			// The loop uses a digest as input. In the first round it is the
			// digest produced in step 12. In the latter steps it is the digest
			// produced in step 21.h. The following text uses the notation
			// "digest A/C" to describe this behavior.
			/*
			 * Repeatedly run the collected hash value through sha512 to burn CPU cycles.
			 */
			for (int i = 0; i <= rounds - 1; i++) {
				// a) start digest C
				/*
				 * New context.
				 */
				ctx = MessageDigest.getInstance(algorithm);

				// b) for odd round numbers add the byte sequense P to digest C
				// c) for even round numbers add digest A/C
				/*
				 * Add key or last result.
				 */
				if ((i & 1) != 0) {
					ctx.update(pBytes, 0, keyLen);
				} else {
					ctx.update(altResult, 0, blocksize);
				}

				// d) for all round numbers not divisible by 3 add the byte sequence S
				/*
				 * Add salt for numbers not divisible by 3.
				 */
				if (i % 3 != 0) {
					ctx.update(sBytes, 0, saltLen);
				}

				// e) for all round numbers not divisible by 7 add the byte sequence P
				/*
				 * Add key for numbers not divisible by 7.
				 */
				if (i % 7 != 0) {
					ctx.update(pBytes, 0, keyLen);
				}

				// f) for odd round numbers add digest A/C
				// g) for even round numbers add the byte sequence P
				/*
				 * Add key or last result.
				 */
				if ((i & 1) != 0) {
					ctx.update(altResult, 0, blocksize);
				} else {
					ctx.update(pBytes, 0, keyLen);
				}

				// h) finish digest C.
				/*
				 * Create intermediate result.
				 */
				altResult = ctx.digest();
			}

			// 22. Produce the output string. This is an ASCII string of the maximum
			// size specified above, consisting of multiple pieces:
			//
			// a) the salt salt_prefix, $5$ or $6$ respectively
			//
			// b) the rounds=<N> specification, if one was present in the input
			// salt string. A trailing '$' is added in this case to separate
			// the rounds specification from the following text.
			//
			// c) the salt string truncated to 16 characters
			//
			// d) a '$' character
			/*
			 * Now we can construct the result string. It consists of three parts.
			 */
			final StringBuilder buffer = new StringBuilder(saltPrefix);
			if (roundsCustom) {
				buffer.append(ROUNDS_PREFIX);
				buffer.append(rounds);
				buffer.append("$");
			}
			buffer.append(saltString);
			buffer.append("$");

			// e) the base-64 encoded final C digest. The encoding used is as
			// follows:
			// [...]
			//
			// Each group of three bytes from the digest produces four
			// characters as output:
			//
			// 1. character: the six low bits of the first byte
			// 2. character: the two high bits of the first byte and the
			// four low bytes from the second byte
			// 3. character: the four high bytes from the second byte and
			// the two low bits from the third byte
			// 4. character: the six high bits from the third byte
			//
			// The groups of three bytes are as follows (in this sequence).
			// These are the indices into the byte array containing the
			// digest, starting with index 0. For the last group there are
			// not enough bytes left in the digest and the value zero is used
			// in its place. This group also produces only three or two
			// characters as output for SHA-512 and SHA-512 respectively.

			// This was just a safeguard in the C implementation:
			// int buflen = salt_prefix.length() - 1 + ROUNDS_PREFIX.length() + 9 + 1 + salt_string.length() + 1 + 86 + 1;

			if (blocksize == 32) {
				HashLib.b64tfrom24bit(altResult[0], altResult[10], altResult[20], 4, buffer);
				HashLib.b64tfrom24bit(altResult[21], altResult[1], altResult[11], 4, buffer);
				HashLib.b64tfrom24bit(altResult[12], altResult[22], altResult[2], 4, buffer);
				HashLib.b64tfrom24bit(altResult[3], altResult[13], altResult[23], 4, buffer);
				HashLib.b64tfrom24bit(altResult[24], altResult[4], altResult[14], 4, buffer);
				HashLib.b64tfrom24bit(altResult[15], altResult[25], altResult[5], 4, buffer);
				HashLib.b64tfrom24bit(altResult[6], altResult[16], altResult[26], 4, buffer);
				HashLib.b64tfrom24bit(altResult[27], altResult[7], altResult[17], 4, buffer);
				HashLib.b64tfrom24bit(altResult[18], altResult[28], altResult[8], 4, buffer);
				HashLib.b64tfrom24bit(altResult[9], altResult[19], altResult[29], 4, buffer);
				HashLib.b64tfrom24bit((byte) 0, altResult[31], altResult[30], 3, buffer);
			} else {
				HashLib.b64tfrom24bit(altResult[0], altResult[21], altResult[42], 4, buffer);
				HashLib.b64tfrom24bit(altResult[22], altResult[43], altResult[1], 4, buffer);
				HashLib.b64tfrom24bit(altResult[44], altResult[2], altResult[23], 4, buffer);
				HashLib.b64tfrom24bit(altResult[3], altResult[24], altResult[45], 4, buffer);
				HashLib.b64tfrom24bit(altResult[25], altResult[46], altResult[4], 4, buffer);
				HashLib.b64tfrom24bit(altResult[47], altResult[5], altResult[26], 4, buffer);
				HashLib.b64tfrom24bit(altResult[6], altResult[27], altResult[48], 4, buffer);
				HashLib.b64tfrom24bit(altResult[28], altResult[49], altResult[7], 4, buffer);
				HashLib.b64tfrom24bit(altResult[50], altResult[8], altResult[29], 4, buffer);
				HashLib.b64tfrom24bit(altResult[9], altResult[30], altResult[51], 4, buffer);
				HashLib.b64tfrom24bit(altResult[31], altResult[52], altResult[10], 4, buffer);
				HashLib.b64tfrom24bit(altResult[53], altResult[11], altResult[32], 4, buffer);
				HashLib.b64tfrom24bit(altResult[12], altResult[33], altResult[54], 4, buffer);
				HashLib.b64tfrom24bit(altResult[34], altResult[55], altResult[13], 4, buffer);
				HashLib.b64tfrom24bit(altResult[56], altResult[14], altResult[35], 4, buffer);
				HashLib.b64tfrom24bit(altResult[15], altResult[36], altResult[57], 4, buffer);
				HashLib.b64tfrom24bit(altResult[37], altResult[58], altResult[16], 4, buffer);
				HashLib.b64tfrom24bit(altResult[59], altResult[17], altResult[38], 4, buffer);
				HashLib.b64tfrom24bit(altResult[18], altResult[39], altResult[60], 4, buffer);
				HashLib.b64tfrom24bit(altResult[40], altResult[61], altResult[19], 4, buffer);
				HashLib.b64tfrom24bit(altResult[62], altResult[20], altResult[41], 4, buffer);
				HashLib.b64tfrom24bit((byte) 0, (byte) 0, altResult[63], 2, buffer);
			}

			/*
			 * Clear the buffer for the intermediate result so that people attaching to processes or reading core dumps
			 * cannot get any information.
			 */
			// Is there a better way to do this with the JVM?
			Arrays.fill(tempResult, (byte) 0);
			Arrays.fill(pBytes, (byte) 0);
			Arrays.fill(sBytes, (byte) 0);
			ctx.reset();
			altCtx.reset();
			Arrays.fill(keyBytes, (byte) 0);
			Arrays.fill(saltBytes, (byte) 0);

			return buffer.toString();
		}

		/**
		 * Generates a libc crypt() compatible "$6$" hash value with random salt.
		 * <p>
		 * A salt is generated for you using {@link ThreadLocalRandom}; for more secure salts consider using
		 * {@link SecureRandom} to generate your own salts and calling {@link #sha512Crypt(byte[], String)}.
		 * </p>
		 *
		 * @param keyBytes
		 *            plaintext to hash
		 * @return complete hash value
		 * @throws IllegalArgumentException
		 *             when a {@link java.security.NoSuchAlgorithmException} is caught.
		 */
		public static String sha512Crypt(final byte[] keyBytes) {
			return sha512Crypt(keyBytes, null);
		}

		/**
		 * Generates a libc6 crypt() compatible "$6$" hash value.
		 * @param keyBytes
		 *            plaintext to hash
		 * @param salt
		 *            real salt value without prefix or "rounds=". The salt may be null, in which case a salt is generated
		 *            for you using {@link SecureRandom}; if you want to use a {@link Random} object other than
		 *            {@link SecureRandom} then we suggest you provide it using
		 *            {@link #sha512Crypt(byte[], String, Random)}.
		 * @return complete hash value including salt
		 * @throws IllegalArgumentException
		 *             if the salt does not match the allowed pattern
		 * @throws IllegalArgumentException
		 *             when a {@link java.security.NoSuchAlgorithmException} is caught.
		 */
		public static String sha512Crypt(final byte[] keyBytes, String salt) {
			if (salt == null) {
				salt = SHA512_PREFIX + HashLib.getB64TRandomSalt(8);
			}
			return sha2Crypt(keyBytes, salt, SHA512_PREFIX, SHA512_BLOCKSIZE, "SHA-512");
		}


		/**
		 * Generates a libc6 crypt() compatible "$6$" hash value.
		 * @param keyBytes
		 *            plaintext to hash
		 * @param salt
		 *            real salt value without prefix or "rounds=". The salt may be null, in which case a salt
		 *            is generated for you using {@link ThreadLocalRandom}; for more secure salts consider using
		 *            {@link SecureRandom} to generate your own salts.
		 * @param random
		 *            the instance of {@link Random} to use for generating the salt. Consider using {@link SecureRandom}
		 *            or {@link ThreadLocalRandom}.
		 * @return complete hash value including salt
		 * @throws IllegalArgumentException
		 *             if the salt does not match the allowed pattern
		 * @throws IllegalArgumentException
		 *             when a {@link java.security.NoSuchAlgorithmException} is caught.
		 * @since 1.12
		 */
		public static String sha512Crypt(final byte[] keyBytes, String salt, final Random random) {
			if (salt == null) {
				salt = SHA512_PREFIX + HashLib.getB64TRandomSalt(8, random);
			}
			return sha2Crypt(keyBytes, salt, SHA512_PREFIX, SHA512_BLOCKSIZE, "SHA-512");
		}
	}

	static class Sha1Crypt {
		public static final String SHA1_SIMPLE_PREFIX = "{sha1}";

		public static final boolean sha1SimpleCheckpw(String pass, String tocheck) {
			return sha1SimpleCrypt(pass).equalsIgnoreCase(tocheck);
		}

		public static final String sha1SimpleCrypt(String pass) {
			return SHA1_SIMPLE_PREFIX+sha1_hex(pass,"");
		}

		public static final String SHA1_PREFIX = "$4$";

		public static final String sha1_crypt(String pass) {
			return sha1_crypt(pass, null, false);
		}

		public static final boolean sha1_checkpw(String pass, String salt) {
			String crypted = sha1_crypt(pass, salt, true);
			int of1 = 3;
			int of2 = salt.lastIndexOf('$');
			return crypted.substring(of1,of2).equals(salt.substring(of1, of2));
		}

		public static final String sha1_crypt(String pass, String salt, boolean for_check) {
			if(salt==null) {
				salt="";
			}

			if (salt.startsWith(SHA1_PREFIX)) {
				salt = salt.substring(3);
			}

			if (salt.contains("$")) {
				salt = salt.substring(0, salt.indexOf('$'));
			}

			if (salt.length() < 8 && !for_check) {
				byte[] buf = HashLib.getB64TRandomSalt(6).getBytes();
				salt = HashLib.toBase64(buf);
			}

			return SHA1_PREFIX+salt+"$"+sha1_base64(pass, salt)+"$";
		}

		public static String sha1_base64(String pass, String salt) {
			String ret = HashLib.toBase64Np(HashLib.hash("SHA1",(pass+salt).getBytes(StandardCharsets.UTF_8)));
			return ret;
		}

		public static String sha1_hex(String pass, String salt) {
			return HashLib.hashHex("SHA1",(pass+salt).getBytes(StandardCharsets.UTF_8)).toLowerCase();
		}
	}

}

/*
%!begin MARKDOWN

## pwcrypt Library

```lua
local pwc = require('pwcrypt');
```
*   `pwc.checkpw ( password , encoded ) -> bool`
*   `pwc.shacrypt ( password [,salt] ) -> string ({sha1})`
*   `pwc.sha1crypt ( password [,salt] ) -> string ($4$)`
*   `pwc.sha1_hex ( password ) -> string`
*   `pwc.sha1_b64 ( password ) -> string`
*   `pwc.sha256crypt ( password [,salt] ) -> string ($5$)`
*   `pwc.sha512crypt ( password [,salt] ) -> string ($6$)`
*   `pwc.apr1crypt ( password [,salt] ) -> string ($apr1$)`
*   `pwc.md5crypt ( password [,salt] ) -> string ($1$)`
*   `pwc.b64crypt ( password [,salt] ) -> string ({b64})`

\pagebreak

%!end MARKDOWN

 */
