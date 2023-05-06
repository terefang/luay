local ldapjndi = require "ldapjndi"

local conn = ldapjndi.connect("ldap://ldap.forumsys.com:389/", "cn=read-only-admin,dc=example,dc=com", "password", "simple", true )

assert(type(conn) == 'userdata')

local _usr = ldapjndi.get(conn,"cn=read-only-admin,dc=example,dc=com");

assert(type(_usr) == 'table')
assert(_usr['cn'][1] == 'read-only-admin')

local _list = ldapjndi.search(conn,"dc=example,dc=com", "uid=*", 'one', 'cn', 'uid');

for _, _u in ipairs(_list) do
  -- print(stringify(_u))
end
assert(#_list == 14)

assert(ldapjndi.authdn(conn, "uid=test,dc=example,dc=com", "*/ignore", "simple") == false)
assert(ldapjndi.authdn(conn, "cn=read-only-admin,dc=example,dc=com", "password", "simple") == true)

ldapjndi.close(conn);