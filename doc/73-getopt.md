## getopt

option processing for lists/arrays.

### Functions

*   `getopt.std( pattern, arglist ) -> map, list` \
    roughly emulates single character mode of getopt(3).
    * option processing always stops at a standalone double-dash (ie. '--').
    * option characters followed by a colon (ie. ':'), the option takes an argument
    * if pattern is prefixed by a dash (ie. '-'), invalid options will be ignored
    * if pattern is prefixed by a colon (ie. ':'), invalid options will be processed as zero-arg options
    * if pattern is prefixed by a plus (ie. '+'), the first non-option argument will stop processing
    * short options can be abbreviated like 
      * 'f:vx' on `-fvx file` -> `f=file, v=true, x=true`
      * 'f:vx' on `-vfx file` -> `f=file, v=true, x=true`
      * 'f:vx' on `-vxf file` -> `f=file, v=true, x=true`

* `getopt.long( [ pattern,] optionmap, arglist ) -> map, list` \
    non-standard long-option processiog in addition or instead of getopt(3).
    * optionmap is given as `map('zero-arg-option',0,'arg-option',1)`   
    * invalid options cause error.
    * option-arguments can be given as `--zero-arg-option` or `--arg-option option-value` or `--arg-option=option-value`

* `getopt.simple( arglist ) -> map, list` \
    hard-wired option rules processing.
    * option processing always stops at a standalone double-dash (ie. '--').
    * option processing always stops at a standalone dash (ie. '-').
    * option processing always stops at the first non-option argument.
    * short options are zero-arg
    * short options cannot be abbreviated, the arg option start immediately like `-ffile -v -x` -> `f=file, v=true, x=true`
    * if a single-dash option string includes an equal sign, it is parsed like `-f=file -file=file` -> `f=file, file=file`
    * short options starting with a plus (ie. '+') are inverted like `-v +x` -> `v=true, x=false`
    * long options are always parsed as `--zero-arg-option` or `--arg-option=option-value`


* `getopt.stdext( pattern, arglist ) -> list<pair>, list`
* `getopt.longext( [ pattern,] optionmap, arglist ) -> list<pair>, list`
* `getopt.simplext( arglist ) -> list<pair>, list`

\pagebreak