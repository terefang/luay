package luay.lib.ext.jdbc;

import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class DAO implements AutoCloseable
{

    public static enum DbType {
        DB_TYPE_POSTGRES,
        DB_TYPE_ORACLE,
        DB_TYPE_MSSQL,
        DB_TYPE_SYBASE,
        DB_TYPE_DB2,
        DB_TYPE_H2,
        DB_TYPE_SQLITE,
        DB_TYPE_CRATE,
        DB_TYPE_ANSI,
        DB_TYPE_MYSQL
    }

    DataSource dataSource;
    Connection connection;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void checkReadOnly() throws Exception
    {
        if(this.isReadOnly())
        {
            throw new IllegalAccessException("DAO is read-only.");
        }
    }

    public boolean isReadOnly()
    {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly)
    {
        this.readOnly = readOnly;
    }

    private boolean readOnly = false;
    boolean autoCommit;

    public boolean isAutoCommit() {
        return autoCommit;
    }

    @SneakyThrows
    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;

        if (this.dataSource != null) {
            if (this.connection != null) {
                if (!autoCommit) {
                    this.connection.commit();
                }
                this.connection.close();
                this.connection = null;
            }

            if (!autoCommit) {
                this.connection = this.dataSource.getConnection();
            }
        }

        if (this.connection != null) {
            this.connection.setAutoCommit(autoCommit);
        }
    }

    DbType dbType = DbType.DB_TYPE_ANSI;

    public DbType getDbType() {
        return dbType;
    }

    public void setDbType(DbType dbType) {
        this.dbType = dbType;
    }


    /**
     * commits a transaction
     */
    @SneakyThrows
    public void commit() {
        if (this.connection != null) {
            this.connection.commit();
        }
    }

    /**
     * rollsback a transaction
     */
    @SneakyThrows
    public void rollback() {
        if (this.connection != null) {
            this.connection.rollback();
        }
    }

    /**
     * @param _query
     * @param _params
     */
    public int execute(String _query, Map<String, Object> _params) {
        return _execute(this, _query, _params);
    }

    @SneakyThrows
    static int _execute(DAO _dao, String _query, Map<String, Object> _params) {
        if (_dao.connection != null) {
            return _doExecute(_dao, _dao.connection, _query, _params);
        } else if (_dao.dataSource != null) {
            try (Connection _connection = _dao.dataSource.getConnection();) {
                return _doExecute(_dao, _connection, _query, _params);
            }
        }
        return -1;
    }

    @SneakyThrows
    private static int _doExecute(DAO _dao, Connection _connection, String _query, Map<String, Object> _params) {
        List<Object> _bind = new Vector<>();
        _query = DaoUtil.preparseParameters(_dao.dbType, _query, _params, _bind);
        try (PreparedStatement _statement = _connection.prepareStatement(_query);) {
            for (int _i = 1; _i <= _bind.size(); _i++) {
                _statement.setObject(_i, _bind.get(_i - 1));
            }
            return _statement.executeUpdate();
        }
    }

    /**
     * @param _query
     * @param _params
     */
    public int execute(String _query, List<Object> _params) {
        return _execute(this, _query, _params);
    }

    @SneakyThrows
    static int _execute(DAO _dao, String _query, List<Object> _params) {
        if (_dao.connection != null) {
            return _doExecute(_dao, _dao.connection, _query, _params);
        } else if (_dao.dataSource != null) {
            try (Connection _connection = _dao.dataSource.getConnection();) {
                return _doExecute(_dao, _connection, _query, _params);
            }
        }
        return -1;
    }

    @SneakyThrows
    private static int _doExecute(DAO _dao, Connection _connection, String _query, List<Object> _bind) {
        try (PreparedStatement _statement = _connection.prepareStatement(_query);) {
            for (int _i = 1; _i <= _bind.size(); _i++) {
                _statement.setObject(_i, _bind.get(_i - 1));
            }
            return _statement.executeUpdate();
        }
    }

    /**
     * @param _query
     */
    public int execute(String _query) {
        return _execute(this, _query);
    }

    @SneakyThrows
    static int _execute(DAO _dao, String _query) {
        if (_dao.connection != null) {
            return _doExecute(_dao, _dao.connection, _query);
        } else if (_dao.dataSource != null) {
            try (Connection _connection = _dao.dataSource.getConnection();) {
                return _doExecute(_dao, _connection, _query);
            }
        }
        return -1;
    }

    @SneakyThrows
    private static int _doExecute(DAO _dao, Connection _connection, String _query) {
        List<Object> _bind = new Vector<>();
        try (PreparedStatement _statement = _connection.prepareStatement(_query);) {
            return _statement.executeUpdate();
        }
    }

    /**
     * @param _query
     * @param _params
     */
    public int insert(String _query, Map<String, Object> _params) {
        return _execute(this, _query, _params);
    }


    /**
     * @param _query
     * @param _params
     */
    public int insert(String _query, List<Object> _params) {
        return _execute(this, _query, _params);
    }

    /**
     * @param _query
     */
    public int insert(String _query) {
        return _execute(this, _query);
    }

    // QUERIES ----------------------------------------------------------------------------------------------------------------------------
    public <T> T queryForT(String _query, Map<String, Object> _params, ResultSetProcessor<T> _proc) {
        return _queryForT(this, _query, _params, _proc);
    }

    @SneakyThrows
    static <T> T _queryForT(DAO _dao, String _query, Map<String, Object> _params, ResultSetProcessor<T> _proc) {
        if (_dao.connection != null) {
            return _doQueryForT(_dao, _dao.connection, _query, _params, _proc);
        } else if (_dao.dataSource != null) {
            try (Connection _connection = _dao.dataSource.getConnection();) {
                return _doQueryForT(_dao, _connection, _query, _params, _proc);
            }
        }
        return null;
    }

    @SneakyThrows
    static <T> T _doQueryForT(DAO _dao, Connection _connection, String _query, Map<String, Object> _params, ResultSetProcessor<T> _proc) {
        List<Object> _bind = new Vector<>();
        _query = DaoUtil.preparseParameters(_dao.dbType, _query, _params, _bind);
        try (PreparedStatement _statement = _connection.prepareStatement(_query);) {
            for (int _i = 1; _i <= _bind.size(); _i++) {
                _statement.setObject(_i, _bind.get(_i - 1));
            }

            try (ResultSet _rs = _statement.executeQuery();) {
                return _proc.processResultSet(_dao, _statement, _rs);
            }
        }
    }

    public <T> T queryForT(String _query, ResultSetProcessor<T> _proc) {
        return _queryForT(this, _query, _proc);
    }

    @SneakyThrows
    static <T> T _queryForT(DAO _dao, String _query, ResultSetProcessor<T> _proc) {
        if (_dao.connection != null) {
            return _doQueryForT(_dao, _dao.connection, _query, _proc);
        } else if (_dao.dataSource != null) {
            try (Connection _connection = _dao.dataSource.getConnection();) {
                return _doQueryForT(_dao, _connection, _query, _proc);
            }
        }
        return null;
    }

    @SneakyThrows
    static <T> T _doQueryForT(DAO _dao, Connection _connection, String _query, ResultSetProcessor<T> _proc) {
        try (PreparedStatement _statement = _connection.prepareStatement(_query);) {
            try (ResultSet _rs = _statement.executeQuery();) {
                return _proc.processResultSet(_dao, _statement, _rs);
            }
        }
    }

    public <T> T queryForT(String _query, List<Object> _params, ResultSetProcessor<T> _proc) {
        return _queryForT(this, _query, _params, _proc);
    }

    @SneakyThrows
    static <T> T _queryForT(DAO _dao, String _query, List<Object> _bind, ResultSetProcessor<T> _proc) {
        if (_dao.connection != null) {
            return _doQueryForT(_dao, _dao.connection, _query, _bind, _proc);
        } else if (_dao.dataSource != null) {
            try (Connection _connection = _dao.dataSource.getConnection();) {
                return _doQueryForT(_dao, _connection, _query, _bind, _proc);
            }
        }
        return null;
    }

    @SneakyThrows
    static <T> T _doQueryForT(DAO _dao, Connection _connection, String _query, List<Object> _bind, ResultSetProcessor<T> _proc) {
        try (PreparedStatement _statement = _connection.prepareStatement(_query);) {
            for (int _i = 1; _i <= _bind.size(); _i++) {
                _statement.setObject(_i, _bind.get(_i - 1));
            }

            try (ResultSet _rs = _statement.executeQuery();) {
                return _proc.processResultSet(_dao, _statement, _rs);
            }
        }
    }


    public int update(String _query, Map<String, Object> _params) {
        return _update(this, _query, _params);
    }

    @SneakyThrows
    static int _update(DAO _dao, String _query, Map<String, Object> _params) {
        if (_dao.connection != null) {
            return _doUpdate(_dao, _dao.connection, _query, _params);
        } else if (_dao.dataSource != null) {
            try (Connection _connection = _dao.dataSource.getConnection();) {
                return _doUpdate(_dao, _connection, _query, _params);
            }
        }
        return -1;
    }

    @SneakyThrows
    private static int _doUpdate(DAO _dao, Connection _connection, String _query, Map<String, Object> _params) {
        List<Object> _bind = new Vector<>();
        _query = DaoUtil.preparseParameters(_dao.dbType, _query, _params, _bind);
        try (PreparedStatement _statement = _connection.prepareStatement(_query);) {
            for (int _i = 1; _i <= _bind.size(); _i++) {
                _statement.setObject(_i, _bind.get(_i - 1));
            }
            return _statement.executeUpdate();
        }
    }

    public int update(String _query, List<Object> _params) {
        return _update(this, _query, _params);
    }

    @SneakyThrows
    static int _update(DAO _dao, String _query, List<Object> _params) {
        if (_dao.connection != null) {
            return _doUpdate(_dao, _dao.connection, _query, _params);
        } else if (_dao.dataSource != null) {
            try (Connection _connection = _dao.dataSource.getConnection();) {
                return _doUpdate(_dao, _connection, _query, _params);
            }
        }
        return -1;
    }

    @SneakyThrows
    private static int _doUpdate(DAO _dao, Connection _connection, String _query, List<Object> _bind) {
        try (PreparedStatement _statement = _connection.prepareStatement(_query);) {
            for (int _i = 1; _i <= _bind.size(); _i++) {
                _statement.setObject(_i, _bind.get(_i - 1));
            }
            return _statement.executeUpdate();
        }
    }

    public int update(String _query) {
        return _update(this, _query);
    }

    @SneakyThrows
    static int _update(DAO _dao, String _query) {
        if (_dao.connection != null) {
            return _doUpdate(_dao, _dao.connection, _query);
        } else if (_dao.dataSource != null) {
            try (Connection _connection = _dao.dataSource.getConnection();) {
                return _doUpdate(_dao, _connection, _query);
            }
        }
        return -1;
    }

    @SneakyThrows
    private static int _doUpdate(DAO _dao, Connection _connection, String _query) {
        List<Object> _bind = new Vector<>();
        try (PreparedStatement _statement = _connection.prepareStatement(_query);) {
            return _statement.executeUpdate();
        }
    }

    // MapList -------------------------------------------------------------------------------------------------------
    public List<Map<String, Object>> queryForMapList(String _query) {
        return _queryForT(this, _query, DefaultMapListHandler.INSTANCE);
    }

    public List<Map<String, Object>> queryForMapList(String _query, List<Object> _bind) {
        return _queryForT(this, _query, _bind, DefaultMapListHandler.INSTANCE);
    }

    public List<Map<String, Object>> queryForMapList(String _query, Map<String, Object> _params) {
        return _queryForT(this, _query, _params, DefaultMapListHandler.INSTANCE);
    }

    // KvMap -------------------------------------------------------------------------------------------------------
    public Map<String, Object> queryForKvMap(String _query) {
        return _queryForT(this, _query, DefaultKvMapHandler.INSTANCE);
    }

    public Map<String, Object> queryForKvMap(String _query, List<Object> _bind) {
        return _queryForT(this, _query, _bind, DefaultKvMapHandler.INSTANCE);
    }

    public Map<String, Object> queryForKvMap(String _query, Map<String, Object> _params) {
        return _queryForT(this, _query, _params, DefaultKvMapHandler.INSTANCE);
    }

    // ArrayList -------------------------------------------------------------------------------------------------------
    public List<Object[]> queryForArrayList(String _query) {
        return _queryForT(this, _query, DefaultArrayListHandler.INSTANCE);
    }

    public List<Object[]> queryForArrayList(String _query, List<Object> _bind) {
        return _queryForT(this, _query, _bind, DefaultArrayListHandler.INSTANCE);
    }

    public List<Object[]> queryForArrayList(String _query, Map<String, Object> _params) {
        return _queryForT(this, _query, _params, DefaultArrayListHandler.INSTANCE);
    }

    // ColumnList -------------------------------------------------------------------------------------------------------
    public List<Object> queryForColumnList(String _query) {
        return _queryForT(this, _query, DefaultColumnListHandler.INSTANCE);
    }

    public List<Object> queryForColumnList(String _query, List<Object> _bind) {
        return _queryForT(this, _query, _bind, DefaultColumnListHandler.INSTANCE);
    }

    public List<Object> queryForColumnList(String _query, Map<String, Object> _params) {
        return _queryForT(this, _query, _params, DefaultColumnListHandler.INSTANCE);
    }

    // Scalar -------------------------------------------------------------------------------------------------------
    public Object queryForScalar(String _query) {
        return _queryForT(this, _query, DefaultScalarHandler.INSTANCE);
    }

    public Object queryForScalar(String _query, List<Object> _bind) {
        return _queryForT(this, _query, _bind, DefaultScalarHandler.INSTANCE);
    }

    public Object queryForScalar(String _query, Map<String, Object> _params) {
        return _queryForT(this, _query, _params, DefaultScalarHandler.INSTANCE);
    }

    @Override
    public void close() throws Exception
    {
        if(this.connection!=null)
        {
            try{this.connection.close();} catch (Exception _xe) {}
        }
    }


}
