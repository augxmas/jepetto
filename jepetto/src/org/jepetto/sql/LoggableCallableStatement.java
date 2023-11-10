package org.jepetto.sql;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.StringTokenizer;

public class LoggableCallableStatement implements CallableStatement {
	
	private java.sql.CallableStatement cStmt;

	public LoggableCallableStatement(Connection con , String query ) throws SQLException{
		cStmt = con.prepareCall(query,ResultSet.TYPE_SCROLL_SENSITIVE ,ResultSet.CONCUR_UPDATABLE);
	}
	
	/**
	 * used for storing parameter values needed for producing log
	 */
	private ArrayList parameterValues;

	/**
	 *the query string with question marks as parameter placeholders
	 */
	private String sqlTemplate;

	
	public Array getArray(int i) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getArray(i);
	}

	public Array getArray(String parameterName) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getArray(parameterName);
	}

	public BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getBigDecimal(parameterIndex);
	}

	public BigDecimal getBigDecimal(String parameterName) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getBigDecimal(parameterName);
	}

	public BigDecimal getBigDecimal(int parameterIndex, int scale)
			throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getBigDecimal(parameterIndex, scale);
	}

	public Blob getBlob(int i) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getBlob(i);
	}

	public Blob getBlob(String parameterName) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getBlob(parameterName);
	}

	public boolean getBoolean(int parameterIndex) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getBoolean(parameterIndex);
	}

	public boolean getBoolean(String parameterName) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getBoolean(parameterName);
	}

	public byte getByte(int parameterIndex) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getByte(parameterIndex);
	}

	public byte getByte(String parameterName) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getByte(parameterName);
	}

	public byte[] getBytes(int parameterIndex) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getBytes(parameterIndex)  ;
	}

	public byte[] getBytes(String parameterName) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getBytes(parameterName)  ;
	}

	public Clob getClob(int i) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getClob(i)  ;
	}

	public Clob getClob(String parameterName) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getClob(parameterName)  ;
	}

	public Date getDate(int parameterIndex) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getDate(parameterIndex)  ;
	}

	public Date getDate(String parameterName) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getDate(parameterName)  ;
	}

	public Date getDate(int parameterIndex, Calendar cal) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getDate(parameterIndex, cal)  ;
	}

	public Date getDate(String parameterName, Calendar cal) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getDate(parameterName,cal)  ;
	}

	public double getDouble(int parameterIndex) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getDouble(parameterIndex)  ;
	}

	public double getDouble(String parameterName) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getDouble(parameterName)  ;
	}

	public float getFloat(int parameterIndex) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getFloat(parameterIndex)  ;
	}

	public float getFloat(String parameterName) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getFloat(parameterName)  ;
	}

	public int getInt(int parameterIndex) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getInt(parameterIndex)  ;
	}

	public int getInt(String parameterName) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getInt(parameterName)  ;
	}

	public long getLong(int parameterIndex) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getLong(parameterIndex)  ;
	}

	public long getLong(String parameterName) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getLong(parameterName)  ;
	}

	public Object getObject(int parameterIndex) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getObject(parameterIndex)  ;
	}

	public Object getObject(String parameterName) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getObject(parameterName)  ;
	}

	public Object getObject(int i, Map map) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getObject(i, map)  ;
	}

	public Object getObject(String parameterName, Map map) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getObject(parameterName, map)  ;
	}

	public Ref getRef(int i) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getRef(i)  ;
	}

	public Ref getRef(String parameterName) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getRef(parameterName)  ;
	}

	public short getShort(int parameterIndex) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getShort(parameterIndex)  ;
	}

	public short getShort(String parameterName) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getShort(parameterName)  ;
	}

	public String getString(int parameterIndex) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getString(parameterIndex)  ;
	}

	public String getString(String parameterName) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getString(parameterName)  ;
	}

	public Time getTime(int parameterIndex) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getTime(parameterIndex)  ;
	}

	public Time getTime(String parameterName) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getTime(parameterName)  ;
	}

	public Time getTime(int parameterIndex, Calendar cal) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getTime(parameterIndex, cal)  ;
	}

	public Time getTime(String parameterName, Calendar cal) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getTime(parameterName, cal)  ;
	}

	public Timestamp getTimestamp(int parameterIndex) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getTimestamp(parameterIndex)  ;
	}

	public Timestamp getTimestamp(String parameterName) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getTimestamp(parameterName)  ;
	}

	public Timestamp getTimestamp(int parameterIndex, Calendar cal)
			throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getTimestamp(parameterIndex, cal)  ;
	}

	public Timestamp getTimestamp(String parameterName, Calendar cal)
			throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getTimestamp(parameterName, cal)  ;
	}

	public URL getURL(int parameterIndex) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getURL(parameterIndex)  ;
	}

	public URL getURL(String parameterName) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getURL(parameterName)  ;
	}

	public void registerOutParameter(int parameterIndex, int sqlType)
			throws SQLException {
		cStmt.registerOutParameter(parameterIndex, sqlType);

	}

	public void registerOutParameter(String parameterName, int sqlType)
			throws SQLException {
		// TODO Auto-generated method stub
		cStmt.registerOutParameter(parameterName, sqlType) ;

	}

	public void registerOutParameter(int parameterIndex, int sqlType, int scale)
			throws SQLException {
		// TODO Auto-generated method stub
		cStmt.registerOutParameter(parameterIndex, sqlType, scale) ;
	}

	public void registerOutParameter(int paramIndex, int sqlType,
			String typeName) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.registerOutParameter(paramIndex, sqlType, typeName) ;

	}

	public void registerOutParameter(String parameterName, int sqlType,
			int scale) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.registerOutParameter(parameterName, sqlType, scale) ;
	}

	public void registerOutParameter(String parameterName, int sqlType,
			String typeName) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.registerOutParameter(parameterName, sqlType, typeName) ;
	}

	public void setAsciiStream(String parameterName, InputStream x, int length)
			throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setAsciiStream(parameterName, x, length) ;
	}

	public void setBigDecimal(String parameterName, BigDecimal x)
			throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setBigDecimal(parameterName, x) ;
	}

	public void setBinaryStream(String parameterName, InputStream x, int length)
			throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setBinaryStream(parameterName, x, length) ;
	}

	public void setBoolean(String parameterName, boolean x) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setBoolean(parameterName, x);
	}

	public void setByte(String parameterName, byte x) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setByte(parameterName, x) ;
	}

	public void setBytes(String parameterName, byte[] x) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setBytes(parameterName, x) ;
	}

	public void setCharacterStream(String parameterName, Reader reader,
			int length) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setCharacterStream(parameterName, reader, length) ;
	}

	public void setDate(String parameterName, Date x) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setDate(parameterName, x);
	}

	public void setDate(String parameterName, Date x, Calendar cal)
			throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setDate(parameterName, x, cal);
	}

	public void setDouble(String parameterName, double x) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setDouble(parameterName, x);
	}

	public void setFloat(String parameterName, float x) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setFloat(parameterName, x);
	}

	public void setInt(String parameterName, int x) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setInt(parameterName, x);
	}

	public void setLong(String parameterName, long x) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setLong(parameterName, x) ;
	}

	public void setNull(String parameterName, int sqlType) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setNull(parameterName, sqlType) ;
	}

	public void setNull(String parameterName, int sqlType, String typeName)
			throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setNull(parameterName, sqlType, typeName) ;
	}

	public void setObject(String parameterName, Object x) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setObject(parameterName, x) ;
	}

	public void setObject(String parameterName, Object x, int targetSqlType)
			throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setObject(parameterName, x, targetSqlType) ;
	}

	public void setObject(String parameterName, Object x, int targetSqlType,
			int scale) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setObject(parameterName, x, targetSqlType) ;
	}

	public void setShort(String parameterName, short x) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setShort(parameterName, x) ;
	}

	public void setString(String parameterName, String x) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setString(parameterName, x) ;
	}

	public void setTime(String parameterName, Time x) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setTime(parameterName, x) ;
	}

	public void setTime(String parameterName, Time x, Calendar cal)
			throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setTime(parameterName, x, cal) ;
	}

	public void setTimestamp(String parameterName, Timestamp x)
			throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setTimestamp(parameterName, x) ;
	}

	public void setTimestamp(String parameterName, Timestamp x, Calendar cal)
			throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setTimestamp(parameterName, x, cal) ;

	}

	public void setURL(String parameterName, URL val) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setURL(parameterName, val) ;
	}

	public boolean wasNull() throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.wasNull() ;
	}

	public void addBatch() throws SQLException {
		// TODO Auto-generated method stub
		cStmt.addBatch() ;
	}

	public void clearParameters() throws SQLException {
		// TODO Auto-generated method stub
		cStmt.clearParameters() ;
	}

	public boolean execute() throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.execute();
	}

	public ResultSet executeQuery() throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.executeQuery()  ;
	}

	public int executeUpdate() throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.executeUpdate()  ;
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getMetaData()  ;
	}

	public ParameterMetaData getParameterMetaData() throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getParameterMetaData()  ;
	}

	public void setArray(int i, Array x) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setArray(i, x) ;
	}

	public void setAsciiStream(int parameterIndex, InputStream x, int length)
			throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setAsciiStream(parameterIndex, x, length) ;
	}

	public void setBigDecimal(int parameterIndex, BigDecimal x)
			throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setBigDecimal(parameterIndex, x) ;
	}

	public void setBinaryStream(int parameterIndex, InputStream x, int length)
			throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setBinaryStream(parameterIndex, x, length) ;
	}

	public void setBlob(int i, Blob x) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setBlob(i, x) ;
	}

	public void setBoolean(int parameterIndex, boolean x) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setBoolean(parameterIndex, x) ;
	}

	public void setByte(int parameterIndex, byte x) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setByte(parameterIndex, x) ;
	}

	public void setBytes(int parameterIndex, byte[] x) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setBytes(parameterIndex, x) ;
	}

	public void setCharacterStream(int parameterIndex, Reader reader, int length)
			throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setCharacterStream(parameterIndex, reader, length) ;
	}

	public void setClob(int i, Clob x) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setClob(i, x) ;
	}

	public void setDate(int parameterIndex, Date x) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setDate(parameterIndex, x) ;
	}

	public void setDate(int parameterIndex, Date x, Calendar cal)
			throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setDate(parameterIndex, x, cal) ;
	}

	public void setDouble(int parameterIndex, double x) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setDouble(parameterIndex, x) ;
	}

	public void setFloat(int parameterIndex, float x) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setFloat(parameterIndex, x) ;
	}

	public void setInt(int parameterIndex, int x) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setInt(parameterIndex, x);
	}

	public void setLong(int parameterIndex, long x) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setLong(parameterIndex, x) ;
	}

	public void setNull(int parameterIndex, int sqlType) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setNull(parameterIndex, sqlType) ;
	}

	public void setNull(int paramIndex, int sqlType, String typeName)
			throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setNull(paramIndex, sqlType, typeName) ;
	}

	public void setObject(int parameterIndex, Object x) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setObject(parameterIndex, x) ;
	}

	public void setObject(int parameterIndex, Object x, int targetSqlType)
			throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setObject(parameterIndex, x, targetSqlType) ;
	}

	public void setObject(int parameterIndex, Object x, int targetSqlType,
			int scale) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setObject(parameterIndex, x, targetSqlType) ;
	}

	public void setRef(int i, Ref x) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setRef(i, x) ;
	}

	public void setShort(int parameterIndex, short x) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setShort(parameterIndex, x) ;
	}

	public void setString(int parameterIndex, String x) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setString(parameterIndex, x) ;
	}

	public void setTime(int parameterIndex, Time x) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setTime(parameterIndex, x) ;
	}

	public void setTime(int parameterIndex, Time x, Calendar cal)
			throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setTime(parameterIndex, x, cal) ;
	}

	public void setTimestamp(int parameterIndex, Timestamp x)
			throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setTimestamp(parameterIndex, x) ;
	}

	public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal)
			throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setTimestamp(parameterIndex, x, cal) ;
	}

	public void setURL(int parameterIndex, URL x) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setURL(parameterIndex, x) ;
	}

	public void setUnicodeStream(int parameterIndex, InputStream x, int length)
			throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setUnicodeStream(parameterIndex, x, length) ;
	}

	public void addBatch(String sql) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.addBatch(sql) ;
	}

	public void cancel() throws SQLException {
		// TODO Auto-generated method stub
		cStmt.cancel() ;
	}

	public void clearBatch() throws SQLException {
		// TODO Auto-generated method stub
		cStmt.clearBatch() ;
	}

	public void clearWarnings() throws SQLException {
		// TODO Auto-generated method stub
		cStmt.clearWarnings() ;
	}

	public void close() throws SQLException {
		// TODO Auto-generated method stub
		cStmt.close() ;
	}

	public boolean execute(String sql) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.execute(sql);
	}

	public boolean execute(String sql, int autoGeneratedKeys)
			throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.execute(sql, autoGeneratedKeys);
	}

	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.execute(sql, columnIndexes);
	}

	public boolean execute(String sql, String[] columnNames)
			throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.execute(sql, columnNames);
	}

	public int[] executeBatch() throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.executeBatch()  ;
	}

	public ResultSet executeQuery(String sql) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.executeQuery(sql)  ;
	}

	public int executeUpdate(String sql) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.executeUpdate(sql)  ;
	}

	public int executeUpdate(String sql, int autoGeneratedKeys)
			throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.executeUpdate(sql, autoGeneratedKeys)  ;
	}

	public int executeUpdate(String sql, int[] columnIndexes)
			throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.executeUpdate(sql, columnIndexes)  ;
	}

	public int executeUpdate(String sql, String[] columnNames)
			throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.executeUpdate(sql, columnNames)  ;
	}

	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getConnection()  ;
	}

	public int getFetchDirection() throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getFetchDirection()  ;
	}

	public int getFetchSize() throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getFetchSize()  ;
	}

	public ResultSet getGeneratedKeys() throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getGeneratedKeys()  ;
	}

	public int getMaxFieldSize() throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getMaxFieldSize()  ;
	}

	public int getMaxRows() throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getMaxRows()  ;
	}

	public boolean getMoreResults() throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getMoreResults();
	}

	public boolean getMoreResults(int current) throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getMoreResults(current);
	}

	public int getQueryTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getQueryTimeout()  ;
	}

	public ResultSet getResultSet() throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getResultSet()  ;
	}

	public int getResultSetConcurrency() throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getResultSetConcurrency()  ;
	}

	public int getResultSetHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getResultSetHoldability()  ;
	}

	public int getResultSetType() throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getResultSetType()  ;
	}

	public int getUpdateCount() throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getUpdateCount()  ;
	}

	public SQLWarning getWarnings() throws SQLException {
		// TODO Auto-generated method stub
		return cStmt.getWarnings()  ;
	}

	public void setCursorName(String name) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setCursorName(name) ;
	}

	public void setEscapeProcessing(boolean enable) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setEscapeProcessing(enable) ;
	}

	public void setFetchDirection(int direction) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setFetchDirection(direction) ;
	}

	public void setFetchSize(int rows) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setFetchSize(rows) ;
	}

	public void setMaxFieldSize(int max) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setMaxFieldSize(max) ;
	}

	public void setMaxRows(int max) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setMaxRows(max) ;
	}

	public void setQueryTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub
		cStmt.setQueryTimeout(seconds) ;
	}
	
	public String getQueryString() {

		StringBuffer buf = new StringBuffer();
		int qMarkCount = 0;
		ArrayList chunks = new ArrayList();
		StringTokenizer tok = new StringTokenizer(sqlTemplate+" ", "?");
		while (tok.hasMoreTokens()) {
			String oneChunk = tok.nextToken();
			
			if(oneChunk.length()>2000) oneChunk = oneChunk.substring(0, 2000)+"...";
			
			buf.append(oneChunk);

			try {
				Object value;
				if (parameterValues.size() > 1 + qMarkCount) {
					value = parameterValues.get(1 + qMarkCount++);
					
					if(value instanceof String) {
						String _value = (String)value;
						if(_value.length()>100) {
							_value = _value.substring(0, 100)+"...";
							value = _value;
						}
					}
					
				} else {
					if (tok.hasMoreTokens()) {
						value = null;
					} else {
						value = "";
					}
				}
				buf.append("" + value);
			} catch (Throwable e) {
				e.printStackTrace();
				buf.append(
					"ERROR WHEN PRODUCING QUERY STRING FOR LOG."
						+ e.toString());
				// catch this without whining, if this fails the only thing wrong is probably this class
			}
		}
		System.out.println("query = " + "\n" + buf.toString().trim());
		return buf.toString().trim();
	}

	public Reader getCharacterStream(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Reader getCharacterStream(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Reader getNCharacterStream(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Reader getNCharacterStream(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public NClob getNClob(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public NClob getNClob(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getNString(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getNString(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public RowId getRowId(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public RowId getRowId(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public SQLXML getSQLXML(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public SQLXML getSQLXML(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setAsciiStream(String arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setAsciiStream(String arg0, InputStream arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setBinaryStream(String arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setBinaryStream(String arg0, InputStream arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setBlob(String arg0, Blob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setBlob(String arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setBlob(String arg0, InputStream arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setCharacterStream(String arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setCharacterStream(String arg0, Reader arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setClob(String arg0, Clob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setClob(String arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setClob(String arg0, Reader arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setNCharacterStream(String arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setNCharacterStream(String arg0, Reader arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setNClob(String arg0, NClob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setNClob(String arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setNClob(String arg0, Reader arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setNString(String arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setRowId(String arg0, RowId arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setSQLXML(String arg0, SQLXML arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setAsciiStream(int arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setAsciiStream(int arg0, InputStream arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setBinaryStream(int arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setBinaryStream(int arg0, InputStream arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setBlob(int arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setBlob(int arg0, InputStream arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setCharacterStream(int arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setCharacterStream(int arg0, Reader arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setClob(int arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setClob(int arg0, Reader arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setNCharacterStream(int arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setNCharacterStream(int arg0, Reader arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setNClob(int arg0, NClob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setNClob(int arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setNClob(int arg0, Reader arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setNString(int arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setRowId(int arg0, RowId arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setSQLXML(int arg0, SQLXML arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isPoolable() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public void setPoolable(boolean arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public boolean isWrapperFor(Class arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public Object unwrap(Class arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setObject(int parameterIndex, Object x, SQLType targetSqlType, int scaleOrLength) throws SQLException {
		// TODO Auto-generated method stub
		CallableStatement.super.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
	}

	public void setObject(int parameterIndex, Object x, SQLType targetSqlType) throws SQLException {
		// TODO Auto-generated method stub
		CallableStatement.super.setObject(parameterIndex, x, targetSqlType);
	}

	public long executeLargeUpdate() throws SQLException {
		// TODO Auto-generated method stub
		return CallableStatement.super.executeLargeUpdate();
	}

	public void closeOnCompletion() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public boolean isCloseOnCompletion() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public long getLargeUpdateCount() throws SQLException {
		// TODO Auto-generated method stub
		return CallableStatement.super.getLargeUpdateCount();
	}

	public void setLargeMaxRows(long max) throws SQLException {
		// TODO Auto-generated method stub
		CallableStatement.super.setLargeMaxRows(max);
	}

	public long getLargeMaxRows() throws SQLException {
		// TODO Auto-generated method stub
		return CallableStatement.super.getLargeMaxRows();
	}

	public long[] executeLargeBatch() throws SQLException {
		// TODO Auto-generated method stub
		return CallableStatement.super.executeLargeBatch();
	}

	public long executeLargeUpdate(String sql) throws SQLException {
		// TODO Auto-generated method stub
		return CallableStatement.super.executeLargeUpdate(sql);
	}

	public long executeLargeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
		// TODO Auto-generated method stub
		return CallableStatement.super.executeLargeUpdate(sql, autoGeneratedKeys);
	}

	public long executeLargeUpdate(String sql, int[] columnIndexes) throws SQLException {
		// TODO Auto-generated method stub
		return CallableStatement.super.executeLargeUpdate(sql, columnIndexes);
	}

	public long executeLargeUpdate(String sql, String[] columnNames) throws SQLException {
		// TODO Auto-generated method stub
		return CallableStatement.super.executeLargeUpdate(sql, columnNames);
	}

	public <T> T getObject(int parameterIndex, Class<T> type) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T getObject(String parameterName, Class<T> type) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setObject(String parameterName, Object x, SQLType targetSqlType, int scaleOrLength)
			throws SQLException {
		// TODO Auto-generated method stub
		CallableStatement.super.setObject(parameterName, x, targetSqlType, scaleOrLength);
	}

	public void setObject(String parameterName, Object x, SQLType targetSqlType) throws SQLException {
		// TODO Auto-generated method stub
		CallableStatement.super.setObject(parameterName, x, targetSqlType);
	}

	public void registerOutParameter(int parameterIndex, SQLType sqlType) throws SQLException {
		// TODO Auto-generated method stub
		CallableStatement.super.registerOutParameter(parameterIndex, sqlType);
	}

	public void registerOutParameter(int parameterIndex, SQLType sqlType, int scale) throws SQLException {
		// TODO Auto-generated method stub
		CallableStatement.super.registerOutParameter(parameterIndex, sqlType, scale);
	}

	public void registerOutParameter(int parameterIndex, SQLType sqlType, String typeName) throws SQLException {
		// TODO Auto-generated method stub
		CallableStatement.super.registerOutParameter(parameterIndex, sqlType, typeName);
	}

	public void registerOutParameter(String parameterName, SQLType sqlType) throws SQLException {
		// TODO Auto-generated method stub
		CallableStatement.super.registerOutParameter(parameterName, sqlType);
	}

	public void registerOutParameter(String parameterName, SQLType sqlType, int scale) throws SQLException {
		// TODO Auto-generated method stub
		CallableStatement.super.registerOutParameter(parameterName, sqlType, scale);
	}

	public void registerOutParameter(String parameterName, SQLType sqlType, String typeName) throws SQLException {
		// TODO Auto-generated method stub
		CallableStatement.super.registerOutParameter(parameterName, sqlType, typeName);
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}


}
