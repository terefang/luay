
## snmp

SNMP.

### Functions
*   `snmp.oid( i1 [, ..., iN]) -> oidstring`
*   `snmp.snmpv2( address [, port [, community]]) -> session`
*   `snmp.close(session)`
*   `snmp.closeall()`
*   `snmp.getall(session, timeout, oid [, ..., oid]) -> list<table>`
*   `snmp.get(session, oid [, timeout]) -> table`
*   `snmp.next(session, oid [, timeout]) -> table`
*   `snmp.walk(session, oid [, timeout [, quiteness]]) -> list<table>`

### return table

* /1/ – oid
* /2/ – type-id
* /3/ – value.toString
* /4/ – snmpToLuaValue


### SMI Type Ids

*   `snmp.SMI_INTEGER`
*   `snmp.SMI_STRING`
*   `snmp.SMI_OBJECTID`
*   `snmp.SMI_NULL`
*   `snmp.SMI_APPSTRING`
*   `snmp.SMI_IPADDRESS`
*   `snmp.SMI_COUNTER32`
*   `snmp.SMI_GAUGE32`
*   `snmp.SMI_UNSIGNED32`
*   `snmp.SMI_TIMETICKS`
*   `snmp.SMI_OPAQUE`
*   `snmp.SMI_COUNTER64`
*   `snmp.SMI_NOSUCHOBJECT`
*   `snmp.SMI_NOSUCHINSTANCE`
*   `snmp.SMI_ENDOFMIBVIEW`


### basic sys oids

*   `snmp.OID_sysDescr`
*   `snmp.OID_sysOid`
*   `snmp.OID_sysUptime`
*   `snmp.OID_sysContact`
*   `snmp.OID_sysName`
*   `snmp.OID_sysLocation`


### iftable oids

*   `snmp.OID_ifNumber`
*   `snmp.OID_ifIndex`
*   `snmp.OID_ifDescr`
*   `snmp.OID_ifType`
*   `snmp.OID_ifMtu`
*   `snmp.OID_ifSpeed`
*   `snmp.OID_ifPhysAddress`
*   `snmp.OID_ifAdminStatus`
*   `snmp.OID_ifOperStatus`
*   `snmp.OID_ifLastChange`
*   `snmp.OID_ifInOctets`
*   `snmp.OID_ifInUcastPkts`
*   `snmp.OID_ifInNUcastPkts`
*   `snmp.OID_ifInDiscards`
*   `snmp.OID_ifInErrors`
*   `snmp.OID_ifInUnknownProtos`
*   `snmp.OID_ifOutOctets`
*   `snmp.OID_ifOutUcastPkts`
*   `snmp.OID_ifOutNUcastPkts`
*   `snmp.OID_ifOutDiscards`
*   `snmp.OID_ifOutErrors`
*   `snmp.OID_ifOutQLen`
*   `snmp.OID_ifSpecific`


\pagebreak

