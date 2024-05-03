package com.example.claimapp;

import com.example.claimapp.Provider.InsuranceManagerController;
import com.example.claimapp.Provider.InsuranceSurveyorController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.function.Consumer;

public class Authentication {
    private Consumer<Scene> loginCallback;

    public void setLoginCallback(Consumer<Scene> loginCallback) {
        this.loginCallback = loginCallback;
    }
    public GridPane loginPane() {
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
        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-font-size: 15px;"); // Optional styling
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        loginButton.setPrefWidth(100);


        // Add components to the grid pane
        gridPane.add(titleLabel, 0, 0, 2, 1);
        gridPane.add(usernameLabel, 0, 1);
        gridPane.add(usernameField, 1, 1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(loginButton, 1, 3);
        GridPane.setHalignment(loginButton, javafx.geometry.HPos.RIGHT);
        Insets buttonMargin = new Insets(0, 10, 0, 0);
        GridPane.setMargin(loginButton, buttonMargin);
        gridPane.setGridLinesVisible(false);

        // Set action for the login button
        loginButton.setOnAction(e -> {
            // Handle login action here
            String username = usernameField.getText();
            String password = passwordField.getText();
            // Execute the login callback with the username
            if ("insurancemanager".equals(username) && "m-password".equals(password)) {
                InsuranceManagerController managerController = new InsuranceManagerController();
                Scene scene = new Scene(managerController.insuranceManagerPane(), 600, 400);
                if (loginCallback != null) {
                    loginCallback.accept(scene);
                }
            } else if ("surveyor".equals(username) && "s-password".equals(password)) {
                InsuranceSurveyorController surveyorController = new InsuranceSurveyorController();
                Scene scene = new Scene(surveyorController.insuranceSurveyorPane(), 600, 400);
                if (loginCallback != null) {
                    loginCallback.accept(scene);
                }
            } else {
                System.out.println("Invalid username or password. Please try again.");
                // Show error message or perform other actions
            }
        });

        // Set alignment of the GridPane to center
        gridPane.setAlignment(Pos.CENTER);
        return gridPane;
    }
}
