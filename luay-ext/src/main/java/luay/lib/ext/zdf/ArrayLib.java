package luay.lib.ext.zdf;

import luay.vm.*;
import luay.lib.ext.AbstractLibrary;
import luay.lib.LuayLibraryFactory;

import java.util.List;

public class ArrayLib extends AbstractLibrary implements LuayLibraryFactory {
    @Override
    public String getLibraryName() {
        return "array";
    }

    @Override
    public List<LuaFunction> getLibraryFunctions() {
        return toList(
                _varArgFunctionWrapper.from("array", ArrayLib::_array),
                _varArgFunctionWrapper.from("list", ArrayLib::_list),
                _varArgFunctionWrapper.from("push", ArrayLib::_push),
                _varArgFunctionWrapper.from("pop", ArrayLib::_pop),
                _varArgFunctionWrapper.from("shift", ArrayLib::_shift),
                _varArgFunctionWrapper.from("unshift", ArrayLib::_unshift),
                _varArgFunctionWrapper.from("append", ArrayLib::_push),
                _varArgFunctionWrapper.from("appendall", ArrayLib::_appendall),
                _varArgFunctionWrapper.from("chunk", ArrayLib::_chunk),
                _varArgFunctionWrapper.from("split", ArrayLib::_split),
                _varArgFunctionWrapper.from("collect", ArrayLib::_collect),
                _varArgFunctionWrapper.from("count", ArrayLib::_count),
                _varArgFunctionWrapper.from("difference", ArrayLib::_difference),
                // zdf:flatten ?
                _varArgFunctionWrapper.from("indexof", ArrayLib::_indexof),
                _varArgFunctionWrapper.from("findif", ArrayLib::_find),
                _varArgFunctionWrapper.from("find", ArrayLib::_find)
        );
    }

    // "appendall" (table, tbl1[, ..., tblN])
    static Varargs _appendall(Varargs args) {
        LuaTable table = args.checktable(1);

        for (int _i = 2; _i <= args.narg(); _i++) {
            LuaTable _tbl = args.checktable(_i);

            for (LuaValue _k : _tbl.keys()) {
                table.set(_k, _tbl.get(_k));
            }
        }
        return table;
    }


    // "chunk" (list, size) -> list(lst1, ..., lstN)
    static Varargs _chunk(Varargs args) {
        LuaTable table = args.checktable(1);
        int _chunksize = args.checkint(2);

        LuaTable _chunks = new LuaTable();
        LuaTable _chunk = null;
        Varargs _kv = table.inext(LuaValue.ZERO);
        int _i = 0;
        while (!_kv.isnil(1)) {
            if (_i % _chunksize == 0) {
                _chunk = new LuaTable();
                _chunks.insert(0, _chunk);
            }
            _chunk.insert(0, _kv.arg(2));
            _kv = table.inext(_kv.arg(1));
            _i++;
        }
        return _chunks;
    }

    // "split" (list, size) -> lst1, lst2
    static Varargs _split(Varargs args) {
        LuaTable _tbl = args.checktable(1);
        int _size = args.checkint(2);

        LuaList _left = new LuaList(_size);
        LuaList _right = new LuaList(_size);
        int _len = _tbl.length();
        for (int _k=0;_k<_len; _k++)
        {
            if(_k<_size)
            {
                _left.insert(_tbl.get(_k+1));
            }
            else
            {
                _right.insert(_tbl.get(_k+1));
            }
        }
        return LuaValue.varargsOf(new LuaValue[] { _left, _right });
    }

    // "count" (array, fv) -> table
    static Varargs _count(Varargs args) {
        LuaTable _tbl = args.checktable(1);
        LuaFunction _func = args.checkfunction(2);
        LuaTable _ret = new LuaTable();
        int _len = _tbl.length();
        for (int _k=0;_k<_len; _k++) {
            LuaValue _fk = _func.call(_tbl.get(_k));
            LuaValue _cv = _ret.get(_fk);
            if (_cv.isnil()) _cv = LuaValue.valueOf(0);
            _cv = _cv.add(1);
            _ret.set(_fk, _cv);
        }
        return _ret;
    }

    // "collect" (list[, ..., listN]) -> list
    static Varargs _collect(Varargs args) {
        LuaTable _ret = new LuaTable();
        for (int _i = 1; _i <= args.narg(); _i++) {
            LuaTable _tbl = args.checktable(_i);
            int _len = _tbl.length();
            for (int _k=0;_k<_len; _k++) {
                _ret.insert(0, _tbl.get(_k));
            }
        }
        return _ret;
    }

    // "append" (table, value[, value ...]) -- alias for "push"
    // "push" (table, value[, value ...])
    static Varargs _push(Varargs args) {
        LuaTable table = args.checktable(1);

        for (int _i = 2; _i <= args.narg(); _i++) {
            table.insert(table.length() + 1, args.arg(_i));
        }
        return table;
    }


    // "pop" (table) -> value
    static Varargs _pop(Varargs args) {
        LuaTable table = args.checktable(1);

        return table.remove(table.length());

    }

    // "unshift" (table, value[, value ...]) -> table
    static Varargs _unshift(Varargs args) {
        LuaTable table = args.checktable(1);

        for (int _i = args.narg(); _i >= 2; _i--) {
            table.insert(1, args.arg(_i));
        }
        return table;
    }


    // "shift" (table) -> value
    static Varargs _shift(Varargs args) {
        LuaTable table = args.checktable(1);

        return table.remove(1);
    }


    // "difference" (list1, list2) -> list
    static Varargs _difference(Varargs args) {
        LuaTable _tbl1 = args.checktable(1);
        LuaTable _tbl2 = args.checktable(2);
        int _size1 = _tbl1.length();
        int _size2 = _tbl2.length();

        LuaList _ret = new LuaList();
        for (int _j=0;_j<_size1; _j++)
        {
            boolean _found = false;
            LuaValue _v1 = _tbl1.get(_j+1);

            for (int _i=0;_i<_size2; _i++)
            {
                if(_v1.eq_b(_tbl2.get(_i+1)))
                {
                    _found = true;
                    break;
                }
            }

            if(!_found)
            {
                _ret.insert(_v1);
            }
        }
        return _ret;
    }

    // find( list, value [, init] ) -> v, i
    // findif( list, fv [, init] ) -> v, i
    static Varargs _find(Varargs args)
    {
        LuaTable _tbl = args.checktable(1);
        int _size = _tbl.length();
        int _index = args.optint(3, 1)-1;

        if(args.isfunction(2))
        {
            LuaFunction _func = args.checkfunction(2);
            for (int _k=_index;_k<_size; _k++)
            {
                LuaValue _v = _tbl.get(_k+1);
                if(_func.call(_v).checkboolean())
                {
                    return LuaValue.varargsOf(new LuaValue[]{ _v, LuaInteger.valueOf(_k+1)});
                }
            }
        }
        else
        {
            LuaValue _val = args.checkvalue(2);
            for (int _k=_index;_k<_size; _k++)
            {
                LuaValue _v = _tbl.get(_k+1);
                if(_val.eq_b(_v))
                {
                    return LuaValue.varargsOf(new LuaValue[]{ _v, LuaInteger.valueOf(_k+1)});
                }
            }
        }
        return LuaValue.varargsOf(LuaValue.NIL, LuaValue.NIL);
    }

    // indexof( list, value [, init] ) -> i
    // indexof( list, fv [, init] ) -> i
    static Varargs _indexof(Varargs args)
    {
        LuaTable _tbl = args.checktable(1);
        int _size = _tbl.length();
        int _index = args.optint(3, 1)-1;
        if(args.isfunction(2))
        {
            LuaFunction _func = args.checkfunction(2);
            for (int _k=_index;_k<_size; _k++)
            {
                LuaValue _v = _tbl.get(_k+1);
                if(_func.call(_v).checkboolean())
                {
                    return LuaInteger.valueOf(_k+1);
                }
            }
        }
        else
        {
            LuaValue _val = args.checkvalue(2);
            for (int _k=_index;_k<_size; _k++)
            {
                LuaValue _v = _tbl.get(_k+1);
                if(_val.eq_b(_v))
                {
                    return LuaInteger.valueOf(_k+1);
                }
            }
        }
        return LuaValue.NIL;
    }

    // array()
    static Varargs _array(Varargs args)
    {
        LuaTable _t = new LuaTable();
        _t.setForceArray(true);
        for (int _i = 0; _i < args.narg(); _i++)
        {
            _t.insert(0, args.arg(_i));
        }
        return _t;
    }

    // list()
    static Varargs _list(Varargs args)
    {
        LuaList _t = new LuaList();
        for (int _i = 0; _i < args.narg(); _i++)
        {
            _t.insert(args.arg(_i));
        }
        return _t;
    }

}
