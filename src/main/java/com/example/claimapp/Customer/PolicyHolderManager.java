package com.example.claimapp.Customer;

import com.example.claimapp.Claim;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javafx.geometry.Insets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PolicyHolderManager {
    private PolicyHolder policyHolder; // Reference to the PolicyHolder object

    public PolicyHolderManager(PolicyHolder policyHolder) {
        this.policyHolder = policyHolder;
    }

    public GridPane policyHolderMenu(Stage stage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));

        Label titleLabel = new Label("Policy Holder Menu");
        titleLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
        grid.add(titleLabel, 0, 0, 2, 1);

        Button addClaim = new Button("Add Claim");
        Button updateClaim = new Button("Update Claim");
        Button retrieveClaims = new Button("Retrieve Claims");
        Button updateInfo = new Button("Update Info");

        addClaim.setPrefWidth(250);
        updateClaim.setPrefWidth(250);
        retrieveClaims.setPrefWidth(250);
        updateInfo.setPrefWidth(250);

        // Action for adding a new claim
        addClaim.setOnAction(event -> {
            Dialog<Claim> dialog = new Dialog<>();
            dialog.setTitle("Add Claim");
            dialog.setHeaderText("Add a New Claim");

            Label idLabel = new Label("Claim ID:");
            TextField idField = new TextField();

            Label claimDateLabel = new Label("Claim Date:");
            DatePicker claimDateField = new DatePicker();

            Label insuredPersonLabel = new Label("Insured Person:");
            TextField insuredPersonField = new TextField();

            Label cardNumberLabel = new Label("Card Number:");
            TextField cardNumberField = new TextField();

            Label examDateLabel = new Label("Exam Date:");
            DatePicker examDateField = new DatePicker();

            Label documentsLabel = new Label("Documents:");
            TextField documentsField = new TextField();

            Label claimAmountLabel = new Label("Claim Amount:");
            TextField claimAmountField = new TextField();

            Label statusLabel = new Label("Status:");
            TextField statusField = new TextField();

            Label bankingInfoLabel = new Label("Banking Info:");
            TextField bankingInfoField = new TextField();

            GridPane dialogGrid = new GridPane();
            dialogGrid.setAlignment(Pos.CENTER);
            dialogGrid.setHgap(10);
            dialogGrid.setVgap(10);
            dialogGrid.add(idLabel, 0, 0);
            dialogGrid.add(idField, 1, 0);
            dialogGrid.add(claimDateLabel, 0, 1);
            dialogGrid.add(claimDateField, 1, 1);
            dialogGrid.add(insuredPersonLabel, 0, 2);
            dialogGrid.add(insuredPersonField, 1, 2);
            dialogGrid.add(cardNumberLabel, 0, 3);
            dialogGrid.add(cardNumberField, 1, 3);
            dialogGrid.add(examDateLabel, 0, 4);
            dialogGrid.add(examDateField, 1, 4);
            dialogGrid.add(documentsLabel, 0, 5);
            dialogGrid.add(documentsField, 1, 5);
            dialogGrid.add(claimAmountLabel, 0, 6);
            dialogGrid.add(claimAmountField, 1, 6);
            dialogGrid.add(statusLabel, 0, 7);
            dialogGrid.add(statusField, 1, 7);
            dialogGrid.add(bankingInfoLabel, 0, 8);
            dialogGrid.add(bankingInfoField, 1, 8);

            dialog.getDialogPane().setContent(dialogGrid);

            ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == addButton) {
                    String id = idField.getText();
                    Date claimDate = java.sql.Date.valueOf(claimDateField.getValue());
                    String insuredPerson = insuredPersonField.getText();
                    String cardNumber = cardNumberField.getText();
                    Date examDate = java.sql.Date.valueOf(examDateField.getValue());
                    ArrayList<String> documents = new ArrayList<>();
                    documents.add(documentsField.getText());
                    double claimAmount = Double.parseDouble(claimAmountField.getText());
                    String status = statusField.getText();
                    String bankingInfo = bankingInfoField.getText();
                    return new Claim(id, claimDate, insuredPerson, cardNumber, examDate, documents, claimAmount, status, bankingInfo);
                }
                return null;
            });

            Optional<Claim> result = dialog.showAndWait();
            result.ifPresent(claim -> policyHolder.manageClaim(claim.getId(), claim.getClaimDate(), claim.getInsuredPerson(), claim.getCardNumber(), claim.getExamDate(), claim.getDocuments(), claim.getClaimAmount(), claim.getStatus(), claim.getBankingInfo(), true));
        });

        // Action for updating an existing claim
        updateClaim.setOnAction(event -> {
            Dialog<Claim> dialog = new Dialog<>();
            dialog.setTitle("Update Claim");
            dialog.setHeaderText("Update an Existing Claim");

            // Create form fields for updating claim details
            GridPane gridpane = new GridPane();
            gridpane.setAlignment(Pos.CENTER);
            gridpane.setHgap(10);
            gridpane.setVgap(10);
            gridpane.setPadding(new Insets(25, 25, 25, 25));

            TextField claimIdField = new TextField();
            claimIdField.setPromptText("Claim ID");
            TextField claimDateField = new TextField();
            claimDateField.setPromptText("Claim Date (yyyy-MM-dd)");
            TextField insuredPersonField = new TextField();
            insuredPersonField.setPromptText("Insured Person");
            TextField cardNumberField = new TextField();
            cardNumberField.setPromptText("Card Number");
            TextField examDateField = new TextField();
            examDateField.setPromptText("Exam Date (yyyy-MM-dd)");
            TextField documentsField = new TextField();
            documentsField.setPromptText("Documents (comma separated)");
            TextField claimAmountField = new TextField();
            claimAmountField.setPromptText("Claim Amount");
            TextField statusField = new TextField();
            statusField.setPromptText("Status");
            TextField bankingInfoField = new TextField();
            bankingInfoField.setPromptText("Banking Info");

            grid.add(new Label("Claim ID:"), 0, 0);
            grid.add(claimIdField, 1, 0);
            grid.add(new Label("Claim Date:"), 0, 1);
            grid.add(claimDateField, 1, 1);
            grid.add(new Label("Insured Person:"), 0, 2);
            grid.add(insuredPersonField, 1, 2);
            grid.add(new Label("Card Number:"), 0, 3);
            grid.add(cardNumberField, 1, 3);
            grid.add(new Label("Exam Date:"), 0, 4);
            grid.add(examDateField, 1, 4);
            grid.add(new Label("Documents:"), 0, 5);
            grid.add(documentsField, 1, 5);
            grid.add(new Label("Claim Amount:"), 0, 6);
            grid.add(claimAmountField, 1, 6);
            grid.add(new Label("Status:"), 0, 7);
            grid.add(statusField, 1, 7);
            grid.add(new Label("Banking Info:"), 0, 8);
            grid.add(bankingInfoField, 1, 8);

            dialog.getDialogPane().setContent(grid);

            ButtonType updateButton = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(updateButton, ButtonType.CANCEL);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == updateButton) {
                    // Get updated claim details from form fields
                    String claimId = claimIdField.getText();
                    String insuredPerson = insuredPersonField.getText();
                    String cardNumber = cardNumberField.getText();
                    ArrayList<String> documents = new ArrayList<>(Arrays.asList(documentsField.getText().split(",")));
                    double claimAmount = Double.parseDouble(claimAmountField.getText());
                    String status = statusField.getText();
                    String bankingInfo = bankingInfoField.getText();

                    Date claimDate;
                    Date examDate;
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                        claimDate = dateFormat.parse(claimDateField.getText());
                        examDate = dateFormat.parse(examDateField.getText());
                    } catch (ParseException e) {
                        e.printStackTrace();
                        // Handle parsing error, e.g., display a message to the user
                        return null;
                    }

                    // Update the existing claim using the manageClaim method of the PolicyHolder class
                    policyHolder.manageClaim(claimId, claimDate, insuredPerson, cardNumber, examDate, documents, claimAmount, status, bankingInfo, false);
                }
                return null;
            });

            dialog.showAndWait();
        });



// Action for retrieving claims
        retrieveClaims.setOnAction(event -> {
            // Retrieve and display claims in a new window or dialog
            ArrayList<Claim> claims = policyHolder.retrieveClaims(policyHolder.getId());

            // Create a dialog to display retrieved claims
            Dialog<String> claimsDialog = new Dialog<>();
            claimsDialog.setTitle("Claims");
            claimsDialog.setHeaderText("Claims for Policy Holder: " + policyHolder.getFullName());

            // Create a ListView to display claims
            ListView<String> claimsListView = new ListView<>();
            for (Claim claim : claims) {
                claimsListView.getItems().add(claim.toString()); // Modify toString method of Claim class to display desired details
            }

            claimsDialog.getDialogPane().setContent(claimsListView);

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            claimsDialog.getDialogPane().getButtonTypes().addAll(okButton);

            claimsDialog.showAndWait();
        });


// Action for updating personal information
        updateInfo.setOnAction(event -> {
            // Create a new dialog for updating personal information
            Dialog<Void> infoDialog = new Dialog<>();
            infoDialog.setTitle("Update Personal Information");
            infoDialog.setHeaderText("Update Your Personal Information");

            // Create form fields for updating personal information
            TextField phoneField = new TextField();
            phoneField.setPromptText("Enter new phone number");

            TextField addressField = new TextField();
            addressField.setPromptText("Enter new address");

            TextField emailField = new TextField();
            emailField.setPromptText("Enter new email");

            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("Enter new password");

            GridPane infoGrid = new GridPane();
            infoGrid.setAlignment(Pos.CENTER);
            infoGrid.setHgap(10);
            infoGrid.setVgap(10);
            infoGrid.setPadding(new Insets(20));

            infoGrid.add(new Label("Phone Number:"), 0, 0);
            infoGrid.add(phoneField, 1, 0);
            infoGrid.add(new Label("Address:"), 0, 1);
            infoGrid.add(addressField, 1, 1);
            infoGrid.add(new Label("Email:"), 0, 2);
            infoGrid.add(emailField, 1, 2);
            infoGrid.add(new Label("Password:"), 0, 3);
            infoGrid.add(passwordField, 1, 3);

            infoDialog.getDialogPane().setContent(infoGrid);

            // Add buttons to the dialog
            ButtonType updateButton = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
            infoDialog.getDialogPane().getButtonTypes().addAll(updateButton, ButtonType.CANCEL);

            // Handle the result of the dialog
            infoDialog.setResultConverter(dialogButton -> {
                if (dialogButton == updateButton) {
                    // Get updated personal information from the form fields
                    String newPhone = phoneField.getText();
                    String newAddress = addressField.getText();
                    String newEmail = emailField.getText();
                    String newPassword = passwordField.getText();

                    // Update personal information using the updatePersonalInfo method of the PolicyHolder class
                    policyHolder.updatePersonalInfo(newPhone, newAddress, newEmail, newPassword);
                }
                return null;
            });

            infoDialog.showAndWait();
        });


        grid.add(addClaim, 0, 1);
        grid.add(updateClaim, 0, 2);
        grid.add(retrieveClaims, 0, 3);
        grid.add(updateInfo, 0, 4);

        return grid;
    }
}
