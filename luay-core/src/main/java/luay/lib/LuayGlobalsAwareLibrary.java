package luay.lib;

import luay.vm.LuaValue;

public interface LuayGlobalsAwareLibrary
{
    void setGlobals(LuaValue _env);
}
