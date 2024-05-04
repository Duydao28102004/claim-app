package com.example.claimapp;

import com.example.claimapp.Customer.PolicyHolder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FileManager {
    private String jdbcUrl;

    public FileManager(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public ArrayList<PolicyHolder> readPolicyHolder() {
        ArrayList<PolicyHolder> policyHolders = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(jdbcUrl)) {
            String query = "SELECT * FROM policy_holder";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String fullName = resultSet.getString("full_name");
                String policyNumber = resultSet.getString("policy_number");
//                PolicyHolder policyHolder = new PolicyHolder(id, fullName, policyNumber);
//                policyHolders.add(policyHolder);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return policyHolders;
    }
}
