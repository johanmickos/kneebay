package common;

public class ItemWish
{
    private Item.Category type;
    private float maxAmount;

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
}
