/*******************************************************************************
* Copyright (c) 2012 Luaj.org. All rights reserved.
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
package luay.lib.ext.core;
/*
%!begin MARKDOWN

## boolean Operations

```
local boolean = require('boolean');
```

* `boolean.and ( bool [, ... , bool]) -> bool`
* `boolean.nand ( bool [, ... , bool]) -> bool`
* `boolean.or ( bool [, ... , bool]) -> bool`
* `boolean.nor ( bool [, ... , bool]) -> bool`
* `boolean.xor ( bool [, ... , bool]) -> bool`
* `boolean.eq ( bool [, ... , bool]) -> bool`
* `boolean.not ( bool ) -> bool`
* `boolean.true() -> bool`
* `boolean.false() -> bool`
* `boolean.is_true( bool ) -> bool`
* `boolean.is_false( bool ) -> bool`

\pagebreak

%!end MARKDOWN

 */

import luay.lib.LuayLibraryFactory;
import luay.lib.ext.AbstractLibrary;
import luay.vm.LuaBoolean;
import luay.vm.LuaFunction;
import luay.vm.LuaValue;
import luay.vm.Varargs;

import java.util.List;

public class BooleanLib extends AbstractLibrary implements LuayLibraryFactory
{
	@Override
	public String getLibraryName() {
		return "boolean";
	}

	@Override
	public List<LuaFunction> getLibraryFunctions() {
		return toList(
				_varArgFunctionWrapper.from("eq", BooleanLib::_eq),
				_varArgFunctionWrapper.from("and", BooleanLib::_and),
				_varArgFunctionWrapper.from("nand", BooleanLib::_nand),
				_varArgFunctionWrapper.from("or", BooleanLib::_or),
				_varArgFunctionWrapper.from("not", BooleanLib::_not),
				_varArgFunctionWrapper.from("xor", BooleanLib::_xor),
				_varArgFunctionWrapper.from("nor", BooleanLib::_nor),
				_varArgFunctionWrapper.from("true", BooleanLib::_true),
				_varArgFunctionWrapper.from("false", BooleanLib::_false),
				_varArgFunctionWrapper.from("is_true", BooleanLib::_is_true),
				_varArgFunctionWrapper.from("is_false", BooleanLib::_is_false)
		);
	}

	public static LuaValue _eq(Varargs args) {
		boolean _b = args.checkboolean(1);
		for(int _i=2, _n=args.narg(); _i<=_n; _i++)
		{
			if(_b != args.checkboolean(_i))
			{
				return LuaValue.FALSE;
			}
		}
		return LuaValue.TRUE;
	}

	public static LuaValue _and(Varargs args) {
		boolean _b = args.checkboolean(1);
		for(int _i=2, _n=args.narg(); _i<=_n; _i++)
		{
			_b = args.checkboolean(_i) && _b;
		}
		return LuaValue.valueOf(_b);
	}

	public static LuaValue _nand(Varargs args) {
		boolean _b = args.checkboolean(1);
		for(int _i=2, _n=args.narg(); _i<=_n; _i++)
		{
			_b = args.checkboolean(_i) && _b;
		}
		return LuaValue.valueOf(!_b);
	}

	public static LuaValue _or(Varargs args) {
		boolean _b = args.checkboolean(1);
		for(int _i=2, _n=args.narg(); _i<=_n; _i++)
		{
			_b = args.checkboolean(_i) || _b;
		}
		return LuaValue.valueOf(_b);
	}

	public static LuaValue _xor(Varargs args) {
		boolean _b = args.checkboolean(1);
		for(int _i=2, _n=args.narg(); _i<=_n; _i++)
		{
			_b = args.checkboolean(_i) ^ _b;
		}
		return LuaValue.valueOf(_b);
	}

	public static LuaValue _nor(Varargs args) {
		boolean _b = args.checkboolean(1);
		for(int _i=2, _n=args.narg(); _i<=_n; _i++)
		{
			_b = args.checkboolean(_i) || _b;
		}
		return LuaValue.valueOf(!_b);
	}

	public static LuaValue _not(Varargs args) {
		return args.checkvalue(1).not();
	}

	public static LuaValue _true(Varargs args) {
		return LuaValue.TRUE;
	}

	public static LuaValue _false(Varargs args) {
		return LuaValue.FALSE;
	}

	public static LuaValue _is_true(Varargs args) {
		boolean _b = args.checkboolean(1);
		return LuaValue.valueOf(_b==true);
	}

	public static LuaValue _is_false(Varargs args) {
		boolean _b = args.checkboolean(1);
		return LuaValue.valueOf(_b==false);
	}

}
