package luay.test.luay.more;

import luay.test.luayTestCase;
import org.junit.jupiter.api.Test;

public class CryptoTest extends luayTestCase
{

    @Test
    void testMain()
    {
        this.luayContext.compileScript(luayTestCase.getAsStream("luay/more/crypto.lua"), "luay/more/crypto.lua");

        this.luayContext.set("_var", "\u007fS3cr3t789");

        long _t0 = System.currentTimeMillis();
        //for(int _i=0; _i<1024000; _i++)
        {
          //  _ctx.set("_var", _i);
            Object _ret = this.luayContext.runScript();
            this.luayContext.unsetAll();
        }
        long _t1 = System.currentTimeMillis();
        System.out.println("ms = "+(_t1-_t0));
    }


    public static void main(String[] args) {
        CryptoTest _ht = new CryptoTest();
        _ht.setUp();
        _ht.testMain();
    }
}
