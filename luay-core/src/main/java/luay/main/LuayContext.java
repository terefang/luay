package luay.main;

import lombok.SneakyThrows;
import luay.vm.LuaTable;
import luay.vm.LuaValue;
import luay.vm.Prototype;
import luay.vm.lib.java.CoerceJavaToLua;
import luay.vm.lib.java.CoerceLuaToJava;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LuayContext {
    private final LuayGlobal globals;

    Map<String,Object> top = new HashMap<>();
    private Prototype script;
    private String scriptName;

    public LuayContext(LuayGlobal _globals) {
        this.globals = _globals;
    }

    public void setOutputStream(OutputStream _out)
    {
        globals.STDOUT=(_out!=null) ? new PrintStream(_out) : null;
    }

    public void set(String key, Object val)
    {
        this.top.put(key, val);
    }

    public void setAll(Map<String, Object> _map) {
        this.top.putAll(_map);
    }

    public void unset(String key)
    {
        this.top.remove(key);
    }

    public void unsetAll()
    {
        this.top.clear();
    }

    @SneakyThrows
    public Object execute(File _file)
    {
        return execute(new FileReader(_file), _file.getName());
    }

    public Object execute(InputStream _stream, String _name)
    {
        return execute(new InputStreamReader(_stream, StandardCharsets.UTF_8), _name);
    }

    public Object execute(String _script, String _name)
    {
        return execute(new StringReader(_script), _name);
    }

    public Object execute(Reader _reader, String _name)
    {
        try
        {
            LuaTable _env = this.globals;
            LuayGlobal.setContext(this.globals);
            LuayGlobal.setEnv(_env);

            for(Map.Entry<String, Object> _entry : top.entrySet())
            {
                _env.set(_entry.getKey(), CoerceJavaToLua.coerce(_entry.getValue()));
            }

            LuaValue _chunk = this.globals.load(_reader, _name, _env);

            LuaValue _ret = _chunk.call();

            if(_ret.isnil()) return null;

            return CoerceLuaToJava.coerce(_ret, Object.class);
        }
        finally
        {
            LuayGlobal.removeEnv();
            LuayGlobal.removeContext();
        }
    }

    @SneakyThrows
    public void compileScript(File _file)
    {
        compileScript(new FileReader(_file), _file.getName());
    }

    public void compileScript(InputStream _stream, String _name)
    {
        compileScript(new InputStreamReader(_stream, StandardCharsets.UTF_8), _name);
    }

    public void compileScript(String _script, String _name)
    {
        compileScript(new StringReader(_script), _name);
    }

    @SneakyThrows
    public void compileScript(Reader _reader, String _name)
    {
        this.script = this.globals.compilePrototype(_reader, _name);
        this.scriptName = _name;
    }

    @SneakyThrows
    public Object runScript() { return runScript(Collections.emptyMap()); }


    @SneakyThrows
    public Object runScript(Map<String,Object> _top)
    {
        LuaTable _env = this.globals;

        try
        {
            for(Map.Entry<String, Object> _entry : this.top.entrySet())
            {
                _env.set(_entry.getKey(), CoerceJavaToLua.coerce(_entry.getValue()));
            }

            for(Map.Entry<String, Object> _entry : _top.entrySet())
            {
                _env.set(_entry.getKey(), CoerceJavaToLua.coerce(_entry.getValue()));
            }

            return runScript(_env);
        }
        finally
        {
            for(Map.Entry<String, Object> _entry : _top.entrySet())
            {
                _env.set(_entry.getKey(), LuaValue.NIL);
            }

            for(Map.Entry<String, Object> _entry : this.top.entrySet())
            {
                _env.set(_entry.getKey(), LuaValue.NIL);
            }
        }
    }

    @SneakyThrows
    public Object runScript(LuaTable _env)
    {
        try
        {
            LuayGlobal.setContext(this.globals);
            LuayGlobal.setEnv(_env);

            LuaValue _chunk = this.globals.loader.load(this.script, this.scriptName, _env);

            LuaValue _ret = _chunk.call();

            if(_ret.isnil()) return null;

            return CoerceLuaToJava.coerce(_ret, Object.class);
        }
        finally
        {
            LuayGlobal.removeEnv();
            LuayGlobal.removeContext();
        }
    }

}
