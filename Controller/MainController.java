package App.Controller;

import App.DAO.LoginDB;
import App.Helper.Utility;
import App.DAO.JDBC;
import App.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.time.Instant;


public class MainController {
    public TextField main_usernameField;
    public TextField main_passwordField;
    public Button main_cancelButton;
    public Button main_submitButton;
    public Button main_quitButton;
    public Text main_userLocationText;
    public Text main_usernameLabel;
    public Text main_passwordLabel;
    public Text main_userZoneID;
    ResourceBundle rb;


    public void initialize() {

        rb = ResourceBundle.getBundle("App.Localization.Local", Locale.getDefault());

        if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
            main_usernameLabel.setText(rb.getString("username"));
            main_usernameField.setPromptText(rb.getString("username"));
            main_passwordLabel.setText(rb.getString("password"));
            main_passwordField.setPromptText(rb.getString("password"));
            main_submitButton.setText(rb.getString("submit"));
            main_cancelButton.setText(rb.getString("cancel"));
            main_quitButton.setText(rb.getString("quit"));
        }

        ZoneId zone = ZoneId.systemDefault();
        String zoneID = zone.getId();
        main_userZoneID.setText(zoneID);
    }

    /**
     * Clears log in fields
     * @param actionEvent
     */
    public void mainLoginCancel(ActionEvent actionEvent) {
        main_usernameField.setText("");
        main_passwordField.setText("");
    }

    /**
     * Checks the entered log in information is valid and loads the home screen
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    public void mainLoginSubmit(ActionEvent actionEvent) throws IOException, SQLException {
        if(main_usernameField.getText().isEmpty() | main_passwordField.getText().isEmpty()){
            Utility.alertBox(rb.getString("enterPasswordUsername"));
        }
        else {

            String enteredUsername = main_usernameField.getText();
            String enteredPassword = main_passwordField.getText();

            if (!LoginDB.login(enteredUsername, enteredPassword)) {
                Utility.alertBox(rb.getString("loginFail"));
                String logonTime = Instant.now().toString();
                File logFile = new File("../login_activity.txt");
                FileWriter logWrite = new FileWriter("./src/App/login_activity.txt", true);
                logWrite.write("User '" + enteredUsername + "' unsuccessfully tried to log on @ " + logonTime + " UTC");
                logWrite.write(System.lineSeparator());
                logWrite.close();
            }

            else {
                User.setUser(enteredUsername);
                String logonTime = Instant.now().toString();

                File logFile = new File("/src/App/login_activity.txt");
                FileWriter logWrite = new FileWriter("./src/App/login_activity.txt", true);
                logWrite.write("User '" + User.getUser() + "' successfully logged on @ " + logonTime + " UTC");
                logWrite.write(System.lineSeparator());
                logWrite.close();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/home-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                Stage homeScreen = new Stage();
                homeScreen.setTitle("Acme Scheduling - Home");
                homeScreen.setScene(scene);
                ((Stage) main_submitButton.getScene().getWindow()).close();
                homeScreen.show();
            }
        }
    }

    /**
     * Quits the application
     * @param actionEvent
     * @throws IOException
     */
    public void mainQuit(ActionEvent actionEvent) throws IOException {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("");
            alert.setHeaderText(null);
            alert.setContentText(rb.getString("quitConfirmation"));
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()) {
                if (result.get() == ButtonType.OK) {
                    JDBC.closeConnection();
                    System.exit(0);
                } else {
                    alert.close();
                }
            }
    }
}

