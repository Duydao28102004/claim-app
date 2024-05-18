package com.example.claimapp;

import com.example.claimapp.Customer.DependentManager;
import com.example.claimapp.Customer.PolicyHolderManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Formattable;
import java.util.Objects;

public class Authentication {
    private String id;
    private String password;
    private String userType;

    public Authentication() {
        this.id = "";
        this.password = "";
        this.userType = "";
    }

    public Authentication(String id, String password, String userType) {
        this.id = id;
        this.password = password;
        this.userType = userType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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
        TextField idField = new TextField();
        idField.setPrefWidth(400);
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
        gridPane.add(idField, 1, 1, 3, 1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordField, 1, 2, 3, 1);
        gridPane.add(loginButton, 3, 3);
        gridPane.add(exitButton, 2, 3);
        gridPane.add(warningLabel, 0, 4, 4, 1);

        GridPane.setHalignment(loginButton, javafx.geometry.HPos.RIGHT);
        Insets buttonMargin = new Insets(0, 10, 0, 0);
        GridPane.setMargin(loginButton, buttonMargin);
        gridPane.setGridLinesVisible(false);
        ArrayList<Authentication> authList = FileManager.authenticationReader();
        // Set action for the login button
        loginButton.setOnAction(e -> {
            // Handle login action here
            String id = idField.getText();
            String password = passwordField.getText();
            for (Authentication authentication : authList) {
                if (authentication.getId().equals(id)) {
                    if (authentication.getPassword().equals(password)) {
                        // Open the policy owner menu
                        if (authentication.getUserType().equals("policyOwner")) {
                            PolicyOwnerManager policyOwnerManager = new PolicyOwnerManager();
                            UserSession.setLoggedInUserId(id);
                            UserSession.getStage().setScene(new Scene(policyOwnerManager.policyOwnerMenu(), 500, 300));
                        } else if (authentication.getUserType().equals("admin")) {
                            AdminManager adminManager = new AdminManager();
                            UserSession.setLoggedInUserId(id);
                            UserSession.getStage().setScene(new Scene(adminManager.adminMenu(), 500, 300));
                        } else if (authentication.getUserType().equals("dependent")) {
                            DependentManager dependentManager = new DependentManager();
                            UserSession.setLoggedInUserId(id);
                            UserSession.getStage().setScene(new Scene(dependentManager.dependentMenu(), 500, 300));
                        } else if (authentication.getUserType().equals("policyHolder")) {
                            PolicyHolderManager policyHolderManager = new PolicyHolderManager();
                            UserSession.setLoggedInUserId(id);
                            UserSession.getStage().setScene(new Scene(policyHolderManager.policyHolderMenu(), 500, 300));
                        } else if (authentication.getUserType().equals("insuranceSurveyor")) {
                            InsuranceSurveyorManager insuranceSurveyorManager = new InsuranceSurveyorManager();
                            insuranceSurveyorManager.insuranceSurveyorMenu();
                        } else if (authentication.getUserType().equals("insuranceManager")) {
                            InsuranceManagerManager insuranceManagerManager = new InsuranceManagerManager();
                            insuranceManagerManager.insuranceManagerMenu();
                        } else {
                            warningLabel.setText("Invalid user type. Please try again.");
                        }
                    } else {
                        warningLabel.setText("Invalid password. Please try again.");
                    }
                } else {
                    warningLabel.setText("Invalid username. Please try again.");
                }
            }
        });

        exitButton.setOnAction(e -> {
            // Handle exit action here
            System.out.println("Exiting...");
            UserSession.getStage().close();
        });

        // Set alignment of the GridPane to center
        gridPane.setAlignment(Pos.CENTER);
        return gridPane;
    }
}
