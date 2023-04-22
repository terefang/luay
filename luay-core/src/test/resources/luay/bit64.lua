-- LUAY EXTENSIONS

local _bit64 = require('bit64')

_x = 0x7FFFFFFFFFFFFFFF
_y = 0x8FFFFFFFFFFFFFFF
_z = 0xFFFFFFFFFFFFFFFF

print(_x, _y, _z)

assert(_bit64.lshift(1,1) == 2)
assert(_bit64.lshift(1,2) == 4)
assert(_bit64.lshift(1,3) == 8)
assert(_bit64.lshift(1,4) == 16)
assert(_bit64.lshift(1,5) == 32)
assert(_bit64.lshift(1,6) == 64)
assert(_bit64.lshift(1,7) == 128)
assert(_bit64.lshift(1,8) == 256)
assert(_bit64.lshift(1,9) == 512)
assert(_bit64.lshift(1,10) == 1024)
assert(_bit64.lshift(1,16) == 65536)
assert(_bit64.lshift(1,17) == 131072)
assert(_bit64.lshift(1,24) == 16777216)
assert(_bit64.lshift(1,25) == 33554432)
assert(_bit64.lshift(1,32) == 4294967296)
assert(_bit64.lshift(1,33) == 8589934592)
assert(_bit64.lshift(1,62) == 4611686018427387904)
assert(_bit64.lshift(1,63) == -9223372036854775808)
assert(_bit64.lshift(1,64) == 0)

assert(_bit64.rshift(0x100,4) == 0x10)
assert(_bit64.rshift(0x100,8) == 1)
assert(_bit64.rshift(0x100,9) == 0)

assert(_bit64.rshift(-1,1) == 0x7FFFFFFFFFFFFFFF)
assert(_bit64.arshift(-1,1) == -1)

-- 64 bit rotation
assert(_bit64.rrotate(1,1) == -9223372036854775808)
assert(_bit64.lrotate(-9223372036854775808,1) == 1)

assert(_bit64.rrotate(0x11,1) == -9223372036854775800)
assert(_bit64.rrotate(0x11,4) == 1152921504606846977)

assert(_bit64.lrotate(0x1100000000000000,7) == -9223372036854775800)
assert(_bit64.lrotate(0x1100000000000000,4) == 1152921504606846977)










