package org.jepetto.sql;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import javax.naming.NamingException;
import org.jepetto.logger.DisneyLogger;
import org.jepetto.proxy.HomeProxy;



/**
 * 
 * @(#) Wrapper.java 
 *
 * <pre> 
 * JDBC, JTA?? ???? wrapping ??????關? ????????? ???? JDBC, JTA?? ???????? ???
 * </pre>
 * @author ??a?
 * @version 1.0 2006.02.10
 * @ see 
 *
 */
public class Wrapper {

	
	DisneyLogger cat = new DisneyLogger(Wrapper.class.getName());
	/**
	 * java.sql.Connection
	 */	
	private Connection con;
	
	/**
	 * loggable statement
	 */
	private LoggableStatement pStmt;
	
	/**
	 * callablestatement
	 */
	private LoggableCallableStatement cStmt;
	
	/**
	 * resultset
	 */
	private ResultSet rset;
	
	
	/**
	 * jndi datasource
	 */
	private String datasource;
	
	/**
	 * ????? ????? datasource ?? ???? connection pool?? ?????. 
	 * @param datasource
	 */
	public Wrapper(String datasource){
		this.datasource = datasource;
	}
	
	public Wrapper(){	}

	/**
	 * ??? ???? ?? resultset?? ???
	 * @return
	 */
	public ResultSet getResultSet()  {
		return rset;
	}
	
	private String query;
	
	/**
	 * result_meta data?? ???
	 * @return
	 * @throws SQLException
	 */
	public  ResultSetMetaData getMetaData() throws SQLException{
		return rset.getMetaData();
	}
	
	/**
	 * datasource?? ???? java.sql.Connection?? ??? ??쨈?
	 * ??? laf.xml?? ??o? datasource?? ???????
	 * @return
	 * @throws SQLException
	 * @throws javax.naming.NamingException
	 */
	public Connection getConnection() throws SQLException,javax.naming.NamingException{
		
		ConnectionFactory factory = ConnectionFactory.getInstance();
		
		con = factory.getConnection(datasource);

		return con;
	}
	
	
	/**
	 * @param query
	 * ????? ?????? ?????? ??????? ?????? preparedstatment?? ??
	 * @throws SQLException
	 * @throws NamingException
	 */
	
	public void setPreparedStatement(String query) throws SQLException, NamingException {
	    
	    try{
	        pStmt.close();
	    }catch(Exception e){
	        
	    }finally{
	        try{
		        if(con.isClosed()){
		            con = getConnection();
		        }
	        }catch(Exception e){	
	        	try{
	        		con = getConnection();
	        	}catch(Exception err){
	        		err.printStackTrace();
	        	}
	        }finally{
	            pStmt = new org.jepetto.sql.LoggableStatement(con,query);
	        }
	    }
		
	}
	
	
	
	/**
	 * <pre>
	 * ??)
	 * w.setCallableStatement("call SELECT_CONTENT(?,?,?)");
	 * w.setCInt(1,1);
	 * w.registOutParameterTypeChar(2);
	 * w.registOutParameterTypeChar(3);
	 * w.execute();
	 * System.out.println(w.getCString(2));
	 * System.out.println(w.getCString(3));
	 * ????? ?????? ?????? CallableStatement?? ???
	 * </pre>
	 * @param query
	 * @throws SQLException
	 * 
	 */
	
	public void setCallableStatement(String query) throws SQLException,javax.naming.NamingException{
		con = getConnection();
		try{
		    cStmt.close();
		}catch(Exception e){
		    
		}finally{
		    cStmt = new LoggableCallableStatement(con, query );//con.prepareCall(query,ResultSet.TYPE_SCROLL_SENSITIVE ,ResultSet.CONCUR_UPDATABLE);
		}
		
	}
	
	/**
	 * Callablestatement?? ????? ????? out ?????? Char ??????? ???
	 * @param index
	 * @throws SQLException
	 */
	
	public void registOutParameterTypeChar(int index) throws SQLException{
		cStmt.registerOutParameter(index,Types.CHAR);
	}
	
	/**
	 *  ????? ????? out ?????? VarChar ??????? ?????? ????
	 * @param index
	 * @throws SQLException
	 */
	public void registOutParameterTypeVarChar(int index) throws SQLException{
		cStmt.registerOutParameter(index,Types.VARCHAR);
	}
	
	/**
	 * Callable statement?? ????? ????? out ?????? int ??????? ?????? ????
	 * @param index
	 * @throws SQLException
	 */
	
	public void registOutParameterTypeInt(int index) throws SQLException{
		cStmt.registerOutParameter(index,Types.INTEGER);
	}
	
	/**
	 * Callable?? ????? ????? out ?????? Numeric ??????? ?????? ????
	 * for exam :
	 * w.setCallableStatement("call SELECT_CONTENT(?,?,?)");
	 * ???? store proc ?????? u째 ?????? in ???? ?????? ???, 2th, 3th ?????? out ???? ????? ???.
	 * w.setCInt(1,1);
	 * w.registOutParameterTypeChar(2); // out ???? ????? ??? ?????? ?關??? ???? ????? ???琯?
	 * w.registOutParameterTypeChar(3);
	 * w.execute(); // ????
	 * 
out.println(w.getCString(2));  // ???琯?? out ?????? ?????? ?琯????? ????? ???? ????
	 * System.out.println(w.getCString(3)); // ???琯?? out ?????? ?????? ?琯????? ????? ???? ????
	 * @param index
	 * @throws SQLException
	 */
	
	public void registOutParameterTypeNumeric(int index) throws SQLException{
		cStmt.registerOutParameter(index,Types.NUMERIC);
	}
	
	// 20160902 addBatch, executeBatch 추가
	public void addBatch() throws SQLException {
		pStmt.addBatch();
	}
	
	public int[] executeBatch() throws SQLException {
		return pStmt.executeBatch();
	}
	
	
	/**
	 * Callable statement?? ????? ????? int ????? in ?????? ???? ???
	 * @param index
	 * @param value
	 * @throws SQLException
	 */
	
	public void setCInt(int index, int value) throws SQLException{
		cStmt.setInt(index,value);
	}
	
	/**
	 * Callable statement?? ????? int ????? in ???? ?첨??? ???? ???
	 * @param col
	 * @param value
	 * @throws SQLException
	 */
	public void setCInt(String col, int value) throws SQLException{
		cStmt.setInt(col,value);
	}
	
	/**
	 * Callable?? ????? ????? char ????? in ?????? ???? ???
	 * @param index
	 * @param value
	 * @throws SQLException
	 */
	public void setCString(int index, String value) throws SQLException{
		cStmt.setString(index,value);;
	}
	
	/**
	 * Callable statement?? ????? char????? in ?????? ?첨? ?????  ???? ???
	 * @param col ???琯?? column name
	 * @param value ???琯?? ??
	 * @throws SQLException
	 */
	public void setCString(String col,String value) throws SQLException{
		cStmt.setString(col,value);
	}

	/**
	 * Callable statment ?? ????? ?첨? indxe?? Object ????? in ?????? ???? ???
	 * @param index ???琯? index
	 * @param o ???琯?? object instance
	 * @throws SQLException
	 */

	public void setCObject(int index, Object o) throws SQLException{
		cStmt.setObject(index,o);
	}

	/**
	 * prepareStatement???? ????? ??? ????? object?????琯????.
	 * @param index
	 * @param o
	 * @throws SQLException
	 */
	public void setObject(int index, Object o) throws SQLException{
		pStmt.setObject(index,o);
	}
	
	public void setClob(int index, String str) throws SQLException{
		Clob c = rset.getClob(index);
		Writer out = null;
		try{
			out = c.setCharacterStream(1L);
			out.write(str);
		}catch(SQLException e){
			throw e;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new SQLException(e.getMessage());
		}finally{
			try{
				out.close();
			}catch(Exception e){}
		}
	}
	
	public String getClob(int index) throws SQLException{
		Clob clob = rset.getClob(index);
		StringBuffer sb = new StringBuffer();
	    Reader instream = null;
	    try{
	    	instream = clob.getCharacterStream();
	    
		    char[] buffer = new char[10];
		    int length = 0;
	
		    while ((length = instream.read(buffer)) != -1)
		    {
		    	sb.append(new String(buffer));
		    }
	    }catch(IOException e){
	    	e.printStackTrace();
	    }finally{
	    	try{
	    		instream.close();
	    	}catch(Exception e){}
	    }
	    return sb.toString();
	}
	
	/**
	public void setBLOB(int index, String str) throws SQLException {
		ByteArrayInputStream bais = new ByteArrayInputStream(str.getBytes());
		pStmt.setBinaryStream(index,bais,str.getBytes().length);
	}
	
	public String getBLOB(String fd) throws SQLException {
		String str = "";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(rset.getBinaryStream(fd)));
			str = br.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				br.close();
			}catch(IOException e){
				
			}
		}
		return str;
	}
	
	
	public void returnCLOB(String fd, String value) throws SQLException {
		try {
			if (rset.next()) {
				CLOB cl = ( (OracleResultSet) rset).getCLOB(fd);
				BufferedWriter writer = new BufferedWriter(cl.getCharacterOutputStream());
				writer.write(value);
				writer.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//*/
	
	/**
	 * ??? ?????  Connection?? ???????
	 */
	public void close(){
	
		
		try{
			rset.close();
		}catch(Exception e){
		}
		
		try	{
			pStmt.close();
		} catch(Exception e){
		}
		
		try	{
			cStmt.close();
		}catch(Exception e){
		}
		
		try	{
		    con.close();
			//LConnectionManager.closeConnection(con);
		}catch(Exception e)	{
		}//*/
	}
	
	/*
	public String getCLOB(String fd) throws SQLException {
		String str = "";
		Reader reader = null;
		try {
			reader = rset.getCharacterStream(fd);
			StringBuffer sb = new StringBuffer();
			char[] buf = new char[1024];
			int readcnt;
			while ( (readcnt = reader.read(buf, 0, 1024)) != -1) {
				sb.append(buf,0,readcnt);
			}
			reader.close();
			return sb.toString();
		} catch (Exception e){
			e.printStackTrace();
		}finally{
			try{
				reader.close();
			}catch(IOException e){
				
			}
		}
		return str;
	}
	
	
	public void setAutoCommit(boolean res) throws SQLException {
		con.setAutoCommit(res);
	}//*/
	
	/**
	 * Callable ???? ?? ??? ??? ??? ????. ????? ?琯????? ?????? char ???? ???
	 * @param index
	 * @return
	 * @throws SQLException
	 */
	
	public String getCString(int index) throws SQLException{
		return cStmt.getString(index);
	}
	
	/**
	 * Callable ???? ?? ??? ??? ??? ????. ????? ?첨??? ?????? char ???? ???
	 * @param index
	 * @return
	 * @throws SQLException
	 */
	
	public String getCString(String col) throws SQLException{
		return cStmt.getString(col);
	}
	
	/**
	 * Callable?? ??????? ????? ?琯????? ?????? int ????? ???? ???
	 * @param index
	 * @return
	 * @throws SQLException
	 */
	public int getCInt(int index) throws SQLException{
		return cStmt.getInt(index);
	}
	
	/**
	 * Callable?? ??????? ????? ?첨??? ?????? int ????? ???? ???
	 * @param index
	 * @return
	 * @throws SQLException
	 */
	public int getCInt(String col) throws SQLException{
		return cStmt.getInt(col);
	}
	
	/*
	public String getLong(int index) throws SQLException{
		Long _long = new Long(rset.getLong(index));
		byte b = _long.byteValue();
		byte arr[] = new byte[1];
		arr[0] = b;
		return new String(arr);
	}
	
	public String getLong(String col) throws SQLException{
		Long _long = new Long(rset.getLong(col));
		byte b = _long.byteValue();
		byte arr[] = new byte[1];
		arr[0] = b;
		return new String(arr);
	}
	
	
	public Document getDocument(String path, String file) throws SAXException, ParserConfigurationException,SQLException,IOException,java.lang.ClassNotFoundException{
		IResult2Xml r2x = Result2XmlFactory.getInstance().getInstance("jepetto.xjdbc.Result2XmlOz");
		String approot = reader.getProperty("approot");
        StringBuffer sb = new StringBuffer(approot);
        sb.append(path);
        return r2x.convert2xml( rset , sb.toString() , file );
    }

	public Document getDocument(String _class, String path, String file) throws SAXException, ParserConfigurationException,SQLException,IOException,ClassNotFoundException{
		IResult2Xml r2x = Result2XmlFactory.getInstance().getInstance(_class);
        return r2x.convert2xml(rset,path,file);
    }
    //*/
    
	/**
	 * prepare statement ????, resultset ???
	 */
    public void executeQuery()throws SQLException{
   		cat.info(pStmt.getQueryString());
   		System.out.println(pStmt.getQueryString());
   		rset = pStmt.executeQuery();
    }
    
    /**
     * update, insert, delete ???? ????
     * @throws SQLException
     */
    public int executeUpdate()throws SQLException{
    	int count = -1;
   		cat.info(pStmt.getQueryString());
   		System.out.println(pStmt.getQueryString());
   		count = pStmt.executeUpdate();   		
    	return count;
    }
    
    /**
     * 
     * 
     * @param call callableStatment
     * @throws SQLException
     * callablestatement ?? ????
     */
    public void execute(CallableStatement call)throws SQLException{
    	
    	call.execute();
    }
    
    /**
     * callable ????
     * @throws SQLException
     */
    public void execute() throws SQLException{
    	cat.info(cStmt.getQueryString());   
    	System.out.println(cStmt.getQueryString());
    	cStmt.execute();
    }

    /**
     * ????? query?? callable statement?? ????
     * @param query callable statement query
     * @throws SQLException
     */
    public void execute(String query) throws SQLException {
    	
    	rset = cStmt.executeQuery(query);
    	cat.info(cStmt.getQueryString());
    }
    
    /**
     * ???쩔? ??? ?? ???? ???罐? ???
     * @return
     * @throws SQLException
     */
    public boolean next() throws SQLException{
    	return rset.next();
	}
	
	/**
	 * @return ???恝? ?恝李� ??????? true, ???????? ?????? false
	 * @throws SQLException
	 */
	public boolean hasPreviousElement() throws SQLException{
		return rset.previous();
	}
	
	/**
	 * @param index ?????? ???? ?첨? ?琯???
	 * @param str ??? ?첨??? ??????? ??
	 * @throws SQLException
	 * ????? ?琯????? ?첨??? ???恝? ???? ????
	 */
	public void setString(int index, String str) throws SQLException{
		pStmt.setString(index,str);
	}
	
	/**
	 * @param index 커???? ???? ?첨? ?????
	 * @param num ?첨??? ??????? ??
	 * @throws SQLException
	 */
	public void setInt(int index, int num)throws SQLException{
		pStmt.setInt(index,num);
	}
	
	/**
	 * @param index
	 * @param date
	 * @throws SQLException
	 */
	public void setDate( int index, Date date)throws SQLException{
		pStmt.setDate(index,date);
	}
	
	public int getInt(int index) throws SQLException{
		return rset.getInt(index);
	}
	
	public int getInt(String columName) throws SQLException{
		return rset.getInt(columName);
	}
	
	public java.sql.Date getDate( int index) throws SQLException {
		return rset.getDate(index);
	}
	
	public float getFloat(String columnName) throws SQLException{
		return rset.getFloat(columnName);
	}
	
	public float getFloat(int index) throws SQLException{
		return rset.getFloat(index);
	}
	
	public void setFloat(int index,float f) throws SQLException{
		pStmt.setFloat(index,f);
	}
	
	public java.sql.Date getDate(String columnName) throws SQLException{
		return rset.getDate(columnName);
	}
	
	public String getString(String columnName) throws SQLException{
		return rset.getString(columnName);
	}
	
	public String getString(int columnIndex) throws SQLException{
		return rset.getString(columnIndex);
	}
	
	
	/**
	 * cursor?? ????? index?? ????????
	 * @param index
	 * @throws SQLException
	 */
	public void  absolute(int index)throws SQLException{
		rset.absolute(index);
	}
	
	public void clearParameters() throws SQLException{
	    pStmt.clearParameters();
	}
	
	/**
	 * ????? resultset?? cursor?? ????? index?? ????
	 * @param rset
	 * @param index
	 * @throws SQLException
	 */
	public void absolute(ResultSet rset, int index)throws SQLException{
		rset.absolute(index);
	}
	
	public void beforeFirst(ResultSet rset) throws SQLException {
		rset.beforeFirst();
	}
	
	/**
	 * result?? cursor?? 
	 * @param rset
	 * @throws SQLException
	 */
	public void setFirst(ResultSet rset) throws SQLException {
		rset.first();
	}
	
	/**
	 * commit
	 * @throws SQLException
	 */
	public void commit() throws SQLException{
	    con.commit();
	}
	
	/**
	 * roll bakc
	 * @throws SQLException
	 */
	public void rollback() throws SQLException{
		con.rollback();
	}
	
	
	public static void main(String args[]) throws SQLException, ClassNotFoundException,Exception{
		
		Wrapper w = new Wrapper();
		Wrapper wrapper = w;
		//ConnectionPool p = ConnectionPool.getInstance();
		
		wrapper.con = w.getConnectionForTest();
		
		/*
		wrapper.setPreparedStatement("insert into simple values ('kdjflsoekfdslsdkdj','dfd',1)");
		wrapper.commit();
		try{
			wrapper.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		wrapper.setCallableStatement("");
		
		for( int i = 0 ; arr != null &&i < arr.length ; i++){
			wrapper.setCObject(i+1, arr[i]);
		}
		
		int index = arr.length + 1;
		wrapper.registOutParameterTypeVarChar( index++ );			// proc_cd varchar
		wrapper.registOutParameterTypeVarChar( index++ );			// proc_msg
		wrapper.registOutParameterTypeNumeric( index++ );		// number count
		wrapper.registOutParameterTypeNumeric( index++ );		// error count
		wrapper.registOutParameterTypeVarChar( index++ );			// error step
		wrapper.registOutParameterTypeVarChar( index++ );			// error msg
		wrapper.registOutParameterTypeVarChar( index++ );			// sqlerm
		*/
	}
	
	private Connection getConnectionForTest(){
		Connection con = null;
		String driver = "oracle.jdbc.driver.OracleDriver";
		String id = "scott";
		String passwd = "tiger";
		String uri = "jdbc:oracle:thin:@192.168.0.15:1521:orcl";
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(uri,id,passwd);
			//System.out.println(con.isClosed());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return con;
	}
	
	public void setBlob(int index, File f) throws SQLException{
		Blob b = (Blob) rset.getBlob(index);
		OutputStream out = null;
		InputStream in = null;
		byte[] bytes;
		try{
			out = b.setBinaryStream(0L);
			in = new FileInputStream(f);
			bytes = new byte[2048];
			int byteread = -1;
			while((byteread = in.read(bytes))!= -1){
				out.write(bytes, 0, byteread);
			}
			
			
		}catch(SQLException e){
			throw e;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				in.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try{
				out.close();
			}catch(Exception e){}
		}
	}	

}