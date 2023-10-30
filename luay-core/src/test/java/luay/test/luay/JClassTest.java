package luay.test.luay;

import luay.test.luayTestCase;
import org.junit.jupiter.api.Test;

public class JClassTest extends luayTestCase
{

    @Test
    void testJClass()
    {
        testScript("luay/jclass.lua");
    }

    public static void main(String[] args) {
        JClassTest _ht = new JClassTest();
        _ht.setUp();
        _ht.testJClass();
    }
}
