-- _var comes from java { 1 = 0, ..., 256 = ff }

for _i,_x in ipairs(_var) do
    assert(string.format('%1x',_i-1)==_x);
end

assert(#_var == 256)

_var[256] = 'hello'
assert(_var[256] == 'hello')
assert(#_var == 256)

_var[257] = 'world'
assert(_var[257] == 'world')
assert(#_var == 257)


