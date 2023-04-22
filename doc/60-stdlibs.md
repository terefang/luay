# Standard Libraries

All libraries are implemented and provided as separate Java classes modules. 
Currently, Lua-5.2 has the following standard libraries:

*   basic library (extended by Luay)
*   coroutine library
*   package library
*   string manipulation
*   table manipulation (extended by Luay)
*   mathematical functions
*   bitwise operations (extended by Luay)
*   input and output
*   operating system facilities
*   debug facilities

Except for the basic and the package libraries, each library provides all its 
functions as fields of a global table or as methods of its objects.

In addition to the extensions of the standard libraries, 
Luay provides the following additional libraries:

*   hash library (md5, sha1, ...)

\pagebreak