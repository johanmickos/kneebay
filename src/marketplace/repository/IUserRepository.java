package marketplace.repository;

import common.User;
import common.rmi.interfaces.Account;

public interface IUserRepository
{
    boolean addUser(String username, String password, Account account);
    User getUser(String username);
    boolean removeUser(String username);
}
