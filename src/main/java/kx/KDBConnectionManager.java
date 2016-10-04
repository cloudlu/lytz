/**
 *
 */
package kx;

import java.io.IOException;

import kx.C.KException;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author cloudlu
 *
 */
@Log4j2
public class KDBConnectionManager {

    @Autowired
    @Qualifier("ticketPlant")
    private KDBServer server;

    private C c;

    private boolean isValid(final C conn) {
        if (null == conn)
            return false;
        if (null == conn.s || conn.s.isClosed() || !conn.s.isConnected())
            return false;
        try {
            conn.k("");
        } catch (final Exception e) {
            return false;
        }
        return true;
    }

    public final C get() {
        if (isValid(c))
            return c;
        synchronized (this) {
            // double check
            if (isValid(c))
                return c;
            try {
                c = new C(server.getHost(), server.getPort(),
                        server.getUsername() + ":" + server.getPassword());
            } catch (KException | IOException e) {
                LOG.error("fail to create connection to host {} port {}",
                        server.getHost(), server.getPort(), e);
            }
        }
        return c;
    }

    public void release(final C con) {

    }
}
