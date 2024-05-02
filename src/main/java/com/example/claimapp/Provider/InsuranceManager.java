package com.example.claimapp.Provider;

import java.util.ArrayList;

public class InsuranceManager extends Provider{

    private ArrayList<InsuranceSurveyor> insuranceSurveyors;

    public InsuranceManager() {
        super();
        insuranceSurveyors = null;
    }

    public InsuranceManager(String id, String fullName, Number phone, String address, String email, String password, ArrayList<InsuranceSurveyor> insuranceSurveyors) {
        super(id, fullName, phone, address, email, password);
        this.insuranceSurveyors = insuranceSurveyors;
    }

    public ArrayList<InsuranceSurveyor> getInsuranceSurveyors() {
        return insuranceSurveyors;
    }

    public void addSurveyor(InsuranceSurveyor surveyor) {
        insuranceSurveyors.add(surveyor);
    }

}
