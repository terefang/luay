package luay.vm.luay;

import lombok.SneakyThrows;
import luay.vm.LuaFunction;
import luay.vm.LuaValue;
import luay.vm.lib.java.CoerceJavaToLua;

public class LuaySimpleLibWrapper extends LuaFunction
{
    public static LuaFunction from(String _name, Object _f)
    {
        LuaySimpleLibWrapper _fw = new LuaySimpleLibWrapper();
        _fw._lib = _f;
        _fw._name = _name;
        return _fw;
    }
    Object _lib;
    String _name;

    @Override
    @SneakyThrows
    public LuaValue call(LuaValue modname, LuaValue env)
    {
        LuaValue _llib = CoerceJavaToLua.coerce(this._lib);

        env.set(this._name, _llib);

        if (!env.get("package").isnil()) env.get("package").get("loaded").set(this._name, _llib);

        return _llib;
    }
}
