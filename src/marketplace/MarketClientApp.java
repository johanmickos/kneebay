package marketplace;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MarketClientApp extends Application {
    private static final String MAIN_FXML_LOC = "/main.fxml";

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(MAIN_FXML_LOC));
        stage.setScene(new Scene(root));
        stage.show();
    }
}
