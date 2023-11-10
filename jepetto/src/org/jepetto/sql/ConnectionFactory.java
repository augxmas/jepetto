package org.jepetto.sql;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

//import com.powerbains.util.PropertyReader;


public class ConnectionFactory{
	
	private static ConnectionFactory instance = new ConnectionFactory();
	
	private ConnectionFactory(){
		try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // (1)
	}

	//private PropertyReader reader = PropertyReader.getInstance();
	
	private Context ctx;
	
	 
	
	public static ConnectionFactory getInstance(){
		return instance;
	}
	
	public Connection getConnection(String dataSource) throws NamingException, SQLException{
		
		//DataSource ds =	(DataSource) ctx.lookup("java:/comp/env/jdbc/mydbtest");
		DataSource ds =	(DataSource) ctx.lookup(dataSource);
		Connection con = ds.getConnection();
		con.setAutoCommit(false);
		return con;
	}
	
}