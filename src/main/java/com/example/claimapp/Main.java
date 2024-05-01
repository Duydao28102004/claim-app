package com.example.claimapp;

import com.example.claimapp.Customer.Dependent;
import com.example.claimapp.Customer.PolicyHolder;
import com.example.claimapp.Provider.Provider;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FileManager fileManager = new FileManager();
        ArrayList<Dependent> dependents = new ArrayList<>();
        ArrayList<InsuranceCard> insuranceCards = new ArrayList<>();
        ArrayList<PolicyHolder> policyHolders = new ArrayList<>();
        ArrayList<Claim> claims = new ArrayList<>();
        Authentication authentication = new Authentication();
        GridPane loginPane = authentication.loginPane();
        Provider provider = new Provider();
        GridPane providerPane = provider.providerPane();

        // Create scene and set it on the stage
        Scene scene = new Scene(loginPane, 500, 300);
        stage.setTitle("Claim Management System - Authentication");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}