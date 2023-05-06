package luay.lib.ext.zdf;

import luay.vm.LuaFunction;
import luay.vm.LuaTable;
import luay.vm.LuaValue;
import luay.vm.Varargs;
import luay.lib.ext.AbstractLibrary;
import luay.lib.LuayLibraryFactory;

import java.util.List;

public class MapLib extends AbstractLibrary implements LuayLibraryFactory {
    @Override
    public String getLibraryName() {
        return "map";
    }

    @Override
    public List<LuaFunction> getLibraryFunctions() {
        return toList(
                _varArgFunctionWrapper.from("collect", MapLib::_collect),
                _varArgFunctionWrapper.from("collectk", MapLib::_collectk),
                _varArgFunctionWrapper.from("collectv", MapLib::_collectv),
                _varArgFunctionWrapper.from("count", MapLib::_count)
        );
    }

    // "collectk" (table[, ..., tableN]) -> list(k1, ..., kN)
    static Varargs _collectk(Varargs args) {
        LuaTable _ret = new LuaTable();
        for (int _i = 1; _i <= args.narg(); _i++) {
            LuaTable _tbl = args.checktable(_i);
            for (LuaValue _k : _tbl.keys()) {
                _ret.insert(0, _k);
            }
        }
        return _ret;
    }

    // "collectv" (table[, ..., tableN]) -> list(v1, ..., vN)
    static Varargs _collectv(Varargs args) {
        LuaTable _ret = new LuaTable();
        for (int _i = 1; _i <= args.narg(); _i++) {
            LuaTable _tbl = args.checktable(_i);
            for (LuaValue _k : _tbl.keys()) {
                _ret.insert(0, _tbl.get(_k));
            }
        }
        return _ret;
    }

    // "count" (table, fv) -> table
    static Varargs _count(Varargs args) {
        LuaTable _tbl = args.checktable(1);
        LuaFunction _func = args.checkfunction(2);
        LuaTable _ret = new LuaTable();
        for (LuaValue _k : _tbl.keys()) {
            LuaValue _fk = _func.call(_tbl.get(_k));
            LuaValue _cv = _ret.get(_fk);
            if (_cv.isnil()) _cv = LuaValue.valueOf(0);
            _cv = _cv.add(1);
            _ret.set(_fk, _cv);
        }
        return _ret;
    }

    // "collect" (table[, ..., tableN]) -> table
    static Varargs _collect(Varargs args) {
        LuaTable _ret = new LuaTable();
        for (int _i = 1; _i <= args.narg(); _i++) {
            LuaTable _tbl = args.checktable(_i);
            for (LuaValue _k : _tbl.keys()) {
                _ret.set(_k, _tbl.get(_k));
            }
        }
        return _ret;
    }
}
