package com.example.claimapp;

import com.example.claimapp.Customer.Dependent;
import com.example.claimapp.Customer.PolicyHolder;


import java.sql.*;
import java.text.SimpleDateFormat;
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

                ArrayList<String> claims = new ArrayList<>(Arrays.asList(claimsString.split(",")));
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

    public static void claimWriter(ArrayList<Claim> claims) {
        // First, delete all existing rows
        String deleteSql = "DELETE FROM Claim";

        // Then, insert new rows
        String insertSql = "INSERT INTO Claim (id, claimDate, insuredPerson, cardNumber, examDate, documents, claimAmount, status, bankingInfo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection conn = DriverManager.getConnection(jdbcUrl);
                PreparedStatement deleteStatement = conn.prepareStatement(deleteSql);
                PreparedStatement insertStatement = conn.prepareStatement(insertSql)
        ) {
            // Execute the delete statement
            deleteStatement.executeUpdate();

            // Now, insert new rows
            for (Claim claim : claims) {
                insertStatement.setString(1, claim.getId());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = dateFormat.format(claim.getClaimDate());
                insertStatement.setString(2, dateString);
                insertStatement.setString(3, claim.getInsuredPerson());
                insertStatement.setString(4, claim.getCardNumber());
                dateString = dateFormat.format(claim.getExamDate());
                insertStatement.setString(5, dateString);
                String documentsString = String.join(",", claim.getDocuments());
                insertStatement.setString(6, documentsString);
                insertStatement.setDouble(7, claim.getClaimAmount());
                insertStatement.setString(8, claim.getStatus());
                insertStatement.setString(9, claim.getBankingInfo());

                insertStatement.addBatch(); // Add the prepared statement to the batch
            }

            insertStatement.executeBatch(); // Execute the batch of prepared statements
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Claim> claimReader() {
        ArrayList<Claim> claims = new ArrayList<>();

        String selectSql = "SELECT id, claimDate, insuredPerson, cardNumber, examDate, documents, claimAmount, status, bankingInfo FROM Claim";

        try (Connection conn = DriverManager.getConnection(jdbcUrl);
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSql)) {

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String claimDateString = resultSet.getString("claimDate");
                String insuredPerson = resultSet.getString("insuredPerson");
                String cardNumber = resultSet.getString("cardNumber");
                String examDateString = resultSet.getString("examDate");
                String documentsString = resultSet.getString("documents");
                double claimAmount = resultSet.getDouble("claimAmount");
                String status = resultSet.getString("status");
                String bankingInfo = resultSet.getString("bankingInfo");

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                java.util.Date claimDate = dateFormat.parse(claimDateString);
                java.util.Date examDate = dateFormat.parse(examDateString);

                ArrayList<String> documents = new ArrayList<>(Arrays.asList(documentsString.split(",")));

                Claim claim = new Claim(id, claimDate, insuredPerson, cardNumber, examDate, documents, claimAmount, status, bankingInfo);

                claims.add(claim);
            }
        } catch (SQLException | java.text.ParseException e) {
            e.printStackTrace();
        }

        return claims;
    }

    public static void insuranceCardWriter(ArrayList<InsuranceCard> insuranceCards) {
        // First, delete all existing rows
        String deleteSql = "DELETE FROM InsuranceCard";

        // Then, insert new rows
        String insertSql = "INSERT INTO InsuranceCard (cardNumber, cardHolder, policyOwner, expirationDate) VALUES (?, ?, ?, ?)";

        try (
                Connection conn = DriverManager.getConnection(jdbcUrl);
                PreparedStatement deleteStatement = conn.prepareStatement(deleteSql);
                PreparedStatement insertStatement = conn.prepareStatement(insertSql)
        ) {
            // Execute the delete statement
            deleteStatement.executeUpdate();

            // Now, insert new rows
            for (InsuranceCard insuranceCard : insuranceCards) {
                insertStatement.setString(1, insuranceCard.getCardNumber());
                insertStatement.setString(2, insuranceCard.getCardHolder());
                insertStatement.setString(3, insuranceCard.getPolicyOwner());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = dateFormat.format(insuranceCard.getExpirationDate());
                insertStatement.setString(4, dateString);

                insertStatement.addBatch(); // Add the prepared statement to the batch
            }

            insertStatement.executeBatch(); // Execute the batch of prepared statements
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<InsuranceCard> insuranceCardReader() {
        ArrayList<InsuranceCard> insuranceCards = new ArrayList<>();

        String selectSql = "SELECT cardNumber, cardHolder, policyOwner, expirationDate FROM InsuranceCard";

        try (Connection conn = DriverManager.getConnection(jdbcUrl);
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSql)) {

            while (resultSet.next()) {
                String cardNumber = resultSet.getString("cardNumber");
                String cardHolder = resultSet.getString("cardHolder");
                String policyOwner = resultSet.getString("policyOwner");
                String expirationDateString = resultSet.getString("expirationDate");

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                java.util.Date expirationDate = dateFormat.parse(expirationDateString);

                InsuranceCard insuranceCard = new InsuranceCard(cardNumber, cardHolder, policyOwner, expirationDate);

                insuranceCards.add(insuranceCard);
            }
        } catch (SQLException | java.text.ParseException e) {
            e.printStackTrace();
        }
        return insuranceCards;
    }

    public static ArrayList<Authentication> authenticationReader() {
        ArrayList<Authentication> authentications = new ArrayList<>();

        String selectSql = "SELECT id, password, userType FROM Authentication";

        try (Connection conn = DriverManager.getConnection(jdbcUrl);
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSql)) {

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String password = resultSet.getString("password");
                String userType = resultSet.getString("userType");

                Authentication authentication = new Authentication(id, password, userType);

                authentications.add(authentication);
            }
        } catch (SQLException e) {
            e.printStackTrace();
    }
        return authentications;
    }

    public static void authenticationWriter(ArrayList<Authentication> authentications) {
        // First, delete all existing rows
        String deleteSql = "DELETE FROM Authentication";

        // Then, insert new rows
        String insertSql = "INSERT INTO Authentication (id, password, userType) VALUES (?, ?, ?)";

        try (
                Connection conn = DriverManager.getConnection(jdbcUrl);
                PreparedStatement deleteStatement = conn.prepareStatement(deleteSql);
                PreparedStatement insertStatement = conn.prepareStatement(insertSql)
        ) {
            // Execute the delete statement
            deleteStatement.executeUpdate();

            // Now, insert new rows
            for (Authentication authentication : authentications) {
                insertStatement.setString(1, authentication.getId());
                insertStatement.setString(2, authentication.getPassword());
                insertStatement.setString(3, authentication.getUserType());

                insertStatement.addBatch(); // Add the prepared statement to the batch
            }

            insertStatement.executeBatch(); // Execute the batch of prepared statements
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<PolicyOwner> policyOwnerReader() {
        ArrayList<PolicyOwner> policyOwners = new ArrayList<>();

        String selectSql = "SELECT id, fullName FROM PolicyOwner";

        try (Connection conn = DriverManager.getConnection(jdbcUrl);
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSql)) {

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String fullName = resultSet.getString("fullName");

                PolicyOwner policyOwner = new PolicyOwner(id, fullName);

                policyOwners.add(policyOwner);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return policyOwners;
    }

    public static void policyOwnerWriter(ArrayList<PolicyOwner> policyOwners) {
        String deleteSql = "DELETE FROM PolicyOwner";

        String insertSql = "INSERT INTO PolicyOwner (id, fullName) VALUES (?, ?)";

        try (
                Connection conn = DriverManager.getConnection(jdbcUrl);
                PreparedStatement deleteStatement = conn.prepareStatement(deleteSql);
                PreparedStatement insertStatement = conn.prepareStatement(insertSql)
        ) {
            deleteStatement.executeUpdate();

            for (PolicyOwner policyOwner : policyOwners) {
                insertStatement.setString(1, policyOwner.getId());
                insertStatement.setString(2, policyOwner.getFullName());

                insertStatement.addBatch();
            }

            insertStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
