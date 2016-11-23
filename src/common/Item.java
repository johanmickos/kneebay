package common;

import java.util.UUID;

public class Item {
    private String id;
    private String name;
    private Double price;
    private Category category;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public enum Category {
        Camera,
        Furniture,
        Electronics,
        Miscellaneaous,
    }

    private Item() {
        this.id = UUID.randomUUID().toString();
    }

    public static Builder builder() {
        return new Item.Builder();
    }


    public static class Builder {
        private Item instance = new Item();

        public Builder name(String name) {
            instance.name = name;
            return this;
        }
        public Builder price(Double price) {
            instance.price = price;
            return this;
        }
        public Builder category(Category category) {
            instance.category = category;
            return this;
        }
        public Item build() {
            return instance;
        }
    }

}
