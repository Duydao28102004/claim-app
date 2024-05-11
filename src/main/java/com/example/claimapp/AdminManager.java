package com.example.claimapp;

import com.example.claimapp.Customer.PolicyHolder;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Formattable;

public class AdminManager {
    public GridPane adminMenu() {
        Label titleLabel = new Label("Admin Menu");
        titleLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
        titleLabel.setAlignment(Pos.CENTER);
        Button createPolicyOwnerButton = new Button("Create Policy Owner");
        createPolicyOwnerButton.setPrefWidth(250);
        createPolicyOwnerButton.setAlignment(Pos.CENTER);
        Button createPolicyHolderButton = new Button("Create Policy Holder");
        createPolicyHolderButton.setPrefWidth(250);
        createPolicyHolderButton.setAlignment(Pos.CENTER);
        Button createDependentButton = new Button("Create Dependent");
        createDependentButton.setPrefWidth(250);
        createDependentButton.setAlignment(Pos.CENTER);
        Button viewPolicyOwnersButton = new Button("View Policy Owners");
        viewPolicyOwnersButton.setPrefWidth(250);
        viewPolicyOwnersButton.setAlignment(Pos.CENTER);
        Button viewPolicyHoldersButton = new Button("View Policy Holders");
        viewPolicyHoldersButton.setPrefWidth(250);
        viewPolicyHoldersButton.setAlignment(Pos.CENTER);
        Button viewDependentsButton = new Button("View Dependents");
        viewDependentsButton.setPrefWidth(250);
        viewDependentsButton.setAlignment(Pos.CENTER);
        javafx.scene.control.Button logout = new javafx.scene.control.Button("Logout");
        logout.setPrefWidth(250);
        logout.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        logout.setOnAction( e -> {
            // Handle logout action here
            System.out.println("Logging out...");
            UserSession.getStage().setScene(new Scene(new Authentication().loginPane(), 500, 300));
        });

        // Create a grid pane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);

        // Add labels and fields to the grid pane
        gridPane.add(titleLabel, 0, 0);
        gridPane.add(createPolicyOwnerButton, 0, 1);
        gridPane.add(createPolicyHolderButton, 0, 2);
        gridPane.add(createDependentButton, 0, 3);
        gridPane.add(viewPolicyOwnersButton, 0, 4);
        gridPane.add(viewPolicyHoldersButton, 0, 5);
        gridPane.add(viewDependentsButton, 0, 6);
        gridPane.add(logout, 0, 7);

        createPolicyOwnerButton.setOnAction(e -> createPolicyOwner());
        createPolicyHolderButton.setOnAction(e -> createPolicyHolder());

        return gridPane;
    }
    public void createPolicyOwner() {
        ArrayList<PolicyOwner> policyOwners = FileManager.policyOwnerReader();
        ArrayList<Authentication> authentications = FileManager.authenticationReader();

        Label idLabel = new Label("ID (po-xxxxxx): ");
        Label passwordLabel = new Label("Password: ");
        Label fullNameLabel = new Label("Full Name: ");

        TextField idField = new TextField();
        TextField passwordField = new TextField();
        TextField fullNameField = new TextField();


        // Create a grid pane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);

        // Add labels and fields to the grid pane
        gridPane.add(idLabel, 0, 0);
        gridPane.add(idField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(fullNameLabel, 0, 2);
        gridPane.add(fullNameField, 1, 2);

        // Add a button to the grid pane
        GridPane buttons = new GridPane();
        Button submitButton = new Button("Submit");
        buttons.add(submitButton, 1, 0);

        Button exitButton = new Button("Exit");
        buttons.add(exitButton, 0, 0);

        gridPane.add(buttons, 1, 3);

        exitButton.setOnAction(e -> UserSession.getStage().setScene(new Scene(adminMenu(), 500, 300)));

        submitButton.setOnAction(e -> {
            String enteredId = idField.getText().trim();
            // Check if the entered ID format is correct
            if (!enteredId.matches("^po-[a-zA-Z0-9]{6}$")) {
                // If the format is incorrect, show a warning
                System.out.println("Incorrect ID Format");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Incorrect ID Format");
                alert.setHeaderText(null);
                alert.setContentText("Please enter the ID in the format po-xxxxxx.");
                alert.showAndWait();
            } else if (isIdPoExists(policyOwners, enteredId)) {
                // If the ID exists, show a warning
                System.out.println("ID Already Exists");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ID Already Exists");
                alert.setHeaderText(null);
                alert.setContentText("The entered ID already exists. Please enter a different ID.");
                alert.showAndWait();
            } else {
                PolicyOwner newPolicyOwner = new PolicyOwner(enteredId, fullNameField.getText());
                policyOwners.add(newPolicyOwner);
                authentications.add(new Authentication(enteredId, passwordField.getText(), "policyOwner"));
                FileManager.policyOwnerWriter(policyOwners);
                FileManager.authenticationWriter(authentications);
                UserSession.getStage().setScene(new Scene(adminMenu(), 400, 200));
            }
        });
        UserSession.getStage().setScene(new Scene(gridPane, 400, 200));
    }
    private boolean isIdPoExists(ArrayList<PolicyOwner> policyOwners, String id) {
        for (PolicyOwner policyOwner : policyOwners) {
            if (policyOwner.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public void createPolicyHolder() {
        ArrayList<PolicyHolder> policyHolders = FileManager.policyHolderReader();
        ArrayList<Authentication> authentications = FileManager.authenticationReader();

        Label idLabel = new Label("ID (ph-xxxxxx): ");
        Label passwordLabel = new Label("Passwowrd: ");
        Label fullNameLabel = new Label("Full Name: ");

        TextField idField = new TextField();
        TextField passwordField = new TextField();
        TextField fullNameField = new TextField();

        // Create a grid pane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(idLabel, 0, 1);
        gridPane.add(idField, 1, 1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(fullNameLabel, 0, 3);
        gridPane.add(fullNameField, 1, 3);

        GridPane buttons = new GridPane();
        Button submitButton = new Button("Submit");
        buttons.add(submitButton, 1, 0);

        Button exitButton = new Button("Exit");
        buttons.add(exitButton, 0, 0);

        gridPane.add(buttons, 1, 4);

        exitButton.setOnAction(e -> UserSession.getStage().setScene(new Scene(adminMenu(), 500, 300)));

        submitButton.setOnAction(e ->{
            String enteredId = idField.getText().trim();
            // Check if the entered ID format is correct
            if (!enteredId.matches("^ph-[a-zA-Z0-9]{6}$")) {
                // If the format is incorrect, show a warning
                System.out.println("Incorrect ID Format");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Incorrect ID Format");
                alert.setHeaderText(null);
                alert.setContentText("Please enter the ID in the format ph-xxxxxx.");
                alert.showAndWait();
            } else if (isIdPhExists(policyHolders, enteredId)) {
                // If the ID exists, show a warning
                System.out.println("ID Already Exists");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ID Already Exists");
                alert.setHeaderText(null);
                alert.setContentText("The entered ID already exists. Please enter a different ID.");
                alert.showAndWait();
            } else {
                PolicyHolder newPolicyHolder = new PolicyHolder(enteredId, fullNameField.getText(), "", new ArrayList<>(), new ArrayList<>());
                policyHolders.add(newPolicyHolder);
                authentications.add(new Authentication(enteredId, passwordField.getText(), "policyHolder"));
                FileManager.policyHolderWriter(policyHolders);
                FileManager.authenticationWriter(authentications);
                UserSession.getStage().setScene(new Scene(adminMenu(), 400, 200));
            }
        });
        UserSession.getStage().setScene(new Scene(gridPane, 400, 200));
    }
    private boolean isIdPhExists(ArrayList<PolicyHolder> policyHolders, String id) {
        for (PolicyHolder policyHolder : policyHolders) {
            if (policyHolder.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

}
