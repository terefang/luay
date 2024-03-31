-- local _string = require('string');
-- already a base library

-- // luay extension
-- string.set("to_utf8", new _to_utf8());

-- *   `string.concat(v1[,...,vN]) -> string`
-- *   `string.concat(table) -> string`

-- *   `string.split(sep, string) -> list`
-- *   `string.split(sep, string, limit) -> list` \
-- Java String.split

-- *   `string.explode(string) -> list`
-- *   `string.explode(sep, string) -> list`
-- *   `string.explode(sep, string, limit) -> list` \
-- Java StringTokenizer, default all whitespace

-- *   `string.implode(sep,v1[,...,vN]) -> string`
-- *   `string.implode(sep,table) -> string`

-- *   `string.mformat(fmt, v1[,...,vN]) -> string` \
-- Java MessageFormatter

-- * `string.to_int (string [, default]) -> long`
local _ints = mklist("12171581","64304473","29735077","75857261","5353031","21463613","64588213","64372799","80042483","55431539","62529583","70723819","83327341","33727171","9249167", "20939833","2980193","353137","54653617","80253137");
for _, v in ipairs(_ints) do
    _d = string.to_int(v)..'';
    assert(v == _d, v..' != '.._d)
end

-- * `string.to_long (string [, default]) -> long`
local _longs = mklist("12171581","64304473","29735077","75857261","5353031","21463613","64588213","64372799","80042483","55431539","62529583","70723819","83327341","33727171","9249167", "20939833","2980193","353137","54653617","80253137");
for _, v in ipairs(_longs) do
    _d = string.to_long(v)..'';
    assert(v == _d, v..' != '.._d)
end

-- * `string.to_float (string [, default]) -> double`
local _floats = mklist("1.0000000", "-1.1234567", "0.0000000")
for _, v in ipairs(_floats) do
    _d = string.to_float(v);
    assert(v == string.sformat("%9.7f", _d), v..' != '.._d)
end

-- * `string.to_double (string [, default]) -> double`
local _doubles = mklist("1.000000000000000", "1.123456789012345", "0.000000000000000")
for _, v in ipairs(_doubles) do
    _d = string.to_double(v);
    assert(v == string.sformat("%17.15f", _d), v..' != '.._d)
end

-- * `string.to_bool (string [, default]) -> boolean`
local _falses = mklist("false", "f", "off", "none", "n", "no", "null", "nul", "nil", "0", "")
for _, v in ipairs(_falses) do
    assert(string.to_bool(v) == false, v.." is not false")
end

local _trues = mklist("true", "t", "on", "some", "y", "yes", "full", "ful", "viel", "1", "#")
for _, v in ipairs(_trues) do
    assert(string.to_bool(v) == true, v.." is not true")
end

