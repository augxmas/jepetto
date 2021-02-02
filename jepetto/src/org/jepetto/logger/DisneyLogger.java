package org.jepetto.logger;

import org.apache.log4j.Category;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;

public class DisneyLogger extends Category {

	private String className;
	
	public DisneyLogger(String className) {
		super(className);
		// TODO Auto-generated constructor stub
		this.className = className;
	}
	
	public Category getInstnce(Class cls){
		return super.getInstance(cls);
	}
	
	public static Category getInstnce(String cls){
		return getInstance(cls);
	}
	
	
	public void info(String msg){
		super.info(className + " " + msg);
	}

	public void debug(String msg){
		super.info(className + " " + msg);
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
		super.error(buffer.toString());
	}
	
	public void setLevel(Level level){
		LogManager.getRootLogger().setLevel(level);
	}
	
	
}
