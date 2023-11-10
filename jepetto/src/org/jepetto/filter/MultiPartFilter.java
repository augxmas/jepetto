package org.jepetto.filter;


import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
//import org.apache.log4j.Category;
import org.jepetto.logger.DisneyLogger;
import org.jepetto.proxy.HomeProxy;
import org.jepetto.util.PropertyReader;
import org.jepetto.util.Util;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * ServletRequest upload  Filter
 * 
 * @web.filter name="MultiPartFilter"
 * 			   display-name="MultiPart Filter"
 * 	
 * @web.filter-init-param name="param"
 * 						  value="value"
 * 
 * @web.filter-mapping url-pattern="/*"
 * 
 * 
 * 					   
 */

public class MultiPartFilter  implements Filter {
	
	DisneyLogger cat = new DisneyLogger(MultiPartFilter.class.getName());	
		
	protected FilterConfig filterConfig = null;

	//private String restrictedSuffix[] = null;
	
	private String allowedSuffix[] = null;
	
	

	//public static final String allowedContentType = "application";

	private static PropertyReader reader = PropertyReader.getInstance();
	
	private String tempRepository = null;
	private String savedRepository = null;
	
	private String MAX = null;//reader.getProperty("max");
	
	public void init(FilterConfig filterConfig) throws ServletException {
		MAX					= reader.getProperty("max");
		tempRepository		= reader.getProperty("tempRepository");
		savedRepository		= reader.getProperty("savedRepository");
		//restrictedSuffix 	= Util.split(reader.getProperty("restrictedSuffix"), ",");
		allowedSuffix 	= Util.split(reader.getProperty("allowedSuffix"), ",");
	}
	
	public void doFilter( ServletRequest _req, ServletResponse res, FilterChain chain)	throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)_req;
		
        DiskFileUpload upload = new DiskFileUpload();
        
        upload.setSizeMax( Integer.parseInt(MAX) );
        
		upload.setRepositoryPath( tempRepository );

		//
		
		
		        
        boolean isMulti = DiskFileUpload.isMultipartContent(req);
		//isMulti = true;

        if( isMulti ){

        	req.setCharacterEncoding("UTF-8");
        	
        	
			List items = null;
            try{
                items = upload.parseRequest(req);
            }catch( FileUploadException e ){
            	e.printStackTrace();
				throw new ServletException(e);
            }

			Iterator iter = items.iterator();
			FileItem item = null;
			
			HashMap map = new HashMap();  
			
			while (iter.hasNext()) {
				item = (FileItem) iter.next();
				if (item.isFormField()) {
					processFormField(req,item,map);
				} else if(item.getSize()>0){
                    try{
						processUploadedFile(req,item);
                    }catch(FileUploadException e){
                    	e.printStackTrace();
						throw new ServletException(e);
                    }
				}
			}

        }else{
			processFormField(req);
        }
        chain.doFilter(req, res);
	}
	
    private void processFormField( ServletRequest req , FileItem item ,HashMap<String,String> map){
    	
        String fieldname = item.getFieldName();
        
		String value = (item.getString());
		


		Object o = null;
		
		if( map.containsKey(fieldname) ){
			String arr[] = null;
			o = req.getAttribute(fieldname);
			if( o instanceof String ){
				 arr = new String[]{o.toString(),value};
			}else if(o instanceof String[]){
				arr = (String[])o;
				String _arr[] = new String[arr.length+1];
				for(int i = 0 ; i < arr.length ; i++){
					_arr[i] = arr[i];
				}
				_arr[_arr.length-1] = value;
				arr = _arr;
			}
			req.setAttribute(fieldname, arr);
		}else{
			
			req.setAttribute(fieldname, value);
		}
		
		
		map.put(fieldname, value);

    }	
	
	private void processFormField( ServletRequest req ){
		
		if(req.getContentType()!= null && req.getContentType().equalsIgnoreCase("application/json")){
			
			java.io.BufferedReader reader = null;
			
			JSONParser parser = new JSONParser();
			Object obj;
			
			try {
				reader = req.getReader();
				String _temp = "";
				StringBuffer buffer = new StringBuffer();
				while(_temp != null) {
					buffer.append(_temp);
					_temp = reader.readLine();
				}

				String jsonStr = buffer.toString();
				System.out.println("input value as json....");
				System.out.println(jsonStr);
				obj = parser.parse( jsonStr );
				
				req.setAttribute("jsonString", jsonStr);
				JSONObject jsonObj = (JSONObject) obj;
				req.setAttribute("jsonObject", jsonObj);
				
				Set <String>set = jsonObj.keySet();
				java.util.Iterator<String> iter = set.iterator();
				String name = null;
				String value = null;
				
				while(iter.hasNext()) {
					name = iter.next();
					try {
						value = (String) jsonObj.get(name);
						req.setAttribute(name, value);
					}catch(java.lang.ClassCastException e) {
						e.printStackTrace();
					}
				}		
			}catch(IOException e) {
				e.printStackTrace();
			}catch (ParseException e) {
				e.printStackTrace();
			}catch(ClassCastException e) {
				e.printStackTrace();
			}
			
		} else {
		
			Enumeration <String>_enum	= req.getParameterNames();
			String name			= null;
	
			while(_enum != null && _enum.hasMoreElements()){
				name = (String)_enum.nextElement();
				String arr[] = req.getParameterValues(name);
				
				try{
					if(arr.length > 1){
						req.setAttribute(name, arr);
					}else{
						req.setAttribute(name, arr[0]);
					}
				}catch(java.lang.NullPointerException e){
					req.setAttribute(name, null);
				}
			}
		}
		
	}
	
	/*
	private void processFormField( ServletRequest req , FileItem item ){
        String fieldname = item.getFieldName();
		String value = item.getString();
		req.setAttribute( fieldname , value );
    }//*/

    private void processUploadedFile( HttpServletRequest req , FileItem item ) throws IOException, FileUploadException {

        String fieldname = item.getFieldName();
        
		byte arr[] = item.get();
		
        String name = item.getName();
        
        name = name.substring( name.lastIndexOf("\\") + 1 );//
        
        int point = name.lastIndexOf(".");

        if( name.trim().length() > 1 && point == -1 ){
        	throw new FileUploadException("No Entension, Name of attached file should include suffix");
        }
        boolean isAllowed = false;
		String suffix = name.substring(point + 1).toLowerCase();
		for( int i = 0 ; i < allowedSuffix.length  ; i++){
			//if( suffix.indexOf(allowedSuffix[i]) > 0 ){
			if( suffix.toLowerCase().equals(allowedSuffix[i].toLowerCase())){
				isAllowed = true;
				break;
            }
        }
		
		if(!isAllowed){
			throw new FileUploadException("Not Allowed File type");
		}

		String rename = getRenamedFile(savedRepository,name);
		
		File file = new File( savedRepository + java.io.File.separator + rename );
		
		try {
			item.write(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd - HH:mm:ss") ;
		
		FileInfo info = new FileInfo((name),(rename), arr.length +"",df.format(new Date()),"0");

		if(req.getAttribute(fieldname)==null) {
			req.setAttribute( fieldname , info );
		} else {
			Object o = req.getAttribute(fieldname);
			
			if(o instanceof FileInfo) {
				FileInfo _info = (FileInfo)o;
				ArrayList<FileInfo> list = new ArrayList<FileInfo>();
				
				list.add(_info);
				list.add(info);
				
				req.setAttribute( fieldname , list);
			} else {
				@SuppressWarnings("unchecked")
				ArrayList<FileInfo> list = (ArrayList<FileInfo>)o;
				list.add(info);
				req.setAttribute( fieldname , list);
			}
		}
		item.delete();
    }



	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	public void setFilterConfig(FilterConfig cfg) {
		filterConfig = cfg;
	}

	public void destroy() {
		//do-nothing
	}

    public  String getRenamedFile(String path, String fileName) throws IOException{
        File file = new File(path);
        if(file.isDirectory()){
            String filesName[] = file.list();
            for(int i = 0 ; i < filesName.length ; i++){
                if(filesName[i].equals(fileName)){
                    fileName = setRenamedFile(fileName,path);
                    break;
                }
            }
        }
        return fileName;
    }

    private  String setRenamedFile(String fileName,String path)throws IOException{
        File file = new File(path);
        int index= fileName.indexOf(".");
        String prefix = fileName.substring(0,index);
        String suffix = fileName.substring(index+1);
        
        try{
            file = File.createTempFile( prefix , "."+suffix , file );
        }catch(java.lang.IllegalArgumentException e){
            file = File.createTempFile(prefix+"tmp","."+suffix,file);
        }
        String name = file.getName();
        return name;
    }
    
    class FileFilter implements FilenameFilter{
    	public boolean accept(File dir, String name){
    		
    		boolean flag = dir.isDirectory() && name.endsWith(".tmp");

    		return flag;
    		
    	}
    }

    /**
     * remove a temp file that generated by apache 
     *
     */
	public void remove(){
		
		String root = tempRepository;
		File file = new File(root);
		
		String arr[] = null;
		if( file.isDirectory() ){
			FileFilter filter = new FileFilter();
			arr = file.list( filter );
		}
		for( int i = 0 ; arr !=null && i < arr.length ; i++){
			
			file = new File( root , arr[i]);
			file.delete();
			
		}
		
	}

}
