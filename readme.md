# a luaj hard-fork for selfish purposes
  
* since luaj 3.0.2 seems to remain static, a new evolution is needed.
* scripting under java is fun and has applications
* jme is dead

## DONE

see docs

## TODO

- [ ] evaluate libuseful-lua, what to implement
    - [x] entropy
    - [x] filesys --> adapted in "lfs" module
    - [ ] hash
- [ ] evaluate https://github.com/aiq/luazdf, what to implement
  - [ ] algo/algo
  - [ ] algo/luhn/luhn
  - [ ] cal/cal
  - [ ] cal/dayofweek/dayofweek
  - [ ] cal/gregdate/gregdate
  - [ ] cal/isleapyear/isleapyear
  - [ ] cal/julianday/julianday
  - [ ] cal/tilldayofweek/tilldayofweek
  - [ ] cli/argsfileinargs/argsfileinargs
  - [ ] cli/argsfilesindir/argsfilesindir
  - [ ] cli/cli
  - [ ] cli/escapeshellarg/escapeshellarg
  - [ ] cli/getoptpair/getoptpair
  - [ ] cli/getoptvalues/getoptvalues
  - [ ] cli/isoneshellarg/isoneshellarg
  - [ ] cli/shelljoin/shelljoin
  - [ ] cli/shellsplit/shellsplit
  - [ ] fn/caddr/caddr
  - [ ] fn/cadr/cadr
  - [ ] fn/car/car
  - [ ] fn/cddr/cddr
  - [ ] fn/cdr/cdr
  - [ ] fn/compose/compose
  - [ ] fn/constantly/constantly
  - [ ] fn/eqf/eqf
  - [ ] fn/fn
  - [ ] fn/fnil/fnil
  - [ ] fn/gtef/gtef
  - [ ] fn/gtf/gtf
  - [ ] fn/iscallable/iscallable
  - [ ] fn/juxt/juxt
  - [ ] fn/lambda/lambda
  - [ ] fn/ltef/ltef
  - [ ] fn/ltf/ltf
  - [ ] fn/matchesf/matchesf
  - [ ] fn/notf/notf
  - [ ] fn/nth/nth
  - [ ] fn/once/once
  - [ ] fn/partial/partial
  - [ ] fn/repeatedly/repeatedly
  - [ ] graph/addedge/addedge
  - [ ] graph/adjmatrix/adjmatrix
  - [ ] graph/buildgraph/buildgraph
  - [ ] graph/graph
  - [ ] graph/isolatednodes/isolatednodes
  - [ ] graph/leafnodes/leafnodes
  - [ ] graph/pathedges/pathedges
  - [ ] graph/rmedge/rmedge
  - [ ] graph/rmnode/rmnode
  - [ ] graph/sinknodes/sinknodes
  - [ ] graph/transposegraph/transposegraph
  - [ ] graph/tsort2d/tsort2d
  - [ ] graph/tsort/tsort
  - [ ] math/acosh/acosh
  - [ ] math/acoth/acoth
  - [ ] math/asinh/asinh
  - [ ] math/atan2/atan2
  - [ ] math/atanh/atanh
  - [ ] math/cbrt/cbrt
  - [ ] math/cosh/cosh
  - [ ] math/cot/cot
  - [ ] math/coth/coth
  - [ ] math/csc/csc
  - [ ] math/csch/csch
  - [ ] math/digitsum/digitsum
  - [ ] math/fact2/fact2
  - [ ] math/fact/fact
  - [ ] math/gcd/gcd
  - [ ] math/isfinite/isfinite
  - [ ] math/isinf/isinf
  - [ ] math/isnan/isnan
  - [ ] math/lcm/lcm
  - [ ] math/math
  - [ ] math/matrixtostrlst/matrixtostrlst
  - [ ] math/minmax/minmax
  - [ ] math/product/product
  - [ ] math/rounddown/rounddown
  - [ ] math/round/round
  - [ ] math/roundup/roundup
  - [ ] math/sech/sech
  - [ ] math/sec/sec
  - [ ] math/sinh/sinh
  - [ ] math/sum/sum
  - [ ] math/tanh/tanh
  - [ ] num/asindex/asindex
  - [ ] num/clamp/clamp
  - [ ] num/inrange/inrange
  - [ ] num/iseven/iseven
  - [ ] num/isinteger/isinteger
  - [ ] num/num
  - [ ] num/numseq/numseq
  - [ ] num/toarabic/toarabic
  - [ ] num/toroman/toroman
  - [ ] num/trunc/trunc
  - [ ] str/asciichunks/asciichunks
  - [ ] str/camelcase/camelcase
  - [ ] str/caretpos/caretpos
  - [ ] str/charat/charat
  - [ ] str/chomp/chomp
  - [ ] str/countmatch/countmatch
  - [ ] str/expandtabs/expandtabs
  - [ ] str/findbyte/findbyte
  - [ ] str/isalnum/isalnum
  - [ ] str/isalpha/isalpha
  - [ ] str/isblank/isblank
  - [ ] str/iscntrl/iscntrl
  - [ ] str/isdigit/isdigit
  - [ ] str/isxdigit/isxdigit
  - [ ] str/kebabcase/kebabcase
  - [ ] str/leftpad/leftpad
  - [ ] str/linecol/linecol
  - [ ] str/lines/lines
  - [ ] str/logline/logline
  - [ ] str/lualiteral/lualiteral
  - [ ] str/mint/mint
  - [ ] str/numeronym/numeronym
  - [ ] str/rightpad/rightpad
  - [ ] str/rmprefix/rmprefix
  - [ ] str/rmsuffix/rmsuffix
  - [ ] str/serialize/serialize
  - [ ] str/snakecase/snakecase
  - [ ] str/str
  - [ ] str/strlstlen/strlstlen
  - [ ] str/utf8codes/utf8codes
  - [ ] sys/capexec/capexec
  - [ ] sys/capinexec/capinexec
  - [ ] sys/rmcmdline/rmcmdline
  - [ ] sys/sys
  - [ ] sys/sysinfo/sysinfo
  - [ ] sys/taptest/taptest
  - [ ] tab/accessor/accessor
  - [ ] tab/buildset/buildset
  - [ ] tab/clear/clear
  - [ ] tab/countlen/countlen
  - [ ] tab/deepcopy/deepcopy
  - [ ] tab/divorce/divorce
  - [ ] tab/dump/dump
  - [ ] tab/flyweightstore/flyweightstore
  - [ ] tab/getin/getin
  - [ ] tab/invertby/invertby
  - [ ] tab/isarray/isarray
  - [ ] tab/isdict/isdict
  - [ ] tab/isempty/isempty
  - [ ] tab/isfilled/isfilled
  - [ ] tab/keys/keys
  - [ ] tab/marry/marry
  - [ ] tab/pluck/pluck
  - [ ] tab/same/same
  - [ ] tab/setin/setin
  - [ ] tab/shallowcopy/shallowcopy
  - [ ] tab/tab
  - [ ] tab/tableize/tableize
  - [ ] tab/tuple/tuple
  - [ ] tab/updatein/updatein
  - [ ] tab/values/values
- [ ] implement lua 5.4 std modules and changes ?
- [ ] evaluate commonly packaged modules, what to implement
    - [ ] https://github.com/Tieske/binaryheap.lua
    - [x] lua-alt-getopt-0.8.0-1 --> adopted in "getopt" module
    - [ ] lua-basexx-0.4.0-2	--> adopted in "hash" module
    - [ ] lua-binaryheap-0.4-1	Binary heap implementation for Lua
    - [x] lua-bit32-5.3.5.1-1	--> adopted in "bit32" and "bit64" module
    - [x] lua5.1-bitop-1.0.2-5	--> adopted in "bit32" and "bit64" module
    - [ ] lua-compat53-0.7-3	Compatibility module providing Lua-5.3-style APIs for Lua 5.1
    - [ ] lua-cqueues-20190813-3	Stackable Continuation Queues for the Lua Programming Language
    - [-] lua-cyrussasl-1.1.0-1	--> not in scope
    - [ ] lua-cjson-2.1.0.12-1	Lua Extension: JSON Encoding/Decoding
    - [x] lua-dbi-0.7.2-1	--> adopted in "dao" module
    - [ ] lua-event-0.4.6-1	This is a binding of libevent to Lua
    - [ ] lua-expat-1.4.1-1	SAX XML parser based on the Expat library
    - [ ] lua-fifo-0.2-1	FIFO library for Lua
    - [ ] lua-filesystem-1.6.2-2	File System Library for the Lua Programming Language
    - [ ] lua-fun-0.1.3-1	Functional programming library for Lua
    - [ ] lua-http-0.3-5	HTTP library for Lua
    - [ ] lua-json-1.3.2-2	JSON Parser/Constructor for Lua
    - [ ] lua-ldap-1.1.0-3	LDAP client library for Lua, using OpenLDAP
    - [ ] lua-ldap-compat-1.1.0-20	LDAP client library for Lua 5.1, using OpenLDAP
    - [ ] lua-lpeg-0.12-1	Parsing Expression Grammars for Lua
    - [ ] lua-lpeg-patterns-0.5-1	A collection of LPEG patterns
    - [ ] lua-luaossl-20190731-1	Most comprehensive OpenSSL module in the Lua universe
    - [ ] lua-lunit-0.5-5	Unit testing framework for Lua
    - [x] lua-md5-1.1.2-1	--> adopted in "hash" module
    - [ ] lua-mmdb-0.2-1	MaxMind database parser for Lua
    - [ ] lua-mpack-1.0.4-2	Implementation of MessagePack for Lua
    - [ ] lua-posix-32-2	A POSIX library for Lua
    - [ ] lua-prelude-5.2.0-2	Lua bindings for prelude
    - [ ] lua-psl-0.3-2	Lua bindings to Public Suffix List library
    - [-] lua-readline-3.2-1	--> not in scope
    - [-] lua-sec-1.2.0-1	--> not in scope
    - [-] lua-socket-3.1.0-1	--> not in scope
    - [-] lua-term-0.03-3	--> not in scope
    - [-] lua-unbound-1.0.0-1	--> not in scope

https://github.com/CDSoft/luax/blob/master/doc/crypt.md