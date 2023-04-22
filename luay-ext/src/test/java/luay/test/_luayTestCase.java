package luay.test;

import luay.main.LuayBuilder;
import luay.main.LuayContext;
import org.junit.jupiter.api.BeforeEach;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public abstract class _luayTestCase
{
    protected LuayContext luayContext;

    @BeforeEach
    protected void setUp() {
        this.luayContext = LuayBuilder.create()
                .inputStream(System.in)
                .outputStream(System.out)
                .errorStream(System.err)
                .allLibraries()
                .build();
    }

    protected void testScript(String _name)
    {
        testScript(_name, null);
    }

    protected void testScript(String _name, String k, Object v, Object... map)
    {
        Map<String, Object> _vars = new HashMap<>();
        _vars.put(k,v);
        if(map!=null && map.length>0 && map.length%2==0)
        {
            for(int _i = 0; _i<map.length; _i+=2)
            {
                _vars.put(map[_i].toString(),map[_i+1]);
            }
        }
        testScript(_name, _vars);
    }

    protected void testScript(String _name, Map<String, Object> _vars)
    {
        this.luayContext.compileScript(getStream(_name), _name);

        if(_vars!=null)
        {
            _vars.forEach((k,v)->{ this.luayContext.set(k, v); });
        }


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

    public static InputStream getStream(String _file)
    {
        return _luayTestCase.class.getClassLoader().getResourceAsStream(_file);
    }
}
