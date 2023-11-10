package org.jepetto.sql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import org.jdom2.Document;
import org.jdom2.input.DOMBuilder;
import java.sql.SQLException;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;

/**
 * 
 * @(#) IResult2Xml.java 
 *
 * <pre> 
 * 
 * java.sql.ResultSet을 xml 형식으로 변환할 수 있는 추상 레벨을 제공한다.
 * Template 패턴으로 정의되어 있으며 원하는 schema를 가진 xml 문서를 만들기 위해
 * setSchema(OutputStream out),
 * setStyleSheet(OutputStream out)
 * convert2xml(ResultSetMetaData meta, OutputStream fout) 
 * convert2xml(ResultSet rset, OutputStream fout), 이들 hook method를 하위 클래스에서 재정의하여 준다
 *  
 * &lt;?xml version='1.0' encoding='euc-kr'?&gt;
 * &lt;SCHEMA/&gt;
 * &lt;XSLSTYLESHEET/&gt;
 * &lt;ROOT_ELEMENT&gt;
 * 	&lt;RESULTMETA_DATA&gt;
 * 	&lt;RESULTMETA_INFO&gt;~&lt;/RESULTMETA_INFO&gt;
 *  &lt;/RESULTMETA_DATA&gt;
 *	&lt;ROW&gt;
 *		&lt;DATA&gt;~&lt;/DATA&gt;
 *	&lt;/ROW&gt;
 * 	&lt;ROW&gt;
 *		&lt;DATA&gt;~&lt;/DATA&gt;
 *	&lt;/ROW&gt;
 * &lt;/ROOT_ELEMENT&gt;
 * &lt;/xml&gt;
 * 
 * 
 * This class supports abstract layer that database resultset transfers xml data type.
 * As you implements this class , you can generate xml file applying your own schema
 * 
 * </pre>
 * 
 * @author 김창호
 * @version 1.0 2006.02.10
 * @ see Result2Xml.java , IResult2Properties.java
 *
 */
public abstract class IResult2Xml {

	/**
	 * specific resultset transfer xml file that is saved given path
	 * @param rset database resultset
	 * @param path saved path
	 * @param file saved xml file name
	 * @param root xml root element
	 * @return org.jdom.Document instance
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws SQLException
	 * @throws IOException
	 */
    public  Document  convert2xml(ResultSet rset, String url, String path, String file, String root) throws SAXException, ParserConfigurationException,SQLException,IOException{
		
		Document doc = null;
        OutputStream fout = null;

        try{
        	
			fout = getFileOutputStream(path,file);
            write(fout,"<?xml version='1.0' encoding='euc-kr'?>");
            setSchema( fout, url, file );
            setStyleSheet( fout , url , file );
            write(fout,"<" + root +">");
	        convert2xml(rset.getMetaData(),fout);
    	    convert2xml(rset,fout);//,root);
    	    write(fout,"</" + root +">");
			//doc = load( path,file );
			
        }catch(SQLException e){
			throw e;
        }catch(IOException e){
			throw e;
        }finally{
            try{
				fout.close();
            }catch(IOException e){}
           	try{
           		rset.close();
           	}catch(SQLException e){}

        }

        return doc;
    }
    
    /**
     * 
     * 주어진 resultset을 xml dom instance를 변화하여, 이를 ResultSet interface로
     * 참조할 수 있도록 변환하여 반환하는 메소드
     * 
     * @param rset
     * @return
     * @throws SQLException
     */
    public ResultSet convertRs2Rs(ResultSet rset) throws SQLException{
    	
		XmlFacade x = new XmlFacade();
    	//Document doc = null;
    	ResultSetMetaData meta = rset.getMetaData();
    	int count = meta.getColumnCount();
    	//String columnName = null;
		x.createRootElement("recordset");//,"row");
		
		while( rset.next()  ){
			x.createSubElement("row");
			for( int i = 1 ; i < count+1 ; i++){
				x.addChild(meta.getColumnName(i),rset.getString(i));
			}
		}
		
		Connection con = new XmlConnection(x.getDocument());
		PreparedStatement stmt = con.prepareStatement("//recordset/row");
            
		rset = stmt.executeQuery();
		
    	return rset;
    }
	
	/**
	 * 주어진 문자열에 싱글 쿼테이션을 첨가하여 반환 value -> 'value'
	 * @param s
	 * @return
	 */
    protected String quote(String s){
		return "'" + s +"'";
    }

	/**
	 * 주어진 정수 값에 싱글 쿼테이션을 첨가하여 반환 5 -> '5'
	 * @param s
	 * @return
	 */
    protected String quote(int type){
		return "'" + type +"'";
    }

	/**
	 * load given xml file
	 * @param path xml file path
	 * @param name xml file name
	 * @return org.jdom.Document
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 */
	public Document load(String path, String name) throws SAXException, ParserConfigurationException,IOException{
		File file = new File(path,name+".xml");
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		org.w3c.dom.Document domDocument  =builder.parse( file );
		return new DOMBuilder().build(domDocument);
    }

	/**
	 * 주어진 문자열에 개행 문자를 첨가하여, 이를 byte 배열로 반환
	 * @param s
	 * @return
	 */
	private byte[] write(String s){
		s+="\n";
        return s.getBytes();
    }

	/**
	 * 주어진 output strea에 문자열를 쓴다
	 * @param out
	 * @param s
	 * @throws IOException
	 */
	protected void write(OutputStream out, String s) throws IOException{
		out.write(write(s));
    }
	
	/**
	 * style sheet를 주어진 outputstrea에 write할 수 있도록 하위 클래스에서 재정의한다.
	 * @param out
	 * @throws IOException
	 */
	protected abstract void setStyleSheet( OutputStream out, String url, String sheet ) throws IOException;
	
	/**
	 * xml schema or dtd를 주어진 outputstream에 write할 수 있도록 하위 클래스에서 재정의한다
	 * @param fout
	 * @throws IOException
	 */
	protected abstract void setSchema( OutputStream fout, String url, String schema ) throws IOException;

	/**
	 * override this override this write meta information of resultset to xml file
	 * @param meta
	 * @param fout
	 * @throws SQLException
	 * @throws IOException
	 */
    protected abstract void convert2xml(ResultSetMetaData meta, OutputStream fout) throws SQLException,IOException;
    
    /**
     * overrride this write resultset content to xml file
     * @param rset
     * @param fout
     * @param appid
     * @throws SQLException
     * @throws IOException
     */
    protected abstract void convert2xml(ResultSet rset, OutputStream fout) throws SQLException,IOException;


	/**
	 * get outputstream that can make xml file
	 * @param path
	 * @param file
	 * @return
	 * @throws IOException
	 */
    private OutputStream getFileOutputStream(String path,String file) throws IOException{
		File f = new File(path,file+".xml");
		System.out.println(path);
		System.out.println(file+".xml");
        if( f.exists() && f.isFile() ) f.delete();
        OutputStream fout = new FileOutputStream(f,true);
		return fout;
    }

}
