package marketplace.database.models;


import javax.persistence.Id;

public class UserModel {
    @Id
    String id;
    String currentSession; // TODO Check if this is necessary
    String username;
    String password; // TODO If we care about security, just store a salted hash
    Integer numItemsSold;
    Integer numItemsBought;
}
