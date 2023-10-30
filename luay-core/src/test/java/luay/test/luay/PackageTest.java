package luay.test.luay;


import luay.test.luayTestCase;
import org.junit.jupiter.api.Test;

public class PackageTest extends luayTestCase
{
    @Test
    void testMain()
    {
        testScript("luay/ext/package.lua");
    }

    public static void main(String[] args) {
        PackageTest _ht = new PackageTest();
        _ht.setUp();
        _ht.testMain();
    }
}
