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
