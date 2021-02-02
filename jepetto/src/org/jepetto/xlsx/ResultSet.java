package org.jepetto.xlsx;

import java.sql.SQLException;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.jepetto.adapter.data.ResultSetAdapter;

/**
 * used for resultset for excel file 
 * @author mymac
 *
 */
public class ResultSet extends ResultSetAdapter {

	/**
	 * current row of cursor
	 */
	private Row row;
	
	/**
	 * iterator for row of result set 
	 */
	private Iterator<Row> rit;
	
	/**
	 * result for sheet 
	 * @param sheet sheet of excel
	 */
	public ResultSet(Sheet sheet) {
		rit = sheet.rowIterator();
	}
	
	/**
	 * exist row or not at current cursor,
	 * direction of easy flow
	 * @return flag return true when sheet having row at current cursor
	 */
	@Override
	public boolean next() throws SQLException {
		boolean flag = true;
		try{
			row = rit.next();
		}catch(Exception e){
			flag = false;
		}
		return flag;
	}

	@Override
	public String getString(int index) throws SQLException {
		index--;
		Cell cell = null;
		String value = null;		
		try{
			cell = row.getCell(index);
			value = cell.getRichStringCellValue().getString();
		}catch(Exception e){
			throw new SQLException(e);
		}
		return value;
	}

	@Override
	public java.sql.Date getDate(int index) throws SQLException {
		index--;
		Cell cell = null;
		java.sql.Date date = null;		
		try{
			cell = row.getCell(index);
			java.util.Date value = cell.getDateCellValue();
			long l = value.getTime();
			date = new java.sql.Date(l); 
		}catch(Exception e){
			throw new SQLException(e);
		}		
		return date;
	}

	@Override
	public double getDouble(int index) throws SQLException {
		index--;
		Cell cell = null;
		double value = -1;
		try{
			cell = row.getCell(index);			
			value = cell.getNumericCellValue();
		}catch(Exception e){
			throw new SQLException(e);
		}	
		return value;
	}

}
