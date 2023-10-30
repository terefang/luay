package luaygui;

import lombok.SneakyThrows;
import luay.lib.LuayLibraryFactory;
import luay.lib.ext.AbstractLibrary;
import luay.lib.ext.image.ImageLib;
import luay.vm.LuaFunction;
import luay.vm.LuaValue;
import luay.vm.Varargs;

import java.util.List;

public class CanvasLib extends AbstractLibrary implements LuayLibraryFactory
{
    @Override
    public String getLibraryName() {
        return "canvas";
    }

    @Override
    public List<LuaFunction> getLibraryFunctions() {
        return toList(
                _varArgFunctionWrapper.from("set", CanvasLib::_set)
        );
    }

    @SneakyThrows
    public static LuaValue _set(Varargs args) {
        ImageLib.ImageLibHolder _ctx = (ImageLib.ImageLibHolder)args.checkuserdata(1, ImageLib.ImageLibHolder.class);
        MainGUI._INSTANCE.setCanvas(_ctx._img);
        return LuaValue.NONE;
    }

}
