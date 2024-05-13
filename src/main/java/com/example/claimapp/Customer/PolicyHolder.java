
package com.example.claimapp.Customer;

import com.example.claimapp.Claim;
import com.example.claimapp.InsuranceCard;

import java.util.ArrayList;
import java.util.Date;

public class PolicyHolder extends Customer {
    private ArrayList<String> dependents;
    private ArrayList<Claim> claims; // Ensure that claims are part of PolicyHolder
    private String phone;
    private String address;
    private String email;
    private String password;

    // Methods to set personal information
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PolicyHolder() {
        super();
        dependents = new ArrayList<>();
        claims = new ArrayList<>();
    }

    public PolicyHolder(String id, String fullName, String insuranceCard, ArrayList<String> claims, ArrayList<String> dependents) {
        super(id, fullName, insuranceCard, claims);
        this.dependents = dependents;
        this.claims = new ArrayList<>();
    }

    public ArrayList<String> getDependents() {
        return dependents;
    }

    public void setDependents(ArrayList<String> dependents) {
        this.dependents = dependents;
    }

    // Method to file or update a claim for self or a dependent
    public void manageClaim(String claimId, Date claimDate, String insuredPerson, String cardNumber, Date examDate, ArrayList<String> documents, double claimAmount, String status, String bankingInfo, boolean isNew) {
        ArrayList<Claim> relevantClaims = insuredPerson.equals(getId()) ? this.claims : getDependentClaims(insuredPerson);
        for (Claim claim : relevantClaims) {
            if (claim.getId().equals(claimId)) {
                if (!isNew) {
                    claim.setClaimDate(claimDate);
                    claim.setInsuredPerson(insuredPerson);
                    claim.setCardNumber(cardNumber);
                    claim.setExamDate(examDate);
                    claim.setDocuments(documents);
                    claim.setClaimAmount(claimAmount);
                    claim.setStatus(status);
                    claim.setBankingInfo(bankingInfo);
                }
                return; // Claim already exists, updated or ignored if new
            }
        }
        if (isNew) {
            relevantClaims.add(new Claim(claimId, claimDate, insuredPerson, cardNumber, examDate, documents, claimAmount, status, bankingInfo));
        }
    }

    // Retrieve claims for self and dependents
    public ArrayList<Claim> retrieveClaims(String insuredPerson) {
        return insuredPerson.equals(getId()) ? new ArrayList<>(this.claims) : new ArrayList<>(getDependentClaims(insuredPerson));
    }

    // Helper method to get claims of a dependent
    private ArrayList<Claim> getDependentClaims(String dependentId) {
        // This implementation is placeholder and should link to actual dependent claims retrieval
        return new ArrayList<>(); // Placeholder for dependent claims retrieval
    }

    // Method to update restricted personal information (phone, address, email, password)
    public void updatePersonalInfo(String phone, String address, String email, String password) {
        setPhone(phone);
        setAddress(address);
        setEmail(email);
        setPassword(password);
    }
}
