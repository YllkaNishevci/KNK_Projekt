package src;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Home implements Initializable {
    @FXML
    private Button logoutButton;
    @FXML
    private Label langLabel;
    @FXML
    private ChoiceBox<String> langChoiceBox;
    @FXML
    private Label aplikoLabel;
    @FXML
    private Button aplikoButton;
    @FXML
    private Label dataLabel;
    @FXML
    private Button dataButton;
    private String[] gjuha = {"Shqip", "Anglisht"};
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        langChoiceBox.getItems().addAll(gjuha);
        langChoiceBox.setOnAction(this::getLang);
    }
    public void getLang(ActionEvent event){
        if(Objects.equals(langChoiceBox.getValue(), "Anglisht")){
            logoutButton.setText("LogOut");
            langLabel.setText("Choose language");
            aplikoLabel.setText("Set the date for graduation");
            aplikoButton.setText("Apply");
            dataLabel.setText("See available dates");
            dataButton.setText("See the list");
        } else if(Objects.equals(langChoiceBox.getValue(), "Shqip")){
            logoutButton.setText("Dilni");
            langLabel.setText("Zgjedhni gjuhen");
            aplikoLabel.setText("Cakto daten per marrjen e diplomes");
            aplikoButton.setText("Apliko");
            dataLabel.setText("Shih datat e lira");
            dataButton.setText("Shih listen");
        }
    }

// KeyCombination combination = new KeyCodeCombination(KeyCode.F4, KeyCodeCombination.SHIFT_ANY);
//    public void LogoutButtonOnKeyPressed(ActionEvent event){
//       logoutButton.setOnKeyPressed((KeyEvent event1) -> {
//        if (combination.match(event1)) {
//                LogoutButtonOnAction(event);
//            }
//       });
//
//    }
    public void LogoutButtonOnAction(ActionEvent event){

                Stage stage = (Stage) logoutButton.getScene().getWindow();
                stage.close();
                Platform.exit();
            }

    public void aplikoButtonOnAction(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("aplikimi.fxml")));
            Stage aplikoStage = new Stage();
            aplikoStage.initStyle(StageStyle.UNDECORATED);
            aplikoStage.setScene(new Scene(root, 827, 565));
            aplikoStage.show();
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    public void dataButtonOnAction(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("lista.fxml")));
            Stage shihlistenStage = new Stage();
            shihlistenStage.initStyle(StageStyle.UNDECORATED);
            shihlistenStage.setScene(new Scene(root, 827, 565));
            shihlistenStage.show();
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    }


