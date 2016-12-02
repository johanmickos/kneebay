package marketplace.services;

import common.Item;
import common.ItemWish;
import common.User;
import marketplace.repositories.IItemWishRepository;

import java.util.Set;

public class ItemWishService
{
    private IItemWishRepository repository;

    public ItemWishService(IItemWishRepository repository)
    {
        this.repository = repository;
    }

    public Set<User> getAllUsersWishingForItem(Item.Category category, float itemPrice)
    {
        return this.repository.getAllUsersWishingForItem(category, itemPrice);
    }

    public void addWish(ItemWish wish)
    {
        this.repository.addWish(wish);
    }

    public void removeWish(ItemWish wish)
    {
        this.repository.removeWish(wish);
    }
}
