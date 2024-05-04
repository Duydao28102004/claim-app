package com.example.claimapp.Customer;

import com.example.claimapp.Authentication;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PolicyHolderManager {
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

        grid.add(addClaim, 0, 1);
        grid.add(updateClaim, 0, 2);
        grid.add(retrieveClaims, 0, 3);
        grid.add(updateInfo, 0, 4);

        return grid;
    }
}
