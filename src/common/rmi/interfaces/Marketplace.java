package common.rmi.interfaces;

import common.Item;
import common.ItemWish;
import common.User;
import marketplace.repositories.exceptions.NotFoundException;
import marketplace.security.exceptions.SessionException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Marketplace extends Remote {
    String DEFAULT_MARKETPLACE = "kneeBay";

    void register(String username, String password, Account account, MarketClient client) throws RemoteException;
    void unregister(String session) throws RemoteException, NotFoundException;
    String login(String username, String password, MarketClient client) throws RemoteException, NotFoundException;
    void logout(String session) throws RemoteException, NotFoundException;

    void addItem(Item item) throws RemoteException;
    void removeItem(Item item, String session) throws RemoteException, SessionException, NotFoundException; // Should verify ownership
    void addWish(ItemWish wish, String session) throws RemoteException, SessionException, NotFoundException;
    void removeWish(ItemWish wish, String session) throws RemoteException, SessionException, NotFoundException;
    void buyItem(Item item, String session) throws RemoteException, SessionException;
    String getActivity(String session) throws RemoteException, SessionException, NotFoundException;

        /*
        Develop a client-server distributed application in Java for trading things (items)
        on a networked marketplace. Clients (traders) and a server (marketplace) communicate
        using Remote Method Invocations (implemented with Java RMI or Java IDL).

        A server represents the marketplace and provides a remote interface that allows clients to
        (un)register at the marketplace, to sell (i.e. to place items for sale) and to buy items,
        and to inspect what items are available on the marketplace. An item is identified by its name
        and price, e.g. "camera" for 3000 SEK. If a client buys an item, the seller should be notified
         by a call-back via the client's remote interface. The server also allows a client to place a
         "wish" to purchase a item for a specified price. When a matching item becomes available at the
         marketplace for the price not higher than the specified wished price, the interested client
         should be notified by a call-back via the client's remote interface.
     */
}
