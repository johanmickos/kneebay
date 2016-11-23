package marketplace;

import bank.RejectedException;
import common.rmi.interfaces.Account;
import common.rmi.interfaces.Bank;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import marketplace.rmi.MarketClientImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class MarketClientController implements Initializable {
    private static final Logger log = Logger.getLogger(MarketClientController.class.getName());

    @FXML
    public Button registerButton;
    @FXML
    public TextField usernameField;
    @FXML
    public TextField fundsField;

    public static final int INITIAL_FUNDS = 1000;

    private MarketClientImpl client;
    private Account account;
    private Bank bank;
    private String bankname = Bank.DEFAULT_BANK;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onRegisterUser(ActionEvent actionEvent) {
        String username = usernameField.getText();

        log.info("Handling user registration for " + username);
        try {
            client = new MarketClientImpl(username);
            bank = (Bank) Naming.lookup(bankname);
            account = bank.newAccount(username);
            account.deposit(INITIAL_FUNDS);
            System.out.println("Connected to bank: " + bankname);
            fundsField.setText(String.valueOf(account.getBalance()));
            // TODO Handle accordingly
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (RejectedException e) {
            e.printStackTrace();
        }
    }
}
