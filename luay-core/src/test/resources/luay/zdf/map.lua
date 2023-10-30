-- zdf.collect*

local map = require('map')

local _tbl = map.map("a","x", "b","y", "c","z" )

print('--- map:')
print(stringify(_tbl))

print('--- collectk:')
local _rc = map.collectk(_tbl)
print(stringify(_rc))
assert(#_rc == 3)

print('--- collectv:')
local _rc = map.collectv(_tbl)
print(stringify(_rc))
assert(#_rc == 3)

print('--- collect:')
local _rc = map.collect(_tbl,_tbl)
_rc = map.collectk(_rc)
print(stringify(_rc))
assert(#_rc == 3)

print('--- collectk:')
_rc = map.collectk(_tbl,_tbl)
print(stringify(_rc))
assert(#_rc == 6)

print('--- collectv:')
_rc = map.collectv(_tbl,_tbl)
print(stringify(_rc))
assert(#_rc == 6)


