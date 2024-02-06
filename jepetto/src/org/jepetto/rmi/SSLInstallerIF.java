package org.jepetto.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SSLInstallerIF extends Remote {

	public boolean install(String str) throws RemoteException;
	
}
