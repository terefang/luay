package luay.test;

import lombok.SneakyThrows;
import luay.main.LuayBuilder;
import luay.main.LuayContext;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public abstract class luayTestCase
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
        //this.luayContext.compileScript(getAsStream(_name), _name);
        this.luayContext.compileScript(getAsFile(_name));

        if(_vars!=null)
        {
            _vars.forEach((k,v)->{ this.luayContext.set(k, v); });
        }


        long _t0 = System.currentTimeMillis();
        try
        {
            Object _ret = this.luayContext.runScript();
            this.luayContext.unsetAll();
            if(_ret instanceof Throwable)
            {
                ((Throwable)_ret).printStackTrace();
            }
        }
        catch (Exception _xe)
        {
            _xe.printStackTrace(System.out);
        }

        long _t1 = System.currentTimeMillis();
        System.out.println("ms = "+(_t1-_t0));
    }

    public static InputStream getAsStream(String _file)
    {
        return luayTestCase.class.getClassLoader().getResourceAsStream(_file);
    }

    @SneakyThrows
    public static File getAsFile(String _file)
    {
        return new File(luayTestCase.class.getClassLoader().getResource(_file).toURI());
    }
}
