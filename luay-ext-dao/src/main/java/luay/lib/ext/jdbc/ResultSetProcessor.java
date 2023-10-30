package luay.lib.ext.jdbc;

import java.sql.ResultSet;
import java.sql.Statement;

public interface ResultSetProcessor<T>
{
    T processResultSet(DAO _dao, Statement _st, ResultSet _rs);
}
