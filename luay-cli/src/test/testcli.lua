#!/usr/bin/env luay

print('--- stringify _G ---')
print(stringify(_G))

print('--- pairs _G ---')
for _i, _u in pairs(_G) do
    print(_i,_u)
end

print('--- ipairs _ARGS ---')
for _i, _u in ipairs(_ARGS) do
    print(_i,_u)
end

print('--- ipairs PATH ---')
local _t = package.getpath();

for i, v in ipairs(_t) do
    print(i, v)
end