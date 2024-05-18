package com.example.claimapp;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Comparator;

public class InsuranceManagerManager {
    public void insuranceManagerMenu(ArrayList<Claim> claims) {

        VBox vbox = new VBox();
        vbox.setSpacing(10);

        int counter = 1;
        for (Claim claim : claims) {
            if (claim.getStatus().equals("Processing")) {
                Label claimLabel = new Label("Claim " + counter + ": " + claim);
                Button approveButton = new Button("Approve");
                approveButton.setOnAction(e -> {
                    claim.setStatus("Completed");
                    FileManager.claimWriter(claims);
                    insuranceManagerMenu();
                });
                vbox.getChildren().add(claimLabel);
                vbox.getChildren().add(approveButton);
                counter++;
            }
        }

        TextField searchField = new TextField();
        searchField.setPromptText("Search by ID");
        Button searchButton = new Button("Search");
        searchButton.setPrefWidth(180);
        searchButton.setOnAction(e -> {
            String searchID = searchField.getText();
            vbox.getChildren().clear();
            for (Claim claim : claims) {
                if (claim.getId().contains(searchID) && claim.getStatus().equals("Processing")) {
                    Label claimLabel = new Label("Claim: " + claim);
                    Button approveButton = new Button("Approve");
                    approveButton.setOnAction(e2 -> {
                        claim.setStatus("Completed");
                        FileManager.claimWriter(claims);
                        insuranceManagerMenu();
                    });
                    vbox.getChildren().add(claimLabel);
                    vbox.getChildren().add(approveButton);
                }
            }
            if (searchID.isEmpty()) {
                insuranceManagerMenu();
            } else if (vbox.getChildren().isEmpty()) {
                vbox.getChildren().clear();
                Label notFoundLabel = new Label("Claim not found or already processed.");
                vbox.getChildren().add(notFoundLabel);
            }
        });
        searchField.setPrefWidth(180);
        searchField.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ENTER")) {
                searchButton.fire();
            }
        });

        Button sortByOldestDateButton = new Button("Sort by Oldest Date");
        sortByOldestDateButton.setPrefWidth(180);
        sortByOldestDateButton.setOnAction(e -> {
            claims.sort(Comparator.comparing(Claim::getClaimDate));
            insuranceManagerMenu(claims);
        });

        Button sortByNewestDateButton = new Button("Sort by Newest Date");
        sortByNewestDateButton.setPrefWidth(180);
        sortByNewestDateButton.setOnAction(e -> {
            claims.sort((c1, c2) -> c2.getClaimDate().compareTo(c1.getClaimDate()));
            insuranceManagerMenu(claims);
        });

        Button resetButton = new Button("Reset");
        resetButton.setPrefWidth(180);
        resetButton.setOnAction(e -> {
            insuranceManagerMenu();
        });

        Button logoutButton = new Button("Logout");
        logoutButton.setPrefWidth(180);
        logoutButton.setOnAction(e -> {
            UserSession.getStage().setScene(new Scene(new Authentication().loginPane(), 500, 300));
        });
        logoutButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vbox);
        scrollPane.setFitToWidth(true);
        GridPane buttons = new GridPane();
        buttons.add(searchField, 0, 0);
        buttons.add(searchButton, 0, 1);
        buttons.add(sortByOldestDateButton, 0, 2);
        buttons.add(sortByNewestDateButton, 0, 3);
        buttons.add(resetButton, 0, 4);
        buttons.add(logoutButton, 0, 5);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(scrollPane);
        borderPane.setLeft(buttons);

        Scene scene = new Scene(borderPane, 800, 600);
        UserSession.getStage().setScene(scene);
    }

    public void insuranceManagerMenu() {
        ArrayList<Claim> claims = FileManager.claimReader();
        insuranceManagerMenu(claims);
    }
}
