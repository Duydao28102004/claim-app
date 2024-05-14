package com.example.claimapp.Provider;

import com.example.claimapp.Claim;
import com.example.claimapp.Customer.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
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
        getClaimsButton.setPrefWidth(200);

        Label sortingLabel = new Label("Sort claims:");
        sortingLabel.setStyle("-fx-font-size: 15px;");
        ChoiceBox<String> sortingChoiceBox = new ChoiceBox<>();
        sortingChoiceBox.getItems().addAll("Default", "Latest to Earliest", "Earliest to Latest");
        sortingChoiceBox.setValue("Default");

        Label customersLabel = new Label("Get all customers:");
        customersLabel.setStyle("-fx-font-size: 15px;"); // Optional styling
        Button getCustomersButton = new Button("Get all customers");
        getCustomersButton.setPrefWidth(200);

        Label specificClaimLabel = new Label("Enter Claim ID:");
        TextField claimIdField = new TextField();
        Button getSpecificClaimButton = new Button("Get Specific Claim");
        getSpecificClaimButton.setPrefWidth(200);

        Label specificCustomerLabel = new Label("Enter Customer ID:");
        TextField customerIdField = new TextField();
        Button getSpecificCustomerButton = new Button("Get customer");
        getSpecificCustomerButton.setPrefWidth(200);

        Label proposeLabel = new Label("Enter Claim ID:");
        TextField proposeClaimField = new TextField();
        Button proposeClaimButton = new Button("Propose Claim");
        proposeClaimButton.setPrefWidth(200);


        // Add components to the grid pane
        gridPane.add(titleLabel, 0, 0, 2, 1);
        gridPane.add(claimsLabel, 0, 1);
        gridPane.add(getClaimsButton, 1, 1);
        GridPane.setHalignment(getClaimsButton, javafx.geometry.HPos.RIGHT);
        Insets buttonMargin = new Insets(0, 10, 5, 2);
        GridPane.setMargin(getClaimsButton, buttonMargin);

        gridPane.add(sortingLabel, 2, 1);
        gridPane.add(sortingChoiceBox, 3, 1);

        gridPane.add(customersLabel, 0, 2);
        gridPane.add(getCustomersButton, 1, 2);
        GridPane.setHalignment(getCustomersButton, javafx.geometry.HPos.RIGHT);
        GridPane.setMargin(getCustomersButton, buttonMargin);

        gridPane.add(specificClaimLabel, 0, 3);
        gridPane.add(claimIdField, 1, 3);
        gridPane.add(getSpecificClaimButton, 2, 3);
        GridPane.setHalignment(getSpecificClaimButton, javafx.geometry.HPos.RIGHT);
        GridPane.setMargin(getSpecificClaimButton, buttonMargin);

        gridPane.add(specificCustomerLabel, 0, 4);
        gridPane.add(customerIdField, 1, 4);
        gridPane.add(getSpecificCustomerButton, 2, 4);
        GridPane.setHalignment(getSpecificCustomerButton, javafx.geometry.HPos.RIGHT);
        GridPane.setMargin(getSpecificCustomerButton, buttonMargin);

        gridPane.add(proposeLabel, 0, 5);
        gridPane.add(proposeClaimField, 1, 5);
        gridPane.add(proposeClaimButton, 2, 5);
        GridPane.setHalignment(proposeClaimButton, javafx.geometry.HPos.RIGHT);
        GridPane.setMargin(proposeClaimButton, buttonMargin);

        gridPane.setGridLinesVisible(false);

        InsuranceProcessManager insuranceProcessManager = new InsuranceProcessManager();
        ProviderManager providerManager = new ProviderManager();

        // Button to propose claim
        proposeClaimButton.setOnAction(e -> {
            String claimId = proposeClaimField.getText().trim();
            // Clear existing text area before adding new claims
            providerManager.clearTextArea(gridPane);

            if (!claimId.isEmpty()) {
                // Call the getSpecificClaim function to check the claim's current status
                Claim claim = providerManager.getSpecificClaim(claimId);

                if (claim != null) {
                    if ("pending".equals(claim.getStatus())) {
                        // Call the proposeClaim function from InsuranceProcessManager
                        insuranceProcessManager.proposeClaim(claimId);

                        // Display a message indicating success
                        proposeLabel.setText("Claim " + claimId + " has been proposed.");
                    } else {
                        // Display a message indicating the claim is not in 'pending' status
                        proposeLabel.setText("Cannot propose claim. Claim " + claimId + " is already in " + claim.getStatus() + " status.");
                    }
                } else {
                    // Display a message indicating the claim was not found
                    proposeLabel.setText("Claim " + claimId + " not found.");
                }
                gridPane.add(proposeLabel, 3, 6);
            } else {
                // Display a message indicating that the claim ID is empty
                proposeLabel.setText("Please enter a claim ID.");
                gridPane.add(proposeLabel, 3, 6);
            }
        });


        // set action for the getClaimsButton
        getClaimsButton.setOnAction(e -> {
            // Step 1: Retrieve all the claims from the database (replace this with your actual database retrieval logic)
            ProviderManager dataRetriever = new ProviderManager();
            // Assuming getAllClaims() returns a List<Claim>
            ArrayList<Claim> Claims = dataRetriever.getAllClaims();
            // Clear existing text area before adding the sorted claims
            providerManager.clearTextArea(gridPane);

            // Check if the claims list is empty
            if (Claims.isEmpty()) {
                // If there are no claims, display a message
                TextArea noClaimsTextArea = new TextArea();
                noClaimsTextArea.setText("There are no claims.");
                gridPane.add(noClaimsTextArea, 0, 7, 2, 1);
            } else {
                // Step 2: Format the claims data into a suitable format for display on the UI
                StringBuilder formattedClaims = new StringBuilder();
                for (Claim claim : Claims) {
                    formattedClaims.append(claim.toString()).append("\n"); // Assuming toString() provides a suitable representation of the claim
                }

                // Step 3: Update the UI to display the formatted claims data
                TextArea claimsTextArea = new TextArea();
                claimsTextArea.setText(formattedClaims.toString());
                // Assuming you have a GridPane named gridPane where you want to display the claims
                gridPane.add(claimsTextArea, 0, 7, 2, 1);
            }
        });


        // Set action for the sortingChoiceBox
        sortingChoiceBox.setOnAction(e -> {
            String selectedSortOption = sortingChoiceBox.getValue();
            ProviderManager dataRetriever = new ProviderManager();
            ArrayList<Claim> sortedClaims = new ArrayList<>();

            switch (selectedSortOption) {
                case "Latest to Earliest":
                    // Step 1: Retrieve all the claims from the database
                    sortedClaims = dataRetriever.claimLatestToEarliest();
                    break;

                case "Earliest to Latest":
                    // Step 1: Retrieve all the claims from the database
                    sortedClaims = dataRetriever.claimEarliestToLatest();
                    break;

                default:
                    // Handle invalid selection
                    break;
            }


            // Step 3: Format the sorted claims data into a suitable format for display on the UI
            StringBuilder formattedSortedClaims = new StringBuilder();
            for (Claim claim : sortedClaims) {
                formattedSortedClaims.append(claim.toString()).append("\n");
            }

            // Step 4: Update the UI to display the formatted sorted claims data
            TextArea sortedClaimsTextArea = new TextArea();
            sortedClaimsTextArea.setText(formattedSortedClaims.toString());
            gridPane.add(sortedClaimsTextArea, 0, 7, 2, 1);
        });


        // set action for the getCustomersButton
        getCustomersButton.setOnAction(e -> {
            // Step 1: Retrieve all the customers from the database (replace this with your actual database retrieval logic)
            ProviderManager dataRetriever = new ProviderManager();
            ArrayList<Customer> allCustomers = dataRetriever.getAllCustomers(); // Assuming getAllCustomers() returns a List<Customer>

            // Clear existing text area before adding new claims
            providerManager.clearTextArea(gridPane);

            // Step 2: Format the customers data into a suitable format for display on the UI
            StringBuilder formattedCustomers = new StringBuilder();
            for (Customer customer : allCustomers) {
                formattedCustomers.append(customer.toString()).append("\n"); // Assuming toString() provides a suitable representation of the customer
            }

            // Step 3: Update the UI to display the formatted customers data
            TextArea customersTextArea = new TextArea();
            customersTextArea.setText(formattedCustomers.toString());
            // Assuming you have a GridPane named gridPane where you want to display the customers
            gridPane.add(customersTextArea, 0, 8, 2, 1);
        });

        // Set action for the getSpecificClaimButton
        getSpecificClaimButton.setOnAction(e -> {
            String claimId = claimIdField.getText().trim();

            if (!claimId.isEmpty()) {
                ProviderManager dataRetriever = new ProviderManager();
                Claim specificClaim = dataRetriever.getSpecificClaim(claimId);

                if (specificClaim != null) {
                    // Display the specific claim information
                    TextArea claimInfoText = new TextArea();
                    claimInfoText.setText("Claim Information:\n" + specificClaim.toString());
                    gridPane.add(claimInfoText, 0, 9, 2, 1);
                } else {
                    // Claim with the provided ID not found
                    TextArea claimNotFoundText = new TextArea();
                    claimNotFoundText.setText("Claim with ID " + claimId + " not found.");
                    gridPane.add(claimNotFoundText, 0, 9, 2, 1);
                }
            } else {
                // Show an error message if claim ID field is empty
                TextArea errorText = new TextArea();
                errorText.setText("Please enter a valid Claim ID.");
                gridPane.add(errorText, 0, 9, 3, 1);
            }
        });

        // Set action for the getCustomerButton
        getSpecificCustomerButton.setOnAction(e -> {
            String customerId = customerIdField.getText().trim();

            if (!customerId.isEmpty()) {
                ProviderManager dataRetriever = new ProviderManager();
                Customer specificCustomer = dataRetriever.getSpecificCustomer(customerId);

                if (specificCustomer != null) {
                    // Display the customer details
                    TextArea customerTextArea = new TextArea();
                    customerTextArea.setText("Customer Information:\n" + specificCustomer.toString());
                    gridPane.add(customerTextArea, 0, 9, 2, 1);
                } else {
                    // Handle case where customer is not found
                    TextArea customerNotFoundText = new TextArea();
                    customerNotFoundText.setText("Customer with ID " + customerId + " not found.");
                    gridPane.add(customerNotFoundText, 0, 9, 2, 1);
                }
            } else {
                // Show an error message if claim ID field is empty
                TextArea errorText = new TextArea();
                errorText.setText("Please enter a valid Customer ID.");
                gridPane.add(errorText, 0, 9, 3, 1);
            }
        });

        // Set alignment of the GridPane to center
        gridPane.setAlignment(Pos.CENTER);
        return gridPane;
    }

}
