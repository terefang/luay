package luay.vm.luay;

import luay.vm.LuaFunction;

import java.util.Iterator;
import java.util.ServiceLoader;

public interface LuaySimpleLibraryFactory
{
    String getName();
    Object getInstance();

    public static LuaFunction load(String _name)
    {
        ServiceLoader<LuaySimpleLibraryFactory> _ldr = ServiceLoader.load(LuaySimpleLibraryFactory.class);
        Iterator<LuaySimpleLibraryFactory> _it =  _ldr.iterator();
        while(_it.hasNext())
        {
            LuaySimpleLibraryFactory _lf = _it.next();
            if(_lf.getName().equalsIgnoreCase(_name))
            {
                return LuaySimpleLibWrapper.from(_name, _lf.getInstance());
            }
        }
        return null;
    }
}
