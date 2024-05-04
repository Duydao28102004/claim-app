package com.example.claimapp.Customer;

import com.example.claimapp.Claim;
import com.example.claimapp.InsuranceCard;

import java.util.ArrayList;

public class Dependent extends Customer {
    private String policyHolder;

    public Dependent() {
        super();
        policyHolder = "null";
    }

    public Dependent(String id, String fullName, InsuranceCard insuranceCard, ArrayList<Claim> claims, String policyHolder) {
        super(id, fullName, insuranceCard, claims);
        this.policyHolder = policyHolder;
    }

    public String getPolicyHolder() {
        return policyHolder;
    }

    public void setPolicyHolder(String policyHolder) {
        this.policyHolder = policyHolder;
    }

    // Method to retrieve own claims
    public ArrayList<Claim> retrieveClaims() {
        return getClaims();
    }

    // Method to retrieve personal information
    public String retrievePersonalInfo() {
        return "ID: " + getId() + ", Full Name: " + getFullName();
    }
}
