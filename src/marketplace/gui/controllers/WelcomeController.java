package marketplace.gui.controllers;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import marketplace.BaseController;
import marketplace.MarketClientApp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class WelcomeController extends BaseController implements Initializable {
    private static final Logger log = Logger.getLogger(WelcomeController.class.getName());

    @FXML public PasswordField passwordField;
    @FXML public TextField usernameField;
    @FXML public Button loginButton;
    @FXML public Button registerButton;
    @FXML public VBox warnings;

    private MarketClientApp app;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginButton.setOnAction((event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            try {
                app.onLogin(username, password);
            } catch (RemoteException | MalformedURLException e) {
                e.printStackTrace();
                Label warning = new Label(e.getMessage());
                warnings.getChildren().add(warning);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        registerButton.setOnAction((event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            try {
                app.onRegister(username, password);
            } catch (RemoteException | MalformedURLException e) {
                e.printStackTrace();
                Label warning = new Label(e.getMessage());
                warnings.getChildren().add(warning);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

    @Override
    public void setApp(Application app) {
        this.app = (MarketClientApp) app;
        log.info("Setting app");
    }

    @Override
    public void exit() {
        // no-op
    }

    public void appendError(String message) {
        Label warning = new Label(message);
        warnings.getChildren().add(warning);
    }
}
