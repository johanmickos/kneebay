package marketplace.repository;

import bank.Client;
import common.rmi.interfaces.MarketClient;
import marketplace.database.mockDB;
import marketplace.repository.exceptions.NotFoundException;

import java.util.List;
import java.util.Map;

public class ClientRepository implements IClientRepository
{
    public ClientRepository()
    {}

    public void mapClientToUser(String username, MarketClient client)
    {
        MarketClient oldClient = mockDB.clients.get(username);

        if(oldClient != null)
        {
            mockDB.clients.remove(username);
        }

        mockDB.clients.put(username, client);
    }

    public void removeClientFromUser(String username)
    {
        mockDB.clients.remove(username);
    }

    public MarketClient getClient(String username) throws NotFoundException
    {
        MarketClient client = mockDB.clients.get(username);

        if(client == null)
            throw new NotFoundException("Market client not found for " + username);

        return client;
    }

    public Map<String, MarketClient> getAllClients()
    {
        return mockDB.clients;
    }
}
