/**
 *
 */
package kx;

import java.io.IOException;
import java.util.Collection;

import kx.C.KException;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author cloudlu
 *
 */
@Log4j2
@Service
public class KDBAPIService {

    @Autowired
    @Qualifier("ticketPlant")
    private KDBConnectionManager manager;

    /**
     * insert data to specified table
     *
     * @param data
     *            : data with key - value from external system
     * @return
     */
    public boolean insertKDBData(final Collection<KDBData> data) {
        final C con = manager.get();
        final Object[] tab = convertData(data);
        final Object[] updStatement = { ".u.upd", KDBData.TABLE_NAME, tab };
        try {
            final Object result = con.k(updStatement);
            if (null == result)
                return true;
        } catch (KException | IOException e) {
            LOG.error(e);
        } finally {
            manager.release(con);
        }
        return false;
    }

    /**
     * need to convert object to data for column based storage
     *
     * @param cols
     * @param data
     * @return
     */
    private Object[] convertData(final Collection<KDBData> data) {
        final Object[] cols = KDBData.COL_NAMES;
        final Object[][] result = new Object[cols.length][data.size()];
        int i = 0;
        for (final KDBData singleData : data) {
            result[0][i] = singleData.getTime();
            result[1][i] = singleData.getSym();
            result[2][i] = singleData.getPrice();
            result[3][i] = singleData.getSize();
            result[4][i] = singleData.isStop();
            result[5][i] = singleData.getCond();
            result[6][i] = singleData.getEx();
            i++;
        }
        return result;
    }
}
