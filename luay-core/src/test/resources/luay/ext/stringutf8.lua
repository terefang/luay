local _utf = require "utf8"

---------
-- split
---------
b = _utf.split("asdfsk", "s")
assert(#b == 3, #b)

assert('a|b|c' == (table.concat(_utf.split("a\tb\tc", "\t"), "|")))
assert(_utf.split("asdf", "s")[1] == "a")

assert('a||b||c' == (table.concat(_utf.split("aXXbXXc", "XX"), "|")))

---------
-- strip
---------
assert(_utf.strip(" asdf ") == "asdf")
assert(_utf.strip("asdf ") == "asdf")
assert(_utf.strip(" asdf  ") == "asdf")
assert(_utf.strip("asdf") == "asdf")
assert(_utf.strip("asdf") == "asdf")
assert(_utf.strip("\nasdf\r\n") == "asdf")
assert(_utf.strip(" asdf ") == "asdf")

--------------
-- startswith
--------------
assert(_utf.startswith("asdf", "a"))
assert(_utf.startswith("asdf", "asd"))
assert(_utf.startswith("asdf", "asdf"))
assert(_utf.startswith("asdf", ""))
assert(_utf.startswith("\0asdf", "\0"))
assert(_utf.startswith("\000asdf", "\000a"))

assert(not _utf.startswith("asdf", "asdfg"))
assert(not _utf.startswith("asdf", "b"))
assert(not _utf.startswith("asdf", "f"))
assert(not _utf.startswith("asdf", "\0"))

--------------
-- endswith
--------------
assert(_utf.endswith("asdf", "f"))
assert(_utf.endswith("asdf", "sdf"))
assert(_utf.endswith("asdf", "asdf"))
assert(_utf.endswith("asdf", "asdf"))
assert(_utf.endswith("asdf", ""))
assert(_utf.endswith("a\0df", "\0df"))

assert(not _utf.endswith("asdf", "\0"))
assert(not _utf.endswith("asdf", "asd"))
assert(not _utf.endswith("asdf", "a"))
assert(not _utf.endswith("a\0df", "a\0s"))

-------
--count
-------

assert(_utf.count("aaa", "a") == 3)
-- counts non-overlapping!
assert(_utf.count("aaa", "aa") == 1)
assert(_utf.count("aaa", "") == 0)
assert(_utf.count("aaa", "b") == 0)
assert(_utf.count("a\0a", "\0") == 1)

assert(_utf.count("aaa", "aaa") == 1)
assert(_utf.count("aaa", "aaaa") == 0)
assert(_utf.count("", "aaaa") == 0)

-- start arg.
-- index at 2, leaves "aaa"
assert(_utf.count("aaaa", "a", 2) == 3)
-- index at 1, leaves "aaa"
assert(_utf.count("aaaa", "a", 1) == 4)

-- index at 2, leaves "aaa" then only take to 3
assert(_utf.count("aaaa", "a", 2, 3) == 1)
-- start at 2 from end.
assert(_utf.count("aaaa", "a", -2) == 2)
assert(_utf.count("bbaa", "a", -2, -1) == 1)
assert(_utf.count("bbba", "a", -1) == 1)

--------
-- find
-------
assert(_utf.find("baaaa", "a") == 2)

assert(_utf.find("baaaa", "baaaa") == 1)
assert(_utf.find("baaaa", "baaaa", 1) == 1)

assert(_utf.find("baaaa", "baaaa", 2) == nil)

assert(_utf.find("abcdef", "b", 3) == nil)

assert(_utf.find("abcdef", "c", 3) == 3)
assert(_utf.find("abcdef", "c", 4) == nil)
assert(_utf.find("abcdef", "c", -1) == nil)


--------
-- join
-------

assert(_utf.join('|', "a") == 'a')
assert(_utf.join('|', "a", "b") == 'a|b')
assert(_utf.join('', "a", "b", "c") == 'abc')

--------
-- replace
-------
local _t = { a="b" }
assert(_utf.replace('abc', "a", "b") == 'bbc')
assert(_utf.replace('abc', _t) == 'bbc')
assert(_utf.replace('abc', "abc", "ab") == 'abb')
_t = { a="abc" }
assert(_utf.replace('abc', _t) == 'abcbc')
assert(_utf.replace('abc', _t, "abc", "a") == 'aaaaa')
assert(_utf.replace('abc', _t, "abc", "a", {['a']='b'}) == 'bbbbb')

--   `utf8.length (s) -> int`
--   `utf8.substr (s, n [, i]) -> string`
--   `utf8.indexof (s, n [, i]) -> int/nil`
--   `utf8.lastindexof (s, n [, i]) -> int/nil`
--   `utf8.indexofany (s, n [,...]) -> int/nil`
--   `utf8.lastindexofany (s, n [,...]) -> int/nil`
--   `utf8.lower (s) -> string`
--   `utf8.upper (s) -> string`
--   `utf8.contains(hay, needle) -> boolean`
--   `utf8.matches(hay, rx) -> boolean`
--   `utf8.rxsplit(hay, rx) -> list`
--   `utf8.format(fmt, ...) -> string`
--   `utf8.repeat(part, int) -> string`
--   `utf8.trim(hay) -> string`
--   `utf8.striptrailing(hay) -> string`
--   `utf8.stripleading(hay) -> string`
--   `utf8.rxreplace(string, rx, to) -> string`
--   `utf8.rxreplaceall(string, rx, to) -> string`