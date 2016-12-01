package marketplace.rmi;

import common.Item;
import common.ItemWish;
import common.User;
import common.rmi.interfaces.Account;
import common.rmi.interfaces.Bank;
import common.rmi.interfaces.MarketClient;
import common.rmi.interfaces.Marketplace;
import marketplace.database.models.ItemModel;
import marketplace.database.models.ItemStatus;
import marketplace.repositories.*;
import marketplace.repositories.exceptions.NotFoundException;
import marketplace.security.SessionManagement;
import marketplace.security.exceptions.SessionException;
import marketplace.services.ItemService;
import marketplace.services.MarketClientService;
import marketplace.services.UserService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class MarketplaceImpl extends UnicastRemoteObject implements Marketplace {
    private static final Logger log = Logger.getLogger(MarketplaceImpl.class.getName());

    private Bank bank;
    private String bankname = Bank.DEFAULT_BANK;

    private UserService userService;
    private MarketClientService marketClientService;
    private ItemService itemService;

    private SessionManagement sessionManagement;

    private EntityManagerFactory emFactory;

    public MarketplaceImpl() throws RemoteException, NotBoundException, MalformedURLException
    {
        super();
        this.emFactory = Persistence.createEntityManagerFactory("marketplace");
        this.bank = (Bank) Naming.lookup(bankname);

        this.sessionManagement = new SessionManagement();
        this.userService = new UserService(new JPAUserRepository(this.emFactory), this.sessionManagement);
        this.marketClientService = new MarketClientService(new ClientRepository());
        this.itemService = new ItemService(new JPAItemRepository(this.emFactory));
    }

    @Override
    public synchronized void register(String username, String password, Account account, MarketClient client) throws RemoteException
    {
        try
        {
            log.info("Registering user: " + username);
            this.userService.register(username, password, account, bank);
            // TODO : let client know of succesful registration?
        }
        catch (Exception ex)
        {
            client.onException(ex);
        }
    }

    @Override
    public synchronized void unregister(String session) throws RemoteException, NotFoundException
    {
        log.info("Unregistering user: " + session);

        this.userService.unregister(session);
        this.marketClientService.removeMarketClientFromUser(this.userService.getUser(session).getName());
    }

    @Override
    public String login(String username, String password, MarketClient client) throws RemoteException, NotFoundException
    {
        // throws a NotFoundException if login fails
        String session = this.userService.login(username, password);
        this.marketClientService.mapMarketClientToUser(username, client);

        Collection<Item> items = this.itemService.getAllItems();
        client.updateMarketplace(items);

        return session;
    }

    @Override
    public void logout(String session) throws RemoteException, NotFoundException
    {
        this.marketClientService.removeMarketClientFromUser(this.userService.getUser(session).getName());
        this.userService.logout(session);
    }

    @Override
    public void addItem(Item item) throws RemoteException
    {
        this.itemService.addItem(item);
        updateMarketplaceForAllClients();

        // TODO : change this to table of wishes, that can be looked up by type and user
        List<User> users = this.userService.getUsers();
        for (User user : users)
        {
            try
            {
                MarketClient userClient = this.marketClientService.getClient(user.getName());
                for (ItemWish wish : user.getWishes())
                {
                    if(wish.getType().equals(item.getCategory()) && wish.getMaxAmount() >= item.getPrice())
                    {
                        userClient.onWishNotify(item);
                    }
                }
            }
            catch (NotFoundException ex)
            {
                continue;
            }
        }
    }

    @Override
    public synchronized void removeItem(Item item, String session)
            throws RemoteException, SessionException, NotFoundException
    {
        if(this.sessionManagement.isValidSession(session))
        {
            String username = this.userService.getUser(session).getName();

            if(item.getSeller().equals(username))
            {
                this.itemService.removeItem(item);
            }
            else
            {
                try
                {
                    MarketClient client = this.marketClientService.getClient(username);
                    client.onException("You don't have the authority to remove this item!");
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }

                return;
            }

            updateMarketplaceForAllClients();
        }
        else
        {
            throw new SessionException("Invalid session");
        }
    }

    @Override
    public synchronized void addWish(ItemWish wish, String session)
            throws RemoteException, SessionException, NotFoundException
    {
        if(this.sessionManagement.isValidSession(session))
        {
            User user = this.userService.getUser(session);
            user.addWish(wish);
            this.userService.updateUser(user);
        }
        else
        {
            throw new SessionException("Invalid session");
        }
    }

    @Override
    public synchronized void removeWish(ItemWish wish, String session)
            throws RemoteException, SessionException, NotFoundException
    {
        if(this.sessionManagement.isValidSession(session))
        {
            User user = this.userService.getUser(session);
            user.removeWish(wish);
            this.userService.updateUser(user);
        }
        else
        {
            throw new SessionException("Invalid session");
        }
    }

    @Override
    public synchronized void buyItem(Item item, String session)
            throws RemoteException, SessionException
    {
        if(this.sessionManagement.isValidSession(session))
        {
            User seller;
            MarketClient sellerClient;
            User buyer;
            MarketClient buyerClient = null;

            try
            {
                float itemPrice = item.getPrice();

                seller = this.userService.getUserByUsername(item.getSeller());
                sellerClient = this.marketClientService.getClient(item.getSeller());

                buyer = this.userService.getUser(session);
                buyerClient = this.marketClientService.getClient(buyer.getName());

                if(buyer.getBankAccount().getBalance() >= itemPrice)
                {
                    buyer.getBankAccount().withdraw(itemPrice);
                    seller.getBankAccount().deposit(itemPrice);

                    //TODO
                    //seller.numberOfItemsSold++;
                    //buyer.numberOfItemsBought++;

                    buyerClient.onItemPurchased(item);
                    sellerClient.onItemSold(item);
                    this.itemService.markItemAsBought(item);

                    updateMarketplaceForAllClients();
                }
                else
                {
                    buyerClient.onLackOfFunds();
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();

                if(buyerClient != null)
                {
                    buyerClient.onException(ex);
                }
            }
        }
        else
        {
            throw new SessionException("Invalid session");
        }
    }

    @Override
    public String getActivity(String session)
            throws RemoteException, SessionException, NotFoundException
    {
        // For each user, the server (marketplace) keeps a record of user's activities:
        // the total number of items the user has bought and the total number of items the user has sold.

        if(this.sessionManagement.isValidSession(session))
        {
            User user = this.userService.getUser(session);

            return "Total number of items bought: "
                    + user.getNumItemsBought()
                    + "\n"
                    + "Total number of items sold: "
                    + user.getNumItemsSold();
        }
        else
        {
            throw new SessionException("Invalid session");
        }
    }

    private void updateMarketplaceForAllClients() throws RemoteException
    {
        Map<String, MarketClient> clients = this.marketClientService.getAllMarketClients();
        Collection<Item> items = this.itemService.getAllItems();

        for (Map.Entry<String, MarketClient> entry : clients.entrySet())
        {
            MarketClient cli = entry.getValue();
            cli.updateMarketplace(items);
        }
    }

}
