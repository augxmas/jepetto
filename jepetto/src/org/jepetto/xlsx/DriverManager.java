package org.jepetto.xlsx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * used for getting connection object that can read excel file
 * @author mymac
 *
 */
public class DriverManager {

	private static final String csvSuffix	= "csv";
	private static final String xlsSuffix	= "xls";
	private static final String xlsxSuffix	= "xlsx";
	
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
			
			if( fileName.endsWith(xlsSuffix) || fileName.endsWith(xlsxSuffix)) {
				con = new Connection4Bin(in);
			}else if(fileName.endsWith(csvSuffix)){
				con = new Connection4CSV(in);
			}else {
				throw new SQLException("Not supported File. only csv, xls, xlsx supported");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
		finally{
			
		}
		
		
		return con;
	}
	

	
}
