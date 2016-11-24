package marketplace.repositories;

import common.rmi.interfaces.MarketClient;
import marketplace.repositories.exceptions.NotFoundException;

import java.util.Map;

public interface IClientRepository
{
    void mapClientToUser(String username, MarketClient client);
    void removeClientFromUser(String username);
    MarketClient getClient(String username) throws NotFoundException;
    Map<String, MarketClient> getAllClients();
}
