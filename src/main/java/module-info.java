module com.example.assignment {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires de.mkammerer.argon2.nolibs;


    opens com.geraj.assignment to javafx.fxml;
    exports com.geraj.assignment;
    exports com.geraj.assignment.controller;
    opens com.geraj.assignment.controller to javafx.fxml;
    exports com.geraj.assignment.model;
    opens com.geraj.assignment.model to javafx.fxml;
}