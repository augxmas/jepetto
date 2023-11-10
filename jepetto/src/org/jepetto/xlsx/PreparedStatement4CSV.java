package org.jepetto.xlsx;

import java.io.BufferedReader;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.poi.ss.usermodel.Sheet;
import org.jepetto.adapter.data.PreparedStatementAdapter;

public class PreparedStatement4CSV extends PreparedStatementAdapter {
	
	String query;
	BufferedReader reader;
	public PreparedStatement4CSV(BufferedReader reader,String query) {
		this.reader = reader;
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
		
		try{
			rset = new ResultSet4CSV(reader);
		}catch(Exception e){
			throw new SQLException(e);
		}
		return rset;
		
	}	
	
}
