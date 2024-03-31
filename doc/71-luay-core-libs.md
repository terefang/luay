
## 64 Bitwise Operations

```
local bit64 = require('bit64');
```

*   `bit64.arshift (x, disp)`
*   `bit64.band (···)`
*   `bit64.bnot (x)`
*   `bit64.bor (···)`
*   `bit64.btest (···)`
*   `bit64.bxor (···)`
*   `bit64.extract (n, field [, width])`
*   `bit64.replace (n, v, field [, width])`
*   `bit64.lrotate (x, disp)`
*   `bit64.lshift (x, disp)`
*   `bit64.rrotate (x, disp)`
*   `bit64.rshift (x, disp)`

\pagebreak


## boolean Operations

```
local boolean = require('boolean');
```

* `boolean.and ( bool [, ... , bool]) -> bool`
* `boolean.nand ( bool [, ... , bool]) -> bool`
* `boolean.or ( bool [, ... , bool]) -> bool`
* `boolean.nor ( bool [, ... , bool]) -> bool`
* `boolean.xor ( bool [, ... , bool]) -> bool`
* `boolean.eq ( bool [, ... , bool]) -> bool`
* `boolean.not ( bool ) -> bool`
* `boolean.true() -> bool`
* `boolean.false() -> bool`
* `boolean.is_true( bool ) -> bool`
* `boolean.is_false( bool ) -> bool`

\pagebreak


## UTF-8 Support

```lua
local utf8 = require('utf8');
```

Refer: http://www.lua.org/manual/5.3/manual.html#6.5

*   `utf8.char (···) -> string`
*   `utf8.charpattern` (NOT IMPLEMENTED)
*   `utf8.codes (s)` (NOT IMPLEMENTED)
*   `utf8.codepoint (s [, i [, j]]) -> list`
*   `utf8.len (s [, i [, j]])` (NOT IMPLEMENTED)
*   `utf8.offset (s, n [, i])` (NOT IMPLEMENTED)

### Luay Extensions

*   `utf8.length (s) -> int`
*   `utf8.substr (s, n [, i]) -> string`
*   `utf8.indexof (s, n [, i]) -> int/nil`
*   `utf8.indexofany (s, n1 [, ..., nN]) -> int/nil`
*   `utf8.lastindexof (s, n [, i]) -> int/nil`
*   `utf8.lastindexofany (s, n1 [, ..., nN]) -> int/nil`
*   `utf8.lower (s) -> string`
*   `utf8.upper (s) -> string`
*   `utf8.find(hay, needle [, start]) -> offset or nil`
*   `utf8.count(hay, needle [, start]) -> offset of nil`
*   `utf8.contains(hay, needle) -> boolean`
*   `utf8.matches(hay, rx) -> boolean`
*   `utf8.matchesany(hay, rx, ...) -> boolean`
*   `utf8.startswith(hay, needle) -> boolean`
*   `utf8.startswithany ( string, string [, ...]) -> boolean`
*   `utf8.startswithnocase ( string, string [, ...] ) -> boolean`
*   `utf8.endswith(hay, needle) -> boolean`
*   `utf8.endswithany ( string, string [, ...]) -> boolean`
*   `utf8.endswithnocase ( string, string [, ...] ) -> boolean`
*   `utf8.rxsplit(hay, rx) -> list`
*   `utf8.split(hay, sep) -> list`
*   `utf8.join(sep, hay, ...) -> string`
*   `utf8.format(fmt, ...) -> string`
*   `utf8.repeat(part, int) -> string`
*   `utf8.trim(hay) -> string`
*   `utf8.strip(hay) -> string`
*   `utf8.striptrailing(hay) -> string`
*   `utf8.stripleading(hay) -> string`
*   `utf8.replace(string, charFrom, charTo) -> string`
*   `utf8.replace(string, table) -> string`
*   `utf8.rxreplace(string, rx, to) -> string`
*   `utf8.rxreplaceall(string, rx, to) -> string`

\pagebreak


