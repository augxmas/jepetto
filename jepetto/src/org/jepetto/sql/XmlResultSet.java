package org.jepetto.sql;

import java.sql.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Hashtable;
import java.util.Calendar;

import org.jdom2.Element;
import org.jdom2.Attribute;



public class XmlResultSet implements ResultSet,Serializable{
    

	public XmlResultSet(List list) throws SQLException{
		try{
			this.list = list;
			this.size = list.size();
			
		}catch(java.lang.NullPointerException e){
			throw new XQLException(e);
		}
	}
	
	/**
	 * 
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean next() throws SQLException{
		cursor++;
		boolean flag = true;
		try{
			ele = (Element)list.get(cursor);
			rows = ele.getChildren();
			Element temp = null;
			table = new HashMap();
			for( int i = 0  ; i < rows.size() ; i++){
				temp = (Element)rows.get(i);
				table.put(temp.getName(),temp.getTextTrim());
			}
		}catch(IndexOutOfBoundsException  e){
			cursor = -1;
			flag = false;
		}catch(java.lang.ClassCastException e){
			Double d = (Double)list.get(cursor);
			table = new HashMap();
			table.put("count",d.intValue());
		}

		return flag;
	}
	
	public String getString(int columnIndex) throws SQLException{
		columnIndex--;
		Element ele = null;
		String value = null;
		try{
			ele = (Element)rows.get(columnIndex);
			value = ele.getTextTrim();
		}catch(Exception e){
			value = this.ele.getTextTrim();
		}
		return (value);
	}
	
	public String getString(String columnName) throws SQLException{
		return (String)table.get(columnName);
	}
	
	public boolean getBoolean(int columnIndex) throws SQLException{
		columnIndex--;
		boolean flag = false;
		Element ele = null;
		try{
			ele = (Element)rows.get(columnIndex);
			flag = Boolean.getBoolean(ele.getTextTrim());
		}catch(Exception e){
			flag = Boolean.getBoolean(this.ele.getTextTrim());
		}
		return flag;
	}
	
	public boolean getBoolean(String columnName) throws SQLException{

		String value = (String)table.get(columnName);
		return Boolean.getBoolean(value);
	}
		

	public byte getByte(int columnIndex) throws SQLException{
		columnIndex--;
		byte value = 0;
		Element ele = null;
		try{
			ele = (Element)rows.get(columnIndex);
			value = Byte.parseByte(ele.getTextTrim());
		}catch(Exception e){
			value = Byte.parseByte(this.ele.getTextTrim());
		}
		return value;
	}
	
	public byte getByte(String columnName) throws SQLException{

		String value = (String)table.get(columnName);
		return Byte.parseByte(value);
	}	

	public short getShort(int columnIndex) throws SQLException{
		columnIndex--;
		short value = 0;
		Element ele = null;
		try{
			ele = (Element)rows.get(columnIndex);
			value = Short.parseShort(ele.getTextTrim());
		}catch(Exception e){
			value = Short.parseShort(this.ele.getTextTrim());
		}
		return value;
	}
	
	public short getShort(String columnName) throws SQLException{

		String value = (String)table.get(columnName);
		return Short.parseShort(value);
		
	}	

	public int getInt(int columnIndex) throws SQLException{
		columnIndex--;
		int value = 0;
		Element ele = null;
		try{
			ele = (Element)rows.get(columnIndex);
			value = Integer.parseInt(ele.getTextTrim());
		}catch(Exception e){
			value = Integer.parseInt(this.ele.getTextTrim());
		}
		return value;
	}
	
	public int getInt(String columnName) throws SQLException{

		String value = (String)table.get(columnName);
		return Integer.parseInt(value);

		 
	}
		

	public long getLong(int columnIndex) throws SQLException{
		columnIndex--;
		long value = 0;
		Element ele = null;
		try{
			ele = (Element)rows.get(columnIndex);
			value = Long.parseLong(ele.getTextTrim());
		}catch(Exception e){
			value = Long.parseLong(this.ele.getTextTrim());
		}
		return value;
	}
	
	public long getLong(String columnName) throws SQLException{

		String value = (String)table.get(columnName);
		return Long.parseLong(value);
		
	}	

	public float getFloat(int columnIndex) throws SQLException{
		columnIndex--;
		float value = 0;
		Element ele = null;
		try{
			ele = (Element)rows.get(columnIndex);
			value = Float.parseFloat(ele.getTextTrim());
		}catch(Exception e){
			value = Float.parseFloat(this.ele.getTextTrim());
		}
		return value;
	}

	public float getFloat(String columnName) throws SQLException{

		String value = (String)table.get(columnName);
		return Float.parseFloat(value);
		
	}

	public double getDouble(int columnIndex) throws SQLException{
		columnIndex--;
		double value = 0;
		Element ele = null;
		try{
			ele = (Element)rows.get(columnIndex);
			value = Double.parseDouble(ele.getTextTrim());
		}catch(Exception e){
			value = Double.parseDouble(this.ele.getTextTrim());
		}
		return value;
	}
	
	public double getDouble(String columnName) throws SQLException{

		String value = (String)table.get(columnName);
		return Double.parseDouble(value);
		
	}	



	public byte[] getBytes(int columnIndex) throws SQLException{ return null ;}

	public java.sql.Date getDate(int columnIndex) throws SQLException{
		columnIndex--;
		java.sql.Date value = null;
		Element ele = null;
		try{
			ele = (Element)rows.get(columnIndex);
			value = java.sql.Date.valueOf(ele.getTextTrim());
		}catch(Exception e){
			value = java.sql.Date.valueOf(ele.getTextTrim());
		}
		return value;
	}
	
	public java.sql.Date getDate(String columnName) throws SQLException{
		String value = (String)table.get(columnName);
		return java.sql.Date.valueOf(value);
	}
	
	public void close() throws SQLException{
		list.clear();
	}
	
	public ResultSetMetaData getMetaData() throws SQLException{
		return new XmlResultSetMetaData(list);
	}

	
	
	public int getRow() throws SQLException{return cursor;}	
	
	public Object getObject(int columnIndex) throws SQLException{ return null;}
	public Object getObject(String columnName) throws SQLException{ return null;}
	public int findColumn(String columnName) throws SQLException{ return -1;}
	public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException{ return null;}
	public java.sql.Time getTime(int columnIndex) throws SQLException{ return null;}
	public java.sql.Timestamp getTimestamp(int columnIndex) throws SQLException{ return null;}
	public java.io.InputStream getAsciiStream(int columnIndex) throws SQLException{ return null;}
	public java.io.InputStream getUnicodeStream(int columnIndex) throws SQLException{ return null;}
	public java.io.InputStream getBinaryStream(int columnIndex)    throws SQLException{ return null;}
	public BigDecimal getBigDecimal(String columnName, int scale) throws SQLException{ return null;}
	public byte[] getBytes(String columnName) throws SQLException{ return null;}
	public java.sql.Time getTime(String columnName) throws SQLException{ return null;}
	public java.sql.Timestamp getTimestamp(String columnName) throws SQLException{ return null;}
	public java.io.InputStream getAsciiStream(String columnName) throws SQLException{ return null;}
	public java.io.InputStream getUnicodeStream(String columnName) throws SQLException{ return null;}
	public java.io.InputStream getBinaryStream(String columnName)throws SQLException{ return null;}
	public boolean wasNull() throws SQLException{return false;}
	public int getType() throws SQLException{return -1;}
	public SQLWarning getWarnings() throws SQLException{ return null;}
	public void clearWarnings() throws SQLException{ }
	public String getCursorName() throws SQLException{ return null;}
	public java.io.Reader getCharacterStream(int columnIndex) throws SQLException{ return null;}
	public java.io.Reader getCharacterStream(String columnName) throws SQLException{ return null;}
	public BigDecimal getBigDecimal(int columnIndex) throws SQLException{ return null;}
	public BigDecimal getBigDecimal(String columnName) throws SQLException{ return null;}
	public boolean isBeforeFirst() throws SQLException{ return false;}
	public boolean isAfterLast() throws SQLException{ return false;}
	public boolean isFirst() throws SQLException{ return false;}
	public boolean isLast() throws SQLException{ return false;}
	public void beforeFirst() throws SQLException{ }
	public void afterLast() throws SQLException{ }
	public boolean first() throws SQLException{ return false;}
	public boolean last() throws SQLException{ return false;}
	public boolean absolute( int row ) throws SQLException{ return false;}
	public boolean relative( int rows ) throws SQLException{ return false;}
	public boolean previous() throws SQLException{ return false;}
	public void setFetchDirection(int direction) throws SQLException{ }
	public int getFetchDirection() throws SQLException{ return -1; }
	public void setFetchSize(int rows) throws SQLException{ }
	public int getFetchSize() throws SQLException{ return -1;}
	public int getConcurrency() throws SQLException{ return -1;}
	public boolean rowUpdated() throws SQLException{ return false;}
	public boolean rowInserted() throws SQLException{ return false;}
	public boolean rowDeleted() throws SQLException{ return false;}
	public void updateNull(int columnIndex) throws SQLException{ }
	public void updateBoolean(int columnIndex, boolean x) throws SQLException{ }
	public void updateByte(int columnIndex, byte x) throws SQLException{ }
	public void updateShort(int columnIndex, short x) throws SQLException{ }
	public void updateInt(int columnIndex, int x) throws SQLException{ }
	public void updateLong(int columnIndex, long x) throws SQLException{ }
	public void updateFloat(int columnIndex, float x) throws SQLException{ }
	public void updateDouble(int columnIndex, double x) throws SQLException{ }
	public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException{ }
	public void updateString(int columnIndex, String x) throws SQLException{ }
	public void updateBytes(int columnIndex, byte x[]) throws SQLException{ }
	public void updateDate(int columnIndex, java.sql.Date x) throws SQLException{ }
	public void updateTime(int columnIndex, java.sql.Time x) throws SQLException{ }
	public void updateTimestamp(int columnIndex, java.sql.Timestamp x) throws SQLException{ }
	public void updateAsciiStream(int columnIndex, java.io.InputStream x, int length) throws SQLException{ }
	public void updateBinaryStream(int columnIndex, java.io.InputStream x, int length) throws SQLException{ }
	public void updateCharacterStream(int columnIndex, java.io.Reader x,int length) throws SQLException{ }
	public void updateObject(int columnIndex, Object x, int scale) throws SQLException{ }
	public void updateObject(int columnIndex, Object x) throws SQLException{ }
	public void updateNull(String columnName) throws SQLException{ }
	public void updateBoolean(String columnName, boolean x) throws SQLException{ }
	public void updateByte(String columnName, byte x) throws SQLException{}
	public void updateShort(String columnName, short x) throws SQLException{ }
	public void updateInt(String columnName, int x) throws SQLException{ }
	public void updateLong(String columnName, long x) throws SQLException{ }
	public void updateFloat(String columnName, float x) throws SQLException{ }
	public void updateDouble(String columnName, double x) throws SQLException{ }
	public void updateBigDecimal(String columnName, BigDecimal x) throws SQLException{ }
	public void updateString(String columnName, String x) throws SQLException{ }
	public void updateBytes(String columnName, byte x[]) throws SQLException{ }
	public void updateDate(String columnName, java.sql.Date x) throws SQLException{ }
	public void updateTime(String columnName, java.sql.Time x) throws SQLException{ }
	public void updateTimestamp(String columnName, java.sql.Timestamp x)  throws SQLException{ }
	public void updateAsciiStream(String columnName, java.io.InputStream x, int length) throws SQLException{ }
	public void updateBinaryStream(String columnName, java.io.InputStream x, int length) throws SQLException{ }
	public void updateCharacterStream(String columnName, java.io.Reader reader, int length) throws SQLException{ }
	public void updateObject(String columnName, Object x, int scale)  throws SQLException{}
	public void updateObject(String columnName, Object x) throws SQLException{ }
	public void insertRow() throws SQLException{ }
	public void updateRow() throws SQLException{ }
	public void deleteRow() throws SQLException{ }
	public void refreshRow() throws SQLException{ }
	public void cancelRowUpdates() throws SQLException{}
	public void moveToInsertRow() throws SQLException{ }
	public void moveToCurrentRow() throws SQLException{ }
	public Statement getStatement() throws SQLException{ return null; }
	public Object getObject(int i, java.util.Map map) throws SQLException{ return null; }
	public Ref getRef(int i) throws SQLException{ return null;}
	public Blob getBlob(int i) throws SQLException{ return null; }
	public Clob getClob(int i) throws SQLException{  return null;}
	public Array getArray(int i) throws SQLException{ return null;}
	public Object getObject(String colName, java.util.Map map) throws SQLException{ return null;}
	public Ref getRef(String colName) throws SQLException{ return null;}
	public Blob getBlob(String colName) throws SQLException{ return null;}
	public Clob getClob(String colName) throws SQLException{ return null;}
	public Array getArray(String colName) throws SQLException{ return null;}
	public java.sql.Date getDate(int columnIndex, Calendar cal) throws SQLException{ return null;}
	public java.sql.Date getDate(String columnName, Calendar cal) throws SQLException{ return null;}
	public java.sql.Time getTime(int columnIndex, Calendar cal) throws SQLException{ return null;}
	public java.sql.Time getTime(String columnName, Calendar cal) throws SQLException{ return null;}
	public java.sql.Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException{ return null;}
	public java.sql.Timestamp getTimestamp(String columnName, Calendar cal)  throws SQLException{ return null;}
	public java.net.URL getURL(int columnIndex) throws SQLException{ return null;}
	public java.net.URL getURL(String columnName) throws SQLException{ return null;}
	public void updateRef(int columnIndex, java.sql.Ref x) throws SQLException{ }
	public void updateRef(String columnName, java.sql.Ref x) throws SQLException{ }
	public void updateBlob(int columnIndex, java.sql.Blob x) throws SQLException{ }
	public void updateBlob(String columnName, java.sql.Blob x) throws SQLException{ }
	public void updateClob(int columnIndex, java.sql.Clob x) throws SQLException{ }
	public void updateClob(String columnName, java.sql.Clob x) throws SQLException{ }
	public void updateArray(int columnIndex, java.sql.Array x) throws SQLException{ }
	public void updateArray(String columnName, java.sql.Array x) throws SQLException{ }
	public String toString(){
		return ele.getText();
	}
	
	int FETCH_FORWARD = 1000;
	int FETCH_REVERSE = 1001;
	int FETCH_UNKNOWN = 1002;
	int TYPE_FORWARD_ONLY = 1003;
	int TYPE_SCROLL_INSENSITIVE = 1004;
	int TYPE_SCROLL_SENSITIVE = 1005;
	int CONCUR_READ_ONLY = 1007;
	int CONCUR_UPDATABLE = 1008;
	int HOLD_CURSORS_OVER_COMMIT = 1;
	int CLOSE_CURSORS_AT_COMMIT = 2;
	
	/**
	 * row cursor position
	 */
	private int cursor = -1;
	
	/**
	 * row count
	 */
	private int size;

	/**
	 * parent list
	 */
	private List list;
	
	/**
	 * chile list
	 */
	private List rows;
	
	/**
	 * parent element
	 */
	private Element ele;
	
	/**
	 * child element
	 */
	private Element rowEle;
	
	/**
	 * collection of child element, key is element name and value is element instance
	 */
	private HashMap table = null;
    
	
	public static void main(String args[]){
		PreparedStatement stmt = null;
		try {
			java.io.File file = new java.io.File("c:/temp/HCSN.HPPL2000_119795426421860934.xml");
			System.out.println("exist " + file.exists());
			
			stmt = new XmlStatement(file.getPath(),"//RecordSet[@xmlns='http://xmlns.oracle.com/apps/otm']/Row/element");
			ResultSet rset = stmt.executeQuery();
			int index = 0;
			System.out.println(rset.next());
			System.out.println( rset.getString("child") );		// column name referencing
			System.out.println( rset.getString("ReferenceTransmissionNo") );		// column name referencing
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public int getHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
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

	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public void updateAsciiStream(int arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateAsciiStream(String arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateAsciiStream(int arg0, InputStream arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateAsciiStream(String arg0, InputStream arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateBinaryStream(int arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateBinaryStream(String arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateBinaryStream(int arg0, InputStream arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateBinaryStream(String arg0, InputStream arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateBlob(int arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateBlob(String arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateBlob(int arg0, InputStream arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateBlob(String arg0, InputStream arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateCharacterStream(int arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateCharacterStream(String arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateCharacterStream(int arg0, Reader arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateCharacterStream(String arg0, Reader arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateClob(int arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateClob(String arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateClob(int arg0, Reader arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateClob(String arg0, Reader arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateNCharacterStream(int arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateNCharacterStream(String arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateNCharacterStream(int arg0, Reader arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateNCharacterStream(String arg0, Reader arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateNClob(int arg0, NClob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateNClob(String arg0, NClob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateNClob(int arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateNClob(String arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateNClob(int arg0, Reader arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateNClob(String arg0, Reader arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateNString(int arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateNString(String arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateRowId(int arg0, RowId arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateRowId(String arg0, RowId arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateSQLXML(int arg0, SQLXML arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateSQLXML(String arg0, SQLXML arg1) throws SQLException {
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

	public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateObject(int columnIndex, Object x, SQLType targetSqlType, int scaleOrLength) throws SQLException {
		// TODO Auto-generated method stub
		ResultSet.super.updateObject(columnIndex, x, targetSqlType, scaleOrLength);
	}

	public void updateObject(String columnLabel, Object x, SQLType targetSqlType, int scaleOrLength)
			throws SQLException {
		// TODO Auto-generated method stub
		ResultSet.super.updateObject(columnLabel, x, targetSqlType, scaleOrLength);
	}

	public void updateObject(int columnIndex, Object x, SQLType targetSqlType) throws SQLException {
		// TODO Auto-generated method stub
		ResultSet.super.updateObject(columnIndex, x, targetSqlType);
	}

	public void updateObject(String columnLabel, Object x, SQLType targetSqlType) throws SQLException {
		// TODO Auto-generated method stub
		ResultSet.super.updateObject(columnLabel, x, targetSqlType);
	}


}
