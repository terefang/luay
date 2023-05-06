package luay.test.luay;


import org.junit.jupiter.api.Test;

public class StringUtf8Test extends luayTestCase
{
    @Test
    void testMain()
    {
        testScript("luay/ext/stringutf8.lua");
    }

    public static void main(String[] args) {
        StringUtf8Test _ht = new StringUtf8Test();
        _ht.setUp();
        _ht.testMain();
    }
}
