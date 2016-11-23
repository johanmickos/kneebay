package marketplace.repository;

import common.Item;
import common.rmi.interfaces.Bank;
import common.rmi.interfaces.MarketClient;
import marketplace.database.mockDB;

import java.util.Collection;

public class ItemRepository implements IItemRepository
{
    public ItemRepository()
    {
    }

    public void addItem(Item item)
    {
        mockDB.items.add(item);
    }

    public void removeItem(Item item)
    {
        mockDB.items.remove(item);
    }

    public Collection<Item> getAllItems()
    {
        return mockDB.items;
    }

    public void buyItem(Item item)
    {

    }

    public void addWish(Item.Category type)
    {

    }

    public void removeWish(Item.Category type)
    {

    }
}
