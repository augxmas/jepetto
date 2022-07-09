package org.jepetto.logger;

import java.io.File;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;



public class DisneyLogger  {

	private String className;
	
	private Logger logger = null;
	
	public DisneyLogger(String cls){
		//PropertyConfigurator.configure(System.getProperty("user.dir") + File.separator + "log4j.properties");
		PropertyConfigurator.configure("c:\\tomcat\\conf\\log4j.conf.xml");
		this.className = cls;
		logger = LogManager.getLogger(cls);
	}
	//*/
	
	public void info(String msg){
		logger.info(className + " " + msg);
	}

	public void debug(String msg){
		logger.debug(className + " " + msg);
	}	
	
	public void fatal(String msg){
		logger.fatal(className + " " + msg);
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
