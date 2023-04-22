package luay.test.luay.libUseful;

import luay.test.luay.BaseTest;
import luay.test.luay._luayTestCase;
import org.junit.jupiter.api.Test;

public class EntropyTest extends _luayTestCase
{

    @Test
    void testMain()
    {
        testScript("luay/libUseful/entropy.lua", "_var", 20);
    }

    public static void main(String[] args) {
        EntropyTest _ht = new EntropyTest();
        _ht.setUp();
        _ht.testMain();
    }
}