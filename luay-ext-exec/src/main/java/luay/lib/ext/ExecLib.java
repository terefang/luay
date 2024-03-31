package luay.lib.ext;

import ch.vorburger.exec.ManagedProcessBuilder;
import lombok.SneakyThrows;
import luay.lib.LuayGlobalsAwareLibrary;
import luay.lib.LuayLibraryFactory;
import luay.lib.LuaySimpleLibraryFactory;
import luay.main.LuayHelper;
import luay.vm.*;
import luay.vm.lib.ThreeArgFunction;
import luay.vm.lib.VarArgFunction;
import luay.vm.lib.ZeroArgFunction;
import luay.vm.lib.java.CoerceJavaToLua;

import java.util.Arrays;
import java.util.List;

public class ExecLib implements LuaySimpleLibraryFactory
{
    public ExecLib() { }
    @Override
    public String getName() {
        return "exec";
    }

    @Override
    public Object getInstance() {
        return null;
    }

    public static class ExecLibrary implements LuayGlobalsAwareLibrary
    {
        private Globals globals = null;
        @SneakyThrows
        public Varargs _luay_execute(Varargs _args)
        {
            ManagedProcessBuilder _bld = new ManagedProcessBuilder(_args.checkjstring(1));

            if(this.globals != null)
            {
                _bld.addStdErr(this.globals.STDERR);
                _bld.addStdOut(this.globals.STDOUT);
            }

            for(int _i=2; _i<=_args.narg(); _i++){
                _bld.addArgument(_args.checkjstring(_i),false);
            }

            return LuaValue.valueOf(_bld.build().start().waitForExit());
        }

        @Override
        public void setGlobals(LuaValue _env)
        {
            this.globals = _env.checkglobals();
        }
    }

}