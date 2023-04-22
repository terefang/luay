package luay.lib.v53;

import luay.vm.*;
import luay.vm.lib.OneArgFunction;
import luay.vm.lib.VarArgFunction;
import luay.vm.luay.AbstractLibrary;
import luay.vm.luay.LuayLibraryFactory;

import java.util.List;

public class Utf8Lib extends AbstractLibrary  implements LuayLibraryFactory
{
	@Override
	public List<Class> getLibraryFunctionClasses()
	{
		return toList(
				_char.class,
				_codepoint.class,
				_length.class,
				_substr.class,
				_indexof.class,
				_lastindexof.class,
				_lower.class,
				_upper.class
		);
	}

	@Override
	public String getLibraryName() {
		return "utf8";
	}

	public Utf8Lib()
	{
		super();
	}

	//   utf8.char (n [, n ...]) -> string
	public static final class _char extends VarArgFunction
	{
		@Override
		public Varargs invoke(Varargs args)
		{
			char [] _ch = new char[args.narg()];
			for(int _i=0; _i<args.narg(); _i++)
			{
				_ch[_i] = (char)args.checkint(_i+1);
			}
			return LuaString.valueOf(new String(_ch));
		}
	}

	//   utf8.codepoint (s [, i [, j]]) -> list
	public static final class _codepoint extends VarArgFunction
	{
		@Override
		public Varargs invoke(Varargs args)
		{
			String _str = args.checkjstring(1);

			char[] _ch = null;

			if(args.narg() == 1)
			{
				_ch = _str.toCharArray();
			}
			else
			{
				int _si = 0;
				int _ei = _str.length();
				if(args.narg()>=2) _si = args.checkint(2)-1;
				if(args.narg()==3) _ei = args.checkint(3);

				_ch = _str.substring(_si,_ei).toCharArray();
			}

			if(_ch.length == 0) return LuaValue.NIL;

			if(_ch.length == 1) return LuaInteger.valueOf((int)_ch[0]);

			LuaList _z = new LuaList();
			for(int _i=0; _i<_ch.length; _i++)
			{
				_z.set(_i+1, LuaInteger.valueOf((int)_ch[_i]));
			}
			return _z;
		}
	}

	// --- LUAY Extensions
	//   utf8.length (s) -> int
	public static final class _length extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue arg)
		{
			return LuaInteger.valueOf(arg.checkstring().lengthAsUtf8());
		}
	}

	//   utf8.substr (s, n [, i]) -> string
	public static final class _substr extends VarArgFunction
	{
		@Override
		public Varargs invoke(Varargs args)
		{
			String _str = args.checkjstring(1);

			if(args.narg() == 2)
			{
				return LuaString.valueOf(_str.substring(args.checkint(2)-1));
			}
			else
			if(args.narg() == 3)
			{
				return LuaString.valueOf(_str.substring(args.checkint(2)-1, args.checkint(3)));
			}
			return LuaValue.NONE;
		}
	}

	//   utf8.indexof (s, n [, i]) -> int/nil
	public static final class _indexof extends VarArgFunction
	{
		@Override
		public Varargs invoke(Varargs args)
		{
			String _str = args.checkjstring(1);

			int _r = -1;
			if(args.narg() == 2)
			{
				_r = _str.indexOf(args.checkjstring(2));
			}
			else
			if(args.narg() == 3)
			{
				_r = _str.indexOf(args.checkjstring(2), args.checkint(3)-1);
			}
			else
			{
				return LuaValue.NONE;
			}
			return _r<0 ? LuaValue.NIL : LuaInteger.valueOf(_r+1);
		}
	}

	//   utf8.lastindexof (s, n [, i]) -> int/nil
	public static final class _lastindexof extends VarArgFunction
	{
		@Override
		public Varargs invoke(Varargs args)
		{
			String _str = args.checkjstring(1);

			int _r = -1;
			if(args.narg() == 2)
			{
				_r = _str.lastIndexOf(args.checkjstring(2));
			}
			else
			if(args.narg() == 3)
			{
				_r = _str.lastIndexOf(args.checkjstring(2), args.checkint(3)-1);
			}
			else
			{
				return LuaValue.NONE;
			}
			return _r<0 ? LuaValue.NIL : LuaInteger.valueOf(_r+1);
		}
	}

	//   utf8.lower (s) -> string
	public static final class _lower extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue arg)
		{
			return LuaString.valueOf(arg.checkjstring().toLowerCase());
		}
	}

	//   utf8.upper (s) -> string
	public static final class _upper extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue arg)
		{
			return LuaString.valueOf(arg.checkjstring().toUpperCase());
		}
	}

}
