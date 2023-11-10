package org.jepetto.sql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jdom2.Document;
import org.jdom2.input.DOMBuilder;
import org.jepetto.logger.DisneyLogger;
import org.jepetto.proxy.HomeProxy;
import org.jepetto.util.Util;
import org.json.simple.JSONObject;
import org.xml.sax.SAXException;


public class XmlTransfer {

	DisneyLogger cat = new DisneyLogger(HomeProxy.class.getName());
	
	public JSONObject trasnferDom2JSon(Document doc) throws SQLException{
		JSONObject json = new JSONObject();
		Connection con = new XmlConnection(doc);
		PreparedStatement stmt = con.prepareStatement("//recordset/row");
		ResultSet rset = stmt.executeQuery();
		ResultSetMetaData meta = null;
		String columnName = null;
		if(rset.next()) {
	  		meta = rset.getMetaData();
	  		int count = meta.getColumnCount();
	  		for( int i = 0 ; i < count ; i++){
	  			columnName = meta.getColumnLabel(i+1);
	  			json.put(columnName.toLowerCase(), rset.getString(columnName.toUpperCase())); 
	  		}
			
		}
		
		return json;
	}
	
	/*
	public JSONObject trasnferRset2JSon(ResultSet rset) throws SQLException{
		JSONObject json = new JSONObject();
		String columnName = null;
		ResultSetMetaData meta = null;
		if(rset.next()) {
			json.put(rset, json);
	  		meta = rset.getMetaData();
	  		int count = meta.getColumnCount();
	  		for( int i = 0 ; i < count ; i++){
	  			columnName = meta.getColumnLabel(i+1);
	  			json.put(columnName.toLowerCase(), rset.getString(i)); 
	  		}
			
		}
		return json;
		
	}//*/
	
	public Document trasnferRset2Dom(ResultSet rset) throws SQLException{

  		XmlFacade x = new XmlFacade();
  		try{
	  		java.sql.ResultSetMetaData meta = rset.getMetaData();
	  		int count = meta.getColumnCount();
	
	  		// initiate meta data
	  		x.createRootElement("recordset");
	  		x.createSubElement("meta");
	  		for( int i = 0 ; i < count ; i++){
	  			x.addChild("column",meta.getColumnLabel(i+1) ); 
	  			x.addChild("type",meta.getColumnTypeName(i+1) );
	  		}
	  		x.addChild("column_length", String.valueOf(count));
	  		
	  		// initiate raw data
	  		String key = null;
	  		String value = null;
	  		while( rset.next()  ){
	  			x.createSubElement("row");
	  			for( int i = 1 ; i < count+1 ; i++){
	  				
	  				key = meta.getColumnLabel(i);
					value = rset.getString(i);
					
					if(value!=null && value.length()>19 && meta.getColumnType(i) == Types.TIMESTAMP) { 
						value = value.substring(0, 19);
					}
	  				
	  				x.addChild(key,value);
	  			}
	  		}

  		}catch(SQLException e){
  			e.printStackTrace();
  		}finally{
  			rset.close();
  		}
  		return x.getDocument();
		
	}

	public Document trasnferRset2Dom(Wrapper wrapper, int columnIndex[]) throws SQLException{

  		XmlFacade x = new XmlFacade();
  		try{
	  		java.sql.ResultSetMetaData meta = wrapper.getMetaData();
	  		int count = meta.getColumnCount();
	
	  		// initiate meta data
	  		x.createRootElement("recordset");
	  		x.createSubElement("meta");
	  		for( int i = 0 ; i < count ; i++){
	  			x.addChild("column",meta.getColumnName(i+1) );
	  			x.addChild("type",meta.getColumnTypeName(i+1) );
	  		}
	  		x.addChild("column_length", String.valueOf(count));
	  		
	  		// initiate raw data
	  		int k = 0;
	  		String s = null;
	  		while( wrapper.next()  ){
	  			x.createSubElement("row");
	  			for( int i = 1 ; i < count+1 ; i++){
	  				try{
	  					x.addChild(meta.getColumnName(i),wrapper.getString(i));
	  					
	  	
	  					
	  					
	  				}catch(SQLException e){
	  					s = wrapper.getClob(columnIndex[k++]);
	  					x.addChild(meta.getColumnName(i),s);
	  				}
	  			}
	  		}

  		}catch(SQLException e){
  			e.printStackTrace();
  		}finally{
  			wrapper.close();
  		}

  		return x.getDocument();
		
	}
	
	public String trasnferRset2Xml(ResultSet rset) throws SQLException{

  		StringBuffer buffer = new StringBuffer();
  		
  		try{
  			
	  		java.sql.ResultSetMetaData meta = rset.getMetaData();
	  		int count = meta.getColumnCount();
	  		
	  		String columnNames[] = new String[count];
	  		
	  		// initiate meta data
			buffer.append("<?xml version='1.0' encoding='utf-8'?>");
			buffer.append("\n");
			buffer.append("<recordset>");
			buffer.append("\n");
			buffer.append("<meta>");
			buffer.append("\n");
			
	  		for( int i = 0 ; i < count ; i++){
	  			
	  			buffer.append("<column type='");
	  			buffer.append(meta.getColumnTypeName(i+1));
	  			buffer.append("'>");
	  			buffer.append(meta.getColumnName(i+1));
	  			buffer.append("</column>");
	  			buffer.append("\n");
	  			columnNames[i] = meta.getColumnName(i+1);
	  		}
			buffer.append("</meta>");
			buffer.append("\n");

	  		
	  		
	  		// initiate raw data 
	  		while( rset.next()  ){
				buffer.append("<row>");	  			
	  			for( int i = 1 ; i < count+1 ; i++){
		  			buffer.append("<");
		  			buffer.append(columnNames[i-1]);
		  			buffer.append(">");
		  			buffer.append("<![CDATA[");
		  			buffer.append(rset.getString(i));
		  			
		  			buffer.append("]]>");
		  			buffer.append("</");
		  			buffer.append(columnNames[i-1]);
		  			buffer.append(">");
		  			buffer.append("\n");
	  			}
	  			buffer.append("</row>");
	  			buffer.append("\n");
	  		}
	  		buffer.append("</recordset>");
  		}catch(SQLException e){
  			e.printStackTrace();
  		}finally{
  			rset.close();
  		}

  		return buffer.toString();
		
	}
	
	
	public String transferDom2StringUTF8(Document doc) throws SQLException{
		Connection con = new XmlConnection(doc);
		PreparedStatement stmt = con.prepareStatement("//recordset/row");
		PreparedStatement _stmt = con.prepareStatement("//recordset/meta");
		
		ResultSet rset = stmt.executeQuery();
		ResultSet _rset = _stmt.executeQuery();
		_rset.next();
		_stmt = con.prepareStatement("/recordset/meta/column");
		_rset = _stmt.executeQuery();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version='1.0' encoding='utf-8'?>");

		buffer.append("<recordset>");

		while( rset.next() ){
			buffer.append("<row>");
			for(int i = 0 ;_rset.next() ; i++){
				buffer.append("<");
				buffer.append(_rset.getString(i+1));
				buffer.append(">");
				buffer.append("<![CDATA[");
				try{
					//buffer.append(Util.utf8(rset.getString( _rset.getString(i+1)) )); 
					buffer.append(rset.getString( _rset.getString(i+1)));
				}catch(NullPointerException e){
					cat.debug(e.getMessage());
				}
				buffer.append("]]>");
				buffer.append("</");
				buffer.append(_rset.getString(i+1));
				buffer.append(">");			
			}
			buffer.append("</row>");
		}
		buffer.append("</recordset>");

		
		
		return buffer.toString().trim();
	}
	
	
	public String transferDom2String(Document doc) throws SQLException{
		Connection con = new XmlConnection(doc);
		PreparedStatement stmt = con.prepareStatement("//recordset/row");
		PreparedStatement _stmt = con.prepareStatement("//recordset/meta");
		
		ResultSet rset = stmt.executeQuery();
		ResultSet _rset = _stmt.executeQuery();
		_rset.next();
		_stmt = con.prepareStatement("/recordset/meta/column");
		_rset = _stmt.executeQuery();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version='1.0' encoding='utf-8'?>");
		buffer.append("<recordset>");
		while( rset.next() ){
			buffer.append("<row>");
			for(int i = 0 ;_rset.next() ; i++){

				
				buffer.append("<");
				buffer.append(_rset.getString(i+1));
				buffer.append(">");
				buffer.append("<![CDATA[");
				try{
					buffer.append(Util.ko( rset.getString( _rset.getString(i+1) ) ) ); 
				}catch(NullPointerException e){
					cat.debug(e.getMessage());
				}
				buffer.append("]]>");
				buffer.append("</");
				buffer.append(_rset.getString(i+1));
				buffer.append(">");
			
			}
			buffer.append("</row>");
		}
		buffer.append("</recordset>");
		
		return buffer.toString().trim();
	}
	
	public static Document string2dom(String s){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		org.w3c.dom.Document doc = null;
		Document _doc = null;
		DOMBuilder domBuilder = null;
		try {
			builder = factory.newDocumentBuilder();
			
			doc = builder.parse(new java.io.ByteArrayInputStream(s.getBytes("UTF-8")));
			
			domBuilder = new DOMBuilder(); 
			_doc = domBuilder.build(doc);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return _doc;
		
	}
	
	public static void main(String args[]){
		
/*		String s = "<?xml version='1.0' encoding='utf-8'?><RESULT><RESULTCODE>V0001</RESULTCODE><DOCID>C325CA76-BCAB-4794-8589-B127084A6169</DOCID><DRAFTURL/><RESULTMESSAGE><![CDATA[SecurityCode 占쏙옙 占쏙옙효占쏙옙占쏙옙 占쏙옙占쏙옙]]></RESULTMESSAGE></RESULT>";
		
		s= "<?xml version='1.0' encoding='utf-8' ?><string xmlns='http://tempuri.org/'><RESULT><RESULTCODE>V0001</RESULTCODE><DOCID>C325CA76-BCAB-4794-8589-B127084A6169</DOCID><DRAFTURL/><RESULTMESSAGE><![CDATA[SecurityCode 占쏙옙 占쏙옙효占쏙옙占쏙옙 占쏙옙占쏙옙]]></RESULTMESSAGE></RESULT></string>";

		s = "<?xml version='1.0' encoding='utf-8'?><RESULT><RESULTCODE>V0001</RESULTCODE><DOCID>C325CA76-BCAB-4794-8589-B127084A6169</DOCID><DRAFTURL/><RESULTMESSAGE><![CDATA[SecurityCode 占쏙옙 占쏙옙효占쏙옙占쏙옙 占쏙옙占쏙옙]]></RESULTMESSAGE></RESULT>";
		
		
		s = "<?xml version='1.0' encoding='utf-8'?><RESULT><RESULTCODE>V0001</RESULTCODE><DOCID>C325CA76-BCAB-4794-8589-B127084A6169</DOCID><DRAFTURL/><RESULTMESSAGE><![CDATA[SecurityCode 占쏙옙 占쏙옙효占쏙옙占쏙옙 占쏙옙占쏙옙]]></RESULTMESSAGE></RESULT>";
		
		Document doc = string2dom(s);
		
		try {
			
			XmlStatement stmt = new XmlStatement(doc,"//RESULT");
			ResultSet rset = stmt.executeQuery();
			while(rset.next()){
				System.out.println(rset.getString("RESULTCODE"));
				System.out.println(rset.getString("RESULTMESSAGE"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} */
		
		/*
		Connection con			= null;
		ResultSet rset			= null;
		java.sql.PreparedStatement pStmt	= null;
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con =java.sql.DriverManager.getConnection("jdbc:oracle:thin:@172.28.2.214:1523:OTMQA","glogowner","glogowner");
			//con =java.sql.DriverManager.getConnection(url,"glogowner","glogowner");
			pStmt = con.prepareStatement("select * from shipment where rownum < 10");
			rset = pStmt.executeQuery();
			XmlTransfer x = new XmlTransfer();
			
			Document doc = x.trasnferRset2Dom(rset);
			System.out.println(doc);
			XsltTransform.save(new java.io.FileOutputStream(new java.io.File("c:/","abc.xml")), doc);
			System.out.println("end of main");
		}catch(SQLException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try{
				con.close();
			}catch(Exception e){}
			try{
				rset.close();
			}catch(Exception e){}
			try{
				pStmt.close();
			}catch(Exception e){}
		}
		//*/
		
		System.out.println("end of main");
		
	}
	
}
