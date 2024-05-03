package com.example.claimapp;

import com.example.claimapp.Customer.Dependent;
import com.example.claimapp.Customer.PolicyHolder;
import com.example.claimapp.Provider.InsuranceManagerController;
import com.example.claimapp.Provider.InsuranceSurveyorController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
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
        InsuranceManagerController insuranceManagerController = new InsuranceManagerController();
        GridPane insuranceManagerPane = insuranceManagerController.insuranceManagerPane();
        InsuranceSurveyorController insuranceSurveyorController = new InsuranceSurveyorController();
        GridPane insuranceSurveyorPane = insuranceSurveyorController.insuranceSurveyorPane();




        // Create scene and set it on the stage
        Scene scene = new Scene(insuranceManagerPane, 500, 300);
        stage.setTitle("Claim Management System - Authentication");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}