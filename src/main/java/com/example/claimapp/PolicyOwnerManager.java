package com.example.claimapp;

import com.example.claimapp.Authentication;
import com.example.claimapp.Customer.PolicyHolder;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class PolicyOwnerManager {
    public GridPane policyOwnerMenu() {
        GridPane policyOwnerMenu = new GridPane();
        policyOwnerMenu.setAlignment(Pos.CENTER);
        policyOwnerMenu.setHgap(10);
        policyOwnerMenu.setVgap(10);
        policyOwnerMenu.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));
        Label titleLabel = new Label("Policy Owner Menu");
        titleLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;"); // Optional styling
        titleLabel.setAlignment(Pos.CENTER);
        policyOwnerMenu.add(titleLabel, 0, 0);

        javafx.scene.control.Button viewPolicyHolders = new javafx.scene.control.Button("View Policy Holders");
        viewPolicyHolders.setPrefWidth(250);
        viewPolicyHolders.setAlignment(Pos.CENTER); // Align the button's content to the center
        policyOwnerMenu.add(viewPolicyHolders, 0, 1);
        viewPolicyHolders.setOnAction( e -> {
            viewPolicyHolders();
        });

        javafx.scene.control.Button viewDependents = new javafx.scene.control.Button("View Dependents");
        viewDependents.setPrefWidth(250);
        viewDependents.setAlignment(Pos.CENTER); // Align the button's content to the center
        policyOwnerMenu.add(viewDependents, 0, 2);

        javafx.scene.control.Button viewClaims = new javafx.scene.control.Button("View Claims");
        viewClaims.setPrefWidth(250);
        viewClaims.setAlignment(Pos.CENTER); // Align the button's content to the center
        policyOwnerMenu.add(viewClaims, 0, 3);

        javafx.scene.control.Button viewInsuranceCards = new javafx.scene.control.Button("View Insurance Cards");
        viewInsuranceCards.setPrefWidth(250);
        viewInsuranceCards.setAlignment(Pos.CENTER); // Align the button's content to the center
        policyOwnerMenu.add(viewInsuranceCards, 0, 4);

        javafx.scene.control.Button logout = new javafx.scene.control.Button("Logout");
        logout.setPrefWidth(250);
        logout.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        logout.setOnAction( e -> {
            // Handle logout action here
            System.out.println("Logging out...");
            UserSession.getStage().setScene(new Scene(new Authentication().loginPane(), 500, 300));
        });
        logout.setAlignment(Pos.CENTER); // Align the button's content to the center
        policyOwnerMenu.add(logout, 0, 5);

        return policyOwnerMenu;
    }

    public void viewPolicyHolders() {
        ArrayList<PolicyHolder> policyHolders = FileManager.policyHolderReader();

        // Create a VBox to hold the labels
        VBox vbox = new VBox();
        vbox.setSpacing(10);

        int counter = 1;
        // Add labels for each PolicyHolder
        for (PolicyHolder policyHolder : policyHolders) {
            Label label = new Label( counter + ") " + policyHolder.toString());
            vbox.getChildren().add(label);
            counter++;
        }

        // Create a ScrollPane and add the VBox to it
        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);

        // Create a search bar
        TextField searchField = new TextField();
        searchField.setPromptText("Search by ID");
        searchField.setPrefWidth(180);

        // Create a search button
        Button searchButton = new Button("Search");
        searchButton.setPrefWidth(180);
        searchButton.setOnAction(e -> searchPolicyHolders(policyHolders, searchField.getText().trim(), vbox));

        // Add event listener to trigger search when Enter key is pressed
        searchField.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                searchPolicyHolders(policyHolders, searchField.getText().trim(), vbox);
            }
        });

        // Create an "Exit to Main Menu" button
        Button exitButton = new Button("Exit to Main Menu");
        exitButton.setPrefWidth(180);
        exitButton.setOnAction(e -> {
            UserSession.getStage().setScene(new Scene(policyOwnerMenu(), 500, 300));
        });

        GridPane gridPane = new GridPane();
        gridPane.add(searchField, 0, 0);
        gridPane.add(searchButton, 0, 1);
        gridPane.add(exitButton, 0, 2);
        // Create a BorderPane to hold the scroll pane, search bar, search button, and exit button
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10));
        borderPane.setCenter(scrollPane);
        borderPane.setAlignment(searchField, Pos.CENTER_LEFT);
        borderPane.setRight(gridPane);

        // Create a Scene with the BorderPane
        Scene scene = new Scene(borderPane, 700, 450);

        // Set the Scene to the Stage
        UserSession.getStage().setScene(scene);
    }

    // Method to search policy holders
    private void searchPolicyHolders(ArrayList<PolicyHolder> policyHolders, String searchText, VBox vbox) {
        if (!searchText.isEmpty()) {
            vbox.getChildren().clear();
            int searchCounter = 1;
            for (PolicyHolder policyHolder : policyHolders) {
                if (policyHolder.getId().contains(searchText)) {
                    Label label = new Label(searchCounter + ") " + policyHolder.toString());
                    searchCounter++;
                    vbox.getChildren().add(label);
                }
            }
            if (vbox.getChildren().isEmpty()) {
                Label label = new Label("No results found");
                vbox.getChildren().add(label);
            }
        } else {
            vbox.getChildren().clear();
            int counter = 1;
            for (PolicyHolder policyHolder : policyHolders) {
                Label label = new Label( counter + ") " + policyHolder.toString());
                vbox.getChildren().add(label);
                counter++;
            }
        }
    }
}
