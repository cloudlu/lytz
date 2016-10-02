package kx;

//2014.03.25 allow calling connection close() even if already closed, use jdk1.7 api
//jdk1.7 specific parts are sections after //1.7
//2012.11.26 getRow(), use jdk6 api, return char[] as String to support Aqua Data Studio for lists of char vectors.
//java.sql.timestamp now maps to kdb+timestamp; use latest http://kx.com/q/s.k
//jdk1.6 specific parts are sections after //4
//For compiling for earlier jdks, remove those sections
//2007.04.20 c.java sql.date/time/timestamp
//jar cf jdbc.jar *.class   url(jdbc:q:host:port) isql(new service resources jdbc.jar)
//javac -Xbootclasspath:/System/Library/Java/JavaVirtualMachines/1.6.0.jdk/Contents/Classes/classes.jar -target 1.6 -source 1.6 jdbc.java
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.RowIdLifetime;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.logging.Logger;

public class JDBC implements Driver {
    static int V = 2, v = 0;

    static void O(final String s) {
        System.out.println(s);
    }

    @Override
    public int getMajorVersion() {
        return V;
    }

    @Override
    public int getMinorVersion() {
        return v;
    }

    @Override
    public boolean jdbcCompliant() {
        return false;
    }

    @Override
    public boolean acceptsURL(final String s) {
        return s.startsWith("jdbc:q:");
    }

    @Override
    public Connection connect(final String s, final Properties p)
            throws SQLException {
        return !acceptsURL(s) ? null : new co(s.substring(7),
                p != null ? p.get("user") : p, p != null ? p.get("password")
                        : p);
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(final String s,
            final Properties p) throws SQLException {
        return new DriverPropertyInfo[0];
    }

    static {
        try {
            DriverManager.registerDriver(new JDBC());
        } catch (final Exception e) {
            JDBC.O(e.getMessage());
        }
    }
    static int[] SQLTYPE = { 0, 16, 0, 0, -2, 5, 4, -5, 7, 8, 0, 12, 0, 0, 91,
        93, 0, 0, 0, 92 };
    static String[] TYPE = { "", "boolean", "", "", "byte", "short", "int",
        "long", "real", "float", "char", "symbol", "", "month", "date",
        "timestamp", "", "minute", "second", "time" };

    static int find(final String[] x, final String s) {
        int i = 0;
        for (; i < x.length && !s.equals(x[i]);) {
            ++i;
        }
        return i;
    }

    static int find(final int[] x, final int j) {
        int i = 0;
        for (; i < x.length && x[i] != j;) {
            ++i;
        }
        return i;
    }

    static void q(final String s) throws SQLException {
        throw new SQLException(s);
    }

    static void q() throws SQLException {
        throw new SQLFeatureNotSupportedException("nyi");
    }

    static void q(final Exception e) throws SQLException {
        throw new SQLException(e.getMessage());
    }

    public class co implements Connection {
        private C c;

        public co(final String s, final Object u, final Object p)
                throws SQLException {
            final int i = s.indexOf(":");
            try {
                c = new C(s.substring(0, i), Integer.parseInt(s
                        .substring(i + 1)), u == null ? "" : (String) u + ":"
                                + (String) p);
            } catch (final Exception e) {
                JDBC.q(e);
            }
        }

        public Object ex(final String s, final Object[] p) throws SQLException {
            try {
                return 0 < C.n(p) ? c.k(s, p) : c.k(".o.ex", s.toCharArray());
            } catch (final Exception e) {
                JDBC.q(e);
                return null;
            }
        }

        public rs qx(final String s) throws SQLException {
            try {
                return new rs(null, c.k(s));
            } catch (final Exception e) {
                JDBC.q(e);
                return null;
            }
        }

        public rs qx(final String s, final Object x) throws SQLException {
            try {
                return new rs(null, c.k(s, x));
            } catch (final Exception e) {
                JDBC.q(e);
                return null;
            }
        }

        private boolean a = true;

        @Override
        public void setAutoCommit(final boolean b) throws SQLException {
            a = b;
        }

        @Override
        public boolean getAutoCommit() throws SQLException {
            return a;
        }

        @Override
        public void rollback() throws SQLException {
        }

        @Override
        public void commit() throws SQLException {
        }

        @Override
        public boolean isClosed() throws SQLException {
            return c == null;
        }

        @Override
        public Statement createStatement() throws SQLException {
            return new st(this);
        }

        @Override
        public DatabaseMetaData getMetaData() throws SQLException {
            return new dm(this);
        }

        @Override
        public PreparedStatement prepareStatement(final String s)
                throws SQLException {
            return new ps(this, s);
        }

        @Override
        public CallableStatement prepareCall(final String s)
                throws SQLException {
            return new cs(this, s);
        }

        @Override
        public String nativeSQL(final String s) throws SQLException {
            return s;
        }

        private boolean b;
        private int i = TRANSACTION_SERIALIZABLE,
                h = rs.HOLD_CURSORS_OVER_COMMIT;

        @Override
        public void setReadOnly(final boolean x) throws SQLException {
            b = x;
        }

        @Override
        public boolean isReadOnly() throws SQLException {
            return b;
        }

        @Override
        public void setCatalog(final String s) throws SQLException {
            JDBC.q("cat");
        }

        @Override
        public String getCatalog() throws SQLException {
            JDBC.q("cat");
            return null;
        }

        @Override
        public void setTransactionIsolation(final int x) throws SQLException {
            i = x;
        }

        @Override
        public int getTransactionIsolation() throws SQLException {
            return i;
        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            return null;
        }

        @Override
        public void clearWarnings() throws SQLException {
        }

        @Override
        public void close() throws SQLException {
            if (isClosed())
                return;
            try {
                c.close();
            } catch (final IOException e) {
                JDBC.q(e);
            } finally {
                c = null;
            }
        }

        @Override
        public Statement createStatement(final int resultSetType,
                final int resultSetConcurrency) throws SQLException {
            return new st(this);
        }

        @Override
        public PreparedStatement prepareStatement(final String s,
                final int resultSetType, final int resultSetConcurrency)
                throws SQLException {
            return new ps(this, s);
        }

        @Override
        public CallableStatement prepareCall(final String s,
                final int resultSetType, final int resultSetConcurrency)
                throws SQLException {
            return new cs(this, s);
        }

        @Override
        public Map getTypeMap() throws SQLException {
            return null;
        }

        @Override
        public void setTypeMap(final Map map) throws SQLException {
        }

        // 3
        @Override
        public void setHoldability(final int holdability) throws SQLException {
            h = holdability;
        }

        @Override
        public int getHoldability() throws SQLException {
            return h;
        }

        @Override
        public Savepoint setSavepoint() throws SQLException {
            JDBC.q("sav");
            return null;
        }

        @Override
        public Savepoint setSavepoint(final String name) throws SQLException {
            JDBC.q("sav");
            return null;
        }

        @Override
        public void rollback(final Savepoint savepoint) throws SQLException {
        }

        @Override
        public void releaseSavepoint(final Savepoint savepoint)
                throws SQLException {
        }

        @Override
        public Statement createStatement(final int resultSetType,
                final int resultSetConcurrency, final int resultSetHoldability)
                        throws SQLException {
            return new st(this);
        }

        @Override
        public PreparedStatement prepareStatement(final String s,
                final int resultSetType, final int resultSetConcurrency,
                final int resultSetHoldability) throws SQLException {
            return new ps(this, s);
        }

        @Override
        public CallableStatement prepareCall(final String s,
                final int resultSetType, final int resultSetConcurrency,
                final int resultSetHoldability) throws SQLException {
            return new cs(this, s);
        }

        @Override
        public PreparedStatement prepareStatement(final String s,
                final int autoGeneratedKeys) throws SQLException {
            return new ps(this, s);
        }

        @Override
        public PreparedStatement prepareStatement(final String s,
                final int[] columnIndexes) throws SQLException {
            return new ps(this, s);
        }

        @Override
        public PreparedStatement prepareStatement(final String s,
                final String[] columnNames) throws SQLException {
            return new ps(this, s);
        }

        // 4
        private Properties clientInfo = new Properties();

        @Override
        public Clob createClob() throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public Blob createBlob() throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public NClob createNClob() throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public SQLXML createSQLXML() throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public boolean isValid(final int i) throws SQLException {
            if (i < 0) {
                JDBC.q();
            }
            return c != null;
        }

        @Override
        public void setClientInfo(final String k, final String v)
                throws SQLClientInfoException {
            clientInfo.setProperty(k, v);
        }

        @Override
        public void setClientInfo(final Properties p)
                throws SQLClientInfoException {
            clientInfo = p;
        }

        @Override
        public String getClientInfo(final String k) throws SQLException {
            return (String) clientInfo.get(k);
        }

        @Override
        public Properties getClientInfo() throws SQLException {
            return clientInfo;
        }

        @Override
        public Array createArrayOf(final String string, final Object[] os)
                throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public Struct createStruct(final String string, final Object[] os)
                throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public <T> T unwrap(final Class<T> type) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public boolean isWrapperFor(final Class<?> type) throws SQLException {
            JDBC.q();
            return false;
        }

        // 1.7
        @Override
        public int getNetworkTimeout() throws SQLFeatureNotSupportedException {
            throw new SQLFeatureNotSupportedException("nyi");
        }

        @Override
        public void setNetworkTimeout(final Executor executor,
                final int milliseconds) throws SQLFeatureNotSupportedException {
            throw new SQLFeatureNotSupportedException("nyi");
        }

        @Override
        public void abort(final Executor executor)
                throws SQLFeatureNotSupportedException {
            throw new SQLFeatureNotSupportedException("nyi");
        }

        @Override
        public void setSchema(final String s) {
        }

        @Override
        public String getSchema() {
            return null;
        }
    }

    public class st implements Statement {
        private co co;
        private Object r;
        private int R, T;
        protected Object[] p = {};

        public st(final co x) {
            co = x;
        }

        @Override
        public int executeUpdate(final String s) throws SQLException {
            co.ex(s, p);
            return -1;
        }

        @Override
        public ResultSet executeQuery(final String s) throws SQLException {
            return new rs(this, co.ex(s, p));
        }

        @Override
        public boolean execute(final String s) throws SQLException {
            return null != (r = co.ex(s, p));
        }

        @Override
        public ResultSet getResultSet() throws SQLException {
            return new rs(this, r);
        }

        @Override
        public int getUpdateCount() {
            return -1;
        }

        @Override
        public int getMaxRows() throws SQLException {
            return R;
        }

        @Override
        public void setMaxRows(final int i) throws SQLException {
            R = i;
        }

        @Override
        public int getQueryTimeout() throws SQLException {
            return T;
        }

        @Override
        public void setQueryTimeout(final int i) throws SQLException {
            T = i;
        }

        // truncate excess BINARY,VARBINARY,LONGVARBINARY,CHAR,VARCHAR,and
        // LONGVARCHAR fields
        @Override
        public int getMaxFieldSize() throws SQLException {
            return 0;
        }

        @Override
        public void setMaxFieldSize(final int i) throws SQLException {
        }

        @Override
        public void setEscapeProcessing(final boolean b) throws SQLException {
        }

        @Override
        public void cancel() throws SQLException {
        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            return null;
        }

        @Override
        public void clearWarnings() throws SQLException {
        }

        // positioned update? different statement?
        @Override
        public void setCursorName(final String name) throws SQLException {
            JDBC.q("cur");
        }

        @Override
        public boolean getMoreResults() throws SQLException {
            return false;
        }

        @Override
        public void close() throws SQLException {
            co = null;
        }

        @Override
        public void setFetchDirection(final int direction) throws SQLException {
            JDBC.q("fd");
        }

        @Override
        public int getFetchDirection() throws SQLException {
            return 0;
        }

        @Override
        public void setFetchSize(final int rows) throws SQLException {
        }

        @Override
        public int getFetchSize() throws SQLException {
            return 0;
        }

        @Override
        public int getResultSetConcurrency() throws SQLException {
            return ResultSet.CONCUR_READ_ONLY;
        }

        @Override
        public int getResultSetType() throws SQLException {
            return ResultSet.TYPE_SCROLL_INSENSITIVE;
        }

        @Override
        public void addBatch(final String sql) throws SQLException {
            JDBC.q("bat");
        }

        @Override
        public void clearBatch() throws SQLException {
        }

        @Override
        public int[] executeBatch() throws SQLException {
            return new int[0];
        }

        @Override
        public Connection getConnection() throws SQLException {
            return co;
        }

        // 3
        @Override
        public boolean getMoreResults(final int current) throws SQLException {
            return false;
        }

        @Override
        public ResultSet getGeneratedKeys() throws SQLException {
            return null;
        }

        @Override
        public int executeUpdate(final String sql, final int autoGeneratedKeys)
                throws SQLException {
            JDBC.q("a");
            return 0;
        }

        @Override
        public int executeUpdate(final String sql, final int[] columnIndexes)
                throws SQLException {
            JDBC.q("a");
            return 0;
        }

        @Override
        public int executeUpdate(final String sql, final String[] columnNames)
                throws SQLException {
            JDBC.q("a");
            return 0;
        }

        @Override
        public boolean execute(final String sql, final int autoGeneratedKeys)
                throws SQLException {
            JDBC.q("a");
            return false;
        }

        @Override
        public boolean execute(final String sql, final int[] columnIndexes)
                throws SQLException {
            JDBC.q("a");
            return false;
        }

        @Override
        public boolean execute(final String sql, final String[] columnNames)
                throws SQLException {
            JDBC.q("a");
            return false;
        }

        @Override
        public int getResultSetHoldability() throws SQLException {
            return ResultSet.HOLD_CURSORS_OVER_COMMIT;
        }

        // 4
        boolean poolable = false;

        @Override
        public boolean isClosed() throws SQLException {
            return co == null || co.isClosed();
        }

        @Override
        public void setPoolable(final boolean b) throws SQLException {
            if (isClosed())
                throw new SQLException("Closed");
            poolable = b;
        }

        @Override
        public boolean isPoolable() throws SQLException {
            if (isClosed())
                throw new SQLException("Closed");
            return poolable;
        }

        @Override
        public <T> T unwrap(final Class<T> type) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public boolean isWrapperFor(final Class<?> type) throws SQLException {
            JDBC.q();
            return false;
        }

        // 1.7
        boolean _closeOnCompletion = false;

        @Override
        public void closeOnCompletion() {
            _closeOnCompletion = true;
        }

        @Override
        public boolean isCloseOnCompletion() {
            return _closeOnCompletion;
        }
    }

    public class ps extends st implements PreparedStatement {
        private final String s;

        public ps(final co co, final String x) {
            super(co);
            s = x;
        }

        @Override
        public ResultSet executeQuery() throws SQLException {
            return executeQuery(s);
        }

        @Override
        public int executeUpdate() throws SQLException {
            return executeUpdate(s);
        }

        @Override
        public boolean execute() throws SQLException {
            return execute(s);
        }

        @Override
        public void clearParameters() throws SQLException {
            try {
                for (int i = 0; i < C.n(p);) {
                    p[i++] = null;
                }
            } catch (final UnsupportedEncodingException ex) {
                throw new SQLException(ex);
            }
        }

        @Override
        public void setObject(final int i, final Object x) throws SQLException {
            int n;
            try {
                n = C.n(p);
            } catch (final UnsupportedEncodingException ex) {
                throw new SQLException(ex);
            }
            if (i > n) {
                final Object[] r = new Object[i];
                System.arraycopy(p, 0, r, 0, n);
                p = r;
                for (; n < i;) {
                    p[n++] = null;
                }
            }
            p[i - 1] = x;
        }

        @Override
        public void setObject(final int i, final Object x,
                final int targetSqlType) throws SQLException {
            setObject(i, x);
        }

        @Override
        public void setObject(final int i, final Object x,
                final int targetSqlType, final int scale) throws SQLException {
            setObject(i, x);
        }

        @Override
        public void setNull(final int i, final int t) throws SQLException {
            setObject(i, C.NULL[JDBC.find(SQLTYPE, t)]);
        }

        @Override
        public void setBoolean(final int i, final boolean x)
                throws SQLException {
            setObject(i, new Boolean(x));
        }

        @Override
        public void setByte(final int i, final byte x) throws SQLException {
            setObject(i, new Byte(x));
        }

        @Override
        public void setShort(final int i, final short x) throws SQLException {
            setObject(i, new Short(x));
        }

        @Override
        public void setInt(final int i, final int x) throws SQLException {
            setObject(i, new Integer(x));
        }

        @Override
        public void setLong(final int i, final long x) throws SQLException {
            setObject(i, new Long(x));
        }

        @Override
        public void setFloat(final int i, final float x) throws SQLException {
            setObject(i, new Float(x));
        }

        @Override
        public void setDouble(final int i, final double x) throws SQLException {
            setObject(i, new Double(x));
        }

        @Override
        public void setString(final int i, final String x) throws SQLException {
            setObject(i, x);
        }

        @Override
        public void setDate(final int i, final Date x) throws SQLException {
            setObject(i, x);
        }

        @Override
        public void setTime(final int i, final Time x) throws SQLException {
            setObject(i, x);
        }

        @Override
        public void setTimestamp(final int i, final Timestamp x)
                throws SQLException {
            setObject(i, x);
        }

        @Override
        public void setBytes(final int i, final byte x[]) throws SQLException {
            JDBC.q();
        }

        @Override
        public void setBigDecimal(final int i, final BigDecimal x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setAsciiStream(final int i, final InputStream x,
                final int length) throws SQLException {
            JDBC.q();
        }

        @Override
        public void setUnicodeStream(final int i, final InputStream x,
                final int length) throws SQLException {
            JDBC.q();
        }

        @Override
        public void setBinaryStream(final int i, final InputStream x,
                final int length) throws SQLException {
            JDBC.q();
        }

        @Override
        public void addBatch() throws SQLException {
        }

        @Override
        public void setCharacterStream(final int parameterIndex,
                final Reader reader, final int length) throws SQLException {
            JDBC.q();
        }

        @Override
        public void setRef(final int i, final Ref x) throws SQLException {
            JDBC.q();
        }

        @Override
        public void setBlob(final int i, final Blob x) throws SQLException {
            JDBC.q();
        }

        @Override
        public void setClob(final int i, final Clob x) throws SQLException {
            JDBC.q();
        }

        @Override
        public void setArray(final int i, final Array x) throws SQLException {
            JDBC.q();
        }

        @Override
        public ResultSetMetaData getMetaData() throws SQLException {
            JDBC.q("m");
            return null;
        }

        @Override
        public void setDate(final int parameterIndex, final Date x,
                final Calendar cal) throws SQLException {
            JDBC.q();
        }

        @Override
        public void setTime(final int parameterIndex, final Time x,
                final Calendar cal) throws SQLException {
            JDBC.q();
        }

        @Override
        public void setTimestamp(final int parameterIndex, final Timestamp x,
                final Calendar cal) throws SQLException {
            JDBC.q();
        }

        @Override
        public void setNull(final int paramIndex, final int sqlType,
                final String typeName) throws SQLException {
            JDBC.q();
        }

        // 3
        @Override
        public void setURL(final int parameterIndex, final URL x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public ParameterMetaData getParameterMetaData() throws SQLException {
            JDBC.q("m");
            return null;
        }

        // 4
        @Override
        public void setRowId(final int i, final RowId rowid)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setNString(final int i, final String string)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setNCharacterStream(final int i, final Reader reader,
                final long l) throws SQLException {
            JDBC.q();
        }

        @Override
        public void setNClob(final int i, final NClob nclob)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setClob(final int i, final Reader reader, final long l)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setBlob(final int i, final InputStream in, final long l)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setNClob(final int i, final Reader reader, final long l)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setSQLXML(final int i, final SQLXML sqlxml)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setAsciiStream(final int i, final InputStream in,
                final long l) throws SQLException {
            JDBC.q();
        }

        @Override
        public void setBinaryStream(final int i, final InputStream in,
                final long l) throws SQLException {
            JDBC.q();
        }

        @Override
        public void setCharacterStream(final int i, final Reader reader,
                final long l) throws SQLException {
            JDBC.q();
        }

        @Override
        public void setAsciiStream(final int i, final InputStream in)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setBinaryStream(final int i, final InputStream in)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setCharacterStream(final int i, final Reader reader)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setNCharacterStream(final int i, final Reader reader)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setClob(final int i, final Reader reader)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setBlob(final int i, final InputStream in)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setNClob(final int i, final Reader reader)
                throws SQLException {
            JDBC.q();
        }
    }

    public class cs extends ps implements CallableStatement {
        public cs(final co c, final String s) {
            super(c, s);
        }

        @Override
        public void registerOutParameter(final int i, final int sqlType)
                throws SQLException {
        }

        @Override
        public void registerOutParameter(final int i, final int sqlType,
                final int scale) throws SQLException {
        }

        @Override
        public boolean wasNull() throws SQLException {
            return false;
        }

        @Override
        public String getString(final int i) throws SQLException {
            return null;
        }

        @Override
        public boolean getBoolean(final int i) throws SQLException {
            return false;
        }

        @Override
        public byte getByte(final int i) throws SQLException {
            return 0;
        }

        @Override
        public short getShort(final int i) throws SQLException {
            return 0;
        }

        @Override
        public int getInt(final int i) throws SQLException {
            return 0;
        }

        @Override
        public long getLong(final int i) throws SQLException {
            return 0;
        }

        @Override
        public float getFloat(final int i) throws SQLException {
            return (float) 0.0;
        }

        @Override
        public double getDouble(final int i) throws SQLException {
            return 0.0;
        }

        @Override
        public BigDecimal getBigDecimal(final int i, final int scale)
                throws SQLException {
            return null;
        }

        @Override
        public Date getDate(final int i) throws SQLException {
            return null;
        }

        @Override
        public Time getTime(final int i) throws SQLException {
            return null;
        }

        @Override
        public Timestamp getTimestamp(final int i) throws SQLException {
            return null;
        }

        @Override
        public byte[] getBytes(final int i) throws SQLException {
            return null;
        }

        @Override
        public Object getObject(final int i) throws SQLException {
            return null;
        }

        @Override
        public BigDecimal getBigDecimal(final int parameterIndex)
                throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public Object getObject(final int i, final Map map) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public Ref getRef(final int i) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public Blob getBlob(final int i) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public Clob getClob(final int i) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public Array getArray(final int i) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public Date getDate(final int parameterIndex, final Calendar cal)
                throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public Time getTime(final int parameterIndex, final Calendar cal)
                throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public Timestamp getTimestamp(final int parameterIndex,
                final Calendar cal) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public void registerOutParameter(final int paramIndex,
                final int sqlType, final String typeName) throws SQLException {
            JDBC.q();
        }

        // 3
        @Override
        public void registerOutParameter(final String parameterName,
                final int sqlType) throws SQLException {
            JDBC.q();
        }

        @Override
        public void registerOutParameter(final String parameterName,
                final int sqlType, final int scale) throws SQLException {
            JDBC.q();
        }

        @Override
        public void registerOutParameter(final String parameterName,
                final int sqlType, final String typeName) throws SQLException {
            JDBC.q();
        }

        @Override
        public URL getURL(final int parameterIndex) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public void setURL(final String parameterName, final URL val)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setNull(final String parameterName, final int sqlType)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setBoolean(final String parameterName, final boolean x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setByte(final String parameterName, final byte x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setShort(final String parameterName, final short x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setInt(final String parameterName, final int x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setLong(final String parameterName, final long x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setFloat(final String parameterName, final float x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setDouble(final String parameterName, final double x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setBigDecimal(final String parameterName, final BigDecimal x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setString(final String parameterName, final String x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setBytes(final String parameterName, final byte[] x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setDate(final String parameterName, final Date x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setTime(final String parameterName, final Time x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setTimestamp(final String parameterName, final Timestamp x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setAsciiStream(final String parameterName,
                final InputStream x, final int length) throws SQLException {
            JDBC.q();
        }

        @Override
        public void setBinaryStream(final String parameterName,
                final InputStream x, final int length) throws SQLException {
            JDBC.q();
        }

        @Override
        public void setObject(final String parameterName, final Object x,
                final int targetSqlType, final int scale) throws SQLException {
            JDBC.q();
        }

        @Override
        public void setObject(final String parameterName, final Object x,
                final int targetSqlType) throws SQLException {
            JDBC.q();
        }

        @Override
        public void setObject(final String parameterName, final Object x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setCharacterStream(final String parameterName,
                final Reader reader, final int length) throws SQLException {
            JDBC.q();
        }

        @Override
        public void setDate(final String parameterName, final Date x,
                final Calendar cal) throws SQLException {
            JDBC.q();
        }

        @Override
        public void setTime(final String parameterName, final Time x,
                final Calendar cal) throws SQLException {
            JDBC.q();
        }

        @Override
        public void setTimestamp(final String parameterName, final Timestamp x,
                final Calendar cal) throws SQLException {
            JDBC.q();
        }

        @Override
        public void setNull(final String parameterName, final int sqlType,
                final String typeName) throws SQLException {
            JDBC.q();
        }

        @Override
        public String getString(final String parameterName) throws SQLException {
            return null;
        }

        @Override
        public boolean getBoolean(final String parameterName)
                throws SQLException {
            return false;
        }

        @Override
        public byte getByte(final String parameterName) throws SQLException {
            return 0;
        }

        @Override
        public short getShort(final String parameterName) throws SQLException {
            return 0;
        }

        @Override
        public int getInt(final String parameterName) throws SQLException {
            return 0;
        }

        @Override
        public long getLong(final String parameterName) throws SQLException {
            return 0;
        }

        @Override
        public float getFloat(final String parameterName) throws SQLException {
            return 0;
        }

        @Override
        public double getDouble(final String parameterName) throws SQLException {
            return 0;
        }

        @Override
        public byte[] getBytes(final String parameterName) throws SQLException {
            return null;
        }

        @Override
        public Date getDate(final String parameterName) throws SQLException {
            return null;
        }

        @Override
        public Time getTime(final String parameterName) throws SQLException {
            return null;
        }

        @Override
        public Timestamp getTimestamp(final String parameterName)
                throws SQLException {
            return null;
        }

        @Override
        public Object getObject(final String parameterName) throws SQLException {
            return null;
        }

        @Override
        public BigDecimal getBigDecimal(final String parameterName)
                throws SQLException {
            return null;
        }

        @Override
        public Object getObject(final String parameterName, final Map map)
                throws SQLException {
            return null;
        }

        @Override
        public Ref getRef(final String parameterName) throws SQLException {
            return null;
        }

        @Override
        public Blob getBlob(final String parameterName) throws SQLException {
            return null;
        }

        @Override
        public Clob getClob(final String parameterName) throws SQLException {
            return null;
        }

        @Override
        public Array getArray(final String parameterName) throws SQLException {
            return null;
        }

        @Override
        public Date getDate(final String parameterName, final Calendar cal)
                throws SQLException {
            return null;
        }

        @Override
        public Time getTime(final String parameterName, final Calendar cal)
                throws SQLException {
            return null;
        }

        @Override
        public Timestamp getTimestamp(final String parameterName,
                final Calendar cal) throws SQLException {
            return null;
        }

        @Override
        public URL getURL(final String parameterName) throws SQLException {
            return null;
        }

        // 4
        @Override
        public RowId getRowId(final int i) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public RowId getRowId(final String string) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public void setRowId(final String string, final RowId rowid)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setNString(final String string, final String string1)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setNCharacterStream(final String string,
                final Reader reader, final long l) throws SQLException {
            JDBC.q();
        }

        @Override
        public void setNClob(final String string, final NClob nclob)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setClob(final String string, final Reader reader,
                final long l) throws SQLException {
            JDBC.q();
        }

        @Override
        public void setBlob(final String string, final InputStream in,
                final long l) throws SQLException {
            JDBC.q();
        }

        @Override
        public void setNClob(final String string, final Reader reader,
                final long l) throws SQLException {
            JDBC.q();
        }

        @Override
        public NClob getNClob(final int i) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public NClob getNClob(final String string) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public void setSQLXML(final String string, final SQLXML sqlxml)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public SQLXML getSQLXML(final int i) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public SQLXML getSQLXML(final String string) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public String getNString(final int i) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public String getNString(final String string) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public Reader getNCharacterStream(final int i) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public Reader getNCharacterStream(final String string)
                throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public Reader getCharacterStream(final int i) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public Reader getCharacterStream(final String string)
                throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public void setBlob(final String string, final Blob blob)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setClob(final String string, final Clob clob)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setAsciiStream(final String string, final InputStream in,
                final long l) throws SQLException {
            JDBC.q();
        }

        @Override
        public void setBinaryStream(final String string, final InputStream in,
                final long l) throws SQLException {
            JDBC.q();
        }

        @Override
        public void setCharacterStream(final String string,
                final Reader reader, final long l) throws SQLException {
            JDBC.q();
        }

        @Override
        public void setAsciiStream(final String string, final InputStream in)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setBinaryStream(final String string, final InputStream in)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setCharacterStream(final String string, final Reader reader)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setNCharacterStream(final String string, final Reader reader)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setClob(final String string, final Reader reader)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setBlob(final String string, final InputStream in)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void setNClob(final String string, final Reader reader)
                throws SQLException {
            JDBC.q();
        }

        // 1.7
        @Override
        public <T> T getObject(final String s, final Class<T> t)
                throws SQLFeatureNotSupportedException {
            throw new SQLFeatureNotSupportedException("nyi");
        }

        @Override
        public <T> T getObject(final int parameterIndex, final Class<T> t)
                throws SQLFeatureNotSupportedException {
            throw new SQLFeatureNotSupportedException("nyi");
        }
    }

    public class rs implements ResultSet {
        private final st st;
        private String[] f;
        private Object o, d[];
        private int r, n;

        public rs(final st s, final Object x) throws SQLException {
            st = s;
            C.Flip a;
            try {
                a = C.td(x);
                f = a.x;
                d = a.y;
                n = C.n(d[0]);
                r = -1;
            } catch (final UnsupportedEncodingException ex) {
                throw new SQLException(ex);
            }
        }

        @Override
        public ResultSetMetaData getMetaData() throws SQLException {
            return new rm(f, d);
        }

        @Override
        public int findColumn(final String s) throws SQLException {
            return 1 + JDBC.find(f, s);
        }

        @Override
        public boolean next() throws SQLException {
            return ++r < n;
        }

        @Override
        public boolean wasNull() throws SQLException {
            return o == null;
        }

        @Override
        public Object getObject(final int i) throws SQLException {
            o = C.at(d[i - 1], r);
            return o instanceof char[] ? new String((char[]) o) : o;
        }

        @Override
        public boolean getBoolean(final int i) throws SQLException {
            return ((Boolean) getObject(i)).booleanValue();
        }

        @Override
        public byte getByte(final int i) throws SQLException {
            return ((Byte) getObject(i)).byteValue();
        }

        @Override
        public short getShort(final int i) throws SQLException {
            final Object x = getObject(i);
            return x == null ? 0 : ((Short) x).shortValue();
        }

        @Override
        public int getInt(final int i) throws SQLException {
            final Object x = getObject(i);
            return x == null ? 0 : ((Integer) x).intValue();
        }

        @Override
        public long getLong(final int i) throws SQLException {
            final Object x = getObject(i);
            return x == null ? 0 : ((Long) x).longValue();
        }

        @Override
        public float getFloat(final int i) throws SQLException {
            final Object x = getObject(i);
            return x == null ? 0 : ((Float) x).floatValue();
        }

        @Override
        public double getDouble(final int i) throws SQLException {
            final Object x = getObject(i);
            return x == null ? 0 : ((Double) x).doubleValue();
        }

        @Override
        public String getString(final int i) throws SQLException {
            final Object x = getObject(i);
            return x == null ? null : x.toString();
        }

        @Override
        public Date getDate(final int i) throws SQLException {
            return (Date) getObject(i);
        }

        @Override
        public Time getTime(final int i) throws SQLException {
            return (Time) getObject(i);
        }

        @Override
        public Timestamp getTimestamp(final int i) throws SQLException {
            return (Timestamp) getObject(i);
        }

        @Override
        public byte[] getBytes(final int i) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public BigDecimal getBigDecimal(final int i, final int scale)
                throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public InputStream getAsciiStream(final int i) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public InputStream getUnicodeStream(final int i) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public InputStream getBinaryStream(final int i) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public Object getObject(final String s) throws SQLException {
            return getObject(findColumn(s));
        }

        @Override
        public boolean getBoolean(final String s) throws SQLException {
            return getBoolean(findColumn(s));
        }

        @Override
        public byte getByte(final String s) throws SQLException {
            return getByte(findColumn(s));
        }

        @Override
        public short getShort(final String s) throws SQLException {
            return getShort(findColumn(s));
        }

        @Override
        public int getInt(final String s) throws SQLException {
            return getInt(findColumn(s));
        }

        @Override
        public long getLong(final String s) throws SQLException {
            return getLong(findColumn(s));
        }

        @Override
        public float getFloat(final String s) throws SQLException {
            return getFloat(findColumn(s));
        }

        @Override
        public double getDouble(final String s) throws SQLException {
            return getDouble(findColumn(s));
        }

        @Override
        public String getString(final String s) throws SQLException {
            return getString(findColumn(s));
        }

        @Override
        public Date getDate(final String s) throws SQLException {
            return getDate(findColumn(s));
        }

        @Override
        public Time getTime(final String s) throws SQLException {
            return getTime(findColumn(s));
        }

        @Override
        public Timestamp getTimestamp(final String s) throws SQLException {
            return getTimestamp(findColumn(s));
        }

        @Override
        public byte[] getBytes(final String s) throws SQLException {
            return getBytes(findColumn(s));
        }

        @Override
        public BigDecimal getBigDecimal(final String s, final int scale)
                throws SQLException {
            return getBigDecimal(findColumn(s), scale);
        }

        @Override
        public InputStream getAsciiStream(final String s) throws SQLException {
            return getAsciiStream(findColumn(s));
        }

        @Override
        public InputStream getUnicodeStream(final String s) throws SQLException {
            return getUnicodeStream(findColumn(s));
        }

        @Override
        public InputStream getBinaryStream(final String s) throws SQLException {
            return getBinaryStream(findColumn(s));
        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            return null;
        }

        @Override
        public void clearWarnings() throws SQLException {
        }

        @Override
        public String getCursorName() throws SQLException {
            JDBC.q("cur");
            return "";
        }

        @Override
        public void close() throws SQLException {
            d = null;
        }

        @Override
        public Reader getCharacterStream(final int columnIndex)
                throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public Reader getCharacterStream(final String columnName)
                throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public BigDecimal getBigDecimal(final int columnIndex)
                throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public BigDecimal getBigDecimal(final String columnName)
                throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public boolean isBeforeFirst() throws SQLException {
            return r < 0;
        }

        @Override
        public boolean isAfterLast() throws SQLException {
            return r >= n;
        }

        @Override
        public boolean isFirst() throws SQLException {
            return r == 0;
        }

        @Override
        public boolean isLast() throws SQLException {
            return r == n - 1;
        }

        @Override
        public void beforeFirst() throws SQLException {
            r = -1;
        }

        @Override
        public void afterLast() throws SQLException {
            r = n;
        }

        @Override
        public boolean first() throws SQLException {
            r = 0;
            return n > 0;
        }

        @Override
        public boolean last() throws SQLException {
            r = n - 1;
            return n > 0;
        }

        @Override
        public int getRow() throws SQLException {
            return r + 1;
        }

        @Override
        public boolean absolute(final int row) throws SQLException {
            r = row - 1;
            return r < n;
        }

        @Override
        public boolean relative(final int rows) throws SQLException {
            r += rows;
            return r >= 0 && r < n;
        }

        @Override
        public boolean previous() throws SQLException {
            --r;
            return r >= 0;
        }

        @Override
        public void setFetchDirection(final int direction) throws SQLException {
            JDBC.q("fd");
        }

        @Override
        public int getFetchDirection() throws SQLException {
            return FETCH_FORWARD;
        }

        @Override
        public void setFetchSize(final int rows) throws SQLException {
        }

        @Override
        public int getFetchSize() throws SQLException {
            return 0;
        }

        @Override
        public int getType() throws SQLException {
            return TYPE_SCROLL_SENSITIVE;
        }

        @Override
        public int getConcurrency() throws SQLException {
            return CONCUR_READ_ONLY;
        }

        @Override
        public boolean rowUpdated() throws SQLException {
            JDBC.q();
            return false;
        }

        @Override
        public boolean rowInserted() throws SQLException {
            JDBC.q();
            return false;
        }

        @Override
        public boolean rowDeleted() throws SQLException {
            JDBC.q();
            return false;
        }

        @Override
        public void updateNull(final int columnIndex) throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateBoolean(final int columnIndex, final boolean x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateByte(final int columnIndex, final byte x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateShort(final int columnIndex, final short x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateInt(final int columnIndex, final int x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateLong(final int columnIndex, final long x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateFloat(final int columnIndex, final float x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateDouble(final int columnIndex, final double x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateBigDecimal(final int columnIndex, final BigDecimal x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateString(final int columnIndex, final String x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateBytes(final int columnIndex, final byte[] x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateDate(final int columnIndex, final Date x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateTime(final int columnIndex, final Time x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateTimestamp(final int columnIndex, final Timestamp x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateAsciiStream(final int columnIndex,
                final InputStream x, final int length) throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateBinaryStream(final int columnIndex,
                final InputStream x, final int length) throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateCharacterStream(final int columnIndex,
                final Reader x, final int length) throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateObject(final int columnIndex, final Object x,
                final int scale) throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateObject(final int columnIndex, final Object x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateNull(final String columnName) throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateBoolean(final String columnName, final boolean x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateByte(final String columnName, final byte x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateShort(final String columnName, final short x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateInt(final String columnName, final int x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateLong(final String columnName, final long x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateFloat(final String columnName, final float x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateDouble(final String columnName, final double x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateBigDecimal(final String columnName, final BigDecimal x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateString(final String columnName, final String x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateBytes(final String columnName, final byte[] x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateDate(final String columnName, final Date x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateTime(final String columnName, final Time x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateTimestamp(final String columnName, final Timestamp x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateAsciiStream(final String columnName,
                final InputStream x, final int length) throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateBinaryStream(final String columnName,
                final InputStream x, final int length) throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateCharacterStream(final String columnName,
                final Reader reader, final int length) throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateObject(final String columnName, final Object x,
                final int scale) throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateObject(final String columnName, final Object x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void insertRow() throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateRow() throws SQLException {
            JDBC.q();
        }

        @Override
        public void deleteRow() throws SQLException {
            JDBC.q();
        }

        @Override
        public void refreshRow() throws SQLException {
            JDBC.q();
        }

        @Override
        public void cancelRowUpdates() throws SQLException {
            JDBC.q();
        }

        @Override
        public void moveToInsertRow() throws SQLException {
            JDBC.q();
        }

        @Override
        public void moveToCurrentRow() throws SQLException {
            JDBC.q();
        }

        @Override
        public Statement getStatement() throws SQLException {
            return st;
        }

        @Override
        public Object getObject(final int i, final Map map) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public Ref getRef(final int i) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public Blob getBlob(final int i) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public Clob getClob(final int i) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public Array getArray(final int i) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public Object getObject(final String colName, final Map map)
                throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public Ref getRef(final String colName) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public Blob getBlob(final String colName) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public Clob getClob(final String colName) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public Array getArray(final String colName) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public Date getDate(final int columnIndex, final Calendar cal)
                throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public Date getDate(final String columnName, final Calendar cal)
                throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public Time getTime(final int columnIndex, final Calendar cal)
                throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public Time getTime(final String columnName, final Calendar cal)
                throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public Timestamp getTimestamp(final int columnIndex, final Calendar cal)
                throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public Timestamp getTimestamp(final String columnName,
                final Calendar cal) throws SQLException {
            JDBC.q();
            return null;
        }

        // 3
        @Override
        public URL getURL(final int columnIndex) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public URL getURL(final String columnName) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public void updateRef(final int columnIndex, final Ref x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateRef(final String columnName, final Ref x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateBlob(final int columnIndex, final Blob x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateBlob(final String columnName, final Blob x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateClob(final int columnIndex, final Clob x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateClob(final String columnName, final Clob x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateArray(final int columnIndex, final Array x)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateArray(final String columnName, final Array x)
                throws SQLException {
            JDBC.q();
        }

        // 4
        @Override
        public RowId getRowId(final int i) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public RowId getRowId(final String string) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public void updateRowId(final int i, final RowId rowid)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateRowId(final String string, final RowId rowid)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public int getHoldability() throws SQLException {
            JDBC.q();
            return 0;
        }

        @Override
        public boolean isClosed() throws SQLException {
            return d == null;
        }

        @Override
        public void updateNString(final int i, final String string)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateNString(final String string, final String string1)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateNClob(final int i, final NClob nclob)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateNClob(final String string, final NClob nclob)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public NClob getNClob(final int i) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public NClob getNClob(final String string) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public SQLXML getSQLXML(final int i) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public SQLXML getSQLXML(final String string) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public void updateSQLXML(final int i, final SQLXML sqlxml)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateSQLXML(final String string, final SQLXML sqlxml)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public String getNString(final int i) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public String getNString(final String string) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public Reader getNCharacterStream(final int i) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public Reader getNCharacterStream(final String string)
                throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public void updateNCharacterStream(final int i, final Reader reader,
                final long l) throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateNCharacterStream(final String string,
                final Reader reader, final long l) throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateAsciiStream(final int i, final InputStream in,
                final long l) throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateBinaryStream(final int i, final InputStream in,
                final long l) throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateCharacterStream(final int i, final Reader reader,
                final long l) throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateAsciiStream(final String string,
                final InputStream in, final long l) throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateBinaryStream(final String string,
                final InputStream in, final long l) throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateCharacterStream(final String string,
                final Reader reader, final long l) throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateBlob(final int i, final InputStream in, final long l)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateBlob(final String string, final InputStream in,
                final long l) throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateClob(final int i, final Reader reader, final long l)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateClob(final String string, final Reader reader,
                final long l) throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateNClob(final int i, final Reader reader, final long l)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateNClob(final String string, final Reader reader,
                final long l) throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateNCharacterStream(final int i, final Reader reader)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateNCharacterStream(final String string,
                final Reader reader) throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateAsciiStream(final int i, final InputStream in)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateBinaryStream(final int i, final InputStream in)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateCharacterStream(final int i, final Reader reader)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateAsciiStream(final String string, final InputStream in)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateBinaryStream(final String string, final InputStream in)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateCharacterStream(final String string,
                final Reader reader) throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateBlob(final int i, final InputStream in)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateBlob(final String string, final InputStream in)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateClob(final int i, final Reader reader)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateClob(final String string, final Reader reader)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateNClob(final int i, final Reader reader)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public void updateNClob(final String string, final Reader reader)
                throws SQLException {
            JDBC.q();
        }

        @Override
        public <T> T unwrap(final Class<T> type) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public boolean isWrapperFor(final Class<?> type) throws SQLException {
            JDBC.q();
            return false;
        }

        // 1.7
        @Override
        public <T> T getObject(final String parameterName, final Class<T> t)
                throws SQLFeatureNotSupportedException {
            throw new SQLFeatureNotSupportedException("nyi");
        }

        @Override
        public <T> T getObject(final int columnIndex, final Class<T> t)
                throws SQLFeatureNotSupportedException {
            throw new SQLFeatureNotSupportedException("nyi");
        }
    }

    public class rm implements ResultSetMetaData {
        private final String[] f;
        private final Object[] d;

        public rm(final String[] x, final Object[] y) {
            f = x;
            d = y;
        }

        @Override
        public int getColumnCount() throws SQLException {
            return f.length;
        }

        @Override
        public String getColumnName(final int i) throws SQLException {
            return f[i - 1];
        }

        @Override
        public String getColumnTypeName(final int i) throws SQLException {
            return TYPE[C.t(d[i - 1])];
        }

        @Override
        public int getColumnDisplaySize(final int i) throws SQLException {
            return 11;
        }

        @Override
        public int getScale(final int i) throws SQLException {
            return 2;
        }

        @Override
        public int isNullable(final int i) throws SQLException {
            return 1;
        }

        @Override
        public String getColumnLabel(final int i) throws SQLException {
            return getColumnName(i);
        }

        @Override
        public int getColumnType(final int i) throws SQLException {
            return SQLTYPE[C.t(d[i - 1])];
        }

        @Override
        public int getPrecision(final int i) throws SQLException {
            return 11;
        } // SQLPREC[c.t(d[i-1])];}

        @Override
        public boolean isSigned(final int i) throws SQLException {
            return true;
        }

        @Override
        public String getTableName(final int i) throws SQLException {
            return "";
        }

        @Override
        public String getSchemaName(final int i) throws SQLException {
            return "";
        }

        @Override
        public String getCatalogName(final int i) throws SQLException {
            return "";
        }

        @Override
        public boolean isReadOnly(final int i) throws SQLException {
            return false;
        }

        @Override
        public boolean isWritable(final int i) throws SQLException {
            return false;
        }

        @Override
        public boolean isDefinitelyWritable(final int i) throws SQLException {
            return false;
        }

        @Override
        public boolean isAutoIncrement(final int i) throws SQLException {
            return false;
        }

        @Override
        public boolean isCaseSensitive(final int i) throws SQLException {
            return true;
        }

        @Override
        public boolean isSearchable(final int i) throws SQLException {
            return true;
        }

        @Override
        public boolean isCurrency(final int i) throws SQLException {
            return false;
        }

        @Override
        public String getColumnClassName(final int column) throws SQLException {
            JDBC.q("col");
            return null;
        }

        // 4
        @Override
        public <T> T unwrap(final Class<T> type) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public boolean isWrapperFor(final Class<?> type) throws SQLException {
            JDBC.q();
            return false;
        }
    }

    public class dm implements DatabaseMetaData {
        private final co co;

        public dm(final co x) {
            co = x;
        }

        @Override
        public ResultSet getCatalogs() throws SQLException {
            return co.qx("([]TABLE_CAT:`symbol$())");
        }

        @Override
        public ResultSet getSchemas() throws SQLException {
            return co.qx("([]TABLE_SCHEM:`symbol$())");
        }

        @Override
        public ResultSet getTableTypes() throws SQLException {
            return co.qx("([]TABLE_TYPE:`TABLE`VIEW)");
        }

        @Override
        public ResultSet getTables(final String a, final String b,
                final String t, final String x[]) throws SQLException {
            return co
                    .qx("raze{([]TABLE_CAT:`;TABLE_SCHEM:`;TABLE_NAME:system string`a`b x=`VIEW;TABLE_TYPE:x)}each",
                            x);
        }

        @Override
        public ResultSet getTypeInfo() throws SQLException {
            return co
                    .qx("`DATA_TYPE xasc([]TYPE_NAME:`boolean`byte`short`int`long`real`float`symbol`date`time`timestamp;DATA_TYPE:16 -2 5 4 -5 7 8 12 91 92 93;PRECISION:11;LITERAL_PREFIX:`;LITERAL_SUFFIX:`;CREATE_PARAMS:`;NULLABLE:1h;CASE_SENSITIVE:1b;SEARCHABLE:1h;UNSIGNED_ATTRIBUTE:0b;FIXED_PREC_SCALE:0b;AUTO_INCREMENT:0b;LOCAL_TYPE_NAME:`;MINIMUM_SCALE:0h;MAXIMUM_SCALE:0h;SQL_DATA_TYPE:0;SQL_DATETIME_SUB:0;NUM_PREC_RADIX:10)");
        }

        @Override
        public ResultSet getColumns(final String a, final String b, String t,
                final String c) throws SQLException {
            if (t.startsWith("%")) {
                t = "";
            }
            return co
                    .qx("select TABLE_CAT:`,TABLE_SCHEM:`,TABLE_NAME:n,COLUMN_NAME:c,DATA_TYPE:0,TYPE_NAME:t,COLUMN_SIZE:2000000000,BUFFER_LENGTH:0,DECIMAL_DIGITS:16,NUM_PREC_RADIX:10,NULLABLE:1,REMARKS:`,COLUMN_DEF:`,SQL_DATA_TYPE:0,SQL_DATETIME_SUB:0,CHAR_OCTET_LENGTH:2000000000,ORDINAL_POSITION:1+til count n,NULLABLE:`YES from .Q.nct`"
                            + t);
        }

        @Override
        public ResultSet getPrimaryKeys(final String a, final String b,
                final String t) throws SQLException {
            JDBC.q("pk");
            return co.qx("");
        } // "q)([]TABLE_CAT:'',TABLE_SCHEM:'',TABLE_NAME:'"+t+"',COLUMN_NAME:key "+t+",KEY_SEQ:1+asc count key "+t+",PK_NAME:'')");}

        @Override
        public ResultSet getImportedKeys(final String a, final String b,
                final String t) throws SQLException {
            JDBC.q("imp");
            return co.qx("");
        } // "q)select PKTABLE_CAT:'',PKTABLE_SCHEM:'',PKTABLE_NAME:x,PKCOLUMN_NAME:first each key each x,FKTABLE_CAT:'',FKTABLE_SCHEM:'',FKTABLE_NAME:'"+t+"',FKCOLUMN_NAME:y,KEY_SEQ:1,UPDATE_RULE:1,DELETE_RULE:0,FK_NAME:'',PK_NAME:'',DEFERRABILITY:0 from('x','y')vars fkey "+t);}

        @Override
        public ResultSet getProcedures(final String a, final String b,
                final String p) throws SQLException {
            JDBC.q("pr");
            return co.qx("");
        } // "q)([]PROCEDURE_CAT:'',PROCEDURE_SCHEM:'',PROCEDURE_NAME:varchar(),r0:0,r1:0,r2:0,REMARKS:'',PROCEDURE_TYPE:0)");}

        @Override
        public ResultSet getExportedKeys(final String a, final String b,
                final String t) throws SQLException {
            JDBC.q("exp");
            return null;
        }

        @Override
        public ResultSet getCrossReference(final String pa, final String pb,
                final String pt, final String fa, final String fb,
                final String ft) throws SQLException {
            JDBC.q("cr");
            return null;
        }

        @Override
        public ResultSet getIndexInfo(final String a, final String b,
                final String t, final boolean unique, final boolean approximate)
                throws SQLException {
            JDBC.q("ii");
            return null;
        }

        @Override
        public ResultSet getProcedureColumns(final String a, final String b,
                final String p, final String c) throws SQLException {
            JDBC.q("pc");
            return null;
        }

        // PROCEDURE_CAT PROCEDURE_SCHEM PROCEDURE_NAME ...
        @Override
        public ResultSet getColumnPrivileges(final String a, final String b,
                final String table, final String columnNamePattern)
                throws SQLException {
            JDBC.q("cp");
            return null;
        }

        // select TABLE_CAT TABLE_SCHEM TABLE_NAME COLUMN_NAME GRANTOR GRANTEE
        // PRIVILEGE IS_GRANTABLE ordered by COLUMN_NAME and PRIVILEGE.
        @Override
        public ResultSet getTablePrivileges(final String a, final String b,
                final String t) throws SQLException {
            JDBC.q("tp");
            return null;
        }

        // select TABLE_CAT TABLE_SCHEM TABLE_NAME GRANTOR GRANTEE PRIVILEGE
        // IS_GRANTABLE ordered by TABLE_SCHEM,TABLE_NAME,and PRIVILEGE.
        @Override
        public ResultSet getBestRowIdentifier(final String a, final String b,
                final String t, final int scope, final boolean nullable)
                throws SQLException {
            JDBC.q("br");
            return null;
        }

        // select SCOPE COLUMN_NAME DATA_TYPE TYPE_NAME COLUMN_SIZE
        // DECIMAL_DIGITS PSEUDO_COLUMN ordered by SCOPE
        @Override
        public ResultSet getVersionColumns(final String a, final String b,
                final String t) throws SQLException {
            JDBC.q("vc");
            return null;
        }

        // select SCOPE COLUMN_NAME DATA_TYPE TYPE_NAME COLUMN_SIZE
        // DECIMAL_DIGITS PSEUDO_COLUMN ordered by SCOPE
        @Override
        public boolean allProceduresAreCallable() throws SQLException {
            return true;
        }

        @Override
        public boolean allTablesAreSelectable() throws SQLException {
            return true;
        }

        @Override
        public boolean dataDefinitionCausesTransactionCommit()
                throws SQLException {
            return false;
        }

        @Override
        public boolean dataDefinitionIgnoredInTransactions()
                throws SQLException {
            return false;
        }

        @Override
        public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
            return true;
        }

        @Override
        public String getSchemaTerm() throws SQLException {
            return "schema";
        }

        @Override
        public String getProcedureTerm() throws SQLException {
            return "procedure";
        }

        @Override
        public String getCatalogTerm() throws SQLException {
            return "catalog";
        }

        @Override
        public String getCatalogSeparator() throws SQLException {
            return ".";
        }

        @Override
        public int getMaxBinaryLiteralLength() throws SQLException {
            return 0;
        }

        @Override
        public int getMaxCharLiteralLength() throws SQLException {
            return 0;
        }

        @Override
        public int getMaxColumnNameLength() throws SQLException {
            return 0;
        }

        @Override
        public int getMaxColumnsInGroupBy() throws SQLException {
            return 0;
        }

        @Override
        public int getMaxColumnsInIndex() throws SQLException {
            return 0;
        }

        @Override
        public int getMaxColumnsInOrderBy() throws SQLException {
            return 0;
        }

        @Override
        public int getMaxColumnsInSelect() throws SQLException {
            return 0;
        }

        @Override
        public int getMaxColumnsInTable() throws SQLException {
            return 0;
        }

        @Override
        public int getMaxConnections() throws SQLException {
            return 0;
        }

        @Override
        public int getMaxCursorNameLength() throws SQLException {
            return 0;
        }

        @Override
        public int getMaxIndexLength() throws SQLException {
            return 0;
        }

        @Override
        public int getMaxSchemaNameLength() throws SQLException {
            return 0;
        }

        @Override
        public int getMaxProcedureNameLength() throws SQLException {
            return 0;
        }

        @Override
        public int getMaxCatalogNameLength() throws SQLException {
            return 0;
        }

        @Override
        public int getMaxRowSize() throws SQLException {
            return 0;
        }

        @Override
        public int getMaxStatementLength() throws SQLException {
            return 0;
        }

        @Override
        public int getMaxStatements() throws SQLException {
            return 0;
        }

        @Override
        public int getMaxTableNameLength() throws SQLException {
            return 0;
        }

        @Override
        public int getMaxTablesInSelect() throws SQLException {
            return 0;
        }

        @Override
        public int getMaxUserNameLength() throws SQLException {
            return 0;
        }

        @Override
        public int getDefaultTransactionIsolation() throws SQLException {
            return Connection.TRANSACTION_SERIALIZABLE;
        }

        @Override
        public String getSQLKeywords() throws SQLException {
            return "show,meta,load,save";
        }

        @Override
        public String getNumericFunctions() throws SQLException {
            return "";
        }

        @Override
        public String getStringFunctions() throws SQLException {
            return "";
        }

        @Override
        public String getSystemFunctions() throws SQLException {
            return "";
        }

        @Override
        public String getTimeDateFunctions() throws SQLException {
            return "";
        }

        @Override
        public String getSearchStringEscape() throws SQLException {
            return "";
        }

        @Override
        public String getExtraNameCharacters() throws SQLException {
            return "";
        }

        @Override
        public String getIdentifierQuoteString() throws SQLException {
            return "";
        }

        @Override
        public String getURL() throws SQLException {
            return null;
        }

        @Override
        public String getUserName() throws SQLException {
            return "";
        }

        @Override
        public String getDatabaseProductName() throws SQLException {
            return "kdb";
        }

        @Override
        public String getDatabaseProductVersion() throws SQLException {
            return "2.0";
        }

        @Override
        public String getDriverName() throws SQLException {
            return "jdbc";
        }

        @Override
        public String getDriverVersion() throws SQLException {
            return V + "." + v;
        }

        @Override
        public int getDriverMajorVersion() {
            return V;
        }

        @Override
        public int getDriverMinorVersion() {
            return v;
        }

        @Override
        public boolean isCatalogAtStart() throws SQLException {
            return true;
        }

        @Override
        public boolean isReadOnly() throws SQLException {
            return false;
        }

        @Override
        public boolean nullsAreSortedHigh() throws SQLException {
            return false;
        }

        @Override
        public boolean nullsAreSortedLow() throws SQLException {
            return true;
        }

        @Override
        public boolean nullsAreSortedAtStart() throws SQLException {
            return false;
        }

        @Override
        public boolean nullsAreSortedAtEnd() throws SQLException {
            return false;
        }

        @Override
        public boolean supportsMixedCaseIdentifiers() throws SQLException {
            return false;
        }

        @Override
        public boolean storesUpperCaseIdentifiers() throws SQLException {
            return false;
        }

        @Override
        public boolean storesLowerCaseIdentifiers() throws SQLException {
            return false;
        }

        @Override
        public boolean storesMixedCaseIdentifiers() throws SQLException {
            return true;
        }

        @Override
        public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
            return true;
        }

        @Override
        public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
            return false;
        }

        @Override
        public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
            return false;
        }

        @Override
        public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
            return true;
        }

        @Override
        public boolean supportsAlterTableWithAddColumn() throws SQLException {
            return true;
        }

        @Override
        public boolean supportsAlterTableWithDropColumn() throws SQLException {
            return true;
        }

        @Override
        public boolean supportsTableCorrelationNames() throws SQLException {
            return true;
        }

        @Override
        public boolean supportsDifferentTableCorrelationNames()
                throws SQLException {
            return true;
        }

        @Override
        public boolean supportsColumnAliasing() throws SQLException {
            return true;
        }

        @Override
        public boolean nullPlusNonNullIsNull() throws SQLException {
            return true;
        }

        @Override
        public boolean supportsExpressionsInOrderBy() throws SQLException {
            return true;
        }

        @Override
        public boolean supportsOrderByUnrelated() throws SQLException {
            return false;
        }

        @Override
        public boolean supportsGroupBy() throws SQLException {
            return true;
        }

        @Override
        public boolean supportsGroupByUnrelated() throws SQLException {
            return false;
        }

        @Override
        public boolean supportsGroupByBeyondSelect() throws SQLException {
            return false;
        }

        @Override
        public boolean supportsLikeEscapeClause() throws SQLException {
            return false;
        }

        @Override
        public boolean supportsMultipleResultSets() throws SQLException {
            return false;
        }

        @Override
        public boolean supportsMultipleTransactions() throws SQLException {
            return false;
        }

        @Override
        public boolean supportsNonNullableColumns() throws SQLException {
            return true;
        }

        @Override
        public boolean supportsMinimumSQLGrammar() throws SQLException {
            return true;
        }

        @Override
        public boolean supportsCoreSQLGrammar() throws SQLException {
            return true;
        }

        @Override
        public boolean supportsExtendedSQLGrammar() throws SQLException {
            return false;
        }

        @Override
        public boolean supportsANSI92EntryLevelSQL() throws SQLException {
            return true;
        }

        @Override
        public boolean supportsANSI92IntermediateSQL() throws SQLException {
            return false;
        }

        @Override
        public boolean supportsANSI92FullSQL() throws SQLException {
            return false;
        }

        @Override
        public boolean supportsIntegrityEnhancementFacility()
                throws SQLException {
            return false;
        }

        @Override
        public boolean supportsOuterJoins() throws SQLException {
            return false;
        }

        @Override
        public boolean supportsFullOuterJoins() throws SQLException {
            return false;
        }

        @Override
        public boolean supportsLimitedOuterJoins() throws SQLException {
            return false;
        }

        @Override
        public boolean supportsConvert() throws SQLException {
            return false;
        }

        @Override
        public boolean supportsConvert(final int fromType, final int toType)
                throws SQLException {
            return false;
        }

        @Override
        public boolean supportsSchemasInDataManipulation() throws SQLException {
            return false;
        }

        @Override
        public boolean supportsSchemasInProcedureCalls() throws SQLException {
            return false;
        }

        @Override
        public boolean supportsSchemasInTableDefinitions() throws SQLException {
            return false;
        }

        @Override
        public boolean supportsSchemasInIndexDefinitions() throws SQLException {
            return false;
        }

        @Override
        public boolean supportsSchemasInPrivilegeDefinitions()
                throws SQLException {
            return false;
        }

        @Override
        public boolean supportsCatalogsInDataManipulation() throws SQLException {
            return false;
        }

        @Override
        public boolean supportsCatalogsInProcedureCalls() throws SQLException {
            return false;
        }

        @Override
        public boolean supportsCatalogsInTableDefinitions() throws SQLException {
            return false;
        }

        @Override
        public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
            return false;
        }

        @Override
        public boolean supportsCatalogsInPrivilegeDefinitions()
                throws SQLException {
            return false;
        }

        @Override
        public boolean supportsSelectForUpdate() throws SQLException {
            return false;
        }

        @Override
        public boolean supportsPositionedDelete() throws SQLException {
            return false;
        }

        @Override
        public boolean supportsPositionedUpdate() throws SQLException {
            return false;
        }

        @Override
        public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
            return true;
        }

        @Override
        public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
            return true;
        }

        @Override
        public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
            return true;
        }

        @Override
        public boolean supportsOpenStatementsAcrossRollback()
                throws SQLException {
            return true;
        }

        @Override
        public boolean supportsStoredProcedures() throws SQLException {
            return false;
        }

        @Override
        public boolean supportsSubqueriesInComparisons() throws SQLException {
            return true;
        }

        @Override
        public boolean supportsSubqueriesInExists() throws SQLException {
            return true;
        }

        @Override
        public boolean supportsSubqueriesInIns() throws SQLException {
            return true;
        }

        @Override
        public boolean supportsSubqueriesInQuantifieds() throws SQLException {
            return true;
        }

        @Override
        public boolean supportsCorrelatedSubqueries() throws SQLException {
            return true;
        }

        @Override
        public boolean supportsUnion() throws SQLException {
            return true;
        }

        @Override
        public boolean supportsUnionAll() throws SQLException {
            return true;
        }

        @Override
        public boolean supportsTransactions() throws SQLException {
            return true;
        }

        @Override
        public boolean supportsTransactionIsolationLevel(final int level)
                throws SQLException {
            return true;
        }

        @Override
        public boolean supportsDataDefinitionAndDataManipulationTransactions()
                throws SQLException {
            return true;
        }

        @Override
        public boolean supportsDataManipulationTransactionsOnly()
                throws SQLException {
            return false;
        }

        @Override
        public boolean usesLocalFiles() throws SQLException {
            return false;
        }

        @Override
        public boolean usesLocalFilePerTable() throws SQLException {
            return false;
        }

        @Override
        public boolean supportsResultSetType(final int type)
                throws SQLException {
            return type != ResultSet.TYPE_SCROLL_SENSITIVE;
        }

        @Override
        public boolean supportsResultSetConcurrency(final int type,
                final int concurrency) throws SQLException {
            return type == ResultSet.CONCUR_READ_ONLY;
        }

        @Override
        public boolean ownUpdatesAreVisible(final int type) throws SQLException {
            return false;
        }

        @Override
        public boolean ownDeletesAreVisible(final int type) throws SQLException {
            return false;
        }

        @Override
        public boolean ownInsertsAreVisible(final int type) throws SQLException {
            return false;
        }

        @Override
        public boolean othersUpdatesAreVisible(final int type)
                throws SQLException {
            return false;
        }

        @Override
        public boolean othersDeletesAreVisible(final int type)
                throws SQLException {
            return false;
        }

        @Override
        public boolean othersInsertsAreVisible(final int type)
                throws SQLException {
            return false;
        }

        @Override
        public boolean updatesAreDetected(final int type) throws SQLException {
            return false;
        }

        @Override
        public boolean deletesAreDetected(final int type) throws SQLException {
            return false;
        }

        @Override
        public boolean insertsAreDetected(final int type) throws SQLException {
            return false;
        }

        @Override
        public boolean supportsBatchUpdates() throws SQLException {
            return false;
        }

        @Override
        public ResultSet getUDTs(final String catalog,
                final String schemaPattern, final String typeNamePattern,
                final int[] types) throws SQLException {
            return null;
        }

        @Override
        public Connection getConnection() throws SQLException {
            return co;
        }

        // 3
        @Override
        public boolean supportsSavepoints() throws SQLException {
            return false;
        }

        @Override
        public boolean supportsNamedParameters() throws SQLException {
            return false;
        }

        @Override
        public boolean supportsMultipleOpenResults() throws SQLException {
            return false;
        }

        @Override
        public boolean supportsGetGeneratedKeys() throws SQLException {
            return false;
        }

        @Override
        public ResultSet getSuperTypes(final String catalog,
                final String schemaPattern, final String typeNamePattern)
                throws SQLException {
            return null;
        }

        @Override
        public ResultSet getSuperTables(final String catalog,
                final String schemaPattern, final String tableNamePattern)
                throws SQLException {
            return null;
        }

        @Override
        public ResultSet getAttributes(final String catalog,
                final String schemaPattern, final String typeNamePattern,
                final String attributeNamePattern) throws SQLException {
            return null;
        }

        @Override
        public boolean supportsResultSetHoldability(final int holdability)
                throws SQLException {
            return false;
        }

        @Override
        public int getResultSetHoldability() throws SQLException {
            return 0;
        }

        @Override
        public int getDatabaseMajorVersion() throws SQLException {
            return 0;
        }

        @Override
        public int getDatabaseMinorVersion() throws SQLException {
            return 0;
        }

        @Override
        public int getJDBCMajorVersion() throws SQLException {
            return 0;
        }

        @Override
        public int getJDBCMinorVersion() throws SQLException {
            return 0;
        }

        @Override
        public int getSQLStateType() throws SQLException {
            return 0;
        }

        @Override
        public boolean locatorsUpdateCopy() throws SQLException {
            return false;
        }

        @Override
        public boolean supportsStatementPooling() throws SQLException {
            return false;
        }

        // 4
        @Override
        public RowIdLifetime getRowIdLifetime() throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public ResultSet getSchemas(final String string, final String string1)
                throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public boolean supportsStoredFunctionsUsingCallSyntax()
                throws SQLException {
            JDBC.q();
            return false;
        }

        @Override
        public boolean autoCommitFailureClosesAllResultSets()
                throws SQLException {
            JDBC.q();
            return false;
        }

        @Override
        public ResultSet getClientInfoProperties() throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public ResultSet getFunctions(final String string,
                final String string1, final String string2) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public ResultSet getFunctionColumns(final String string,
                final String string1, final String string2, final String string3)
                throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public <T> T unwrap(final Class<T> type) throws SQLException {
            JDBC.q();
            return null;
        }

        @Override
        public boolean isWrapperFor(final Class<?> type) throws SQLException {
            JDBC.q();
            return false;
        }

        // 1.7
        @Override
        public boolean generatedKeyAlwaysReturned() {
            return false;
        }

        @Override
        public ResultSet getPseudoColumns(final String catalog,
                final String schemaPattern, final String tableNamePattern,
                final String columnNamePattern)
                        throws SQLFeatureNotSupportedException {
            throw new SQLFeatureNotSupportedException("nyi");
        }
    }

    // 1.7
    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException("nyi");
    }
}

/*
 * class ar implements Array{ public String getBaseTypeName()throws
 * SQLException{q();return null;} public int getBaseType()throws
 * SQLException{q();return 0;} public Object getArray()throws
 * SQLException{q();return null;} public Object getArray(Map map)throws
 * SQLException{q();return null;} public Object getArray(long index,int
 * count)throws SQLException{q();return null;} public Object getArray(long
 * index,int count,Map map)throws SQLException{q();return null;} public
 * ResultSet getResultSet()throws SQLException{q();return null;} public
 * ResultSet getResultSet(Map map)throws SQLException{q();return null;} public
 * ResultSet getResultSet(long index,int count)throws SQLException{q();return
 * null;} public ResultSet getResultSet(long index,int count,Map map)throws
 * SQLException{q();return null;}} class bl implements Blob{ public long
 * length()throws SQLException{q();return 0L;} public byte[]getBytes(long
 * pos,int length)throws SQLException{q();return null;} public InputStream
 * getBinaryStream()throws SQLException{q();return null;} public long
 * position(byte[]pattern,long start)throws SQLException{q();return 0L;} public
 * long position(Blob pattern,long start)throws SQLException{q();return 0L;}}
 * class cl implements Clob{ public long length()throws SQLException{q();return
 * 0L;} public String getSubString(long pos,int length)throws
 * SQLException{q();return null;} public Reader getCharacterStream()throws
 * SQLException{q();return null;} public InputStream getAsciiStream()throws
 * SQLException{q();return null;} public long position(String searchstr,long
 * start)throws SQLException{q();return 0L;} public long position(Clob
 * searchstr,long start)throws SQLException{q();return 0L;}} class re implements
 * Ref{public String getBaseTypeName()throws SQLException{q();return null;}}
 * //DriverPropertyInfo a=new DriverPropertyInfo("user",null),b=new
 * DriverPropertyInfo("password",null),r[]=new DriverPropertyInfo[2];
 * //a.required=b.required=false;r[0]=a;r[1]=b;for(int
 * i=0;i<r.length;i++)r[i].value = p.getProperty(r[i].name);return r;} public
 * ResultSet getBestRowIdentifier(String a,String b,String t,int scope,boolean
 * nullable)throws SQLException {return co.qx("select
 * SCOPE:'1',COLUMN_NAME:name,
 * DATA_TYPE:([x:('int','float','varchar','date','time','timestamp','varbinary')]
 * y
 * :(4,8,12,91,92,93,-3,1111))[T].y,TYPE_NAME:T,COLUMN_SIZE:2000000000,BUFFER_LENGTH
 * :0,DECIMAL_DIGITS:16,PSEUDO_COLUMN:1 from meta " + t+" where name in key
 * "+t);}
 */