package marketplace.services;

import common.Item;
import marketplace.repositories.IItemRepository;
import marketplace.repositories.ItemRepository;

import java.util.Collection;

public class ItemService
{
    private IItemRepository repository;

    public ItemService(IItemRepository repository)
    {
        this.repository = repository;
    }

    public void addItem(Item item)
    {
        this.repository.addItem(item);
    }

    public void removeItem(Item item)
    {
        this.repository.removeItem(item);
    }

    public void markItemAsBought(Item item)
    {
        this.repository.markAsBought(item);
    }

    public Collection<Item> getAllItems()
    {
        return this.repository.getAllAvailableItems();
    }
}
