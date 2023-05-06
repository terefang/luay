package luay.lib.ext;

import luay.vm.*;
import luay.vm.lib.BaseLib;
import luay.vm.lib.OneArgFunction;
import luay.vm.lib.VarArgFunction;
import luay.lib.ext.AbstractLibrary;
import luay.lib.LuayLibraryFactory;

import java.util.List;
import java.util.StringTokenizer;

public class Utf8Lib extends AbstractLibrary  implements LuayLibraryFactory
{
	@Override
	public List<Class> getLibraryFunctionClasses() {
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
	public List<LuaFunction> getLibraryFunctions() {
		return toList(
				_oneArgFunctionWrapper.from("strip", Utf8Lib::_trim),
				_oneArgFunctionWrapper.from("stripleading", Utf8Lib::_stripleading),
				_oneArgFunctionWrapper.from("striptrailing", Utf8Lib::_striptrailing),
				_oneArgFunctionWrapper.from("trim", Utf8Lib::_trim),
				_twoArgFunctionWrapper.from("contains", Utf8Lib::_contains),
				_twoArgFunctionWrapper.from("matches", Utf8Lib::_matches),
				_varArgFunctionWrapper.from("split", Utf8Lib::_split),
				_varArgFunctionWrapper.from("rxsplit", Utf8Lib::_rxsplit),
				_varArgFunctionWrapper.from("replace", Utf8Lib::_replace),
				_varArgFunctionWrapper.from("rxreplace", Utf8Lib::_rxreplace),
				_varArgFunctionWrapper.from("join", Utf8Lib::_join),
				_varArgFunctionWrapper.from("format", Utf8Lib::_format),
				_varArgFunctionWrapper.from("repeat", Utf8Lib::_repeat),
				_varArgFunctionWrapper.from("count", Utf8Lib::_count),
				_varArgFunctionWrapper.from("find", Utf8Lib::_find),
				_varArgFunctionWrapper.from("startswith", Utf8Lib::_startsWith),
				_varArgFunctionWrapper.from("endswith", Utf8Lib::_endsWith)
		);
	}

	// utf8.find(hay, needle [, start]) -> offset or nil
	public static LuaValue _find(Varargs args) {
		String _hay = args.checkjstring(1);
		String _needle = args.checkjstring(2);
		int _start = args.optint(3, 1);
		if(_start<0) {
			_start = _hay.length()+_start;
		} else {
			_start--;
		}

		int _ofs = 0;
		if(_start<0) {
			_ofs = _hay.lastIndexOf(_needle, _hay.length()+_start);
		} else {
			_ofs = _hay.indexOf(_needle, _start);
		}
		if(_ofs>=0) return LuaInteger.valueOf(_ofs+1);
		return LuaValue.NIL;
	}

	// utf8.count(hay, needle [, start]) -> offset of nil
	public static LuaValue _count(Varargs args) {
		String _hay = args.checkjstring(1);
		String _needle = args.checkjstring(2);

		if(_needle.length()==0) return LuaInteger.ZERO;

		int _start = args.optint(3, 1);
		int _end = args.optint(4, _hay.length()+1);
		if(_start<0) {
			_start = _hay.length()+_start;
		} else {
			_start--;
		}

		if(_end<0) {
			_end = _hay.length()+_end;
		} else {
			_end--;
		}

		int _count = 0;

		if(_needle.length()==1) {
			for(int _i = _start; _i<_end; _i++) {
				if(_hay.charAt(_i) == _needle.charAt(0)) _count++;
			}
		} else {
			while((_start = _hay.indexOf(_needle, _start))>=0) {
				if(_start>=_end) break;
				_count++;
				_start+=_needle.length();
			}
		}

		return LuaValue.valueOf(_count);
	}

	public static LuaValue _contains(LuaValue _hay, LuaValue _needle) {
		if(_hay.tojstring().contains(_needle.tojstring())) {
			return LuaValue.TRUE;
		}
		return LuaValue.FALSE;
	}

	public static LuaValue _matches(LuaValue _hay, LuaValue _needle) {
		if(_hay.tojstring().matches(_needle.tojstring())) {
			return LuaValue.TRUE;
		}
		return LuaValue.FALSE;
	}

	public static LuaValue _startsWith(Varargs _args) {
		String _hay = _args.checkjstring(1);
		String _needle = _args.checkjstring(2);
		int _ofs = _args.optint(3, 1)-1;
		if(_hay.startsWith(_needle, _ofs)) {
			return LuaValue.TRUE;
		}
		return LuaValue.FALSE;
	}

	public static LuaValue _endsWith(Varargs _args) {
		String _hay = _args.checkjstring(1);
		String _needle = _args.checkjstring(2);
		if(_hay.endsWith(_needle)) {
			return LuaValue.TRUE;
		}
		return LuaValue.FALSE;
	}

	// utf8.rxsplit(hay, rx) -> list
	public static LuaValue _rxsplit(Varargs _args) {
		String _hay = _args.checkjstring(1);
		String _needle = _args.optjstring(2, "\\s+");

		LuaTable _table = new LuaTable();

		for(String _part : _hay.split(_needle)) {
			_table.insert(0, LuaValue.valueOf(_part));
		}
		return _table;
	}

	// utf8.split(hay, sep) -> list
	public static LuaValue _split(Varargs args) {
		String _hay = args.checkjstring(1);
		String _sep = args.checkjstring(2);
		LuaList _list = new LuaList();

		StringTokenizer _st = new StringTokenizer(_hay, _sep, true);
		boolean _lastWasSep = false;
		while(_st.hasMoreTokens()) {
			String _t = _st.nextToken();
			if(_sep.indexOf(_t)<0) {
				_list.insert(LuaString.valueOf(_t));
				_lastWasSep = false;
			} else {
				if(_lastWasSep) {
					_list.insert(LuaString.valueOf(""));
				}
				_lastWasSep = true;
			}
		}
		return _list;
	}

	// utf8.replace(string, charFrom, charTo) -> string
	// utf8.replace(string, table) -> string
	public static LuaValue _replace(Varargs args) {
		String _string = args.checkjstring(1);

		for(int _j = 2, _n = args.narg(); _j<=_n; _j+=2)
		{
			if(args.istable(_j))
			{
				LuaTable _t = args.checktable(_j);
				for(LuaValue _k : _t.keys())
				{
					_string = _string.replace(_k.tojstring(), _t.get(_k).tojstring());
				}
				_j--;
			}
			else
			{
				char[] _from = args.checkjstring(_j).toCharArray();
				char[] _to = args.checkjstring(_j+1).toCharArray();

				for(int _i = 0; _i< _from.length; _i++)
				{
					int _ii = _i<_to.length ? _i : _to.length-1;
					_string = _string.replace(_from[_i], _to[_ii]);
				}
			}
		}
		return LuaString.valueOf(_string);
	}

	// utf8.rxreplace(string, rx, to) -> string
	public static LuaValue _rxreplace(Varargs args) {
		String _string = args.checkjstring(1);
		String _from = args.checkjstring(2);
		String _to = args.checkjstring(3);

		_string = _string.replaceAll(_from, _to);

		return LuaString.valueOf(_string);
	}

	// utf8.join(sep, hay, ...) -> string
	public static LuaValue _join(Varargs args) {
		String _sep = args.checkjstring(1);
		boolean _first = true;
		StringBuilder _sb = new StringBuilder();
		for(int _i=2; _i<=args.narg();_i++) {
			if(!_first) {
				_sb.append(_sep);
			}
			_first = false;
			_sb.append(args.checkjstring(_i));
		}
		return LuaString.valueOf(_sb.toString());
	}

	public static LuaValue _format(Varargs _args) {
		return LuaValue.valueOf(String.format(_args.checkjstring(1), BaseLib.varArgsTo(_args.subargs(2))));
	}

	public static LuaValue _repeat(Varargs _args) {
		StringBuilder _sb = new StringBuilder();
		String _str = _args.checkjstring(1);
		int _count = _args.checkint(2);
		for(int _i=0; _i<_count; _i++) {
			_sb.append(_str);
		}
		return LuaValue.valueOf(_sb.toString());
	}

	public static LuaValue _trim(LuaValue _arg) {
		return LuaString.valueOf(_arg.checkjstring().trim());
	}

	public static LuaValue _striptrailing(LuaValue _arg) {
		return LuaString.valueOf(_arg.checkjstring().stripTrailing());
	}

	public static LuaValue _stripleading(LuaValue _arg) {
		return LuaString.valueOf(_arg.checkjstring().stripLeading());
	}

	@Override
	public String getLibraryName() {
		return "utf8";
	}

	public Utf8Lib() {
		super();
	}

	//   utf8.char (n [, n ...]) -> string
	public static final class _char extends VarArgFunction {
		@Override
		public Varargs invoke(Varargs args) {
			char [] _ch = new char[args.narg()];
			for(int _i=0; _i<args.narg(); _i++) {
				_ch[_i] = (char)args.checkint(_i+1);
			}
			return LuaString.valueOf(new String(_ch));
		}
	}

	//   utf8.codepoint (s [, i [, j]]) -> list
	public static final class _codepoint extends VarArgFunction {
		@Override
		public Varargs invoke(Varargs args) {
			String _str = args.checkjstring(1);

			char[] _ch = null;

			if(args.narg() == 1) {
				_ch = _str.toCharArray();
			} else {
				int _si = 0;
				int _ei = _str.length();
				if(args.narg()>=2) _si = args.checkint(2)-1;
				if(args.narg()==3) _ei = args.checkint(3);

				_ch = _str.substring(_si,_ei).toCharArray();
			}

			if(_ch.length == 0) return LuaValue.NIL;

			if(_ch.length == 1) return LuaInteger.valueOf((int)_ch[0]);

			LuaList _z = new LuaList();
			for(int _i=0; _i<_ch.length; _i++) {
				_z.set(_i+1, LuaInteger.valueOf((int)_ch[_i]));
			}
			return _z;
		}
	}

	// --- LUAY Extensions
	//   utf8.length (s) -> int
	public static final class _length extends OneArgFunction {
		@Override
		public LuaValue call(LuaValue arg) {
			return LuaInteger.valueOf(arg.checkstring().lengthAsUtf8());
		}
	}

	//   utf8.substr (s, n [, i]) -> string
	public static final class _substr extends VarArgFunction {
		@Override
		public Varargs invoke(Varargs args) {
			String _str = args.checkjstring(1);

			if(args.narg() == 2) {
				return LuaString.valueOf(_str.substring(args.checkint(2)-1));
			} else if(args.narg() == 3) {
				return LuaString.valueOf(_str.substring(args.checkint(2)-1, args.checkint(3)));
			}
			return LuaValue.NONE;
		}
	}

	//   utf8.indexof (s, n [, i]) -> int/nil
	public static final class _indexof extends VarArgFunction {
		@Override
		public Varargs invoke(Varargs args) {
			String _str = args.checkjstring(1);

			int _r = -1;
			if(args.narg() == 2) {
				_r = _str.indexOf(args.checkjstring(2));
			} else if(args.narg() == 3) {
				_r = _str.indexOf(args.checkjstring(2), args.checkint(3)-1);
			} else {
				return LuaValue.NONE;
			}
			return _r<0 ? LuaValue.NIL : LuaInteger.valueOf(_r+1);
		}
	}

	//   utf8.lastindexof (s, n [, i]) -> int/nil
	public static final class _lastindexof extends VarArgFunction {
		@Override
		public Varargs invoke(Varargs args) {
			String _str = args.checkjstring(1);

			int _r = -1;
			if(args.narg() == 2) {
				_r = _str.lastIndexOf(args.checkjstring(2));
			} else if(args.narg() == 3) {
				_r = _str.lastIndexOf(args.checkjstring(2), args.checkint(3)-1);
			} else {
				return LuaValue.NONE;
			}
			return _r<0 ? LuaValue.NIL : LuaInteger.valueOf(_r+1);
		}
	}

	//   utf8.lower (s) -> string
	public static final class _lower extends OneArgFunction {
		@Override
		public LuaValue call(LuaValue arg) {
			return LuaString.valueOf(arg.checkjstring().toLowerCase());
		}
	}

	//   utf8.upper (s) -> string
	public static final class _upper extends OneArgFunction {
		@Override
		public LuaValue call(LuaValue arg) {
			return LuaString.valueOf(arg.checkjstring().toUpperCase());
		}
	}

}
