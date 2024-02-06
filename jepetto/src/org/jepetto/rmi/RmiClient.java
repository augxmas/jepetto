package org.jepetto.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RmiClient {
	
	public static void main(String args[]) {
		String rmiHost		= args[0];
		String rmiPort		= args[1];
		String bindingName	= args[2];
		String key			= args[3];
		try {
			/*
			Registry registry = LocateRegistry.getRegistry(rmiHost,Integer.parseInt(rmiPort));
			RegistryIF stub = (RegistryIF)registry.lookup(bindingName);
			//*/
			
			String url = "rmi://" + rmiHost + ":" + rmiPort + "/" + bindingName;
			System.out.println(url);
	        RegistryIF remote = (RegistryIF)Naming.lookup(url);
			SSLInstallerIF installer = new SSLInstallerImpl();
			remote.regist(key, installer);
			//*/
			
	        
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
