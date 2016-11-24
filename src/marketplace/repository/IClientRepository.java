package marketplace.repository;

import common.rmi.interfaces.MarketClient;
import marketplace.repository.exceptions.NotFoundException;

import java.util.List;
import java.util.Map;

public interface IClientRepository
{
    void mapClientToUser(String username, MarketClient client);
    void removeClientFromUser(String username);
    MarketClient getClient(String username) throws NotFoundException;
    Map<String, MarketClient> getAllClients();
}
