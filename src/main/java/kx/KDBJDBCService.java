/**
 *
 */
package kx;

import java.sql.Connection;
import java.sql.PreparedStatement;

import lombok.extern.log4j.Log4j2;

import org.apache.commons.pool2.ObjectPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author cloudlu
 *
 */
@Log4j2
@Service
public class KDBJDBCService {

    @Autowired
    @Qualifier("jdbc")
    // new GenericObjectPool<Connection>(new KDBJDBCPoolFactory()))
    private ObjectPool<Connection> pool;

    /**
     * insert data to specified table
     *
     * @param data
     *            : data object save to kdb
     * @return
     */
    public boolean insertKDBData(final KDBData data) {
        Connection con = null;
        try {
            con = pool.borrowObject();
            final PreparedStatement insert = con
                    .prepareStatement("q){`t insert 0N!a::x}");
            insert.setTime(1, data.getTime());
            insert.setString(2, data.getSym());
            insert.setDouble(3, data.getPrice());
            insert.setInt(4, data.getSize());
            insert.setBoolean(5, data.isStop());
            insert.setString(6, String.valueOf(data.getCond()));
            insert.setString(7, String.valueOf(data.getEx()));
            insert.executeUpdate();
            return true;
        } catch (final Exception e) {
            LOG.error("fail to insert data", e);
        } finally {
            if (null != con) {
                try {
                    pool.returnObject(con);
                } catch (final Exception e) {
                    LOG.error("fail to return con back to poll", e);
                }
            }
        }
        return false;
    }
}