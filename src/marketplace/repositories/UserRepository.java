package marketplace.repositories;

import common.User;
import common.rmi.interfaces.Account;
import marketplace.database.mockDB;
import marketplace.repositories.exceptions.NotFoundException;

import java.util.List;

public class UserRepository implements IUserRepository
{
    public UserRepository()
    {}

    public boolean addUser(String username, String password, Account account, Integer numItemsBought, Integer numItemsSold)
    {
        if (!isUserUniqueByName(username))
            return false;

        User user = new User(username, password, account, numItemsBought, numItemsSold);
        mockDB.users.add(user);

        return true;
    }

    private boolean isUserUniqueByName(String username)
    {
        return mockDB.users.stream().filter(u -> u.getName().equals(username)).count() == 0;
    }

    public User getUser(String username) throws NotFoundException
    {
        User user = null;

        for (User u : mockDB.users)
        {
            if (u.getName().equals(username))
            {
                user = u;
                break;
            }
        }

        if(user == null)
            throw new NotFoundException("User not found for " + username);

        return user;
    }

    public User getUser(String username, String password) throws NotFoundException
    {
        for (User user : mockDB.users) {
            if (user.getName().equals(username)) {
                if (user.getPassword().equals(password)) {
                    return user;
                } else {
                    throw new NotFoundException("Invalid credentials");
                }
            }
        }
        throw new NotFoundException("User not found");
    }

    public void updateUser(User user)
    {
        try
        {
            User oldUser = getUser(user.getName());
            mockDB.users.remove(oldUser);
            mockDB.users.add(user);
        }
        catch (NotFoundException ex)
        {
            mockDB.users.add(user);
        }
    }

    public boolean removeUser(String username)
    {
        mockDB.users.removeIf(u -> u.getName().equals(username));

        return true;
    }

    public List<User> getUsers()
    {
        return mockDB.users;
    }
}
