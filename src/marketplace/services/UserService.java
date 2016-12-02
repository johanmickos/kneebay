package marketplace.services;

import common.User;
import common.rmi.interfaces.Account;
import common.rmi.interfaces.Bank;
import marketplace.repositories.IUserRepository;
import marketplace.repositories.UserRepository;
import marketplace.repositories.exceptions.NotFoundException;
import marketplace.repositories.exceptions.RegistrationException;
import marketplace.security.SessionManagement;

import java.util.List;

public class UserService
{
    private IUserRepository repository;
    private SessionManagement sessionManagement;

    public UserService(IUserRepository repository, SessionManagement sessionManagement)
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
                throw new RegistrationException("Password must be at least 8 chars");
            }
            else
            {
                boolean addedUser = this.repository.addUser(username, password, account, 0, 0);
                if(!addedUser)
                {
                    throw new RegistrationException("Account already exists at market!");
                }
            }
        }
        else
        {
            throw new RegistrationException("Account does not exist at bank!");
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
        this.repository.removeUser(session);
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
    public User getUserByUsername(String username) throws NotFoundException {
        return repository.getUser(username);
    }

    public List<User> getUsers()
    {
        return this.repository.getUsers();
    }

}
