/*******************************************************************************
* Copyright (c) 2011 Luaj.org. All rights reserved.
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
package luay.vm.lib.java;

import luay.vm.*;
import luay.vm.lib.OneArgFunction;

import java.util.List;

public class JavaList extends LuaUserdata {

	private static final class LenFunction extends OneArgFunction {
		@Override
		public LuaValue call(LuaValue u) {
			return LuaValue.valueOf(((List)((LuaUserdata) u).m_instance).size());
		}
	}

	static final LuaValue LENGTH = valueOf("length");

	static final LuaTable array_metatable;
	static {
		array_metatable = new LuaTable();
		array_metatable.rawset(LuaValue.LEN, new LenFunction());
	}

	JavaList(Object instance) {
		super(instance);
		//setmetatable(array_metatable);
	}

	@Override
	public LuaValue len() {
		return valueOf(((List)m_instance).size());
	}

	@Override
	public LuaValue get(LuaValue key) {
		if (key.equals(LENGTH))
			return valueOf(((List)m_instance).size());
		if (key.isint()) {
			int i = key.toint()-1;
			return i >= 0 && i < ((List)m_instance).size()
					? CoerceJavaToLua.coerce(((List)m_instance).get(key.toint()-1))
					: NIL;
		}
		return super.get(key);
	}

	@Override
	public void set(LuaValue key, LuaValue value) {
		if (key.isint()) {
			int i = key.toint()-1;
			Class<?> _type = ((List) m_instance).get(0).getClass();
			if (i >= 0 && i < ((List)m_instance).size())
				((List)m_instance).set(i, CoerceLuaToJava.coerce(value, _type));
			else if (i == ((List)m_instance).size())
				((List)m_instance).add(CoerceLuaToJava.coerce(value, _type));
			else if (m_metatable == null || !settable(this, key, value))
				error("array index out of bounds");
		} else
			super.set(key, value);
	}

	@Override
	public boolean isarray()
	{
		return true;
	}

	@Override
	public Varargs inext(LuaValue key)
	{
		int _index = key.checkint();
		if(_index<0) return LuaValue.NIL;
		if(_index>=((List)m_instance).size()) return LuaValue.NIL;
		return LuaValue.varargsOf(LuaValue.valueOf(_index+1), this.get(_index+1));
	}
}
