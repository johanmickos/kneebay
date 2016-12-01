package marketplace.repositories;

import common.Item;
import common.User;
import common.rmi.interfaces.Account;
import marketplace.database.models.UserModel;
import marketplace.repositories.exceptions.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import java.util.List;

public class JPAUserRepository implements IUserRepository
{
    private EntityManagerFactory emFactory;

    public JPAUserRepository(EntityManagerFactory emFactory)
    {
        this.emFactory = emFactory;
    }

    public boolean addUser(
            String username, String password, Account account, Integer numItemsBought, Integer numItemsSold)
    {
        if(isUserUniqueByName(username))
        {
            // Convert item to ItemModel
            EntityManager em = null;
            try
            {
                em = beginTransaction();

                // TODO use builder
                UserModel user = new UserModel();
                user.setUsername(username);
                user.setPassword(password);
                user.setNumItemsBought(numItemsBought);
                user.setNumItemsSold(numItemsSold);
                em.persist(user);

                return true;

            } finally
            {
                commitTransaction(em);
            }
        }
        else
        {
            return false;
        }
    }

    private boolean isUserUniqueByName(String username)
    {
        try
        {
            getUser(username);
            return false;
        }
        catch (NotFoundException ex)
        {
            return true;
        }
    }

    public User getUser(String username) throws NotFoundException
    {
        if (username == null)
        {
            throw new NotFoundException("User not found");
        }

        try
        {
            EntityManager em = emFactory.createEntityManager();

            UserModel userModel = (UserModel) em.createNamedQuery("findUserWithUsername").
                    setParameter("userName", username).getSingleResult();

            if(userModel != null)
            {
                // TODO : use builder
                User user = new User(userModel.getUsername(), userModel.getPassword(), null,
                        userModel.getNumItemsBought(), userModel.getNumItemsSold());

                return user;
            }
            else
            {
                throw new NoResultException();
            }
        }
        catch (NoResultException ex)
        {
            throw new NotFoundException("User not found");
        }
    }

    public User getUser(String username, String password) throws NotFoundException
    {
        User user = getUser(username);

        if(user.getPassword().equals(password))
        {
            return user;
        }
        else
        {
            throw new NotFoundException("Invalid credentials");
        }
    }

    public void updateUser(User user)
    {
        EntityManager em = null;
        try
        {
            em = beginTransaction();
            removeUser(user.getName());
            addUser(user.getName(), user.getPassword(), user.getBankAccount(),
                    user.getNumItemsBought(), user.getNumItemsSold());

        } finally
        {
            commitTransaction(em);
        }
    }

    public boolean removeUser(String username)
    {
        EntityManager em = null;
        try
        {
            em = beginTransaction();

            UserModel userModel = (UserModel) em.createNamedQuery("findUserWithUsername").
                    setParameter("userName", username).getSingleResult();

            em.remove(userModel);

            return true;

        } finally
        {
            commitTransaction(em);
        }
    }

    public List<User> getUsers()
    {
        EntityManager em = emFactory.createEntityManager();
        List<User> users = null;
        List<UserModel> userModels = em.createNamedQuery("getAllAvailableItems").getResultList();

        for (UserModel userModel : userModels)
        {
            users.add(new User(userModel.getUsername(), userModel.getPassword(), null,
                    userModel.getNumItemsBought(), userModel.getNumItemsSold()));
        }

        return users;
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
