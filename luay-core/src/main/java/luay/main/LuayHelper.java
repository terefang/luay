package luay.main;

import luay.vm.LuaList;
import luay.vm.LuaTable;
import luay.vm.LuaValue;

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
        return null;
    }
}
