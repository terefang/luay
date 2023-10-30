## Modules

Refer: http://www.lua.org/manual/5.2/manual.html#6.3

*   `require (modname)`
*   `package.config`
*   `package.loaded`
*   `package.loadlib (libname, funcname)` — not implemented
*   `package.path`
*   `package.preload`
*   `package.searchers`
*   `package.searchpath (name, path [, sep [, rep]])`

### Luay Extensions

*   `package.addpath(path [, ...]) -> _, string` — prepends to existing search path
*   `package.setpath(path [, ...]) -> _, string` — replaces existing search path
*   `package.loadlib (javaclassname or luayextname)`

\pagebreak