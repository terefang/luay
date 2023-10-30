package luay.test.luay;


import luay.test.luayTestCase;
import org.junit.jupiter.api.Test;

public class LdapJndiTest extends luayTestCase
{
    @Test
    void testMain()
    {
        testScript("luay/ext/ldapjndi.lua");
    }

    public static void main(String[] args) {
        LdapJndiTest _ht = new LdapJndiTest();
        _ht.setUp();
        _ht.testMain();
    }
}
