-- luay extension

local _utf8 = require('utf8')

local _codes = { 254,255,256,267,258,259,260 }

local _string = _utf8.char(254,255,256,267,258,259,260)
print(_string)

local _list = _utf8.codepoint(_string)

for i, v in ipairs(_codes) do
    print(i,v,_list[i])
    assert(v == _list[i])
end

for i, v in ipairs(_codes) do
    print(i,v,_list[i])
    assert(v == _list[i])
end

for i, v in ipairs(_codes) do
    local _u = _utf8.char(v)
    local _i = _utf8.indexof(_string, _u)
    print(i,v,_u,_i)
    assert(i == _i)
end

for i, v in ipairs(_codes) do
    local _u = _utf8.char(v)
    local _i = _utf8.lastindexof(_string, _u)
    print(i,v,_u,_i)
    assert(i == _i)
end

print(_utf8.lower(_string))
print(_utf8.upper(_string))

