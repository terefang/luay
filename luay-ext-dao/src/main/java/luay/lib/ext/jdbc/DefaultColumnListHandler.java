package luay.lib.ext.jdbc;

public class DefaultColumnListHandler extends DefaultResultSetPerRowHandler<Object>
{
    public static final DefaultColumnListHandler INSTANCE = new DefaultColumnListHandler();

    public DefaultColumnListHandler()
    {
        this.setRowProcessor(DefaultScalarRowHandler.INSTANCE);
    }
}
