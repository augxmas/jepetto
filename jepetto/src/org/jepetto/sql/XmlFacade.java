package org.jepetto.sql;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jdom2.Attribute;
import org.jdom2.CDATA;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.JDOMFactory;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.DOMOutputter;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/*
import org.jdom.transform.JDOMResult;
import org.jdom.transform.JDOMSource;
import java.util.List;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import org.jdom.JDOMException;
//*/



/**
 * <pre>
 * 
 * xml dom instance의 작성 시 사용할 수 있는 wrapping 클래스 
 * 
 * use case
 * 
 * 		XmlFacade x = new XmlFacade();
 * 		ResultSetMetaData meta = rset.getMetaData();
 * 		int count = meta.getColumnCount();
 * 		String columnName = null;
 * 		x.createRootElement("recordset");
 * 		while( rset.next()  ){
 * 			x.createSubElement("row");
 * 			for( int i = 1 ; i &lt; count+1 ; i++){
 * 				x.addChild(meta.getColumnName(i),rset.getString(i));
 * 			}
 * 		}
 *   
 * @author umlkorea 김창호
 *
 * </pre>
 */


public class XmlFacade {
	
	/**
	 * dom instance factory
	 */
	private JDOMFactory factory;
	
	/**
	 * dom instance
	 */
	private Document doc;

	
	
	/**
	 * 
	 * root element의 sub element
	 * root element
	 *    |
	 *    +-- sub_element
	 * 	  |			|
	 * 	  |			+---- elements
	 *    |
	 *    +-- sub_element
	 *              |
	 *              +---- elements
	 */

	private Element root;
	
	
	/**
	 * root element의 sub element
	 * root element
	 *    |
	 *    +-- sub_element
	 * 	  |			|
	 * 	  |			+---- elements
	 *    |
	 *    +-- sub_element
	 *              |
	 *              +---- elements
	 */
	
	private Element ele;
	
	public XmlFacade(){
		SAXBuilder builder = new SAXBuilder();
		factory = builder.getFactory();	
	}
	

	
	/**
	 * 
	 * 주어진 이름으로 element 생성하고, 이를 xml 문서의 최상위 element로 삼는다
	 * 
	 * @param name root element name
	 */
	public void createRootElement( String name ){
	    root = createElement(name);
		doc = factory.document( root );
		
	}
	
	/**
	 * 
	 * attribute를 가진 root element 생성
	 * 
	 * @param name
	 * @param attr
	 * @param value
	 */
	public void createRootElement(String name, String attrs[], String values[]){
	    Element ele = createElement(name);
	    
	    for( int i = 0 ; i < attrs.length ; i++){
	        ele.setAttribute( attrs[i] , values[i] );
	    }
	    doc = factory.document(ele);
	}
	
	
	
	/**
	 * 주어진 이름의 element를 생성
	 * @param name element name
	 * @return
	 */
	public Element createElement(String name){
		return factory.element(name);
	}

	/**
	 * 주어진 이름의 element를 생성하여, root element의 child element로 삼는다
	 * @param name
	 */
	
	/*
	public void createSubElement(String name){
	    ele = createElement(name);
	    doc.getRootElement().addContent( ele );
	}
	//*/
	
	public Element createSubElement(String name){
	    ele = createElement(name);
	    doc.getRootElement().addContent( ele );
	    return ele;
	}

	
	public void createSubElement(String name, String value, String attrs[], String values[]){
	    ele = createElement(name);
	    for( int i = 0 ; i < attrs.length ; i++){
	        ele.setAttribute(attrs[i],values[i]);
	    }
	    doc.getRootElement().addContent( ele );
	}
	
	
	
	/**
	 * 주어진 이름의 attribute를 생성하고 그의 값을 할당한다
	 * @param name attribute name
	 * @param value attribute value
	 * @return
	 */
	public Attribute createAttribute(String name, String value){
		return factory.attribute(name,value);
	}

	/**
	 * parent element에 child element를 종속시킨다
	 * @param parent parent element
	 * @param child  child element
	 * @return
	 */
	public Element addChild(Element parent, Element child){
		return (Element)parent.addContent(child);
	}
	
	/**
	 * 부모 element에 자식 element를 종속시킨다
	 * @param parent parent element
	 * @param children children element(s)
	 * @return
	 */
	public Element append(Element parent, Element children[]){
		for( int i = 0 ; i < children.length ; i++){
			parent.addContent(children[i]);
		}
		ele.addContent(parent);
		return parent;
	}
	
	/**
	 * 
	 * 
	 * @param parent parent element
	 * @param names child's element name
	 * @param values child's element value
	 * @return
	 */
	public Element append(Element parent, String names[], String values[]){
		Element ele = null;
		for( int i = 0 ; i < names.length ; i++){
			ele = this.createElement(names[i]);
			this.setValue(ele, values[i]);
			parent.addContent(ele);
		}
		this.ele.addContent(parent);
		return parent;
	}
	
	public Element append(Element parent,String attr[],String attrValues[], String names[], String values[]){
		Element ele = null;
		
		for( int j = 0 ; j < attr.length ; j++){
			parent.setAttribute(attr[j], attrValues[j]);
		}
		
		for( int i = 0 ; i < names.length ; i++){
			ele = createElement(names[i]);
			setValue(ele, values[i]);
			parent.addContent(ele);
		}
		this.ele.addContent(parent);
		return parent;
	}

	
	/**
	 * 주어진 이름으로 element 생성하고 값을 할당한 후, 이 element를 ,sub element에 종속시킨다
	 * @param name element name
	 * @param value element value
	 */
	public void addChild(String name, String value){
		Element _ele = createElement(name);
		setValue(_ele,value);
		ele.addContent(_ele);
	}
	
	/**
	 * 주어진 이름의 element를 생성하고 주어진 값을할당한다. 
	 * 주어진 이름의 attribute 들을 생성하고, 그 값들을 할당한다.
	 * @param name		element name
	 * @param value		element value
	 * @param names		attribute names
	 * @param values	attribute values
	 */
	public void addChild(String name, String value, String names[], String values[]){
		Element _ele = createElement(name);
		
		if( value != null){
		    setValue(_ele,value);
		}
		
		ele.addContent(_ele);
		for( int i = 0 ; names != null && i < names.length ; i++){
			addAttribute( _ele,  createAttribute(names[i],values[i]) );	
		}
	}
	
	/**
	 * 주어진 element에 주어진 attribute를 종속시킨다
	 * @param ele element
	 * @param attr attribute
	 */
	public void addAttribute(Element ele, Attribute attr){
		ele.setAttribute(attr);
	}
	
	/**
	 * 주어진 element에 값을 할당한다. 이때 값은 CDATA로 처리된다
	 * @param ele element
	 * @param value value
	 */
	public void setValue(Element ele, String value){
	    if( value !=null ){
			CDATA cdata = new CDATA(value);
			ele.setText(cdata.getValue());
			//ele.setText(cdata.getText());
	    }
	}
	
	/**
	 * 주어진 element에 값을 할당한다. 이때 값은 CDATA로 처리된다
	 * @param ele element
	 * @param value value
	 */
	public void setValue(Element ele, int value){
		CDATA cdata = new CDATA(String.valueOf(value));
		
		ele.setText(cdata.getValue());
	}
	

	/**
	 * 주어진 element에 값을 할당한다. 이때 값은 CDATA로 처리된다
	 * @param ele element
	 * @param value value
	 */
	public void setValue(Element ele, double value){
		CDATA cdata = new CDATA(String.valueOf(value));
		ele.setText(cdata.getValue());
	}

	/**
	 * dom instance를 반환
	 * @return dom instance
	 */
	public Document getDocument(){
		return doc;
	}
	
	public void setDocument(Document doc){
		this.doc = doc;
	}

	/**
	 * dom instance를 주어진 경로에 xml 파일로 저장한다
	 * @param path xml 파일 경로
	 * @param file
	 * @param encoding
	 * @return xml 문서가 성공적으로 저장되면 true, 그렇지 않으면 false
	 * @throws IOException
	 */
	public boolean save( String path, String file, String encoding) throws IOException{
	    XMLOutputter out = new XMLOutputter();
	    
		Format format = Format.getCompactFormat();
		format.setLineSeparator("\n");
		if( encoding == null ) encoding = "euc-kr";
		format.setEncoding(encoding);
	    out.setFormat(format);
	    
	    /*
	    try {
            XSLTransformer tranfer = new XSLTransformer(new java.io.File("c:/temp/a.xsl"));
            doc = tranfer.transform(doc);
        } catch (XSLTransformException e1) {
            e1.printStackTrace();
        }//*/


	    
	    
	    FileOutputStream fos = null;
	    boolean flag = true;
	    try{
	        fos = new FileOutputStream( new File(path,file) );
	        out.output(doc,fos);
	    }catch(IOException e){
	        flag = false;
	        throw e;
	    }finally{
	        fos.close();
	    }
	    return flag;
	}
	
	public String getDomString(){
		
        DOMOutputter putter = new DOMOutputter(); 
        org.w3c.dom.Document doc;
        StringWriter writer = new StringWriter();
		try {
			
			doc = putter.output(getDocument());
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();
	        transformer.setOutputProperty(OutputKeys.INDENT, "YES");
	        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	        transformer.setOutputProperty(OutputKeys.ENCODING, "euc-kr");
	        
	        transformer.transform(new DOMSource(doc), new StreamResult(writer));
			
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return writer.toString();
		
	}
	
	
	public static void main(String args[]){
	    XmlFacade facade = new XmlFacade();
	    String attrs[] = {"spec","codebase","href"};
	    String values[] = {"1.0+","http://172.16.13.129","JWSDemo.jnlp"};
	    
	    facade.createRootElement("jnlp", attrs, values);
	    
	    	facade.createSubElement("information");
	    		facade.addChild("title","white snow and 7 litte guys");
	    		facade.addChild("vendor","Ubitas");
	    		facade.addChild("homepage", null ,new String[]{"href" } , new String[]{"http://172.16.13.129"});
	    		facade.addChild("description","Grumpy is downing xls........");
	    		facade.addChild("description",null,new String[]{"kind"},new String[]{"short"});
	    		facade.addChild("offline-allowed",null);

	    	String names[] = {"urlpath","loginid","password","returnfield"};
	    	values = new String[]{"urlpath","loginid","password","returnfield"};
	    	
	    	facade.append(facade.createElement("RETURNURL"), names, values);
	    	facade.append(facade.createElement("RETURNURL"),new String[]{"attr","attr1"},new String[]{"value","value1"}, names, values);
	    	
	    	facade.createSubElement("security");
	    		facade.addChild("all-permissions",null);
	    		
	    	facade.createSubElement("resources");
	    		facade.addChild("j2se",null, new String[]{"version"}, new String[]{"1.5"});
	    		facade.addChild("jar",null, new String[]{"href"}, new String[]{"http://172.16.13.129/a.jar"});
	    	
		    	facade.append(facade.createElement("RETURNURL"), names, values);
		    	facade.append(facade.createElement("RETURNURL"), names, values);
		    	

		    	
	    	facade.createSubElement("application-desc", null, new String[]{"main-class"},new String[]{"com.ecs.jnlp.ms.Grumpy"} );
	    		
	    		args = new String[]{"&user_id","query_id","query_key","condition"};
	    		for( int i = 0 ; i < args.length ; i++){
	    		    facade.addChild("argument",args[i]);
	    		}
	    

            try {
				System.out.println( facade.save("c:/","a.xml",null) );
				System.out.println(facade.getDomString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    		
	}//*/
	
	
	
	
	
}
