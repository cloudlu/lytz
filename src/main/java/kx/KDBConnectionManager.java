/**
 *
 */
package kx;

import java.io.IOException;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import kx.C.KException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

/**
 * @author cloudlu
 *
 */
@Log4j2
@Getter
@Setter
public class KDBConnectionManager {

    @NotNull
    @Size(min = 2, max = 30)
    private String host;
    @NotNull
    private int port;
    @NotNull
    @Size(min = 2, max = 30)
    private String username;
    @NotNull
    @Size(min = 2, max = 30)
    private String password;

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
                c = new C(host, port, username + ":" + password);
            } catch (KException | IOException e) {
                LOG.error("fail to create connection to host {} port {}", host,
                        port, e);
            }
        }
        return c;
    }

    public void release(final C con) {

    }
}
