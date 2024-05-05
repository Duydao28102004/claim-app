package com.example.claimapp.Provider;

import com.example.claimapp.Claim;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class InsuranceProcessManager {

    // Method to retrieve a manager from the list of insurance managers based on manager ID
    InsuranceManager getManager(String managerId) {
        // Retrieve the lists from ProviderManager
        ArrayList<InsuranceManager> insuranceManagers = ProviderManager.getInsuranceManagers();
        for (InsuranceManager manager : insuranceManagers) {
            if (manager.getId().equals(managerId)) {
                return manager;
            }
        }
        return null; // Return null if manager with the specified ID is not found
    }

    // Method to retrieve a surveyor from the list of insurance surveyors based on surveyor ID
    InsuranceSurveyor getSurveyor(String surveyorId) {
        // Retrieve the lists from ProviderManager
        ArrayList<InsuranceSurveyor> insuranceSurveyors = ProviderManager.getInsuranceSurveyors();
        for (InsuranceSurveyor surveyor : insuranceSurveyors) {
            if (surveyor.getId().equals(surveyorId)) {
                return surveyor;
            }
        }
        return null; // Return null if surveyor with the specified ID is not found
    }

    public void addSurveyorToManager(String managerId, String surveyorId) {
        // Retrieve the manager from the insuranceManagers list using the manager ID
        InsuranceManager manager = getManager(managerId);

        if (manager != null) {
            // Retrieve the surveyor from the insuranceSurveyors list using the surveyor ID
            InsuranceSurveyor surveyor = getSurveyor(surveyorId);

            if (surveyor != null) {
                // Add the surveyor to the InsuranceManager
                manager.addSurveyor(surveyor);

                // Optionally, you can display a message indicating success
                System.out.println("Surveyor added to manager: " + manager.getFullName());
            } else {
                // Optionally, display a message indicating that the surveyor was not found
                System.out.println("Surveyor not found with ID: " + surveyorId);
            }
        } else {
            // Optionally, display a message indicating that the manager was not found
            System.out.println("Manager not found with ID: " + managerId);
        }
    }

    public void proposeClaim(String claimId) {
        ProviderManager providerManager = new ProviderManager();
        Claim claim = providerManager.getSpecificClaim(claimId);
        if (claim != null) {
            if (claim.getStatus().equals("New")) {
                claim.setStatus("Processing");
                System.out.println("Claim " + claimId + " has been proposed and is now in processing.");
            } else {
                System.out.println("Cannot propose claim. Claim " + claimId + " is already in " + claim.getStatus() + " status.");
            }
        } else {
            System.out.println("Claim with ID " + claimId + " not found.");
        }
    }

    public void processClaim(String claimId, String status) {
        ProviderManager providerManager = new ProviderManager();
        Claim claim = providerManager.getSpecificClaim(claimId);
        if (claim != null) {
            if (claim.getStatus().equals("Processing")) {
                if (status.equals("Accepted") || status.equals("Rejected")) {
                    claim.setStatus(status);
                    System.out.println("Claim " + claimId + " has been " + status);
                } else {
                    System.out.println("Invalid status. Status must be either 'Accepted' or 'Rejected'.");
                }
            } else {
                System.out.println("Cannot process claim. Claim " + claimId + " is not in 'Processing' status.");
            }
        } else {
            System.out.println("Claim with ID " + claimId + " not found.");
        }
    }


}
