package org.jepetto.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

public class RegistryImpl extends UnicastRemoteObject implements RegistryIF {
	
	private static final long serialVersionUID = 1L;
	Map<String,SSLInstallerIF> installers = new java.util.HashMap<String,SSLInstallerIF>();
	
	public RegistryImpl() throws RemoteException{
		System.out.println("initailized.....");
	}

	@Override
	public boolean regist(String key, SSLInstallerIF installer) throws RemoteException {
		boolean flag = true;
		installers.put(key,installer);
		System.out.printf(
				"installer size ? %d",
				installers.size()
				);
		
		return flag;
	}

	@Override
	public boolean remove(String key) throws RemoteException {
		// TODO Auto-generated method stub
		//SSLInstaller installer = 
		installers.remove(key);
		System.out.printf(
				"installer size ? %d \n",
				installers.size()
				);
		
		boolean flag = true;
		return flag;
	}

	@Override
	public void install(String key) throws RemoteException {
		// TODO Auto-generated method stub
		SSLInstallerIF installer = installers.get(key);
		installer.install(String.valueOf(System.currentTimeMillis()));
	}

}
