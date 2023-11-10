package org.jepetto.servlet;

import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;

import org.jepetto.util.PropertyReader;

/**
 * Application Lifecycle Listener implementation class LicenseListener
 *
 */
public class LicenseListener implements ServletContextListener {

	
	protected PropertyReader reader = PropertyReader.getInstance();
	
	
	private String userCountry;
	private String osArch;
	private String osName;
	private String osVersion;
	private String sunCpuIsalist;
		
    /**
     * Default constructor. 
     */
    public LicenseListener() {
        // TODO Auto-generated constructor stub
		try {
			userCountry		= reader.getProperty("user.country");
			osArch			= reader.getProperty("os.arch");
			osName			= reader.getProperty("os.name");
			osVersion		= reader.getProperty("os.version");
			sunCpuIsalist	= reader.getProperty("sun.cpu.isalist");
			System.out.print("license is checked........");
			isValid(userCountry,osArch,osName,osVersion,sunCpuIsalist);
			System.out.println("correct");
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Can't work......buy license");
			System.exit(0);
		}    	
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent e) {
        // TODO Auto-generated method stub
		System.out.println(e.getSource().getClass());
    	
    }
    
	public void isValid(String userCountry, String osArch, String osName,String osVersion,String sunCpuIsalist) throws ServletException{
		Properties sysInfo = System.getProperties();
		this.userCountry	= sysInfo.getProperty("user.country");
		this.osArch			= sysInfo.getProperty("os.arch");
		this.osName			= sysInfo.getProperty("os.name");
		this.osVersion		= sysInfo.getProperty("os.version");
		this.sunCpuIsalist	= sysInfo.getProperty("sun.cpu.isalist");
		
		boolean flag = false;
		
		if(this.userCountry.equals(userCountry) && this.osArch.equals(osArch) && this.osName.equals(osName) && this.osVersion.equals(osVersion) && this.sunCpuIsalist.equals(sunCpuIsalist) ) {
			
		}else{
			throw new ServletException("License Invalid");
		}
		
		
	}    

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}
