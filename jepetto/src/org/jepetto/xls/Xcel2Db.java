package org.jepetto.xls;
//import jepetto.xjdbc.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
//import jepetto.jutil.utility.GenUtil;
import java.io.File;
import java.sql.SQLException;
import javax.naming.NamingException;
//import jepetto.common.logging.DailyLog;

/**
 * 
 * '\t' 援щ텇�옄瑜� 媛뽮퀬 �엳�뒗 csv �뙆�씪�쓣 db�뿉 ���옣�븷 �닔 �엳�뒗 湲곕뒫�쓣 �젣怨�
 * 癒쇱� jepetto.ms.XcelReader.tranXls2Txt 硫붿냼�뱶瑜� �씠�슜�빐�꽌 xls�쓣 csv濡� 蹂��솚�쓣 �븳�떎.
 * 
 * @author 源�李쏀샇
 *
 */

public class Xcel2Db {


	private String del = "\t";
	
	
	/**
	 * 二쇱뼱吏� 寃쎈줈�뿉 �엳�뒗 csv �뙆�씪�쓣 二쇱뼱吏� query �뵲�씪 泥섎━�븳�떎.
	 * @param path
	 * @param file
	 * @param query
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 * @throws NamingException
	 */
	public int transTxt2Db(String path, String file, String query)	throws IOException, SQLException,NamingException{
	 //NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException{

		BufferedReader reader = null;
		String buffer = "";
		String arr[] = null;
		int count = 0;

		try{
			reader = new BufferedReader(new FileReader( new File(path,file) ) );
			
			buffer = reader.readLine();
			while( buffer != null ){
					for( int i = 0 ; i < arr.length ; i++){
						if( arr[i].equals(".")){
							arr[i]= " ";
						}
					}

				buffer = reader.readLine();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return count;
		
	}
	
	
	
		
	
	/*
	public int transTxt2Db(String path, String file, String query)	throws IOException, SQLException, NamingException,
	 NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException{

		BufferedReader reader = new BufferedReader(new FileReader( new File(path,file) ) );
		String buffer = "";
		String arr[] = null;
		int count = 0;

			wrapper.setPreparedStatement(query);
			
			buffer = reader.readLine();
			while( buffer != null ){
				try{
				
				arr = GenUtil.getSplitedStringArr(buffer,del);
				
					for( int i = 0 ; i < arr.length ; i++){
						if( arr[i].equals(".")){
							arr[i]= " ";
						}
						wrapper.setString(i+1,arr[i]);
					}
				count = wrapper.executeUpdate();
				
				
				}catch(SQLException e){
					log.fatal(buffer);
				}
				buffer = reader.readLine();
			}
			wrapper.close();
		return count;
	}//*/
	
	
	
}