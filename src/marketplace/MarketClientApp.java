package marketplace;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MarketClientApp extends Application {
    private static final String MAIN_FXML_LOC = "/main.fxml";
    private static final String MAIN_CSS_LOC = "/css/market-client.css";

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(MAIN_FXML_LOC));
        Scene scene = new Scene(root);
        scene.getStylesheets().clear();
        scene.getStylesheets().add(MAIN_CSS_LOC);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
