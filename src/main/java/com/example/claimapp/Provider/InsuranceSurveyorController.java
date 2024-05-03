package com.example.claimapp.Provider;

import com.example.claimapp.Claim;
import com.example.claimapp.Customer.Customer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

import java.util.List;


public class InsuranceSurveyorController {

    public GridPane insuranceSurveyorPane() {
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


        Label claimsLabel = new Label("Get all claims:");
        claimsLabel.setStyle("-fx-font-size: 15px;"); // Optional styling
        Button getClaimsButton = new Button("Get all claims");
        getClaimsButton.setPrefWidth(100);

        Label customersLabel = new Label("Get all customers:");
        customersLabel.setStyle("-fx-font-size: 15px;"); // Optional styling
        Button getCustomersButton = new Button("Get all customers");
        getCustomersButton.setPrefWidth(150);

        // Add components to the grid pane
        gridPane.add(titleLabel, 0, 0, 2, 1);
        gridPane.add(claimsLabel, 0, 1);
        gridPane.add(getClaimsButton, 1, 1);
        GridPane.setHalignment(getClaimsButton, javafx.geometry.HPos.RIGHT);
        Insets buttonMargin = new Insets(0, 10, 0, 0);
        GridPane.setMargin(getClaimsButton, buttonMargin);
        gridPane.add(customersLabel, 0, 2);
        gridPane.add(getCustomersButton, 1, 2);
        GridPane.setHalignment(getCustomersButton, javafx.geometry.HPos.RIGHT);
        GridPane.setMargin(getCustomersButton, buttonMargin);
        gridPane.setGridLinesVisible(false);

        // set action for the getClaimsButton
        getClaimsButton.setOnAction(e -> {
            // Step 1: Retrieve all the claims from the database (replace this with your actual database retrieval logic)
            ProviderManager dataRetriever = new ProviderManager();
            List<Claim> allClaims = dataRetriever.getAllClaims(); // Assuming retrieveAllClaimsFromDatabase() returns a List<Claim>

            // Step 2: Format the claims data into a suitable format for display on the UI
            StringBuilder formattedClaims = new StringBuilder();
            for (Claim claim : allClaims) {
                formattedClaims.append(claim.toString()).append("\n"); // Assuming toString() provides a suitable representation of the claim
            }

            // Step 3: Update the UI to display the formatted claims data
            TextArea claimsTextArea = new TextArea();
            claimsTextArea.setText(formattedClaims.toString());
            // Assuming you have a GridPane named gridPane where you want to display the claims
            gridPane.add(claimsTextArea, 0, 2, 2, 1);
        });

        // set action for the getCustomersButton
        getCustomersButton.setOnAction(e -> {
            // Step 1: Retrieve all the customers from the database (replace this with your actual database retrieval logic)
            ProviderManager dataRetriever = new ProviderManager();
            List<Customer> allCustomers = dataRetriever.getAllCustomers(); // Assuming getAllCustomers() returns a List<Customer>

            // Step 2: Format the customers data into a suitable format for display on the UI
            StringBuilder formattedCustomers = new StringBuilder();
            for (Customer customer : allCustomers) {
                formattedCustomers.append(customer.toString()).append("\n"); // Assuming toString() provides a suitable representation of the customer
            }

            // Step 3: Update the UI to display the formatted customers data
            TextArea customersTextArea = new TextArea();
            customersTextArea.setText(formattedCustomers.toString());
            // Assuming you have a GridPane named gridPane where you want to display the customers
            gridPane.add(customersTextArea, 0, 5, 2, 1);
        });

        // Set alignment of the GridPane to center
        gridPane.setAlignment(Pos.CENTER);
        return gridPane;
    }


}
