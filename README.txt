My application is called "Acme SCheduling," and its purpose is to allow a company to manage customers and appointments belonging to those customers. Users can add and update both customer information and appointment information. Information is stored in a SQL database, provided as part of the class.

Additionally, users can run reports detailing various types of information related to the saved appointments.  

IDE: IntelliJ IDEA 2021.1.3 (Community Edition)
JDK version: Java SE 17.0.1
JavaFX version: JavaFX-SDK-17.0.1
MySQL Connector driver version: 8.0.25

Upon opening the program, the user is presented with a login screen. When the user successfully logs in, they are presented with the home screen. This screen is divided into two main parts. The top half of the screen contains a list of the customers in the company database. The bottom half of the screen contains a list of appointments. Each section contains a delete, update, and add button. This allows both customers and appointments to be added, modified, and deleted.

Additionally, at the bottom of the window, there are quit, logout, and report buttons. The quit button will close the application. The logout button will return the user to the login screen, where they will have to successfully log in again to use the application. The report button will take the user to a report window, where they can view the number of appointments by month and type, and the appointments belonging to a specific contact. 

I also added a third report, which allows the user to see the number of appopintments at a specific location. This report checks each appointment location against the selected location, and increases the count by 1 every time there is a match. Populating the combobox for selecting the location will ignore any locations already imported, thus preventing duplicates in the list.

Two lamdas have been added to the code:

1. App/Controller/HomeController.java - line 360 in monthApptRadio()
2. App/Controller/ReportController.java - line 108 in contactRun()

