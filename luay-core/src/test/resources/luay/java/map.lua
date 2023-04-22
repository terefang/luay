-- _var comes from java { 1 = 0, ..., 256 = ff }

local _string = require('string')

for _i,_x in pairs(_var) do
    assert(_i==_x);
end

_var['256'] = 'hello'
assert(_var['256'] == 'hello')

_var[257] = 'world'
assert(_var['257'] == 'world')


