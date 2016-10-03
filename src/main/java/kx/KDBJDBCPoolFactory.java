package kx;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class KDBJDBCPoolFactory extends BasePooledObjectFactory<Connection> {

    @Override
    public Connection create() throws Exception {
        Class.forName("jdbc");
        final Connection localConnection = DriverManager.getConnection(
                "jdbc:q:localhost:5001", "", "");
        return localConnection;
    }

    @Override
    public PooledObject<Connection> wrap(final Connection con) {
        return new DefaultPooledObject<Connection>(con);
    }

}
