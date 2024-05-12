package com.example.claimapp;

import com.example.claimapp.Customer.Dependent;
import com.example.claimapp.Customer.PolicyHolder;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Formattable;

public class AdminManager {
    public GridPane adminMenu() {
        Label titleLabel = new Label("Admin Menu");
        titleLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
        titleLabel.setAlignment(Pos.CENTER);
        Button policyOwnerButton = new Button("Policy Owner");
        policyOwnerButton.setPrefWidth(250);
        policyOwnerButton.setAlignment(Pos.CENTER);
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
        gridPane.add(policyOwnerButton, 0, 1);
        gridPane.add(createPolicyHolderButton, 0, 2);
        gridPane.add(createDependentButton, 0, 3);
        gridPane.add(viewPolicyOwnersButton, 0, 4);
        gridPane.add(viewPolicyHoldersButton, 0, 5);
        gridPane.add(viewDependentsButton, 0, 6);
        gridPane.add(logout, 0, 7);

        policyOwnerButton.setOnAction(e -> viewPolicyOwner());
        createPolicyHolderButton.setOnAction(e -> createPolicyHolder());
        createDependentButton.setOnAction(e -> createDependent());

        return gridPane;
    }

    public void viewPolicyOwner() {
        ArrayList<PolicyOwner> policyOwners = FileManager.policyOwnerReader();

        // Create a VBox to hold the labels
        VBox vbox = new VBox();
        vbox.setSpacing(10);

        int counter = 1;
        for (PolicyOwner policyOwner : policyOwners) {
            Label label = new Label(counter + ") " + policyOwner.toString());
            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(e -> deletePolicyOwner(policyOwner.getId()));
            Button editButton = new Button("Edit");

            HBox policyOwnerBox = new HBox(10);
            policyOwnerBox.getChildren().addAll(label, editButton, deleteButton);
            vbox.getChildren().add(policyOwnerBox);

            counter++;
        }

        // Create a scroll pane
        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);

        // Create a search bar
        TextField searchField = new TextField();
        searchField.setPromptText("Search by ID");
        searchField.setPrefWidth(180);

        // Create a search button
        Button searchButton = new Button("Search");
        searchButton.setPrefWidth(180);
        searchButton.setOnAction(e -> searchPolicyOwner(policyOwners, searchField.getText(), vbox));

        searchField.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ENTER")) {
                searchPolicyOwner(policyOwners, searchField.getText(), vbox);
            }
        });

        // Create a create button
        Button createButton = new Button("Create policy owner");
        createButton.setPrefWidth(180);
        createButton.setOnAction(e -> createPolicyOwner());

        // Create an exit button
        Button exitButton = new Button("Exit");
        exitButton.setPrefWidth(180);
        exitButton.setOnAction(e -> UserSession.getStage().setScene(new Scene(adminMenu(), 500, 300)));

        // Create a grid pane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.add(searchField, 0, 0);
        gridPane.add(searchButton, 0, 1);
        gridPane.add(createButton, 0, 2);
        gridPane.add(exitButton, 0, 3);

        // Create a BorderPane to hold the scroll pane and grid pane
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10));
        borderPane.setCenter(scrollPane);
        borderPane.setAlignment(searchField, Pos.TOP_CENTER);
        borderPane.setRight(gridPane);

        UserSession.getStage().setScene(new Scene(borderPane, 700, 450));
    }

    private void searchPolicyOwner(ArrayList<PolicyOwner> policyOwners, String searchText, VBox vbox) {
        if (!searchText.isEmpty()) {
            vbox.getChildren().clear();
            int searchCounter = 1;
            for (PolicyOwner policyOwner : policyOwners) {
                if (policyOwner.getId().contains(searchText)) {
                    Label label = new Label(searchCounter + ") " + policyOwner.toString());
                    Button deleteButton = new Button("Delete");
                    Button editButton = new Button("Edit");
                    vbox.getChildren().addAll(label, editButton, deleteButton);
                    searchCounter++;
                }
            }
            if (vbox.getChildren().isEmpty()) {
                Label label = new Label("No results found.");
                vbox.getChildren().add(label);
            }
        } else {
            vbox.getChildren().clear();
            int counter = 1;
            for (PolicyOwner policyOwner : policyOwners) {
                Label label = new Label(counter + ") " + policyOwner.toString());
                Button deleteButton = new Button("Delete");
                Button editButton = new Button("Edit");
                vbox.getChildren().addAll(label, editButton, deleteButton);
                counter++;
            }
        }
    }
    public void createPolicyOwner() {
        ArrayList<PolicyOwner> policyOwners = FileManager.policyOwnerReader();
        ArrayList<Authentication> authentications = FileManager.authenticationReader();
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Create Policy Owner");

        Label idLabel = new Label("ID (po-xxxxxx): ");
        Label passwordLabel = new Label("Password: ");
        Label fullNameLabel = new Label("Full Name: ");

        TextField idField = new TextField();
        TextField passwordField = new TextField();
        TextField fullNameField = new TextField();

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        gridPane.add(idLabel, 0, 0);
        gridPane.add(idField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(fullNameLabel, 0, 2);
        gridPane.add(fullNameField, 1, 2);

        Button submitButton = new Button("Submit");
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
                createPolicyOwner();
            } else if (isIdPoExists(policyOwners, enteredId)) {
                // If the ID exists, show a warning
                System.out.println("ID Already Exists");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ID Already Exists");
                alert.setHeaderText(null);
                alert.setContentText("The entered ID already exists. Please enter a different ID.");
                alert.showAndWait();
                createPolicyOwner();
            } else {
                PolicyOwner newPolicyOwner = new PolicyOwner(enteredId, fullNameField.getText());
                policyOwners.add(newPolicyOwner);
                authentications.add(new Authentication(enteredId, passwordField.getText(), "policyOwner"));
                FileManager.policyOwnerWriter(policyOwners);
                FileManager.authenticationWriter(authentications);
                popupStage.close();
                viewPolicyOwner();
            }
        });

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> popupStage.close());

        gridPane.add(submitButton, 1, 3);
        gridPane.add(exitButton, 0, 3);

        Scene popupScene = new Scene(gridPane, 300, 200);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }

    public void editPolicyOwner(String id) {

    }

    public void deletePolicyOwner(String id) {
        ArrayList<PolicyOwner> policyOwners = FileManager.policyOwnerReader();
        ArrayList<Authentication> authentications = FileManager.authenticationReader();
        for (PolicyOwner policyOwner : policyOwners) {
            if (policyOwner.getId().equals(id)) {
                policyOwners.remove(policyOwner);
                break;
            }
        }
        for (Authentication authentication : authentications) {
            if (authentication.getId().equals(id)) {
                authentications.remove(authentication);
                break;
            }
        }
        FileManager.policyOwnerWriter(policyOwners);
        FileManager.authenticationWriter(authentications);
        viewPolicyOwner();
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

    public void createDependent() {
        ArrayList<Dependent> dependents = FileManager.dependentReader();
        ArrayList<Authentication> authentications = FileManager.authenticationReader();

        Label idLabel = new Label("ID (dp-xxxxxx): ");
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

        submitButton.setOnAction(e -> {
            String enteredId = idField.getText().trim();
            // Check if the entered ID format is correct
            if (!enteredId.matches("^dp-[a-zA-Z0-9]{6}$")) {
                // If the format is incorrect, show a warning
                System.out.println("Incorrect ID Format");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Incorrect ID Format");
                alert.setHeaderText(null);
                alert.setContentText("Please enter the ID in the format dp-xxxxxx.");
                alert.showAndWait();
            } else if (isIdDpExists(dependents, enteredId)) {
                // If the ID exists, show a warning
                System.out.println("ID Already Exists");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ID Already Exists");
                alert.setHeaderText(null);
                alert.setContentText("The entered ID already exists. Please enter a different ID.");
                alert.showAndWait();
            } else {
                Dependent newDependent = new Dependent(enteredId, fullNameField.getText(), "", new ArrayList<>(), "");
                dependents.add(newDependent);
                authentications.add(new Authentication(enteredId, passwordField.getText(), "dependent"));
                FileManager.dependentWriter(dependents);
                FileManager.authenticationWriter(authentications);
                UserSession.getStage().setScene(new Scene(adminMenu(), 400, 200));
            }
        });
        UserSession.getStage().setScene(new Scene(gridPane, 400, 200));
    }
    private boolean isIdDpExists(ArrayList<Dependent> dependents, String id) {
        for (Dependent dependent : dependents) {
            if (dependent.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
