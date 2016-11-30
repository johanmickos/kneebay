package marketplace.repositories;

import common.User;
import common.rmi.interfaces.Account;
import marketplace.repositories.exceptions.NotFoundException;

import java.util.List;

public interface IUserRepository
{
    boolean addUser(String username, String password, Account account);
    User getUser(String username) throws NotFoundException;
    User getUser(String username, String password) throws NotFoundException;
    void updateUser(User user);
    boolean removeUser(String username);
    List<User> getUsers();
}
