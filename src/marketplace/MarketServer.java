package marketplace;

import common.rmi.interfaces.Marketplace;
import marketplace.rmi.MarketplaceImpl;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Logger;

public class MarketServer
{
    private static final Logger log = Logger.getLogger(MarketServer.class.getName());

    private static final String USAGE = "marketplace.MarketServer <bank_rmi_url>";

    public MarketServer(String marketplace)
    {
        try
        {
            Marketplace marketplaceObject = new MarketplaceImpl();

            // Register the newly created object at rmiregistry.
            try
            {
                log.info("Getting registry");
                LocateRegistry.getRegistry(1099).list();
            }
            catch (RemoteException e)
            {
                log.info("Creating registry");
                LocateRegistry.createRegistry(1099);
            }
            Naming.rebind(marketplace, marketplaceObject);
            System.out.println(marketplaceObject + " is ready.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        if (args.length > 1 || (args.length > 0 && args[0].equalsIgnoreCase("-h")))
        {
            System.out.println(USAGE);
            System.exit(1);
        }

        String marketplace;
        if (args.length > 0)
        {
            marketplace = args[0];
        }
        else
        {
            marketplace = Marketplace.DEFAULT_MARKETPLACE;
        }

        System.out.println("Starting marketplace " + marketplace);
        new MarketServer(marketplace);
    }
}
