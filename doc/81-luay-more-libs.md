
## array (inspired by luazdf)

```lua
local array = require('array');
```

*   `array.array(value[, value ...]) -> array-only-table`
*   `array.list(value[, value ...]) -> list-only-table`
*   `array.push (list, value[, value ...]) -> list` \
    Appends the values onto the list, returns the list.

*   `array.pop (list) -> value` \
    Removes the last element from the list and returns it.

*   `array.unshift (list, value[, value ...]) -> list` \
    Prepends the values to the list, returns the list.

*   `array.shift (table) -> value` \
    Removes the first element from the list and return it.

*   `array.append (list, value[, value ...]) -> table` \
    Alias for push.

*   `array.appendall (table, tbl1[, ..., tblN]) -> table` \
    Append all k,v from following tables to the first one, return the first.

*   `array.chunk(list, size) -> list(lst1, ..., lstN)` \
    Function to split array into /size/ chunks.

*   `array.split(list, size) -> lst1, lst2` \
    Function to split array in two at /size/.

*   `array.collect (table[, ..., tableN]) -> list(v1, ..., vX)` \
    Function to collect all values for the list of tables.

*   `array.count (table, fv) -> table` \
    Sorts a list into groups and returns a count for the number of objects in
    each group. Similar to groupBy, but instead of returning a list of values,
    returns a count for the number of values in that group.

*   `array.difference (list1, list2) -> list` \
    Computes the values of list1 that not occure in list2.

*   `array.find( list, value [, init] ) -> v, i`
*   `array.findif( list, fv [, init] ) -> v, i` \
    Looks through an array table and returning the first element that matches.

*   `array.indexof( list, value [, init] ) -> i`
*   `array.indexof( list, fv [, init] ) -> i` \
    Looks through an array table and returning the index of the first element that matches.

\pagebreak


## crypto Library

```lua
local crypto = require('crypto');
```
*   `crypto.blowfish_cbc_pkcs5(encrypt_flag, key_bytes [,initvec_bytes]) -> function` \
	function(bytes [, final_flag]) -> bytes – does blowfish in CBC mode

*   `crypto.cipher(algorithm, encrypt_flag, key_bytes [,initvec_bytes]) -> function` \
	function(bytes [, final_flag]) -> bytes – does blowfish in CBC mode

\pagebreak


## lfs/filesys

posix file system functions mostly inspired by libUseful and zdf lfs.

### Functions

*   `lfs.basename(Path) -> map, list` \
    gets a filename (basename) from a path
*   `lfs.dirname(Path)` \
    gets a directory part of a path, clipping off the last part that should be the filename
*   `lfs.filename(Path)` \
    gets a file name from a path, this is name without extension, so distinct from basename
*   `lfs.exists(Path)` \
    return true if a filesystem object (file, directory, etc) exists at path 'Path', false otherwise
*   `lfs.extn(Path)` \
    gets a file extension from a path
*   `lfs.mkdir(Path)` \
    make a directory. DirMask is the 'mode' of the created directory, and is optional
*   `lfs.mkdirPath(Path)` \
    make a directory, CREATING ALL PARENT DIRECTORIES AS NEEDED.
*   `lfs.size(Path)` \
    get size of a file
*   `lfs.mtime(Path)` \
    get modification time of a file
*   `lfs.chown(Path, Owner)` \
    change owner of a file. 'Owner' is the name, not the uid
*   `lfs.chgrp(Path, Group)` \
    change group of a file. 'Group' is the group name, not the gid
*   `lfs.chmod(Path, Mode)` \
    change mode/permissions of a file.
    Perms can be a numeric value like '0666' or rwx string like 'rw-rw-rw'
*   `lfs.rename(from, to)
*   `lfs.rmdir(path)` \
    remove directory. Directory must be empty
*   `lfs.copy(src, dest)` \
    make a copy of a file
*   `lfs.copydir(src, dest)` \
    make a recursive copy of a directory
*   `lfs.find(File,path) -> path`
*   `lfs.symlink(path, symlink)` \
    create a symbolic link at 'symlink' pointing to file/directory at 'path'
*   `lfs.link(path, linkname)` \
    create a hard link at 'linkname' pointing to file/directory at 'path'
*   `lfs.is_file(filename) -> boolean`
*   `lfs.is_dir(filename) -> boolean`
*   `lfs.list_dirs(dirname [, ext, ...]) -> list`
*   `lfs.list_dir(dirname [, ext, ...]) -> list`

### TODO ZDF FS

abspath
appendfile
copyfile
currentdir
cutpath
delimiter
dirfiles
dirhas
dirif
dirmatch
dirtree
extname
filelist
findup
isabsolute
isdir
isdodd
isfile
isleafdir
isposixname
isreadable
iswindowsname
joinpath
longextname
mkdirtree
mkpath
normpath
readargsfile
readfile
readlines
readmxtfile
relativepath
rmdirtree
rootprefix
separator
shortfilename
splitpath
subdirs
syspath
unixpath
winpath
writefile
writelines

\pagebreak


## getopt

option processing for lists/arrays.

### Functions

*   `getopt.std( pattern, arglist ) -> map, list` \
    roughly emulates single character mode of getopt(3).
    * option processing always stops at a standalone double-dash (ie. '--').
    * option characters followed by a colon (ie. ':'), the option takes an argument
    * if pattern is prefixed by a dash (ie. '-'), invalid options will be ignored
    * if pattern is prefixed by a colon (ie. ':'), invalid options will be processed as zero-arg options
    * if pattern is prefixed by a plus (ie. '+'), the first non-option argument will stop processing
    * short options can be abbreviated like
      * 'f:vx' on `-fvx file` -> `f=file, v=true, x=true`
      * 'f:vx' on `-vfx file` -> `f=file, v=true, x=true`
      * 'f:vx' on `-vxf file` -> `f=file, v=true, x=true`

* `getopt.long( [ pattern,] optionmap, arglist ) -> map, list` \
    non-standard long-option processiog in addition or instead of getopt(3).
    * optionmap is given as `map('zero-arg-option',0,'arg-option',1)`
    * invalid options cause error.
    * option-arguments can be given as `--zero-arg-option` or `--arg-option option-value` or `--arg-option=option-value`

* `getopt.simple( arglist ) -> map, list` \
    hard-wired option rules processing.
    * option processing always stops at a standalone double-dash (ie. '--').
    * option processing always stops at a standalone dash (ie. '-').
    * option processing always stops at the first non-option argument.
    * short options are zero-arg
    * short options cannot be abbreviated, the arg option start immediately like `-ffile -v -x` -> `f=file, v=true, x=true`
    * if a single-dash option string includes an equal sign, it is parsed like `-f=file -file=file` -> `f=file, file=file`
    * short options starting with a plus (ie. '+') are inverted like `-v +x` -> `v=true, x=false`
    * long options are always parsed as `--zero-arg-option` or `--arg-option=option-value`


* `getopt.stdext( pattern, arglist ) -> list<pair>, list`
* `getopt.longext( [ pattern,] optionmap, arglist ) -> list<pair>, list`
* `getopt.simplext( arglist ) -> list<pair>, list`

\pagebreak


## hash Library

```lua
local hash = require('hash');
```
*   `hash.md5 (bytes [,bytes]) -> bytes`
*   `hash.sha1 (bytes [,bytes]) -> bytes`
*   `hash.sha256 (bytes [,bytes]) -> bytes`
*   `hash.sha512 (bytes [,bytes]) -> bytes`
*   `hash.sha3 (bytes [,bytes]) -> bytes`
*   `hash.hash (algo, bytes [,bytes]) -> bytes`
*   `hash.hash_hex (algo, bytes [,bytes]) -> bytes`

*   `hash.md5mac (salt,bytes [,bytes]) -> bytes`
*   `hash.sha1mac (salt,bytes [,bytes]) -> bytes`
*   `hash.sha256mac (salt,bytes [,bytes]) -> bytes`
*   `hash.sha512mac (salt,bytes [,bytes]) -> bytes`
*   `hash.hashmac (algo,salt,bytes [,bytes]) -> bytes`
*   `hash.hashmac_hex (algo,salt,bytes [,bytes]) -> bytes`

*   `hash.to_hex (bytes) -> string`
*   `hash.from_hex (string) -> bytes`
*   `hash.to_b32 (bytes) -> string`
*   `hash.from_b32 (string) -> bytes`
*   `hash.to_b64 (bytes) -> string`
*   `hash.from_b64 (string) -> bytes`
*   `hash.to_b26 (bytes) -> string`
*   `hash.to_b36 (bytes) -> string`
*   `hash.to_b62 (bytes) -> string`
*   `hash.to_uuid (bytes) -> string`
*   `hash.to_xid (bytes) -> string`
*   `hash.to_xxid (bytes, bytes) -> string`
*   `hash.random_string (int [, string[, string]]) -> string`

### hash object

```lua
local _md = hash.new_hash('MD5');
_md:update('this');
_md:update('hello','world');
print('md5 hex =',hash.to_hex(_md:finish()))
print('md5 hex =',_md:finish_hex())
print('md5 b64 =',_md:finish_b64())
print('md5 b32 =',_md:finish_b32())
```

### mac object

```lua
local _mh = hash.new_mac('HMACMD5', 's3cr3t');
_mh:update('this');
_mh:update('hello','world');
print('hmacmd5 hex =',hash.to_hex(_mh:finish()))
print('hmacmd5 hex =',_mh:finish_hex())
print('hmacmd5 b64 =',_mh:finish_b64())
print('hmacmd5 b32 =',_mh:finish_b32())
```

\pagebreak


## ldapjndi

Read-only Ldap access via JNDI.

### Functions

*   `ldapjndi.connect(uri, admindn, adminpw[, mode, starttls]) -> session` \
    uri = "ldap://ldap.forumsys.com:port/" or "ldaps://ldap.forumsys.com:port/"
*   `ldapjndi.disconnect(session)`
*   `ldapjndi.close(session)`
*   `ldapjndi.get(session. dn [, a1, ..., aN]) -> map`
*   `ldapjndi.search(session. basedn, filter [, scope, a1, ..., aN]) -> list of maps`
*   `ldapjndi.authdn(session. dn, pw [, mode]) -> bool`

\pagebreak


## map (inspired by luazdf)

```lua
local map = require('map');
```

*   `map.map (k1,v1[, ..., kN, vN]) -> map-only-table` \
    Function to collect all kv-pairs into a map-only table.

* `map.keys (table[, ..., tableN]) -> list(k1, ..., kX)` \
    alias of collectk
* `map.collectk (table[, ..., tableN]) -> list(k1, ..., kX)` \
    Function to collect all keys for the list of tables.

*   `map.values (table[, ..., tableN]) -> list(v1, ..., vX)` \
    alias of collectv
*   `map.collectv (table[, ..., tableN]) -> list(v1, ..., vX)` \
    Function to collect all values for the list of tables.

*   `map.collect (table[, ..., tableN]) -> table` \
    Function to collect all k,v for the list of tables.

*   `map.count (table, fv) -> table` \
    Sorts a list into groups and returns a count for the number of objects in
    each group. Similar to groupBy, but instead of returning a list of values,
    returns a count for the number of values in that group.

\pagebreak


## pwcrypt Library

```lua
local pwc = require('pwcrypt');
```
*   `pwc.checkpw ( password , encoded ) -> bool`
*   `pwc.shacrypt ( password [,salt] ) -> string ({sha1})`
*   `pwc.sha1crypt ( password [,salt] ) -> string ($4$)`
*   `pwc.sha1_hex ( password ) -> string`
*   `pwc.sha1_b64 ( password ) -> string`
*   `pwc.sha256crypt ( password [,salt] ) -> string ($5$)`
*   `pwc.sha512crypt ( password [,salt] ) -> string ($6$)`
*   `pwc.apr1crypt ( password [,salt] ) -> string ($apr1$)`
*   `pwc.md5crypt ( password [,salt] ) -> string ($1$)`
*   `pwc.b64crypt ( password [,salt] ) -> string ({b64})`

\pagebreak

