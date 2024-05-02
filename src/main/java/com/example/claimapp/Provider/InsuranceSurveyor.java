package com.example.claimapp.Provider;

public class InsuranceSurveyor extends Provider{

    private String insuranceManager;

    public InsuranceSurveyor() {
        super();
        insuranceManager = "null";
    }

    public InsuranceSurveyor(String id, String fullName, Number phone, String address, String email, String password, String insuranceManager) {
        super(id, fullName, phone, address, email, password);
        this.insuranceManager = insuranceManager;
    }

    public String getManager() {
        return insuranceManager;
    }

}