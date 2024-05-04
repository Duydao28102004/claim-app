package com.example.claimapp;

import com.example.claimapp.Authentication;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PolicyOwnerManager {
    public GridPane policyOwnerMenu(Stage stage) {
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
            stage.setScene(new Scene(new Authentication().loginPane(stage), 500, 300));
        });
        logout.setAlignment(Pos.CENTER); // Align the button's content to the center
        policyOwnerMenu.add(logout, 0, 5);

        return policyOwnerMenu;
    }

//    public GridPane viewPolicyHolders(Stage stage) {
//        FileManager fileManager = new FileManager("jdbc:postgresql://ep-bitter-firefly-a1234bmn.ap-southeast-1.aws.neon.tech/claim-app?user=claim-app_owner&password=VZw2xWjlAL");
//    }
}
