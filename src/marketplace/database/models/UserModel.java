package marketplace.database.models;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(
                name = "findUserWithUsername",
                query = "SELECT usr FROM UserModel usr WHERE usr.username LIKE :userName",
                lockMode = LockModeType.OPTIMISTIC
        ),
        @NamedQuery(
                name = "deleteUserWithUsername",
                query = "DELETE FROM UserModel usr WHERE usr.username LIKE :userName",
                lockMode = LockModeType.OPTIMISTIC
        ),
        @NamedQuery(
                name = "getAllUsers",
                query = "SELECT u FROM UserModel u"
        )
})

@Entity
@Table(name = "userTable")
public class UserModel {
    @Id
    private String id;
    private String currentSession; // TODO Check if this is necessary
    private String username;
    private String password; // TODO If we care about security, just store a salted hash
    private Integer numItemsSold;
    private Integer numItemsBought;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(String currentSession) {
        this.currentSession = currentSession;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getNumItemsSold() {
        return numItemsSold;
    }

    public void setNumItemsSold(Integer numItemsSold) {
        this.numItemsSold = numItemsSold;
    }

    public Integer getNumItemsBought() {
        return numItemsBought;
    }

    public void setNumItemsBought(Integer numItemsBought) {
        this.numItemsBought = numItemsBought;
    }
}
