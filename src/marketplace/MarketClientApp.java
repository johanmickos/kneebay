package marketplace;

import common.rmi.interfaces.Account;
import common.rmi.interfaces.Bank;
import common.rmi.interfaces.Marketplace;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import marketplace.gui.controllers.MarketClientController;
import marketplace.gui.controllers.WelcomeController;
import marketplace.rmi.MarketClientImpl;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;

public class MarketClientApp extends Application {
    private static final String MAIN_FXML_LOC = "/main.fxml";
    private static final String WELCOME_FXLM_LOC = "/welcome.fxml";
    private static final String MAIN_CSS_LOC = "/css/market-client.css";

    private Stage stage;
    private MarketClientImpl client;
    private Account account;
    private Bank bank;
    private Marketplace marketplace;
    private BaseController controller;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        gotoWelcome();
        this.stage.show();

        stage.setOnCloseRequest(e -> {
            if (controller != null) {
                    controller.exit();
            }
            Platform.exit();
        });
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

    public void onLogin(String username, String password) throws IOException {
        try {
            MarketClientController controller = (MarketClientController) replaceSceneContent(MAIN_FXML_LOC);
            client = new MarketClientImpl(username);
            client.setController(controller);
            bank = (Bank) Naming.lookup(Bank.DEFAULT_BANK);
            account = bank.getAccount(username);
            marketplace = (Marketplace) Naming.lookup(Marketplace.DEFAULT_MARKETPLACE);
            String session = marketplace.login(username, password, client);
            if (controller != null) {
                controller.updateRmiFields(username, client, account, session, marketplace, bank);
            }
        } catch (Exception ex) {
            WelcomeController controller = (WelcomeController) replaceSceneContent(WELCOME_FXLM_LOC);
            controller.appendError(ex.getMessage());
        }
    }

    public void onRegister(String username, String password) throws IOException {
        try {
            MarketClientController controller = (MarketClientController) replaceSceneContent(MAIN_FXML_LOC);
            client = new MarketClientImpl(username);
            client.setController(controller);
            bank = (Bank) Naming.lookup(Bank.DEFAULT_BANK);
            account = bank.newAccount(username);
            account.deposit(1000);
            marketplace = (Marketplace) Naming.lookup(Marketplace.DEFAULT_MARKETPLACE);
            marketplace.register(username, password, account, client);
            String session = marketplace.login(username, password, client);
            if (controller != null) {
                controller.updateRmiFields(username, client, account, session, marketplace, bank);
            }
        } catch (Exception ex) {
            WelcomeController controller = (WelcomeController) replaceSceneContent(WELCOME_FXLM_LOC);
            controller.appendError(ex.getMessage());
        }
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
            this.controller = controller;
        }
        return controller;
    }
}
