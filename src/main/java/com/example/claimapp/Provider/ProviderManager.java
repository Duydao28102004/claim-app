package com.example.claimapp.Provider;

import com.example.claimapp.Claim;

import java.util.*;

import com.example.claimapp.Customer.Customer;
import com.example.claimapp.Customer.Dependent;
import com.example.claimapp.Customer.PolicyHolder;
import com.example.claimapp.FileManager;
import com.example.claimapp.InsuranceCard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;


public class ProviderManager {

    private ArrayList<Claim> claims = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private static ArrayList<InsuranceManager> insuranceManagers = new ArrayList<>();
    private static ArrayList<InsuranceSurveyor> insuranceSurveyors = new ArrayList<>();

//    public ProviderManager() {
//        initializeSampleData();
//    }
//
//    private void initializeSampleData() {
//        // Sample claims
//        claims.add(new Claim("Claim-1", new Date("11/11/11111"), "John Doe", "1234-5678-9012-3456", new Date(), new ArrayList<>(), 1000.00, "New", "BankInfo: ABCBank_123456789"));
//        claims.add(new Claim("Claim-2", new Date("22/22/2222"), "Jane Smith", "2345-6789-0123-4567", new Date(), new ArrayList<>(), 1500.00, "New", "BankInfo: XYZ_987654321"));
//
//        // Sample customers
//        customers.add(new Customer("c-1111111", "John Doe", new InsuranceCard(), null));
//        customers.add(new Customer("c-2222222", "Jane Smith", new InsuranceCard(), null));
//
//        // Sample insurance manager
//        InsuranceManager manager = new InsuranceManager("im-1", "Manager One", "1234567890", "123 Main St", "manager@example.com", "password", null);
//        insuranceManagers.add(manager);
//
//        // Sample insurance surveyor
//        InsuranceSurveyor surveyor = new InsuranceSurveyor("is-1", "Surveyor One", "9876543210", "456 Elm St", "surveyor@example.com", "password", null);
//        insuranceSurveyors.add(surveyor);
//    }

    public ArrayList<Claim> getAllClaims() {
        // Retrieve all claims from the database using FileManager
        return FileManager.claimReader();
    }


    public ArrayList<Customer> getAllCustomers() {
        // Retrieve all dependents and add them to the list
        ArrayList<Dependent> dependents = FileManager.dependentReader();
        customers.addAll(dependents);

        // Retrieve all policy holders and add them to the list
        ArrayList<PolicyHolder> policyHolders = FileManager.policyHolderReader();
        customers.addAll(policyHolders);

        return customers;
    }


    public Claim getSpecificClaim(String claimId) {
        // Retrieve all claims using claimReader
        ArrayList<Claim> claims = FileManager.claimReader();

        // Iterate through the claims to find the specific claim
        for (Claim claim : claims) {
            if (claim.getId().equals(claimId)) {
                return claim; // Return the claim if found
            }
        }
        return null; // Return null if claim with the specified ID is not found
    }


    public Customer getSpecificCustomer(String customerId) {
        // Retrieve all customers using getAllCustomers function
        ArrayList<Customer> customers = getAllCustomers();

        // Iterate through the customers to find the specific customer
        for (Customer customer : customers) {
            if (customer.getId().equals(customerId)) {
                return customer; // Return the customer if found
            }
        }
        return null; // Return null if customer with the specified name is not found
    }


    // Function to arrange claims from the latest to earliest creation date
    public ArrayList<Claim> claimLatestToEarliest() {
        ArrayList<Claim> claims = FileManager.claimReader();
        claims.sort(Comparator.comparing(Claim::getClaimDate).reversed());
        return claims;
    }

    // Function to arrange claims from the earliest to latest creation date
    public ArrayList<Claim> claimEarliestToLatest() {
        ArrayList<Claim> claims = FileManager.claimReader();
        claims.sort(Comparator.comparing(Claim::getClaimDate));
        return claims;
    }

    public static ArrayList<InsuranceManager> getInsuranceManagers() {
        return insuranceManagers;
    }

    public static ArrayList<InsuranceSurveyor> getInsuranceSurveyors() {
        return insuranceSurveyors;
    }

    void displayNewClaims(ArrayList<Claim> newClaims, GridPane gridPane) {
        // Step 1: Clear existing claims if any
       clearTextArea(gridPane);

        // Step 2: Display each claim with a process button
        for (int i = 0; i < newClaims.size(); i++) {
            Claim claim = newClaims.get(i);
            Label claimLabel = new Label("Claim ID: " + claim.getId());
            Button processButton = new Button("Process");
            processButton.setOnAction(event -> processClaim(claim));

            // Add claim and process button to the grid pane
            gridPane.add(claimLabel, 0, i + 1);
            gridPane.add(processButton, 1, i + 1);
        }
    }

    void processClaim(Claim claim) {
        // Implement logic to process the claim (e.g., update status)
        // For example:
        claim.setStatus("Processed");
        // Update the UI or database accordingly
    }

    void clearTextArea(GridPane gridPane) {
        ObservableList<Node> nodesToRemove = FXCollections.observableArrayList();
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) >= 7) {
                // Remove the node if it is in the specified area of the grid pane
                nodesToRemove.add(node);
            }
        }
        // Remove all nodes in the specified area
        gridPane.getChildren().removeAll(nodesToRemove);
    }

}
