package luay.test.luay.core;

import luay.test.luayTestCase;
import org.junit.jupiter.api.Test;

public class StringTest extends luayTestCase
{
    @Test
    void testMain()
    {
        testScript("luay/core/string.lua", "_var2", 2);
    }

    @Test
    void testsformat()
    {
        testScript("luay/core/string_sformat.lua", "_var2", 2);
    }

    public static void main(String[] args) {
        StringTest _ht = new StringTest();
        _ht.setUp();
        _ht.testMain();
        _ht.testsformat();
    }
}
