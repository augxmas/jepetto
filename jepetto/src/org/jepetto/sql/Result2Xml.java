package org.jepetto.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.io.OutputStream;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 
 * @(#) Result2Xml.java 
 *
 * <pre> 
 *
 * IResult2Xml의 구현 클래스
 * 결과 집합을 xml로 변환한다
 * schema, sheet는 정의하지 않았다
 *
 * </pre>
 * 
 * @author 김창호
 * @version 1.0 2006.02.10
 * @ see IResult2Xml.java , Result2Properties.java
 *
 */
public class Result2Xml extends IResult2Xml {
	
    /**
     * result의 컬럼명들
     */
	private String names[];
	
	/**
	 * resultset의 메타 데이타를 xml로 변환한다.
	 * @param meta resultset meta data
	 * @param fout xml 문서를 생성할 outputstream
	 * @return
	 */
	protected  void convert2xml(ResultSetMetaData meta, OutputStream fout) throws SQLException,IOException{
	    
		int size = meta.getColumnCount();
		write(fout,"<rsetsetmeta>");
		names = new String[size];
		String name = null;
		for( int i = 0 ; i < size ; i++){
			name = meta.getColumnName(i+1);
			names[i] = name;
			write( fout, "<column name=" + quote(name).trim() + " type=" + quote(meta.getColumnType(i+1)) +"/> ");
		}
		write(fout,"</rsetsetmeta>");//*/
	}
	
	/**
	 * 
	 * resultset의 raw 데이타를 xml로 변환한
	 * 
	 * @param rset result
	 * @param fout xml 문서를 생성할 outputstream
	 * return
	 */
	protected  void convert2xml(ResultSet rset, OutputStream fout  ) throws SQLException,IOException{
	    
	    for( int i = 0 ; rset.next() ; i++){
	        write(fout,"<row num=" + quote(String.valueOf(i))+ ">");
	        for( int j = 0 ; j < names.length ; j++ ){
	            write(fout,"<" + names[j] +">" + rset.getObject(j+1) +"</"+names[j]+">");
	        }
	        write(fout,"</row>");
	    }
		
	}

    protected void setStyleSheet(OutputStream fout,String url, String sheet) throws IOException {
        write(fout, "<?xml-stylesheet type='text/xsl' href=" + quote( url + sheet+".xsl" ) + "?>");
    }

    protected void setSchema(OutputStream fout,String url, String schema ) throws IOException {
        //write(fout, "<?xml-stylesheet type='text/xsl' href=" + quote( path + schema ) + "?>");        
    }
	

}
