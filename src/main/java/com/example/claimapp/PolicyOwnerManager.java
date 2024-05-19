package com.example.claimapp;

import com.example.claimapp.Authentication;
import com.example.claimapp.Customer.Dependent;
import com.example.claimapp.Customer.PolicyHolder;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formattable;

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
        viewDependents.setOnAction( e -> {
            viewDependents();
        });

        javafx.scene.control.Button viewClaims = new javafx.scene.control.Button("View Claims");
        viewClaims.setPrefWidth(250);
        viewClaims.setAlignment(Pos.CENTER); // Align the button's content to the center
        policyOwnerMenu.add(viewClaims, 0, 3);
        viewClaims.setOnAction( e -> {
            viewClaim();
        });

        javafx.scene.control.Button addInsuranceCards = new javafx.scene.control.Button("Add Insurance Card for Policy Holder and their dependent");
        addInsuranceCards.setPrefWidth(250);
        addInsuranceCards.setAlignment(Pos.CENTER); // Align the button's content to the center
        policyOwnerMenu.add(addInsuranceCards, 0, 4);
        addInsuranceCards.setOnAction( e -> {
            addInsuranceCardForPolicyHolder();
        });

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
        ArrayList<InsuranceCard> insuranceCards = FileManager.insuranceCardReader();
        policyHolders.removeIf(policyHolder -> policyHolder.getInsuranceCard().equals(""));
        for (InsuranceCard insuranceCard : insuranceCards) {
            if (!insuranceCard.getPolicyOwner().equals(UserSession.getLoggedInUserId())) {
                policyHolders.removeIf(policyHolder -> policyHolder.getInsuranceCard().equals(insuranceCard.getCardNumber()));
            }
        }

        // Create a VBox to hold the labels
        VBox vbox = new VBox();
        vbox.setSpacing(10);

        int counter = 1;
        // Add labels for each PolicyHolder
        for (PolicyHolder policyHolder : policyHolders) {
            HBox hBox = new HBox();
            Label label = new Label(counter + ") " + policyHolder.toString());
            Button deleteButton = new Button("Delete insurance card");
            deleteButton.setOnAction(e -> deletePolicyHolder(policyHolder, vbox));
            hBox.getChildren().addAll(label, deleteButton);
            vbox.getChildren().add(hBox);
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
                    HBox hBox = new HBox();
                    Label label = new Label(searchCounter + ") " + policyHolder.toString());
                    Button deleteButton = new Button("Delete");
                    deleteButton.setOnAction(e -> deletePolicyHolder(policyHolder, vbox));
                    hBox.getChildren().addAll(label, deleteButton);
                    vbox.getChildren().add(hBox);
                    searchCounter++;
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
                HBox hBox = new HBox();
                Label label = new Label(counter + ") " + policyHolder.toString());
                Button deleteButton = new Button("Delete");
                deleteButton.setOnAction(e -> deletePolicyHolder(policyHolder, vbox));
                hBox.getChildren().addAll(label, deleteButton);
                vbox.getChildren().add(hBox);
                counter++;
            }
        }
    }

    private void deletePolicyHolder(PolicyHolder policyHolder, VBox vbox) {
        ArrayList<PolicyHolder> policyHolders = FileManager.policyHolderReader();
        ArrayList<InsuranceCard> insuranceCards = FileManager.insuranceCardReader();
        ArrayList<Dependent> dependents = FileManager.dependentReader();
        ArrayList<Claim> claims = FileManager.claimReader();
        for (PolicyHolder p : policyHolders) {
            if (p.getId().equals(policyHolder.getId())) {
                for (Claim claim : claims) {
                    if (claim.getCardNumber().equals(p.getInsuranceCard())) {
                       if (claim.getStatus().equals("processing")) {
                           Alert alert = new Alert(Alert.AlertType.ERROR);
                           alert.setTitle("Error");
                           alert.setHeaderText("Unable to delete policy holder and their dependent insurance card");
                           alert.setContentText("A claim associated with this policy holder is currently in processing state.");
                           alert.showAndWait();
                           return;
                       }
                    }
                }
                if (!p.getDependents().isEmpty()) {
                    for (Dependent dependent : dependents) {
                        if (p.getDependents().contains(dependent.getId())) {
                            dependent.setInsuranceCard("");
                        }
                    }
                }
                insuranceCards.removeIf(insuranceCard -> insuranceCard.getCardNumber().equals(policyHolder.getInsuranceCard()));
                p.setInsuranceCard("");
                FileManager.policyHolderWriter(policyHolders);
                FileManager.insuranceCardWriter(insuranceCards);
                FileManager.dependentWriter(dependents);
                viewPolicyHolders();
            }
        }
    }

    public void viewDependents() {
        ArrayList<Dependent> dependents = FileManager.dependentReader();
        ArrayList<InsuranceCard> insuranceCards = FileManager.insuranceCardReader();
        dependents.removeIf(dependent -> dependent.getInsuranceCard().equals(""));
        for (InsuranceCard insuranceCard : insuranceCards) {
            if (!insuranceCard.getPolicyOwner().equals(UserSession.getLoggedInUserId())) {
                dependents.removeIf(dependent -> dependent.getInsuranceCard().equals(insuranceCard.getCardNumber()));
            }
        }

        // Create a VBox to hold the labels
        VBox vbox = new VBox();
        vbox.setSpacing(10);

        int counter = 1;
        // Add labels for each Dependent
        for (Dependent dependent : dependents) {
            HBox hBox = new HBox();
            Label label = new Label(counter + ") " + dependent.toString());
            hBox.getChildren().addAll(label);
            vbox.getChildren().add(hBox);
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
        searchButton.setOnAction(e -> searchDependents(dependents, searchField.getText().trim(), vbox));

        // Add event listener to trigger search when Enter key is pressed
        searchField.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                searchDependents(dependents, searchField.getText().trim(), vbox);
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

    // Method to search dependents
    private void searchDependents(ArrayList<Dependent> dependents, String searchText, VBox vbox) {
        if (!searchText.isEmpty()) {
            vbox.getChildren().clear();
            int searchCounter = 1;
            for (Dependent dependent : dependents) {
                if (dependent.getId().contains(searchText)) {
                    HBox hBox = new HBox();
                    Label label = new Label(searchCounter + ") " + dependent);
                    hBox.getChildren().addAll(label);
                    vbox.getChildren().add(hBox);
                    searchCounter++;
                }
            }
            if (vbox.getChildren().isEmpty()) {
                Label label = new Label("No results found");
                vbox.getChildren().add(label);
            }
        } else {
            vbox.getChildren().clear();
            int counter = 1;
            for (Dependent dependent : dependents) {
                HBox hBox = new HBox();
                Label label = new Label(counter + ") " + dependent.toString());
                hBox.getChildren().addAll(label);
                vbox.getChildren().add(hBox);
                counter++;
            }
        }
    }

    public void addInsuranceCardForPolicyHolder() {
        ArrayList<PolicyHolder> policyHolders = FileManager.policyHolderReader();
        ArrayList<InsuranceCard> insuranceCards = FileManager.insuranceCardReader();

        ArrayList<PolicyHolder> displayPolicyHolders = new ArrayList<>();
        // remove policy holder that already have insurance card
        for (PolicyHolder policyHolder : policyHolders) {
            if (policyHolder.getInsuranceCard().equals("")) {
                displayPolicyHolders.add(policyHolder);
            }
        }

        VBox vbox = new VBox();
        vbox.setSpacing(10);

        int counter = 1;
        for (PolicyHolder policyHolder : displayPolicyHolders) {
            HBox hbox = new HBox();
            Label label = new Label(counter + ")" + policyHolder.toString());
            Button addInsuranceCardButton = new Button("Add Insurance Card");
            addInsuranceCardButton.setOnAction(e -> {
                try {
                    addInsuranceCard(policyHolder);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            });
            hbox.getChildren().addAll(label, addInsuranceCardButton);
            vbox.getChildren().add(hbox);
        }

        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);

        Button exitButton = new Button("Exit to Main Menu");
        exitButton.setPrefWidth(180);
        exitButton.setOnAction(e -> {
            UserSession.getStage().setScene(new Scene(policyOwnerMenu(), 500, 300));
        });

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10));
        borderPane.setCenter(scrollPane);
        borderPane.setRight(exitButton);

        Scene scene = new Scene(borderPane, 700, 450);
        UserSession.getStage().setScene(scene);
    }

    private void addInsuranceCard(PolicyHolder policyHolder) throws ParseException {
        ArrayList<InsuranceCard> insuranceCards = FileManager.insuranceCardReader();
        ArrayList<Dependent> dependents = FileManager.dependentReader();
        ArrayList<PolicyHolder> policyHolders = FileManager.policyHolderReader();
        // Create a GridPane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(40));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Add labels, text fields, and buttons
        Label titleLabel = new Label("Add Insurance Card for Policy Holder");
        titleLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;"); // Optional styling
        titleLabel.setAlignment(Pos.CENTER); // Align center

        Label cardNumberLabel = new Label("Card Number (ic-xxxxxx):");
        cardNumberLabel.setStyle("-fx-font-size: 15px;"); // Optional styling
        TextField cardNumberField = new TextField();
        cardNumberField.setPrefWidth(400);


        Label expirationDateLabel = new Label("Expiration Date:");
        expirationDateLabel.setStyle("-fx-font-size: 15px;"); // Optional styling
        TextField expirationDateField = new TextField();
        expirationDateField.setPrefWidth(400);

        Button addInsuranceCardButton = new Button("Add Insurance Card");
        addInsuranceCardButton.setPrefWidth(200);

        Button exitButton = new Button("Exit");
        exitButton.setPrefWidth(200);
        exitButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        Label warningLabel = new Label(); // Warning label for displaying error message
        warningLabel.setStyle("-fx-text-fill: red;");

        addInsuranceCardButton.setOnAction(e -> {
            String cardNumber = cardNumberField.getText().trim();
            String inputDate = expirationDateField.getText();
            Date selectedDate = null;
            try {
                selectedDate = new SimpleDateFormat("dd/MM/yyyy").parse(inputDate);
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
            if (cardNumber.isEmpty() || inputDate.isEmpty()) {
                warningLabel.setText("Please fill in all fields");
            } else if (!cardNumberField.getText().matches("^ic-[a-zA-Z0-9]{6}$")) {
                warningLabel.setText("Invalid card number format. Please use ic-xxxxxx format");
            } else if (inputDate.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                warningLabel.setText("Invalid date format. Please use dd/MM/yyyy format");
            } else if (insuranceCards.stream().anyMatch(insuranceCard -> insuranceCard.getCardNumber().equals(cardNumber))) {
                warningLabel.setText("Insurance card already exists");
            } else {
                InsuranceCard insuranceCard = new InsuranceCard(cardNumber, policyHolder.getFullName(), UserSession.getLoggedInUserId(), selectedDate);
                insuranceCards.add(insuranceCard);
                for (Dependent dependent : dependents) {
                    if (dependent.getPolicyHolder().equals(policyHolder.getId())) {
                        dependent.setInsuranceCard(cardNumber);
                    }
                }
                for (PolicyHolder p : policyHolders) {
                    if (p.getId().equals(policyHolder.getId())) {
                        p.setInsuranceCard(cardNumber);
                    }
                }
                FileManager.insuranceCardWriter(insuranceCards);
                FileManager.dependentWriter(dependents);
                FileManager.policyHolderWriter(policyHolders);
                UserSession.getStage().setScene(new Scene(policyOwnerMenu(), 500, 300));
            }
        });

        // Add components to the grid pane
        gridPane.add(titleLabel, 0, 0, 4, 1);
        gridPane.add(cardNumberLabel, 0, 1);
        gridPane.add(cardNumberField, 1, 1, 3, 1);
        gridPane.add(expirationDateLabel, 0, 2);
        gridPane.add(expirationDateField, 1, 2, 3, 1);
        gridPane.add(addInsuranceCardButton, 3, 3);
        gridPane.add(warningLabel, 0, 4, 4, 1);

        // Create a Scene with the GridPane
        Scene scene = new Scene(gridPane, 500, 300);
        UserSession.getStage().setScene(scene);
    }

    public void viewClaim() {
        ArrayList<Claim> claims = FileManager.claimReader();
        ArrayList<InsuranceCard> insuranceCards = FileManager.insuranceCardReader();
        ArrayList<Dependent> dependents = FileManager.dependentReader();
        ArrayList<PolicyHolder> policyHolders = FileManager.policyHolderReader();

        // remove claim that is not associated with the policy owner
        for (Claim claim : claims) {
            if (!insuranceCards.stream().anyMatch(insuranceCard -> insuranceCard.getCardNumber().equals(claim.getCardNumber()))) {
                claims.remove(claim);
            }
        }

        // Create a VBox to hold the labels
        VBox vbox = new VBox();
        vbox.setSpacing(10);

        int counter = 1;
        // Add labels for each Claim
        for (Claim claim : claims) {
            HBox hBox = new HBox();
            String personName = "";
            if (claim.getInsuredPerson().contains("ph")) {
                for (PolicyHolder policyHolder : policyHolders) {
                    if (policyHolder.getId().equals(claim.getInsuredPerson())) {
                        personName = policyHolder.getId() + " - " +  policyHolder.getFullName();
                    }
                }
            } else {
                for (Dependent dependent : dependents) {
                    if (dependent.getId().equals(claim.getInsuredPerson())) {
                        personName = dependent.getId() + " - " + dependent.getFullName();
                    }
                }
            }
            String display = counter + ") " + "Claim ID: " + claim.getId() + "\n" +
                    "Insured Person" + personName + "\n" +
                    "Claim Date: " + claim.getClaimDate() + "\n" +
                    "Exam Date: " + claim.getExamDate() + "\n"+
                    "Card Number: " + claim.getCardNumber() + "\n" +
                    "Documents: " + claim.getDocuments() + "\n" +
                    "Banking Info: " + claim.getBankingInfo() + "\n" +
                    "Status: " + claim.getStatus() + "\n" +
                    "Amount: " + claim.getClaimAmount();
            Label label = new Label(display);
            hBox.getChildren().addAll(label);
            vbox.getChildren().add(hBox);
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
        searchButton.setOnAction(e -> searchClaim(claims, searchField.getText().trim(), vbox));

        // Add event listener to trigger search when Enter key is pressed
        searchField.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                searchClaim(claims, searchField.getText().trim(), vbox);
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
    private void searchClaim(ArrayList<Claim> claims, String searchText, VBox vbox) {
        if (!searchText.isEmpty()) {
            vbox.getChildren().clear();
            int searchCounter = 1;
            for (Claim claim : claims) {
                if (claim.getId().contains(searchText)) {
                    HBox hBox = new HBox();
                    Label label = new Label(searchCounter + ") " + claim.toString());
                    hBox.getChildren().addAll(label);
                    vbox.getChildren().add(hBox);
                    searchCounter++;
                }
            }
            if (vbox.getChildren().isEmpty()) {
                Label label = new Label("No results found");
                vbox.getChildren().add(label);
            }
        } else {
            vbox.getChildren().clear();
            int counter = 1;
            for (Claim claim : claims) {
                HBox hBox = new HBox();
                Label label = new Label(counter + ") " + claim.toString());
                hBox.getChildren().addAll(label);
                vbox.getChildren().add(hBox);
                counter++;
            }
        }
    }
}
