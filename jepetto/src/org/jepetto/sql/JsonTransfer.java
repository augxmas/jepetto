package org.jepetto.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jdom2.Document;
import org.jepetto.logger.DisneyLogger;
import org.jepetto.proxy.HomeProxy;
import org.json.JSONArray;
import org.json.JSONObject;

/*
 *  JSON과 Map 형태로 출력
 * 
 *  추가 : 김방영 201512
 *  수정 : 김방영 201607 - keyName을 uppercase로 고정할 수 있음
 * */

public class JsonTransfer {
	
	DisneyLogger cat = new DisneyLogger(HomeProxy.class.getName());	
	
	
	
	@SuppressWarnings("resource")
	public JSONObject transferDom2JSON(Document doc, boolean keyUppercase) throws SQLException{
		Connection con = new XmlConnection(doc);
		PreparedStatement stmt = con.prepareStatement("//recordset/row");
		PreparedStatement _stmt = con.prepareStatement("//recordset/meta");
		
		ResultSet rset = stmt.executeQuery();
		ResultSet _rset = _stmt.executeQuery();
		_rset.next();
		_stmt = con.prepareStatement("/recordset/meta/column");
		_rset = _stmt.executeQuery();
		
		//ResultSet _rsetType = con.prepareStatement("/recordset/meta/type").executeQuery();
		
		JSONObject o = new JSONObject();
		o.put("result", "success");
		
		JSONArray arr = new JSONArray();
		
		while( rset.next() ){
			JSONObject _o = new JSONObject();
			for(int i = 0 ;_rset.next() ; i++){
				
				if(keyUppercase) _o.put(_rset.getString(i+1).toUpperCase(), rset.getString( _rset.getString(i+1) ));
				else _o.put(_rset.getString(i+1), rset.getString( _rset.getString(i+1) ));
			}
			
			arr.put(_o);
		}
		o.put("data", arr);
		return o;
	}
	
	
	public JSONObject transferDom2JSON(Document doc) throws SQLException{
		return transferDom2JSON(doc,false);
	}
	
	public String transferDom2StringUTF8(Document doc, boolean keyUppercase) throws SQLException{
		return transferDom2JSON(doc, keyUppercase).toString();
	}
	
	public String transferDom2StringUTF8(Document doc) throws SQLException{
		return transferDom2JSON(doc).toString();
	}
	
	
	@SuppressWarnings("resource")
	public List<Map<String, String>> transferDom2MapArrayUTF8(Document doc, boolean keyUppercase) throws SQLException{
		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		Connection con = new XmlConnection(doc);
		PreparedStatement stmt = con.prepareStatement("//recordset/row");
		PreparedStatement _stmt = con.prepareStatement("//recordset/meta");
		
		ResultSet rset = stmt.executeQuery();
		ResultSet _rset = _stmt.executeQuery();
		_rset.next();
		_stmt = con.prepareStatement("/recordset/meta/column");
		_rset = _stmt.executeQuery();
		
		//StringBuffer buffer = new StringBuffer();
		//buffer.append("{\"result\":\"success\",\"data\":");
		//buffer.append("[");
		while( rset.next() ){
			//buffer.append("{");
			
			Map<String, String> map = new HashMap<String, String>();
			
			for(int i = 0 ;_rset.next() ; i++){
				try{
					String name =  _rset.getString(i+1);
					String value = rset.getString( _rset.getString(i+1) );
					
					if(keyUppercase) name = name.toUpperCase();
					
					map.put(name, value);
				}catch(NullPointerException e){
					cat.debug(e.getMessage());
				}
			}
			list.add(map);
		}
		return list;
	}
	
	
	public List<Map<String, String>> transferDom2MapArrayUTF8(Document doc) throws SQLException{
		return transferDom2MapArrayUTF8(doc, false);
	}
	
	@SuppressWarnings("resource")
	public Map<String, Map<String, String>> transferDom2MapInMapUTF8(Document doc, String keyName, boolean keyUppercase) throws SQLException{
		Map<String, Map<String, String>> list = new HashMap<String, Map<String, String>>();
		
		Connection con = new XmlConnection(doc);
		PreparedStatement stmt = con.prepareStatement("//recordset/row");
		PreparedStatement _stmt = con.prepareStatement("//recordset/meta");
		
		ResultSet rset = stmt.executeQuery();
		ResultSet _rset = _stmt.executeQuery();
		_rset.next();
		_stmt = con.prepareStatement("/recordset/meta/column");
		_rset = _stmt.executeQuery();
		
		while( rset.next() ){
			String key = rset.getString( keyName );
			Map<String, String> map = new HashMap<String, String>();
			
			for(int i = 0 ;_rset.next() ; i++){
				try{
					String name =  _rset.getString(i+1);
					String value = rset.getString( _rset.getString(i+1) );
					
					map.put(name, value);
				}catch(NullPointerException e){
					cat.debug(e.getMessage());
				}
			}
			list.put(key, map);
		}
		return list;
	}
	
	
	public Map<String, Map<String, String>> transferDom2MapInMapUTF8(Document doc, String keyName) throws SQLException{
		return transferDom2MapInMapUTF8(doc, keyName, false);
	}
	
	
	@SuppressWarnings("resource")
	public Map<String, String> transferDom2MapUTF8(Document doc, boolean keyUppercase) throws SQLException{
		Map<String, String> map = null;
		
		Connection con = new XmlConnection(doc);
		PreparedStatement stmt = con.prepareStatement("//recordset/row");
		PreparedStatement _stmt = con.prepareStatement("//recordset/meta");
		
		ResultSet rset = stmt.executeQuery();
		ResultSet _rset = _stmt.executeQuery();
		_rset.next();
		_stmt = con.prepareStatement("/recordset/meta/column");
		_rset = _stmt.executeQuery();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("{\"result\":\"success\",\"data\":");
		buffer.append("[");
		if( rset.next() ){
			 map = new HashMap<String, String>();
			for(int i = 0 ;_rset.next() ; i++){
				try{
					String name =  _rset.getString(i+1);
					String value = rset.getString( _rset.getString(i+1) );
					
					if(keyUppercase) name = name.toUpperCase();
					
					map.put(name, value);
				}catch(NullPointerException e){
					cat.debug(e.getMessage());
				}
			}
		}
		return map;
	}
	
	public Map<String, String> transferDom2MapUTF8(Document doc) throws SQLException{
		return transferDom2MapUTF8(doc,false);
	}

}
