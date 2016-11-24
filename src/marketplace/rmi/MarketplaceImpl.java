package marketplace.rmi;

import common.Item;
import common.ItemWish;
import common.User;
import common.rmi.interfaces.Account;
import common.rmi.interfaces.Bank;
import common.rmi.interfaces.MarketClient;
import common.rmi.interfaces.Marketplace;
import marketplace.repositories.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;

public class MarketplaceImpl extends UnicastRemoteObject implements Marketplace {
    private static final Logger log = Logger.getLogger(MarketplaceImpl.class.getName());

    private Bank bank;
    private String bankname = Bank.DEFAULT_BANK;

    private IUserRepository userRepository;
    private IClientRepository clientRepository;
    private IItemRepository itemRepository;

    public MarketplaceImpl() throws RemoteException, NotBoundException, MalformedURLException
    {
        super();
        bank = (Bank) Naming.lookup(bankname);

        // Here we should be able to switch between mock repo and a real db repo
        this.userRepository = new UserRepository();
        this.clientRepository = new ClientRepository();
        this.itemRepository = new ItemRepository();
    }

    @Override
    public synchronized void register(String username, String password, Account account, MarketClient client) throws RemoteException
    {
        log.info("Registering user: " + username);

        try
        {
            if(bank.getAccount(account.getName()) != null)
            {
                boolean addedUser = this.userRepository.addUser(username, password, account);

                if(addedUser)
                {
                    this.clientRepository.mapClientToUser(username, client);

                    Collection<Item> items = this.itemRepository.getAllItems();
                    client.updateMarketplace(items);
                }
                else
                {
                    client.onException("Account already exists at the marketplace!");
                }
            }
            else
            {
                client.onException("Account does not exist at bank!");
            }
        }
        catch (Exception ex)
        {
            client.onException(ex);
        }

    }

    @Override
    public synchronized void unregister(String username) throws RemoteException
    {
        log.info("Unregistering user: " + username);

        this.userRepository.removeUser(username);
        this.clientRepository.removeClientFromUser(username);
    }

    @Override
    public void addItem(Item item) throws RemoteException
    {
        this.itemRepository.addItem(item);
        updateMarketplaceForAllClients();
    }

    @Override
    public synchronized void removeItem(Item item, String username) throws RemoteException
    {
        if(item.getSeller().equals(username))
        {
            this.itemRepository.removeItem(item);
        }
        else
        {
            try
            {
                MarketClient client = this.clientRepository.getClient(username);
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

    @Override
    public void addWish(ItemWish wish, String username) throws RemoteException
    {
        User user;
        MarketClient userClient = null;

        try
        {
            user = this.userRepository.getUser(username);
            userClient = this.clientRepository.getClient(username);

            user.addWish(wish);
            this.userRepository.updateUser(user);
        }
        catch (Exception ex)
        {
            if(userClient != null)
            {
                userClient.onException(ex);
            }
        }
    }

    @Override
    public void removeWish(ItemWish wish, String username) throws RemoteException
    {
        User user;
        MarketClient userClient = null;

        try
        {
            user = this.userRepository.getUser(username);
            userClient = this.clientRepository.getClient(username);

            user.removeWish(wish);
            this.userRepository.updateUser(user);
        }
        catch (Exception ex)
        {
            if(userClient != null)
            {
                userClient.onException(ex);
            }
        }
    }

    @Override
    public synchronized void buyItem(Item item, String username) throws RemoteException
    {
        User seller;
        MarketClient sellerClient;
        User buyer;
        MarketClient buyerClient = null;

        try
        {
            float itemPrice = item.getPrice();

            seller = this.userRepository.getUser(username);
            sellerClient = this.clientRepository.getClient(item.getSeller());

            buyer = this.userRepository.getUser(username);
            buyerClient = this.clientRepository.getClient(username);

            if(buyer.getBankAccount().getBalance() >= itemPrice)
            {
                buyer.getBankAccount().withdraw(itemPrice);
                seller.getBankAccount().deposit(itemPrice);
                buyerClient.onItemPurchased(item);
                sellerClient.onItemSold(item);
                this.itemRepository.removeItem(item);
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

    private void updateMarketplaceForAllClients() throws RemoteException
    {
        Map<String, MarketClient> clients = this.clientRepository.getAllClients();
        Collection<Item> items = this.itemRepository.getAllItems();

        for (Map.Entry<String, MarketClient> entry : clients.entrySet())
        {
            MarketClient cli = entry.getValue();
            cli.updateMarketplace(items);
        }
    }

}
