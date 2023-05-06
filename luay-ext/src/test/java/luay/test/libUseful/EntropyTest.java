package luay.test.libUseful;

import luay.test.luayTestCase;
import org.junit.jupiter.api.Test;

public class EntropyTest extends luayTestCase
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