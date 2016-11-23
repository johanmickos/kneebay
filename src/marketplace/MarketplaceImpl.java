package marketplace;

import common.Item;
import common.rmi.interfaces.Account;
import common.rmi.interfaces.Marketplace;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

public class MarketplaceImpl extends UnicastRemoteObject implements Marketplace {
    private static final Logger log = Logger.getLogger(MarketplaceImpl.class.getName());

    protected MarketplaceImpl() throws RemoteException {
        super();
    }

    @Override
    public void register(Account account) {
        log.info("Registering account: " + account.toString());
    }

    @Override
    public void unregister(Account account) {
        log.info("Unregistering account: " + account.toString());
    }

    @Override
    public void addItem(Item item) throws RemoteException {

    }

    @Override
    public void removeItem(Item item) throws RemoteException {

    }

    @Override
    public void addWish(Item.ItemType type) throws RemoteException {

    }

    @Override
    public void removeWish(Item.ItemType type) throws RemoteException {

    }

    @Override
    public void buyItem(Item item) throws RemoteException {

    }

}
