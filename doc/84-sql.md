## sql

jdbc access to sql databases.

### Functions
*   `sql.xlsql( hostportorpath) -> connection`
*   `sql.sqlite( hostportorpath, username, password ) -> connection`
*   `sql.mysql( hostportorpath, username, password ) -> connection`
*   `sql.mysql5( hostportorpath, username, password ) -> connection`
*   `sql.oracle( hostportorpath, username, password ) -> connection`
*   `sql.mariadb( hostportorpath, username, password ) -> connection`
*   `sql.mssql( hostportorpath, username, password ) -> connection`
*   `sql.jtds( hostportorpath, username, password ) -> connection`
*   `sql.pgsql( hostportorpath, username, password ) -> connection`

*   `sql.jdbc( type, driver, uri, username, password ) -> connection`

### jdbc database types

`POSTGRES`,  `ORACLE`,  `MSSQL`,  `SYBASE`,  `DB2`,  `H2`,  `SQLITE`,  `CRATE`,  `ANSI`,  `MYSQL`

### Connection object

* `connection:autocommit(boolean)`

* `connection:commit()` 
* `connection:rollback()`

* `connection:execute(sqlquery) -> rc, err`
* `connection:execute(sqlquery, map) -> rc, err`
* `connection:execute(sqlquery, list) -> rc, err`
* `connection:execute(sqlquery, ...) -> rc, err`

* `connection:insert(sqlquery) -> rc, err`
* `connection:insert(sqlquery, map) -> rc, err`
* `connection:insert(sqlquery, list) -> rc, err`
* `connection:insert(sqlquery, ...) -> rc, err`
* 
* `connection:query(sqlquery) -> maplist, err`
* `connection:query(sqlquery, map) -> maplist, err`
* `connection:query(sqlquery, list) -> maplist, err`
* `connection:query(sqlquery, ...) -> maplist, err`

* `connection:close()`

      
\pagebreak