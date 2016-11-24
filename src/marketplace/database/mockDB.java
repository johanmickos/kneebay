package marketplace.database;

import common.Item;
import common.User;
import common.rmi.interfaces.MarketClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class mockDB
{
    public static List<User> users = new ArrayList<>();
    public static List<Item> items = new ArrayList<>();
    public static Map<String, MarketClient> clients = new HashMap<>();
}
