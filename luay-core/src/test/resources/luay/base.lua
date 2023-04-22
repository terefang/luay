-- local _IO = require('io');

if(_var==nil) then _var='nil' end
if(_var2==nil) then _var2='nil' end

print('Hello World' .. _var .. ' x ' .. _var2);
print(_IO)

-- table
local _tbl = {}
_tbl[1] = "_1"
_tbl[3] = "_3"
_tbl[2] = "_2"
_tbl['x'] = "_x"

print("standard-table",_tbl,_tbl['x'])

for k, v in pairs(_tbl) do
    print(k,v)
end
-- end

-- LUAY EXTENSIONS

-- printf c/j style
printf("%s = %d (%.6f)\n", 'value', 1337, .13376332)
-- end

-- end

-- binary or
local ___op = (1 | 4 | 8)
print(___op)

-- binary and
___op = ( 4 & 5)
print(___op)

_tbl['boolean'] = true
_tbl['null'] = nil
_tbl['number'] = 0.987654321
_tbl['int'] = 987654321
_tbl['utf8'] = "¹²³¼½¬@ł€¶ŧ←↓→øþæſðđŋħł»«¢„“µ"
print('stringify',stringify(_tbl,'ldata'))
print('stringify',stringify(_tbl,'json'))
print('stringify',stringify(___op))
print('stringify',stringify(_lst))

