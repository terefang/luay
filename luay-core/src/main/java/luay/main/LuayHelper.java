package luay.main;

import luay.vm.*;
import luay.vm.lib.java.CoerceJavaToLua;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LuayHelper
{
    public static Map<String,Object> tableToJavaMap(LuaTable _table)
    {
        Map<String,Object> _ret = new HashMap<>();
        for(LuaValue _key : _table.keys())
        {
            _ret.put(_key.checkjstring(), valueToJava(_table.get(_key)));
        }
        return _ret;
    }

    public static List<Object> tableToJavaList(LuaTable _table)
    {
        int _len = _table.getArrayLength();
        List _ret = new ArrayList(_len);
        for(int _i = 0; _i<_len; _i++)
        {
            _ret.add(valueToJava(_table.get(_i+1)));
        }
        return _ret;
    }

    public static List<String> tableToJavaStringList(LuaTable _table)
    {
        int _len = _table.getArrayLength();
        List _ret = new ArrayList(_len);
        for(int _i = 0; _i<_len; _i++)
        {
            _ret.add(valueToJava(_table.get(_i+1)).toString());
        }
        return _ret;
    }

    public static Object valueToJava(LuaValue _value)
    {
        if(_value==null || _value.isnil())
        {
            return null;
        }
        else
        if(_value instanceof LuaList)
        {
            return tableToJavaList((LuaTable) _value);
        }
        else
        if(_value.istable())
        {
            if(((LuaTable) _value).isForceArray())
            {
                return tableToJavaList((LuaTable) _value);
            }
            return tableToJavaMap((LuaTable) _value);
        }
        else if(_value instanceof LuaBoolean)
        {
            return _value.checkboolean();
        }
        else if(_value instanceof LuaDouble)
        {
            return _value.checkdouble();
        }
        else if(_value instanceof LuaInteger)
        {
            return _value.checklong();
        }
        else
        if(_value.isboolean())
        {
            return _value.checkboolean();
        }
        else
        if(_value.isinttype())
        {
            return _value.checkint();
        }
        else
        if(_value.islong())
        {
            return _value.checklong();
        }
        else
        if(_value.isnumber())
        {
            return _value.checkdouble();
        }
        else
        if(_value.isstring())
        {
            return _value.checkjstring();
        }
        return _value.toString();
    }

    public static LuaTable varargsToTable(Varargs _args)
    {
        if(_args.narg()%2!=0) throw new LuaError("not kv pairs");
        LuaTable _t = new LuaTable();
        _t.setForceMap(true);
        for(int _i = 1; _i<_args.narg(); _i+=2)
        {
            _t.set(_args.arg(_i), _args.arg(_i+1));
        }
        return _t;
    }

    public static LuaList varargsToList(Varargs _args)
    {
        LuaList _t = new LuaList();
        for(int _i = 1; _i<=_args.narg(); _i++)
        {
            _t.insert(_args.arg(_i));
        }
        return _t;
    }

    public static LuaTable toTable(String _k, Object _v, Object ... _args)
    {
        if(_args.length%2!=0) throw new LuaError("not kv pairs");
        LuaTable _t = new LuaTable();
        _t.setForceMap(true);
        _t.set(_k,CoerceJavaToLua.coerce(_v));
        for(int _i = 0; _i<_args.length; _i+=2)
        {
            _t.set(_args[_i].toString(), toValue(_args[_i]));
        }
        return _t;
    }

    public static LuaTable toTable(Map<String,?> _args)
    {
        LuaTable _t = new LuaTable();
        _t.setForceMap(true);
        for(Map.Entry<String,?> _entry : _args.entrySet())
        {
            _t.set(_entry.getKey(), toValue(_entry.getValue()));
        }
        return _t;
    }

    public static LuaList toList(Object ... _args)
    {
        LuaList _t = new LuaList();
        for(Object _a : _args)
        {
            _t.insert(toValue(_a));
        }
        return _t;
    }

    public static LuaList toList(int ... _args)
    {
        LuaList _t = new LuaList();
        for(Object _a : _args)
        {
            _t.insert(toValue(_a));
        }
        return _t;
    }

    public static LuaList toList(long ... _args)
    {
        LuaList _t = new LuaList();
        for(Object _a : _args)
        {
            _t.insert(toValue(_a));
        }
        return _t;
    }

    public static LuaList toList(float ... _args)
    {
        LuaList _t = new LuaList();
        for(Object _a : _args)
        {
            _t.insert(toValue(_a));
        }
        return _t;
    }

    public static LuaList toList(double ... _args)
    {
        LuaList _t = new LuaList();
        for(Object _a : _args)
        {
            _t.insert(toValue(_a));
        }
        return _t;
    }

    public static LuaList toList(String ... _args)
    {
        LuaList _t = new LuaList();
        for(Object _a : _args)
        {
            _t.insert(toValue(_a));
        }
        return _t;
    }

    public static LuaList toList(boolean ... _args)
    {
        LuaList _t = new LuaList();
        for(Object _a : _args)
        {
            _t.insert(toValue(_a));
        }
        return _t;
    }

    public static LuaList toList(List _args)
    {
        LuaList _t = new LuaList();
        for(Object _a : _args)
        {
            _t.insert(toValue(_a));
        }
        return _t;
    }

    public static LuaValue toValue(Object _that)
    {
        if(_that instanceof LuaValue)
        {
            return (LuaValue) _that;
        }
        else
        if(_that.getClass().isArray() && _that.getClass().getComponentType() == int.class)
        {
            return toList((int[])_that);
        }
        else
        if(_that.getClass().isArray() && _that.getClass().getComponentType() == long.class)
        {
            return toList((long[])_that);
        }
        else
        if(_that.getClass().isArray() && _that.getClass().getComponentType() == float.class)
        {
            return toList((float[])_that);
        }
        else
        if(_that.getClass().isArray() && _that.getClass().getComponentType() == double.class)
        {
            return toList((double[])_that);
        }
        else
        if(_that.getClass().isArray() && _that.getClass().getComponentType() == boolean.class)
        {
            return toList((boolean[])_that);
        }
        else
        if(_that.getClass().isArray() && _that.getClass().getComponentType() == String.class)
        {
            return toList((String[])_that);
        }
        else
        if(_that.getClass().isArray())
        {
            return toList((Object[])_that);
        }
        else
        if(_that instanceof List)
        {
            return toList((List)_that);
        }
        else
        if(_that instanceof Map)
        {
            return toTable((Map)_that);
        }
        else
        if(_that instanceof Boolean)
        {
            return LuaValue.valueOf(((Boolean)_that).booleanValue());
        }
        else
        if(_that instanceof Short)
        {
            return LuaValue.valueOf(((Short)_that).intValue());
        }
        else
        if(_that instanceof Integer)
        {
            return LuaValue.valueOf(((Integer)_that).intValue());
        }
        else
        if(_that instanceof Long)
        {
            return LuaValue.valueOf(((Long)_that).longValue());
        }
        else
        if(_that instanceof Float)
        {
            return LuaValue.valueOf(((Float)_that).doubleValue());
        }
        else
        if(_that instanceof Double)
        {
            return LuaValue.valueOf(((Double)_that).doubleValue());
        }
        else
        if(_that instanceof String)
        {
            return LuaString.valueOf(_that.toString());
        }
        return CoerceJavaToLua.coerce(_that);
    }
}
