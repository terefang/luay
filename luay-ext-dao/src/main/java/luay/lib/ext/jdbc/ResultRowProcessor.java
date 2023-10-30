package luay.lib.ext.jdbc;

import java.sql.ResultSet;
import java.sql.Statement;

public interface ResultRowProcessor<T>
{
    T processResultRow(DAO _dao, Statement _st, ResultSet _rs);
}
