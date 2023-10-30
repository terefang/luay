package luay.lib.ext.jdbc;

public class DefaultArrayListHandler extends DefaultResultSetPerRowHandler<Object[]>
{
    public static final DefaultArrayListHandler INSTANCE = new DefaultArrayListHandler();

    public DefaultArrayListHandler()
    {
        this.setRowProcessor(DefaultArrayRowHandler.INSTANCE);
    }
}
