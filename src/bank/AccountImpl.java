package bank;

import common.rmi.interfaces.Account;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@SuppressWarnings("serial")
public class AccountImpl extends UnicastRemoteObject implements Account {
    private float balance = 0;
    private String name;

    /**
     * Constructs a persistently named object.
     */
    public AccountImpl(String name) throws RemoteException {
        super();
        this.name = name;
    }

    @Override
    public synchronized void deposit(float value) throws RemoteException,
            RejectedException {
        if (value < 0) {
            throw new RejectedException("Rejected: common.rmi.interfaces.Account " + name + ": Illegal value: " + value);
        }
        balance += value;
        System.out.println("Transaction: common.rmi.interfaces.Account " + name + ": deposit: $" + value + ", balance: $"
                + balance);
    }

    @Override
    public synchronized void withdraw(float value) throws RemoteException,
            RejectedException {
        if (value < 0) {
            throw new RejectedException("Rejected: common.rmi.interfaces.Account " + name + ": Illegal value: " + value);
        }
        if ((balance - value) < 0) {
            throw new RejectedException("Rejected: common.rmi.interfaces.Account " + name
                    + ": Negative balance on withdraw: " + (balance - value));
        }
        balance -= value;
        System.out.println("Transaction: common.rmi.interfaces.Account " + name + ": withdraw: $" + value + ", balance: $"
                + balance);
    }

    @Override
    public synchronized float getBalance() throws RemoteException {
        return balance;
    }

    @Override
    public synchronized String getName() throws RemoteException {
        return name;
    }
}