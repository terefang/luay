## snmp

SNMP.

### Functions
*   `snmp.oid( i1 [, ..., iN]) -> oidstring`
*   `snmp.snmpv2( address [, port [, community]]) -> session`
*   `snmp.close(session)`
*   `snmp.closeall()`
*   `snmp.getall(session, timeout, oid [, ..., oid]) -> tablelist`
*   `snmp.get(session, oid [, timeout]) -> table`
*   `snmp.next(session, oid [, timeout]) -> table`
*   `snmp.walk(session, oid [, timeout [, quiteness]]) -> tablelist`

### return table

* /1/ – oid
* /2/ – type-id
* /3/ – value.toString
* /4/ – snmpToLuaValue
      
\pagebreak