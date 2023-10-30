package luay.lib.ext.jdbc;

import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.Statement;

public class DefaultScalarHandler implements ResultSetProcessor<Object>
{
    public static DefaultScalarHandler INSTANCE = new DefaultScalarHandler();

    @SneakyThrows
    @Override
    public Object processResultSet(DAO _dao, Statement _st, ResultSet _rs) {
        _rs.next();
        return DefaultScalarRowHandler.INSTANCE.processResultRow(_dao, _st, _rs);
    }
}
