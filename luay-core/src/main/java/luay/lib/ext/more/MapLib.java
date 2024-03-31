package luay.lib.ext.more;

import luay.main.LuayHelper;
import luay.vm.LuaFunction;
import luay.vm.LuaTable;
import luay.vm.LuaValue;
import luay.vm.Varargs;
import luay.lib.ext.AbstractLibrary;
import luay.lib.LuayLibraryFactory;

import java.util.List;
/*
%!begin MARKDOWN

## map (inspired by luazdf)

```lua
local map = require('map');
```

*   `map.map (k1,v1[, ..., kN, vN]) -> map-only-table` \
    Function to collect all kv-pairs into a map-only table.

* `map.keys (table[, ..., tableN]) -> list(k1, ..., kX)` \
    alias of collectk
* `map.collectk (table[, ..., tableN]) -> list(k1, ..., kX)` \
    Function to collect all keys for the list of tables.

*   `map.values (table[, ..., tableN]) -> list(v1, ..., vX)` \
    alias of collectv
*   `map.collectv (table[, ..., tableN]) -> list(v1, ..., vX)` \
    Function to collect all values for the list of tables.

*   `map.collect (table[, ..., tableN]) -> table` \
    Function to collect all k,v for the list of tables.

*   `map.count (table, fv) -> table` \
    Sorts a list into groups and returns a count for the number of objects in
    each group. Similar to groupBy, but instead of returning a list of values,
    returns a count for the number of values in that group.

\pagebreak

%!end MARKDOWN
 */
public class MapLib extends AbstractLibrary implements LuayLibraryFactory {
    @Override
    public String getLibraryName() {
        return "map";
    }

    @Override
    public List<LuaFunction> getLibraryFunctions() {
        return toList(
                _varArgFunctionWrapper.from("map", MapLib::_map),
                _varArgFunctionWrapper.from("collect", MapLib::_collect),
                _varArgFunctionWrapper.from("collectk", MapLib::_collectk),
                _varArgFunctionWrapper.from("keys", MapLib::_collectk),
                _varArgFunctionWrapper.from("collectv", MapLib::_collectv),
                _varArgFunctionWrapper.from("values", MapLib::_collectv),
                _varArgFunctionWrapper.from("count", MapLib::_count)
        );
    }

    static Varargs _map(Varargs args)
    {
        return LuayHelper.varargsToTable(args);
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
