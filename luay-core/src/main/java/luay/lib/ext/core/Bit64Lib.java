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

## 64 Bitwise Operations

```
local bit64 = require('bit64');
```

*   `bit64.arshift (x, disp)`
*   `bit64.band (···)`
*   `bit64.bnot (x)`
*   `bit64.bor (···)`
*   `bit64.btest (···)`
*   `bit64.bxor (···)`
*   `bit64.extract (n, field [, width])`
*   `bit64.replace (n, v, field [, width])`
*   `bit64.lrotate (x, disp)`
*   `bit64.lshift (x, disp)`
*   `bit64.rrotate (x, disp)`
*   `bit64.rshift (x, disp)`

\pagebreak

%!end MARKDOWN

 */

import luay.lib.ext.AbstractLibrary;
import luay.vm.LuaFunction;
import luay.vm.LuaValue;
import luay.vm.Varargs;
import luay.lib.LuayLibraryFactory;

import java.util.List;

public class Bit64Lib extends AbstractLibrary implements LuayLibraryFactory
{
	@Override
	public String getLibraryName() {
		return "bit64";
	}

	@Override
	public List<LuaFunction> getLibraryFunctions() {
		return toList(
				_varArgFunctionWrapper.from("band", Bit64Lib::band),
				_varArgFunctionWrapper.from("bor", Bit64Lib::bor),
				_varArgFunctionWrapper.from("bnot", Bit64Lib::bnot),
				_varArgFunctionWrapper.from("btest", Bit64Lib::btest),
				_varArgFunctionWrapper.from("bxor", Bit64Lib::bxor),
				_varArgFunctionWrapper.from("lrotate", Bit64Lib::lrotate),
				_varArgFunctionWrapper.from("rrotate", Bit64Lib::rrotate),
				_varArgFunctionWrapper.from("lshift", Bit64Lib::lshift),
				_varArgFunctionWrapper.from("rshift", Bit64Lib::rshift),
				_varArgFunctionWrapper.from("arshift", Bit64Lib::arshift),
				_varArgFunctionWrapper.from("extract", Bit64Lib::extract),
				_varArgFunctionWrapper.from("replace", Bit64Lib::replace)
		);
	}

	public static LuaValue arshift(Varargs args) {
		long x = args.checklong(1);
		int disp = args.checkint(2);
		if (disp >= 0) {
			return bitsToValue(x>>disp);
		} else {
			return bitsToValue(x<<-disp);
		}
	}

	public static LuaValue rshift(Varargs args) {
		long x = args.checklong(1);
		int disp = args.checkint(2);
		if (disp >= 64 || disp <= -64) {
			return LuaValue.ZERO;
		} else if (disp >= 0) {
			return bitsToValue(x>>>disp);
		} else {
			return bitsToValue(x<<-disp);
		}
	}

	public static LuaValue lshift(Varargs args) {
		long x = args.checklong(1);
		int disp = args.checkint(2);
		if (disp >= 64 || disp <= -64) {
			return LuaValue.ZERO;
		} else if (disp >= 0) {
			return bitsToValue(x<<disp);
		} else {
			return bitsToValue(x>>>-disp);
		}
	}

	public static Varargs band(Varargs args) {
		long result = -1L;
		for (int i = 1; i <= args.narg(); i++) {
			result &= args.checklong(i);
		}
		return bitsToValue(result);
	}

	public static Varargs bnot(Varargs args) {
		return bitsToValue(~args.checklong(1));
	}

	public static Varargs bor(Varargs args) {
		long result = 0;
		for (int i = 1; i <= args.narg(); i++) {
			result |= args.checklong(i);
		}
		return bitsToValue(result);
	}

	public static Varargs btest(Varargs args) {
		long bits = -1L;
		for (int i = 1; i <= args.narg(); i++) {
			bits &= args.checklong(i);
		}
		return LuaValue.valueOf(bits != 0);
	}

	public static Varargs bxor(Varargs args) {
		long result = 0;
		for (int i = 1; i <= args.narg(); i++) {
			result ^= args.checklong(i);
		}
		return bitsToValue(result);
	}

	public static LuaValue lrotate(Varargs args) {
		long x = args.checklong(1);
		int disp = args.checkint(2);
		if (disp < 0) {
			return rrotate(LuaValue.varargsOf(args.arg(1), LuaValue.valueOf(-disp)));
		} else {
			disp = disp & 63;
			return bitsToValue(x<<disp | x>>>64-disp);
		}
	}

	public static LuaValue rrotate(Varargs args) {
		long x = args.checklong(1);
		int disp = args.checkint(2);
		if (disp < 0) {
			return lrotate(LuaValue.varargsOf(args.arg(1), LuaValue.valueOf(-disp)));
		} else {
			disp = disp & 63;
			return bitsToValue(x>>>disp | x<<64-disp);
		}
	}

	public static LuaValue extract(Varargs args) {
		long n = args.checklong(1);
		int field = args.checkint(2);
		int width = args.checkint(3);
		if (field < 0) {
			LuaValue.argerror(2, "field cannot be negative");
		}
		if (width < 0) {
			LuaValue.argerror(3, "width must be postive");
		}
		if (field+width > 32) {
			LuaValue.error("trying to access non-existent bits");
		}
		return bitsToValue(n>>>field & -1L>>>64-width);
	}

	public static LuaValue replace(Varargs args) {
		long n = args.checklong(1);
		int v = args.checkint(2);
		int field = args.checkint(3);
		int width = args.checkint(4);
		if (field < 0) {
			LuaValue.argerror(3, "field cannot be negative");
		}
		if (width < 0) {
			LuaValue.argerror(4, "width must be postive");
		}
		if (field+width > 64) {
			LuaValue.error("trying to access non-existent bits");
		}
		long mask = (-1L>>>(64-width))<<field;
		n = n & ~mask | v<<field & mask;
		return bitsToValue(n);
	}

	private static LuaValue bitsToValue(long x) {
		return x < 0? LuaValue.valueOf(x & 0xFFFFFFFFFFFFFFFFL): LuaValue.valueOf(x);
	}
}
