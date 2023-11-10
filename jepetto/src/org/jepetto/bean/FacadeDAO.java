package org.jepetto.bean;


import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;


import org.jdom2.Document;
import org.jdom2.JDOMException;

import org.jepetto.logger.DisneyLogger;
import org.jepetto.proxy.HomeProxy;
import org.jepetto.sql.AbstractDAO;
import org.jepetto.sql.Wrapper;
import org.jepetto.sql.XmlTransfer;



public class FacadeDAO {

	DisneyLogger cat = new DisneyLogger(FacadeDAO.class.getName());
	
	/**
	 * 
	 * ??? ??? Single LData?? ?????? ????
	 * ??? ?? ???? 0 ??? 1 ????? ??????
	 * 
	 * @param dataSource laf.xml???? ?????? datasource
	 * @param file query xml ????
	 * @param key ????? qurey id
	 * @param table	????
	 * @param arr ???ε? ????
	 * @return
	 * @throws LQueryException
	 * @throws SQLException
	 * @throws NamingException
	 * @throws JDOMException 
	 * @throws IOException 
	 * @throws Exception
	 */
	public Document executeQuery(String dataSource, String file, String key, Map table,String arr[]) throws  SQLException, NamingException, JDOMException, IOException {

		
		Wrapper wrapper = new Wrapper(dataSource);

		AbstractDAO dao = new AbstractDAO(); 
		XmlTransfer transfer = new XmlTransfer(); 
		ResultSet rset = null;
		Document doc = null;
		try {
			rset = dao.executeQuery(wrapper,file,key,table,arr);
			doc = transfer.trasnferRset2Dom(rset);
		} catch (SQLException e) {
			throw  e;
		} catch (NamingException e) {
			throw e;
		} catch (JDOMException e) {
			throw e;

		} catch (IOException e) {
			throw e;

		}
		finally{
			dao.close(wrapper);
		}

		return doc;
		
	}

	
	public Document executeQuery(String dataSource, String file, String key, Map table,String arr[], int clobColumnIndex[]) throws  SQLException, NamingException, JDOMException, IOException {
		
		Wrapper wrapper = new Wrapper(dataSource);

		AbstractDAO dao = new AbstractDAO(); 
		XmlTransfer transfer = new XmlTransfer(); 
		ResultSet rset = null;
		Document doc = null;
		try {
			rset = dao.executeQuery(wrapper,file,key,table,arr);
			doc = transfer.trasnferRset2Dom(wrapper,clobColumnIndex);
		} catch (SQLException e) {
			throw  e;
		} catch (NamingException e) {
			throw e;
		} catch (JDOMException e) {
			throw e;

		} catch (IOException e) {
			throw e;

		}
		finally{
			dao.close(wrapper);
		}

		return doc;
		
	}
	
	/**
	 * 
	 * ?? ??(single row)?? create, update, delete?? o????? ????
	 * 
	 * @param docSource laf.xml???? ?????? docsource
	 * @param file query xml ????
	 * @param key ????? query id 
	 * @param arr ???ε? ????
	 * @return
	 * @throws LSysException
	 * @throws SQLException
	 * @throws SQLException
	 * @throws LQueryException
	 * @throws NamingException
	 * @throws JDOMException 
	 * @throws IOException 
	 */
	public int executeUpdate(String docSource, String file, String key, String arr[]) throws  SQLException,  NamingException, JDOMException, IOException{
		
		AbstractDAO dao = new AbstractDAO();
		int count = 0;
		
		
		Wrapper wrapper = new Wrapper(docSource);
		try {
			count = dao.executeUpdate(wrapper,file,key,arr);
			dao.commit(wrapper);
		} catch (SQLException e) {
			dao.rollback(wrapper);
			throw e;
		} catch (NamingException e) {
			dao.rollback(wrapper);
			throw e;
		} catch (JDOMException e) {
			dao.rollback(wrapper);
			throw e;
		} catch (IOException e) {
			dao.rollback(wrapper);
			throw e;
		} 
		finally{
			dao.close(wrapper);
		}
	
		return count;
	}


	/**
	 * 
	 * ???? query?? ?? ????(multi row)?? ??????? ???? create, update, delete?? o????? ????
	 * 
	 * @param docSource laf.xml???? ?????? docsource
	 * @param file query xml ????
	 * @param key ????? query id 
	 * @param arr ???ε? ????
	 * @return
	 * @throws LSysException
	 * @throws LQueryException
	 * @throws SQLException
	 * @throws NamingException
	 * @throws JDOMException 
	 * @throws IOException 
	 */
	public int executeUpdate(String docSource, String file, String key, String arr[][]) throws   SQLException, NamingException, JDOMException, IOException{
		
		AbstractDAO dao = new AbstractDAO();
		
		int count = 0;
		Wrapper wrapper = new Wrapper(docSource);
		
		try {
			count = dao.executeUpdateX(wrapper,file,key,arr);
			dao.commit(wrapper);
		} catch (SQLException e) {
			dao.rollback(wrapper);
			throw e;
		} catch (NamingException e) {
			dao.rollback(wrapper);
			throw e;
		} catch (JDOMException e) {
			dao.rollback(wrapper);
			throw e;
		} catch (IOException e) {
			dao.rollback(wrapper);
			throw e;
		}finally{
			dao.close(wrapper);
		}
		
		return count; 
		
	}
	
	
	/**
	 * 
	 * ????????(multi row) o????μ? ???, ??? ?? ???? ????? query ????, key, ???ε? ???????? ?????? ??
	 * ??????
	 * 
	 * @param docSource laf.xml???? ?????? docsource
	 * @param files query xml ?????
	 * @param keys ????? query id
	 * @param arr ???ε? ????
	 * @return
	 * @throws LSysException
	 * @throws LQueryException
	 * @throws SQLException
	 * @throws NamingException
	 * @throws JDOMException 
	 * @throws IOException 
	 */
	public int executeUpdateX(String docSource, String files[], String keys[], String arr[][]) throws SQLException, NamingException, JDOMException, IOException{
	    int updateCount = 0;
		AbstractDAO dao = new AbstractDAO();
		
		Wrapper wrapper = new Wrapper(docSource);
		
		try {
		    for( int i = 0 ; i < files.length ; i++){
		        updateCount += dao.executeUpdate(wrapper,files[i],keys[i],arr[i]);
		    }
		    dao.commit(wrapper);
		} catch (SQLException e) {
			dao.rollback(wrapper);
			throw e;
		} catch (NamingException e) {
			dao.rollback(wrapper);
			throw e;
		} catch (JDOMException e) {
			dao.rollback(wrapper);
			throw e;
		} catch (IOException e) {
			dao.rollback(wrapper);
			throw e;
		} finally {
			dao.close(wrapper);
		}
	    
	    return updateCount;
	}
	
	

	/**
	 * 
	 * ?? ??(single row)?? create, update, delete?? o????? ????
	 * 
	 * @param docSource laf.xml???? ?????? docsource
	 * @param file query ????
	 * @param key ????? query id
	 * @param table ????
	 * @param arr ???ε?
	 * @return
	 * @throws SQLException
	 * @throws LQueryException
	 * @throws NamingException
	 * @throws JDOMException 
	 * @throws IOException 
	 */
	public int executeUpdate(String docSource, String file, String key, Map table, String arr[]) throws  SQLException,  NamingException, JDOMException, IOException{
		
		AbstractDAO dao = new AbstractDAO();
		int count = 0;
		
		
		Wrapper wrapper = new Wrapper(docSource);
		try {
			count = dao.executeUpdate(wrapper,file,key,table,arr);
			dao.commit(wrapper);
		} catch (SQLException e) {
			dao.rollback(wrapper);
			throw e;
		} catch (NamingException e) {
			dao.rollback(wrapper);
			throw e;
		} catch (JDOMException e) {
			dao.rollback(wrapper);
			throw e;
		} catch (IOException e) {
			dao.rollback(wrapper);
			throw e;
		} 
		finally{
			dao.close(wrapper);
		}
	
		return count;
	}

	
	/**
	 * multi row
	 * @param docSource
	 * @param file
	 * @param key
	 * @param table
	 * @param arr
	 * @return
	 * @throws SQLException
	 * @throws NamingException
	 * @throws JDOMException
	 * @throws IOException
	 */
	public int executeUpdate(String docSource, String file, String key, Map table, String arr[][]) throws  SQLException,  NamingException, JDOMException, IOException{
		
		AbstractDAO dao = new AbstractDAO();
		int count = 0;
		
		Wrapper wrapper = new Wrapper(docSource);
		
		try {
			count = dao.executeUpdateX(wrapper,file,key,table,arr);
			dao.commit(wrapper);
		} catch (SQLException e) {
			dao.rollback(wrapper);
			throw e;
		} catch (NamingException e) {
			dao.rollback(wrapper);
			throw e;
		} catch (JDOMException e) {
			dao.rollback(wrapper);
			throw e;
		} catch (IOException e) {
			dao.rollback(wrapper);
			throw e;
		} 
		finally{
			dao.close(wrapper);
		}
	
		return count;
	}
	
	
	public String execute(String docSource, String file ,String key, String arr[]) throws  SQLException, NamingException, JDOMException, IOException{
	    AbstractDAO dao = new AbstractDAO();
		Wrapper wrapper = new Wrapper(docSource);
	    String out = null;
	    
		try {
            out = dao.execute(wrapper,file,key,arr);
            dao.commit(wrapper);
        } catch (SQLException e) {
        	dao.rollback(wrapper);
            throw e;
        } catch (NamingException e) {
        	dao.rollback(wrapper);
            throw e;
        } catch (JDOMException e) {
        	dao.rollback(wrapper);
        	throw e;
		} catch (IOException e) {
			dao.rollback(wrapper);
			throw e;
		}finally{
            dao.close(wrapper);
        }
		
		return out;
		
	}

	
	public int executeUpdate( String docSource, String files[], String keys[], String arr[][], int clobColumnIndex[],String clobValues[] ) throws  SQLException, NamingException, JDOMException, IOException{
		AbstractDAO dao = new AbstractDAO();
		int count = 0;
		
		Wrapper wrapper = new Wrapper(docSource);
		
		try {
			count = dao.executeUpdate(wrapper,files,keys,arr,clobColumnIndex,clobValues);
			dao.commit(wrapper);
		} catch (SQLException e) {
			dao.rollback(wrapper);
			throw e;
		} catch (NamingException e) {
			dao.rollback(wrapper);
			throw e;
		} catch (JDOMException e) {
			dao.rollback(wrapper);
			throw e;
		} catch (IOException e) {
			dao.rollback(wrapper);
			throw e;
		} 
		finally{
			dao.close(wrapper);
		}
	
		return count;
		
	}


	public int executeUpdate(String dataSource, String[] files, String[] keys,
			String[][] arr, int blobColumnsIndex, File blobValue) throws  SQLException, NamingException, JDOMException, IOException{
		AbstractDAO dao = new AbstractDAO();
		int count = 0;
		
		Wrapper wrapper = new Wrapper(dataSource);
		
		try {
			count = dao.executeUpdate(wrapper,files,keys,arr,blobColumnsIndex,blobValue);
			dao.commit(wrapper);
		} catch (SQLException e) {
			dao.rollback(wrapper);
			throw e;
		} 
		finally{
			dao.close(wrapper);
		}
	
		return count;
		
	}


	public int executeUpdateX(String dataSource, String[] files, String[] keys,
			String[][] arr, int[] blobColumnsIndex, File[] blobValues) throws JDOMException, IOException, NamingException, SQLException {
		AbstractDAO dao = new AbstractDAO();
		int count = 0;
		
		Wrapper wrapper = new Wrapper(dataSource);
		
		try {
			count = dao.executeUpdateX(wrapper,files,keys,arr,blobColumnsIndex,blobValues);
			dao.commit(wrapper);
		} catch (SQLException e) {
			dao.rollback(wrapper);
			throw e;
		} 
		finally{
			dao.close(wrapper);
		}
	
		return count;
	}
}
