package org.jepetto.sql;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.NamingException;

import org.jdom2.JDOMException;
import org.jepetto.util.MapComparator;



public class AbstractDAO {

	public AbstractDAO(){}
	
	// 수정 : 김방영 2016.06.30
	// xmlquery에서 가져온 element mapping query를 preparedstatement로 사용하기 위한 query문 , String[] 수정 작업
	
	public class QueryFarm {
		
		private String query;
		private Map input;
		private Map<String, String> retMap = new HashMap<String, String>();
		private String[] arr;
		private String[] arr2;
		
		public String[] getParams() {
			return arr2;
		}
		
		public String getQuery() {
			return query;
		}
		
		
		public QueryFarm(String sql, Map map, String[] arr){
			//this.query = sql;
			this.input = map;
			this.arr = arr;

			List<Map<String, String>> list = new ArrayList<Map<String, String>>();

			int lastIndx = sql.indexOf("?");
			
			int arrIndx = 0;
			while(lastIndx!=-1) {
				Map<String, String> p = new HashMap<String, String>();
				p.put("POSITION", new Integer(lastIndx).toString());
				p.put("VALUE", arr[arrIndx]);
				
				list.add(p);
				
				lastIndx = sql.indexOf("?",lastIndx+1);
				arrIndx++;
			}
			
			Iterator it = input.keySet().iterator();
			
			while(it.hasNext()) {
				String key = (String)it.next();
				String value = (String)input.get(key);
				
				int indx = sql.indexOf("#{"+key+"}");
				if(indx!=-1) {
					Map<String, String> p = new HashMap<String, String>();
					p.put("POSITION", new Integer(indx).toString());
					p.put("VALUE", value);
					
					list.add(p);
				}
			}
			
			//MapComparator comp = new MapComparator("POSITION");
			Comparator comp = new MapComparator("POSITION");
			
			Collections.sort(list, comp);
			
			arr2 = new String[list.size()];
			
			
			String log = "";
			for(int i=0;i<list.size();i++) {
				arr2[i] = list.get(i).get("VALUE");
				
				log += list.get(i).get("POSITION") + " : "+list.get(i).get("VALUE") +"\r\n";
			}
			
			it = input.keySet().iterator();
			
			while(it.hasNext()) {
				String key = (String)it.next();
				
				sql = sql.replaceAll(Pattern.quote("#{"+key+"}"), 
			               Matcher.quoteReplacement("?"));
			}
			
			this.query = sql;
		}
		
	}

	
	public 	ResultSet executeQuery(Wrapper wrapper, String file ,String key, Map table, String arr[]) throws   SQLException, NamingException, JDOMException, IOException{
		ResultSet rset = null;
		QueryReader matcher = new QueryReader(file,key);
		
		Map<String, String> retable = new HashMap<String, String>();
		
		String query = matcher.getQuery(table, retable); // 쿼리 전체를 PreparedStatement로 변경 2016.06.30
		
		QueryFarm qfarm = new QueryFarm(query, retable, arr);
		
		//save_sql(key, matcher.getQuery(), matcher.getQuery(table), query, qfarm.getQuery());
		
		query = qfarm.getQuery();
		arr = qfarm.getParams();
		

			
		wrapper.setPreparedStatement( query );
		for( int i = 0 ; arr != null && i < arr.length ; i++){
			wrapper.setObject(i+1,arr[i]);	
		}				
		wrapper.executeQuery();
		rset = wrapper.getResultSet();		
		return rset;
			
	}
	

	public 	ResultSet executeQuery(Wrapper wrapper, String file ,String key, Map table, String arr[], int clobColumnIndex[]) throws   SQLException, NamingException, JDOMException, IOException{
		
		ResultSet rset = null;
		QueryReader matcher = new QueryReader(file,key);
		
		Map<String, String> retable = new HashMap<String, String>();
		
		String query = matcher.getQuery(table, retable); // 쿼리 전체를 PreparedStatement로 변경 2016.06.30
		
		QueryFarm qfarm = new QueryFarm(query, retable, arr);
		
		query = qfarm.getQuery();
		arr = qfarm.getParams();
		
		wrapper.setPreparedStatement( query );
		for( int i = 0 ; arr != null && i < arr.length ; i++){
			wrapper.setObject(i+1,arr[i]);	
		}				
		wrapper.executeQuery();
		rset = wrapper.getResultSet();			
		return rset;
		
	}
	
	/*
	public 	ResultSet executeQueryJ(Wrapper wrapper, String file ,String key, Map table, String arr[]) throws   SQLException, NamingException, JDOMException, IOException{
		ResultSet rset = null;
		QueryReader matcher = new QueryReader(file,key);
		
		Map<String, String> retable = new HashMap<String, String>();
		
		String query = matcher.getQuery(table, retable); // 쿼리 전체를 PreparedStatement로 변경 2016.06.30
		
		QueryFarm qfarm = new QueryFarm(query, retable, arr);
		
		//save_sql(key, matcher.getQuery(), matcher.getQuery(table), query, qfarm.getQuery());
		
		query = qfarm.getQuery();
		arr = qfarm.getParams();
		

			
		wrapper.setPreparedStatement( query );
		for( int i = 0 ; arr != null && i < arr.length ; i++){
			wrapper.setObject(i+1,arr[i]);	
		}				
		wrapper.executeQuery();
		rset = wrapper.getResultSet();		
		return rset;
			
	}
	

	public 	ResultSet executeQueryJ(Wrapper wrapper, String file ,String key, Map table, String arr[], int clobColumnIndex[]) throws   SQLException, NamingException, JDOMException, IOException{
		
		ResultSet rset = null;
		QueryReader matcher = new QueryReader(file,key);
		
		Map<String, String> retable = new HashMap<String, String>();
		
		String query = matcher.getQuery(table, retable); // 쿼리 전체를 PreparedStatement로 변경 2016.06.30
		
		QueryFarm qfarm = new QueryFarm(query, retable, arr);
		
		query = qfarm.getQuery();
		arr = qfarm.getParams();
		
		wrapper.setPreparedStatement( query );
		for( int i = 0 ; arr != null && i < arr.length ; i++){
			wrapper.setObject(i+1,arr[i]);	
		}				
		wrapper.executeQuery();
		rset = wrapper.getResultSet();			
		return rset;
		
	}
	//*/

	public int executeUpdate( Wrapper wrapper, String file, String key, String arr[] ) throws  SQLException, NamingException, JDOMException, IOException{
		
		int updateCount = -1;	
		QueryReader matcher = new QueryReader(file,key);
		
		String query = matcher.getQuery();
		
		wrapper.setPreparedStatement( query );			
		for( int i = 0 ; arr != null && i < arr.length ; i++){
			wrapper.setObject(i+1, arr[i]);
		}			
		updateCount = wrapper.executeUpdate();
		return updateCount;
	}
	
	public int executeUpdate( Wrapper wrapper, String files[], String keys[], String arr[][], int clobColumnIndex[],String clobValues[] ) throws  SQLException, NamingException, JDOMException, IOException{
		
		int updateCount = -1;	
		QueryReader matcher = new QueryReader(files[0],keys[0]);;
		String query = matcher.getQuery();
		wrapper.setPreparedStatement(query);
		int i = 0;
		for( int j = 0 ; arr != null && j < arr.length ; j++){
			wrapper.setObject(i+1, arr[i][j]);
		}
		wrapper.executeUpdate();
		
		i++;
		matcher = new QueryReader(files[1],keys[1]);;
		query = matcher.getQuery();
		wrapper.setPreparedStatement(query);
		for( int j = 0 ; j < arr[i].length ; j++){
			wrapper.setObject(i+1, arr[i][j]);
		}
		wrapper.executeQuery();
		
		if(wrapper.next()){
			for(int j = 0 ; j < clobValues.length ; j++){
				wrapper.setClob(j+1, clobValues[j]);
			}
			//wrapper.executeUpdate();
		}

		return i;
	}
	
	public int executeUpdateX( Wrapper wrapper, String file, String key, String arr[][] ) throws  SQLException, NamingException, JDOMException, IOException {
		
		int updateCount = 0;		
		QueryReader matcher = new QueryReader(file,key);
		String query = matcher.getQuery();
		wrapper.setPreparedStatement( query );		
		
		// 20160902 executeUpdate를 executeBatch로 변경
		for( int i = 0 ; arr != null && i < arr.length ; i++){
			for( int j = 0 ; j < arr[i].length ; j++){
				wrapper.setObject(j+1, arr[i][j]);
			}
			wrapper.addBatch();
		}
		
		int[] res = wrapper.executeBatch();
		
		for(int r:res) 	updateCount += r;
		
		return updateCount;
	}
	
	public int executeUpdate(Wrapper wrapper , String file, String key, Map table, String arr[]) throws SQLException, NamingException, JDOMException, IOException{
	    
	    int updateCount = 0;	    
		QueryReader matcher = new QueryReader(file,key);	
		
		Map<String, String> retable = new HashMap<String, String>();
		
		String query = matcher.getQuery(table, retable); // 쿼리 전체를 PreparedStatement로 변경 2016.06.30
		
		QueryFarm qfarm = new QueryFarm(query, retable, arr);
		
		query = qfarm.getQuery();
		arr = qfarm.getParams();
		
		wrapper.setPreparedStatement( query );		
		for( int i = 0 ; arr != null && i < arr.length ; i++){
		    wrapper.setObject(i+1, arr[i]);
		}		
		updateCount = wrapper.executeUpdate();	    
	    return updateCount;
	}

	public int executeUpdateX(Wrapper wrapper , String file, String key, Map table, String arr[][]) throws SQLException, NamingException, JDOMException, IOException{
	    
	    int updateCount = 0;	    
		QueryReader matcher = new QueryReader(file,key);	
		
		Map<String, String> retable = new HashMap<String, String>();
		
		String query = matcher.getQuery(table, retable); // 쿼리 전체를 PreparedStatement로 변경 2016.06.30
		
		wrapper.setPreparedStatement( query );		
		
		// 20160902 executeUpdate를 executeBatch로 변경
		for( int i = 0 ; arr != null &&i < arr.length ; i++){
			
			QueryFarm qfarm = new QueryFarm(query, retable, arr[i]);
			
			query = qfarm.getQuery();
			arr[i] = qfarm.getParams();
			
		    for( int j = 0 ; j < arr[i].length ; j++){
		        wrapper.setObject(j+1, arr[i][j]);
		    }
		    wrapper.addBatch();
		}
		
		int[] res = wrapper.executeBatch();
		for(int r:res) 	updateCount += r;
	    return updateCount;
	}
	
	public String execute( Wrapper wrapper, String file, String key, String arr[] ) throws SQLException, NamingException, JDOMException, IOException {
		
		String out = null;		
		
		QueryReader matcher = new QueryReader(file,key);
		String query = matcher.getQuery();		
		wrapper.setCallableStatement( query );	
		
		for( int i = 0 ; arr != null && i < arr.length-1  ; i++){
			wrapper.setCObject(i+1, arr[i]);
		}
		
		int index = arr.length; 		
		wrapper.registOutParameterTypeVarChar( index );			
		wrapper.execute();
		out = wrapper.getCString(index);//*/
					
		return out;

	}//*/
	
	public void rollback(Wrapper wrapper) throws SQLException{
		wrapper.rollback();
	}
	
	public void commit(Wrapper wrapper) throws SQLException{
		wrapper.commit();
	}
	
	public void close(Wrapper wrapper) throws SQLException{
		wrapper.close();
	}

	public int executeUpdate(Wrapper wrapper, String files[], String keys[],
			String[][] arr, int blobColumnIndex, File blobValue) throws SQLException, JDOMException, IOException, NamingException {
		int updateCount = -1;	
		QueryReader matcher;
		
			//insert
			matcher = new QueryReader(files[0],keys[0]);
			String query = matcher.getQuery();
			wrapper.setPreparedStatement(query);
			int i = 0;
			for( int j = 0 ; arr != null && j < arr[i].length ; j++){
				wrapper.setObject(j+1, arr[i][j]);
			}
			wrapper.executeUpdate();
			
			i++;
			//select
			matcher = new QueryReader(files[1],keys[1]);
			query = matcher.getQuery();
			wrapper.setPreparedStatement(query);
			for( int j = 0 ; j < arr[i].length ; j++){
				wrapper.setObject(j+1, arr[i][j]);
			}
			wrapper.executeQuery();
			
			if(wrapper.next()){
					wrapper.setBlob(blobColumnIndex, blobValue);
					
					
			}
			
		return i;
	}

	public int executeUpdateX(Wrapper wrapper, String[] files, String[] keys,
			String[][] arr, int[] blobColumnsIndex, File[] blobValues) throws JDOMException, IOException, SQLException, NamingException {
			int updateCount = -1;	
			QueryReader matcher;
		
			//insert
			matcher = new QueryReader(files[0],keys[0]);
			String query = matcher.getQuery();
			wrapper.setPreparedStatement(query);
			int i = 0;
			for( int j = 0 ;  j < arr[i].length ; j++){
				wrapper.setObject(j+1, arr[i][j]);
			}
			wrapper.executeUpdate();
			
			i++;
			//select
			matcher = new QueryReader(files[1],keys[1]);
			query = matcher.getQuery();
			wrapper.setPreparedStatement(query);
			for( int j = 0 ; j < arr[i].length ; j++){
				wrapper.setObject(j+1, arr[i][j]);
			}
			wrapper.executeQuery();
			
			if(wrapper.next()){
					int index = 0;
					wrapper.setBlob(blobColumnsIndex[index], blobValues[index++]);
					wrapper.setBlob(blobColumnsIndex[index], blobValues[index++]);
					wrapper.setBlob(blobColumnsIndex[index], blobValues[index++]);
			}
		return i;
	}

}
