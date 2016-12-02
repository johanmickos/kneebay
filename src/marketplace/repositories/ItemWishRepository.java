package marketplace.repositories;

import common.Item;
import common.ItemWish;
import common.User;
import marketplace.database.mockDB;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ItemWishRepository implements IItemWishRepository
{
    public ItemWishRepository()
    {
    }

    public Set<User> getAllUsersWishingForItem(Item.Category category, float itemPrice)
    {
        List<ItemWish> wishes = mockDB.wishes.stream().
                filter(u -> u.getType().equals(category) && u.getMaxAmount() >= itemPrice).collect(Collectors.toList());

        Set<User> users = new HashSet<>();

        for (ItemWish wish : wishes)
        {
            users.add(wish.getWisher());
        }

        return users;
    }

    public void addWish(ItemWish wish)
    {
        mockDB.wishes.add(wish);
    }

    public void removeWish(ItemWish wish)
    {
        mockDB.wishes.remove(wish);
    }
}
