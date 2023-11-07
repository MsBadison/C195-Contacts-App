package App;

import App.Model.AppointmentType;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import App.DAO.JDBC;
import java.util.Locale;

/**
 * Creates the application
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("View/main-view.fxml"));

        String country = Locale.getDefault().getDisplayLanguage();
        if (country.equals("français")) {
            primaryStage.setTitle("Acme Scheduling - Connectez-vous");
        }
        else {
            primaryStage.setTitle("Acme Scheduling - Login");
        }

        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    /**
     * The main method that runs when the application starts
     * @param args String[]
     */
    public static void main(String[] args) {
        //Locale.setDefault(new Locale("fr"));
        JDBC.openConnection();

        AppointmentType initial = new AppointmentType("Initial meeting");
        AppointmentType.addAppointmentType(initial);
        AppointmentType followUp = new AppointmentType("Follow up");
        AppointmentType.addAppointmentType(followUp);
        AppointmentType coffee = new AppointmentType("Coffee");
        AppointmentType.addAppointmentType(coffee);
        AppointmentType brunch = new AppointmentType("Brunch");
        AppointmentType.addAppointmentType(brunch);
        AppointmentType lunch = new AppointmentType("Lunch");
        AppointmentType.addAppointmentType(lunch);
        AppointmentType dinner = new AppointmentType("Dinner");
        AppointmentType.addAppointmentType(dinner);
        AppointmentType planning = new AppointmentType("Planning Session");
        AppointmentType.addAppointmentType(planning);
        AppointmentType deBrief = new AppointmentType("De-Briefing");
        AppointmentType.addAppointmentType(deBrief);

        launch(args);
    }
}
