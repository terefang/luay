package luay.test;

import org.junit.jupiter.api.Test;

public class DaoTest extends luayTestCase
{

    @Test
    void testSqlite()
    {
        testScript("luay/sqlite.lua");
    }

    public static void main(String[] args) {
        DaoTest _ht = new DaoTest();
        _ht.setUp();
        _ht.testSqlite();
    }
}
