package com.example.claimapp;

import com.example.claimapp.Customer.Dependent;
import com.example.claimapp.Customer.PolicyHolder;
import com.example.claimapp.Provider.InsuranceManagerController;
import com.example.claimapp.Provider.InsuranceSurveyorController;
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
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FileManager fileManager = new FileManager("jdbc:postgresql://ep-bitter-firefly-a1234bmn.ap-southeast-1.aws.neon.tech/claim-app?user=claim-app_owner&password=VZw2xWjlAL7C&sslmode=require");
        ArrayList<Claim> claims = FileManager.claimReader();
        for (Claim claim : claims) {
            System.out.println(claim);
        }
        Authentication authentication = new Authentication();
        InsuranceManagerController insuranceManagerController = new InsuranceManagerController();
        InsuranceSurveyorController insuranceSurveyorController = new InsuranceSurveyorController();
        // Create scene and set it on the stage
        Scene scene = new Scene(authentication.loginPane(stage), 500, 300);
        Scene scene2 = new Scene(insuranceManagerController.insuranceManagerPane(), 900, 600);
        Scene scene3 = new Scene(insuranceSurveyorController.insuranceSurveyorPane(), 900, 600);
        stage.setTitle("Claim Management System - Authentication");
        stage.setScene(scene3);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}