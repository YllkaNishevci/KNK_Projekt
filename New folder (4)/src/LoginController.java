package src;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;
import java.net.URL;
public class LoginController implements Initializable {
    @FXML
    private Button cancelButton;
    @FXML Button RegisterButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private ImageView brandingImageView;
    @FXML
    private ImageView lockImageView;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField enterPasswordField;
    @FXML
    private Button loginButton;
    @FXML
    private Label langLabel;
    @FXML
    private ChoiceBox<String> langChoiceBox;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;

    private String[] gjuha = {"Shqip", "Anglisht"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        langChoiceBox.getItems().addAll(gjuha);
        langChoiceBox.setOnAction(this::getLang);
    }
    public void getLang(ActionEvent event){
        if(Objects.equals(langChoiceBox.getValue(), "Anglisht")){
            langLabel.setText("Choose language");
            usernameLabel.setText("Username");
            usernameTextField.setPromptText("username");
            passwordLabel.setText("Password");
            enterPasswordField.setPromptText("password");
            loginButton.setText("Login");
            RegisterButton.setText("Register");
            cancelButton.setText("Cancel");
        }else if(Objects.equals(langChoiceBox.getValue(), "Shqip")){
            langLabel.setText("Zgjedhni gjuhen");
            usernameLabel.setText("Emri i perdoruesit");
            usernameTextField.setPromptText("emri i perdoruesit");
            passwordLabel.setText("Password");
            enterPasswordField.setPromptText("password");
            loginButton.setText("Kyçu");
            RegisterButton.setText("Regjistrohu");
            cancelButton.setText("Anulo");
        }
    }

    public void loginButtonOnAction(ActionEvent event){
        if(!usernameTextField.getText().isBlank() && !enterPasswordField.getText().isBlank()){
            validateLogin();
        }else {
           if(Objects.equals(langChoiceBox.getValue(), "Anglisht")){
               loginMessageLabel.setText("Please fill out all details!");
            }else if(Objects.equals(langChoiceBox.getValue(), "Shqip")){
         loginMessageLabel.setText("Ju lutem mbushni te dhenat!"); }
        }

   }

    public void cancelButtonOnAction(ActionEvent event){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }
    public void RegisterButtonOnAction(ActionEvent event){
        try{
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("register.fxml")));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root,600,583));
            registerStage.show();
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void validateLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM user_account WHERE username = ? AND password = ?";

        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(verifyLogin);
            preparedStatement.setString(1, usernameTextField.getText());
            preparedStatement.setString(2, enterPasswordField.getText());
            ResultSet queryResult = preparedStatement.executeQuery();

            if (queryResult.next()) {
                int count = queryResult.getInt(1);
                if (count == 1) {
                    createAccountForm();
                } else {
                    if (Objects.equals(langChoiceBox.getValue(), "Anglisht")) {
                        loginMessageLabel.setText("Invalid inputs! Please try again!");
                    } else if (Objects.equals(langChoiceBox.getValue(), "Shqip")) {
                        loginMessageLabel.setText("Te dhena invalide! Provoni perseri!");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void createAccountForm(){
        try{
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home.fxml")));
            Stage homeStage = new Stage();
            homeStage.initStyle(StageStyle.UNDECORATED);
            homeStage.setScene(new Scene(root,824,585));
            homeStage.show();
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
}

