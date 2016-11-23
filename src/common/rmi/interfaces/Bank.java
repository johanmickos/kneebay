package common.rmi.interfaces;

import bank.RejectedException;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Bank extends Remote, Serializable {
    String DEFAULT_BANK = "Nordea";

    Account newAccount(String name) throws RemoteException, RejectedException;
    Account getAccount(String name) throws RemoteException;
    boolean deleteAccount(String name) throws RemoteException;
    String[] listAccounts() throws RemoteException;
}