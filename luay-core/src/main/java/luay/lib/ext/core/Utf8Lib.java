package luay.lib.ext.core;
/*
%!begin MARKDOWN

## UTF-8 Support

```lua
local utf8 = require('utf8');
```

Refer: http://www.lua.org/manual/5.3/manual.html#6.5

*   `utf8.char (···) -> string`
*   `utf8.charpattern` (NOT IMPLEMENTED)
*   `utf8.codes (s)` (NOT IMPLEMENTED)
*   `utf8.codepoint (s [, i [, j]]) -> list`
*   `utf8.len (s [, i [, j]])` (NOT IMPLEMENTED)
*   `utf8.offset (s, n [, i])` (NOT IMPLEMENTED)

### Luay Extensions

*   `utf8.charat (s, n) -> string`
*   `utf8.firstchar (s) -> string`
*   `utf8.lastchar (s) -> string`

*   `utf8.length (s) -> int`
*   `utf8.substr (s, n [, i]) -> string`
*   `utf8.cutstr (s, n) -> str1, str2`

*   `utf8.indexof (s, n [, i]) -> int/nil`
*   `utf8.indexofany (s, n1 [, ..., nN]) -> int/nil`
*   `utf8.lastindexof (s, n [, i]) -> int/nil`
*   `utf8.lastindexofany (s, n1 [, ..., nN]) -> int/nil`

*   `utf8.lower (s) -> string`
*   `utf8.upper (s) -> string`

*   `utf8.find(hay, needle [, start]) -> offset or nil`
*   `utf8.count(hay, needle [, start]) -> offset of nil`
*   `utf8.contains(hay, needle) -> boolean`

*   `utf8.matches(hay, rx) -> boolean`
*   `utf8.matchesany(hay, rx, ...) -> boolean`

*   `utf8.startswith(hay, needle) -> boolean`
*   `utf8.startswithany ( string, string [, ...]) -> boolean`
*   `utf8.startswithnocase ( string, string [, ...] ) -> boolean`

*   `utf8.endswith(hay, needle) -> boolean`
*   `utf8.endswithany ( string, string [, ...]) -> boolean`
*   `utf8.endswithnocase ( string, string [, ...] ) -> boolean`

*   `utf8.rxsplit(hay, rx) -> list`
*   `utf8.split(hay, sep) -> list`
*   `utf8.join(sep, hay, ...) -> string`
*   `utf8.format(fmt, ...) -> string`
*   `utf8.repeat(part, int) -> string`

*   `utf8.trim(hay) -> string`
*   `utf8.ltrim(hay) -> string`
*   `utf8.rtrim(hay) -> string`

*   `utf8.strip(hay) -> string`
*   `utf8.striptrailing(hay) -> string`
*   `utf8.stripleading(hay) -> string`

*   `utf8.replace(string, charFrom, charTo) -> string`
*   `utf8.replace(string, table) -> string`

*   `utf8.rxreplace(string, rx, to) -> string`
*   `utf8.rxreplaceall(string, rx, to) -> string`

\pagebreak


%!end MARKDOWN

https://code.aiq.dk/luazdf/fn/asciichunks.html


 */

import luay.vm.*;
import luay.vm.lib.BaseLib;
import luay.vm.lib.OneArgFunction;
import luay.vm.lib.VarArgFunction;
import luay.lib.ext.AbstractLibrary;
import luay.lib.LuayLibraryFactory;

import java.util.*;

public class Utf8Lib extends AbstractLibrary  implements LuayLibraryFactory
{
	@Override
	public String getLibraryName() {
		return "utf8";
	}

	public Utf8Lib() {
		super();
	}

	@Override
	public List<Class> getLibraryFunctionClasses() {
		return toList(
				_char.class,
				_codepoint.class,
				_length.class,
				_substr.class,
				_lower.class,
				_upper.class
		);
	}

	@Override
	public List<LuaFunction> getLibraryFunctions() {
		return toList(
				_varArgFunctionWrapper.from("hammingdistance", Utf8Lib::_hammingdistance),
				_varArgFunctionWrapper.from("cutstr", Utf8Lib::_cutstr),
				_varArgFunctionWrapper.from("charat", Utf8Lib::_charat),
				_varArgFunctionWrapper.from("firstchar", Utf8Lib::_firstchar),
				_varArgFunctionWrapper.from("lastchar", Utf8Lib::_lastchar),
				_oneArgFunctionWrapper.from("strip", Utf8Lib::_trim),
				_oneArgFunctionWrapper.from("stripleading", Utf8Lib::_stripleading),
				_oneArgFunctionWrapper.from("ltrim", Utf8Lib::_stripleading),
				_oneArgFunctionWrapper.from("striptrailing", Utf8Lib::_striptrailing),
				_oneArgFunctionWrapper.from("rtrim", Utf8Lib::_striptrailing),
				_oneArgFunctionWrapper.from("trim", Utf8Lib::_trim),
				_twoArgFunctionWrapper.from("contains", Utf8Lib::_contains),
				_twoArgFunctionWrapper.from("matches", Utf8Lib::_matches),
				_varArgFunctionWrapper.from("matchesany", Utf8Lib::_matchesAny),
				_varArgFunctionWrapper.from("explode", Utf8Lib::_explode),
				_varArgFunctionWrapper.from("split", Utf8Lib::_split),
				_varArgFunctionWrapper.from("rxsplit", Utf8Lib::_rxsplit),
				_varArgFunctionWrapper.from("replace", Utf8Lib::_replace),
				_varArgFunctionWrapper.from("rxreplace", Utf8Lib::_rxreplace),
				_varArgFunctionWrapper.from("rxreplaceall", Utf8Lib::_rxreplaceall),
				_varArgFunctionWrapper.from("join", Utf8Lib::_join),
				_varArgFunctionWrapper.from("format", Utf8Lib::_format),
				_varArgFunctionWrapper.from("repeat", Utf8Lib::_repeat),
				_varArgFunctionWrapper.from("count", Utf8Lib::_count),
				_varArgFunctionWrapper.from("find", Utf8Lib::_find),
				_varArgFunctionWrapper.from("indexof", Utf8Lib::_indexof),
				_varArgFunctionWrapper.from("indexofany", Utf8Lib::_indexofany),
				_varArgFunctionWrapper.from("lastindexof", Utf8Lib::_lastindexof),
				_varArgFunctionWrapper.from("lastindexofany", Utf8Lib::_lastindexofany),
				_varArgFunctionWrapper.from("startswith", Utf8Lib::_startsWith),
				_varArgFunctionWrapper.from("startswithany", Utf8Lib::_startsWithAny),
				_varArgFunctionWrapper.from("startswithnocase", Utf8Lib::_startsWithNocase),
				_varArgFunctionWrapper.from("endswith", Utf8Lib::_endsWith),
				_varArgFunctionWrapper.from("endswithany", Utf8Lib::_endsWithAny),
				_varArgFunctionWrapper.from("endswithnocase", Utf8Lib::_endsWithNocase)
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

	public static LuaValue _matchesAny(Varargs _args) {
		String _hay = _args.checkjstring(1);
		for(int _i = 2; _i<=_args.narg(); _i++) {
			if(_hay.matches(_args.checkjstring(_i))) {
				return LuaValue.TRUE;
			}
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

	public static LuaValue _startsWithAny(Varargs _args) {
		String _hay = _args.checkjstring(1);
		for(int _i = 2; _i<=_args.narg(); _i++) {
			if(_hay.startsWith(_args.checkjstring(_i))) {
				return LuaValue.TRUE;
			}
		}
		return LuaValue.FALSE;
	}

	public static LuaValue _startsWithNocase(Varargs _args)
	{
		String _hay = _args.checkjstring(1).toLowerCase();
		for(int _i = 2; _i<=_args.narg(); _i++) {
			if(_hay.startsWith(_args.checkjstring(_i).toLowerCase())) {
				return LuaValue.TRUE;
			}
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

	public static LuaValue _endsWithAny(Varargs _args) {
		String _hay = _args.checkjstring(1);
		for(int _i = 2; _i<=_args.narg(); _i++) {
			if(_hay.endsWith(_args.checkjstring(_i))) {
				return LuaValue.TRUE;
			}
		}
		return LuaValue.FALSE;
	}

	public static LuaValue _endsWithNocase(Varargs _args)
	{
		String _hay = _args.checkjstring(1).toLowerCase();
		for(int _i = 2; _i<=_args.narg(); _i++) {
			if(_hay.endsWith(_args.checkjstring(_i).toLowerCase())) {
				return LuaValue.TRUE;
			}
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
	
	// utf8.explode(hay, sep, limit) -> list
	public static LuaValue _explode(Varargs args) {
		String _hay = args.checkjstring(1);
		String _sep = args.checkjstring(2);
		if(args.narg()==2)
		{
			return _split(args);
		}
		int _n = args.checkint(3);
		
		LuaList _list = new LuaList();
		
		CustomStringTokenizer _st = new CustomStringTokenizer(_hay, _sep, true);
		boolean _lastWasSep = false;
		while(_st.hasMoreTokens() && _n>1) {
			String _t = _st.nextToken();
			if(_sep.indexOf(_t)<0) {
				_list.insert(LuaString.valueOf(_t));
				_lastWasSep = false;
				_n--;
			} else {
				if(_lastWasSep) {
					_list.insert(LuaString.valueOf(""));
					_n--;
				}
				_lastWasSep = true;
			}
		}
		if(_n==1)
		{
			_list.insert(LuaString.valueOf(_st.remainingString(true)));
		}
		return _list;
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
		char[] _str = _string.toCharArray();

		for(int _j = 2, _n = args.narg(); _j<=_n; _j+=2) {
			if(args.istable(_j)) {
				LuaTable _t = args.checktable(_j);
				for(LuaValue _k : _t.keys()) {
					_string = _string.replace(_k.tojstring(), _t.get(_k).tojstring());
				}
				_j--;
				_str = _string.toCharArray();
			} else {
				String _from = args.checkjstring(_j);
				String _to = args.checkjstring(_j+1);

				for(int _i = 0, _l = _string.length(); _i<_l; _i++) {
					int _idx = _from.indexOf(_string.charAt(_i));
					if(_idx==-1) continue;
					_idx = _idx<_to.length() ? _idx : _to.length()-1;
					_str[_i] = _to.charAt(_idx);
				}
				_string = new String(_str);
			}
		}
		return LuaString.valueOf(new String(_str));
	}

	// utf8.rxreplace(string, rx, to) -> string
	public static LuaValue _rxreplace(Varargs args) {
		String _string = args.checkjstring(1);
		String _from = args.checkjstring(2);
		String _to = args.checkjstring(3);

		_string = _string.replaceFirst(_from, _to);

		return LuaString.valueOf(_string);
	}

	// utf8.rxreplaceall(string, rx, to) -> string
	public static LuaValue _rxreplaceall(Varargs args) {
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
	public static final Varargs _indexof(Varargs args) {
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

	//   utf8.indexofany (s, n1 [, ..., nN]) -> int/nil
	public static final Varargs _indexofany(Varargs args) {
		String _str = args.checkjstring(1);

		int _r = Integer.MAX_VALUE;
		for(int _i = 2; _i<=args.narg(); _i++) {
			int _f = _str.indexOf(args.checkjstring(_i));
			if(_f!=-1 && _f<_r) {
				_r = _f;
			}
		}
		return _r==Integer.MAX_VALUE ? LuaValue.NIL : LuaInteger.valueOf(_r+1);
	}


	//   utf8.lastindexof (s, n [, i]) -> int/nil
	public static final Varargs _lastindexof(Varargs args) {
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

	//   utf8.lastindexofany (s, n [, i]) -> int/nil
	public static final Varargs _lastindexofany(Varargs args) {
		String _str = args.checkjstring(1);

		int _r = -1;
		for(int _i = 2; _i<=args.narg(); _i++) {
			int _f = _str.lastIndexOf(args.checkjstring(_i));
			if(_f!=-1 && _f>_r) {
				_r = _f;
			}
		}
		return _r<0 ? LuaValue.NIL : LuaInteger.valueOf(_r+1);
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
	
	// utf8.firstchar(s) -> string
	public static LuaValue _firstchar(Varargs args) {
		String _hay = args.checkjstring(1);
		return LuaString.valueOf(_hay.substring(0,1));
	}
	
	// utf8.charat(s,i) -> string
	public static LuaValue _charat(Varargs args) {
		String _hay = args.checkjstring(1);
		int _i = args.checkint(2);
		return LuaString.valueOf(_hay.substring(_i-1,_i));
	}
	
	// utf8.lastchar(s) -> string
	public static LuaValue _lastchar(Varargs args) {
		String _hay = args.checkjstring(1);
		int _i = _hay.length()-1;
		return LuaString.valueOf(_hay.substring(_i,_i+1));
	}
	
	// utf8.cutstr(s,n) -> string1, string 2
	public static Varargs _cutstr(Varargs args) {
		String _hay = args.checkjstring(1);
		int _n = args.checkint(2);
		return varargsOf(LuaString.valueOf(_hay.substring(0,_n)), LuaString.valueOf(_hay.substring(_n)));
	}
	
	// utf8.hammingdistance(s1,s1) -> i
	public static Varargs _hammingdistance(Varargs args) {
		String _hay1 = args.checkjstring(1);
		String _hay2 = args.checkjstring(2);
		if(_hay1.length() != _hay2.length()) return LuaInteger.valueOf(-1);
		int _d = 0;
		for(int _i = 0; _i<_hay2.length(); _i++)
		{
			if(_hay1.charAt(_i)!=_hay2.charAt(_i)) _d++;
		}
		return LuaInteger.valueOf(_d);
	}
	
	/*
	 *  Licensed to the Apache Software Foundation (ASF) under one or more
	 *  contributor license agreements.  See the NOTICE file distributed with
	 *  this work for additional information regarding copyright ownership.
	 *  The ASF licenses this file to You under the Apache License, Version 2.0
	 *  (the "License"); you may not use this file except in compliance with
	 *  the License.  You may obtain a copy of the License at
	 *
	 *     http://www.apache.org/licenses/LICENSE-2.0
	 *
	 *  Unless required by applicable law or agreed to in writing, software
	 *  distributed under the License is distributed on an "AS IS" BASIS,
	 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	 *  See the License for the specific language governing permissions and
	 *  limitations under the License.
	 */
	
	
	/**
	 * The {@code StringTokenizer} class allows an application to break a string
	 * into tokens by performing code point comparison. The {@code StringTokenizer}
	 * methods do not distinguish among identifiers, numbers, and quoted strings,
	 * nor do they recognize and skip comments.
	 * <p>
	 * The set of delimiters (the codepoints that separate tokens) may be specified
	 * either at creation time or on a per-token basis.
	 * <p>
	 * An instance of {@code StringTokenizer} behaves in one of three ways,
	 * depending on whether it was created with the {@code returnDelimiters} flag
	 * having the value {@code true} or {@code false}:
	 * <ul>
	 * <li>If returnDelims is {@code false}, delimiter code points serve to separate
	 * tokens. A token is a maximal sequence of consecutive code points that are not
	 * delimiters.
	 * <li>If returnDelims is {@code true}, delimiter code points are themselves
	 * considered to be tokens. In this case a token will be received for each
	 * delimiter code point.
	 * </ul>
	 * <p>
	 * A token is thus either one delimiter code point, or a maximal sequence of
	 * consecutive code points that are not delimiters.
	 * <p>
	 * A {@code StringTokenizer} object internally maintains a current position
	 * within the string to be tokenized. Some operations advance this current
	 * position past the code point processed.
	 * <p>
	 * A token is returned by taking a substring of the string that was used to
	 * create the {@code StringTokenizer} object.
	 * <p>
	 * Here's an example of the use of the default delimiter {@code StringTokenizer}
	 * : <blockquote>
	 *
	 * <pre>
	 * StringTokenizer st = new StringTokenizer(&quot;this is a test&quot;);
	 * while (st.hasMoreTokens()) {
	 *     println(st.nextToken());
	 * }
	 * </pre>
	 *
	 * </blockquote>
	 * <p>
	 * This prints the following output: <blockquote>
	 *
	 * <pre>
	 *     this
	 *     is
	 *     a
	 *     test
	 * </pre>
	 *
	 * </blockquote>
	 * <p>
	 * Here's an example of how to use a {@code StringTokenizer} with a user
	 * specified delimiter: <blockquote>
	 *
	 * <pre>
	 * StringTokenizer st = new StringTokenizer(
	 *         &quot;this is a test with supplementary characters \ud800\ud800\udc00\udc00&quot;,
	 *         &quot; \ud800\udc00&quot;);
	 * while (st.hasMoreTokens()) {
	 *     println(st.nextToken());
	 * }
	 * </pre>
	 *
	 * </blockquote>
	 * <p>
	 * This prints the following output: <blockquote>
	 *
	 * <pre>
	 *     this
	 *     is
	 *     a
	 *     test
	 *     with
	 *     supplementary
	 *     characters
	 *     \ud800
	 *     \udc00
	 * </pre>
	 *
	 * </blockquote>
	 */
	public static class CustomStringTokenizer implements Enumeration<Object>
	{
		
		private String string;
		
		private String delimiters;
		
		private boolean returnDelimiters;
		
		private int position;
		
		/**
		 * Constructs a new {@code StringTokenizer} for the parameter string using
		 * whitespace as the delimiter. The {@code returnDelimiters} flag is set to
		 * {@code false}.
		 *
		 * @param string
		 *            the string to be tokenized.
		 */
		public CustomStringTokenizer(String string) {
			this(string, " \t\n\r\f", false); //$NON-NLS-1$
		}
		
		/**
		 * Constructs a new {@code StringTokenizer} for the parameter string using
		 * the specified delimiters. The {@code returnDelimiters} flag is set to
		 * {@code false}. If {@code delimiters} is {@code null}, this constructor
		 * doesn't throw an {@code Exception}, but later calls to some methods might
		 * throw a {@code NullPointerException}.
		 *
		 * @param string
		 *            the string to be tokenized.
		 * @param delimiters
		 *            the delimiters to use.
		 */
		public CustomStringTokenizer(String string, String delimiters) {
			this(string, delimiters, false);
		}
		
		/**
		 * Constructs a new {@code StringTokenizer} for the parameter string using
		 * the specified delimiters, returning the delimiters as tokens if the
		 * parameter {@code returnDelimiters} is {@code true}. If {@code delimiters}
		 * is null this constructor doesn't throw an {@code Exception}, but later
		 * calls to some methods might throw a {@code NullPointerException}.
		 *
		 * @param string
		 *            the string to be tokenized.
		 * @param delimiters
		 *            the delimiters to use.
		 * @param returnDelimiters
		 *            {@code true} to return each delimiter as a token.
		 */
		public CustomStringTokenizer(String string, String delimiters,
									 boolean returnDelimiters) {
			if (string != null) {
				this.string = string;
				this.delimiters = delimiters;
				this.returnDelimiters = returnDelimiters;
				this.position = 0;
			} else
				throw new NullPointerException();
		}
		
		/**
		 * Returns the number of unprocessed tokens remaining in the string.
		 *
		 * @return number of tokens that can be retreived before an {@code
		 *         Exception} will result from a call to {@code nextToken()}.
		 */
		public int countTokens() {
			int count = 0;
			boolean inToken = false;
			for (int i = position, length = string.length(); i < length; i++) {
				if (delimiters.indexOf(string.charAt(i), 0) >= 0) {
					if (returnDelimiters)
						count++;
					if (inToken) {
						count++;
						inToken = false;
					}
				} else {
					inToken = true;
				}
			}
			if (inToken)
				count++;
			return count;
		}
		
		/**
		 * Returns {@code true} if unprocessed tokens remain. This method is
		 * implemented in order to satisfy the {@code Enumeration} interface.
		 *
		 * @return {@code true} if unprocessed tokens remain.
		 */
		public boolean hasMoreElements() {
			return hasMoreTokens();
		}
		
		/**
		 * Returns {@code true} if unprocessed tokens remain.
		 *
		 * @return {@code true} if unprocessed tokens remain.
		 */
		public boolean hasMoreTokens() {
			if (delimiters == null) {
				throw new NullPointerException();
			}
			int length = string.length();
			if (position < length) {
				if (returnDelimiters)
					return true; // there is at least one character and even if
				// it is a delimiter it is a token
				
				// otherwise find a character which is not a delimiter
				for (int i = position; i < length; i++)
					if (delimiters.indexOf(string.charAt(i), 0) == -1)
						return true;
			}
			return false;
		}
		
		/**
		 * Returns the next token in the string as an {@code Object}. This method is
		 * implemented in order to satisfy the {@code Enumeration} interface.
		 *
		 * @return next token in the string as an {@code Object}
		 * @throws NoSuchElementException
		 *                if no tokens remain.
		 */
		public Object nextElement() {
			return nextToken();
		}
		
		/**
		 * Returns the next token in the string as a {@code String}.
		 *
		 * @return next token in the string as a {@code String}.
		 * @throws NoSuchElementException
		 *                if no tokens remain.
		 */
		public String nextToken() {
			if (delimiters == null) {
				throw new NullPointerException();
			}
			int i = position;
			int length = string.length();
			
			if (i < length) {
				if (returnDelimiters) {
					if (delimiters.indexOf(string.charAt(position), 0) >= 0)
						return String.valueOf(string.charAt(position++));
					for (position++; position < length; position++)
						if (delimiters.indexOf(string.charAt(position), 0) >= 0)
							return string.substring(i, position);
					return string.substring(i);
				}
				
				while (i < length && delimiters.indexOf(string.charAt(i), 0) >= 0)
					i++;
				position = i;
				if (i < length) {
					for (position++; position < length; position++)
						if (delimiters.indexOf(string.charAt(position), 0) >= 0)
							return string.substring(i, position);
					return string.substring(i);
				}
			}
			throw new NoSuchElementException();
		}
		
		/**
		 * Returns the next token in the string as a {@code String}. The delimiters
		 * used are changed to the specified delimiters.
		 *
		 * @param delims
		 *            the new delimiters to use.
		 * @return next token in the string as a {@code String}.
		 * @throws NoSuchElementException
		 *                if no tokens remain.
		 */
		public String nextToken(String delims) {
			this.delimiters = delims;
			return nextToken();
		}
		
		public String remainingString()
		{
			return remainingString(false);
		}
		public String remainingString(boolean skipDelim)
		{
			if (skipDelim && (delimiters.indexOf(string.charAt(position), 0) == 0))
				return this.string.substring(this.position+1);
			return this.string.substring(this.position);
		}
	}
}
