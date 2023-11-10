package org.jepetto.util;

//package jepetto.jutil.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import org.jepetto.logger.DisneyLogger;
import org.jepetto.sql.Wrapper;


public class PropertyReader implements java.io.Serializable
{
	private Properties prop;
	private static PropertyReader reader = new PropertyReader();
	private String path;
	
	DisneyLogger cat = new DisneyLogger(PropertyReader.class.getName());	
	
	private PropertyReader()
	{
		prop = new Properties();
		try
		{
			cat.debug("properties is loading.....");
			load(System.getProperty("jepetto.properties"));
			cat.info("properties loading is finished....");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//cat.info(e);
		}
		
	}

	public static PropertyReader getInstance()	{
		return reader;
	}

	private InputStream init(String path)
	{
		return super.getClass().getResourceAsStream(path);
	}

	public void load(String path) throws IOException
	{
		this.path = path;
		File file = new File(path);
		prop.load( new FileInputStream ( file ) );
	}

	public void reload() throws IOException
	{
		File file = new File(path);
		prop.load( new FileInputStream ( file ) );
	}

	
	public void clear()
	{
		prop.clear();
	}
	public String getProperty(String key)
	{
		return prop.getProperty(key);
	}

	public void setProperty(String key,String value)
	{
		prop.setProperty(key,value);
	}

	public void store() throws IOException
	{
		FileOutputStream fout = new FileOutputStream(new File(path));
		prop.store(fout,null);
		fout.close();
	}

	public String[] getProperties()
	{
		Enumeration _enum = prop.keys();
		String arr[] = null;
		Vector v = new Vector();
		while(_enum.hasMoreElements())
		{
			v.addElement((String)_enum.nextElement());
		}
		arr = new String[v.size()];
		v.copyInto(arr);
		return arr;
	}

	// use case
	public static void main(String args[]) throws IOException
	{

		Properties p = System.getProperties();//("jepetto.properties");
		
		
		PropertyReader reader = PropertyReader.getInstance();
		System.out.println(reader.getProperty("daohome"));
		System.out.println(reader.getProperty("max"));
		//reader.load("C:/kepco/OZApplicationDesigner/jepetto.properties");

		//*/
	}//*/

}