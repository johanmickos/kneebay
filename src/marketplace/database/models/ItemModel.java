package marketplace.database.models;

import common.Item;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(
                name = "getAllAvailableItems",
                query = "SELECT i FROM ItemModel i WHERE i.status = marketplace.database.models.ItemStatus.IN_AUCTION"
        )
})

@Entity
@Table(name="itemTable")
public class ItemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String id;
    //@ManyToOne
    private String buyer;  // Foreign key constraint
    //@ManyToOne
    private String seller;  // Foreign key constraint
    private String name;
    private float price;
    private ItemStatus status;
    private Item.Category category;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
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

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    public Item.Category getCategory()
    {
        return category;
    }

    public void setCategory(Item.Category category)
    {
        this.category = category;
    }

    public static Builder builder() {
        return new ItemModel.Builder();
    }


    public static class Builder {
        private ItemModel instance = new ItemModel();

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
        public Builder category(Item.Category category) {
            instance.category = category;
            return this;
        }
        public Builder buyer(String buyer) {
            instance.buyer = buyer;
            return this;
        }
        public Builder seller(String seller) {
            instance.seller = seller;
            return this;
        }
        public Builder status(ItemStatus status) {
            instance.status = status;
            return this;
        }
        public ItemModel build() {
            return instance;
        }
    }
}
