package org.jepetto.proxy;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


import org.jepetto.bean.Facade;
import org.jepetto.bean.FacadeBean;
import org.jepetto.logger.DisneyLogger;
import org.jepetto.util.PropertyReader;




/**
 * 
 * @(#) HomeProxy.java 
 *
 * <pre> 
 * </pre>
 * @author kimchangho 
 * @version 1.0 2006.02.10
 * @ see 
 *
 */
public class HomeProxy {
	
	//static Category cat = DisneyLogger.getInstance(HomeProxy.class.getName());
	DisneyLogger cat = new DisneyLogger(HomeProxy.class.getName()); 
	
	static PropertyReader reader = PropertyReader.getInstance();  
	
	/**
	 * 蹂� �겢�옒�뒪�쓽 �쑀�씪�븳 instance
	 */
	private static HomeProxy proxy = new HomeProxy();
	
	private HomeProxy(){}
	
	public static HomeProxy getInstance(){
		return proxy;
	}
	

	
	public Facade getFacade(){

		Facade remote = new FacadeBean();//{arr[count++];
	    
		return remote;
	}
	
	
	
}
