# Basics

Luay is a hard fork of LuaJ for (selfish) purposes

* since luaj 3.0.2 seems to remain static, a new evolution is needed.
* scripting under java is fun and has applications
* jme is dead
* several extensions added in

Refer: https://github.com/luaj/luaj

Refer: https://www.lua.org/manual/5.2/manual.html#2

## What has been done

* import LuaJ 3.0.2 sources
* maven-ize build system
* remove Java ME-isms
* move to luay namespace
* 64-bit ints, with bit64 operator library (check also bitop below ↓)
* array-only tables
* hash-only tables
* hack coerce for java Map and List interface objects to pose as native 
* hack module loader to allow loading using serviceloader bader libraries
* implement lua 5.3 – io – only partially – implemented file:read options without '*', but keeping compat with 5.2
* implement lua 5.3 – utf8 – only partially – see docs
* extend the core modules with functions from ZDF – ONGOING.
* lua-stringy – Fast lua string operations (should we move that to core ?)

