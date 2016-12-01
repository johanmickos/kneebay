package marketplace.rmi;

import common.Item;
import common.rmi.interfaces.MarketClient;
import marketplace.gui.controllers.MarketClientController;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;

public class MarketClientImpl extends UnicastRemoteObject implements MarketClient {

    private final String username;
    private MarketClientController controller;

    public MarketClientImpl(String username, MarketClientController controller) throws RemoteException {
        super();
        this.username = username;
        this.controller = controller;
    }

    public MarketClientImpl(String username) throws RemoteException {
        super();
        this.username = username;
    }

    public void setController(MarketClientController controller) {
        this.controller = controller;
    }

    @Override
    public void onWishNotify(Item wish) throws RemoteException {
        if (controller != null) controller.onWishNotify(wish);
    }

    @Override
    public void onItemSold(Item item) throws RemoteException {
        if (controller != null) controller.onItemSold(item);
    }

    @Override
    public void onItemPurchased(Item item) throws RemoteException {
        if (controller != null) controller.onItemPurchased(item);
    }

    @Override
    public void onLackOfFunds() throws RemoteException {
        if (controller != null) controller.onLackOfFunds();
    }

    @Override
    public void onException(String data) {
        if (controller != null) controller.onException(data);
    }

    @Override
    public void onException(Exception ex) {
        if (controller != null) controller.onException(ex.getMessage());
    }

    @Override
    public void updateMarketplace(Collection<Item> allItems) throws RemoteException {
        if (controller != null) controller.updateMarketplace(allItems);
    }
}
