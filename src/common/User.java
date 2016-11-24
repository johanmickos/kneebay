package common;

import common.rmi.interfaces.Account;

import java.util.ArrayList;
import java.util.List;

public class User
{
    // Unique key
    private String name;
    private String password;
    private Account bankAccount;
    private List<ItemWish> wishes;

    public User(String name, String password, Account bankAccount)
    {
        this.name = name;
        this.password = password;
        this.bankAccount = bankAccount;
        this.wishes = new ArrayList<>();
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

    public void addWish(ItemWish wish)
    {
        this.wishes.add(wish);
    }

    public void removeWish(ItemWish wish)
    {
        this.wishes.remove(wish);
    }

    public List<ItemWish> getWishes()
    {
        return wishes;
    }
}
