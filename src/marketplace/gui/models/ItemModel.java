package marketplace.gui.models;

import common.Item;
import javafx.beans.property.*;

public class ItemModel {
    private final SimpleStringProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty seller;
    private final SimpleFloatProperty price;
    private final SimpleObjectProperty<Item.Category> category;

    public ItemModel(String id, String name, float price, Item.Category category, String seller) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.seller = new SimpleStringProperty(seller);
        this.price = new SimpleFloatProperty(price);
        this.category = new SimpleObjectProperty<>(category);
    }

    public void setId(String id) {
        this.id.set(id);
    }
    public String getId() {
        return id.get();
    }
    public void setName(String name) {
        this.name.set(name);
    }
    public String getName() {
        return name.get();
    }
    public void setSeller(String seller) {
        this.seller.set(seller);
    }
    public String getSeller() {
        return seller.get();
    }
    public void setPrice(float price) {
        this.price.set(price);
    }
    public float getPrice() {
        return price.get();
    }
    public void setCategory(Item.Category category) {
        this.category.set(category);
    }
    public Item.Category getCategory() {
        return category.get();
    }
}
