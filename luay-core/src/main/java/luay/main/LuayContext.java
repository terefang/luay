package luay.main;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import luay.vm.*;
import luay.vm.lib.PackageLib;
import luay.vm.lib.java.CoerceJavaToLua;
import luay.vm.lib.java.CoerceLuaToJava;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LuayContext {
    private final LuayGlobal globals;

    Map<String,Object> top = new HashMap<>();
    private Prototype script;
    private String scriptName;

    private String basePath;
    private String baseFile;

    public LuayContext(LuayGlobal _globals)
    {
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
        setBase(_file);
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

            _env.set("__FILE__", this.baseFile==null ? LuaValue.NIL : LuaString.valueOf(this.baseFile));
            _env.set("__PATH__", this.basePath==null ? LuaValue.NIL : LuaString.valueOf(this.basePath));

            checkBaseIncludePath(this.basePath, this.globals);

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

    private void checkBaseIncludePath(String _basePath, LuayGlobal _globals)
    {
        StringBuilder _sb = new StringBuilder();
        if(_basePath!=null)
        {
            _sb.append(_basePath+"/?.lua;");
            _sb.append(_basePath+"/?/init.lua");
            String _spath = _globals.getPackageLib().getLuaPath();
            if(!_spath.startsWith(_sb.toString()))
            {
                _globals.getPackageLib().addLuaPath(_sb.toString());
            }
        }
    }

    public void setBase(File _file)
    {
        this.baseFile = _file == null ? null : _file.getAbsolutePath();
        this.basePath = _file == null ? null : (_file.getParentFile() == null ? null : _file.getParentFile().getAbsolutePath());
    }

    @SneakyThrows
    public void compileScript(File _file)
    {
        setBase(_file);
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

            _env.set("__FILE__", this.baseFile==null ? LuaValue.NIL : LuaString.valueOf(this.baseFile));
            _env.set("__PATH__", this.basePath==null ? LuaValue.NIL : LuaString.valueOf(this.basePath));

            checkBaseIncludePath(this.basePath, this.globals);

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
        catch(LuaError _le)
        {
            log.error(_le.getMessage(),_le);
            return _le;
        }
        catch(Throwable _th)
        {
            log.error(_th.getMessage());
            return _th;
        }
        finally
        {
            LuayGlobal.removeEnv();
            LuayGlobal.removeContext();
        }
    }

}
