## LuaZDF inspired Libraries

### array

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

### map

```lua
local map = require('map');
```

*   `map.map (k1,v1[, ..., kN, vN]) -> map-only-table` \
    Function to collect all kv-pairs into a map-only table.

* `map.collectk (table[, ..., tableN]) -> list(k1, ..., kX)` \
    Function to collect all keys for the list of tables.

*   `map.collectv (table[, ..., tableN]) -> list(v1, ..., vX)` \
    Function to collect all values for the list of tables.

*   `map.collect (table[, ..., tableN]) -> table` \
    Function to collect all k,v for the list of tables.
    
*   `map.count (table, fv) -> table` \
    Sorts a list into groups and returns a count for the number of objects in 
    each group. Similar to groupBy, but instead of returning a list of values, 
    returns a count for the number of values in that group.

\pagebreak
