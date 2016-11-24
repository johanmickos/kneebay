package common.rmi.interfaces;

import common.Item;
import common.ItemWish;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

public interface MarketClient extends Remote {

    // Notifications
    void onWishNotify(ItemWish wish) throws RemoteException;
    void onItemSold(Item item) throws RemoteException;
    void onItemPurchased(Item item) throws RemoteException;
    void onLackOfFunds() throws RemoteException;

    // TODO void updateFunds(Double balance) throws RemoteException;
    // Could do either from server or in GUI, but only if we display available funds

    void onException(Object data) throws RemoteException;

    void updateMarketplace(Collection<Item> allItems) throws RemoteException;
}
