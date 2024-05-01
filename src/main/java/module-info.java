module com.example.claimapp {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.claimapp to javafx.fxml;
    exports com.example.claimapp;
}