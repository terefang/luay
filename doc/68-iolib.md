## IO Functions

```lua
local io = require('io');
```

Refer: http://www.lua.org/manual/5.2/manual.html#6.8

*   `io.close ([file])`
*   `io.flush ()`
*   `io.input ([file])`
*   `io.lines ([filename ···])`
*   `io.open (filename [, mode]) -> file`
*   `io.output ([file])`
*   `io.popen (prog [, mode])`
*   `io.read (···)`
*   `io.tmpfile () -> file`
*   `io.type (obj)`
*   `io.write (···)`

*   `file:close ()`
*   `file:flush ()`
*   `file:lines (···)`
*   `file:read (···)`
*   `file:seek ([whence [, offset]])`
*   `file:setvbuf (mode [, size])`
*   `file:write (···)`

### Luay Extensions

*   `io.loadasstring(file[, mode]) -> string, err`
*   `io.loadastable(file[, mode]) -> list, err`


\pagebreak
