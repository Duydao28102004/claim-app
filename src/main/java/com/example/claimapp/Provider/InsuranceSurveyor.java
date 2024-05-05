package com.example.claimapp.Provider;

public class InsuranceSurveyor extends Provider{

    private String insuranceManager;

    public InsuranceSurveyor() {
        super();
        insuranceManager = "null";
    }

    public InsuranceSurveyor(String id, String fullName, String phone, String address, String email, String password, InsuranceManager insuranceManager) {
        super(id, fullName, phone, address, email, password);
        this.insuranceManager = String.valueOf(insuranceManager);
    }

    public String getManager() {
        return insuranceManager;
    }



}