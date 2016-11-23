package marketplace.gui.models;

import common.Item;
import javafx.beans.property.*;

public class ItemModel {
    private final SimpleStringProperty id;
    private final SimpleStringProperty name;
    private final SimpleDoubleProperty price;
    private final SimpleObjectProperty<Item.Category> category;

    public ItemModel(String id, String name, Double price, Item.Category category) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
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
    public void setPrice(Double price) {
        this.price.set(price);
    }
    public Double getPrice() {
        return price.get();
    }
    public void setCategory(Item.Category category) {
        this.category.set(category);
    }
    public Item.Category getCategory() {
        return category.get();
    }
}
