package com.example.claimapp;

import com.example.claimapp.Customer.Dependent;
import com.example.claimapp.Customer.PolicyHolder;


import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class FileManager {
    private static String jdbcUrl;

    public FileManager(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public static void policyHolderWriter(ArrayList<PolicyHolder> policyHolders) {
        // First, delete all existing rows
        String deleteSql = "DELETE FROM PolicyHolder";

        // Then, insert new rows
        String insertSql = "INSERT INTO PolicyHolder (id, fullName, insuranceCard, claims, dependents) VALUES (?, ?, ?, ?, ?)";

        try (
                Connection conn = DriverManager.getConnection(jdbcUrl);
                PreparedStatement deleteStatement = conn.prepareStatement(deleteSql);
                PreparedStatement insertStatement = conn.prepareStatement(insertSql)
        ) {
            // Execute the delete statement
            deleteStatement.executeUpdate();

            // Now, insert new rows
            for (PolicyHolder policyHolder : policyHolders) {
                ArrayList<String> claims = policyHolder.getClaims();
                String claimsString = String.join(",", claims);

                ArrayList<String> dependents = policyHolder.getDependents();
                String dependentsString = String.join(",", dependents);

                insertStatement.setString(1, policyHolder.getId());
                insertStatement.setString(2, policyHolder.getFullName());
                insertStatement.setString(3, policyHolder.getInsuranceCard());
                insertStatement.setString(4, claimsString);
                insertStatement.setString(5, dependentsString);

                insertStatement.addBatch(); // Add the prepared statement to the batch
            }

            insertStatement.executeBatch(); // Execute the batch of prepared statements
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<PolicyHolder> policyHolderReader() {
        ArrayList<PolicyHolder> policyHolders = new ArrayList<>();

        String selectSql = "SELECT id, fullName, insuranceCard, claims, dependents FROM PolicyHolder";

        try (Connection conn = DriverManager.getConnection(jdbcUrl);
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSql)) {

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String fullName = resultSet.getString("fullName");
                String insuranceCard = resultSet.getString("insuranceCard");
                String claimsString = resultSet.getString("claims");
                String dependentsString = resultSet.getString("dependents");

                ArrayList<String> claims = new ArrayList<>(Arrays.asList(insuranceCard.split(",")));
                ArrayList<String> dependents = new ArrayList<>(Arrays.asList(dependentsString.split(",")));

                PolicyHolder policyHolder = new PolicyHolder(id, fullName, insuranceCard, claims, dependents);

                policyHolders.add(policyHolder);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return policyHolders;
    }

    public static void dependentWriter(ArrayList<Dependent> dependents) {
        // First, delete all existing rows
        String deleteSql = "DELETE FROM Dependent";

        // Then, insert new rows
        String insertSql = "INSERT INTO Dependent (id, fullName, insuranceCard, claims, policyHolder) VALUES (?, ?, ?, ?, ?)";

        try (
                Connection conn = DriverManager.getConnection(jdbcUrl);
                PreparedStatement deleteStatement = conn.prepareStatement(deleteSql);
                PreparedStatement insertStatement = conn.prepareStatement(insertSql)
        ) {
            // Execute the delete statement
            deleteStatement.executeUpdate();

            // Now, insert new rows
            for (Dependent dependent : dependents) {
                ArrayList<String> claims = dependent.getClaims();
                String claimsString = String.join(",", claims);

                insertStatement.setString(1, dependent.getId());
                insertStatement.setString(2, dependent.getFullName());
                insertStatement.setString(3, dependent.getInsuranceCard());
                insertStatement.setString(4, claimsString);
                insertStatement.setString(5, dependent.getPolicyHolder());

                insertStatement.addBatch(); // Add the prepared statement to the batch
            }

            insertStatement.executeBatch(); // Execute the batch of prepared statements
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Dependent> dependentReader() {
        ArrayList<Dependent> dependents = new ArrayList<>();

        String selectSql = "SELECT id, fullName, insuranceCard, claims, policyHolder FROM Dependent";

        try (Connection conn = DriverManager.getConnection(jdbcUrl);
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSql)) {

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String fullName = resultSet.getString("fullName");
                String insuranceCard = resultSet.getString("insuranceCard");
                String claimsString = resultSet.getString("claims");
                String policyHolder = resultSet.getString("policyHolder");

                ArrayList<String> claims = new ArrayList<>(Arrays.asList(claimsString.split(",")));

                Dependent dependent = new Dependent(id, fullName, insuranceCard, claims, policyHolder);

                dependents.add(dependent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dependents;
    }

}





