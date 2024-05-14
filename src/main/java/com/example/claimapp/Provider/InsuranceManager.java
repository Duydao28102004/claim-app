package com.example.claimapp.Provider;

import java.util.ArrayList;

public class InsuranceManager extends Provider{

    private ArrayList<String> insuranceSurveyors;

    public InsuranceManager() {
        super();
        this.insuranceSurveyors = null;
    }

    public InsuranceManager(String id, ArrayList<String> insuranceSurveyors) {
        super(id);
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
                "insuranceSurveyors=" + insuranceSurveyors +
                '}';
    }

}
