package luay.lib.ext.jdbc;

import lombok.SneakyThrows;

import java.sql.*;


public class LuayJdbcResultSet
{
    ResultSet rs = null;

    @SneakyThrows
    public void close()
    {
        rs.close();
    }

    public boolean next() throws SQLException {
        return rs.next();
    }

    public int getRow() throws SQLException {
        return rs.getRow();
    }


}
