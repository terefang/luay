package luay.test.luay;

import org.junit.jupiter.api.Test;

public class BaseTest extends luayTestCase
{

    @Test
    void testMain()
    {
        testScript("luay/base.lua", "_var2", 2);
    }

    public static void main(String[] args) {
        BaseTest _ht = new BaseTest();
        _ht.setUp();
        _ht.testMain();
    }
}
