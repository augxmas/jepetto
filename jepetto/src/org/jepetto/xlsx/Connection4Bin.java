package org.jepetto.xlsx;

import java.io.IOException;
import java.io.InputStream;

import java.sql.SQLException;

//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import org.jepetto.adapter.data.ConnectionAdapter;

/**
 * getting session object that used for reading excel file
 * @author mymac
 *
 */
public class Connection4Bin extends ConnectionAdapter {

	/**
	 * excel file inputstream
	 */
	private InputStream in;
	
	/**
	 * excel workbook
	 */
	private Workbook wb;
	
	/**
	 * Excel Connection object
	 * concept comes from java.sql.Connection
	 * 
	 * @param in excel file inputstream
	 * @throws SQLException InvalidFormatException, IOException wrapped to SQLExctpion. 
	 *  Exception occur when can't read excel file
	 */
	public Connection4Bin(InputStream in) throws SQLException {
		this.in = in;
		try {
			wb = WorkbookFactory.create(in);

		} catch (IOException e) {
			throw new SQLException(e);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void close() throws SQLException {
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
	}

	@Override
	/**
	 * SELECT 1, 2, 3, ....... COLUMN_NUM FROM SHEET_NUM
	 * ex:) select 1, 2, 3, 4, 5, 6 from 0
	 * 
	 * @param query for how to read excel file
	 * @return pstmt session object for reading excel file with specific query
	 * @throws SQLException InvalidFormatException, IOException wrapped to SQLExctpion. 	 * 
	 */
	public PreparedStatement4Bin prepareStatement(String query) throws SQLException {
		return new PreparedStatement4Bin(wb,query);
	}
	
}
