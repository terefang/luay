package luay.test.luay;


import luay.test.luayTestCase;
import org.junit.jupiter.api.Test;

public class GetoptTest extends luayTestCase
{
    @Test
    void testMain()
    {
        testScript("luay/ext/getopt.lua");
    }

    public static void main(String[] args) {
        GetoptTest _ht = new GetoptTest();
        _ht.setUp();
        _ht.testMain();
    }
}
