## Table Manipulation

```
local table = require('table');
```

Refer: http://www.lua.org/manual/5.2/manual.html#6.5

*   `table.concat (list [, sep [, i [, j]]])`
*   `table.insert (list, [pos,] value)`
*   `table.pack (···)`
*   `table.remove (list [, pos]) -> value`
*   `table.sort (list [, comp])`
*   `table.unpack (list [, i [, j]])`

Refer: http://www.lua.org/manual/5.3/manual.html#6.6

*TODO*

table.move (a1, f, e, t [,a2])

Moves elements from table a1 to table a2, performing the equivalent to the following multiple assignment: a2[t],··· = a1[f],···,a1[e]. The default for a2 is a1. The destination range can overlap with the source range. The number of elements to be moved must fit in a Lua integer.

\pagebreak
