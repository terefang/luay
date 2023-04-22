package luay.main;

import lombok.SneakyThrows;
import luay.vm.LuaTable;
import luay.vm.LuaValue;
import luay.vm.Prototype;
import luay.vm.lib.java.CoerceJavaToLua;
import luay.vm.lib.java.CoerceLuaToJava;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LuayContext {
    private final LuayGlobals globals;

    Map<String,Object> top = new HashMap<>();
    private Prototype script;
    private String scriptName;

    public LuayContext(LuayGlobals _globals) {
        this.globals = _globals;
    }

    public void set(String key, Object val)
    {
        this.top.put(key, val);
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
    public Object execute(File _file, boolean _useLocal)
    {
        return execute(new FileReader(_file), _file.getName(), _useLocal);
    }

    @SneakyThrows
    public Object execute(File _file)
    {
        return execute(new FileReader(_file), _file.getName());
    }

    public Object execute(InputStream _stream, String _name, boolean _useLocal)
    {
        return execute(new InputStreamReader(_stream, StandardCharsets.UTF_8), _name, _useLocal);
    }

    public Object execute(InputStream _stream, String _name)
    {
        return execute(_stream, _name, false);
    }

    public Object execute(String _script, String _name)
    {
        return execute(_script, _name, false);
    }

    public Object execute(String _script, String _name, boolean _useLocal)
    {
        return execute(new StringReader(_script), _name, _useLocal);
    }

    public Object execute(Reader _reader, String _name)
    {
        return execute(_reader, _name, false);
    }

    public Object execute(Reader _reader, String _name, boolean _useLocal)
    {
        LuaTable _env = this.globals;
        if(_useLocal)
        {
            _env = this.globals.cloneEnv();
        }

        for(Map.Entry<String, Object> _entry : top.entrySet())
        {
            _env.set(_entry.getKey(), CoerceJavaToLua.coerce(_entry.getValue()));
        }

        LuaValue _chunk = this.globals.load(_reader, _name, _env);

        LuaValue _ret = _chunk.call();

        return CoerceLuaToJava.coerce(_ret, Object.class);
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
    public Object runScript() { return runScript(false); }

    @SneakyThrows
    public Object runScript(boolean _useLocal) {
        LuaTable _env = this.globals;
        if (_useLocal)
        {
            _env = this.globals.cloneEnv();
        }

        for(Map.Entry<String, Object> _entry : top.entrySet())
        {
            _env.set(_entry.getKey(), CoerceJavaToLua.coerce(_entry.getValue()));
        }

        return runScript(_env);
    }

    @SneakyThrows
    public Object runScript(Map<String,Object> _top) { return runScript(_top, false); }

    @SneakyThrows
    public Object runScript(Map<String,Object> _top, boolean _useLocal)
    {
        LuaTable _env = this.globals;
        if (_useLocal)
        {
            _env = this.globals.cloneEnv();
        }

        for(Map.Entry<String, Object> _entry : _top.entrySet())
        {
            _env.set(_entry.getKey(), CoerceJavaToLua.coerce(_entry.getValue()));
        }

        return runScript(_env);
    }

    @SneakyThrows
    public Object runScript(LuaTable _env)
    {
        LuaValue _chunk = this.globals.loader.load(this.script, this.scriptName, _env);

        LuaValue _ret = _chunk.call();

        return CoerceLuaToJava.coerce(_ret, Object.class);
    }
}
