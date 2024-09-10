package luay.test.luay.ext;

import luay.test.luayTestCase;
import org.junit.jupiter.api.Test;

public class BlahTest extends luayTestCase
{
    @Test
    void testMain()
    {
        testScript("luay/ext/blah/test.lua", "_var2", 2);
    }

    public static void main(String[] args) {
        BlahTest _ht = new BlahTest();
        _ht.setUp();
        _ht.testMain();
    }
}
