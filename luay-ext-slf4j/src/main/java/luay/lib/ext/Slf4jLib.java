package luay.lib.ext;

import lombok.extern.slf4j.Slf4j;
import luay.lib.LuayLibraryFactory;
import luay.lib.LuayStringifierFunction;
import luay.vm.*;
import luay.vm.lib.VarArgFunction;

import java.util.List;

@Slf4j
public class Slf4jLib extends AbstractLibrary  implements LuayLibraryFactory
{
	@Override
	public List<Class> getLibraryFunctionClasses()
	{
		return AbstractLibrary.toList(
			_log.class,
			_logf.class,
			_debug.class,
			_info.class,
			_warn.class,
			_error.class,
			_debugf.class,
			_infof.class,
			_warnf.class,
			_errorf.class
		);
	}

	@Override
	public String getLibraryName() {
		return "slf4j";
	}

	public Slf4jLib()
	{
		super();
	}

	public static String argsToString(Varargs args)
	{
		StringBuilder _sb = new StringBuilder();
		for(int _i=0; _i<args.narg(); _i++)
		{
			if(_i>0)_sb.append(" ");

			_sb.append(LuayStringifierFunction._stringify(args.arg(_i+1), false));
		}
		return _sb.toString();
	}

	public static String argsToStringFmt(Varargs args)
	{
		String _fmt = args.checkjstring(1);
		Object[] _farg = new Object[args.narg()-1];

		for (int i = 2, n = args.narg(); i <= n; i++)
		{
			if(args.arg(i).isnil())
			{
				_farg[i-2] = "nil";
			}
			else
			if(args.arg(i).isboolean())
			{
				_farg[i-2] = args.checkboolean(i);
			}
			else
			if(args.arg(i).islong())
			{
				_farg[i-2] = args.checklong(i);
			}
			else
			if(args.arg(i).isinttype())
			{
				_farg[i-2] = args.checkint(i);
			}
			else
			if(args.arg(i).isnumber())
			{
				_farg[i-2] = args.checkdouble(i);
			}
			else
			if(args.arg(i).isstring())
			{
				_farg[i-2] = args.checkjstring(i);
			}
			else
			{
				_farg[i-2] = LuayStringifierFunction._stringify(args.arg(i), false).tojstring();
			}
		}
		return String.format(_fmt, _farg);
	}

	//   slf4j.log (s [, s ...])
	public static final class _log extends VarArgFunction
	{
		@Override
		public Varargs invoke(Varargs args)
		{
			log.info(argsToString(args));
			return LuaValue.NONE;
		}
	}

	//   slf4j.logf (fmt [, p, ...])
	public static final class _logf extends VarArgFunction
	{
		@Override
		public Varargs invoke(Varargs args)
		{
			log.info(argsToStringFmt(args));
			return LuaValue.NONE;
		}
	}

	//   slf4j.debug (s [, s ...])
	public static final class _debug extends VarArgFunction
	{
		@Override
		public Varargs invoke(Varargs args)
		{
			log.debug(argsToString(args));
			return LuaValue.NONE;
		}
	}

	//   slf4j.debugf (fmt [, p, ...])
	public static final class _debugf extends VarArgFunction
	{
		@Override
		public Varargs invoke(Varargs args)
		{
			log.debug(argsToStringFmt(args));
			return LuaValue.NONE;
		}
	}

	//   slf4j.info (s [, s ...])
	public static final class _info extends VarArgFunction
	{
		@Override
		public Varargs invoke(Varargs args)
		{
			log.info(argsToString(args));
			return LuaValue.NONE;
		}
	}

	//   slf4j.infof (fmt [, p, ...])
	public static final class _infof extends VarArgFunction
	{
		@Override
		public Varargs invoke(Varargs args)
		{
			log.info(argsToStringFmt(args));
			return LuaValue.NONE;
		}
	}

	//   slf4j.warn (s [, s ...])
	public static final class _warn extends VarArgFunction
	{
		@Override
		public Varargs invoke(Varargs args)
		{
			log.warn(argsToString(args));
			return LuaValue.NONE;
		}
	}

	//   slf4j.warnf (fmt [, p, ...])
	public static final class _warnf extends VarArgFunction
	{
		@Override
		public Varargs invoke(Varargs args)
		{
			log.warn(argsToStringFmt(args));
			return LuaValue.NONE;
		}
	}

	//   slf4j.error (s [, s ...])
	public static final class _error extends VarArgFunction
	{
		@Override
		public Varargs invoke(Varargs args)
		{
			log.error(argsToString(args));
			return LuaValue.NONE;
		}
	}

	//   slf4j.errorf (fmt [, p, ...])
	public static final class _errorf extends VarArgFunction
	{
		@Override
		public Varargs invoke(Varargs args)
		{
			log.error(argsToStringFmt(args));
			return LuaValue.NONE;
		}
	}

}
