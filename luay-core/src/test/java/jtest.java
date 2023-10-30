import luay.vm.LuaValue;
import luay.vm.lib.TwoArgFunction;
import luay.lib.ext.AbstractLibrary;

public class jtest extends TwoArgFunction
{
    @Override
    public LuaValue call(LuaValue _name, LuaValue _env) {
        return AbstractLibrary._zeroArgFunctionWrapper.from(_name.tojstring(),jtest::_call);
    }

    public static LuaValue _call()
    {
        return LuaValue.TRUE;
    }
}
