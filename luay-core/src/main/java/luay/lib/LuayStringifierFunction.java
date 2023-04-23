package luay.lib;

import luay.vm.*;

public class LuayStringifierFunction extends LuaFunction {

    public static final LuayStringifierFunction INSTANCE = new LuayStringifierFunction();

    @Override
    public LuaValue call(LuaValue arg) {
        return _stringify(arg);
    }

    @Override
    public Varargs invoke(Varargs args) {
        return _stringify_vararg(args);
    }

    public static LuaValue _stringify_string(LuaString _that)
    {
        StringBuilder _sb = new StringBuilder();
        _sb.append("\"");
        _sb.append(_that.tojstring());
        _sb.append("\"");
        return LuaString.valueOf(_sb.toString());
    }

    public static LuaValue _stringify(LuaValue _that)
    {
        if(_that instanceof LuaList)
        {
            return _stringify_table((LuaList)_that, true);
        }
        else
        if(_that.istable())
        {
            return _stringify_table((LuaTable)_that, false);
        }
        else
        if(_that instanceof LuaInteger)
        {
            return _stringify_int((LuaInteger)_that);
        }
        else
        if(_that instanceof LuaNumber)
        {
            return _stringify_num((LuaNumber)_that);
        }
        else
        if(_that.isstring())
        {
            return _stringify_string((LuaString) _that);
        }
        return LuaString.valueOf(_that.tojstring());
    }

    public static LuaValue _stringify_num(LuaNumber _that)
    {
        return LuaString.valueOf(_that.tojstring());
    }

    public static LuaValue _stringify_int(LuaInteger _that)
    {
        return LuaString.valueOf(_that.tojstring());
    }

    public static Varargs _stringify_vararg(Varargs _that)
    {
        // stringify was called with arguments
        if(_that.narg()==2 && "json".equalsIgnoreCase(_that.checkjstring(2)))
        {
            return _stringify_json(_that.arg1());
        }
        else
        if(_that.narg()==2 && "ldata".equalsIgnoreCase(_that.checkjstring(2)))
        {
            return _stringify_ldata(_that.arg1());
        }
        else // std
        {
            return _stringify(_that.arg1());
        }
    }

    public static LuaValue _stringify_table(LuaTable _that, boolean _forcelist)
    {
        if(_forcelist || _that.isarray())
        {
            StringBuilder _sb = new StringBuilder();
            boolean _first = true;
            _sb.append("[ ");
            int _len = _that.keyCount();
            for(int _i=0; _i<_len; _i++)
            {
                if(!_first) _sb.append(", ");
                _sb.append(_stringify(_that.get(_i+1)).tojstring());
                _first=false;
            }
            _sb.append(" ]");
            return LuaString.valueOf(_sb.toString());
        }
        else
        {
            StringBuilder _sb = new StringBuilder();
            boolean _first = true;
            _sb.append("{ ");
            for(LuaValue _i : _that.keys())
            {
                if(!_first) _sb.append(", ");
                _sb.append(_stringify(_i).tojstring());
                _sb.append(" -> ");
                _sb.append(_stringify(_that.get(_i)).tojstring());
                _first=false;
            }
            _sb.append(" }");
            return LuaString.valueOf(_sb.toString());
        }
    }

    // JSON
    static String escape_json(String s){
        if(s==null)
            return "null";
        StringBuffer sb = new StringBuffer();
        escape_json(s, sb);
        return sb.toString();
    }

    static void escape_json(String s, StringBuffer sb) {
        final int len = s.length();
        for(int i=0;i<len;i++){
            char ch=s.charAt(i);
            switch(ch){
                case '"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                default:
                    //Reference: http://www.unicode.org/versions/Unicode5.1.0/
                    if((ch>=0x00 && ch<=0x1F) || (ch>=0x7F))
                    {
                        sb.append(String.format("\\u%04X",(int)ch));
                    }
                    else{
                        sb.append(ch);
                    }
            }
        }
    }

    public static LuaValue _stringify_json(LuaValue _that)
    {
        if(_that.isnil())
        {
            return _stringify_json_nil((LuaNil) _that);
        }
        else
        if(_that.isboolean())
        {
            return _stringify_json_bool((LuaBoolean) _that);
        }
        else
        if(_that instanceof LuaList)
        {
            return _stringify_json_table((LuaList)_that, true);
        }
        else
        if(_that.istable())
        {
            return _stringify_json_table((LuaTable)_that, false);
        }
        else
        if(_that instanceof LuaInteger)
        {
            return _stringify((LuaInteger)_that);
        }
        else
        if(_that instanceof LuaNumber)
        {
            return _stringify((LuaNumber)_that);
        }
        else
        if(_that.isstring())
        {
            return _stringify_json_string((LuaString) _that);
        }
        return LuaString.valueOf("'"+_that.tojstring()+"'");
    }

    private static LuaValue _stringify_json_bool(LuaBoolean _that)
    {
        return LuaString.valueOf(_that.toboolean() ? "true" : "false");
    }

    private static LuaValue _stringify_json_string(LuaString _that)
    {
        return LuaString.valueOf("\""+escape_json(_that.tojstring())+"\"");
    }

    private static LuaValue _stringify_json_nil(LuaNil _that)
    {
        return LuaString.valueOf("null");
    }

    private static LuaValue _stringify_json_table(LuaTable _that, boolean _forcelist)
    {
        if(_forcelist || _that.isarray())
        {
            StringBuilder _sb = new StringBuilder();
            boolean _first = true;
            _sb.append("[ ");
            int _len = _that.getArrayLength();
            for(int _i=0; _i<_len; _i++)
            {
                if(!_first) _sb.append(", ");
                _sb.append(_stringify_json(_that.get(_i+1)).tojstring());
                _first=false;
            }
            _sb.append(" ]");
            return LuaString.valueOf(_sb.toString());
        }
        else
        {
            StringBuilder _sb = new StringBuilder();
            boolean _first = true;
            _sb.append("{ ");
            int _len = _that.getArrayLength();
            for(LuaValue _i : _that.keys())
            {
                if(!_first) _sb.append(", ");
                _sb.append(_stringify_json(_i).tojstring());
                _sb.append(": ");
                _sb.append(_stringify_json(_that.get(_i)).tojstring());
                _first=false;
            }
            _sb.append(" }");
            return LuaString.valueOf(_sb.toString());
        }
    }


    // LDATA

    public static LuaValue _stringify_ldata(LuaValue _that)
    {
        if(_that.isnil())
        {
            return _stringify_ldata_nil((LuaNil) _that);
        }
        else
        if(_that.isboolean())
        {
            return _stringify_ldata_bool((LuaBoolean) _that);
        }
        else
        if(_that instanceof LuaList)
        {
            return _stringify_ldata_table((LuaList)_that, true);
        }
        else
        if(_that.istable())
        {
            return _stringify_ldata_table((LuaTable)_that, false);
        }
        else
        if(_that instanceof LuaInteger)
        {
            return _stringify((LuaInteger)_that);
        }
        else
        if(_that instanceof LuaNumber)
        {
            return _stringify((LuaNumber)_that);
        }
        else
        if(_that.isstring())
        {
            return _stringify_ldata_string((LuaString) _that);
        }
        return LuaString.valueOf("'"+_that.tojstring()+"'");
    }

    public static LuaValue _stringify_ldata_string(LuaString _that)
    {
        StringBuilder _sb = new StringBuilder();
        _sb.append("\"");
        _sb.append(_that.tojstring());
        _sb.append("\"");
        return LuaString.valueOf(_sb.toString());
    }

    public static LuaValue _stringify_ldata_nil(LuaNil _that)
    {
        return LuaString.valueOf("NULL");
    }

    public static LuaValue _stringify_ldata_bool(LuaBoolean _that)
    {
        return LuaString.valueOf(_that.toboolean() ? "TRUE" : "FALSE");
    }

    public static LuaValue _stringify_ldata_table(LuaTable _that, boolean _forcelist)
    {
        if(_forcelist || _that.isarray())
        {
            StringBuilder _sb = new StringBuilder();
            boolean _first = true;
            _sb.append("[ ");
            int _len = _that.getArrayLength();
            for(int _i=0; _i<_len; _i++)
            {
                if(!_first) _sb.append(" ");
                _sb.append(_stringify_ldata(_that.get(_i+1)).tojstring());
                _first=false;
            }
            _sb.append(" ]");
            return LuaString.valueOf(_sb.toString());
        }
        else
        {
            StringBuilder _sb = new StringBuilder();
            boolean _first = true;
            _sb.append("{ ");
            int _len = _that.getArrayLength();
            for(LuaValue _i : _that.keys())
            {
                if(!_first) _sb.append(" ");
                _sb.append(_stringify_ldata(_i).tojstring());
                _sb.append(" = ");
                _sb.append(_stringify_ldata(_that.get(_i)).tojstring());
                _first=false;
            }
            _sb.append(" }");
            return LuaString.valueOf(_sb.toString());
        }
    }

}


