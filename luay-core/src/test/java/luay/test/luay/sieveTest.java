package luay.test.luay;

import luay.test.luayTestCase;
import org.junit.jupiter.api.Test;

import java.util.List;

import static luay.lib.ext.AbstractLibrary.toList;

public class sieveTest extends luayTestCase
{
    static List<String> _list = toList("perf/sieve1.lua", "perf/sieve2.lua");
    @Test
    void test()
    {
        for(String _s : _list)
        {
            testScript(_s);
        }
    }


    public static void main(String[] args) {
        sieveTest _ht = new sieveTest();
        _ht.setUp();
        _ht.test();
        _ht.test();
    }
}
