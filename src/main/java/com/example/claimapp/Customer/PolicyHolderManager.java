package com.example.claimapp.Customer;

import com.example.claimapp.Claim;
import com.example.claimapp.FileManager;
import com.example.claimapp.UserSession;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


public class PolicyHolderManager {
    public GridPane policyHolderMenu() {
        Label titleLabel = new Label("Policy Holder Manager");
        titleLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
        titleLabel.setAlignment(Pos.CENTER);

        Button informationOfPolicyHolderButton = new Button("Get information of policy holder");
        informationOfPolicyHolderButton.setPrefWidth(250);
        informationOfPolicyHolderButton.setAlignment(Pos.CENTER);
        informationOfPolicyHolderButton.setOnAction(e -> {
            informationOfPolicyHolder();
        });

        Button createClaimButton = new Button("Create claim");
        createClaimButton.setPrefWidth(250);
        createClaimButton.setAlignment(Pos.CENTER);
        createClaimButton.setOnAction(e -> createClaim());

        Button viewClaimButton = new Button("View claim");
        viewClaimButton.setPrefWidth(250);
        viewClaimButton.setAlignment(Pos.CENTER);
        viewClaimButton.setOnAction(e -> viewClaim());

        Button viewDependentButton = new Button("view dependents");
        viewDependentButton.setPrefWidth(250);
        viewDependentButton.setAlignment(Pos.CENTER);
        viewDependentButton.setOnAction(e -> {
            viewDependent();
        });

        Button logoutButton = new Button("Logout");
        logoutButton.setPrefWidth(250);
        logoutButton.setAlignment(Pos.CENTER);
        logoutButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        logoutButton.setOnAction(e -> {
            // Handle logout action here
            System.out.println("Logging out...");
            UserSession.getStage().setScene(new Scene(new com.example.claimapp.Authentication().loginPane(), 500, 300));
        });

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(20);
        gridPane.add(titleLabel, 0, 0);
        gridPane.add(informationOfPolicyHolderButton, 0, 1);
        gridPane.add(createClaimButton, 0, 2);
        gridPane.add(viewClaimButton, 0, 3);
        gridPane.add(viewDependentButton, 0, 4);
        gridPane.add(logoutButton, 0, 5);

        return gridPane;
    }

    public void informationOfPolicyHolder() {
        ArrayList<PolicyHolder> policyHolders = FileManager.policyHolderReader();

        Label titleLabel = new Label("Information of Policy Holder");
        for (PolicyHolder policyHolder : policyHolders) {
            if (policyHolder.getId().equals(UserSession.getLoggedInUserId())) {
                Label idLabel = new Label("ID: " + policyHolder.getId());
                Label fullNameLabel = new Label("Full Name: " + policyHolder.getFullName());
                Label insuranceCardLabel = new Label("Insurance Card: " + policyHolder.getInsuranceCard());
                Label claimsLabel = new Label("Claims: " + policyHolder.getClaims());
                Label dependentsLabel = new Label("Dependents: " + policyHolder.getDependents());
                GridPane gridPane = new GridPane();
                gridPane.setAlignment(Pos.CENTER);
                gridPane.setVgap(20);
                gridPane.add(titleLabel, 0, 0, 2, 1);
                gridPane.add(idLabel, 0, 1);
                gridPane.add(fullNameLabel, 0, 2);
                gridPane.add(insuranceCardLabel, 0, 3);
                gridPane.add(claimsLabel, 0, 4);
                gridPane.add(dependentsLabel, 0, 5);


                Button backButton = new Button("Back to homepage");
                backButton.setOnAction(e -> {
                    UserSession.getStage().setScene(new Scene(policyHolderMenu(), 500, 300));
                });

                gridPane.add(backButton, 0, 6);

                UserSession.getStage().setScene(new Scene(gridPane, 500, 300));
            }
        }
    }

    public void createClaim() {
        ArrayList<PolicyHolder> policyHolders = FileManager.policyHolderReader();
        ArrayList<Claim> claims = FileManager.claimReader();

        Label titleLabel = new Label("Create Claim");
        titleLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
        titleLabel.setAlignment(Pos.CENTER);

        Label claimIdLabel = new Label("Claim ID (cl-xxxxxx): ");
        TextField claimIdField = new TextField();
        claimIdField.setPrefWidth(400);

        Label insuredPersonLabel = new Label("Insured Person: ");
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPrefWidth(400);
        ArrayList<String> selectionValue = new ArrayList<>();
        selectionValue.add(UserSession.getLoggedInUserId());
        for (PolicyHolder policyHolder : policyHolders) {
            if (policyHolder.getId().equals(UserSession.getLoggedInUserId())) {
                selectionValue.addAll(policyHolder.getDependents());
            }
        }
        // Add items to the ComboBox
        comboBox.setItems(FXCollections.observableArrayList(selectionValue));

        Label examDateLabel = new Label("Exam Date (dd/mm/yyyy): ");
        TextField examDateField = new TextField();
        examDateField.setPrefWidth(400);

        Label claimAmountLabel = new Label("Claim Amount: ");
        TextField claimAmountField = new TextField();
        claimAmountField.setPrefWidth(400);

        Label documentsLabel = new Label("Documents: ");
        TextField documentsField = new TextField();
        documentsField.setPrefWidth(400);

        Label bankingInfoLabel = new Label("Banking Information(Bank name - Bank account name - Bank account number): ");
        TextField bankingInfoField = new TextField();
        bankingInfoField.setPrefWidth(400);


        Button submitButton = new Button("Submit");
        submitButton.setPrefWidth(100);
        Button backButton = new Button("Back");
        backButton.setPrefWidth(100);
        Label warningLabel = new Label(); // Warning label for displaying error message
        warningLabel.setStyle("-fx-text-fill: red;");

        submitButton.setOnAction(e -> {

            if (claimIdField.getText().equals("")) {
                warningLabel.setText("Please enter the claim ID.");
                return;
            } else if (examDateField.getText().equals("")) {
                warningLabel.setText("Please enter the exam date.");
                return;
            } else if (claimAmountField.getText().equals("")) {
                warningLabel.setText("Please enter the claim amount.");
                return;
            } else if (documentsField.getText().equals("")) {
                warningLabel.setText("Please enter the documents.");
                return;
            } else if (bankingInfoField.getText().equals("")) {
                warningLabel.setText("Please enter the banking information.");
                return;
            }
            String id = claimIdField.getText();
            for (Claim claim : claims) {
                if (claim.getId().equals(id)) {
                    warningLabel.setText("Claim ID already exists. Please enter a different one.");
                    return;
                }
            }
            String insuredPerson = comboBox.getValue();
            Date currentDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            Date claimDate = calendar.getTime();
            Date examDate = null;
            try {
                examDate = new SimpleDateFormat("dd/MM/yyyy").parse(examDateField.getText());
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }

            String[] documentArray = documentsField.getText().split(",");
            ArrayList<String> documents = new ArrayList<>(Arrays.asList(documentArray));
            String insuranceCard = "";
            for (PolicyHolder policyHolder : policyHolders) {
                if (policyHolder.getId().equals(UserSession.getLoggedInUserId())) {
                    if (policyHolder.getInsuranceCard().equals("")) {
                        warningLabel.setText("Please add your insurance card information before creating a claim.");
                        return;
                    } else {
                        insuranceCard = policyHolder.getInsuranceCard();
                    }
                }
            }
            double claimAmount = Double.parseDouble(claimAmountField.getText());
            String status = "Pending";
            String bankingInfo = bankingInfoField.getText();

            Claim claim = new Claim(id, claimDate, insuredPerson, insuranceCard, examDate, documents, claimAmount, status, bankingInfo);
            claims.add(claim);
            FileManager.claimWriter(claims);
            UserSession.getStage().setScene(new Scene(policyHolderMenu(), 500, 300));
        });

        backButton.setOnAction(e -> {
            PolicyHolderManager policyHolderManager = new PolicyHolderManager();
            UserSession.getStage().setScene(new Scene(policyHolderManager.policyHolderMenu(), 500, 300));
        });

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(20);
        gridPane.add(titleLabel, 0, 0, 2, 1);
        gridPane.add(claimIdLabel, 0, 1);
        gridPane.add(claimIdField, 1, 1);
        gridPane.add(insuredPersonLabel, 0, 2);
        gridPane.add(comboBox, 1, 2);
        gridPane.add(examDateLabel, 0, 3);
        gridPane.add(examDateField, 1, 3);
        gridPane.add(claimAmountLabel, 0, 4);
        gridPane.add(claimAmountField, 1, 4);
        gridPane.add(documentsLabel, 0, 5);
        gridPane.add(documentsField, 1, 5);
        gridPane.add(bankingInfoLabel, 0, 6);
        gridPane.add(bankingInfoField, 1, 6);
        gridPane.add(submitButton, 0, 7);
        gridPane.add(backButton, 1, 7);
        gridPane.add(warningLabel, 0, 8, 2, 1);

        UserSession.getStage().setScene(new Scene(gridPane, 900, 600));
    }

    public void viewClaim() {
        ArrayList<Claim> claims = FileManager.claimReader();
        ArrayList<PolicyHolder> policyHolders = FileManager.policyHolderReader();
        String poInsuranceCard = "";
        for (PolicyHolder policyHolder : policyHolders) {
            if (policyHolder.getId().equals(UserSession.getLoggedInUserId())) {
                poInsuranceCard = policyHolder.getInsuranceCard();
            }
        }
        ArrayList<Claim> displayClaims = new ArrayList<>();
        for (Claim claim : claims) {
            if (claim.getCardNumber().equals(poInsuranceCard)) {
                displayClaims.add(claim);
            }
        }

        VBox vbox = new VBox();
        vbox.setSpacing(10);

        int counter = 1;
        for (Claim claim : displayClaims) {
            Label claimLabel = new Label("Claim " + counter);
            Label idLabel = new Label("ID: " + claim.getId());
            Label claimDateLabel = new Label("Claim Date: " + claim.getClaimDate());
            Label insuredPersonLabel = new Label("Insured Person: " + claim.getInsuredPerson());
            Label cardNumberLabel = new Label("Card Number: " + claim.getCardNumber());
            Label examDateLabel = new Label("Exam Date: " + claim.getExamDate());
            Label claimAmountLabel = new Label("Claim Amount: " + claim.getClaimAmount());
            Label statusLabel = new Label("Status: " + claim.getStatus());
            Label bankingInfoLabel = new Label("Banking Info: " + claim.getBankingInfo());
            Button updateButton = new Button("Update");
            updateButton.setOnAction(e -> updateClaim(claim.getId()));
            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(e -> deleteClaim(claim.getId()));
            GridPane button = new GridPane();
            button.add(updateButton, 0, 0);
            button.add(deleteButton, 1, 0);
            vbox.getChildren().addAll(claimLabel, idLabel, claimDateLabel, insuredPersonLabel, cardNumberLabel, examDateLabel, claimAmountLabel, statusLabel, bankingInfoLabel);
            vbox.getChildren().add(button);

            counter++;
        }

        Button backButton = new Button("Back");
        backButton.setPrefWidth(250);
        backButton.setAlignment(Pos.CENTER);
        backButton.setOnAction(e -> {
            UserSession.getStage().setScene(new Scene(policyHolderMenu(), 500, 300));
        });

        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(new Label("Claim Information"));
        borderPane.setCenter(scrollPane);
        borderPane.setBottom(backButton);

        UserSession.getStage().setScene(new Scene(borderPane, 500, 300));
    }

    private void updateClaim(String claimID) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Update Claim");

        ArrayList<Claim> claims = FileManager.claimReader();
        for (Claim claim : claims) {
            if (claim.getId().equals(claimID)) {
                Label titleLabel = new Label("Update Claim");
                titleLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
                titleLabel.setAlignment(Pos.CENTER);
                Label claimId = new Label("Claim ID: " + claim.getId());
                Label insuredPerson = new Label("Insured Person: " + claim.getInsuredPerson());
                Label claimDate = new Label("Claim Date: " + claim.getClaimDate());
                Label cardNumber = new Label("Card Number: " + claim.getCardNumber());
                Label examDate = new Label("Exam Date: ");
                TextField examDateField = new TextField();
                examDateField.setPrefWidth(400);
                examDateField.setText(new SimpleDateFormat("dd/MM/yyyy").format(claim.getExamDate()));
                Label claimAmount = new Label("Claim Amount: ");
                TextField claimAmountField = new TextField();
                claimAmountField.setPrefWidth(400);
                claimAmountField.setText(String.valueOf(claim.getClaimAmount()));
                Label documents = new Label("Documents: ");
                TextField documentsField = new TextField();
                documentsField.setPrefWidth(400);
                documentsField.setText(String.join(",", claim.getDocuments()));
                Label bankingInfo = new Label("Banking Information: ");
                TextField bankingInfoField = new TextField();
                bankingInfoField.setPrefWidth(400);
                bankingInfoField.setText(claim.getBankingInfo());
                Button updateButton = new Button("Update");
                updateButton.setPrefWidth(100);
                Button backButton = new Button("Back");
                backButton.setPrefWidth(100);
                Label warningLabel = new Label(); // Warning label for displaying error message
                backButton.setOnAction(e -> {
                    popupStage.close();
                    viewClaim();
                });
                warningLabel.setStyle("-fx-text-fill: red;");

                updateButton.setOnAction(e -> {
                    if (examDateField.getText().equals("")) {
                        warningLabel.setText("Please enter the exam date.");
                        return;
                    } else if (claimAmountField.getText().equals("")) {
                        warningLabel.setText("Please enter the claim amount.");
                        return;
                    } else if (documentsField.getText().equals("")) {
                        warningLabel.setText("Please enter the documents.");
                        return;
                    } else if (bankingInfoField.getText().equals("")) {
                        warningLabel.setText("Please enter the banking information.");
                        return;
                    }
                    Date newExamDate = null;
                    try {
                        newExamDate = new SimpleDateFormat("dd/MM/yyyy").parse(examDateField.getText());
                    } catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }
                    double newClaimAmount = Double.parseDouble(claimAmountField.getText());
                    String[] documentArray = documentsField.getText().split(",");
                    ArrayList<String> documentsList = new ArrayList<>(Arrays.asList(documentArray));
                    String newBankingInfo = bankingInfoField.getText();
                    for (Claim claimToUpdate : claims) {
                        if (claimToUpdate.getId().equals(claimID)) {
                            claimToUpdate.setExamDate(newExamDate);
                            claimToUpdate.setClaimAmount(newClaimAmount);
                            claimToUpdate.setDocuments(documentsList);
                            claimToUpdate.setBankingInfo(newBankingInfo);
                        }
                    }
                    FileManager.claimWriter(claims);
                    popupStage.close();
                    viewClaim();
                });

                GridPane gridPane = new GridPane();
                gridPane.setAlignment(Pos.CENTER);
                gridPane.setVgap(20);
                gridPane.add(titleLabel, 0, 0, 2, 1);
                gridPane.add(claimId, 0, 1);
                gridPane.add(insuredPerson, 0, 2);
                gridPane.add(claimDate, 0, 3);
                gridPane.add(cardNumber, 0, 4);
                gridPane.add(examDate, 0, 5);
                gridPane.add(examDateField, 1, 5);
                gridPane.add(claimAmount, 0, 6);
                gridPane.add(claimAmountField, 1, 6);
                gridPane.add(documents, 0, 7);
                gridPane.add(documentsField, 1, 7);
                gridPane.add(bankingInfo, 0, 8);
                gridPane.add(bankingInfoField, 1, 8);
                gridPane.add(updateButton, 0, 9);
                gridPane.add(backButton, 1, 9);
                gridPane.add(warningLabel, 0, 10, 2, 1);

                Scene scene = new Scene(gridPane, 800, 600);
                popupStage.setScene(scene);
                popupStage.show();
                return;
            }
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Claim not found");
        alert.setContentText("Claim with ID " + claimID + " not found.");
        alert.showAndWait();
    }

    private void deleteClaim(String claimID) {
        ArrayList<Claim> claims = FileManager.claimReader();
        for (Claim claim : claims) {
            if (claim.getId().equals(claimID)) {
                claims.remove(claim);
                FileManager.claimWriter(claims);
                viewClaim();
                return;
            }
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Claim not found");
        alert.setContentText("Claim with ID " + claimID + " not found.");
        alert.showAndWait();
    }

    public void viewDependent() {
        ArrayList<Dependent> dependents = FileManager.dependentReader();
        ArrayList<PolicyHolder> policyHolders = FileManager.policyHolderReader();
        ArrayList<Dependent> displayDependents = new ArrayList<>();
        for (PolicyHolder policyHolder : policyHolders) {
            if (policyHolder.getId().equals(UserSession.getLoggedInUserId())) {
                for (String dependentId : policyHolder.getDependents()) {
                    for (Dependent dependent : dependents) {
                        if (dependent.getId().equals(dependentId)) {
                            displayDependents.add(dependent);
                        }
                    }
                }
            }
        }

        VBox vbox = new VBox();
        vbox.setSpacing(10);

        int counter = 1;
        for (Dependent dependent : displayDependents) {
            Label dependentLabel = new Label(counter + ") " + "Dependent: " + dependent.toString());
            Button updateButton = new Button("Update");
            updateButton.setOnAction(e -> editDependent(dependent.getId()));
            Button removeButton = new Button("Remove");
            removeButton.setOnAction(e -> removeDependent(dependent.getId()));
            GridPane button = new GridPane();
            button.add(updateButton, 0, 0);
            button.add(removeButton, 1, 0);
            vbox.getChildren().addAll(dependentLabel);
            vbox.getChildren().add(button);
        }

        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            UserSession.getStage().setScene(new Scene(policyHolderMenu(), 500, 300));
        });

        Button addDependent = new Button("Add dependent");
        addDependent.setOnAction(e -> {
            addDependent();
        });

        GridPane gridPane = new GridPane();
        gridPane.add(backButton, 0, 0);
        gridPane.add(addDependent, 1, 0);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(new Label("Dependent Information"));
        borderPane.setCenter(scrollPane);
        borderPane.setBottom(gridPane);


        UserSession.getStage().setScene(new Scene(borderPane, 500, 300));
    }

    public void addDependent() {
        ArrayList<Dependent> dependents = FileManager.dependentReader();
        ArrayList<PolicyHolder> policyHolders = FileManager.policyHolderReader();
        ArrayList<Dependent> displayDependent = new ArrayList<>();
        for (Dependent dependent : dependents) {
            if (dependent.getPolicyHolder().equals("")) {
                displayDependent.add(dependent);
            }
        }
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Add dependent");

        VBox vbox = new VBox();
        vbox.setSpacing(10);

        int counter = 1;
        for (Dependent dependent : displayDependent) {
            Label dependentLabel = new Label(counter + ") Dependent: " + dependent.toString());
            Button addButton = new Button("Add");
            addButton.setOnAction(e -> {
                for (PolicyHolder policyHolder : policyHolders) {
                    if (policyHolder.getId().equals(UserSession.getLoggedInUserId())) {
                        policyHolder.getDependents().add(dependent.getId());
                        dependent.setPolicyHolder(UserSession.getLoggedInUserId());
                        if (policyHolder.getInsuranceCard().equals("")) {
                            dependent.setInsuranceCard(policyHolder.getInsuranceCard());
                        }
                        FileManager.policyHolderWriter(policyHolders);
                        FileManager.dependentWriter(dependents);
                        popupStage.close();
                        viewDependent();
                    }
                }
            });
            vbox.getChildren().addAll(dependentLabel);
            vbox.getChildren().add(addButton);
            counter++;
        }

        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            popupStage.close();
            viewDependent();
        });

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(new Label("Add dependent"));
        borderPane.setCenter(scrollPane);
        borderPane.setBottom(backButton);

        Scene scene = new Scene(borderPane, 500, 300);
        popupStage.setScene(scene);
        popupStage.show();
    }

    public void editDependent(String id) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Update Dependent");

        ArrayList<Dependent> dependents = FileManager.dependentReader();
        for (Dependent dependent : dependents) {
            if (dependent.getId().equals(id)) {
                Label titleLabel = new Label("Edit Dependent");
                titleLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
                titleLabel.setAlignment(Pos.CENTER);
                Label idLabel = new Label("ID: " + dependent.getId());
                Label fullNameLabel = new Label("Full Name: ");
                TextField fullNameField = new TextField();
                fullNameField.setPrefWidth(400);
                fullNameField.setText(dependent.getFullName());
                Button updateButton = new Button("Update");
                updateButton.setPrefWidth(100);
                Button backButton = new Button("Back");
                backButton.setPrefWidth(100);
                Label warningLabel = new Label(); // Warning label for displaying error message
                warningLabel.setStyle("-fx-text-fill: red;");

                updateButton.setOnAction(e -> {
                    if (fullNameField.getText().equals("")) {
                        warningLabel.setText("Please enter the full name.");
                        return;
                    }
                    for (Dependent dependentToUpdate : dependents) {
                        if (dependentToUpdate.getId().equals(id)) {
                            dependentToUpdate.setFullName(fullNameField.getText());
                        }
                    }
                    FileManager.dependentWriter(dependents);
                    popupStage.close();
                    viewDependent();
                });

                backButton.setOnAction(e -> {
                    popupStage.close();
                    viewDependent();
                });

                GridPane gridPane = new GridPane();
                gridPane.setAlignment(Pos.CENTER);
                gridPane.setVgap(20);
                gridPane.add(titleLabel, 0, 0, 2, 1);
                gridPane.add(idLabel, 0, 1);
                gridPane.add(fullNameLabel, 0, 2);
                gridPane.add(fullNameField, 1, 2);
                gridPane.add(updateButton, 0, 3);
                gridPane.add(backButton, 1, 3);
                gridPane.add(warningLabel, 0, 4, 2, 1);

                Scene scene = new Scene(gridPane, 500, 300);
                popupStage.setScene(scene);
                popupStage.show();
                viewDependent();
            }
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Dependent not found");
            alert.setContentText("Dependent with ID " + id + " not found.");
            alert.showAndWait();
        }
    }

    public void removeDependent(String id) {
        ArrayList<Dependent> dependents = FileManager.dependentReader();
        ArrayList<PolicyHolder> policyHolders = FileManager.policyHolderReader();
        for (PolicyHolder policyHolder : policyHolders) {
            if (policyHolder.getId().equals(UserSession.getLoggedInUserId())) {
                policyHolder.getDependents().remove(id);
                FileManager.policyHolderWriter(policyHolders);
                break;
            }
        }
        for (Dependent dependent : dependents) {
            if (dependent.getId().equals(id)) {
                dependent.setPolicyHolder("");
                dependent.setInsuranceCard("");
                FileManager.dependentWriter(dependents);
                viewDependent();
                return;
            }
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Dependent not found");
        alert.setContentText("Dependent with ID " + id + " not found.");
        alert.showAndWait();
    }
}
