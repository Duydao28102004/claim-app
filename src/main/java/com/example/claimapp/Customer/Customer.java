package com.example.claimapp.Customer;

/**
 * @author <Dao Bao Duy - s3978826>
 * Adapted from: chatGPT, w3schools
 */

import java.util.ArrayList;

public class Customer {
    private String id;
    private String fullName;
    private String insuranceCard;
    private ArrayList<String> claims;

    public Customer() {
        id = "default";
        fullName = "default";
        insuranceCard = null;
        claims = null;
    }

    public Customer(String id, String fullName, String insuranceCard, ArrayList<String> claims) {
        this.id = id;
        this.fullName = fullName;
        this.insuranceCard = insuranceCard;
        this.claims = claims;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getInsuranceCard() {
        return insuranceCard;
    }

    public void setInsuranceCard(String insuranceCard) {
        this.insuranceCard = insuranceCard;
    }

    public ArrayList<String> getClaims() {
        return claims;
    }

    public void setClaims(ArrayList<String> claims) {
        this.claims = claims;
    }
}
