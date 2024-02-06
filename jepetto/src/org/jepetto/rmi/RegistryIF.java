package org.jepetto.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RegistryIF extends Remote {

	public boolean regist(String key, SSLInstallerIF installer) throws RemoteException;
	
	public boolean remove(String key) throws RemoteException;
	
	public void install(String key) throws RemoteException;
	
}
