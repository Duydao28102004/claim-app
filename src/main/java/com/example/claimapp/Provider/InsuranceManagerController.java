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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class InsuranceManagerController {

    public GridPane insuranceManagerPane() {
        // Create a horizontal ScrollPane
        ScrollPane scrollPane = new ScrollPane();

        // Create a grid pane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(40));
        gridPane.setHgap(20);
        gridPane.setVgap(20);

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

        Label specificCustomerLabel = new Label("Get specific customer:");
        TextField customerNameField = new TextField();
        Button getSpecificCustomerButton = new Button("Get customer");
        getSpecificCustomerButton.setPrefWidth(200);

        Label surveyorIdLabel = new Label("Enter Surveyor ID:");
        TextField managerIdField = new TextField();
        TextField surveyorIdField = new TextField();
        Button addSurveyorButton = new Button("Add Surveyor");
        addSurveyorButton.setPrefWidth(200);

        Label processClaimLabel = new Label("Process Claim:");
        processClaimLabel.setStyle("-fx-font-size: 15px;");
        TextField processClaimField = new TextField();
        processClaimField.setPromptText("Enter Claim ID");
        TextField processField = new TextField();
        processField.setPromptText("Enter Status (Accepted/Rejected)");
        Button processClaimButton = new Button("Process Claim");
        processClaimButton.setPrefWidth(200);


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
        gridPane.add(customerNameField, 1, 4);
        gridPane.add(getSpecificCustomerButton, 2, 4);
        GridPane.setHalignment(getSpecificCustomerButton, javafx.geometry.HPos.RIGHT);
        GridPane.setMargin(getSpecificCustomerButton, buttonMargin);

        gridPane.add(surveyorIdLabel, 0, 5);
        gridPane.add(surveyorIdField, 1, 5);
        gridPane.add(managerIdField, 2,5);
        gridPane.add(addSurveyorButton, 3, 5);
        addSurveyorButton.setPrefWidth(150);


        gridPane.setGridLinesVisible(false);


        InsuranceProcessManager insuranceProcessManager = new InsuranceProcessManager();


        // Retrieve all claims from the database
        ProviderManager providerManager = new ProviderManager();
        List<Claim> allClaims = providerManager.getAllClaims();

        // Filter claims to get only the ones with status "New"
        List<Claim> newClaims = allClaims.stream()
                .filter(claim -> claim.getStatus().equals("New"))
                .toList();

        // Clear existing text area before adding the new claims
        clearTextArea(gridPane);

        // Create a grid pane to contain the claim boxes
        GridPane claimGridPane = new GridPane();
        claimGridPane.setHgap(10); // Set horizontal gap between nodes
        claimGridPane.setVgap(10); // Set vertical gap between nodes
        claimGridPane.setPadding(new Insets(10)); // Set padding around the grid pane

        // Display new claims with process button for each claim
        int newRow = 0; // Choose the row index where you want to add the new components
        for (Claim claim : newClaims) {
            // Create a VBox to contain the claim information and process button
            VBox claimBox = new VBox();
            claimBox.setSpacing(10); // Set spacing between children
            claimBox.setPrefWidth(1000); // Set preferred width for the VBox
            claimBox.setPrefHeight(100); // Set preferred height for the VBox

            // Add styling to create a grey border
            claimBox.setStyle("-fx-border-color: grey; -fx-border-width: 1px; -fx-padding: 10px;");

            // Create a label to display the claim information
            Label claimLabel = new Label(claim.toString());

            // Create a button for processing the claim
            Button processButton = new Button("Process");
            processButton.setOnAction(e -> providerManager.processClaim(claim));

            // Add components to the VBox
            claimBox.getChildren().addAll(claimLabel, processButton);

            // Add the VBox to the GridPane
            claimGridPane.add(claimBox, 0, newRow); // Span 2 columns for full width
            newRow++; // Move to the next row for the next claim
        }

        // Set the content of the horizontal scroll pane to the grid pane
        scrollPane.setContent(claimGridPane);

        // Set horizontal scroll policy to "ALWAYS"
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        // Add the horizontal scroll pane to your layout
        gridPane.add(scrollPane, 0, newRow, 11, 1); // Add it to the bottom of the grid pane



        addSurveyorButton.setOnAction(e -> {
            String managerId = managerIdField.getText().trim(); // Assuming you have a TextField for entering the manager ID
            String surveyorId = surveyorIdField.getText().trim();

            if (!managerId.isEmpty() && !surveyorId.isEmpty()) {
                // Call the addSurveyorToManager method of InsuranceProcessManager
                insuranceProcessManager.addSurveyorToManager(managerId, surveyorId);
            } else {
                // Optionally, display a message indicating that either the manager ID or surveyor ID is empty
                System.out.println("Please enter both Manager ID and Surveyor ID.");
            }
        });


        // set action for the getClaimsButton
        getClaimsButton.setOnAction(e -> {
            // Step 1: Retrieve all the claims from the database (replace this with your actual database retrieval logic)
            ProviderManager dataRetriever = new ProviderManager();
            List<Claim> Claims = dataRetriever.getAllClaims(); // Assuming getAllClaims() returns a List<Claim>

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
        });


        // Set action for the sortingChoiceBox
        sortingChoiceBox.setOnAction(e -> {
            String selectedSortOption = sortingChoiceBox.getValue();
            switch (selectedSortOption) {
                case "Latest to Earliest":
                    // Step 1: Retrieve all the claims from the database
                    ProviderManager dataRetriever = new ProviderManager();
                    List<Claim> sortedClaims = dataRetriever.getAllClaims();

                    // Step 2: Sort the claims from the latest to earliest creation date
                    List<Claim> sortedClaimsLatestToEarliest = dataRetriever.claimLatestToEarliest(sortedClaims);

                    // Step 3: Format the sorted claims data into a suitable format for display on the UI
                    StringBuilder formattedSortedClaims = new StringBuilder();
                    for (Claim claim : sortedClaimsLatestToEarliest) {
                        formattedSortedClaims.append(claim.toString()).append("\n");
                    }

                    // Step 4: Update the UI to display the formatted sorted claims data
                    TextArea sortedClaimsTextArea = new TextArea();
                    sortedClaimsTextArea.setText(formattedSortedClaims.toString());
                    clearTextArea(gridPane); // Clear existing text area before adding the sorted claims
                    gridPane.add(sortedClaimsTextArea, 0, 7, 2, 1);
                    break;

                case "Earliest to Latest":
                    // Similar steps as above, but using claimEarliestToLatest function
                    // Step 1: Retrieve all the claims from the database
                    dataRetriever = new ProviderManager();
                    List<Claim> allClaimsEarliestToLatest = dataRetriever.getAllClaims();

                    // Step 2: Sort the claims from the earliest to latest creation date
                    List<Claim> sortedClaimsEarliestToLatest = dataRetriever.claimEarliestToLatest(allClaimsEarliestToLatest);

                    // Step 3: Format the sorted claims data into a suitable format for display on the UI
                    StringBuilder formattedSortedClaimsEarliestToLatest = new StringBuilder();
                    for (Claim claim : sortedClaimsEarliestToLatest) {
                        formattedSortedClaimsEarliestToLatest.append(claim.toString()).append("\n");
                    }

                    // Step 4: Update the UI to display the formatted sorted claims data
                    TextArea sortedClaimsEarliestToLatestTextArea = new TextArea();
                    sortedClaimsEarliestToLatestTextArea.setText(formattedSortedClaimsEarliestToLatest.toString());
                    clearTextArea(gridPane); // Clear existing text area before adding the sorted claims
                    gridPane.add(sortedClaimsEarliestToLatestTextArea, 0, 7, 2, 1);
                    break;
                default:
                    // Handle invalid selection
                    break;
            }
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
            String customerName = customerNameField.getText();

           if (!customerName.isEmpty()) {
               ProviderManager dataRetriever = new ProviderManager();
               Customer specificCustomer = dataRetriever.getSpecificCustomer(customerName);

               if (specificCustomer != null) {
                   // Display the customer details
                   TextArea customerTextArea = new TextArea();
                   customerTextArea.setText(specificCustomer.toString());
                   gridPane.add(customerTextArea, 0, 10, 3, 1);
               } else {
                   // Handle case where customer is not found
                   TextArea customerNotFoundTextArea = new TextArea();
                   customerNotFoundTextArea.setText("Customer not found.");
                   gridPane.add(customerNotFoundTextArea, 0, 10, 3, 1);
               }
           } else {
               // Show an error message if claim ID field is empty
               TextArea errorText = new TextArea();
               errorText.setText("Please enter a valid Claim ID.");
               gridPane.add(errorText, 0, 10, 3, 1);
           }
        });

        // Set alignment of the GridPane to center
        gridPane.setAlignment(Pos.CENTER);
        return gridPane;
    }

    void clearTextArea(GridPane gridPane) {
        ObservableList<Node> nodesToRemove = FXCollections.observableArrayList();
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) >= 4) {
                // Remove the node if it is in the specified area of the grid pane
                nodesToRemove.add(node);
            }
        }
        // Remove all nodes in the specified area
        gridPane.getChildren().removeAll(nodesToRemove);
    }

}
