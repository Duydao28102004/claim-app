package com.example.claimapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataManager {
    // Other methods...

    public List<Claim> getAllClaims() {
        List<Claim> claims = new ArrayList<>();

        // Establish a connection to the database
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM claims"; // Assuming 'claims' is the table name
            PreparedStatement statement = connection.prepareStatement(query);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Process the result set
            while (resultSet.next()) {
                // Retrieve data from the result set and create Claim objects
                String id = resultSet.getString("id");
                Date claimDate = resultSet.getDate("claim_date");
                // Retrieve other claim properties...

                // Create a new Claim object and add it to the list
                Claim claim = new Claim(id, claimDate, );
                claims.add(claim);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database-related exceptions
        }

        return claims;
    }

    public void addSampleClaim() {
        // Sample data for testing
        Claim sampleClaim = new Claim();
        sampleClaim.setId("12345");
        sampleClaim.setClaimDate(new Date());
        // Set other properties...

        // Add the sample claim to the database
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO claims (id, claim_date /*, other columns...*/) VALUES (?, ? /*, ?...*/)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, sampleClaim.getId());
            statement.setDate(2, new java.sql.Date(sampleClaim.getClaimDate().getTime()));
            // Set other parameters...

            // Execute the query
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Sample claim added successfully.");
            } else {
                System.out.println("Failed to add sample claim.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database-related exceptions
        }
    }
}
