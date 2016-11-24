package marketplace.rmi;

import common.Item;
import common.rmi.interfaces.Account;
import common.rmi.interfaces.Bank;
import common.rmi.interfaces.MarketClient;
import common.rmi.interfaces.Marketplace;
import marketplace.repository.*;

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
            MarketClient client = this.clientRepository.getClient(username);
            client.onException("You don't have the authority to remove this item!");
            return;
        }

        updateMarketplaceForAllClients();
    }

    @Override
    public void addWish(Item.Category type) throws RemoteException {

    }

    @Override
    public void removeWish(Item.Category type) throws RemoteException {

    }

    @Override
    public synchronized void buyItem(Item item, String username) throws RemoteException
    {
        Double itemPrice = item.getPrice();

        MarketClient seller = this.clientRepository.getClient(item.getSeller());
        MarketClient buyer = this.clientRepository.getClient(username);

        

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
