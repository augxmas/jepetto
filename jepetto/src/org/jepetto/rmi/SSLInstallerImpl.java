package org.jepetto.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SSLInstallerImpl extends UnicastRemoteObject implements SSLInstallerIF {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SSLInstallerImpl() throws RemoteException{
		
	}
	
	@Override
	public boolean install(String str) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.printf(
				"called id is %s \n", 
			str);
		
		return false;
	}

}
