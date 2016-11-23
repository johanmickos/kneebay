package bank;

import common.rmi.interfaces.Bank;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Logger;

public class Server {
    private static final Logger log = Logger.getLogger(Server.class.getName());

    private static final String USAGE = "bank.Server <bank_rmi_url>";
    private static final String BANK = "Nordea";

    public Server(String bankName) {
        try {
            Bank bankobj = new BankImpl(bankName);
            // Register the newly created object at rmiregistry.
            try {
                log.info("Getting registry");
                LocateRegistry.getRegistry(1099).list();
            } catch (RemoteException e) {
                log.info("Creating registry");
                LocateRegistry.createRegistry(1099);
            }
            Naming.rebind(bankName, bankobj);
            System.out.println(bankobj + " is ready.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length > 1 || (args.length > 0 && args[0].equalsIgnoreCase("-h"))) {
            System.out.println(USAGE);
            System.exit(1);
        }

        String bankName;
        if (args.length > 0) {
            bankName = args[0];
        } else {
            bankName = BANK;
        }

        System.out.println("Starting bank " + bankName);
        new Server(bankName);
    }
}