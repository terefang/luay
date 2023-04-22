package luay.test.luay.java;


import luay.test.luay._luayTestCase;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Vector;

public class ListTest extends _luayTestCase
{
    @Test
    void testMain()
    {
        List<String> _list = new Vector<>();
        for(int _i=0; _i<256;_i++)
        {
            _list.add(Integer.toHexString(_i));
        }
        testScript("luay/java/list.lua", "_var", _list);
    }

    public static void main(String[] args) {
        ListTest _ht = new ListTest();
        _ht.setUp();
        _ht.testMain();
    }
}
