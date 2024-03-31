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
print('--- MD5')
print_all(_hash.md5(_var));
print('--- MD5 ×2')
print_all(_hash.md5(_var,_var));
print('--- SHA1')
print_all(_hash.sha1(_var));
print('--- SHA1 ×2')
print_all(_hash.sha1(_var,_var));
print('--- SHA256')
print_all(_hash.sha256(_var));
print('--- SHA256 ×2')
print_all(_hash.sha256(_var,_var));
print('--- SHA512')
print_all(_hash.sha512(_var));
print('--- SHA512 ×2')
print_all(_hash.sha512(_var,_var));
print('--- SHA3')
print_all(_hash.sha3(_var));
print('--- SHA3 ×2')
print_all(_hash.sha3(_var,_var));

print('--- MD5 MAC')
print_all(_hash.md5mac(_var,_var));
print('--- SHA1 MAC')
_res,_err = _hash.sha1mac(_var,_var);
if _res == nil then
    print(_err)
end
print_all(_res);
print('--- SHA256 MAC')
print_all(_hash.sha256mac(_var,_var));
print('--- SHA512 MAC')
print_all(_hash.sha512mac(_var,_var));


print('--- MD5 SSLMAC')
_res,_err = _hash.md5sslmac(_var,_var)
if _res == nil then
    print(_err)
end
print_all(_res);
print('--- SHA1 SSLMAC')
_res,_err = _hash.sha1sslmac(_var,_var)
if _res == nil then
    print(_err)
end
print_all(_res);


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

local _malgos = mklist("MD5","SHA-1","SHA-224","SHA-256","SHA-384","SHA-512");

for _, _malgo in ipairs(_malgos) do
    print(_malgo,'=', _hash.hash_hex(_malgo, "123456789"))
    print(_malgo,'=', _hash.hash_hex(_malgo, "123456789", "123456789"))
    print(_malgo,'=', _hash.hash_hex(_malgo, "123456789", "123456789", "123456789"))
end

local _halgos = mklist("HmacPBESHA1","HmacSHA1", "PBEWithHmacSHA1", "PBEWithHmacSHA224", "HmacSHA224", "PBEWithHmacSHA256" , "HmacSHA256" , "PBEWithHmacSHA384" ,"HmacSHA384" , "PBEWithHmacSHA512", "HmacSHA512");

for _, _halgo in ipairs(_halgos) do
    print(_halgo,'=', _hash.hashmac_hex(_halgo, "123456789", "123456789"))
    print(_halgo,'=', _hash.hashmac_hex(_halgo, "123456789", "123456789", "123456789"))
    print(_halgo,'=', _hash.hashmac_hex(_halgo, "123456789", "123456789", "123456789", "123456789"))
end

