package luay.lib.ext.jdbc;


import java.sql.Statement;

public interface ResultPkProcessor<T>
{
    T processResultPK(DAO _dao, Statement _st);
}
