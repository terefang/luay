## hash Library

```lua
local hash = require('hash');
```
*   `hash.md5 (bytes) -> bytes`
*   `hash.sha1 (bytes) -> bytes`
*   `hash.sha256 (bytes) -> bytes`
*   `hash.sha512 (bytes) -> bytes`
*   `hash.to_hex (bytes) -> string`
*   `hash.from_hex (string) -> bytes`
*   `hash.to_b32 (bytes) -> string`
*   `hash.from_b32 (string) -> bytes`
*   `hash.to_b64 (bytes) -> string`
*   `hash.from_b64 (string) -> bytes`
*   `hash.to_b26 (bytes) -> string`
*   `hash.to_b36 (bytes) -> string`
*   `hash.to_b62 (bytes) -> string`
*   `hash.to_uuid (bytes) -> string`
*   `hash.to_xid (bytes) -> string`
*   `hash.to_xxid (bytes, bytes) -> string`
*   `hash.random_string (int [, string[, string]]) -> string`

### hash object

```lua
local _md = hash.new_hash('MD5');
_md:update('this');
_md:update('hello','world');
print('md5 hex =',hash.to_hex(_md:finish()))
```

### mac object

```lua
local _mh = hash.new_mac('HMACMD5', 's3cr3t');
_mh:update('this');
_mh:update('hello','world');
print('hmacmd5 hex =',hash.to_hex(_mh:finish()))
```

\pagebreak
