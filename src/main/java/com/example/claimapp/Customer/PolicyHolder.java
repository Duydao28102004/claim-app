package com.example.claimapp.Customer;

/**
 * @author <Dao Bao Duy - s3978826>
 * Adapted from: chatGPT, w3schools
 */

import java.util.ArrayList;


public class PolicyHolder extends Customer {
    private ArrayList<String> dependents;

    public PolicyHolder() {
        super();
        dependents = null;
    }

    public PolicyHolder(String id, String fullName, String insuranceCard, ArrayList<String> claims, ArrayList<String> dependents) {
        super(id, fullName, insuranceCard, claims);
        this.dependents = dependents;
    }

    public ArrayList<String> getDependents() {
        return dependents;
    }

    public void setDependents(ArrayList<String> dependents) {
        this.dependents = dependents;
    }

    @Override
    public String toString() {
        return
                "id: " + getId() + ' ' +
                        ", full name: " + getFullName() + ' ' +
                        ", insurance card: " + getInsuranceCard() + "\n " +
                        "    claims: " + getClaims() + "\n " +
                        "    dependents: " + dependents + ' ';
    }
}
