package marketplace.database;

import common.Item;
import common.User;
import common.rmi.interfaces.Account;
import common.rmi.interfaces.MarketClient;

import java.util.*;

public class mockDB
{
    public static List<User> users;
    public static List<Item> items;
    public static Map<String, MarketClient> clients;

    public mockDB()
    {
        this.users = new ArrayList<>();
        this.items = new ArrayList<>();
        this.clients = new HashMap<>();
    }
}
