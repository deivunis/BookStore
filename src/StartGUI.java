import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class StartGUI extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGUI.class.getResource("view/login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Book store");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) { launch(); }
}
