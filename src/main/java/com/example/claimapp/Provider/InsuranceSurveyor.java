package com.example.claimapp.Provider;

public class InsuranceSurveyor extends Provider{

    private String insuranceManager;

    public InsuranceSurveyor() {
        super();
        insuranceManager = "null";
    }

    public InsuranceSurveyor(String id, String insuranceManager) {
        super(id);
        this.insuranceManager = insuranceManager;
    }

    public String getManager() {
        return insuranceManager;
    }



}