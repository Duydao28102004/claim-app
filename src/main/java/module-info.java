module com.example.claimapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;


    opens com.example.claimapp to javafx.fxml;
    exports com.example.claimapp;
}