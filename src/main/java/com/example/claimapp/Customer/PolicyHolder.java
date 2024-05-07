package com.example.claimapp.Customer;

import com.example.claimapp.Claim;
import com.example.claimapp.InsuranceCard;

import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;

public class PolicyHolder extends Customer {
    private ArrayList<String> dependents;
    private ArrayList<Claim> claims;  // This will hold the actual Claim objects.

    public PolicyHolder(String id, String fullName, String insuranceCard, ArrayList<String> serializedClaims, ArrayList<String> dependents) {
        super(id, fullName, insuranceCard, serializedClaims);  // Passing serialized claims to the superclass.
        this.dependents = dependents;
        this.claims = deserializeClaims(serializedClaims);
    }

    // Serialize Claim objects to a JSON string
    private String serializeClaim(Claim claim) {
        Gson gson = new Gson();
        return gson.toJson(claim);
    }

    // Deserialize JSON strings back to Claim objects
    private ArrayList<Claim> deserializeClaims(ArrayList<String> serializedClaims) {
        Gson gson = new Gson();
        ArrayList<Claim> claimsList = new ArrayList<>();
        for (String serializedClaim : serializedClaims) {
            claimsList.add(gson.fromJson(serializedClaim, Claim.class));
        }
        return claimsList;
    }

    // Serialize a list of claims
    private ArrayList<String> serializeClaims(ArrayList<Claim> claims) {
        ArrayList<String> serializedClaims = new ArrayList<>();
        for (Claim claim : claims) {
            serializedClaims.add(serializeClaim(claim));
        }
        return serializedClaims;
    }

    // Method to add a claim
    public void addClaim(Claim newClaim) {
        claims.add(newClaim);
        setClaims(serializeClaims(claims));  // Update the serialized claims in the superclass.
    }

    // Method to update a claim
    public void updateClaim(String claimId, Claim updatedClaim) {
        for (int i = 0; i < claims.size(); i++) {
            if (claims.get(i).getId().equals(claimId)) {
                claims.set(i, updatedClaim);
                setClaims(serializeClaims(claims));
                break;
            }
        }
    }

    // Method to retrieve claims
    public ArrayList<Claim> retrieveClaims() {
        return claims;
    }


    // Method to update restricted personal information (phone, address, email, password)
    public void updatePersonalInfo(String phone, String address, String email, String password) {
        // Add logic to update personal information
    }
}
