import common.AbstractMessage;
import common.FileMessage;
import common.FileRequest;
import common.FileDeleteRequest;
import common.FileListRequest;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    public TextField Feld;
    @FXML
    ListView<String> filesRList;

    @FXML
    VBox leftPanel, rightPanel;

    public void btnExitAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Network.start();
    }

    public void createTextFild(){
        String str = LoginController.folderName;
        Feld.setText("Server/ServerStorage/" + str);
    }

    //скачиваем с сервака
    public void pressOnDownloadBtn(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        if (filesRList.getSelectionModel().getSelectedItem() != null && !filesRList.getSelectionModel().
                getSelectedItem().equals("")) {
            Network.sendMsg(new FileRequest(filesRList.getSelectionModel().getSelectedItem()));
            AbstractMessage am = Network.readObject();
            if (am instanceof FileMessage) {
                FileMessage fm = (FileMessage) am;
                Files.write(Paths.get("Client/ClientStorage/" + fm.getFileName()), fm.getData(), StandardOpenOption.CREATE);
            }
        }
    }

    // Взяли из логинКонтроллера
    public static void updateUI(Runnable runnable) {
        if (Platform.isFxApplicationThread()) {
            runnable.run();
        } else {
            Platform.runLater(runnable);
        }
    }

    //Обновляем правое окно с файлами с сервака
    public void refreshRemoteFilesList(List<String> serverFileList) {
        updateUI(() -> {
            filesRList.getItems().clear();
            filesRList.getItems().addAll(serverFileList);
        });
        createTextFild();
    }

    //метод обновления правого окна
    public void updatesRList() {
        Network.sendMsg(new FileListRequest());
        AbstractMessage am = null;
        try {
            am = Network.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Не удалось обновить лист");
            alert.showAndWait();
        }
        FileListRequest fileListRequest = (FileListRequest) am;
        System.out.println(fileListRequest.getRemoteFiles());
        refreshRemoteFilesList(fileListRequest.getRemoteFiles());
    }

    //Кнпка обновления правого окна
    public void pressUpdatesRList(ActionEvent actionEvent) {
        updatesRList();
    }

    public void pressOnDeleteBtn(ActionEvent actionEvent) throws InterruptedException {
        if (filesRList.getSelectionModel().getSelectedItem() != null && !filesRList.
                getSelectionModel().
                getSelectedItem().
                equals("")) {
            Network.sendMsg(new FileDeleteRequest(filesRList.getSelectionModel().getSelectedItem()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION,  "Файл: " + filesRList
                    .getSelectionModel()
                    .getSelectedItem() +" успешно удален с сервера");
            alert.showAndWait();
            updatesRList();
        }
    }
}