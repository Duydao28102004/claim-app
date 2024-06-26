package com.example.claimapp.Customer;

/**
 * @author <Dao Bao Duy - s3978826>
 * Adapted from: chatGPT, w3schools
 */

import java.util.ArrayList;

public class Dependent extends Customer {
    private String policyHolder;

    public Dependent() {
        super();
        policyHolder = "null";
    }

    public Dependent(String id, String fullName, String insuranceCard, ArrayList<String> claims, String policyHolder) {
        super(id, fullName, insuranceCard, claims);
        this.policyHolder = policyHolder;
    }

    public String getPolicyHolder() {
        return policyHolder;
    }

    public void setPolicyHolder(String policyHolder) {
        this.policyHolder = policyHolder;
    }

    @Override
    public String toString() {
        return
                "id: " + getId() + ' ' +
                        "  full name: " + getFullName() + ' ' +
                        "  insuranceCard: " + getInsuranceCard() + "\n " +
                        "  claims: " + getClaims() + "\n " +
                        "  policyHolder: " + policyHolder + ' ';
    }
}
