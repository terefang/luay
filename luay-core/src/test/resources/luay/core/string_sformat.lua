-- local _string = require('string');
-- already a base library

assert(string.sformat("%d", -1000)=="-1000");
-- TEST_CASE("space flag", "[]" )
assert(string.sformat("% d", 42)==" 42");
assert(string.sformat("% d", -42)=="-42");
assert(string.sformat("% 5d", 42)=="   42");
assert(string.sformat("% 5d", -42)=="  -42");
assert(string.sformat("% 15d", 42)=="             42");
assert(string.sformat("% 15d", -42)=="            -42");
assert(string.sformat("% 15d", -42)=="            -42");
assert(string.sformat("% 15.3f", -42.987)=="        -42.987");
assert(string.sformat("% 15.3f", 42.987)=="         42.987");
--assert(string.sformat("% s", "Hello testing")=="Hello testing");
assert(string.sformat("% d", 1024)==" 1024");
assert(string.sformat("% d", -1024)=="-1024");
--assert(string.sformat("% i", 1024)==" 1024");
--assert(string.sformat("% i", -1024)=="-1024");
--assert(string.sformat("% u", 1024)=="1024");
--assert(string.sformat("% u", 4294966272)=="4294966272");
--assert(string.sformat("% o", 511)=="777");
--assert(string.sformat("% o", 4294966785)=="37777777001");
--assert(string.sformat("% x", 305441741)=="1234abcd");
--assert(string.sformat("% x", 3989525555)=="edcb5433");
--assert(string.sformat("% X", 305441741)=="1234ABCD");
--assert(string.sformat("% X", 3989525555)=="EDCB5433");
--assert(string.sformat("% c", 'x')=="x");

-- TEST_CASE("+ flag", "[]" ) {
assert(string.sformat("%+d", 42)=="+42");
assert(string.sformat("%+d", -42)=="-42");
assert(string.sformat("%+5d", 42)=="  +42");
assert(string.sformat("%+5d", -42)=="  -42");
assert(string.sformat("%+15d", 42)=="            +42");
assert(string.sformat("%+15d", -42)=="            -42");
--assert(string.sformat("%+s", "Hello testing")=="Hello testing");
assert(string.sformat("%+d", 1024)=="+1024");
assert(string.sformat("%+d", -1024)=="-1024");
--assert(string.sformat("%+i", 1024)=="+1024");
--assert(string.sformat("%+i", -1024)=="-1024");
--assert(string.sformat("%+u", 1024)=="1024");
--assert(string.sformat("%+u", 4294966272)=="4294966272");
--assert(string.sformat("%+o", 511)=="777");
--assert(string.sformat("%+o", 4294966785)=="37777777001");
--assert(string.sformat("%+x", 305441741)=="1234abcd");
--assert(string.sformat("%+x", 3989525555)=="edcb5433");
--assert(string.sformat("%+X", 305441741)=="1234ABCD");
--assert(string.sformat("%+X", 3989525555)=="EDCB5433");
--assert(string.sformat("%+c", 'x')=="x");
--assert(string.sformat("%+.0d", 0)=="+");

-- TEST_CASE("0 flag", "[]" ) {
--assert(string.sformat("%0d", 42)=="42");
--assert(string.sformat("%0ld", 42)=="42");
--assert(string.sformat("%0d", -42)=="-42");
assert(string.sformat("%05d", 42)=="00042");
assert(string.sformat("%05d", -42)=="-0042");
assert(string.sformat("%015d", 42)=="000000000000042");
assert(string.sformat("%015d", -42)=="-00000000000042");
assert(string.sformat("%015.2f", 42.1234)=="000000000042.12");
assert(string.sformat("%015.3f", 42.9876)=="00000000042.988");
assert(string.sformat("%015.5f", -42.9876)=="-00000042.98760");

-- TEST_CASE("- flag", "[]" ) {
--assert(string.sformat("%-d", 42)=="42");
--assert(string.sformat("%-d", -42)=="-42");
assert(string.sformat("%-5d", 42)=="42   ");
assert(string.sformat("%-5d", -42)=="-42  ");
assert(string.sformat("%-15d", 42)=="42             ");
assert(string.sformat("%-15d", -42)=="-42            ");
--assert(string.sformat("%-0d", 42)=="42");
--assert(string.sformat("%-0d", -42)=="-42");
--assert(string.sformat("%-05d", 42)=="42   ");
--assert(string.sformat("%-05d", -42)=="-42  ");
--assert(string.sformat("%-015d", 42)=="42             ");
--assert(string.sformat("%-015d", -42)=="-42            ");
--assert(string.sformat("%0-d", 42)=="42");
--assert(string.sformat("%0-d", -42)=="-42");
--assert(string.sformat("%0-5d", 42)=="42   ");
--assert(string.sformat("%0-5d", -42)=="-42  ");
--assert(string.sformat("%0-15d", 42)=="42             ");
--assert(string.sformat("%0-15d", -42)=="-42            ");
--assert(string.sformat("%0-15.3e", -42.)=="-4.200e+01     ");
--assert(string.sformat("%0-15.3g", -42.)=="-42.0          ");

-- TEST_CASE("# flag", "[]" ) {
--assert(string.sformat("%#.0x", 0)=="");
--assert(string.sformat("%#.1x", 0)=="0");
--assert(string.sformat("%#.8x", 0x614e)=="0x0000614e");
--assert(string.sformat("%#b", 6)=="0b110");



-- TEST_CASE("specifier", "[]" ) {
assert(string.sformat("Hello testing")=="Hello testing");
assert(string.sformat("%s", "Hello testing")=="Hello testing");
assert(string.sformat("%d", 1024)=="1024");
assert(string.sformat("%d", -1024)=="-1024");
--assert(string.sformat("%i", 1024)=="1024");
--assert(string.sformat("%i", -1024)=="-1024");
--assert(string.sformat("%u", 1024)=="1024");
--assert(string.sformat("%u", 4294966272)=="4294966272");
assert(string.sformat("%o", 511)=="777");
assert(string.sformat("%o", string.to_long("4294966785"))=="37777777001");
assert(string.sformat("%x", 305441741)=="1234abcd");
assert(string.sformat("%x", string.to_long("3989525555"))=="edcb5433");
assert(string.sformat("%X", 305441741)=="1234ABCD");
assert(string.sformat("%X", string.to_long("3989525555"))=="EDCB5433");
assert(string.sformat("%%")=="%");

-- TEST_CASE("width", "[]" ) {
assert(string.sformat("%1s", "Hello testing")=="Hello testing");
assert(string.sformat("%1d", 1024)=="1024");
assert(string.sformat("%1d", -1024)=="-1024");
--assert(string.sformat("%1i", 1024)=="1024");
--assert(string.sformat("%1i", -1024)=="-1024");
--assert(string.sformat("%1u", 1024)=="1024");
----assert(string.sformat("%1u", 4294966272)=="4294966272");
assert(string.sformat("%1o", 511)=="777");
assert(string.sformat("%1o", string.to_long("4294966785"))=="37777777001");
assert(string.sformat("%1x", 305441741)=="1234abcd");
assert(string.sformat("%1x", string.to_long("3989525555"))=="edcb5433");
assert(string.sformat("%1X", 305441741)=="1234ABCD");
assert(string.sformat("%1X", string.to_long("3989525555"))=="EDCB5433");
--assert(string.sformat("%1c", 'x')=="x");


-- TEST_CASE("width 20", "[]" ) {
assert(string.sformat("%20s", "Hello")=="               Hello");
assert(string.sformat("%20d", 1024)=="                1024");
assert(string.sformat("%20d", -1024)=="               -1024");
--assert(string.sformat("%20i", 1024)=="                1024");
--assert(string.sformat("%20i", -1024)=="               -1024");
--assert(string.sformat("%20u", 1024)=="                1024");
--assert(string.sformat("%20u", 4294966272)=="          4294966272");
assert(string.sformat("%20o", 511)=="                 777");
assert(string.sformat("%20o", string.to_long("4294966785"))=="         37777777001");
assert(string.sformat("%20x", 305441741)=="            1234abcd");
assert(string.sformat("%20x", string.to_long("3989525555"))=="            edcb5433");
assert(string.sformat("%20X", 305441741)=="            1234ABCD");
assert(string.sformat("%20X", string.to_long("3989525555"))=="            EDCB5433");
--assert(string.sformat("%20c", 'x')=="                   x");

-- TEST_CASE("width -20", "[]" ) {
assert(string.sformat("%-20s", "Hello")=="Hello               ");
assert(string.sformat("%-20d", 1024)=="1024                ");
assert(string.sformat("%-20d", -1024)=="-1024               ");
--assert(string.sformat("%-20i", 1024)=="1024                ");
--assert(string.sformat("%-20i", -1024)=="-1024               ");
--assert(string.sformat("%-20u", 1024)=="1024                ");
assert(string.sformat("%-20.4f", 1024.1234)=="1024.1234           ");
--assert(string.sformat("%-20u", 4294966272)=="4294966272          ");
assert(string.sformat("%-20o", 511)=="777                 ");
assert(string.sformat("%-20o", string.to_long("4294966785"))=="37777777001         ");
assert(string.sformat("%-20x", 305441741)=="1234abcd            ");
assert(string.sformat("%-20x", string.to_long("3989525555"))=="edcb5433            ");
assert(string.sformat("%-20X", 305441741)=="1234ABCD            ");
assert(string.sformat("%-20X", string.to_long("3989525555"))=="EDCB5433            ");
--assert(string.sformat("%-20c", 'x')=="x                   ");
assert(string.sformat("|%5d| |%-2d| |%5d|", 9, 9, 9)=="|    9| |9 | |    9|");
assert(string.sformat("|%5d| |%-2d| |%5d|", 10, 10, 10)=="|   10| |10| |   10|");
assert(string.sformat("|%5d| |%-12d| |%5d|", 9, 9, 9)=="|    9| |9           | |    9|");
assert(string.sformat("|%5d| |%-12d| |%5d|", 10, 10, 10)=="|   10| |10          | |   10|");

-- TEST_CASE("padding 20", "[]" ) {
assert(string.sformat("%020d", 1024)=="00000000000000001024");
assert(string.sformat("%020d", -1024)=="-0000000000000001024");
--assert(string.sformat("%020i", 1024)=="00000000000000001024");
--assert(string.sformat("%020i", -1024)=="-0000000000000001024");
--assert(string.sformat("%020u", 1024)=="00000000000000001024");
--assert(string.sformat("%020u", 4294966272)=="00000000004294966272");
assert(string.sformat("%020o", 511)=="00000000000000000777");
assert(string.sformat("%020o", string.to_long("4294966785"))=="00000000037777777001");
assert(string.sformat("%020x", 305441741)=="0000000000001234abcd");
assert(string.sformat("%020x", string.to_long("3989525555"))=="000000000000edcb5433");
assert(string.sformat("%020X", 305441741)=="0000000000001234ABCD");
assert(string.sformat("%020X", string.to_long("3989525555"))=="000000000000EDCB5433");

-- TEST_CASE("padding neg numbers", "[]" ) {
-- // space padding
assert(string.sformat("% 1d", -5)=="-5");
assert(string.sformat("% 2d", -5)=="-5");
assert(string.sformat("% 3d", -5)==" -5");
assert(string.sformat("% 4d", -5)=="  -5");
-- // zero padding
assert(string.sformat("%01d", -5)=="-5");
assert(string.sformat("%02d", -5)=="-5");
assert(string.sformat("%03d", -5)=="-05");
assert(string.sformat("%04d", -5)=="-005");

-- TEST_CASE("float padding neg numbers", "[]" ) {
-- // space padding
assert(string.sformat("% 3.1f", -5.1)=="-5.1");
assert(string.sformat("% 4.1f", -5.1)=="-5.1");
assert(string.sformat("% 5.1f", -5.1)==" -5.1");
assert(string.sformat("% 6.1g", -5.1)=="    -5");
assert(string.sformat("% 6.1e", -5.1)=="-5.1e+00");
assert(string.sformat("% 10.1e", -5.1)=="  -5.1e+00");
-- // zero padding
assert(string.sformat("%03.1f", -5.1)=="-5.1");
assert(string.sformat("%04.1f", -5.1)=="-5.1");
assert(string.sformat("%05.1f", -5.1)=="-05.1");
-- // zero padding no decimal point
assert(string.sformat("%01.0f", -5.1)=="-5");
assert(string.sformat("%02.0f", -5.1)=="-5");
assert(string.sformat("%03.0f", -5.1)=="-05");

assert(string.sformat("%010.1e", -5.1)=="-005.1e+00");
assert(string.sformat("%07.0E", -5.1)=="-05E+00");
assert(string.sformat("%03.0g", -5.1)=="-05");


-- // this testcase checks, that the precision is truncated AND rounded to 9 digits.
-- // a perfect working float should return the whole number
assert(string.sformat("%.12f", 42.89522387654321)=="42.895223876543");
assert(string.sformat("%6.2f", 42.8952)==" 42.90");
assert(string.sformat("%+6.2f", 42.8952)=="+42.90");
assert(string.sformat("%+5.1f", 42.9252)=="+42.9");
assert(string.sformat("%f", 42.5)=="42.500000");
assert(string.sformat("%.1f", 42.5)=="42.5");
assert(string.sformat("%f", string.to_double(42167.0))=="42167.000000");
assert(string.sformat("%.9f", -12345.987654321)=="-12345.987654321");
assert(string.sformat("%.1f", 3.999)=="4.0");
assert(string.sformat("%.0f", 3.5)=="4");
assert(string.sformat("%.0f", 4.5)=="5");
assert(string.sformat("%.0f", 3.49)=="3");
assert(string.sformat("%.1f", 3.49)=="3.5");
assert(string.sformat("a%-5.1f", 0.5)=="a0.5  ");
assert(string.sformat("a%-5.1fend", 0.5)=="a0.5  end");

-- #ifndef PRINTF_DISABLE_SUPPORT_EXPONENTIAL
assert(string.sformat("%G", 12345.678)=="12345.7");
assert(string.sformat("%.7G", 12345.678)=="12345.68");
assert(string.sformat("%.5G", string.to_double(123456789))=="1.2346E+08");
assert(string.sformat("%.6G", string.to_double(12345))=="12345.0");
assert(string.sformat("%+12.4g", string.to_double(123456789))=="  +1.235e+08");
assert(string.sformat("%.2G", 0.001234)=="0.0012");
assert(string.sformat("%+10.4G", 0.001234)==" +0.001234");
assert(string.sformat("%+012.4g", 0.00001234)=="+001.234e-05");
assert(string.sformat("%.3g", -1.2345e-308)=="-1.23e-308");
assert(string.sformat("%+.3E", 1.23e+308)=="+1.230E+308");

-- // out of range for float: should switch to exp notation if supported, else empty
assert(string.sformat("%.1f", string.to_double(1E20))=="100000000000000000000.0");

-- TEST_CASE("types", "[]" ) {
-- assert(string.sformat("%b", 60000)=="1110101001100000");
-- assert(string.sformat("%lb", 12345678)=="101111000110000101001110");
-- assert(string.sformat("%o", 60000)=="165140");
-- assert(string.sformat("%lo", 12345678)=="57060516");
-- assert(string.sformat("%lx", 0x12345678)=="12345678");
-- assert(string.sformat("%llx", 0x1234567891234567LL)=="1234567891234567");
-- assert(string.sformat("%lx", 0xabcdefab)=="abcdefab");
-- assert(string.sformat("%lX", 0xabcdefab)=="ABCDEFAB");
-- assert(string.sformat("%s", "A Test")=="A Test");
-- assert(string.sformat("%hhu", 0xFFFF)=="255");
-- assert(string.sformat("%hu", 0x123456)=="13398");
-- assert(string.sformat("%s%hhi %hu", "Test", 10000, 0xFFFFFFFF)=="Test16 65535");
-- assert(string.sformat("%tx", &buffer[10] - &buffer[0])=="a");
--
-- assert(string.sformat("%ji", -2147483647)=="-2147483647");
--
-- TEST_CASE("string length", "[]" ) {
-- assert(string.sformat("%.4s", "This is a test")=="This");
-- assert(string.sformat("%.4s", "test")=="test");
-- assert(string.sformat("%.7s", "123")=="123");
-- assert(string.sformat("%.7s", "")=="");
-- assert(string.sformat("%.4s%.2s", "123456", "abcdef")=="1234ab");
-- assert(string.sformat("%.4.2s", "123456")==".2s");
-- assert(string.sformat("%.*s", 3, "123456")=="123");
--
-- assert(string.sformat("%u%u%ctest%d %s", 5, 3000, 'a', -20, "bit")=="53000atest-20 bit");
-- assert(string.sformat("%.*f", 2, 0.33333333)=="0.33");
-- assert(string.sformat("%.*d", -1, 1)=="1");
-- assert(string.sformat("%.3s", "foobar")=="foo");
-- assert(string.sformat("% .0d", 0)==" ");
-- assert(string.sformat("%10.5d", 4)=="     00004");
-- assert(string.sformat("%*sx", -3, "hi")=="hi x");
-- assert(string.sformat("%.*g", 2, 0.33333333)=="0.33");
-- assert(string.sformat("%.*e", 2, 0.33333333)=="3.33e-01");
--
--