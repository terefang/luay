package luay.lib.ext.jdbc;

import java.sql.ResultSet;
import java.sql.Statement;

public interface ResultColumnProcessor<T>
{
    T processResultColumn(DAO _dao, Statement _st, ResultSet _rs, int _index);
}
