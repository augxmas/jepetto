package org.jepetto.xlsx;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jepetto.adapter.data.PreparedStatementAdapter;

public class PreparedStatement4Bin extends PreparedStatementAdapter{
	
	private Workbook wb = null;
	
	private String query;

	public PreparedStatement4Bin(String query) {
		// TODO Auto-generated constructor stub
		
	}

	public PreparedStatement4Bin(Workbook wb, String query) {
		// TODO Auto-generated constructor stub
		this.wb = wb;
		this.query = query;
	}

	@Override
	public ResultSet executeQuery() throws SQLException {
		//int columnIndexes[] = parse(query);
		

		String regex = " ";
		String arr[] = query.split(regex);
		
		if(!"SELECT".equalsIgnoreCase(arr[0])){
			throw new SQLException("Query should be started with 'SELECT'");
		}
		
		if(!"FROM".equalsIgnoreCase(arr[arr.length-2])){
			throw new SQLException("Query should include 'FROM'");
		}
		
		int sheetIndex = Integer.parseInt(arr[arr.length-1]);
		
		arr = arr[1].split(",");
		int columnIndexes[] = new int[arr.length];
		
		for( int i = 0 ; i < columnIndexes.length ; i++){
			try{
				columnIndexes[i] = Integer.parseInt(arr[i]);//+1].replace(",","").trim());
			}catch(NumberFormatException e){
				throw new SQLException(e);
			}
		}

		ResultSet rset = null;
		Sheet sheet = null;
		try{
			sheet = wb.getSheetAt(sheetIndex);
			//rset = new ResultSet(sheet, columnIndexes);
			rset = new ResultSet4Bin(sheet);
		}catch(Exception e){
			throw new SQLException(e);
		}
		return rset;
		
	}
	
	
}
