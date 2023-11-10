package org.jepetto.xlsx;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.jepetto.adapter.data.ResultSetAdapter;
import org.jepetto.util.Util;

/**
 * used for resultset for excel file 
 * @author mymac
 *
 */
public class ResultSet4Bin extends ResultSetAdapter {

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
	public ResultSet4Bin(Sheet sheet) {
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

	@Override
	public int getInt(int index) throws SQLException {
		index--;
		Cell cell = null;
		int value = -0;
		try{
			cell = row.getCell(index);			
			value = (int)cell.getNumericCellValue();
		}catch(Exception e){
			throw new SQLException(e);
		}	
		return value;
	}	

	@Override
	public boolean getBoolean(int index) throws SQLException {
		index--;
		Cell cell = null;
		boolean value = false;
		try{
			cell = row.getCell(index);			
			//value = (int)cell.getNumericCellValue();
			value = cell.getBooleanCellValue();
		}catch(Exception e){
			throw new SQLException(e);
		}	
		return value;
	}		
	
	public static void main(String args[]) {
		Connection con			= null;
		PreparedStatement pStmt = null;
		ResultSet rset			= null;
		List<String> dates = new ArrayList<String>();
		List<String> models = new ArrayList<String>();
		List<String> titles = new ArrayList<String>();
		String date		= null;
		String model	= null;
		String title	= null;		
		try {
			con = DriverManager.getConnection("c://temp", "orders.xlsx");
			//con = DriverManager.getConnection("c://temp", "abc.csv");
			//con = DriverManager.getConnection("c://temp", "NIADic.csv");
			pStmt = con.prepareStatement("select 1, 2, 3 from 0");
			rset = pStmt.executeQuery();
			rset.next();

			while(rset.next()) {
				/*
				
				System.out.println(title);
				
				String charset[] = {"CP949","x-windows-949","MS949","utf-8", "euc-kr", "ksc5601", "iso-8859-1", "8859_1", "ascii"};
				  
				for (String before : charset){
				    for (String after : charset){
				        if (!before.equals(after)){
				            try {
								System.out.println(before + " -> " + after + " = " + new String(title.getBytes(before), after));
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				        }
				    }
				}	
				//*/			

				title = rset.getString(1).trim();
				try {
					date = rset.getString(2);
					if(date.equals("1900-01-00")) {
						date = " ";
					}
				}catch(Exception e) {
					date=" ";
				}
				try {
					model = rset.getString(3);
				}catch(Exception e) {
					model=" ";
				}
				titles.add(title);
				dates.add(date);
				models.add(model);
			
				System.out.println(title+"/"+ date+"/"+model);
				/*
				title = Util.utf8(rset.getString(1));
				date = Util.utf8(rset.getString(2));
				try {
					model = Util.utf8(rset.getString(3));
				}catch(Exception e) {
					
				}
				System.out.println(title+"/"+ date+"/"+model);
				//*/
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String connectionUrl = "jdbc:sqlserver://52.231.158.63:1433;databaseName=db_figurestory;encrypt=true;trustServer;Certificate=true;user=admin;password=storyaws1!";
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//String query = "update t_good set subtitledate = ? where ";
		
		Connection uCon = null;
		PreparedStatement rStmt = null;
		PreparedStatement uStmt = null;
		PreparedStatement uStmt1 = null;
		Connection rCon = null;
		Connection uCon1 = null;
		try {
			
			rCon = java.sql.DriverManager.getConnection("jdbc:sqlserver://52.231.158.63:1433;databaseName=db_figurestory;encrypt=true;trustServerCertificate=true","figure1","figure1");
			uCon = java.sql.DriverManager.getConnection("jdbc:sqlserver://52.231.158.63:1433;databaseName=db_figurestory;encrypt=true;trustServerCertificate=true","figure1","figure1");
			uCon1 = java.sql.DriverManager.getConnection("jdbc:sqlserver://52.231.158.63:1433;databaseName=db_figurestory;encrypt=true;trustServerCertificate=true","figure1","figure1");
			rStmt = rCon.prepareStatement("select uid, title from t_goods where 1=1 and title = ?");
			
			uStmt = uCon.prepareStatement("update t_goods_sub set subtitledate = ?  where uid = ?");
			uStmt1 = uCon1.prepareStatement("update t_goods set Model_Name = ? where uid = ?");
			ResultSet _rset = null;
			uCon.setAutoCommit(false);
			uCon1.setAutoCommit(false);
			rCon.setAutoCommit(false);
			
			int updatedCnt = 0;
			int updatedCnt1 = 0;
			
			int size = titles.size();
			System.out.println(size);
			String uid = null;
			for(int i = 0 ; i < size ; i++) {
				rStmt.setString(1, titles.get(i).toString());
				_rset = rStmt.executeQuery();
				
				//System.out.println(i + " : " + _rset.next() + " " + titles.get(i));
				updatedCnt = 0;
				
				if(_rset.next()) {
					
					//System.out.println(_rset.getString(1));
					title = titles.get(i).toString();
					date = dates.get(i).toString();
					
					model = models.get(i).toString();
					
					uStmt.setString(1, date );
					
					
					//uStmt.setString(2, title);
					
					uid = _rset.getString(1);
					uStmt.setString(2,uid);
					
					updatedCnt = uStmt.executeUpdate();
					
					
					
					uStmt1.setString(1, model);
					uStmt1.setString(2, uid);
					updatedCnt += uStmt1.executeUpdate();
					
					System.out.println("index " + i + " updated : "+ updatedCnt + " uid: " + uid + " title:" + title + " date : " + date + " model : " + model );		
					
				}else {
					//System.out.println(title);
				}
				
			}
			//rCon.rollback();
			//uCon.rollback();
			//uCon1.rollback();
			
			rCon.commit();
			uCon.commit();
			uCon1.commit();
			
			

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rCon.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
			try {
				uCon.close();
			}catch(Exception e) {
				e.printStackTrace();
			}	
			try {
				uCon1.close();
			}catch(Exception e) {
				e.printStackTrace();
			}	
			try {
				
				rStmt.close();
			}catch(Exception e) {
				e.printStackTrace();
			}			
			try {
				
				uStmt.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
			try {
				
				uStmt1.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}//*/
		System.out.println("end of main");
		
	}

}
