package luay.lib.ext.libUseful;

import luay.vm.LuaFunction;
import luay.vm.LuaString;
import luay.vm.LuaValue;
import luay.vm.Varargs;
import luay.lib.ext.AbstractLibrary;
import luay.lib.LuayLibraryFactory;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

public class EntropyLib extends AbstractLibrary  implements LuayLibraryFactory
{
    @Override
    public String getLibraryName() {
        return "entropy";
    }

    @Override
    public List<LuaFunction> getLibraryFunctions() {
        return toList(
                _varArgFunctionWrapper.from("get", EntropyLib::get),
                _varArgFunctionWrapper.from("hex", EntropyLib::hex),
                _varArgFunctionWrapper.from("alphanum", EntropyLib::alphanum)
        );
    }

    private static SecureRandom INSTANCE;

    static {
        try {
            INSTANCE = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static final String ENC_OCT   = "oct"     ;   // Octal Encoding
    public static final String ENC_DEC   = "dec"     ;   // Decimal Encoding
    public static final String ENC_HEX   = "hex"     ;   // HexaDecimal Encoding
    public static final String ENC_B64   = "b64"     ;   // base64 Encoding
    public static final String ENC_R64   = "r64"     ;   // rfc4648 compliant alternative base64 Encoding
    public static final String ENC_I64   = "i64"     ;   // alternative base64 Encoding
    public static final String ENC_P64   = "p64"     ;   // another alternative base64 Encoding
    public static final String ENC_Q64   = "q64"     ;   // another alternative base64 Encoding
    public static final String ENC_XX    = "xx"      ;   // xxencode encoding
    public static final String ENC_UU    = "uu"      ;   // uuencode encoding
    public static final String ENC_ALPHANUM = "alphanum"   ;   // alpha+num encoding
    public static final String ENC_CRYPT = "crypt"   ;   // unix 'crypt' encoding
    public static final String ENC_A85   = "ascii85" ;   // ascii85 encoding
    public static final String ENC_Z85   = "z85"     ;   // z85 encoding
    private static final String OCT_CHARS     = "01234567";
    private static final String DEC_CHARS     = "0123456789";
    private static final String HEX_CHARS     = "0123456789ABCDEF";
    private static final String ALPHA_CHARS   = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String BASE64_CHARS  = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    private static final String RBASE64_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_"; //RFC4648 compliant
    private static final String IBASE64_CHARS = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789+/";
    private static final String PBASE64_CHARS = "0123456789-ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz";
    private static final String QBASE64_CHARS = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890/*";
    private static final String CRYPT_CHARS   = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String UUENC_CHARS   = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_";
    private static final String XXENC_CHARS   = "+-0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String ASCII85_CHARS = "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstu";
    private static final String Z85_CHARS     = "01234567899abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ.-:+=^!/*?&<>()[]{}@%$#.";

    public static LuaValue get(Varargs args)
    {
        int _len = args.checkint(1);
        String _enc = args.optjstring(2,ENC_P64);
        return LuaString.valueOf(get_(_len, _enc));
    }

    public static LuaValue hex(Varargs args)
    {
        int _len = args.checkint(1);
        return LuaString.valueOf(get_(_len, ENC_HEX));
    }

    public static LuaValue alphanum(Varargs args)
    {
        int _len = args.checkint(1);
        return LuaString.valueOf(get_(_len, ENC_ALPHANUM));
    }


    public static String get_(int _len, String _enc)
    {
        byte[] _buf = new byte[_len];
        INSTANCE.nextBytes(_buf);
        BigInteger _bi = new BigInteger(1, _buf);
        if(ENC_Z85.equalsIgnoreCase(_enc))
        {
            return encode(_bi, Z85_CHARS);
        }
        else
        if(ENC_A85.equalsIgnoreCase(_enc))
        {
            return encode(_bi, ASCII85_CHARS);
        }
        else
        if(ENC_CRYPT.equalsIgnoreCase(_enc))
        {
            return encode(_bi, CRYPT_CHARS);
        }
        else
        if(ENC_UU.equalsIgnoreCase(_enc))
        {
            return encode(_bi, UUENC_CHARS);
        }
        else
        if(ENC_XX.equalsIgnoreCase(_enc))
        {
            return encode(_bi, XXENC_CHARS);
        }
        else
        if(ENC_I64.equalsIgnoreCase(_enc))
        {
            return encode(_bi, IBASE64_CHARS);
        }
        else
        if(ENC_P64.equalsIgnoreCase(_enc))
        {
            return encode(_bi, PBASE64_CHARS);
        }
        else
        if(ENC_R64.equalsIgnoreCase(_enc))
        {
            return encode(_bi, RBASE64_CHARS);
        }
        else
        if(ENC_B64.equalsIgnoreCase(_enc))
        {
            return encode(_bi, BASE64_CHARS);
        }
        else
        if(ENC_Q64.equalsIgnoreCase(_enc))
        {
            return encode(_bi, QBASE64_CHARS);
        }
        else
        if(ENC_HEX.equalsIgnoreCase(_enc))
        {
            return encode(_bi, HEX_CHARS);
        }
        else
        if(ENC_DEC.equalsIgnoreCase(_enc))
        {
            return encode(_bi, DEC_CHARS);
        }
        else
        if(ENC_OCT.equalsIgnoreCase(_enc))
        {
            return encode(_bi, OCT_CHARS);
        }
        else
        if(ENC_ALPHANUM.equalsIgnoreCase(_enc))
        {
            return encode(_bi, ALPHA_CHARS);
        }
        return null;
    }

    static String encode(BigInteger _buf, String _set)
    {
        BigInteger _div = BigInteger.valueOf(_set.length());
        StringBuilder _sb = new StringBuilder();
        while(_buf.bitLength()>0)
        {
            _sb.append((char)_set.charAt(_buf.remainder(_div).intValue()));
            _buf = _buf.divide(_div);
        }
        return _sb.reverse().toString();
    }

    public static void main(String[] args) {
        System.err.println(get_(20, null));

        System.err.println(get_(20, ENC_OCT));
        System.err.println(get_(20, ENC_DEC));
        System.err.println(get_(20, ENC_HEX));
        System.err.println(get_(20, ENC_ALPHANUM));
        System.err.println(get_(20, ENC_B64));
        System.err.println(get_(20, ENC_R64));
        System.err.println(get_(20, ENC_I64));
        System.err.println(get_(20, ENC_P64));
        System.err.println(get_(20, ENC_Q64));
        System.err.println(get_(20, ENC_XX));
        System.err.println(get_(20, ENC_UU));
        System.err.println(get_(20, ENC_CRYPT));
        System.err.println(get_(20, ENC_A85));
        System.err.println(get_(20, ENC_Z85));

    }
}
