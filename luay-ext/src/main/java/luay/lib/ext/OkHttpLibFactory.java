package luay.lib.ext;

import lombok.SneakyThrows;
import luay.vm.luay.AbstractLibrary;
import luay.vm.luay.LuayLibraryFactory;

public class OkHttpLibFactory implements LuayLibraryFactory
{
    @Override
    public String getName() {
        return "http";
    }

    @Override
    @SneakyThrows
    public AbstractLibrary getInstance() {
        return (AbstractLibrary) Class.forName("luay.lib.ext.OkHttpLib").newInstance();
    }
}
