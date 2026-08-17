module com.example.assignment {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires de.mkammerer.argon2.nolibs;


    opens com.example.assignment to javafx.fxml;
    exports com.example.assignment;
    exports com.example.assignment.controller;
    opens com.example.assignment.controller to javafx.fxml;
    exports com.example.assignment.model;
    opens com.example.assignment.model to javafx.fxml;
}