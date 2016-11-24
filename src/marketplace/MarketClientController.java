package marketplace;

import bank.RejectedException;
import common.Item;
import common.rmi.interfaces.Account;
import common.rmi.interfaces.Bank;
import common.rmi.interfaces.Marketplace;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import marketplace.gui.models.ItemModel;
import marketplace.rmi.MarketClientImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class MarketClientController implements Initializable {
    private static final Logger log = Logger.getLogger(MarketClientController.class.getName());

    @FXML public ToggleButton registerButton;
    @FXML public TextField usernameField;
    @FXML public Label fundsLabel;
    @FXML public Label marketplaceLabel;
    @FXML public TextArea logArea;
    @FXML public TableView marketplaceTable;

    public static final int INITIAL_FUNDS = 1000;

    private MarketClientImpl client;
    private Account account;
    private Bank bank;
    private String bankname = Bank.DEFAULT_BANK;

    private Marketplace marketplace;
    private String marketplaceName = Marketplace.DEFAULT_MARKETPLACE;

    private static final String DISCONNECT_STR = "Disconnect";
    private static final String REGISTER_STR   = "\u2007Register\u2007";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        registerButton.setText(REGISTER_STR);

        /*
                        <columns>
                    <TableColumn prefWidth="75.0" text="Price"/>
                    <TableColumn prefWidth="75.0" text="Title"/>
                    <TableColumn prefWidth="75.0" text="Category"/>
                    <TableColumn prefWidth="97.0" text="Description"/>
                    <TableColumn prefWidth="117.0" text="ID"/>
                </columns>
         */

        TableColumn<ItemModel, String> priceCol = new TableColumn<>("Price");
        priceCol.setMinWidth(75);
        priceCol.setCellValueFactory(new PropertyValueFactory<ItemModel, String>("price"));

        TableColumn<ItemModel, String> titleCol = new TableColumn<>("Title");
        titleCol.setMinWidth(75);
        titleCol.setCellValueFactory(new PropertyValueFactory<ItemModel, String>("name"));

        TableColumn<ItemModel, String> catCol = new TableColumn<>("Category");
        catCol.setMinWidth(75);
        catCol.setCellValueFactory(new PropertyValueFactory<ItemModel, String>("category"));

//
//        TableColumn<ItemModel, String> descriptionCol = new TableColumn<>("Description");
//        descriptionCol.setMinWidth(97);
//        descriptionCol.setCellValueFactory(new PropertyValueFactory<ItemModel, String>("description"));

        TableColumn<ItemModel, String> idCol = new TableColumn<>("ID");
        idCol.setMinWidth(117);
        idCol.setCellValueFactory(new PropertyValueFactory<ItemModel, String>("id"));


        marketplaceTable.getColumns().addAll(priceCol, titleCol, catCol, idCol);

    }

    public void onRegisterUser(ActionEvent actionEvent) {
        String username = usernameField.getText();

        log.info("Handling user registration for " + username);
        try {
            client = new MarketClientImpl(username);


            bank = (Bank) Naming.lookup(bankname);
            // TODO Look up marketplace

            account = bank.newAccount(username);
            account.deposit(INITIAL_FUNDS);
            System.out.println("Connected to bank: " + bankname);
            fundsLabel.setText(String.valueOf("$" + account.getBalance()));
            usernameField.setEditable(false);
            registerButton.setText(DISCONNECT_STR);
            marketplaceLabel.setText("Marketplace: " + marketplaceName);
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
    public void onUnregisterUser(ActionEvent actionEvent) {
        // TODO Complete
        log.info("Unregistering user");
        registerButton.setText(REGISTER_STR);
        usernameField.setEditable(true);
    }

    public void onNewWish(ActionEvent actionEvent) {
        log.info("Creating new wish");
    }

    public void onNewItem(ActionEvent actionEvent) {
        log.info("Creating new item");
    }

    public void onToggleRegistration(ActionEvent actionEvent) {
        if (registerButton.isSelected()) {
            onRegisterUser(null);
        } else {
            onUnregisterUser(null);
        }
    }

    public void onFetchUpdates(ActionEvent actionEvent) {
        // TODO This is just a mockup for now
        Collection<Item> updates = new ArrayList<>();
        updates.add(Item.builder().name("Nikon X80").category(Item.Category.Camera).price(829).build());
        updates.add(Item.builder().name("Giant Sofa").category(Item.Category.Furniture).price(1200).build());
        updates.add(Item.builder().name("Jar of Bees").category(Item.Category.Miscellaneaous).price(9).build());
        setMarketplaceTable(updates);
    }

    private void setMarketplaceTable(Collection<Item> items) {
        ObservableList<ItemModel> data = generateItemModels(items);
        marketplaceTable.setItems(data);
    }

    private ObservableList<ItemModel> generateItemModels(Collection<Item> items) {
        ObservableList<ItemModel> data = FXCollections.observableArrayList();
        for (Item it : items) {
            data.add(new ItemModel(it.getId(), it.getName(), it.getPrice(), it.getCategory()));
        }
        return data;
    }
}
