package marketplace.repository;

import common.User;
import common.rmi.interfaces.Account;
import marketplace.database.mockDB;
import marketplace.repository.exceptions.NotFoundException;

public class UserRepository implements IUserRepository
{
    public UserRepository()
    {}

    public boolean addUser(String username, String password, Account account)
    {
        if (!isUserUniqueByName(username))
            return false;

        User user = new User(username, password, account);
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

}
