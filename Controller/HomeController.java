package App.Controller;

import App.DAO.AppointmentDB;
import App.DAO.CustomerDB;
import App.Helper.Time;
import App.Helper.Utility;
import App.Model.Appointment;
import App.Model.Customer;
import App.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Optional;

public class HomeController {

    public TableView<Customer> custTable = new TableView<>();
    public TableColumn<Customer, Integer> home_custIDCol = new TableColumn<>();
    public TableColumn<Customer, String> home_custNameCol = new TableColumn<>();
    public TableColumn<Customer, String> home_custAddCol = new TableColumn<>();
    public TableColumn<Customer, String> home_custZipCol = new TableColumn<>();
    public TableColumn<Customer, String> home_custStateCol = new TableColumn<>();
    public TableColumn<Customer, String> home_custCountryCol = new TableColumn<>();
    public TableColumn<Customer, String> home_custPhoneCol = new TableColumn<>();

    public TableView<Appointment> apptTable = new TableView<>();
    public TableColumn<Appointment, Integer> home_apptIDCol = new TableColumn<>();
    public TableColumn<Appointment, String> home_apptTitleCol = new TableColumn<>();
    public TableColumn<Appointment, String> home_apptDescCol = new TableColumn<>();
    public TableColumn<Appointment, String> home_apptLocCol = new TableColumn<>();
    public TableColumn<Appointment, String> home_apptContCol = new TableColumn<>();
    public TableColumn<Appointment, String> home_apptTypeCol = new TableColumn<>();
    public TableColumn<Appointment, Timestamp> home_apptStartCol = new TableColumn<>();
    public TableColumn<Appointment, Timestamp> home_apptEndCol = new TableColumn<>();
    public TableColumn<Appointment, Integer> home_apptCustIDCol = new TableColumn<>();
    public TableColumn<Appointment, Integer> home_apptUserIDCol = new TableColumn<>();

    public Button home_updateCustButton;
    public Button home_addCustButton;
    public Button home_delCustButton;
    public Button home_updateApptButton;
    public Button home_addApptButton;
    public Button home_delApptButton;
    public Button home_quitButton;
    public Button home_logoutButton;
    public Button home_reportsButton;
    public RadioButton home_allApptRadio;
    public ToggleGroup apptFilterGroup;
    public RadioButton home_weekApptRadio;
    public RadioButton home_monthApptRadio;

    interface CheckMonth {
        void perform(int apptMonth, int monthNow);
    }

    /**
     * Imports customer and appointment information from database and populates the tables
     * @throws SQLException
     */
    public void initialize() throws SQLException {
        CustomerDB.importCustomers();
        custTable.setItems(Customer.getAllCustomers());
        home_custIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        home_custNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        home_custAddCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        home_custZipCol.setCellValueFactory(new PropertyValueFactory<>("postalcode"));
        home_custStateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
        home_custCountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        home_custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        AppointmentDB.importAppointments();
        apptTable.setItems(Appointment.getAllAppointments());
        home_apptIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        home_apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        home_apptDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        home_apptLocCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        home_apptContCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        home_apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        home_apptStartCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        home_apptEndCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        home_apptCustIDCol.setCellValueFactory(new PropertyValueFactory<>("custId"));
        home_apptUserIDCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

        Utility.appointmentCheck();
    }

    /**
     * Quits the application
     *
     * @param actionEvent
     */
    public void homeQuit(ActionEvent actionEvent)  {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText("Quit application?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                System.exit(0);
            } else {
                alert.close();
            }
        }
    }

    /**
     * Checks if a customer is selected
     * Opens the update customer window
     *
     * @param actionEvent
     * @throws IOException
     */
    public void mainCustUpdate(ActionEvent actionEvent) throws IOException {

        if(custTable.getSelectionModel().getSelectedItem() == null){
            Utility.alertBox("Please select a customer to update.");
        }
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/updateCustomer-view.fxml"));
            Customer selectedCustomer = custTable.getSelectionModel().getSelectedItem();
            UpdateCustomerController.updateCustomer(selectedCustomer);
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            Stage custUpdateScreen = new Stage();
            custUpdateScreen.setTitle("Acme Scheduling - Update Customer");
            custUpdateScreen.initModality(Modality.APPLICATION_MODAL);
            custUpdateScreen.setScene(scene);
            custUpdateScreen.show();
        }
    }

    /**
     * Opens the add customer window
     *
     * @param actionEvent
     * @throws IOException
     */
    public void mainCustAdd(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/addCustomer-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        Stage custAddScreen = new Stage();
        custAddScreen.setTitle("Acme Scheduling - Add Customer");
        custAddScreen.initModality(Modality.APPLICATION_MODAL);
        custAddScreen.setScene(scene);
        custAddScreen.show();
    }

    /**
     * Checks if a customer is selected
     * Tries to deleted the selected customer
     * Confirms if delete was successful
     *
     * @param actionEvent
     * @throws SQLException
     */
    public void mainCustDel(ActionEvent actionEvent) throws SQLException {
        if(custTable.getSelectionModel().getSelectedItem() == null){
            Utility.alertBox("Please select a customer to delete.");
        }
        else {
            Customer selectedCustomer = custTable.getSelectionModel().getSelectedItem();
            String selectedName = selectedCustomer.getName();
            Integer selectedID = selectedCustomer.getId();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("");
            alert.setHeaderText(null);
            alert.setContentText("Delete " + selectedName + "? This will also delete any appointments with " + selectedName + ".");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()) {
                if (result.get() == ButtonType.OK) {
                    Integer apptsDel = AppointmentDB.deleteCustAppt(selectedID);
                    Appointment.clearAppointmentList();
                    AppointmentDB.importAppointments();

                    Integer rowsAffected = CustomerDB.deleteCustomer(selectedID);
                    Customer.clearCustomerList();
                    CustomerDB.importCustomers();

                    if((rowsAffected > 0) & (apptsDel < 1)) {
                        Utility.alertBox(selectedName + " has been successfully deleted.");
                    }
                    if((rowsAffected > 0) & (apptsDel == 1)){
                        Utility.alertBox(selectedName + " and 1 appointment have been successfully deleted.");
                    }
                    if((rowsAffected > 0) & (apptsDel > 1)){
                        Utility.alertBox(selectedName + " and " + apptsDel + " appointments have been successfully deleted.");
                    }
                } else {
                    alert.close();
                }
            }
        }
    }

    /**
     * Checks if an appointment is selected and throws it to the update appointment screen
     * @param actionEvent
     * @throws IOException
     */
    public void mainApptUpdate(ActionEvent actionEvent) throws IOException {
        if(apptTable.getSelectionModel().getSelectedItem() == null){
            Utility.alertBox("Please select an appointment to update.");
        }
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/updateAppt-view.fxml"));
            Appointment selectedAppt = apptTable.getSelectionModel().getSelectedItem();
            UpdateApptController.updateAppt(selectedAppt);
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            Stage apptUpdateScreen = new Stage();
            apptUpdateScreen.setTitle("Acme Scheduling - Update Appointment");
            apptUpdateScreen.initModality(Modality.APPLICATION_MODAL);
            apptUpdateScreen.setScene(scene);
            apptUpdateScreen.show();
        }
    }

    /**
     * Opens the add appointment window
     *
     * @param actionEvent
     * @throws IOException
     */
    public void mainApptAdd(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/addAppt-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        Stage apptAddScreen = new Stage();
        apptAddScreen.setTitle("Acme Scheduling - Add Appointment");
        apptAddScreen.initModality(Modality.APPLICATION_MODAL);
        apptAddScreen.setScene(scene);
        apptAddScreen.show();
    }

    /**
     * Deletes an appointment from the database
     * @param actionEvent
     * @throws SQLException
     */
    public void mainApptDel(ActionEvent actionEvent) throws SQLException {
        if(apptTable.getSelectionModel().getSelectedItem() == null){
            Utility.alertBox("Please select an appointment to delete.");
        }
        else {
            Appointment selectedAppt = apptTable.getSelectionModel().getSelectedItem();
            Integer selectedID = selectedAppt.getAppointmentId();
            String selectedType = selectedAppt.getType();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("");
            alert.setHeaderText(null);
            alert.setContentText("Appointment ID: " + selectedID + System.lineSeparator() + "Appointment type: " + selectedType +
                    System.lineSeparator() + "Delete this appointment?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()) {
                if (result.get() == ButtonType.OK) {
                    Integer rowsAffected = AppointmentDB.deleteAppointment(selectedID);
                    if(apptFilterGroup.getSelectedToggle() == home_weekApptRadio) {
                        Appointment.clearWeekAppointmentList();
                        AppointmentDB.importAppointments();
                        for (Appointment appt : Appointment.getAllAppointments()) {
                            if (appt.getWeek().equals(Time.getWeekNumber(ZonedDateTime.now()))) {
                                Appointment.addWeekAppointment(appt);
                            }
                        }
                        apptTable.setItems(Appointment.getWeekAppointments());
                    }
                    if(apptFilterGroup.getSelectedToggle() == home_monthApptRadio) {
                        Appointment.clearMonthAppointmentList();
                        AppointmentDB.importAppointments();
                        for (Appointment appt : Appointment.getAllAppointments()) {
                            if (appt.getMonth().equals(Time.getMonthNumber(ZonedDateTime.now()))) {
                                Appointment.addMonthAppointment(appt);
                            }
                        }
                        apptTable.setItems(Appointment.getMonthAppointments());
                    }
                    if(apptFilterGroup.getSelectedToggle() == home_allApptRadio){
                        Appointment.clearAppointmentList();
                        AppointmentDB.importAppointments();
                    }
                    if(rowsAffected > 0) {
                        Utility.alertBox("Appointment successfully deleted.");
                    }
                } else {
                    alert.close();
                }
            }
        }

    }

    /**
     * Logs user out
     * Returns to the login screen
     *
     * @param actionEvent
     * @throws IOException
     */
    public void homeLogout(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText("Logout?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {

                String logoffTime = Instant.now().toString();
                File logFile = new File("/src/App/login_activity.txt");
                FileWriter logWrite = new FileWriter("./src/App/login_activity.txt", true);
                logWrite.write("User '" + User.getUser() + "' successfully logged off @ " + logoffTime + " UTC");
                logWrite.write(System.lineSeparator());
                logWrite.close();
                User.setUser("");

                Customer.clearCustomerList();
                ((Stage) (home_logoutButton.getScene().getWindow())).close();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                Stage apptAddScreen = new Stage();
                apptAddScreen.setTitle("Acme Scheduling - Login");
                apptAddScreen.setScene(scene);
                apptAddScreen.show();
            } else {
                alert.close();
            }
        }
    }

    /**
     * Opens the report window
     * @param actionEvent
     * @throws IOException
     */
    public void homeReports(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/report-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        Stage reportScreen = new Stage();
        reportScreen.setTitle("Acme Scheduling - Reports");
        reportScreen.initModality(Modality.APPLICATION_MODAL);
        reportScreen.setScene(scene);
        reportScreen.show();

    }

    /**
     * Displays all appointments in the appointment table
     * @param actionEvent
     * @throws SQLException
     */
    public void allApptRadio(ActionEvent actionEvent) throws SQLException {
        AppointmentDB.importAppointments();
        apptTable.setItems(Appointment.getAllAppointments());
    }

    /**
     * Displays appointments this week in the appointment table
     * @param actionEvent
     */
    public void weekApptRadio(ActionEvent actionEvent) {
        Appointment.clearWeekAppointmentList();
        for (Appointment appt : Appointment.getAllAppointments()) {
            if(appt.getWeek().equals(Time.getWeekNumber(ZonedDateTime.now()))){
                Appointment.addWeekAppointment(appt);
            }
        }
        apptTable.setItems(Appointment.getWeekAppointments());
    }

    /**
     * Displays appointments this month in the appointment table
     * I wrote part of this as a lambda expression because the original code was messy, and this is much easier to understand
     * @param actionEvent
     */
    public void monthApptRadio(ActionEvent actionEvent) {
        Appointment.clearMonthAppointmentList();
        for (Appointment appt : Appointment.getAllAppointments()) {
            int apptMonth = appt.getMonth();
            int monthNow = Time.getMonthNumber(ZonedDateTime.now());

            CheckMonth check = (a, b) -> {
                if (a == b) {
                    Appointment.addMonthAppointment(appt);
                }
            };
            check.perform(apptMonth, monthNow);
        }
        apptTable.setItems(Appointment.getMonthAppointments());
    }
}
