local _hash = require('hash');

function print_all(arg)
    print('arg =',arg)
    print('hex =',_hash.to_hex(arg))
    print('--- =',_hash.to_hex(_hash.from_hex(_hash.to_hex(arg))))
    print('b32 =',_hash.to_b32(arg))
    print('--- =',_hash.to_hex(_hash.from_b32(_hash.to_b32(arg))))
    print('b64 =',_hash.to_b64(arg))
    print('--- =',_hash.to_hex(_hash.from_b64(_hash.to_b64(arg))))
    print('b26 =',_hash.to_b26(arg))
    print('b36 =',_hash.to_b36(arg))
    print('b62 =',_hash.to_b62(arg))
    print('uuid=',_hash.to_uuid(arg))
    print('xid =',_hash.to_xid(arg))
    print('xxid=',_hash.to_xxid('default', arg))
end

print_all(_var);
print_all(_hash.md5(_var));
print_all(_hash.sha1(_var));
print_all(_hash.sha256(_var));
print_all(_hash.sha512(_var));

print('random uuid = ',_hash.to_uuid())


-- hash object
local _md = _hash.new_hash('MD5');
_md:update('this');
_md:update('hello','world');
print('md5 hex =',_hash.to_hex(_md:finish()))

local _mh = _hash.new_mac('HMACMD5', 's3cr3t');
_mh:update('this');
_mh:update('hello','world');
print('hmacmd5 hex =',_hash.to_hex(_mh:finish()))

print('random string =', _hash.random_string(64))
print('random string =', _hash.random_string(64,"ABCDEFGHIJKLMNOPQRSTUVWXYZ"))
print('random string =', _hash.random_string(64,"PGHWCBADIRENVzmfkosljxyutq","0123456789./+*"))
print('random string =', _hash.random_string(64,"PGHWCBADIRENVzmfkosljxyutq",""))




