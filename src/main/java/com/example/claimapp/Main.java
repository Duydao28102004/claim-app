package com.example.claimapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
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
            // Validate username and password
            if ("admin".equals(username) && "password".equals(password)) {
                System.out.println("Login successful!");
                // Open main application window or perform other actions
            } else {
                System.out.println("Invalid username or password. Please try again.");
                // Show error message or perform other actions
            }
        });

        // Set alignment of the GridPane to center
        gridPane.setAlignment(Pos.CENTER);




        // Create scene and set it on the stage
        Scene scene = new Scene(gridPane, 500, 300);
        stage.setTitle("Claim Management System - Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}