package src;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {


    @FXML
    private ImageView logoImageView;
    @FXML
    private Button LoginButton;
    @FXML
    private Label registrationMessageLabel;
    @FXML
    private PasswordField setPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label confirmPasswordLabel;
    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private ChoiceBox<String> langChoiceBox;
    @FXML
    private Label langLabel;
    @FXML
    private Label regjistrohuLabel;
    @FXML
    private Label emriLabel;
    @FXML
    private Label mbiemriLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label confirmpassLabel;
    @FXML
    private Button registerButton;

    private String[] gjuha = {"Shqip", "Anglisht"};

    public void initialize(URL url, ResourceBundle resourceBundle) {
        langChoiceBox.getItems().addAll(gjuha);
        langChoiceBox.setOnAction(this::getLang);
    }

    public void getLang(ActionEvent event) {
        if (Objects.equals(langChoiceBox.getValue(), "Anglisht")) {
            langLabel.setText("Choose language");
            regjistrohuLabel.setText("Register");
            emriLabel.setText("First name");
            firstnameTextField.setPromptText("Enter your first name...");
            mbiemriLabel.setText("Last name");
            lastnameTextField.setPromptText("Enter your last name...");
            usernameLabel.setText("Username");
            usernameTextField.setPromptText("Enter your username...");
            passwordLabel.setText("Password");
            setPasswordField.setPromptText("Enter your password");
            confirmpassLabel.setText("Confirm password");
            confirmPasswordField.setPromptText("Re-enter your password");
            registerButton.setText("Register");
            LoginButton.setText("Login");

        } else if (Objects.equals(langChoiceBox.getValue(), "Shqip")) {
            langLabel.setText("Zgjedhni gjuhen");
            regjistrohuLabel.setText("Regjistrohu");
            emriLabel.setText("Emri");
            firstnameTextField.setPromptText("Shkruani emrin...");
            mbiemriLabel.setText("Mbiemri");
            lastnameTextField.setPromptText("Shkruani mbiemrin...");
            usernameLabel.setText("Emri i perdoruesit");
            usernameTextField.setPromptText("Shkruani emrin e perdoruesit...");
            passwordLabel.setText("Password");
            setPasswordField.setPromptText("Shkruani passwordin");
            confirmpassLabel.setText("Konfirmo passwordin");
            confirmPasswordField.setPromptText("Ri-shkruani passwordin");
            registerButton.setText("Regjistrohu");
            LoginButton.setText("Kyçu");
        }
    }

    public void registerButtonOnAction(ActionEvent event) {
        registerUser();
        if (setPasswordField.getText().equals(confirmPasswordField.getText())) {
            confirmPasswordLabel.setText("");

        } else {
            if (Objects.equals(langChoiceBox.getValue(), "Anglisht")) {
                confirmPasswordLabel.setText("Password does not match");
            } else if (Objects.equals(langChoiceBox.getValue(), "Shqip")) {
                confirmPasswordLabel.setText("Passwordi nuk perputhet!");
            }
        }

    }

    public void LoginButtonOnAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));
            Stage loginStage = new Stage();
            loginStage.initStyle(StageStyle.UNDECORATED);
            loginStage.setScene(new Scene(root, 590, 400));
            loginStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }

    public void registerUser() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String firstname = firstnameTextField.getText();
        String lastname = lastnameTextField.getText();
        String username = usernameTextField.getText();
        String password = setPasswordField.getText();

        String insertFields = "INSERT INTO user_account(firstname,lastname,username,password) VALUES ('";
        String insertValues = firstname + "','" + lastname + "','" + username + "','" + password + "');";
        String insertToRegister = insertFields + insertValues;

        if (!firstnameTextField.getText().isBlank() && !lastnameTextField.getText().isBlank() && !usernameTextField.getText().isBlank()
                && !setPasswordField.getText().isBlank() && setPasswordField.getText().equals(confirmPasswordField.getText())) {
            try {

                String selectQuery = "SELECT COUNT(*) FROM user_account WHERE username = '" + username + "'";
                Statement statement = connectDB.createStatement();
                ResultSet resultSet = statement.executeQuery(selectQuery);
                resultSet.next();
                int count = resultSet.getInt(1);

                if (count > 0) {
                    if (Objects.equals(langChoiceBox.getValue(), "Anglisht")) {
                        registrationMessageLabel.setText("Username already exists");
                    } else if (Objects.equals(langChoiceBox.getValue(), "Shqip")) {
                        registrationMessageLabel.setText("Ky emër përdoruesi ekziston tashmë");
                    }
                } else {
                    statement.executeUpdate(insertToRegister);
                    if (Objects.equals(langChoiceBox.getValue(), "Anglisht")) {
                        registrationMessageLabel.setText("User added successfully!");
                    } else if (Objects.equals(langChoiceBox.getValue(), "Shqip")) {
                        registrationMessageLabel.setText("Perdoruesi u shtua me sukses!");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
        } else {
            if (Objects.equals(langChoiceBox.getValue(), "Anglisht")) {
                registrationMessageLabel.setText("Please fill out all details");
            } else if (Objects.equals(langChoiceBox.getValue(), "Shqip")) {
                registrationMessageLabel.setText("Ju lutem mbushni të gjitha të dhënat!");
            }
        }
    }
}
