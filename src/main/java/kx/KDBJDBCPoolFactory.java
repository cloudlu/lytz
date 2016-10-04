package kx;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class KDBJDBCPoolFactory extends BasePooledObjectFactory<Connection> {

    @Autowired
    @Qualifier("ticketPlant")
    private KDBServer server;

    @Override
    public Connection create() throws Exception {
        Class.forName(JDBC.class.getName());
        final Connection localConnection = DriverManager.getConnection(
                "jdbc:q:" + server.getHost() + ":" + server.getPort(),
                server.getUsername(), server.getPassword());
        return localConnection;
    }

    @Override
    public PooledObject<Connection> wrap(final Connection con) {
        return new DefaultPooledObject<Connection>(con);
    }
}
