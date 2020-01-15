package service;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MessageAlert {
    static void showMessage(Stage owner, Alert.AlertType type, String header, String text){
        Alert message=new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        message.initOwner(owner);
        message.showAndWait();
    }

    static void showErrorMessage(Stage owner, String text, AnchorPane mainAnchorPane){
        Alert message=new Alert(Alert.AlertType.ERROR);

        DialogPane dialogPane = message.getDialogPane();
        if(!mainAnchorPane.getStylesheets().isEmpty()) {
            dialogPane.getStylesheets().add(MessageAlert.class.getResource("/darkmode.css").toExternalForm());
        }
        else {
            dialogPane.getStylesheets().remove(MessageAlert.class.getResource("/darkmode.css").toExternalForm());

        }

        message.initOwner(owner);
        message.setTitle("Mesaj eroare");
        message.setContentText(text);
        message.showAndWait();
    }
}
