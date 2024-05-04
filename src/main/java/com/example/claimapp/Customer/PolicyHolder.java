package com.example.claimapp.Customer;

import com.example.claimapp.Claim;
import com.example.claimapp.InsuranceCard;

import java.util.ArrayList;
import java.util.Date;

public class PolicyHolder extends Customer {
    private ArrayList<String> dependents;
    private ArrayList<Claim> claims; // Ensure that claims are part of PolicyHolder

    public PolicyHolder() {
        super();
        dependents = new ArrayList<>();
        claims = new ArrayList<>();
    }

    public PolicyHolder(String id, String fullName, InsuranceCard insuranceCard, ArrayList<Claim> claims, ArrayList<String> dependents) {
        super(id, fullName, insuranceCard, claims);
        this.dependents = dependents;
        this.claims = new ArrayList<>(claims);
    }

    public ArrayList<String> getDependents() {
        return dependents;
    }

    public void setDependents(ArrayList<String> dependents) {
        this.dependents = dependents;
    }

    // Method to file a new claim
    public void addClaim(String id, Date claimDate, String insuredPerson, String cardNumber, Date examDate, ArrayList<String> documents, double claimAmount, String status, String bankingInfo) {
        for (Claim claim : claims) {
            if (claim.getId().equals(id)) {
                return; // Claim already exists, no duplication
            }
        }
        claims.add(new Claim(id, claimDate, insuredPerson, cardNumber, examDate, documents, claimAmount, status, bankingInfo));
    }

    // Method to update an existing claim
    public void updateClaim(String id, Date claimDate, String insuredPerson, String cardNumber, Date examDate, ArrayList<String> documents, double claimAmount, String status, String bankingInfo) {
        for (Claim claim : claims) {
            if (claim.getId().equals(id)) {
                claim.setClaimDate(claimDate);
                claim.setInsuredPerson(insuredPerson);
                claim.setCardNumber(cardNumber);
                claim.setExamDate(examDate);
                claim.setDocuments(documents);
                claim.setClaimAmount(claimAmount);
                claim.setStatus(status);
                claim.setBankingInfo(bankingInfo);
                return; // Update the claim and return
            }
        }
        // If no claim found, optionally add it or handle error
    }

    // Method to retrieve claims
    public ArrayList<Claim> retrieveClaims() {
        return new ArrayList<>(claims); // Return a copy of the claims list
    }

    // Method to update restricted personal information (phone, address, email, password)
    public void updatePersonalInfo(String phone, String address, String email, String password) {
        // Add logic to update personal information
    }
}
