package marketplace;

import java.util.logging.Logger;

public class MarketServer {
    private static final Logger log = Logger.getLogger(MarketServer.class.getName());

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
