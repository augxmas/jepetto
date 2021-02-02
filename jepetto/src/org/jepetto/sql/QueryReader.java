package org.jepetto.sql;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Category;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPath;

import org.jepetto.logger.DisneyLogger;
import org.jepetto.util.PropertyReader;
import org.jepetto.validator.ColumnValidator;
import org.json.JSONObject;


/**
 * 
 * query xml로 부터 필요롤 하는 query를 읽어드림
 * 
 * @author umlkorea 김창호
 * 
 * 수정 2016.01.12 김방영 HashMap 쪽 옵션 및 order option 강화
 * 수정 2016.06.23 김방영 Columns 기능 추가, select 시 필드명 지정하여 사용 가능
 */
public class QueryReader {

	private List list;
	private Element ele;
	
	private static PropertyReader	reader		= PropertyReader.getInstance();
	private static final String		webinf		= reader.getProperty("webinf"); 
	private static final String		daohome		= reader.getProperty("daohome");
	private static Hashtable		map			= new Hashtable();
	
	private static final String space = " ";
	private static final String comma = ",";
	private static final String from = "FROM";
	private static final String headerSkipedAttr = "HEADER_CNT";
	
	private int headerSkipedCnt	= -1;
	
	private ColumnValidator validators[];
	
	Category cat = DisneyLogger.getInstance(Wrapper.class.getName());
	
	/*
	static {
		doRefresh();
	}*/

	public static void doRefresh(){
		File file = new File(daohome);
		String fileNames[] = file.list();
		for( int i = 0 ; i < fileNames.length ; i++){
			if( fileNames[i].endsWith("xml")){
				
				SAXBuilder builder = new SAXBuilder();
				Document doc = null;
				try {
					
					doc = builder.build( new File(daohome,fileNames[i]) );
					
				} catch (JDOMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				map.put(fileNames[i], doc);
				
			}
		}		
	}
	
	/**
	 * 주어진 클래스의 이름에 해당하는 query xml 파일에서 주어진 key에 해당하는 query을 읽을 수 있는
	 * instance를 생성
	 * 
	 * @param cls dao class
	 * @param key 참조할 query key
	 * @throws JDOMException
	 * @throws IOException
	 */
    public QueryReader( Class cls , String key ) throws JDOMException,IOException {
        String path = cls.getName();
        StringBuffer sb = new StringBuffer();
        sb.append("//xmlquery/sql[@key='");
        sb.append(key);
        sb.append("']");
        String xpath = sb.toString();
		SAXBuilder builder = new SAXBuilder();
        path = webinf +'/'+ path.replace('.','/') + ".xml";
		Document doc = builder.build( new File(path) );
		XPath servletPath = XPath.newInstance(xpath);
		list = servletPath.selectNodes(doc);
    }

	
	/**
	 * 주어진 경로에 해당하는  query xml 파일에서 주어진 key에 해당하는 query을 읽을 수 있는
	 * instance를 생성
	 * 
	 * @param path query xml 파일의 경로
	 * @param key  참조할 query key
	 * @throws JDOMException
	 * @throws IOException
	 */
    
    private Document _doc = null;
    
    public QueryReader( String file , String key ) throws JDOMException,IOException {
    	
		//String path = daohome +'/'+ _class.replace('.','/') + ".xml";
    	
		/*
    	String path = daohome +'/'+ file + ".xml";
    	if( file.endsWith("xml")){
    		path = daohome +'/'+ file;
    	}else{
    		path = daohome +'/'+ file + ".xml";
    	}
    	//*/
    	doRefresh();
    	cat.info("query_key : key " + key + " at " + file);
        StringBuffer sb = new StringBuffer();
        sb.append("//xmlquery/sql[@key='");
        sb.append(key);
        sb.append("']");
        String xpath = sb.toString();
        
		SAXBuilder builder = new SAXBuilder();
		//Document doc = (Document)map.get(file);
		
		_doc = (Document)map.get(file);

		XPath servletPath = XPath.newInstance(xpath);
		list = servletPath.selectNodes(_doc);
    }


	/**
	 * 반환할 query에 바인딩 도는 치환블록이 존재하지 않을 경우
	 * 
	 * @return query
	 */
    public String getQuery(){
        ele = (Element)list.get(0);
        String query =  ele.getText().trim();
		return query;
    }
    
    public String getXlsQeury(){
    	ele = (Element)list.get(0);
    	ele = ele.getChild("SELECT");
    	String eleName = ele.getName();
	
    	String fromAttrValue	= ele.getAttributeValue(from);
    	headerSkipedCnt			= Integer.parseInt(ele.getAttributeValue(headerSkipedAttr));
    	StringBuffer buffer = new StringBuffer();

    	List<Element> children = ele.getChildren();
    	
    	buffer.append(eleName);
    	buffer.append(space);
    	
    	Element subEle = null;
    	
    	int columnSize = children.size();
    	
    	validators = new ColumnValidator[columnSize];
    	
    	String type		= null;
    	String pattern	= null;
    	String min		= null;
    	String max		= null;
    	
    	for( int i = 0 ; i < columnSize-1 ; i++ ){
    		
    		subEle = children.get(i);
    		buffer.append(subEle.getValue());
    		buffer.append(comma);
    		
    		type	= subEle.getAttributeValue("TYPE");
    		pattern = subEle.getAttributeValue("PATTERN");
    		min		= subEle.getAttributeValue("MIN");
    		max		= subEle.getAttributeValue("MAX");
    		
    		validators[i] = new ColumnValidator(i,type,pattern,min,max);
    		
    	}
    	
    	
    	subEle = children.get(columnSize-1);
    	
		type	= subEle.getAttributeValue("TYPE");
		pattern = subEle.getAttributeValue("PATTERN");
		min		= subEle.getAttributeValue("MIN");
		max		= subEle.getAttributeValue("MAX");
		
		validators[columnSize-1] = new ColumnValidator(columnSize-1,type,pattern,min,max);  
		
    	buffer.append(subEle.getValue());
    	
    	
    	
    	buffer.append(space);
    	buffer.append(from);
    	buffer.append(space);
    	buffer.append(fromAttrValue);
    	
    	String xlsQuery = buffer.toString();
    	return xlsQuery;
    }

	public int getHeaderSkipedCnt() {
		return headerSkipedCnt;
	}

	public void setHeaderSkipedCnt(int headerSkipedCnt) {
		this.headerSkipedCnt = headerSkipedCnt;
	}

	public ColumnValidator[] getValidators() {
		return validators;
	}

	public void setValidators(ColumnValidator[] validators) {
		this.validators = validators;
	}

	/**
	 * 해당 query에 존재하는 치환블럭을 주어진 값으로 대체하여 query 를 반환한다
	 * @param table 치환될 값들
	 * @return
	 */
	
	private String repl(String elName, String elValue, HashMap map, HashMap<String, String> params) {
		
		String a = "'%#%'";
		String b = "'%#'";
		String c = "'#%'";
		String d = "'#'";
		String e = "#";
		
		if(elValue.indexOf("#")!=-1) {
			int indx = elValue.indexOf("#");
			int num = 1;
			
			String name = elName+num;
			String value = (map.get(elName)==null) ? "" : (String)map.get(elName);
			while(indx!=-1) {
				name = elName+num;
				
				if(elValue.indexOf(a)!=-1) {
					elValue = repl(elValue, a, "__SHARP__{"+name+"}");
					params.put(name, "%"+value+"%");
				} else if(elValue.indexOf(b)!=-1) {
					elValue = repl(elValue, b, "__SHARP__{"+name+"}");
					params.put(name, "%"+value+"");
				} else if(elValue.indexOf(c)!=-1) {
					elValue = repl(elValue, c, "__SHARP__{"+name+"}");
					params.put(name, ""+value+"%");
				} else if(elValue.indexOf(d)!=-1) {
					elValue = repl(elValue, d, "__SHARP__{"+name+"}");
					params.put(name, value);
				} else if(elValue.indexOf(e)!=-1) {
					elValue = repl(elValue, e, "__SHARP__{"+name+"}");
					params.put(name, value);
				} else {
					elValue = "";
				}
				indx = elValue.indexOf("#");
				num++;
			}
			elValue = replAll(elValue, "__SHARP__", "#");
		} else if(elValue.indexOf("$")!=-1) {
			//elValue = elValue.replaceAll("$", elValue );	
			String value = (map.get(elName)==null) ? "" : (String)map.get(elName);
			elValue = replAll(elValue,"$",value);
		} else {
			
		}
		//sql = sql.replaceAll("%"+elName+"%",elValue);
		
/*		if(elValue.indexOf(a)!=-1) {
			sql = repl(sql,"%"+elName+"%", repl(elValue, a, "${"+elName+"}"));
			params.put(elName, "%"+map.get(elName)+"%");
		} else if(elValue.indexOf(b)!=-1) {
			sql = repl(sql,"%"+elName+"%", repl(elValue, b, "${"+elName+"}"));
			params.put(elName, "%"+map.get(elName)+"");
		} else if(elValue.indexOf(c)!=-1) {
			sql = repl(sql,"%"+elName+"%", repl(elValue, c, "${"+elName+"}"));
			params.put(elName, ""+map.get(elName)+"%");
		} else if(elValue.indexOf(d)!=-1) {
			sql = repl(sql,"%"+elName+"%", repl(elValue, d, "${"+elName+"}"));
			params.put(elName, (String)map.get(elName));
		} else if(elValue.indexOf(e)!=-1) {
			sql = repl(sql,"%"+elName+"%", repl(elValue, e, "${"+elName+"}"));
			params.put(elName, (String)map.get(elName));
		} */
		
		return elValue;
	}
	
	private String repl(String txt, String pattern, String matcher) {
		txt = txt.replaceFirst(Pattern.quote(pattern), 
	               Matcher.quoteReplacement(matcher));
		return txt;
	}
	
	private String replAll(String txt, String pattern, String matcher) {
		txt = txt.replaceAll(Pattern.quote(pattern), 
	               Matcher.quoteReplacement(matcher));
		return txt;
	}
	
	
    public String getQuery(HashMap table, HashMap<String, String> retable){
    	
        String query = getQuery();
		String key = null;
		String value = null;
		StringBuffer sb = new StringBuffer();
		//Set set = table.keySet();
		
		//Iterator iter = set.iterator();

		String match = "";
		
		List<Attribute> attrs = ele.getAttributes();
		for(int i=0;i<attrs.size();i++) {
			Attribute attr = (Attribute)attrs.get(i);
			
			if("".equals(attr.getName())) {
				
			} else if("ORDER_OPTION".equals(attr.getName())) {
				JSONObject obj = new JSONObject(attr.getValue());
				key = (String)table.get("ORDER_OPTION");
				
				if(table.get("ORDER_OPTION")!=null) {
					value = obj.getString(key);
				} else {
					value = obj.getString("default");
				}
				query = query.replaceAll("%ORDER_OPTION%",value);
			} else if("COLUMNS".equals(attr.getName())) {	
				try {
					String[] names = attr.getValue().split(",");
					
					for( String name : names) {
						StringBuffer sb2 = new StringBuffer();
			            sb2.append("//xmlcolumns/columns[@key='");
			            sb2.append(name);
			            sb2.append("']");
			            
						XPath xPath = XPath.newInstance(sb2.toString());
						List list2 = xPath.selectNodes(_doc);
						if(list2.size()>0) {
							value = ((Element)list2.get(0)).getText().trim();
						} else {
						}
						query = query.replaceAll("%"+name+"%",value);
					} 
				} catch (JDOMException e) {
					e.printStackTrace();
				}
			} else if("PAGING".equals(attr.getName()) || "ORDERING".equals(attr.getName()) || "TABLE_NAME".equals(attr.getName())) {
				
				match = (String)table.get(attr.getName());
				
				if("ORDERING".equals(attr.getName())) {
					String[] ms = match.split(" ");
					if(ms.length>4) match = "";
				}
				
				value = attr.getValue();
				value = value.replaceAll("#", match );	
				//value = value.replaceAll("$", match );	
				value = replAll(value, "$", match);
				
				query = query.replaceAll("%"+attr.getName()+"%",value);
			} else {
				if(table.get(attr.getName())!=null) {
					value = attr.getValue();
					match = (String)table.get(attr.getName());
					if( match.equals("")){
						value = "";
					}else{
						//value = value.replaceAll("#", match );	
						value = repl(attr.getName(), value, table, retable);
					}
				} else {
					value = "";
				}
				try {
					//query = query.replaceAll("%"+attr.getName()+"%",value);
					query = replAll(query, "%"+attr.getName()+"%", value); // 2017.02.13 json 관련 $ 변환 문제 픽스 
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Error replace name with value");
					System.out.println("query : "+query);
					System.out.println("name : "+attr.getName());
					System.out.println("value : "+value);
				}
				
			}
		}
        return query;
    }
	
	
	
    public String getQuery(HashMap table){
    	
        String query = getQuery();
		String key = null;
		String value = null;
		StringBuffer sb = new StringBuffer();
		//Set set = table.keySet();
		
		//Iterator iter = set.iterator();

		String match = "";
		
		
		List<Attribute> attrs = ele.getAttributes();
		for(int i=0;i<attrs.size();i++) {
			Attribute attr = (Attribute)attrs.get(i);
			
			if("".equals(attr.getName())) {
				
			} else if("ORDER_OPTION".equals(attr.getName())) {
				JSONObject obj = new JSONObject(attr.getValue());
				key = (String)table.get("ORDER_OPTION");
				
				if(table.get("ORDER_OPTION")!=null) {
					value = obj.getString(key);
				} else {
					value = obj.getString("default");
				}
				query = query.replaceAll("%ORDER_OPTION%",value);
			} else if("COLUMNS".equals(attr.getName())) {	
				try {
					String[] names = attr.getValue().split(",");
					
					for( String name : names) {
						StringBuffer sb2 = new StringBuffer();
			            sb2.append("//xmlcolumns/columns[@key='");
			            sb2.append(name);
			            sb2.append("']");
			            
						XPath xPath = XPath.newInstance(sb2.toString());
						List list2 = xPath.selectNodes(_doc);
						if(list2.size()>0) {
							value = ((Element)list2.get(0)).getText().trim();
						} else {
						}
						query = query.replaceAll("%"+name+"%",value);
					} 
				} catch (JDOMException e) {
					e.printStackTrace();
				}
			} else {
				if(table.get(attr.getName())!=null) {
					value = attr.getValue();
					match = (String)table.get(attr.getName());
					if( match.equals("")){
						value = "";
					}else{
						value = value.replaceAll("#", match );	
					}
				} else {
					value = "";
				}
				query = query.replaceAll("%"+attr.getName()+"%",value);
			}
		}
		/*
		while(iter.hasNext()){
			try{
				key = (String)iter.next();

				
				value = ele.getAttribute( key ).getValue();

				
				match = (String)table.get(key);

				if( match.equals("")){
					value = "";
				}else{
					value = value.replaceAll("#", match );	
				}
	
				value.replaceAll("'","&`");	// "'" 문자를 "&`"로 치환하여 저장, 값을 가져올때 다시 바꿔준다.
	
				sb.append("%");
				sb.append(key);
				sb.append("%");
				key = sb.toString();
	
				sb.delete(0,sb.toString().length());
				
				query = query.replaceAll(key,value);
			}catch(java.lang.NullPointerException e){
				e.printStackTrace();
				//System.out.println("key : " + key);
				//System.out.println(query);
			}
			
		}*/
        return query;
    }
    
   
    
	public static void main(String args[]){
		
		//System.out.println((int)Double.parseDouble("1.0"));
		
        try{
        	
        	File file = new File("x:/");
        	String fileNames[] = file.list();
        	
        	for( int i = 0 ; i < fileNames.length ; i++){
        		
        		System.out.println(fileNames[i].endsWith("xml") ? fileNames[i] : "X");
        	}
        	
        	/*
			QueryReader reader = new QueryReader("Com.Rfid.MyApp.ComRfidMyApp","basic1");
            String name = reader.getClass().getName();
            name = name.replace('.',File.separatorChar);
            System.out.println(name);
			HashMap table = new HashMap();
			String id="'y'";
			table.put("USE_YN", id != null ? id : "");
			System.out.println( reader.getQuery(table));
			
			reader = new QueryReader("mysample","myquery1");
			System.out.println(reader.getQuery());
			
			reader = new QueryReader("mysample","myquery2");
			table = new HashMap();
			table.put("select", id != null ? "mytable" : "");
			System.out.println( reader.getQuery(table));//*/
			
			
        }catch(Exception e){
			e.printStackTrace();
        }//*/
        
    }//*/
	


}
