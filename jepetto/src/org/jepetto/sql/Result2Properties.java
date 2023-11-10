
package org.jepetto.sql;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * @(#) Result2Properties.java 
 *
 * <pre>
 * IResult2Xml의 구현 클래스로서 Digitori에서 사용할 수 있는 
 * 사용자 configuration 파일을 생성할 때 사용된다.
 * 결과 집합을 xml로 변환한다
 * schema, sheet는 정의하지 않았다
 * </pre>
 * 
 * @author 김창호
 * @version 1.0 2006.02.10
 * @ see 
 * 
 */
public class Result2Properties extends IResult2Xml{
    
    /**
     * 
     */
    protected void convert2xml(ResultSetMetaData meta, OutputStream fout) throws SQLException, IOException {
        // XXX Auto-generated method stub
        
    }

    /**
     * resultset의 raw데이타를 xml로 변환한다.
     * &lt;?xml version='1.0' encoding='euc-kr'?&gt;
     * &lt;imageurl&gt;
     * &lt;row&gt;
     * &lt;host&gt;http://image.kyobobook.co.kr&lt;/host&gt;
     * &lt;largeimage&gt;/images/book/large/noimage_l.jpg&lt;/largeimage&gt;
     * &lt;largeurl&gt;/images/book/large/&lt;/largeurl&gt;
     * &lt;medimage&gt;/images/book/medium/noimage_m.jpg&lt;/medimage&gt;
     * &lt;medurl&gt;/images/book/medium/&lt;/medurl&gt;
     * &lt;smallimage&gt;/images/book/small/noimage_s.jpg&lt;/smallimage&gt;
     * &lt;smallurl&gt;/images/book/small/&lt;/smallurl&gt;
     * &lt;/row&gt;
     * &lt;/imageurl&gt;
     * @param rset resultset
     * @param fout xml outputstream
     */
    
    
    protected void convert2xml(ResultSet rset, OutputStream fout) throws SQLException, IOException {
		
        write(fout,"<row>");
		for(;rset.next();){
		    
		    write( fout , "<" + rset.getString(1) + ">" + rset.getString(2) + "</" + rset.getString(1)+">");
		}
		write(fout,"</row>");
        
    }

    /* (non-Javadoc)
     * @see com.uml.xml.IResult2Xml#setStyleSheet(java.io.OutputStream)
     */
    protected void setStyleSheet(OutputStream out, String url, String sheet) throws IOException {
        // XXX Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see com.uml.xml.IResult2Xml#setSchema(java.io.OutputStream)
     */
    protected void setSchema(OutputStream fout,String url, String schema ) throws IOException {
        // XXX Auto-generated method stub
        
    }

}
