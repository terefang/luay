package luay.lib.ext;

import lombok.SneakyThrows;
import luay.vm.*;
import luay.vm.lib.TwoArgFunction;
import luay.vm.lib.VarArgFunction;
import luay.lib.LuayLibrary;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractLibrary extends TwoArgFunction implements LuayLibrary
{
	public static <T> List<T> toList(T... _args)
	{
		return Arrays.asList(_args);
	}

	public interface _methodNameGetter
	{
		String methodName();
	}

	public List<Class> getLibraryFunctionClasses() { return Collections.emptyList(); }
	public List<LuaFunction> getLibraryFunctions() { return Collections.emptyList(); }
	public String getLibraryName() { return "*invalid*"; }
;

	public interface _zeroArgFunction
	{
		Varargs runFunction();
	}

	public static class _zeroArgFunctionWrapper extends VarArgFunction implements _methodNameGetter
	{
		public static LuaFunction from(String _name, _zeroArgFunction _f)
		{
			_zeroArgFunctionWrapper _fw = new _zeroArgFunctionWrapper();
			_fw._func = _f;
			_fw._name = _name;
			return _fw;
		}

		private String _name = null;
		private _zeroArgFunction _func = null;

		@Override
		public String methodName() {
			return this._name;
		}

		@Override
		public Varargs invoke(Varargs args) {
			return this._func.runFunction();
		}
	}

	public interface _oneArgFunction
	{
		Varargs runFunction(LuaValue arg);
	}

	public static class _oneArgFunctionWrapper extends VarArgFunction implements _methodNameGetter
	{
		public static LuaFunction from(String _name, _oneArgFunction _f)
		{
			_oneArgFunctionWrapper _fw = new _oneArgFunctionWrapper();
			_fw._func = _f;
			_fw._name = _name;
			return _fw;
		}

		private String _name = null;
		private _oneArgFunction _func = null;

		@Override
		public String methodName() {
			return this._name;
		}

		@Override
		public Varargs invoke(Varargs args) {
			return this._func.runFunction(args.arg(1));
		}
	}

	public interface _twoArgFunction
	{
		Varargs runFunction(LuaValue arg1, LuaValue arg2);
	}

	public static class _twoArgFunctionWrapper extends VarArgFunction implements _methodNameGetter
	{
		public static LuaFunction from(String _name, _twoArgFunction _f)
		{
			_twoArgFunctionWrapper _fw = new _twoArgFunctionWrapper();
			_fw._func = _f;
			_fw._name = _name;
			return _fw;
		}

		private String _name = null;
		private _twoArgFunction _func = null;

		@Override
		public String methodName() {
			return this._name;
		}

		@Override
		public Varargs invoke(Varargs args) {
			return this._func.runFunction(args.arg(1), args.arg(2));
		}
	}

	public interface _threeArgFunction
	{
		Varargs runFunction(LuaValue arg1, LuaValue arg2, LuaValue arg3);
	}

	public static class _threeArgFunctionWrapper extends VarArgFunction implements _methodNameGetter
	{
		public static LuaFunction from(String _name, _threeArgFunction _f)
		{
			_threeArgFunctionWrapper _fw = new _threeArgFunctionWrapper();
			_fw._func = _f;
			_fw._name = _name;
			return _fw;
		}

		private String _name = null;
		private _threeArgFunction _func = null;

		@Override
		public String methodName() {
			return this._name;
		}

		@Override
		public Varargs invoke(Varargs args) {
			return this._func.runFunction(args.arg(1), args.arg(2), args.arg(3));
		}
	}

	public interface _varArgFunction
	{
		Varargs runFunction(Varargs args);
	}

	public static class _varArgFunctionWrapper extends VarArgFunction implements _methodNameGetter
	{
		public static LuaFunction from(String _name, _varArgFunction _f)
		{
			_varArgFunctionWrapper _fw = new _varArgFunctionWrapper();
			_fw._func = _f;
			_fw._name = _name;
			return _fw;
		}

		private String _name = null;
		private _varArgFunction _func = null;

		@Override
		public String methodName() {
			return this._name;
		}

		@Override
		public Varargs invoke(Varargs args) {
			return this._func.runFunction(args);
		}

		@Override
		public Varargs invoke() {
			return super.invoke();
		}

		@Override
		public Varargs invoke(LuaValue arg, Varargs varargs) {
			return super.invoke(arg, varargs);
		}

		@Override
		public Varargs invoke(LuaValue arg1, LuaValue arg2, Varargs varargs) {
			return super.invoke(arg1, arg2, varargs);
		}

		@Override
		public Varargs invoke(LuaValue[] args) {
			return super.invoke(args);
		}

		@Override
		public Varargs invoke(LuaValue[] args, Varargs varargs) {
			return super.invoke(args, varargs);
		}

		@Override
		public Varargs invokemethod(String name) {
			return super.invokemethod(name);
		}

		@Override
		public Varargs invokemethod(LuaValue name) {
			return super.invokemethod(name);
		}

		@Override
		public Varargs invokemethod(String name, Varargs args) {
			return super.invokemethod(name, args);
		}

		@Override
		public Varargs invokemethod(LuaValue name, Varargs args) {
			return super.invokemethod(name, args);
		}

		@Override
		public Varargs invokemethod(String name, LuaValue[] args) {
			return super.invokemethod(name, args);
		}

		@Override
		public Varargs invokemethod(LuaValue name, LuaValue[] args) {
			return super.invokemethod(name, args);
		}
	}

	public String getName() {
		return this.getLibraryName();
	}

	public LuayLibrary getInstance() {
		return this;
	}

	@Override
	@SneakyThrows
	public LuaValue call(LuaValue modname, LuaValue env) {
		LuaTable string = new LuaTable();

		for(Class _fn : this.getLibraryFunctionClasses())
		{
			String _n = _fn.getSimpleName();
			if(_n.startsWith("_"))
			{
				_n = _n.substring(1);
			}
			//System.err.println(_n);
			Object _inst = _fn.newInstance();
			if(_inst instanceof _methodNameGetter)
			{
				_n = ((_methodNameGetter)_inst).methodName();
			}
			string.set(_n, (LuaValue) _inst);
		}

		for(LuaFunction _fn : this.getLibraryFunctions())
		{
			String _n = _fn.getClass().getSimpleName();

			if(_fn instanceof _methodNameGetter)
			{
				_n = ((_methodNameGetter)_fn).methodName();
			}
			string.set(_n, (LuaValue) _fn);
		}

		customizeLibraryPackage(this.getLibraryName(), string);

		env.set(this.getLibraryName(), string);

		if (!env.get("package").isnil()) env.get("package").get("loaded").set(this.getLibraryName(), string);

		return string;
	}

	public void customizeLibraryPackage(String _pname, LuaTable _package)
	{

	}
}
