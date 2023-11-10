package org.jepetto.logger;

import java.util.Enumeration;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jepetto.util.PropertyReader;



public class DisneyLogger  {

	private String className;
	
	private Logger logger = null;
	//static PropertyReader reader = PropertyReader.getInstance(); 
	//log4j.configurationFile
	public DisneyLogger(String cls){
		this.className = cls;
		logger = Logger.getLogger(cls);
		
	}
	//*/
	
	public void info(String msg){
		logger.info(msg);
	}

	public void debug(String msg){
		logger.debug(msg);
	}	
	
	public void fatal(String msg){
		logger.fatal(msg);
	}	
	
	/*
	protected DisneyLogger(String arg0) {
		super(arg0);
	}//*/
	

	public void error(Object o){
		Exception e = (Exception)o;
		StackTraceElement ele[] = e.getStackTrace();
		StringBuffer buffer = new StringBuffer();
		buffer.append(ele[0].getClassName() +":" + e.getMessage() );
		for( int i = 1 ; i < ele.length ; i++){
			buffer.append("\t");
			buffer.append("at ");
			buffer.append(ele[i]);
			buffer.append("\n");
		}
		logger.error(buffer.toString());
	}
	
	
	
}
