package com.example.claimapp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Authentication {
    public GridPane loginPane(Stage stage) {
        // Create a grid pane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(40));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Add labels, text fields, and buttons
        // Add labels, text fields, and buttons
        Label titleLabel = new Label("Claim Management System");
        titleLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;"); // Optional styling
        titleLabel.setAlignment(Pos.CENTER); // Align center

        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-font-size: 15px;"); // Optional styling
        TextField usernameField = new TextField();
        usernameField.setPrefWidth(400);
        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-font-size: 15px;"); // Optional styling
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefWidth(400);
        Button loginButton = new Button("Login");
        loginButton.setPrefWidth(100);
        Button exitButton = new Button("Exit");
        exitButton.setPrefWidth(100);
        exitButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        Label warningLabel = new Label(); // Warning label for displaying error message
        warningLabel.setStyle("-fx-text-fill: red;");


        // Add components to the grid pane
        gridPane.add(titleLabel, 0, 0, 4, 1);
        gridPane.add(usernameLabel, 0, 1);
        gridPane.add(usernameField, 1, 1, 3, 1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordField, 1, 2, 3, 1);
        gridPane.add(loginButton, 3, 3);
        gridPane.add(exitButton, 2, 3);
        gridPane.add(warningLabel, 0, 4, 4, 1);

        GridPane.setHalignment(loginButton, javafx.geometry.HPos.RIGHT);
        Insets buttonMargin = new Insets(0, 10, 0, 0);
        GridPane.setMargin(loginButton, buttonMargin);
        gridPane.setGridLinesVisible(false);

        // Set action for the login button
        loginButton.setOnAction(e -> {
            // Handle login action here
            String username = usernameField.getText();
            String password = passwordField.getText();
            // Validate username and password
            if ("admin".equals(username)) {
                if ("password".equals(password)) {
                    // Open the policy owner menu
                    PolicyOwnerManager policyOwnerManager = new PolicyOwnerManager();
                    stage.setScene(new Scene(policyOwnerManager.policyOwnerMenu(stage), 500, 300));
                } else {
                    warningLabel.setText("Invalid password. Please try again.");
                }
            } else {
                warningLabel.setText("Invalid username. Please try again.");
            }
        });

        exitButton.setOnAction(e -> {
            // Handle exit action here
            System.out.println("Exiting...");
            stage.close();
        });

        // Set alignment of the GridPane to center
        gridPane.setAlignment(Pos.CENTER);
        return gridPane;
    }
}