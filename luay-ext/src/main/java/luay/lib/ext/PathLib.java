package luay.lib.ext;

import luay.vm.*;
import luay.vm.lib.OneArgFunction;
import luay.vm.lib.VarArgFunction;
import luay.lib.LuayLibraryFactory;


import java.io.File;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class PathLib extends AbstractLibrary implements LuayLibraryFactory
{
	@Override
	public List<Class> getLibraryFunctionClasses()
	{
		return toList(
				_is_dir.class,
				_is_file.class,
				_list_dir.class,
				_list_dirs.class
		);
	}

	@Override
	public String getLibraryName() {
		return "path";
	}

	public PathLib()
	{
		super();
	}

	// is_file(filename) -> boolean
	static final class _is_file extends OneArgFunction {
		@Override
		public LuaValue call(LuaValue arg1)
		{
			return LuaBoolean.valueOf(new File(arg1.checkjstring()).isFile());
		}
	}

	// is_dir(filename) -> boolean
	static final class _is_dir extends OneArgFunction {
		@Override
		public LuaValue call(LuaValue arg1)
		{
			return LuaBoolean.valueOf(new File(arg1.checkjstring()).isDirectory());
		}
	}

	// list_dirs(dirname [, ext, ...]) -> list
	static final class _list_dirs extends VarArgFunction
	{
		@Override
		public Varargs invoke(Varargs args)
		{
			LuaTable _z = new LuaTable();
			Deque<File> _queue = new LinkedList<>();
			String _base = new File(args.checkjstring(1)).getAbsolutePath();
			_queue.add(new File(_base));

			while(_queue.size()>0)
			{
				for(File _f : _queue.pollLast().listFiles())
				{
					if(_f.isDirectory()) _queue.push(_f);

					boolean _found = false;

					if(args.narg()==1)
					{
						_found=true;
					}
					else
					{
						for(int _i=1; _i<args.narg(); _i++)
						{
							if(_f.getName().endsWith(args.checkjstring(_i+1)))
							{
								_found=true;
								break;
							}
						}
					}

					if(_found)
					{
						_z.insert(_z.length()+1, LuaString.valueOf(_f.getAbsolutePath().substring(_base.length())));
					}
				}
			}

			return _z;
		}
	}

	// list_dir(dirname [, ext, ...]) -> list
	static final class _list_dir extends VarArgFunction
	{
		@Override
		public Varargs invoke(Varargs args)
		{
			LuaTable _z = new LuaTable();

			for(File _f : new File(args.checkjstring(1)).listFiles())
			{
				boolean _found = false;

				if(args.narg()==1)
				{
					_found=true;
				}
				else
				{
					for(int _i=1; _i<args.narg(); _i++)
					{
						if(_f.getName().endsWith(args.checkjstring(_i+1)))
						{
							_found=true;
							break;
						}
					}
				}

				if(_found)
				{
					_z.insert(_z.length()+1, LuaString.valueOf(_f.getName()));
				}
			}

			return _z;
		}
	}

}
