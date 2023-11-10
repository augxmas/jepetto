package org.jepetto.sql;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPath;

public class XmlStatement extends  LoggableStatement{

    private Document doc;
	private String xpath;

	private List list;
	
	public List getList(){
		return list;
    }

	public XmlStatement(String path,String xpath)throws SQLException, IOException{

    	try{
			SAXBuilder builder = new SAXBuilder();
			
			
			Document doc = builder.build( new File(path) );
			
			XPath servletPath = XPath.newInstance(xpath);
			list = servletPath.selectNodes(doc);
    	}catch(JDOMException e){
			throw new XQLException(e);
    	}catch(IOException e){
    		throw e;
    	}
    }
    
	public XmlStatement(Document doc, String xpath) throws SQLException{
		try{
			XPath servletPath = XPath.newInstance(xpath);
			list = servletPath.selectNodes(doc);
		}catch(JDOMException e){
			//e.printStackTrace();
			throw new XQLException(e);
		}
	}
    

	public ResultSet executeQuery() throws SQLException{
		XmlResultSet rset = new XmlResultSet(list);
		return rset;
	}//*/
	

    public boolean match(String query){
        
        query = query.trim().toLowerCase();
        boolean flag = false;
        
        String arr[] ={ "@covan","@cv","$covan","$cv","@ems","$ems","$etis","@etis"};
        
        int index = 0;
        if( query.startsWith("insert") || query.startsWith("update") || query.startsWith("delete") ){
            //System.out.println(query);
            for( int i = 0 ; i < arr.length ; i++){
                if(query.indexOf(arr[i]) > 0){
                    return true;
                }
            }
        }
        
        return flag;
    }
    
    public static void main(String args[]){
       // System.out.println(new java.util.Date()+"   kjlj");
        
        
        
        try{
            //XmlStatement stmt = new XmlStatement("c:/XXX.xml","//xmlquery/sql[@key='QUERY_SELECT']");///query");
        	//XmlStatement stmt = new XmlStatement("c:/temp/my.xml","/RecordSet[@xmlns='http://xmlns.oracle.com/apps/otm']/Row/element");///query");
        	//XmlStatement stmt = new XmlStatement("c:/temp/my.xml","/RecordSet/Row[@xmlns='http://xmlns.oracle.com/apps/otm']/element");///query");
            String path="D:/eclipse/workspace/eps/tool/SYSTEMiER/application_suite/core_framework/query/query_eps_COM00.xml";
        	//XmlStatement stmt = new XmlStatement("c:/temp/my.xml","/RecordSet[@abc='key']/Row/element");///query");
            
            String dir = "d:/";
            
            File file = new File(dir);
            
            String files[] = file.list();
            
            for( int j = 0 ; j < files.length ; j++){
            
                XmlStatement stmt = new XmlStatement(files[j],"/queryservice/queries/query");///query");
    			List list = stmt.getList();
    			String str = null;
                Element ele = null;
                Element _ele = null;
                Attribute attr = null;
                
    			for( int i = 0 ; i <list.size() ; i++){
    				ele = (Element)list.get(0);
                    _ele = ele.getChild("statement");
    				attr = ele.getAttribute("id");
    			}
            }
            
        }catch(Exception e){
			e.printStackTrace();
        }//*/

        /*
        try{
        	Connection con = new XmlConnection("c:/sample.xml");
			PreparedStatement stmt = con.prepareStatement("//recordset/row");
			
			//PreparedStatement stmt = new XmlStatement("c:/vitac.xml","//recordset/row");///query");
			
            //PreparedStatement stmt = new XmlStatement("c:/vitac.xml","//recordset/row");///query");
            //XmlStatement stmt = new XmlStatement("c:/vitac.xml","//recordset/row/OF_HAN1");///query");
            
            ResultSet rset = stmt.executeQuery();
            int index = 0;
            while( rset.next() ){
            	System.out.println();
            	
				System.out.println( rset.getString("empid") );
				//System.out.println( rset.getString(++index) );				
				//System.out.println( rset.getString(++index) );
				//System.out.println( rset.getString(++index) );
            	index = 0;
            }
            /*
			List list = stmt.getList();
			String str = null;
            Element ele = null;
            org.jdom.Attribute attr = null;
            List _list = null;
			for( int i = 0 ; i <list.size() ; i++){
				ele = (Element)list.get(i);
				_list = ele.getChildren();
                System.out.println("-------------");
                for( int  j = 0 ; j < _list.size() ; j++){
					ele = (Element)_list.get(j);
                    System.out.println("\t" + ele.getText());
                }
			}
        }catch(Exception e){
			e.printStackTrace();
        }//*/

    }


}
