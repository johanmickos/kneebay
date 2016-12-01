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
    private Integer numItemsSold;
    private Integer numItemsBought;

    public User(String name, String password, Account bankAccount, Integer numItemsBought, Integer numItemsSold)
    {
        this.name = name;
        this.password = password;
        this.bankAccount = bankAccount;
        this.wishes = new ArrayList<>();
        this.numItemsBought = numItemsBought;
        this.numItemsSold = numItemsSold;
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

    public Integer getNumItemsSold()
    {
        return numItemsSold;
    }

    public void setNumItemsSold(Integer numItemsSold)
    {
        this.numItemsSold = numItemsSold;
    }

    public Integer getNumItemsBought()
    {
        return numItemsBought;
    }

    public void setNumItemsBought(Integer numItemsBought)
    {
        this.numItemsBought = numItemsBought;
    }
}
