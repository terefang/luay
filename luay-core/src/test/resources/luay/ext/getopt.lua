local getopt = require 'getopt'

-- *********************************************************************************
local _l42 = mklist('--alpha=1', '--alpha=2', '--alpha=3','a1','a2','a3')
local _l41 = mklist('--alpha','--beta','a1','a2','a3')
local _list4 = mklist(_l41,_l42)

print()
print('getopt','---', 'simplext')
for _, _xarg in ipairs(_list4) do
    local _opts = false
    local _res = false
    print('args=',stringify(_xarg))
    pcall(function ()
        _opts, _res = getopt.simple(_xarg);
    end)
    print('->','opts=',stringify(_res),stringify(_opts))
    pcall(function ()
        _opts, _res = getopt.simplext(_xarg);
    end)
    print('->','optsext=',stringify(_res),stringify(_opts))
end

-- *********************************************************************************

local _alist = mklist('CN:xv:f', '+CN:xv:f', '-CN:xv:f', ':CN:xv:f', ':+CN:xv:f');

-- *********************************************************************************

local _list2 = mklist(
        mklist('-N','some','--','-defaults','a1','a2','a3'),
        mklist('-N','some','-defaults','a1','a2','a3'),
        mklist("-C",'a1','a2','a3'),
        mklist("-C",'a1','--','a2','a3'),
        mklist('a1','a2',"-C",'a3'),
        mklist('a1','a2','--',"-C",'a3'),
        mklist('-xvf','/tmp','a1','a2','a3'),
        mklist('--','-xvf','/tmp','a1','a2','a3'),
        mklist('a1','a2','a3','-xvf','/tmp'),
        mklist('a1','a2','a3','--','-xvf','/tmp'),
        mklist('-DEFAULTS','a1','a2','a3'),
        mklist('--','-DEFAULTS','a1','a2','a3'),
        mklist('a1','-DEFAULTS','a2','a3'),
        mklist('a1','--','-DEFAULTS','a2','a3')
);

-- *********************************************************************************

local _list = mklist(
        mklist("-C",'a1','a2','a3'),
        mklist('a1','a2','a3',"-C"),
        mklist('--defaults','a1','a2','a3'),
        mklist('a1','a2','a3','--defaults'),
        mklist('--default=/tmp','a1','a2','a3'),
        mklist('a1','a2','a3','--default=/tmp'),
        mklist('-D/tmp','a1','a2','a3'),
        mklist('a1','a2','a3','-D/tmp'),
        mklist('+F','a1','a2','a3'),
        mklist('a1','a2','a3','+F'),
        mklist('-gafl=URKS','a1','a2','a3'),
        mklist('a1','a2','a3','-gafl=URKS'),
        mklist('--','-DEFAULTS','a1','a2','a3'),
        mklist('a1','a2','a3','--','-DEFAULTS'),
        mklist('-DEFAULTS','--','a1','a2','a3'),
        mklist('a1','a2','a3','-DEFAULTS'),
        mklist('a1','a2','a3','-DEFAULTS')
);

-- *********************************************************************************

for _, _sarg in ipairs(_alist) do
    print()
    print('getopt','---', 'std')
    for _, _xarg in ipairs(_list2) do
        local _opts = false
        local _res = false
        print('args=',_sarg,stringify(_xarg))
        pcall(function ()
            _opts, _res = getopt.std(_sarg, _xarg);
        end)
        print('->','opts=',stringify(_res),stringify(_opts))
        pcall(function ()
            _opts, _res = getopt.stdext(_sarg, _xarg);
        end)
        print('->','optsext=',stringify(_res),stringify(_opts))
    end
end

-- *********************************************************************************

print()
print('getopt','---', 'simple')

for _, _xarg in ipairs(_list) do
    print('args=',stringify(_xarg))
    local _opts, _res = getopt.simple(_xarg);
    print('->','opts=',stringify(_res),stringify(_opts))
    local _opts, _res = getopt.simplext(_xarg);
    print('->','optsext=',stringify(_res),stringify(_opts))
end

-- *********************************************************************************

local _list3 = mklist(
        mklist('--alpha','--beta', "some"),
        mklist('--beta', '--alpha', "some"),
        mklist('--beta', '--', '--alpha', "some"),
        mklist('--beta', '--alpha', '--',  "some"),
        mklist('--alpha', '--', '--beta', "some"),
        mklist('--alpha', '--beta', '--',  "some")
);

print()
local _lopts = mkmap('alpha',0,'beta',1);

for _, _sarg in ipairs(_alist) do
    print()
    print('getopt','---', 'long')
    for _, _xarg in ipairs(_list3) do
        local _opts = false
        local _res = false
        print('args=',_sarg,stringify(_lopts),stringify(_xarg))
        pcall(function ()
            _opts, _res = getopt.long(_sarg, _lopts, _xarg);
        end)
        print('->','opts=',stringify(_res),stringify(_opts))
        pcall(function ()
            _opts, _res = getopt.longext(_sarg, _lopts, _xarg);
        end)
        print('->','optsext=',stringify(_res),stringify(_opts))
    end
end


