package luay.test;

import org.junit.jupiter.api.Test;

public class SnmpTest extends luayTestCase
{

    @Test
    void testMain()
    {
        testScript("luay/ext/snmp.lua", "_var2", 2);
    }

    public static void main(String[] args) {
        SnmpTest _ht = new SnmpTest();
        _ht.setUp();
        _ht.testMain();
    }
}
