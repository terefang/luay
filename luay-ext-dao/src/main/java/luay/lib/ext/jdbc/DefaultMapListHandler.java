package luay.lib.ext.jdbc;

import java.util.Map;

public class DefaultMapListHandler extends DefaultResultSetPerRowHandler<Map<String, Object>>
{
    public static final DefaultMapListHandler INSTANCE = new DefaultMapListHandler();

    public DefaultMapListHandler()
    {
        this.setRowProcessor(DefaultMapRowHandler.INSTANCE);
    }
}
