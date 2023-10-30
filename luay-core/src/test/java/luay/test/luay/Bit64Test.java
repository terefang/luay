package luay.test.luay;

import luay.test.luayTestCase;
import org.junit.jupiter.api.Test;

public class Bit64Test extends luayTestCase
{

    @Test
    void testMain()
    {
        testScript("luay/bit64.lua", "_var2", 2);
    }

    public static void main(String[] args) {
        Bit64Test _ht = new Bit64Test();
        _ht.setUp();
        _ht.testMain();
    }
}
