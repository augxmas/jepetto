package org.jepetto.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class RmiDaemon {
	
	

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("rmi server started.....");
		String rmiHost		= args[0];
		String rmiPort		= args[1];
		String bindingName	= args[2];
		
		RegistryIF registry = null;
		try {
			registry = new RegistryImpl();
			
			//RegistryIF stub = (RegistryIF) UnicastRemoteObject.exportObject(registry,Integer.parseInt(rmiPort));
			//Registry _registry = LocateRegistry.getRegistry();
			Naming.rebind(bindingName, registry);
			
			while(true) {
				try {
					Thread.sleep(2*1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int min = 1;
				int max = 3;
				int i = (int)(max*Math.random()+min);
				try {
					registry.install(String.valueOf(i));
				}catch(Exception e) {
					//e.printStackTrace();
				}
			}
		
		} catch (RemoteException | MalformedURLException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
