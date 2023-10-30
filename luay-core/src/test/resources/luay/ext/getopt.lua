local getopt = require 'getopt'

-- *********************************************************************************
local _l42 = list('--alpha=1', '--alpha=2', '--alpha=3','a1','a2','a3')
local _l41 = list('--alpha','--beta','a1','a2','a3')
local _list4 = list(_l41,_l42)

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

local _alist = list('CN:xv:f', '+CN:xv:f', '-CN:xv:f', ':CN:xv:f', ':+CN:xv:f');

-- *********************************************************************************

local _list2 = list(
        list('-N','some','--','-defaults','a1','a2','a3'),
        list('-N','some','-defaults','a1','a2','a3'),
        list("-C",'a1','a2','a3'),
        list("-C",'a1','--','a2','a3'),
        list('a1','a2',"-C",'a3'),
        list('a1','a2','--',"-C",'a3'),
        list('-xvf','/tmp','a1','a2','a3'),
        list('--','-xvf','/tmp','a1','a2','a3'),
        list('a1','a2','a3','-xvf','/tmp'),
        list('a1','a2','a3','--','-xvf','/tmp'),
        list('-DEFAULTS','a1','a2','a3'),
        list('--','-DEFAULTS','a1','a2','a3'),
        list('a1','-DEFAULTS','a2','a3'),
        list('a1','--','-DEFAULTS','a2','a3')
);

-- *********************************************************************************

local _list = list(
        list("-C",'a1','a2','a3'),
        list('a1','a2','a3',"-C"),
        list('--defaults','a1','a2','a3'),
        list('a1','a2','a3','--defaults'),
        list('--default=/tmp','a1','a2','a3'),
        list('a1','a2','a3','--default=/tmp'),
        list('-D/tmp','a1','a2','a3'),
        list('a1','a2','a3','-D/tmp'),
        list('+F','a1','a2','a3'),
        list('a1','a2','a3','+F'),
        list('-gafl=URKS','a1','a2','a3'),
        list('a1','a2','a3','-gafl=URKS'),
        list('--','-DEFAULTS','a1','a2','a3'),
        list('a1','a2','a3','--','-DEFAULTS'),
        list('-DEFAULTS','--','a1','a2','a3'),
        list('a1','a2','a3','-DEFAULTS'),
        list('a1','a2','a3','-DEFAULTS')
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

local _list3 = list(
        list('--alpha','--beta', "some"),
        list('--beta', '--alpha', "some"),
        list('--beta', '--', '--alpha', "some"),
        list('--beta', '--alpha', '--',  "some"),
        list('--alpha', '--', '--beta', "some"),
        list('--alpha', '--beta', '--',  "some")
);

print()
local _lopts = map('alpha',0,'beta',1);

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


