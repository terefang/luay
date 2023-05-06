package luay.main;

import luay.vm.Globals;
import luay.vm.LuaString;
import luay.vm.LuaTable;
import luay.vm.LuaValue;
import luay.vm.lib.PackageLib;

public class LuayGlobal extends Globals
{
    public PackageLib getPackageLib()
    {
        return this.package_;
    }

    public void setIsLoaded(String name, LuaTable value) {
        getPackageLib().setIsLoaded(name, value);
    }

    static ThreadLocal<LuayGlobal> thlGlobals = new ThreadLocal();

    public static LuaValue getContext()
    {
        LuaValue _g = thlGlobals.get();
        if(_g==null) return LuaValue.NIL;
        return _g;
    }

    public static void setContext(LuayGlobal _g)
    {
        thlGlobals.set(_g);
    }

    public static void removeContext()
    {
        thlGlobals.remove();
    }

    public static boolean hasContext()
    {
        return thlGlobals.get()!=null;
    }

    static ThreadLocal<LuaTable> thlEnvs = new ThreadLocal();

    public static LuaValue getEnv()
    {
        LuaTable _env = thlEnvs.get();
        if(_env==null) return LuaValue.NIL;
        return _env;
    }

    public static void setEnv(LuaTable _g)
    {
        thlEnvs.set(_g);
    }

    public static void removeEnv()
    {
        thlEnvs.remove();
    }

    public static boolean hasEnv()
    {
        return thlGlobals.get()!=null;
    }

    public static LuaValue contextGet(LuaValue _key)
    {
        return getContext().get(_key);
    }

    public static LuaValue envGet(LuaValue _key)
    {
        LuaValue _val = getEnv().get(_key);
        if(_val == null || _val.isnil())
        {
            _val = getContext().get(_key);
        }
        return _val;
    }

}