package common;

import common.rmi.interfaces.Account;

public class User
{
    // Unique key
    private String name;
    private String password;
    private Account bankAccount;

    public User(String name, String password, Account bankAccount)
    {
        this.name = name;
        this.password = password;
        this.bankAccount = bankAccount;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Account getBankAccount()
    {
        return bankAccount;
    }

    public void setBankAccount(Account bankAccount)
    {
        this.bankAccount = bankAccount;
    }
}
