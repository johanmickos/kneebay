package marketplace.services;

import common.rmi.interfaces.MarketClient;
import marketplace.repositories.ClientRepository;
import marketplace.repositories.IClientRepository;
import marketplace.repositories.exceptions.NotFoundException;

import java.util.Map;

public class MarketClientService
{
    private IClientRepository repository;
    public MarketClientService(ClientRepository repository)
    {
        this.repository = repository;
    }

    public void mapMarketClientToUser(String username, MarketClient client)
    {
        this.repository.mapClientToUser(username, client);
    }

    public void removeMarketClientFromUser(String username)
    {
        this.repository.removeClientFromUser(username);
    }

    public MarketClient getClient(String username) throws NotFoundException
    {
        return this.repository.getClient(username);
    }

    public Map<String, MarketClient> getAllMarketClients()
    {
        return this.repository.getAllClients();
    }
}
