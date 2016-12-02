package marketplace.repositories;

import common.Item;
import common.ItemWish;
import common.User;

import java.util.List;
import java.util.Set;

public interface IItemWishRepository
{
    Set<User> getAllUsersWishingForItem(Item.Category category, float itemPrice);
    void addWish(ItemWish wish);
    void removeWish(ItemWish wish);
}
