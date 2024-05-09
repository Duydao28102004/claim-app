package com.example.claimapp.Provider;

public class InsuranceSurveyor extends Provider{

    private String insuranceManager;

    public InsuranceSurveyor() {
        super();
        insuranceManager = "null";
    }

    public InsuranceSurveyor(String id, String fullName, String phone, String address, String email, String insuranceManager) {
        super(id, fullName, phone, address, email);
        this.insuranceManager = insuranceManager;
    }

    public String getManager() {
        return insuranceManager;
    }



}