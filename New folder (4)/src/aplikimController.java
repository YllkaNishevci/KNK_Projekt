package src;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Objects;
import java.util.ResourceBundle;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
public class aplikimController implements Initializable {

    @FXML
    private ImageView Fotoja;
    @FXML
    private Label Happy;
    @FXML
    private ChoiceBox<String> langChoiceBox;
    @FXML
    private Button ListButton;
    @FXML
    private Button ApplyButton;
    @FXML
    private TextField Id_S;
    @FXML
    private TextField EmriMbiemri;
    @FXML
    private TextField NumriPersonal;
    @FXML
    private TextField Fakullteti;
    @FXML
    private TextField Drejtimii;
    @FXML
    private TextField Titulli;

    @FXML
    private Label Id_SLabel;
    @FXML
    private Label EmriMbiemriLabel;
    @FXML
    private Label NumriPersonalLabel;
    @FXML
    private Label FakulltetiLabel;
    @FXML
    private Label DrejtimiLabel;
    @FXML
    private Label TitulliLabel;
    @FXML
    private Label confirmApplyLabel;

    private String[] gjuha = {"Shqip", "Anglisht"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        langChoiceBox.getItems().addAll(gjuha);
        langChoiceBox.setOnAction(this::getLang);
    }

    public void getLang(ActionEvent event) {
        if (Objects.equals(langChoiceBox.getValue(), "Anglisht")) {
            Happy.setText("We are happy for your graduation");
            ListButton.setText("See waiting list");
            Id_SLabel.setText("Student's ID");
            EmriMbiemriLabel.setText("First name and Last name");
            NumriPersonalLabel.setText("Personal Number");
            FakulltetiLabel.setText("Faculty");
            DrejtimiLabel.setText("Faculty major");
            TitulliLabel.setText("Diploma title");
            ApplyButton.setText("Apply");
        } else if (Objects.equals(langChoiceBox.getValue(), "Shqip")) {
            Happy.setText("Jemi te gezuar per diplomimin tuaj");
            ListButton.setText("Shiko listen e pritjes");
            Id_SLabel.setText("ID e studentit");
            EmriMbiemriLabel.setText("Emri dhe Mbiemri");
            NumriPersonalLabel.setText("Numri Personal");
            FakulltetiLabel.setText("Fakullteti");
            DrejtimiLabel.setText("Drejtimi");
            TitulliLabel.setText("Titulli i Diplomes");
            ApplyButton.setText("Apliko");

        }
    }

    public void ApplyButtonOnAction(ActionEvent event) {
        AddtoList();
//        if (Objects.equals(langChoiceBox.getValue(), "Anglisht")) {
//            confirmApplyLabel.setText("Your Application is done!");
//        } else if (Objects.equals(langChoiceBox.getValue(), "Shqip")) {
//            confirmApplyLabel.setText("Aplikimi ka perfunduar!");
//        }
    }

    public void dataButtonOnAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("lista.fxml")));
            Stage shihlistenStage = new Stage();
            shihlistenStage.initStyle(StageStyle.UNDECORATED);
            shihlistenStage.setScene(new Scene(root, 827, 565));
            shihlistenStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }


    public void AddtoList() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String id = Id_S.getText();
        String Emri_dhe_Mbiemri = EmriMbiemri.getText();
        String Nr_personal = NumriPersonal.getText();
        String Fakulteti = Fakullteti.getText();
        String Drejtimi = Drejtimii.getText();
        String Titulli_i_temes = Titulli.getText();

        String insertFields = "INSERT INTO apliko(id_s,Emri_dhe_Mbiemri,Nr_personal,Fakulteti,Drejtimi,Titulli_i_temes) VALUES ('";
        String insertValues = id + "','" + Emri_dhe_Mbiemri + "','" + Nr_personal + "','" + Fakulteti + "','" + Drejtimi + "','" + Titulli_i_temes + "')";
        String insertToAplikimi = insertFields + insertValues;
        String selectQuery = "SELECT COUNT(*) FROM apliko WHERE id_s = '" + id + "'";

        if (!Id_S.getText().isBlank() && !EmriMbiemri.getText().isBlank() && !NumriPersonal.getText().isBlank()
                && !Fakullteti.getText().isBlank() && !Drejtimii.getText().isBlank() && !Titulli.getText().isBlank()) {
            try {
                Statement statement = connectDB.createStatement();
                ResultSet resultSet = statement.executeQuery(selectQuery);
                resultSet.next();
                int count = resultSet.getInt(1);

                if (count > 0) {
                    if (Objects.equals(langChoiceBox.getValue(), "Anglisht")) {
                        confirmApplyLabel.setText("You have already applied once!");
                    } else if (Objects.equals(langChoiceBox.getValue(), "Shqip")) {
                        confirmApplyLabel.setText("Ju keni aplikuar tashmë një herë!");
                    }
                } else {
                    statement.executeUpdate(insertToAplikimi);
                    if (Objects.equals(langChoiceBox.getValue(), "Anglisht")) {
                        confirmApplyLabel.setText("Aplication added successfully");
                    } else if (Objects.equals(langChoiceBox.getValue(), "Shqip")) {
                        confirmApplyLabel.setText("Aplikimi u shtua me sukses");
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            if (Objects.equals(langChoiceBox.getValue(), "Anglisht")) {
                confirmApplyLabel.setText("Please fill out all details");
            } else if (Objects.equals(langChoiceBox.getValue(), "Shqip")) {
                confirmApplyLabel.setText("Ju lutem mbushni te gjitha te dhenat!");
            }
        }
    }
}
