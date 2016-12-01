package marketplace.services;

import common.User;
import common.rmi.interfaces.Account;
import common.rmi.interfaces.Bank;
import marketplace.repositories.IUserRepository;
import marketplace.repositories.UserRepository;
import marketplace.repositories.exceptions.NotFoundException;
import marketplace.security.SessionManagement;

import java.util.List;

public class UserService
{
    private IUserRepository repository;
    private SessionManagement sessionManagement;

    public UserService(UserRepository repository, SessionManagement sessionManagement)
    {
        this.repository = repository;
        this.sessionManagement = sessionManagement;
    }

    public void register(String username, String password, Account account, Bank bank) throws Exception
    {
        if(bank.getAccount(account.getName()) != null)
        {
            if(password.length() < 8)
            {
                throw new Exception("password must be at least eight characters");
            }
            else
            {
                boolean addedUser = this.repository.addUser(username, password, account);
                if(!addedUser)
                {
                    throw new Exception("Account already exists at the marketplace!");
                }
            }
        }
        else
        {
            throw new Exception("Account does not exist at bank!");
        }
    }

    public String login(String username, String password) throws NotFoundException
    {
        // throws NotFoundException if username or password is not correct
        User user = this.repository.getUser(username, password);

        return this.sessionManagement.login(username, password);
    }

    public void logout(String session)
    {
        this.sessionManagement.logout(session);
    }

    public void unregister(String session)
    {
        this.sessionManagement.logout(session);

        //TODO
        //this.repository.removeUser(username);
    }

    public void updateUser(User user)
    {
        this.repository.updateUser(user);
    }

    public User getUser(String session) throws NotFoundException
    {
        String username = this.sessionManagement.getUsernameBySession(session);

        return this.repository.getUser(username);
    }

    public List<User> getUsers()
    {
        return this.repository.getUsers();
    }


}
