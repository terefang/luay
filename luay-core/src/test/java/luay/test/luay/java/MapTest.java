package luay.test.luay.java;


import luay.test.luay._luayTestCase;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class MapTest extends _luayTestCase
{
    @Test
    void testMain()
    {
        Map<String,String> _list = new HashMap<>();
        for(int _i=0; _i<256;_i++)
        {
            String _x = Integer.toHexString(_i);
            _list.put(_x,_x);
        }
        testScript("luay/java/map.lua", "_var", _list);
    }

    public static void main(String[] args) {
        MapTest _ht = new MapTest();
        _ht.setUp();
        _ht.testMain();
    }
}
