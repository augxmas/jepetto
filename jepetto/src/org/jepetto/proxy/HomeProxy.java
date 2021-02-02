package org.jepetto.proxy;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Category;

import org.jepetto.bean.Facade;
import org.jepetto.bean.FacadeBean;
import org.jepetto.logger.DisneyLogger;
import org.jepetto.util.PropertyReader;




/**
 * 
 * @(#) HomeProxy.java 
 *
 * <pre> 
 * remote home inerface, remote interce�뿉 ���븳 proxy �뿭�븷�쓣 �븳�떎. 利�
 * �궗�슜�옄�뒗 lookup�쓣 �븯�뒗 寃껋씠 �븘�땲�씪 蹂� �겢�옒�뒪�뿉 remote home �삉�뒗 interface瑜� �슂泥��븳�떎
 * 蹂� �겢�옒�뒪�뒗 �씠�윺 寃쎌슦 �꽦�뒫 �뼢�긽�쓣 �쐞�빐 �씠�뱾�뿉 ���빐�꽌 pooling �꽌鍮꾩뒪濡쒖꽌 吏��썝�븯怨� �엳�떎
 * </pre>
 * @author 源�李쏀샇
 * @version 1.0 2006.02.10
 * @ see 
 *
 */
public class HomeProxy {
	
	static Category cat = DisneyLogger.getInstance(HomeProxy.class.getName());
	static PropertyReader reader = PropertyReader.getInstance();  
	
	/**
	 * 蹂� �겢�옒�뒪�쓽 �쑀�씪�븳 instance
	 */
	private static HomeProxy proxy = new HomeProxy();
	
	private HomeProxy(){}
	
	public static HomeProxy getInstance(){
		return proxy;
	}
	
	/*
	static Facade arr[] = null;
	static int count = 0;
	static{
		count = Integer.parseInt(reader.getProperty("bean_init_count"));
		arr = new Facade[count];
		for( int i = 0 ; i < arr.length ; i++){
			arr[i] = new FacadeBean();
		}
		count = 0;
	}
	*/
	
	public Facade getFacade(){

		Facade remote = new FacadeBean();//{arr[count++];
	    
		return remote;
	}
	
	
	
}
