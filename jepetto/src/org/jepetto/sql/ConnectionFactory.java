package org.jepetto.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.jepetto.util.PropertyReader;

//import com.powerbains.util.PropertyReader;


public class ConnectionFactory{
	
	private static ConnectionFactory instance = new ConnectionFactory();
	
	private Context ctx;
	
	private static String url;
	private static String user;
	private static String passwd;
	private static String driverClass;
	
	static {
		PropertyReader reader = PropertyReader.getInstance();
		url = reader.getProperty(Constants.DBURL);
		user = reader.getProperty(Constants.DBUSER);
		passwd = reader.getProperty(Constants.DBPASSWORD);
		/*
		driverClass = reader.getProperty(Constants.DRIVERCLASS);
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//*/
	}
	
	private ConnectionFactory(){
		try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // (1)
	}
	
	public static ConnectionFactory getInstance(){
		return instance;
	}
	
	public Connection getConnection(String dataSource) throws NamingException, SQLException{
		//DataSource ds =	(DataSource) ctx.lookup("java:/comp/env/jdbc/mydbtest");
		DataSource ds =	(DataSource) ctx.lookup(dataSource);
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			con = getConnection();
			throw e;
		}
		con.setAutoCommit(false);
		return con;
	}
	
	public Connection getConnection() throws SQLException {
		Connection con = DriverManager.getConnection(url,user,passwd);
		return con;
	}
	
}