package com.example.claimapp.Customer;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class DependentManager {

    private Dependent dependent;

    public DependentManager(Dependent dependent) {
        this.dependent = dependent;
    }

    public Scene dependentMenu(Stage stage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));

        Label titleLabel = new Label("Dependent Menu");
        titleLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
        grid.add(titleLabel, 0, 0, 2, 1);

        Button viewClaims = new Button("View Claims");
        Button viewPersonalInfo = new Button("View Personal Info");

        viewClaims.setPrefWidth(250);
        viewPersonalInfo.setPrefWidth(250);

        // Handle view claims button click
        viewClaims.setOnAction(event -> {
            ArrayList<String> claims = dependent.retrieveClaims();
            // Display or process the claims data as needed
            System.out.println("Retrieved claims: " + claims);
        });

        // Handle view personal info button click
        viewPersonalInfo.setOnAction(event -> {
            String personalInfo = dependent.retrievePersonalInfo();
            // Display or process the personal info data as needed
            System.out.println("Personal Information: " + personalInfo);
        });

        grid.add(viewClaims, 0, 1);
        grid.add(viewPersonalInfo, 0, 2);

        return new Scene(grid, 600, 400);
    }
}

