_rc = pcall(function ()

    local _http = require('snmp')

end)

print(_rc)

local _c = snmp.snmpv2('fredonas.local');

local _oid = snmp.oid(1,3,6) -- -> '.1.3.6'
assert(_oid=='.1.3.6')
local _row = 'm';

while (not (_oid == nil))
do
    _row = snmp.next(_c, _oid)
    if (type(_row) == 'table') then
        print(stringify(_row))
        _oid = _row[1]
    else
        _oid = nil
    end
    _oid = nil
end

print('------------------------------------')
local _list = snmp.getall(_c, 5192, ".1.3.6.1.2.1.1.1.0", ".1.3.6.1.2.1.1.2.0", ".1.3.6.1.2.1.1.3.0", ".1.3.6.1.2.1.1.4.0", ".1.3.6.1.2.1.1.5.0", ".1.3.6.1.2.1.1.6.0",  ".1.3.6.1.2.1.1.8.0")

for _, _row in ipairs(_list) do
    print(stringify(_row))
end

print('------------------------------------')
_list = snmp.walk(_c, ".1.3.6.1.2.1.1")

for _, _row in ipairs(_list) do
    print(stringify(_row))
end

snmp.close(_c)
print('finis.')