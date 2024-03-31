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

### Luay Extensions

*   `string.concat(v1[,...,vN]) -> string`
*   `string.concat(table) -> string`
  
*   `string.split(sep, string) -> list`
*   `string.split(sep, string, limit) -> list` \
    Java String.split
   
*   `string.explode(string) -> list`
*   `string.explode(sep, string) -> list`
*   `string.explode(sep, string, limit) -> list` \
    Java StringTokenizer, default all whitespace
  
*   `string.implode(sep,v1[,...,vN]) -> string`
*   `string.implode(sep,table) -> string`
  
*   `string.mformat(fmt, v1[,...,vN]) -> string` \
    Java MessageFormatter

*   `string.sformat(fmt, v1[,...,vN]) -> string` \
    Java String.format

* `string.to_int (string [, default]) -> long`
* `string.to_long (string [, default]) -> long`
* `string.to_bool (string [, default]) -> boolean`
* `string.to_float (string [, default]) -> double`
* `string.to_double (string [, default]) -> double`

\pagebreak

TODO

decformat - #.##
octformat - 1.45 MiB
isoformat - 2.34 M
