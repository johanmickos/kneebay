package marketplace.repositories;

import common.User;
import common.rmi.interfaces.Account;
import marketplace.repositories.exceptions.NotFoundException;

public interface IUserRepository
{
    boolean addUser(String username, String password, Account account);
    User getUser(String username) throws NotFoundException;
    void updateUser(User user);
    boolean removeUser(String username);
}
