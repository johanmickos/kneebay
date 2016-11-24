package marketplace.repository;

import common.rmi.interfaces.MarketClient;

import java.util.List;
import java.util.Map;

public interface IClientRepository
{
    void mapClientToUser(String username, MarketClient client);
    void removeClientFromUser(String username);
    MarketClient getClient(String username);
    Map<String, MarketClient> getAllClients();
}
