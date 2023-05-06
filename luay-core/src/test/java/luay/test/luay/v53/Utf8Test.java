package luay.test.luay.v53;

import luay.test.luay.luayTestCase;
import org.junit.jupiter.api.Test;

public class Utf8Test extends luayTestCase
{

    @Test
    void testMain()
    {
        testScript("luay/v53/utf8.lua");
    }

    public static void main(String[] args) {
        Utf8Test _ht = new Utf8Test();
        _ht.setUp();
        _ht.testMain();
    }
}
