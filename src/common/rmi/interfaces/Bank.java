package common.rmi.interfaces;

import bank.RejectedException;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Bank extends Remote, Serializable {

    public Account newAccount(String name) throws RemoteException, RejectedException;
    public Account getAccount(String name) throws RemoteException;
    public boolean deleteAccount(String name) throws RemoteException;
    public String[] listAccounts() throws RemoteException;
}