package luay.test.luay.rocks;


import luay.test.luay._luayTestCase;
import org.junit.jupiter.api.Test;

public class StringyTest  extends _luayTestCase
{
    @Test
    void testMain()
    {
        testScript("luay/rocks/stringy.lua");
    }

    public static void main(String[] args) {
        StringyTest _ht = new StringyTest();
        _ht.setUp();
        _ht.testMain();
    }
}
