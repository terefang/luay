package luay.test;

import luay.test._luayTestCase;
import org.junit.jupiter.api.Test;

public class HttpTest extends _luayTestCase
{

    @Test
    void testMain()
    {
        testScript("luay/ext/http.lua", "_var2", 2);
    }

    public static void main(String[] args) {
        HttpTest _ht = new HttpTest();
        _ht.setUp();
        _ht.testMain();
    }
}
