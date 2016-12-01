package marketplace.repositories;

import common.Item;
import marketplace.database.models.ItemModel;
import marketplace.database.models.ItemStatus;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

public class JPAItemRepository implements IItemRepository
{
    private EntityManagerFactory emFactory;

    public JPAItemRepository(EntityManagerFactory emFactory)
    {
        this.emFactory = emFactory;
    }

    public void addItem(Item item)
    {
        // Convert item to ItemModel
        EntityManager em = null;
        try
        {
            em = beginTransaction();

            // TODO : use item model builder
            ItemModel itemModel = new ItemModel();
            itemModel.setName(item.getName());
            itemModel.setSeller(item.getSeller());
            itemModel.setPrice(item.getPrice());
            itemModel.setStatus(ItemStatus.IN_AUCTION);
            itemModel.setCategory(item.getCategory());
            em.persist(itemModel);

        } finally
        {
            commitTransaction(em);
        }
    }

    public void removeItem(Item item)
    {
        EntityManager em = null;
        try
        {
            em = beginTransaction();

            ItemModel itemModel = em.find(ItemModel.class, item.getId());

            if(itemModel != null)
            {
                itemModel.setStatus(ItemStatus.REMOVED);
                em.persist(itemModel);
            }

        } finally
        {
            commitTransaction(em);
        }
    }

    public Collection<Item> getAllAvailableItems()
    {
        EntityManager em = emFactory.createEntityManager();
        Collection<Item> items = null;
        List<ItemModel> itemModels = em.createNamedQuery("getAllAvailableItems").getResultList();

        for (ItemModel itemModel : itemModels)
        {
            items.add(new Item.Builder().id(itemModel.getId()).name(itemModel.getName())
                    .price(itemModel.getPrice()).category(itemModel.getCategory())
                    .seller(itemModel.getSeller()).build());
        }

        return items;
    }

    public void markAsBought(Item item)
    {
        EntityManager em = null;
        try
        {
            em = beginTransaction();

            ItemModel itemModel = em.find(ItemModel.class, item.getId());

            if(itemModel != null)
            {
                itemModel.setStatus(ItemStatus.BOUGHT);
                em.persist(itemModel);
            }

        } finally
        {
            commitTransaction(em);
        }
    }

    public void addWish(Item.Category type)
    {
        // Not implemented
    }

    public void removeWish(Item.Category type)
    {
        // Not implemented
    }

    private EntityManager beginTransaction()
    {
        EntityManager em = emFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        return em;
    }

    private void commitTransaction(EntityManager em)
    {
        em.getTransaction().commit();
    }
}
