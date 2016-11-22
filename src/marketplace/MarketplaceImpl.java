package marketplace;

import java.util.logging.Logger;

public class MarketplaceImpl implements Marketplace {
    private static final Logger log = Logger.getLogger(MarketplaceImpl.class.getName());

    @Override
    public void register(Account account) {
        log.info("Registering account: " + account.toString());
    }

    @Override
    public void unregister(Account account) {
        log.info("Unregistering account: " + account.toString());
    }
}
