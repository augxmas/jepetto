package org.jepetto.bean;


import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Map;

import javax.naming.NamingException;



import org.jdom2.Document;
import org.jdom2.JDOMException;


import org.jepetto.logger.DisneyLogger;
import org.jepetto.proxy.HomeProxy;


/**
 * XDoclet-based session bean.  The class must be declared
 * public according to the EJB specification.
 *
 * To generate the EJB related files to this EJB:
 *		- Add Standard EJB module to XDoclet project properties
 *		- Customize XDoclet configuration for your appserver
 *		- Run XDoclet
 *
 * Below are the xdoclet-related tags needed for this EJB.
 * 
 * @ejb.bean name="Facade"
 *           display-name="Name for Facade"
 *           description="Description for Facade"
 *           jndi-name="ejb/Facade"
 *           type="Stateless"
 *           view-type="remote"
 */
public class FacadeBean implements Facade{//extends SessionAdapter{

	DisneyLogger cat = new DisneyLogger(FacadeBean.class.getName());

	
    /**
     * 寃��깋 寃곌낵媛� 0..1 �씪 �븣 �궗�슜�븳�떎
     * 
     * @param datasource 李몄“�븷 datasource name
     * @param file query xml �뙆�씪 紐�
     * @param key query xml �뙆�씪�궡�뿉�꽌 李몄“�븷 query id
     * @param table	移섑솚釉붾줉
     * @param arr	諛붿씤�뵫蹂��닔
     * @return 議고쉶寃곌낵
     * @throws SQLException 
     * @throws LSysException
     * @throws NamingException
     * @throws SQLException
     * @throws NamingException 
     * @throws JDOMException 
     * @throws IOException 
     * @throws JDOMException 
     * @throws IOException 
     * @throws LQueryException
     * 
     * 	
	 * An example business method
	 *
	 * @ejb.interface-method view-type = "remote"
	 * 
	 * 
	 */

     
    
	public Document executeQuery(String datasource, String file, String key, Map table, String arr[]) throws SQLException, NamingException, JDOMException, IOException{
            
		FacadeDAO dao = new FacadeDAO();
		Document doc = null;
		
		try {
			doc= dao.executeQuery(datasource,file,key,table,arr);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw e;
		}
		
		return doc;
	}//*/
	
	public Document executeQuery(String datasource, String file, String key, Map table, String arr[], int clobColumnIndex[]) throws SQLException, NamingException, JDOMException, IOException{
        
		FacadeDAO dao = new FacadeDAO();
		Document doc = null;
		
		try {
			doc= dao.executeQuery(datasource,file,key,table,arr,clobColumnIndex);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw e;
		}
		
		return doc;
	}//*/

	
	
	/**
	 * �떒�씪 嫄댁뿉 ���븳 insert, update, delete�뿉�꽌 �궗�슜�븳�떎
	 * @param datasource 李몄“�븷 datasource name
	 * @param file query xml �뙆�씪 紐�
	 * @param key query xml �뙆�씪�궡�뿉�꽌 李몄“�븷 query id
	 * @param arr 諛붿씤�뵫蹂��닔
	 * @return 泥섎━�맂 嫄댁닔
	 * @throws SQLException 
	 * @throws NamingException 
	 * @throws JDOMException 
	 * @throws IOException 
	 * @throws IllegalStateException
	 * @throws SystemException
	 * 
	 * @ejb.interface-method view-type = "remote"
	 */
	public int executeUpdate(String datasource, String file, String key, String arr[]) throws SQLException, NamingException, JDOMException, IOException {		
		FacadeDAO dao = new FacadeDAO();
		
		int count = -1;
		try {
			count = dao.executeUpdate(datasource,file,key,arr);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw e;
		}
		
		return count;
		
	}
	
	
	/**
	 * �떒�씪 嫄댁뿉 ���븳 insert, update, delete瑜� �떎�뻾�븳�떎. �떒 query�궡�뿉 移섑솚釉붾윮�씠 議댁옱�븷 寃쎌슦�뿉 �궗�슜�븳�떎
	 * 
	 * @param datasource
	 * @param file
	 * @param key
	 * @param table
	 * @param arr
	 * @return
	 * @throws SQLException 
	 * @throws NamingException 
	 * @throws JDOMException 
	 * @throws IOException 
	 * @throws IllegalStateException
	 * @throws SystemException
	 * @ejb.interface-method view-type = "remote"
	 */
	public int executeUpdate(String datasource, String file, String key, Map table, String arr[]) throws SQLException, NamingException, JDOMException, IOException {		
		FacadeDAO dao = new FacadeDAO();
		
		int count = -1;
		
		try {
			count = dao.executeUpdate(datasource,file,key,table, arr);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw e;
		}
		return count;
		
	}//*/
	
	
	/**
	 * 
	 * 0..N 嫄댁쓽 insert, update, delete瑜� �떎�뻾�븳�떎
	 * 
	 * @param datasource
	 * @param file
	 * @param key
	 * @param table
	 * @param arr
	 * @return
	 * @throws SQLException 
	 * @throws NamingException 
	 * @throws JDOMException 
	 * @throws IOException 
	 * @throws IllegalStateException
	 * @throws SystemException
	 * @ejb.interface-method view-type = "remote"
	 */
	public int executeUpdate(String datasource, String file, String key, Map table, String arr[][]) throws SQLException, NamingException, JDOMException, IOException {		
		FacadeDAO dao = new FacadeDAO();
		
		
		int count = -1;
		try {
			count = dao.executeUpdate(datasource,file,key,table, arr);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw e;
		}
		
		return count;
		
	}
	
	public int executeUpdate( String datasource, String files[], String keys[], String arr[][], int clobColumnIndex[],String clobValues[] ) throws  SQLException, NamingException, JDOMException, IOException{
		FacadeDAO dao = new FacadeDAO();
		
		
		int count = -1;
		try {
			count = dao.executeUpdate(datasource,files,keys, arr,clobColumnIndex,clobValues);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw e;
		}
		
		return count;
		
	}
	
	/**
	 * 蹂듭닔媛쒖쓽 insert,update,delete �떆�뿉 �궗�슜�븳�떎
	 * @param datasource 李몄“�븷 datasource name
	 * @param file query xml �뙆�씪 紐�
	 * @param key query xml �뙆�씪�궡�뿉�꽌 李몄“�븷 query id
	 * @param arr 諛붿씤�뵫 蹂��닔
	 * @return 泥섎━ 嫄댁닔
	 * @throws SQLException 
	 * @throws NamingException 
	 * @throws JDOMException 
	 * @throws IOException 
	 * @throws IllegalStateException
	 * @throws SystemException
	 * @ejb.interface-method view-type = "remote"
	 */
	public int executeUpdateX(String datasource, String file, String key, String arr[]) throws SQLException, NamingException, JDOMException, IOException { 
		
		FacadeDAO dao = new FacadeDAO();
		
		int count = -1;
		try {
			count = dao.executeUpdate(datasource,file,key,arr);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw e;
		}
		return count;
		
	}
	
	/**
	 * 
	 * 0..N 媛쒖쓽 insert,update, delete瑜� 泥섎━�븳�떎.
	 * �떒 媛� row 留덈떎 �샇異쒗븷 query瑜� �떖由ы븷 �닔 �엳�떎.
	 * 利� 1st row�뒗 insert, 2nd �뒗 delete ,3rd update �씠�윴 �떇�쑝濡� �떖由ы븷 �닔 �엳�떎.
	 * 
	 * @param dataSource
	 * @param files
	 * @param keys
	 * @param arr
	 * @return
	 * @throws SQLException 
	 * @throws NamingException 
	 * @throws JDOMException 
	 * @throws IOException 
	 * @throws IllegalStateException
	 * @throws SystemException
	 * @ejb.interface-method view-type = "remote"
	 */
	public int executeUpdateX(String dataSource, String files[], String keys[], String arr[][]) throws SQLException, NamingException, JDOMException, IOException {
	   
		FacadeDAO dao = new FacadeDAO();
		
		int count = -1;
		try {
			count = dao.executeUpdateX(dataSource,files,keys,arr);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw e;
		}
		return count;
	
	}
	
	/**
	 * SP瑜� �떎�뻾�븷 �븣 �궗�슜�븳�떎.
	 * 諛붿씤�뵫 蹂��닔�뒗 VarChar type�쑝濡� �븳�떎. SP�쓽 out 蹂��닔�룄 varchar濡� �젣�븳�븳�떎. 
	 * 
	 * @param dataSource
	 * @param file
	 * @param key
	 * @param arr
	 * @return
	 * @throws SQLException 
	 * @throws NamingException 
	 * @throws JDOMException 
	 * @throws IOException  
	 * @throws IllegalStateException
	 * @throws SystemException
	 * @ejb.interface-method view-type = "remote"
	 */
	public String execute(String dataSource, String file, String key, String arr[]) throws SQLException, NamingException, JDOMException, IOException {
		FacadeDAO dao = new FacadeDAO();
		
		String count = null;
		try {
			count = dao.execute(dataSource,file,key,arr);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw e;
		}
		
		return count;
	
	}
	
	public int executeUpdate( String dataSource, String files[], String keys[], String arr[][], int blobColumnsIndex,File blobValue ) throws  SQLException, NamingException, JDOMException, IOException{		
		
		FacadeDAO dao = new FacadeDAO();
		int count = -1;
		count = dao.executeUpdate(dataSource,files,keys, arr,blobColumnsIndex,blobValue);
		
		return count;
	}


	
	public int executeUpdateX(String dataSource, String[] files, String[] keys,String[][] arr, int[] blobColumnsIndex, File[] blobValues) throws SQLException, NamingException, JDOMException, IOException {
		FacadeDAO dao = new FacadeDAO();
		int count = -1;
		count = dao.executeUpdateX(dataSource,files,keys, arr,blobColumnsIndex,blobValues);
		
		return count;
	}
	
	
	
}
