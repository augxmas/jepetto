package org.jepetto.xlsx;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import org.jepetto.adapter.data.ResultSetAdapter;

public class ResultSet4CSV extends ResultSetAdapter {

	BufferedReader reader;
	String temp;
	static final String delim = ",";
	String arr[];
	
	public ResultSet4CSV(BufferedReader reader) {
		this.reader = reader;
	}

	@Override
	public boolean next() throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = true;
		//List <String>list = null;
		Vector v = new Vector();
		try {
			temp = reader.readLine();
			StringTokenizer stk = new StringTokenizer(temp, delim);
			while(stk.hasMoreTokens()) {
				v.add(stk.nextToken());
			}
			arr = new String[v.size()];
			v.copyInto(arr);
		} catch (IOException e) {
			flag = false;
			throw new SQLException(e.getMessage());
		} catch(NullPointerException e) {
			flag = false;
		}catch(Exception e) {
			e.printStackTrace();
			flag = false;
			throw new SQLException(e.getMessage());
		}
		return flag;
	}

	@Override
	public String getString(int columnIndex) throws SQLException {
		int index = columnIndex - 1;
		String value = arr[index];
		
		return value;
	}

	@Override
	public int getInt(int columnIndex) throws SQLException {
		int index = columnIndex - 1;
		String temp = arr[index];
		int value = Integer.parseInt(temp);
		return value;
	}

	@Override
	public float getFloat(int columnIndex) throws SQLException {
		int index = columnIndex - 1;
		String temp = arr[index];
		float value = Float.parseFloat(temp);
		return value;
	}

	@Override
	public Date getDate(int columnIndex) throws SQLException {
		int index = columnIndex - 1;
		String temp = arr[index];
		SimpleDateFormat format = new SimpleDateFormat(""); 
		Date value = null;
		return value;
	}
	
	
	
	
	
}
