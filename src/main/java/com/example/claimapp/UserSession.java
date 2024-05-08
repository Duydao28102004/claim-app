package com.example.claimapp;

import javafx.stage.Stage;

public class UserSession {
    private static String loggedInUserId;
    private static Stage stage;

    // Method to set the logged-in user ID
    public static void setLoggedInUserId(String userId) {
        loggedInUserId = userId;
    }

    // Method to get the logged-in user ID
    public static String getLoggedInUserId() {
        return loggedInUserId;
    }

    // Method to set the stage
    public static void setStage(Stage stage) {
        UserSession.stage = stage;
    }

    // Method to get the stage
    public static Stage getStage() {
        return stage;
    }
}