package utils;

import javafx.scene.control.Alert;
import javafx.stage.Modality;

public class WarningMessages {
    public static void alertMessage(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error has occurred");
        alert.setContentText(s);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }
}
