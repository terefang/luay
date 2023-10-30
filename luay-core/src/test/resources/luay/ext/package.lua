
print("path0", __PATH__)
print("path1", package.path)


local _test1 = require('blah.test');

package.addpath(__PATH__..'/blah')

print("path1", package.path)

local _test2 = require('test');

local _test3 = package.loadlib('getopt');

print("1", _test1)
print("2", _test2)
print("3", _test3)

local _test4 = package.getpath();

for i, v in ipairs(_test4) do
    print(i, v)
end


