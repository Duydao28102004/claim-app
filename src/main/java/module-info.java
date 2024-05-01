module com.example.claimapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.sql;


    opens com.example.claimapp to javafx.fxml;
    exports com.example.claimapp;
}