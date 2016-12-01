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

    public Collection<Item> getAllAvailableItems()
    {
        return mockDB.items;
    }

    public void markAsBought(Item item)
    {

    }

    public void addWish(Item.Category type)
    {

    }

    public void removeWish(Item.Category type)
    {

    }
}
