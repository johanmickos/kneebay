package marketplace;

import bank.RejectedException;
import common.rmi.interfaces.Account;
import common.rmi.interfaces.Bank;
import common.rmi.interfaces.Marketplace;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import marketplace.gui.controllers.MarketClientController;
import marketplace.repositories.exceptions.NotFoundException;
import marketplace.rmi.MarketClientImpl;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;

public class MarketClientApp extends Application {
    private static final String MAIN_FXML_LOC = "/main.fxml";
    private static final String WELCOME_FXLM_LOC = "/welcome.fxml";
    private static final String MAIN_CSS_LOC = "/css/market-client.css";

    private Stage stage;
    private MarketClientImpl client;
    private Account account;
    private Bank bank;
    private Marketplace marketplace;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        gotoWelcome();
        this.stage.show();
    }

    private void gotoWelcome() {
        try {
            replaceSceneContent(WELCOME_FXLM_LOC);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void onLogin(String username, String password) throws IOException, NotBoundException, NotFoundException {
        MarketClientController controller = (MarketClientController) replaceSceneContent(MAIN_FXML_LOC);
        client = new MarketClientImpl(username);
        client.setController(controller);
        bank = (Bank) Naming.lookup(Bank.DEFAULT_BANK);
        try {
            bank.newAccount(username);
        } catch (RejectedException e) {
            e.printStackTrace();
        }
        account = bank.getAccount(username);
        marketplace = (Marketplace) Naming.lookup(Marketplace.DEFAULT_MARKETPLACE);
        marketplace.register(username, password, account, client);
        String session = marketplace.login(username, password, client);
        if (controller != null) {
            controller.updateRmiFields(username, client, account, session, marketplace, bank);
        }
    }

    public void onRegister(String username, String password) {

    }


    public void onLogout() throws IOException {
        replaceSceneContent(WELCOME_FXLM_LOC);
    }

    private BaseController replaceSceneContent(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(root);
            scene.getStylesheets().add(MAIN_CSS_LOC);
            stage.setScene(scene);
        } else {
            stage.getScene().setRoot(root);
        }
        stage.sizeToScene();
        BaseController controller = loader.getController();
        if (controller != null) {
            controller.setApp(this);
        }
        return controller;
    }
}
