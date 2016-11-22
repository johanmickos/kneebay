package marketplace;

import java.rmi.Remote;

public interface Marketplace extends Remote {
    public void register(Account account);
    public void unregister(Account account);
}
