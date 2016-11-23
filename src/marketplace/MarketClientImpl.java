package marketplace;

import common.Item;
import common.rmi.interfaces.MarketClient;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;

public class MarketClientImpl extends UnicastRemoteObject implements MarketClient {

    protected MarketClientImpl() throws RemoteException {
        super();
    }

    @Override
    public void onWishNotify(Item.ItemType type) throws RemoteException {

    }

    @Override
    public void onItemSold(Item item) throws RemoteException {

    }

    @Override
    public void onItemPurchased(Item item) throws RemoteException {

    }

    @Override
    public void onLackOfFunds() throws RemoteException {

    }

    @Override
    public void onException(Object data) throws RemoteException {

    }

    @Override
    public void updateMarketplace(Collection<Item> allItems) throws RemoteException {

    }
}
