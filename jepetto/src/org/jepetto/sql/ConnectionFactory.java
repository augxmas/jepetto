package org.jepetto.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.jepetto.util.PropertyReader;

//import com.powerbains.util.PropertyReader;

public class ConnectionFactory {

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
		 * driverClass = reader.getProperty(Constants.DRIVERCLASS); try {
		 * Class.forName(driverClass); } catch (ClassNotFoundException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }//
		 */
	}

	private ConnectionFactory() {
		try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // (1)
	}

	public static ConnectionFactory getInstance() {
		return instance;
	}

	public Connection getConnection(String dataSource) throws NamingException, SQLException,ClassCastException {
		// DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/mydbtest");
		Connection con = null;
		try {
			DataSource ds = (DataSource) ctx.lookup(dataSource);
			con = ds.getConnection();
		}catch(ClassCastException e) {
			con = getConnection();
			con.setAutoCommit(false);
			e.printStackTrace();
			//throw e;
		}catch(NamingException e) {
			con = getConnection();
			con.setAutoCommit(false);
			e.printStackTrace();
			//throw e;
		}catch (SQLException e) {
			con = getConnection();
			con.setAutoCommit(false);
			e.printStackTrace();
			//throw e;
		}finally {
			con.setAutoCommit(false);
		}
		
		
		
		return con;
	}

	public Connection getConnection() throws SQLException {
		System.out.println("callme");
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, user, passwd);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			con = DriverManager.getConnection(url);
			
		}
		
		System.out.println(con.isClosed());
		
		return con;
	}

	public static void main(String[] args) {
		System.out.println("start..main");
		ConnectionFactory instance = ConnectionFactory.getInstance();
		Connection connection = null;
		try {
			// Load the SQLite JDBC driver
			// Class.forName("org.sqlite.JDBC");

			// Provide the full path to the database file
			connection = instance.getConnection();
			System.out.println("Connection to SQLite has been established.");

			// Create a statement object to execute SQL queries
			Statement statement = connection.createStatement();

			// Execute a simple SQL query
			String sql = "CREATE TABLE IF NOT EXISTS users (" + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "name TEXT NOT NULL," + "email TEXT NOT NULL" + ");";
			statement.executeUpdate(sql);

			System.out.println("Table created successfully.");

			// } catch (ClassNotFoundException e) {
			// System.out.println("SQLite JDBC Driver not found.");
			// e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
		System.out.println("end of..main");
	}

}