package luay.lib.rocks;

import luay.vm.*;
import luay.vm.luay.AbstractLibrary;
import luay.vm.luay.LuayLibraryFactory;

import java.util.List;
import java.util.StringTokenizer;

public class StringyLib extends AbstractLibrary  implements LuayLibraryFactory
{
    @Override
    public String getLibraryName() {
        return "stringy";
    }

    @Override
    public List<LuaFunction> getLibraryFunctions() {
        return toList(
                _varArgFunctionWrapper.from("join", StringyLib::join),
                _varArgFunctionWrapper.from("split", StringyLib::split),
                _varArgFunctionWrapper.from("strip", StringyLib::strip),
                _varArgFunctionWrapper.from("startswith", StringyLib::startswith),
                _varArgFunctionWrapper.from("endswith", StringyLib::endswith),
                _varArgFunctionWrapper.from("count", StringyLib::count),
                _varArgFunctionWrapper.from("find", StringyLib::find)
        );
    }

    // stringy.join(sep, hay, ...) -> string
    public static LuaValue join(Varargs args)
    {
        String _sep = args.checkjstring(1);
        boolean _first = true;
        StringBuilder _sb = new StringBuilder();
        for(int _i=2; _i<=args.narg();_i++)
        {
            if(!_first)
            {
                _sb.append(_sep);
            }
            _first = false;
            _sb.append(args.checkjstring(_i));
        }
        return LuaString.valueOf(_sb.toString());
    }

    // stringy.split(hay, sep) -> list
    public static LuaValue split(Varargs args)
    {
        String _hay = args.checkjstring(1);
        String _sep = args.checkjstring(2);
        LuaList _list = new LuaList();
        /*
        int _end = _hay.length();
        for(int _ofs = 0; _ofs<_end && !(_ofs<0);)
        {
            int _next = _hay.indexOf(_sep, _ofs);
            if(_next==-1)
            {
                _list.insert(LuaString.valueOf(_hay.substring(_ofs)));
                break;
            }
            else
            {
                _list.insert(LuaString.valueOf(_hay.substring(_ofs, _next)));
                _ofs=_next+_sep.length();
            }
        }
        */
        StringTokenizer _st = new StringTokenizer(_hay, _sep, true);
        boolean _lastWasSep = false;
        while(_st.hasMoreTokens())
        {
            String _t = _st.nextToken();
            if(_sep.indexOf(_t)<0)
            {
                _list.insert(LuaString.valueOf(_t));
                _lastWasSep = false;
            }
            else
            {
                if(_lastWasSep)
                {
                    _list.insert(LuaString.valueOf(""));
                }
                _lastWasSep = true;
            }
        }
        return _list;
    }

    // stringy.strip(hay) -> trimmed
    public static LuaValue strip(Varargs args)
    {
        return LuaString.valueOf(args.checkjstring(1).trim());
    }

    // stringy.startswith(hay, needle) -> bool
    public static LuaValue startswith(Varargs args)
    {
        String _hay = args.checkjstring(1);
        String _needle = args.checkjstring(2);
        return LuaBoolean.valueOf(_hay.startsWith(_needle));
    }


    // stringy.endswith(hay, needle) -> bool
    public static LuaValue endswith(Varargs args)
    {
        String _hay = args.checkjstring(1);
        String _needle = args.checkjstring(2);
        return LuaBoolean.valueOf(_hay.endsWith(_needle));
    }

    // stringy.count(hay, needle [, start]) -> offset of nil
    public static LuaValue count(Varargs args)
    {
        String _hay = args.checkjstring(1);
        String _needle = args.checkjstring(2);

        if(_needle.length()==0) return LuaInteger.ZERO;

        int _start = args.optint(3, 1);
        int _end = args.optint(4, _hay.length()+1);
        if(_start<0)
        {
            _start = _hay.length()+_start;
        }
        else
        {
            _start--;
        }

        if(_end<0)
        {
            _end = _hay.length()+_end;
        }
        else
        {
            _end--;
        }

        int _count = 0;

        if(_needle.length()==1)
        {
            for(int _i = _start; _i<_end; _i++)
            {
                if(_hay.charAt(_i) == _needle.charAt(0)) _count++;
            }
        }
        else
        {
            while((_start = _hay.indexOf(_needle, _start))>=0)
            {
                if(_start>=_end) break;
                _count++;
                _start+=_needle.length();
            }
        }

        return LuaValue.valueOf(_count);
    }

    // stringy.find(hay, needle [, start]) -> offset of nil
    public static LuaValue find(Varargs args)
    {
        String _hay = args.checkjstring(1);
        String _needle = args.checkjstring(2);
        int _start = args.optint(3, 1);
        if(_start<0)
        {
            _start = _hay.length()+_start;
        }
        else
        {
            _start--;
        }

        int _ofs = 0;
        if(_start<0)
        {
            _ofs = _hay.lastIndexOf(_needle, _hay.length()+_start);
        }
        else
        {
            _ofs = _hay.indexOf(_needle, _start);
        }
        if(_ofs>=0) return LuaInteger.valueOf(_ofs+1);
        return LuaValue.NIL;
    }


}
