/*******************************************************************************
* Copyright (c) 2009 Luaj.org. All rights reserved.
*
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in
* all copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
* THE SOFTWARE.
******************************************************************************/
package luay.vm.lib;

import java.io.*;

import luay.main.LuayHelper;
import luay.vm.*;

import luay.vm.lib.java.JsePlatform;
import luay.lib.LuayStringifierFunction;

/**
 * Subclass of {@link LibFunction} which implements the luay.main.lua basic library
 * functions.
 * <p>
 * This contains all library functions listed as "basic functions" in the luay.main.lua
 * documentation for JME. The functions dofile and loadfile use the
 * {@link Globals#finder} instance to find resource files. Since JME has no file
 * system by default, {@link BaseLib} implements {@link ResourceFinder} using
 * {@link Class#getResource(String)}, which is the closest equivalent on JME.
 * The default loader chain in {@link PackageLib} will use these as well.
 * <p>
 * To use basic library functions that include a {@link ResourceFinder} based on
 * directory lookup.
 * <p>
 * Typically, this library is included as part of a call to either
 * {@link JsePlatform#standardGlobals()}
 *
 * <pre>
 * {
 * 	&#64;code
 * 	Globals globals = JsePlatform.standardGlobals();
 * 	globals.get("print").call(LuaValue.valueOf("hello, world"));
 * }
 * </pre>
 * <p>
 * For special cases where the smallest possible footprint is desired, a minimal
 * set of libraries could be loaded directly via {@link Globals#load(LuaValue)}
 * using code such as:
 *
 * <pre>
 * {
 * 	&#64;code
 * 	Globals globals = new Globals();
 * 	globals.load(new JseBaseLib());
 * 	globals.get("print").call(LuaValue.valueOf("hello, world"));
 * }
 * </pre>
 *
 * Doing so will ensure the library is properly initialized and loaded into the
 * globals table.
 * <p>
 * This is a direct port of the corresponding library in C.
 *
 * @see ResourceFinder
 * @see Globals#finder
 * @see LibFunction
 * @see JsePlatform
 * @see <a href="http://www.lua.org/manual/5.2/manual.html#6.1">Lua 5.2 Base Lib
 *      Reference</a>
 */
public class BaseLib extends TwoArgFunction implements ResourceFinder {

	Globals globals;

	/**
	 * Perform one-time initialization on the library by adding base functions
	 * to the supplied environment, and returning it as the return value.
	 *
	 * @param modname the module name supplied if this is loaded via 'require'.
	 * @param env     the environment to load into, which must be a Globals
	 *                instance.
	 */
	@Override
	public LuaValue call(LuaValue modname, LuaValue env) {
		globals = env.checkglobals();
		globals.finder = this;
		globals.baselib = this;
		globals.STDIN = System.in;

		env.set("_G", env);
		env.set("_VERSION", Lua._VERSION);
		env.set("assert", new _assert());
		env.set("collectgarbage", new collectgarbage());
		env.set("dofile", new dofile());
		env.set("error", new error());
		env.set("getmetatable", new getmetatable());
		env.set("load", new load());
		env.set("loadfile", new loadfile());
		env.set("pcall", new pcall());
		env.set("print", new print(this));
		env.set("rawequal", new rawequal());
		env.set("rawget", new rawget());
		env.set("rawlen", new rawlen());
		env.set("rawset", new rawset());
		env.set("select", new select());
		env.set("setmetatable", new setmetatable());
		env.set("tonumber", new tonumber());
		env.set("tostring", new tostring());
		env.set("type", new type());
		env.set("xpcall", new xpcall());
		next next;
		env.set("next", next = new next());
		env.set("pairs", new pairs(next));
		env.set("ipairs", new ipairs());

		// --- LUAY extensions
		env.set("stringify", LuayStringifierFunction.INSTANCE);
		env.set("printnl", new printnl(this));
		env.set("printf", new printf());
		env.set("apairs", new apairs());

		env.set("mkmap", new _map());
		env.set("mklist", new _list());
		return env;
	}

	/**
	 * Try to open a file in the current working directory, or fall back to base
	 * opener if not found.
	 *
	 * This implementation attempts to open the file using new File(filename).
	 * It falls back to the base implementation that looks it up as a resource
	 * in the class path if not found as a plain file.
	 *
	 * @see BaseLib
	 * @see ResourceFinder
	 *
	 * @param filename
	 * @return InputStream, or null if not found.
	 */
	public InputStream findResource(String filename) {
		File f = new File(filename);
		if (!f.exists())
			return _findResource(filename);
		try {
			return new BufferedInputStream(new FileInputStream(f));
		} catch (IOException ioe) {
			return null;
		}
	}

	/**
	 * ResourceFinder implementation
	 *
	 * Tries to open the file as a resource, which can work for JSE and JME.
	 */
	public InputStream _findResource(String filename) {
		return getClass().getResourceAsStream(filename.startsWith("/")? filename: "/" + filename);
	}

	// "assert", // ( v [,message] ) -> v, message | ERR
	static final class _assert extends VarArgFunction {
		@Override
		public Varargs invoke(Varargs args) {
			if (!args.arg1().toboolean())
				error(args.narg() > 1? args.optjstring(2, "assertion failed!"): "assertion failed!");
			return args;
		}
	}

	// "collectgarbage", // ( opt [,arg] ) -> value
	static final class collectgarbage extends VarArgFunction {
		@Override
		public Varargs invoke(Varargs args) {
			String s = args.optjstring(1, "collect");
			if ("collect".equals(s)) {
				System.gc();
				return ZERO;
			} else if ("count".equals(s)) {
				Runtime rt = Runtime.getRuntime();
				long used = rt.totalMemory()-rt.freeMemory();
				return varargsOf(valueOf(used/1024.), valueOf(used%1024));
			} else if ("step".equals(s)) {
				System.gc();
				return LuaValue.TRUE;
			} else {
				argerror(1, "invalid option '" + s + "'");
			}
			return NIL;
		}
	}

	// "dofile", // ( filename ) -> result1, ...
	final class dofile extends VarArgFunction {
		@Override
		public Varargs invoke(Varargs args) {
			args.argcheck(args.isstring(1) || args.isnil(1), 1, "filename must be string or nil");
			String filename = args.isstring(1)? args.tojstring(1): null;
			Varargs v = filename == null? loadStream(globals.STDIN, "=stdin", "bt", globals)
				: loadFile(args.checkjstring(1), "bt", globals);
			return v.isnil(1)? error(v.tojstring(2)): v.arg1().invoke();
		}
	}

	// "error", // ( message [,level] ) -> ERR
	static final class error extends TwoArgFunction {
		@Override
		public LuaValue call(LuaValue arg1, LuaValue arg2) {
			if (arg1.isnil())
				throw new LuaError(NIL);
			if (!arg1.isstring() || arg2.optint(1) == 0)
				throw new LuaError(arg1);
			throw new LuaError(arg1.tojstring(), arg2.optint(1));
		}
	}

	// "getmetatable", // ( object ) -> table
	static final class getmetatable extends LibFunction {
		@Override
		public LuaValue call() {
			return argerror(1, "value expected");
		}

		@Override
		public LuaValue call(LuaValue arg) {
			LuaValue mt = arg.getmetatable();
			return mt != null? mt.rawget(METATABLE).optvalue(mt): NIL;
		}
	}

	// "load", // ( ld [, source [, mode [, env]]] ) -> chunk | nil, msg
	final class load extends VarArgFunction {
		@Override
		public Varargs invoke(Varargs args) {
			LuaValue ld = args.arg1();
			if (!ld.isstring() && !ld.isfunction()) {
				throw new LuaError(
					"bad argument #1 to 'load' (string or function expected, got " + ld.typename() + ")");
			}
			String source = args.optjstring(2, ld.isstring()? ld.tojstring(): "=(load)");
			String mode = args.optjstring(3, "bt");
			LuaValue env = args.optvalue(4, globals);
			return loadStream(ld.isstring()? ld.strvalue().toInputStream(): new StringInputStream(ld.checkfunction()),
				source, mode, env);
		}
	}

	// "loadfile", // ( [filename [, mode [, env]]] ) -> chunk | nil, msg
	final class loadfile extends VarArgFunction {
		@Override
		public Varargs invoke(Varargs args) {
			args.argcheck(args.isstring(1) || args.isnil(1), 1, "filename must be string or nil");
			String filename = args.isstring(1)? args.tojstring(1): null;
			String mode = args.optjstring(2, "bt");
			LuaValue env = args.optvalue(3, globals);
			return filename == null? loadStream(globals.STDIN, "=stdin", mode, env): loadFile(filename, mode, env);
		}
	}

	// "pcall", // (f, arg1, ...) -> status, result1, ...
	final class pcall extends VarArgFunction {
		@Override
		public Varargs invoke(Varargs args) {
			LuaValue func = args.checkvalue(1);
			if (globals != null && globals.debuglib != null)
				globals.debuglib.onCall(this);
			try {
				return varargsOf(TRUE, func.invoke(args.subargs(2)));
			} catch (LuaError le) {
				final LuaValue m = le.getMessageObject();
				return varargsOf(FALSE, m != null? m: NIL);
			} catch (Exception e) {
				final String m = e.getMessage();
				return varargsOf(FALSE, valueOf(m != null? m: e.toString()));
			} finally {
				if (globals != null && globals.debuglib != null)
					globals.debuglib.onReturn();
			}
		}
	}

	// "print", // (...) -> void
	final class print extends VarArgFunction {
		final BaseLib baselib;

		print(BaseLib baselib) {
			this.baselib = baselib;
		}

		@Override
		public Varargs invoke(Varargs args) {
			LuaValue tostring = globals.get("tostring");
			for (int i = 1, n = args.narg(); i <= n; i++) {
				if (i > 1)
					globals.STDOUT.print('\t');
				LuaString s = tostring.call(args.arg(i)).strvalue();
				String _s = null;
				try
				{
					_s = s.tojstring();
				}
				catch (Exception _xe)
				{
					_s = new String(s.tojbytes());
				}
				globals.STDOUT.print(_s);
			}
			globals.STDOUT.print('\n');
			globals.STDOUT.flush();
			return NONE;
		}
	}

	final class printnl extends VarArgFunction {
		final BaseLib baselib;

		printnl(BaseLib baselib) {
			this.baselib = baselib;
		}

		@Override
		public Varargs invoke(Varargs args) {
			LuaValue tostring = globals.get("tostring");
			for (int i = 1, n = args.narg(); i <= n; i++) {
				if (i > 1)
					globals.STDOUT.print('\t');
				LuaString s = tostring.call(args.arg(i)).strvalue();
				globals.STDOUT.print(s.tojstring());
			}
			globals.STDOUT.flush();
			return NONE;
		}
	}

	// "rawequal", // (v1, v2) -> boolean
	static final class rawequal extends LibFunction {
		@Override
		public LuaValue call() {
			return argerror(1, "value expected");
		}

		@Override
		public LuaValue call(LuaValue arg) {
			return argerror(2, "value expected");
		}

		@Override
		public LuaValue call(LuaValue arg1, LuaValue arg2) {
			return valueOf(arg1.raweq(arg2));
		}
	}

	// "rawget", // (table, index) -> value
	static final class rawget extends TableLibFunction {
		@Override
		public LuaValue call(LuaValue arg) {
			return argerror(2, "value expected");
		}

		@Override
		public LuaValue call(LuaValue arg1, LuaValue arg2) {
			return arg1.checktable().rawget(arg2);
		}
	}

	// "rawlen", // (v) -> value
	static final class rawlen extends LibFunction {
		@Override
		public LuaValue call(LuaValue arg) {
			return valueOf(arg.rawlen());
		}
	}

	// "rawset", // (table, index, value) -> table
	static final class rawset extends TableLibFunction {
		@Override
		public LuaValue call(LuaValue table) {
			return argerror(2, "value expected");
		}

		@Override
		public LuaValue call(LuaValue table, LuaValue index) {
			return argerror(3, "value expected");
		}

		@Override
		public LuaValue call(LuaValue table, LuaValue index, LuaValue value) {
			LuaTable t = table.checktable();
			if (!index.isvalidkey())
				argerror(2, "table index is nil");
			t.rawset(index, value);
			return t;
		}
	}

	// "select", // (f, ...) -> value1, ...
	static final class select extends VarArgFunction {
		@Override
		public Varargs invoke(Varargs args) {
			int n = args.narg()-1;
			if (args.arg1().equals(valueOf("#")))
				return valueOf(n);
			int i = args.checkint(1);
			if (i == 0 || i < -n)
				argerror(1, "index out of range");
			return args.subargs(i < 0? n+i+2: i+1);
		}
	}

	// "setmetatable", // (table, metatable) -> table
	static final class setmetatable extends TableLibFunction {
		@Override
		public LuaValue call(LuaValue table) {
			return argerror(2, "nil or table expected");
		}

		@Override
		public LuaValue call(LuaValue table, LuaValue metatable) {
			final LuaValue mt0 = table.checktable().getmetatable();
			if (mt0 != null && !mt0.rawget(METATABLE).isnil())
				error("cannot change a protected metatable");
			return table.setmetatable(metatable.isnil()? null: metatable.checktable());
		}
	}

	// "tonumber", // (e [,base]) -> value
	static final class tonumber extends LibFunction {
		@Override
		public LuaValue call(LuaValue e) {
			return e.tonumber();
		}

		@Override
		public LuaValue call(LuaValue e, LuaValue base) {
			if (base.isnil())
				return e.tonumber();
			final int b = base.checkint();
			if (b < 2 || b > 36)
				argerror(2, "base out of range");
			return e.checkstring().tonumber(b);
		}
	}

	// "tostring", // (e) -> value
	static final class tostring extends LibFunction {
		@Override
		public LuaValue call(LuaValue arg) {
			LuaValue h = arg.metatag(TOSTRING);
			if (!h.isnil())
				return h.call(arg);
			LuaValue v = arg.tostring();
			if (!v.isnil())
				return v;
			return valueOf(arg.tojstring());
		}
	}

	// "type",  // (v) -> value
	static final class type extends LibFunction {
		@Override
		public LuaValue call(LuaValue arg) {
			return valueOf(arg.typename());
		}
	}

	// "xpcall", // (f, err) -> result1, ...
	final class xpcall extends VarArgFunction {
		@Override
		public Varargs invoke(Varargs args) {
			final LuaThread t = globals.running;
			final LuaValue preverror = t.errorfunc;
			t.errorfunc = args.checkvalue(2);
			try {
				if (globals != null && globals.debuglib != null)
					globals.debuglib.onCall(this);
				try {
					return varargsOf(TRUE, args.arg1().invoke(args.subargs(3)));
				} catch (LuaError le) {
					final LuaValue m = le.getMessageObject();
					return varargsOf(FALSE, m != null? m: NIL);
				} catch (Exception e) {
					final String m = e.getMessage();
					return varargsOf(FALSE, valueOf(m != null? m: e.toString()));
				} finally {
					if (globals != null && globals.debuglib != null)
						globals.debuglib.onReturn();
				}
			} finally {
				t.errorfunc = preverror;
			}
		}
	}

	// "pairs" (t) -> iter-func, t, nil
	static final class pairs extends VarArgFunction {
		final next next;

		pairs(next next) {
			this.next = next;
		}

		@Override
		public Varargs invoke(Varargs args)
		{
			LuaValue _val = args.checkvalue(1);
			if(_val.isuserdata() && _val.istable())
			{
				// this is userdate object posing as a map
			}
			else
			{
				_val = args.checktable(1);
			}
			return varargsOf(next, _val, NIL);
		}
	}

	// // "ipairs", // (t) -> iter-func, t, 0
	static final class ipairs extends VarArgFunction {
		inext inext = new inext();

		@Override
		public Varargs invoke(Varargs args)
		{
			LuaValue _val = args.checkvalue(1);
			if(_val.isuserdata() && _val.isarray())
			{
				// this is userdate object posing as a list
			}
			else
			{
				_val = args.checktable(1);
			}
			return varargsOf(inext, _val, ZERO);
		}
	}

	// "apairs", // (v1[,...,vN]) -> iter-func, t, 0
	static final class apairs extends VarArgFunction {
		inext inext = new inext();

		@Override
		public Varargs invoke(Varargs args)
		{
			int _len = args.narg();
			LuaList _list = new LuaList(_len);
			for(int _i=0; _i<_len; _i++)
			{
				_list.insert(args.arg(_i+1));
			}
			return varargsOf(inext, _list, ZERO);
		}
	}

	// "next"  ( table, [index] ) -> next-index, next-value
	static final class next extends VarArgFunction {
		@Override
		public Varargs invoke(Varargs args)
		{
			LuaValue _val = args.checkvalue(1);
			if(_val.isuserdata() && _val.istable())
			{
				// this is userdate object posing as a map
			}
			else
			{
				_val = args.checktable(1);
			}
			return _val.next(args.arg(2));
		}
	}

	// "inext" ( table, [int-index] ) -> next-index, next-value
	static final class inext extends VarArgFunction {
		@Override
		public Varargs invoke(Varargs args) {
			LuaValue _val = args.checkvalue(1);
			if(_val.isuserdata() && _val.isarray())
			{
				// this is userdate object posing as a list
			}
			else
			{
				_val = args.checktable(1);
			}
			return _val.inext(args.arg(2));
		}
	}

	/**
	 * Load from a named file, returning the chunk or nil,error of can't load
	 *
	 * @param env
	 * @param mode
	 * @return Varargs containing chunk, or NIL,error-text on error
	 */
	public Varargs loadFile(String filename, String mode, LuaValue env) {
		InputStream is = globals.finder.findResource(filename);
		if (is == null)
			return varargsOf(NIL, valueOf("cannot open " + filename + ": No such file or directory"));
		try {
			return loadStream(is, "@" + filename, mode, env);
		} finally {
			try {
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Varargs loadStream(InputStream is, String chunkname, String mode, LuaValue env) {
		try {
			if (is == null)
				return varargsOf(NIL, valueOf("not found: " + chunkname));
			return globals.load(is, chunkname, mode, env);
		} catch (Exception e) {
			return varargsOf(NIL, valueOf(e.getMessage()));
		}
	}

	private static class StringInputStream extends InputStream {
		final LuaValue func;
		byte[]         bytes;
		int            offset, remaining = 0;

		StringInputStream(LuaValue func) {
			this.func = func;
		}

		@Override
		public int read() throws IOException {
			if (remaining < 0)
				return -1;
			if (remaining == 0) {
				LuaValue s = func.call();
				if (s.isnil())
					return remaining = -1;
				LuaString ls = s.strvalue();
				bytes = ls.m_bytes;
				offset = ls.m_offset;
				remaining = ls.m_length;
				if (remaining <= 0)
					return -1;
			}
			--remaining;
			return 0xFF & bytes[offset++];
		}
	}

	// --- Luay Extensions
	// printf(...)
	final class printf extends VarArgFunction {

		printf() {}

		@Override
		public Varargs invoke(Varargs args)
		{
			String _fmt = args.checkjstring(1);
			globals.STDOUT.print(String.format(_fmt, varArgsTo(args.subargs(2))));
			return NONE;
		}
	}

	// --- Luay Extensions
	// mkmap(...)
	final class _map extends VarArgFunction {

		_map() {}

		@Override
		public Varargs invoke(Varargs args)
		{
			return LuayHelper.varargsToTable(args);
		}
	}

	// --- Luay Extensions
	// mklist(...)
	final class _list extends VarArgFunction {

		_list() {}

		@Override
		public Varargs invoke(Varargs args)
		{
			return LuayHelper.varargsToList(args);
		}
	}

	public static Object[] varArgsTo(Varargs args)
	{
		Object[] _farg = new Object[args.narg()];

		for (int i = 1, n = args.narg(); i <= n; i++)
		{
			if(args.arg(i).isnil())
			{
				_farg[i-1] = "nil";
			}
			else
			if(args.arg(i).isboolean())
			{
				_farg[i-1] = args.checkboolean(i);
			}
			else
			if(args.arg(i).islong())
			{
				_farg[i-1] = args.checklong(i);
			}
			else
			if(args.arg(i).isinttype())
			{
				_farg[i-1] = args.checkint(i);
			}
			else
			if(args.arg(i).isnumber())
			{
				_farg[i-1] = args.checkdouble(i);
			}
			else
			if(args.arg(i).isstring())
			{
				_farg[i-1] = args.checkjstring(i);
			}
			else
			{
				_farg[i-1] = args.arg(i).tojstring();
			}
		}
		return _farg;
	}

}
