package com.example.claimapp.Customer;

import com.example.claimapp.Authentication;
import com.example.claimapp.Claim;
import com.example.claimapp.FileManager;
import com.example.claimapp.UserSession;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class DependentManager {
    public GridPane dependentMenu() {
        Label titleLabel = new Label("Dependent Manager");
        titleLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
        titleLabel.setAlignment(Pos.CENTER);

        Button informationOfDependentButton = new Button("Get information of dependent");
        informationOfDependentButton.setPrefWidth(250);
        informationOfDependentButton.setAlignment(Pos.CENTER);
        informationOfDependentButton.setOnAction(e -> informationOfDependent());

        Button viewClaimButton = new Button("View claim");
        viewClaimButton.setPrefWidth(250);
        viewClaimButton.setAlignment(Pos.CENTER);
        viewClaimButton.setOnAction(e -> viewClaim());

        Button logoutButton = new Button("Logout");
        logoutButton.setPrefWidth(250);
        logoutButton.setAlignment(Pos.CENTER);
        logoutButton.setOnAction(e -> {
            // Handle logout action here
            System.out.println("Logging out...");
            UserSession.getStage().setScene(new Scene(new Authentication().loginPane(), 500, 300));
        });

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(20);
        gridPane.add(titleLabel, 0, 0);
        gridPane.add(informationOfDependentButton, 0, 1);
        gridPane.add(viewClaimButton, 0, 2);
        gridPane.add(logoutButton, 0, 3);

        return gridPane;
    }

    public void viewClaim() {
        ArrayList<Claim> claims = FileManager.claimReader();

        Label titleLabel = new Label("Claim Information");
        titleLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
        titleLabel.setAlignment(Pos.CENTER);

        ArrayList<Claim> displayClaims = new ArrayList<>();
        for (Claim claim : claims) {
            if (claim.getInsuredPerson().equals(UserSession.getLoggedInUserId())) {
                displayClaims.add(claim);
            }
        }

        // Create a VBox to hold the Labels
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

            vbox.getChildren().addAll(claimLabel, idLabel, claimDateLabel, insuredPersonLabel, cardNumberLabel, examDateLabel, claimAmountLabel, statusLabel, bankingInfoLabel);
            counter++;
        }

        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);

        Button backButton = new Button("Back");
        backButton.setPrefWidth(250);
        backButton.setAlignment(Pos.CENTER);
        backButton.setOnAction(e -> {
            UserSession.getStage().setScene(new Scene(dependentMenu(), 500, 300));
        });

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(titleLabel);
        borderPane.setCenter(scrollPane);
        borderPane.setBottom(backButton);

        UserSession.getStage().setScene(new Scene(borderPane, 500, 300));
    }

    public void informationOfDependent() {
        ArrayList<Dependent> dependents = FileManager.dependentReader();
        for (Dependent dependent : dependents) {
            if (dependent.getId().equals(UserSession.getLoggedInUserId())) {
                Label titleLabel = new Label("Information of Dependent");
                titleLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
                titleLabel.setAlignment(Pos.CENTER);

                Label idLabel = new Label("ID: ");
                Label nameLabel = new Label("Name: ");
                Label insuranceCardLabel = new Label("Insurance Card: ");
                Label policyHolderLabel = new Label("Policy Holder: ");

                Label idValueLabel = new Label(dependent.getId());
                Label nameValueLabel = new Label(dependent.getFullName());
                Label insuranceCardValueLabel = new Label(dependent.getInsuranceCard());
                Label policyHolderValueLabel = new Label(dependent.getPolicyHolder());


                Button backButton = new Button("Back");
                backButton.setPrefWidth(250);
                backButton.setAlignment(Pos.CENTER);
                backButton.setOnAction(e -> {
                    UserSession.getStage().setScene(new Scene(dependentMenu(), 500, 300));
                });


                GridPane gridPane = new GridPane();
                gridPane.setAlignment(Pos.CENTER);
                gridPane.setVgap(20);
                gridPane.add(titleLabel, 0, 0);
                gridPane.add(idLabel, 0, 1);
                gridPane.add(idValueLabel, 1, 1);
                gridPane.add(nameLabel, 0, 2);
                gridPane.add(nameValueLabel, 1, 2);
                gridPane.add(insuranceCardLabel, 0, 3);
                gridPane.add(insuranceCardValueLabel, 1, 3);
                gridPane.add(policyHolderLabel, 0, 4);
                gridPane.add(policyHolderValueLabel, 1, 4);
                gridPane.add(backButton, 0, 5);

                UserSession.getStage().setScene(new Scene(gridPane, 800, 600));
            }
        }
    }
}
