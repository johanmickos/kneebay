package marketplace.repositories;

import common.Item;
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
        mockDB.items.removeIf(i -> i.getId().equals(item.getId()));
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
