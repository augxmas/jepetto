package org.jepetto.xlsx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.jepetto.adapter.data.ConnectionAdapter;

public class Connection4CSV extends ConnectionAdapter{
	
	InputStream in = null;
	InputStreamReader isr = null;
	
	BufferedReader reader = null;
	public Connection4CSV(InputStream in) throws SQLException, UnsupportedEncodingException {
		this.in = in;
		isr = new InputStreamReader(in,"UTF-8");
		reader = new BufferedReader(isr);			
	}	
	
	public PreparedStatement prepareStatement(String query) throws SQLException {
		
		return new PreparedStatement4CSV(reader,query);
	}	
	
	@Override
	public void close() throws SQLException {
		try {
			in.close();
		} catch (IOException e) {
			throw new SQLException(e.getMessage());
		}
		try {
			isr.close();
		} catch (IOException e) {
			throw new SQLException(e.getMessage());
		}
		try {
			reader.close();
		} catch (IOException e) {
			throw new SQLException(e.getMessage());
		}	
	}	
	
	
}
