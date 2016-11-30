package marketplace.database.models;

import javax.persistence.Id;

public class ItemModel {
    @Id
    String id;
    String buyer;  // Foreign key constraint
    String seller;  // Foreign key constraint
    String name;
    float price;
    ItemStatus status;
}
