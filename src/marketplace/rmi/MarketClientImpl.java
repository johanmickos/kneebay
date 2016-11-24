package marketplace.rmi;

import common.Item;
import common.rmi.interfaces.MarketClient;
import marketplace.MarketClientController;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;

public class MarketClientImpl extends UnicastRemoteObject implements MarketClient {

    private final String username;
    private final MarketClientController controller;

    public MarketClientImpl(String username, MarketClientController controller) throws RemoteException {
        super();
        this.username = username;
        this.controller = controller;
    }

    @Override
    public void onWishNotify(Item wish) throws RemoteException {
        controller.onWishNotify(wish);
    }

    @Override
    public void onItemSold(Item item) throws RemoteException {
        controller.onItemSold(item);
    }

    @Override
    public void onItemPurchased(Item item) throws RemoteException {
        controller.onItemPurchased(item);
    }

    @Override
    public void onLackOfFunds() throws RemoteException {
        controller.onLackOfFunds();
    }

    @Override
    public void onException(String data) throws RemoteException {
        controller.onException(data);
    }

    @Override
    public void onException(Exception ex) throws RemoteException {
        controller.onException(ex.getMessage());
    }

    @Override
    public void updateMarketplace(Collection<Item> allItems) throws RemoteException {
        controller.updateMarketplace(allItems);
    }
}
