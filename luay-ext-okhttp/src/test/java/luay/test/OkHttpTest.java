package luay.test;

import org.junit.jupiter.api.Test;

public class OkHttpTest extends luayTestCase
{

    @Test
    void testMain()
    {
        testScript("luay/ext/http.lua", "_var2", 2);
    }

    public static void main(String[] args) {
        OkHttpTest _ht = new OkHttpTest();
        _ht.setUp();
        _ht.testMain();
    }
}
