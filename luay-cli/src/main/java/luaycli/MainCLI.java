package luaycli;

import lombok.extern.slf4j.Slf4j;
import luay.lib.LuayLibraryFactory;
import luay.lib.LuaySimpleLibraryFactory;
import luay.main.LuayBuilder;
import luay.main.LuayContext;
import luay.main.LuayHelper;
import luay.vm.LuaError;
import luay.vm.LuaList;
import com.github.terefang.jmelange.data.ldata.LdataParser;
import picocli.CommandLine;

import java.io.File;
import java.util.*;

@Slf4j
public class MainCLI {
    private CliOptions opts;

    public static void main(String[] args) {
        MainCLI pMain = new MainCLI();

        if (args.length == 0 || "-help".equalsIgnoreCase(args[0]) || "--help".equalsIgnoreCase(args[0]))
        {
            pMain.handleHelp();
        }
        else if ("-version".equalsIgnoreCase(args[0])
                || "--version".equalsIgnoreCase(args[0]))
        {
            System.out.println("Command: luay-cli Version: "+ Version._VERSION+" Build: "+Version._BUILD);
        }
        else if ("-list-path".equalsIgnoreCase(args[0])
                || "--list-path".equalsIgnoreCase(args[0])) {
            for (String _path : buildSearchpath())
            {
                System.out.println(_path);
            }
        }
        else if ("-list-modules".equalsIgnoreCase(args[0])
                || "--list-modules".equalsIgnoreCase(args[0])) {
            for (String _path : buildModlist())
            {
                System.out.println(_path);
            }
        }
        else
        {
            pMain.opts = new CliOptions();
            CommandLine cmd = new CommandLine(pMain.opts);
            cmd.parseArgs(args);
            pMain.runCli();
        }
        System.exit(0);
    }

    static List<String> buildModlist()
    {
        List<String> _ret = new Vector<>();
        {
            ServiceLoader<LuayLibraryFactory> _ldr = ServiceLoader.load(LuayLibraryFactory.class);
            Iterator<LuayLibraryFactory> _it = _ldr.iterator();
            while (_it.hasNext()) {
                LuayLibraryFactory _lf = _it.next();
                _ret.add(_lf.getName());
            }
        }
        {
            ServiceLoader<LuaySimpleLibraryFactory> _ldr = ServiceLoader.load(LuaySimpleLibraryFactory.class);
            Iterator<LuaySimpleLibraryFactory> _it = _ldr.iterator();
            while (_it.hasNext()) {
                LuaySimpleLibraryFactory _lf = _it.next();
                _ret.add(_lf.getName());
            }
        }
        return _ret;
    }

    static List<String> buildSearchpath()
    {
        List<String> _ret = new Vector<>();
        for(String _Env : new String[] { "LUAY_MODULES", "LUAY_LIB", "LUAY_PATH", "LUAY_HOME" })
        {
            if(System.getenv(_Env)!=null)
            {
                for(String _part : System.getenv(_Env).split(":"))
                {
                    _ret.add(_part);
                }
            }
        }

        _ret.add(OsUtil.getUserConfigDirectory("luay"));
        _ret.add(OsUtil.getUserDataDirectory("luay"));
        _ret.add(OsUtil.getUnixyUserDataDirectory("luay"));
        _ret.add(OsUtil.getSystemDataDirectory("luay"));

        return _ret;
    }

    LuayContext buildContaxt()
    {
        LuayBuilder _b = LuayBuilder.create();
        _b.allLibraries();
        if(this.opts.includePaths!=null)
        {
            this.opts.includePaths.forEach((_f)->{_b.searchPath(_f.getAbsolutePath());});
        }

        for(String _part : buildSearchpath())
        {
            _b.searchPath(_part);
        }
        return _b.build();
    }

    private void runCli()
    {
        LuayContext _ctx = buildContaxt();

        if(this.opts.optFile!=null && this.opts.optFile.exists())
        {
            for(Map.Entry<String, Object> _entry : LdataParser.loadFrom(this.opts.optFile).entrySet())
            {
                _ctx.set(_entry.getKey(), LuayHelper.toValue(_entry.getValue()));
            };
        }

        if(this.opts.options!=null && this.opts.options.size()>0)
        {
            for(String _k : this.opts.options.stringPropertyNames())
            {
                _ctx.set(_k, LuayHelper.toValue(this.opts.options.getProperty(_k)));
            };
        }

        try {
            File _file = this.opts.infile;
            if(_file == null)
            {
                if(this.opts.arguments!=null && this.opts.arguments.size()>0)
                {
                    // the first argument is the luay file
                    _file = new File(this.opts.arguments.remove(0));
                }
                else
                {
                    log.error("no file given");
                    return;
                }
            }

            if(this.opts.arguments!=null && this.opts.arguments.size()>0)
            {
                _ctx.set("_ARGS", LuayHelper.toList(this.opts.arguments));
            }
            else
            {
                _ctx.set("_ARGS", new LuaList());
            }

            _ctx.setBase(_file);
            _ctx.compileScript(_file);
            _ctx.runScript();
        }
        catch(LuaError _lxe)
        {
            log.error(_lxe.getMessage());
            for(StackTraceElement _st :_lxe.getStackTrace())
            {
                log.error("file = "+_st.getFileName()+", line = "+_st.getLineNumber());
                if(_st.getFileName()!=null && _st.getFileName().endsWith(".lua")) break;
            }
        }
        catch(Exception _xe)
        {
            log.error(_xe.toString());
        }
    }

    private void handleHelp()
    {
        System.out.println("Command: luay-cli ...");
        System.out.println("Version: "+Version._VERSION);
        System.out.println("Build: "+Version._BUILD);
        this.opts = new CliOptions();
        CommandLine cmd = new CommandLine(this.opts);
        cmd.usage(System.out);
    }
}
