package common;

import java.io.Serializable;

public class ItemWish implements Serializable
{
    private Item.Category type;
    private float maxAmount;
    private User wisher;

    public ItemWish(Item.Category type, float maxAmount)
    {
        this.type = type;
        this.maxAmount = maxAmount;
    }

    public Item.Category getType()
    {
        return type;
    }

    public void setType(Item.Category type)
    {
        this.type = type;
    }

    public float getMaxAmount()
    {
        return maxAmount;
    }

    public void setMaxAmount(float maxAmount)
    {
        this.maxAmount = maxAmount;
    }

    public String displayString() {
        return type + " (max: " + maxAmount + ")";
    }

    public User getWisher()
    {
        return wisher;
    }

    public void setWisher(User wisher)
    {
        this.wisher = wisher;
    }
}
