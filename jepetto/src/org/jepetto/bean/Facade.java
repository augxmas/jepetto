package org.jepetto.bean;


import java.io.File;
import java.io.IOException;
//import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Map;

import javax.naming.NamingException;

import org.jdom2.Document;
import org.jdom2.JDOMException;

//import org.jepetto.sql.Wrapper;

 
public interface Facade  { 
	 public java.lang.String execute(java.lang.String a,java.lang.String b,java.lang.String c,java.lang.String [] d) throws SQLException, NamingException, JDOMException, IOException;
	 public Document executeQuery(java.lang.String a,java.lang.String b,java.lang.String c,java.util.Map d,java.lang.String [] e) throws SQLException, NamingException, JDOMException, IOException;
	 public Document executeQuery(String dataSource, String file, String key, Map table,String arr[], int clobColumnIndex[]) throws  SQLException, NamingException, JDOMException, IOException;
	 
	 public int executeUpdate(java.lang.String a,java.lang.String b,java.lang.String c,java.util.Map d,java.lang.String [][] e) throws SQLException, NamingException, JDOMException, IOException;
	 public int executeUpdate(java.lang.String a,java.lang.String b,java.lang.String c,java.util.Map d,java.lang.String [] e) throws SQLException, NamingException, JDOMException, IOException;
	 public int executeUpdate(java.lang.String a,java.lang.String b,java.lang.String c,java.lang.String [] d) throws SQLException, NamingException, JDOMException, IOException;
	 public int executeUpdateX(java.lang.String a,java.lang.String [] b,java.lang.String [] c,java.lang.String [][] d) throws SQLException, NamingException, JDOMException, IOException;
	 public int executeUpdateX(java.lang.String a,java.lang.String b,java.lang.String c,java.lang.String [] d) throws SQLException, NamingException, JDOMException, IOException;
	 
	 public int executeUpdate( String dataSource, String files[], String keys[], String arr[][], int clobColumnIndex[],String clobValues[] ) throws  SQLException, NamingException, JDOMException, IOException;
	 public int executeUpdate( String dataSource, String files[], String keys[], String arr[][], int blobColumnIndex,File blobValue ) throws  SQLException, NamingException, JDOMException, IOException;
	 public int executeUpdateX( String dataSource, String files[], String keys[], String arr[][], int[] blobColumnsIndex,File[] blobValues ) throws  SQLException, NamingException, JDOMException, IOException;
	 
} 
