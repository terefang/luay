package luay.vm.luay;

import java.util.Iterator;
import java.util.ServiceLoader;

public interface LuayLibraryFactory
{
    String getName();
    AbstractLibrary getInstance();

    public static AbstractLibrary load(String _name)
    {
        ServiceLoader<LuayLibraryFactory> _ldr = ServiceLoader.load(LuayLibraryFactory.class);
        Iterator<LuayLibraryFactory> _it =  _ldr.iterator();
        while(_it.hasNext())
        {
            LuayLibraryFactory _lf = _it.next();
            if(_lf.getName().equalsIgnoreCase(_name))
            {
                return _lf.getInstance();
            }
        }
        return null;
    }
}
