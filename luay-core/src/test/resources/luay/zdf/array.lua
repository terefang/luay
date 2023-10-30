local array = require('array')

-- array() // array only table
local _arr = array.array()
_arr[1] = "_1"
_arr[3] = "_3"
_arr[2] = "_2"
assert(pcall(function()
    _arr['x'] = "_x"
end)==false)

print("luay-array",_arr)

assert(pcall(function()
    print("luay-array",_arr['x'])
end)==false)


for k, v in pairs(_arr) do
    print(k,v)
end
-- end

-- list() // list only table
local _lst = array.list()
_lst[1] = "_1"
_lst[3] = "_3"
_lst[2] = "_2"

print("luay-list",_lst)

for k, v in pairs(_lst) do
    print(k,v)
end

-- zdf.chunk

local _tbl = {1,2,3,4,5,6,7,8,9,0}

local _rc = array.chunk(_tbl,1)
print(stringify(_rc))
assert(#_rc == 10)

_rc = array.chunk(_tbl,2)
print(stringify(_rc))
assert(#_rc == 5)

_rc = array.chunk(_tbl,3)
print(stringify(_rc))
assert(#_rc == 4)

_rc = array.chunk(_tbl,4)
print(stringify(_rc))
assert(#_rc == 3)

_rc = array.chunk(_tbl,5)
print(stringify(_rc))
assert(#_rc == 2)

_rc = array.chunk(_tbl,6)
print(stringify(_rc))
assert(#_rc == 2)

_rc = array.chunk(_tbl,9)
print(stringify(_rc))
assert(#_rc == 2)

_rc = array.chunk(_tbl,10)
print(stringify(_rc))
assert(#_rc == 1)

_rc = array.chunk(_tbl,20)
print(stringify(_rc))
assert(#_rc == 1)

