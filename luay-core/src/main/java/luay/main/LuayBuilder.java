package luay.main;

import luay.vm.LoadState;
import luay.vm.LuaValue;
import luay.vm.compiler.LuaC;
import luay.vm.lib.*;
import luay.vm.lib.java.*;
import luay.vm.luajc.LuaJC;
import luay.lib.LuaySimpleLibraryFactory;
import luay.lib.LuayLibraryFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Vector;

public class LuayBuilder
{
    private List<LuaValue> globalLibs = new Vector<>();
    private List<String> namedLibs = new Vector<>();
    private List<LuaValue> extLibs = new Vector<>();
    private List<String> searchPath = new Vector<>();
    private List<String> searchPackages = new Vector<>();
    private OutputStream outputStream;
    private InputStream inputStream;
    private OutputStream errorStream;

    public static LuayBuilder create() {
        return new LuayBuilder();
    }

    private LuayBuilder()
    {
    }

    public LuayContext build()
    {
        LuayGlobal _globals = new LuayGlobal();
        _globals.load(new BaseLib());

        PackageLib _pkglib = new PackageLib();
        _globals.load(_pkglib);

        StringBuilder _sb = new StringBuilder();
        this.searchPath.forEach((x) -> _sb.append(x+";"));
        _sb.append(";?.lua");
        _pkglib.setLuaPath(_sb.toString());

        if(this.outputStream!=null)
        {
            _globals.STDOUT=new PrintStream(this.outputStream);
        }

        if(this.inputStream!=null)
        {
            _globals.STDIN=this.inputStream;
        }

        if(this.errorStream!=null)
        {
            _globals.STDOUT=new PrintStream(this.errorStream);
        }

        this.globalLibs.forEach((x) -> _globals.load(x));

        this.extLibs.forEach((x) -> _globals.load(x));

        LoadState.install(_globals);
        LuaC.install(_globals);
        LuaJC.install(_globals);

        return new LuayContext(_globals);
    }

    public LuayBuilder globalLibrary(LuaValue _lib)
    {
        this.globalLibs.add(_lib);
        return this;
    }

    public LuayBuilder extensionLibrary(LuaValue _lib)
    {
        this.extLibs.add(_lib);
        return this;
    }

    public LuayBuilder namedLibrary(String _lib)
    {
        LuaValue _val = (LuaValue) LuayLibraryFactory.load(_lib);

        if(_val == null)
        {
            _val = LuaySimpleLibraryFactory.load(_lib);
        }

        if(_val != null) this.extLibs.add(_val);

        return this;
    }

    public LuayBuilder baseLibraries()
    {
        this.globalLibs.add(new Bit32Lib());
        this.globalLibs.add(new TableLib());
        this.globalLibs.add(new StringLib());
        this.globalLibs.add(new CoroutineLib());
        this.globalLibs.add(new MathLib());
        this.globalLibs.add(new JseIoLib());
        this.globalLibs.add(new OsLib());
        return this;
    }

    public LuayBuilder v53Libraries()
    {
        // --- dont need this anymore
        // --- luay-factory will take care of
        //this.globalLibs.add(new Bit64Lib());
        //this.namedLibrary("utf8");
        return this;
    }

    public LuayBuilder javaLibraries()
    {
        this.globalLibs.add(new LuajavaLib().addSearchPackages(this.searchPackages));
        // --- dont need this anymore
        // --- luay-factory will take care of
        // this.namedLibrary("hash");
        // this.namedLibrary("path");
        return this;
    }

    public LuayBuilder rocksLibraries()
    {
        // --- dont need this anymore
        // --- luay-factory will take care of
        //this.namedLibrary("lfs");
        return this;
    }

    public LuayBuilder allLibraries()
    {
        return this.baseLibraries().javaLibraries().v53Libraries().rocksLibraries();
    }

    public LuayBuilder searchPath(String _path)
    {
        if(_path.contains("?"))
        {
            this.searchPath.add(_path);
        }
        else
        {
            this.searchPath.add(_path+"/?.lua");
        }
        return this;
    }

    public LuayBuilder searchPackage(String _path)
    {
        this.searchPackages.add(_path);
        return this;
    }

    public LuayBuilder outputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
        return this;
    }

    public LuayBuilder errorStream(OutputStream outputStream) {
        this.errorStream = outputStream;
        return this;
    }

    public LuayBuilder inputStream(InputStream inputStream) {
        this.inputStream = inputStream;
        return this;
    }

    public LuayBuilder searchPath(List<String> _path)
    {
        for(String _p : _path)
        {
            searchPath(_p);
        }
        return this;
    }
}
