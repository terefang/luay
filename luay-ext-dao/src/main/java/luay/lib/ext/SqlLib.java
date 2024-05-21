package luay.lib.ext;

import lombok.SneakyThrows;
import luay.lib.LuayLibraryFactory;
import luay.lib.ext.jdbc.DAO;
import luay.lib.ext.jdbc.DaoUtil;
import luay.main.LuayHelper;
import luay.vm.*;
import luay.vm.lib.ThreeArgFunction;
import luay.vm.lib.VarArgFunction;
import luay.vm.lib.ZeroArgFunction;
import luay.vm.lib.java.CoerceJavaToLua;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.*;

public class SqlLib extends AbstractLibrary implements LuayLibraryFactory
{
    @Override
    public List<Class> getLibraryFunctionClasses() {
        return Arrays.asList(
                _sqlite.class,
				_mysql.class,
				_mysql5.class,
				_oracle.class,
				_mariadb.class,
				_mssql.class,
				_jtds.class,
                _xlsql.class,
                _pgsql.class,
				_jdbc.class,
				_drivers.class
                );
    }

    @Override
    public String getLibraryName() {
        return "sql";
    }

	public SqlLib() { }


	public static class _drivers extends ZeroArgFunction
	{

		@Override
		public LuaValue call()
		{
			LuaList _t = new LuaList();
			Enumeration<Driver> _dr = DriverManager.getDrivers();
			while(_dr.hasMoreElements())
			{
				Driver _d = _dr.nextElement();
				_t.insert(LuaValue.valueOf(_d.getClass().getName()));
			}
			return _t;
		}
	}

	public static abstract class _driver extends VarArgFunction
	{
		public _driver()
		{
			super();
		}

		@Override
		@SneakyThrows
		public Varargs invoke(Varargs args)
		{
			if(args.narg()>0)
			{
				String _suffix = args.checkjstring(1);
				if(args.narg()==1)
				{
					return CoerceJavaToLua.coerce(new _conn(_createDao( _suffix, "", "")));
				}

				String _user = args.checkjstring(2);
				if(args.narg()==2)
				{
					return CoerceJavaToLua.coerce(new _conn(_createDao( _suffix, _user, "")));
				}

				String _pass = args.checkjstring(3);
				if(args.narg()==3)
				{
					return CoerceJavaToLua.coerce(new _conn(_createDao( _suffix, _user, _pass)));
				}
			}
			return LuaValue.NIL;
		}

		abstract DAO _createDao(String _suffix, String _user, String _pass);

	}

	public static final class _duckdb extends _driver {
		public _duckdb() { super(); }

		@Override
		DAO _createDao(String _suffix, String _user, String _pass) {
			return DaoUtil.duckdbDao(true,_suffix,_user, _pass);
		}
	}

	public static final class _sqlite extends _driver {
		public _sqlite() { super(); }

		@Override
		DAO _createDao(String _suffix, String _user, String _pass) {
			return DaoUtil.sqliteDao(true,_suffix,_user, _pass);
		}
	}

	public static final class _mariadb extends _driver {
		public _mariadb() { super(); }

		@Override
		DAO _createDao(String _suffix, String _user, String _pass) {
			return DaoUtil.mariadbDao(true,_suffix,_user, _pass);
		}
	}

	public static final class _mysql extends _driver {
		public _mysql() { super(); }

		@Override
		DAO _createDao(String _suffix, String _user, String _pass) {
			return DaoUtil.mysqlDao(true,_suffix,_user, _pass);
		}
	}

	public static final class _mysql5 extends _driver {
		public _mysql5() { super(); }

		@Override
		DAO _createDao(String _suffix, String _user, String _pass) {
			return DaoUtil.mysql5Dao(true,_suffix,_user, _pass);
		}
	}

	public static final class _oracle extends _driver {
		public _oracle() { super(); }

		@Override
		DAO _createDao(String _suffix, String _user, String _pass) {
			return DaoUtil.oracleDao(true,_suffix,_user, _pass);
		}
	}

	public static final class _jtds extends _driver {
		public _jtds() { super(); }

		@Override
		DAO _createDao(String _suffix, String _user, String _pass) {
			return DaoUtil.jtdsDao(true,_suffix,_user, _pass);
		}
	}

	public static final class _mssql extends _driver {
		public _mssql() { super(); }

		@Override
		DAO _createDao(String _suffix, String _user, String _pass) {
			return DaoUtil.mssqlDao(true,_suffix,_user, _pass);
		}
	}

	public static final class _xlsql extends _driver {
		public _xlsql() { super(); }

		@Override
		DAO _createDao(String _suffix, String _user, String _pass) {
			return DaoUtil.xlsxDao(true,_suffix);
		}
	}

	public static final class _pgsql extends _driver {
		public _pgsql() { super(); }

		@Override
		DAO _createDao(String _suffix, String _user, String _pass) {
			return DaoUtil.pgsqlDao(true,_suffix,_user, _pass);
		}
	}

	public static class _jdbc extends VarArgFunction
	{
		public _jdbc()
		{
			super();
		}

		LuaValue _createDao(String _type, String _clazz, String _uri, String _user, String _pass)
		{
			DAO _d = DaoUtil.daoFromJdbc(true,_clazz, _uri, _user,_pass);
			_d.setDbType(DAO.DbType.valueOf("DB_TYPE_"+_type.toUpperCase()));
			return CoerceJavaToLua.coerce(new _conn(_d));
		}

		@Override
		@SneakyThrows
		public Varargs invoke(Varargs args)
		{
			if(args.narg()>2)
			{
				String _type = args.checkjstring(1);
				String _clazz = args.checkjstring(2);
				String _uri = args.checkjstring(3);
				if(args.narg()==3)
				{
					return _createDao(_type, _clazz, _uri, "", "");
				}

				String _user = args.checkjstring(3);
				if(args.narg()==3)
				{
					return _createDao(_type, _clazz, _uri, _user, "");
				}

				String _pass = args.checkjstring(4);
				if(args.narg()==4)
				{
					return _createDao(_type, _clazz, _uri, _user, _pass);
				}
			}
			return LuaValue.NIL;
		}
	}

	public static class _conn {
		DAO _dao;

		public _conn(DAO _d)
        {
			this._dao = _d;
			this._dao.setAutoCommit(true);
        }

        public void autocommit(Varargs args) {
			this._dao.setAutoCommit(args.checkboolean(1));
		}

		public void commit() {
			this._dao.commit();;
		}

		public void rollback() {
			this._dao.rollback();
		}

		@SneakyThrows
		public Varargs execute(Varargs args)
		{
			try {
				if(args.narg()==1)
				{
					return LuaValue.valueOf(this._dao.execute(args.checkjstring(1)));
				}
				else
				if(args.narg()==2 && args.istable(2))
				{
					LuaTable _t = args.checktable(2);
					if(_t.isarray() || _t.isForceArray())
					{
						return LuaValue.valueOf(this._dao.execute(args.checkjstring(1), LuayHelper.tableToJavaList(_t)));
					}
					else
					{
						return LuaValue.valueOf(this._dao.execute(args.checkjstring(1), LuayHelper.tableToJavaMap(_t)));
					}
				}
				else
				if(args.narg()>1)
				{
					List<Object> _list = new Vector<>();
					for(int _i=2; _i<=args.narg(); _i++)
					{
						_list.add(LuayHelper.valueToJava(args.arg(_i)));
					}
					return LuaValue.valueOf(this._dao.execute(args.checkjstring(1), _list));
				}
			}
			catch (Exception _xe)
			{
				throw new LuaError(_xe);
			}
			return LuaValue.NIL;
		}

		@SneakyThrows
		public Varargs insert(Varargs args)
		{
			return execute(args);
		}

		@SneakyThrows
		public Varargs query(Varargs args)
		{
			try {
				if(args.narg()==1)
				{
					return LuayHelper.toList(this._dao.queryForMapList(args.checkjstring(1)));
				}
				else
				if(args.narg()==2 && args.istable(2))
				{
					LuaTable _t = args.checktable(2);
					if(_t.isarray() || _t.isForceArray())
					{
						return LuayHelper.toList(this._dao.queryForMapList(args.checkjstring(1), LuayHelper.tableToJavaList(_t)));
					}
					else
					{
						return LuayHelper.toList(this._dao.queryForMapList(args.checkjstring(1), LuayHelper.tableToJavaMap(_t)));
					}
				}
				else
				if(args.narg()>1)
				{
					List<Object> _list = new Vector<>();
					for(int _i=2; _i<=args.narg(); _i++)
					{
						_list.add(LuayHelper.valueToJava(args.arg(_i)));
					}
					return LuayHelper.toList(this._dao.queryForMapList(args.checkjstring(1), _list));
				}
			}
			catch (Exception _xe)
			{
				throw new LuaError(_xe);
			}
			return LuaValue.NIL;
		}

		@SneakyThrows
		public void close()
		{
			this._dao.close();
		}
	}

}