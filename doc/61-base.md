## Basic Functions

Refer: http://www.lua.org/manual/5.2/manual.html#6.1

*   `assert (v [, message])`
*   `collectgarbage ([opt [, arg]])`
*   `dofile ([filename])`
*   `error (message [, level])`
*   `_G`
*   `ipairs (t) -> iterator, t, 0`
*   `load (ld [, source [, mode [, env]]])`
*   `loadfile ([filename [, mode [, env]]])`
*   `next (table [, index])`
*   `pairs (t) -> iterator, t, nil`
*   `pcall (f [, arg1, ···])`
*   `print (···)`
*   `rawequal (v1, v2)`
*   `rawget (table, index)`
*   `rawlen (v)`
*   `rawset (table, index, value)`
*   `select (index, ···)`
*   `setmetatable (table, metatable)`
*   `tonumber (e [, base])`
*   `tostring (v)`
*   `type (v)`
*   `_VERSION`
*   `xpcall (f, msgh [, arg1, ···])`

### Luay Extensions

*   `stringify (string[, type])`
*   `printf (···)`
*   `apairs(v1[,...,vN]) -> iterator, list, 0` — inspired from idle-lang
*   `mklist(value[, value ...]) -> list-only-table`
*   `mkmap(key1, value1 [, keyN, valueN ...]) -> map-only-table`

*   `__FILE__` — filepath of the initial executed script, if available.
*   `__PATH__` — path containing the initial executed script, if available.

\pagebreak