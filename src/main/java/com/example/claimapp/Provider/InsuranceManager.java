package com.example.claimapp.Provider;

import java.util.ArrayList;

public class InsuranceManager extends Provider{

    private ArrayList<String> insuranceSurveyors;

    public InsuranceManager(String id, String fullName, String phone, String address, ArrayList<String> insuranceSurveyors) {
        super();
        this.insuranceSurveyors = null;
    }

    public InsuranceManager(String id, String fullName, String phone, String address, String email, ArrayList<String> insuranceSurveyors) {
        super(id, fullName, phone, address, email);
        this.insuranceSurveyors = insuranceSurveyors;
    }

    public ArrayList<String> getInsuranceSurveyors() {
        return insuranceSurveyors;
    }

    public void addSurveyor(String surveyor) {
        insuranceSurveyors.add(surveyor);
    }

    @Override
    public String toString() {
        return "InsuranceManager{" + getId() + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", address='" + getAddress() + '\'' +
                ", email='" + getEmail() + '\'' +
                "insuranceSurveyors=" + insuranceSurveyors +
                '}';
    }

}
