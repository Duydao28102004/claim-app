package com.example.claimapp;
import com.example.claimapp.Customer.Customer;

import java.util.ArrayList;

public class PolicyOwner {
    private String id;
    private String fullName;
    private ArrayList<Customer> beneficiaries;

    public PolicyOwner() {
        beneficiaries = new ArrayList<>();
    }

    public PolicyOwner(ArrayList<Customer> beneficiaries) {
        this.beneficiaries = beneficiaries;
    }

    public ArrayList<Customer> getBeneficiaries() {
        return beneficiaries;
    }

    public void setBeneficiaries(ArrayList<Customer> beneficiaries) {
        this.beneficiaries = beneficiaries;
    }
}
