-- https://hashcat.net/wiki/doku.php?id=example_hashes

local _pwc = require('pwcrypt')
_password = 'hashcat'

-- Apache $apr1$ MD5, md5apr1, MD5 (APR) 2	$apr1$71850310$gh9m4xcAn3MGxogwX/ztb.
_salt = '$apr1$71850310$gh9m4xcAn3MGxogwX/ztb.'
_enc = _pwc.apr1crypt(_password,_salt)
assert(_pwc.checkpw(_password,_salt) == true)
--print(_enc,_salt)


-- md5crypt, MD5 (Unix), Cisco-IOS $1$ (MD5) 2	$1$28772684$iEwNOgGugqO9.bIz5sk8k/
_salt = '$1$28772684$iEwNOgGugqO9.bIz5sk8k/'
_enc = _pwc.md5crypt(_password,_salt)
assert(_pwc.checkpw(_password,_salt) == true)
--print(_enc,_salt)


-- bcrypt $2*$, Blowfish (Unix)	$2a$05$LhayLxezLhK1LhWvKxCyLOj0j1u.Kj0jZ0pEmm134uzrQlFvQJLF6


-- sha1crypt, SHA1 (Unix)	$4$28772684$kHkV4xln0J8wHf6+HGhumD9Tbgs$
_salt = '$4$28772684$kHkV4xln0J8wHf6+HGhumD9Tbgs$'
_enc = _pwc.sha1crypt(_password,_salt)
assert(_pwc.checkpw(_password,_salt) == true)
--print(_enc,_salt)


-- sha256crypt $5$, SHA256 (Unix) 2	$5$rounds=5000$GX7BopJZJxPc/KEK$le16UF8I2Anb.rOrn22AUPWvzUETDGefUmAV8AZkGcD
_salt = '$5$rounds=5000$GX7BopJZJxPc/KEK$le16UF8I2Anb.rOrn22AUPWvzUETDGefUmAV8AZkGcD'
_enc = _pwc.sha256crypt(_password,_salt)
assert(_pwc.checkpw(_password,_salt) == true)
--print(_enc,_salt)


-- sha512crypt $6$, SHA512 (Unix) 2	$6$52450745$k5ka2p8bFuSmoVT1tzOyyuaREkkKBcCNqoDKzYiJL9RaE8yMnPgh2XzzF0NDrUhgrcLwg78xs1w5pJiypEdFX/
_salt = '$6$52450745$k5ka2p8bFuSmoVT1tzOyyuaREkkKBcCNqoDKzYiJL9RaE8yMnPgh2XzzF0NDrUhgrcLwg78xs1w5pJiypEdFX/'
_enc = _pwc.sha512crypt(_password,_salt)
assert(_pwc.checkpw(_password,_salt) == true)
--print(_enc,_salt)


-- shacrypt, SHA1 (LDAP)
_salt = '{sha1}b89eaac7e61417341b710b727768294d0e6a277b'
_enc = _pwc.shacrypt(_password)
assert(_pwc.checkpw(_password,_salt) == true)
--print(_enc,_salt)


-- sha1/base64 uJ6qx+YUFzQbcQtyd2gpTQ5qJ3s
assert(_pwc.sha1_b64(_password) == 'uJ6qx+YUFzQbcQtyd2gpTQ5qJ3s')


-- sha1/base16 b89eaac7e61417341b710b727768294d0e6a277b
assert(_pwc.sha1_hex(_password) == 'b89eaac7e61417341b710b727768294d0e6a277b')


-- sha1($pass.$salt)	2fc5a684737ce1bf7b3b239df432416e0dd07357:2014
assert(_pwc.sha1_hex(_password,'2014') == '2fc5a684737ce1bf7b3b239df432416e0dd07357')


-- b64crypt, SHA1 (LDAP)
_salt = '{b64}aGFzaGNhdA'
_enc = _pwc.b64crypt(_password)
assert(_pwc.checkpw(_password,_salt) == true)
--print(_enc,_salt)

