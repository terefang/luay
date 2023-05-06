package luay.test;

import org.junit.jupiter.api.Test;

public class ZdfTest extends luayTestCase
{

    @Test
    void testArray()
    {
        testScript("luay/zdf/array.lua");
    }

    @Test
    void testMap()
    {
        testScript("luay/zdf/map.lua");
    }

    public static void main(String[] args) {
        ZdfTest _ht = new ZdfTest();
        _ht.setUp();
        _ht.testArray();
        _ht.testMap();
    }
}
