# Extending the Runtime with Libraries

These are my observations on how to add additional libraries to the runtime that can be `require`'d.

## The old LuaJ Way.

In LuaJ each available library comes in 3 forms:

* A `.lua` file on the filesystem or classpath (ie. searchpath), using the standard lua way.
* A predefined/preloaded Java Class that extends from TwoArgFunction that is provided to the runtime at setuptime.
    This is the most restrictive option as the library must provide its own setup and the executer the registration code.
* A Java Class that extends from TwoArcFunction available on classpath, with exact naming conventions.
    This can either return a library or a single function.

### Library Case

```java
public class extlib extends TwoArgFunction
{
    public extlib() {}
    
    @Override
	public LuaValue call(LuaValue modname, LuaValue env) {
		// setup code
        LuaTable _extlib = new LuaTable();
		_extlib.set('extfunction', new _extfunction());
		env.set("extlib", _extlib);
		if (!env.get("package").isnil())
			env.get("package").get("loaded").set("extlib", _extlib);
		return _extlib;
	}

    static class _extfunction extends VarArgFunction
    {
        @Override
    	public Varargs invoke(Varargs args) {
            // function code
    		return ...;
    	}
    }
}
```

### Single Function Case

```java
public class jtest extends TwoArgFunction
{
    @Override
    public LuaValue call(LuaValue _name, LuaValue _env) {
        return AbstractLibrary._zeroArgFunctionWrapper.from(_name.tojstring(),jtest::_call);
    }

    public static LuaValue _call()
    {
        return LuaValue.TRUE;
    }
}
```

## LuayBuilder support for preload.

```java
luayContext = LuayBuilder.create()
    .inputStream(System.in)
    .outputStream(System.out)
    .errorStream(System.err)
    .baseLibraries()
    .extensionLibrary(new extlib())
    .build();
```

## The Luay Way

Luay extends the library loader with a serviceloader, so classes can have arbitrary names and paths.

### LuaySimpleLibraryFactory

This is the simplest option. You implement a glue-factory class that provides proper 
library name and object instance, the java object will be auto-coerced and all 
methods be exposed.

```java
public class ExtLibFactory implements LuaySimpleLibraryFactory
{
    @Override
    public String getName() {
        return "extlib";
    }

    @Override
    public Object getInstance() {
        return Class.forName("ext.ExtLib").newInstance();
    }
}

public class ExtLib
{
    // library methods
    // ...
}
```

### LuayLibraryFactory

If you need more control over which methods get exposed, use this option.
Note: your class needs to extend AbstractLibrary and some setup code.

```java
public class ExtLibFactory implements LuayLibraryFactory
{
    @Override
    public String getName() {
        return "extlib";
    }

    @Override
    @SneakyThrows
    public AbstractLibrary getInstance() {
        return (AbstractLibrary) Class.forName("ext.ExtLib").newInstance();
    }
}

public class ExtLib extends AbstractLibrary
{
	@Override
	public List<LuaFunction> getLibraryFunctions() {
		return toList(
				_varArgFunctionWrapper.from("extfunc", ExtLib::extFunc)
		);
	}

	public static LuaValue extFunc(Varargs args) {
        // function code ...
	}

}
```

\pagebreak
