## String Manipulation

```
local string = require('string');
```

Refer: http://www.lua.org/manual/5.2/manual.html#6.4

*   `string.byte (s [, i [, j]]) -> bytei[, ..., bytej ]`
*   `string.char (···) -> charstring`
*   `string.dump (function) -> string`
*   `string.find (s, pattern [, init [, plain]]) -> index1, index2 [, catures ...]`
*   `string.format (formatstring, ···) -> string`
*   `string.gmatch (s, pattern) -> iterator`

    ```lua
    s = "hello world from Lua"
    for w in string.gmatch(s, "%a+") do
        print(w)
    end
    ```

    ```lua
    t = {}
    s = "from=world, to=Lua"
    for k, v in string.gmatch(s, "(%w+)=(%w+)") do
        t[k] = v
    end
    ```

*   `string.gsub (s, pattern, repl [, n]) -> string`
*   `string.len (s) -> int`
*   `string.lower (s)`
*   `string.match (s, pattern [, init])`
*   `string.rep (s, n [, sep])`
*   `string.reverse (s)`
*   `string.sub (s, i [, j])`
*   `string.upper (s)`

Refer: http://www.lua.org/manual/5.3/manual.html#6.4

*TODO*

*   `string.pack (fmt, v1, v2, ···)`
*   `string.packsize (fmt)`
*   `string.unpack (fmt, s [, pos])`

### Patterns

Refer: http://www.lua.org/manual/5.2/manual.html#6.4.1

### Format Strings for Pack and Unpack

*TODO*

Refer: http://www.lua.org/manual/5.3/manual.html#6.4.2

\pagebreak
