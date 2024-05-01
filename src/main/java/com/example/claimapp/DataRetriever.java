package com.example.claimapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DataRetriever {
    public List<Claim> retrieveAllClaimsFromDatabase() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM your_table";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Process each row of the result set
                String column1Value = resultSet.getString("column1");
                // Retrieve other columns similarly
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
