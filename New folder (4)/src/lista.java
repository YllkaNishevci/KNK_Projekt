package src;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.Chart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class lista implements Initializable {
    @FXML
    private Button logoutButton;
    @FXML
    private Button gobackButton;
    @FXML
    private TableView<tabela> table;
    @FXML
    private TableColumn<tabela, String> id;
    @FXML
    private TableColumn<tabela, String> emrimbiemri;
    @FXML
    private TableColumn<tabela, String> nrpersonal;
    @FXML
    private TableColumn<tabela, String> fakulteti;
    @FXML
    private TableColumn<tabela, String> drejtimi;
    @FXML
    private TableColumn<tabela, String> titullitemes;
    @FXML
    private TableColumn<tabela, String> DataEaplikimit;
    @FXML
    private Chart chart;
    @FXML
    private Label dataembrojtjes;

    @FXML
    private Button shikodaten;

    ObservableList<tabela> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        try {
            ResultSet rs = connectDB.createStatement().executeQuery("SELECT * FROM apliko;");
            while (rs.next()) {
                oblist.add(new tabela(rs.getString("id_s"), rs.getString("Emri_dhe_Mbiemri"), rs.getString("Nr_personal"),
                        rs.getString("Fakulteti"), rs.getString("Drejtimi"), rs.getString("Titulli_i_temes"), rs.getString("DataEaplikimit")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        emrimbiemri.setCellValueFactory(new PropertyValueFactory<>("Emri_dhe_Mbiemri"));
        nrpersonal.setCellValueFactory(new PropertyValueFactory<>("Nr_personal"));
        fakulteti.setCellValueFactory(new PropertyValueFactory<>("Fakulteti"));
        drejtimi.setCellValueFactory(new PropertyValueFactory<>("Drejtimi"));
        titullitemes.setCellValueFactory(new PropertyValueFactory<>("Titulli_i_temes"));
        DataEaplikimit.setCellValueFactory(new PropertyValueFactory<>("DataEaplikimit"));
        table.setItems(oblist);
    }

    public void shikodatenButtonOnAction(ActionEvent event) {
        try {
            String dateAfter = calculatedate();
            dataembrojtjes.setText("The date is: " + dateAfter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logoutButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    public void gobackButtonOnAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home.fxml")));
            Stage homeStage = new Stage();
            homeStage.initStyle(StageStyle.UNDECORATED);
            homeStage.setScene(new Scene(root, 827, 565));
            homeStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String calculatedate() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        Date date = new Date();
        String str = new SimpleDateFormat("dd/MM/yy").format(date);

        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(new SimpleDateFormat("dd/MM/yy").parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        cal.add(Calendar.DAY_OF_MONTH, 40);
        String dateAfter = new SimpleDateFormat("dd/MM/yy").format(cal.getTime());
        return dateAfter;
    }
}
