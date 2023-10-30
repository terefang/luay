package luay.vm.lib.java;

import luay.main.LuayHelper;
import luay.vm.LuaUserdata;
import luay.vm.LuaValue;
import luay.vm.Varargs;

import java.util.Iterator;

public class JavaIterator extends LuaUserdata
{
    public static JavaIterator of(Iterator obj)
    {
        return new JavaIterator(obj);
    }

    public static JavaIterator of(Iterable obj)
    {
        return of(obj.iterator());
    }

    int _it = 0;
    public JavaIterator(Iterator obj) {
        super(obj);
    }

    @Override
    public boolean isarray() {
        return super.isarray();
    }

    @Override
    public Varargs inext(LuaValue index) {
        if((index.isnil() && _it==0) || (index.checkint() == _it))
        {
            if(((Iterator)this.userdata()).hasNext())
            {
                _it++;
                Object _next = ((Iterator)this.userdata()).next();
                if(!(_next instanceof LuaValue))
                {
                    _next = LuayHelper.toValue(_next);
                }
                return LuaValue.varargsOf(new LuaValue[]{ LuaValue.valueOf(_it), (LuaValue) _next});
            }
            else
            {
                return LuaValue.NIL;
            }
        }
        return LuaValue.NIL;
    }

    @Override
    public Varargs next(LuaValue index) {
        return inext(index);
    }
}
