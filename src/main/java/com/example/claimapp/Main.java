package com.example.claimapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        UserSession.setStage(stage);
        FileManager fileManager = new FileManager("jdbc:postgresql://ep-bitter-firefly-a1234bmn.ap-southeast-1.aws.neon.tech/claim-app?user=claim-app_owner&password=VZw2xWjlAL7C&sslmode=require");
        Authentication authentication = new Authentication();
        // Create scene and set it on the stage
        Scene scene = new Scene(authentication.loginPane(), 500, 300);
        UserSession.getStage().setTitle("Claim Management System - Authentication");
        UserSession.getStage().setScene(scene);
        UserSession.getStage().show();
    }

    public static void main(String[] args) {
        launch();
    }
}