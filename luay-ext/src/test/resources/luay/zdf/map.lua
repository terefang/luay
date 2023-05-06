-- zdf.collect*

local map = require('map')

local _tbl = { a="x", b="y", c="z" }

local _rc = map.collectk(_tbl)
print(stringify(_rc))
assert(#_rc == 3)

local _rc = map.collectv(_tbl)
print(stringify(_rc))
assert(#_rc == 3)

local _rc = map.collect(_tbl,_tbl)
_rc = map.collectk(_rc)
print(stringify(_rc))
assert(#_rc == 3)

_rc = map.collectk(_tbl,_tbl)
print(stringify(_rc))
assert(#_rc == 6)

_rc = map.collectv(_tbl,_tbl)
print(stringify(_rc))
assert(#_rc == 6)

