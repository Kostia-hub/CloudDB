import common.AuthRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    public static String folderName;
    @FXML
    TextField login;

    @FXML
    VBox globParent;

    @FXML
    Label errorLabel;

    public void auth(ActionEvent actionEvent) throws IOException  {
        Network.start();
        Network.sendMsg(new AuthRequest(login.getText()));
        folderName = login.getText();
        System.out.println(folderName);
        globParent.getScene().getWindow().hide();

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setScene(new Scene(root, 1000, 600));
        newWindow.show(); //show - показ окна
    }
}
