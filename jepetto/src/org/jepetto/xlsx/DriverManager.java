package org.jepetto.xlsx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

/**
 * used for getting connection object that can read excel file
 * @author mymac
 *
 */
public class DriverManager {

	
	/**
	 * 
	 * used for getting object that can read excel file
	 * this class's concept comes from java.lang.DriverManager
	 * 
	 * @param path directory for saved excel file
	 * @param fileName excel file name with suffix like "myexcel.xls"
	 * @return con a object that can read excel file content
	 * 
	 * @throws SQLException
	 */
	public static Connection getConnection(String path, String fileName) throws SQLException{
		
		Connection con = null;
		InputStream in = null;	
		File file = new File(path, fileName);
		
		try {
			
			in = new FileInputStream(file);
			con = new Connection(in);
			System.out.println(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			
		}
		
		
		return con;
	}
	

	
}
