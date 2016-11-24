package marketplace.repositories;

import common.Item;

import java.util.Collection;

public interface IItemRepository
{
    void addItem(Item item);
    void removeItem(Item item);
    Collection<Item> getAllItems();
    void buyItem(Item item);
    void addWish(Item.Category type);
    void removeWish(Item.Category type);
}
