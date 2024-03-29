package common;

import java.io.Serializable;
import java.util.UUID;

public class Item implements Serializable{
    private String id;
    private String name;
    private float price;
    private Category category;
    private String seller;

    //buyer field

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getSeller()
    {
        return seller;
    }

    public void setSeller(String seller)
    {
        this.seller = seller;
    }

    public enum Category {
        Camera,
        Clothing,
        Sport,
        Outdoor,
        Furniture,
        Electronics,
        Miscellaneous,
    }

    private Item() {
        this.id = UUID.randomUUID().toString();
    }

    public static Builder builder() {
        return new Item.Builder();
    }


    public static class Builder {
        private Item instance = new Item();

        public Builder id(String id) {
            instance.id = id;
            return this;
        }
        public Builder name(String name) {
            instance.name = name;
            return this;
        }
        public Builder price(float price) {
            instance.price = price;
            return this;
        }
        public Builder category(Category category) {
            instance.category = category;
            return this;
        }
        public Builder seller(String seller) {
            instance.seller = seller;
            return this;
        }
        public Item build() {
            return instance;
        }
    }

}
