package luaycli;

import lombok.extern.slf4j.Slf4j;
import luay.main.LuayBuilder;
import luay.main.LuayContext;
import luay.main.LuayHelper;
import luay.vm.LuaError;
import luay.vm.LuaList;
import luay.ldata.LdataParser;
import picocli.CommandLine;

import java.io.File;
import java.util.Map;

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
        else
        {
            pMain.opts = new CliOptions();
            CommandLine cmd = new CommandLine(pMain.opts);
            cmd.parseArgs(args);
            pMain.runCli();
        }
        System.exit(0);
    }

    LuayContext buildContaxt()
    {
        LuayBuilder _b = LuayBuilder.create();
        _b.allLibraries();
        if(this.opts.includePaths!=null)
        {
            this.opts.includePaths.forEach((_f)->{_b.searchPath(_f.getAbsolutePath());});
        }

        for(String _Env : new String[] { "LUAY_MODULES", "LUAY_LIB", "LUAY_PATH", "LUAY_HOME" })
        {
            if(System.getenv(_Env)!=null)
            {
                for(String _part : System.getenv(_Env).split(":"))
                {
                    _b.searchPath(_part);
                }
            }
        }

        _b.searchPath(new File(OsUtil.getJarDirectory(),"lib/luay").getAbsolutePath());
        //_b.searchPath(new File(OsUtil.getCurrentDirectory(),"lib/luay").getAbsolutePath());

        _b.searchPath(new File(new File(OsUtil.getJarDirectory()).getParentFile(),"share/luay").getAbsolutePath());
        //_b.searchPath(new File(new File(OsUtil.getCurrentDirectory()).getParentFile(),"share/luay").getAbsolutePath());

        _b.searchPath(new File(new File(OsUtil.getJarDirectory()).getParentFile(),"lib/luay").getAbsolutePath());
        //_b.searchPath(new File(new File(OsUtil.getCurrentDirectory()).getParentFile(),"lib/luay").getAbsolutePath());

        _b.searchPath(OsUtil.getUserDataDirectory("luay"));
        _b.searchPath(OsUtil.getUserConfigDirectory("luay"));

        _b.searchPath(OsUtil.getUnixyUserDataDirectory("luay"));

        _b.searchPath(OsUtil.getUserDataDirectory());

        _b.searchPath(OsUtil.getSystemDataDirectory("luay"));

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
