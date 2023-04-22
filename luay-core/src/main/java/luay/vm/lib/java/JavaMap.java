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

import luay.vm.LuaString;
import luay.vm.LuaUserdata;
import luay.vm.LuaValue;
import luay.vm.Varargs;

import java.util.Iterator;
import java.util.Map;

class JavaMap extends LuaUserdata
{
	JavaMap(Object instance) {
		super(instance);
		//setmetatable(array_metatable);
	}

	@Override
	public LuaValue get(LuaValue key)
	{
		Object _o = ((Map)m_instance).get(key.checkjstring());

		return _o==null ? NIL : CoerceJavaToLua.coerce(_o);
	}

	@Override
	public void set(LuaValue key, LuaValue value) {
		((Map)m_instance).put(key.checkjstring(),value);
	}

	@Override
	public void set(String key, LuaValue value) {
		((Map)m_instance).put(key,value);
	}

	@Override
	public boolean istable()
	{
		return true;
	}

	@Override
	public Varargs next(LuaValue key)
	{
		Iterator _it = ((Map) m_instance).keySet().iterator();

		if(key.isnil())
		{
			if(!_it.hasNext()) return LuaValue.NIL;
			LuaValue _key = LuaString.valueOf(_it.next().toString());
			return varargsOf(_key,this.get(_key));
		}
		String _key = key.checkjstring();
		while(_it.hasNext())
		{
			if(_it.next().toString().equals(_key))
			{
				if(!_it.hasNext()) return LuaValue.NIL;
				LuaValue _k = LuaString.valueOf(_it.next().toString());
				return varargsOf(_k,this.get(_k));
			}
		}
		return LuaValue.NIL;
	}
}
