package com.example.claimapp.Provider;

import com.example.claimapp.Claim;

import java.util.*;

import com.example.claimapp.Customer.Customer;
import com.example.claimapp.InsuranceCard;


public class ProviderManager {

    private static final String SUPABASE_URL = "https://byvqoczsxkvoqxbftqqb.supabase.co";
    private static final String SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJ5dnFvY3pzeGt2b3F4YmZ0cX";

    // Rest of the class implementation...

    private ArrayList<Claim> claims = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();

    public ProviderManager() {
        initializeSampleData();
    }

    private void initializeSampleData() {
        // Sample claims
        claims.add(new Claim("Claim-1", new Date("11/11/11111"), "John Doe", "1234-5678-9012-3456", new Date(), new ArrayList<>(), 1000.00, "Pending", "BankInfo: ABCBank_123456789"));
        claims.add(new Claim("Claim-2", new Date("22/22/2222"), "Jane Smith", "2345-6789-0123-4567", new Date(), new ArrayList<>(), 1500.00, "Pending", "BankInfo: XYZ_987654321"));

        // Sample customers
        customers.add(new Customer("c-1111111", "John Doe", new InsuranceCard(), null));
        customers.add(new Customer("c-2222222", "Jane Smith", new InsuranceCard(), null));
    }

    public ArrayList<Claim> getAllClaims() {
        return claims;
    }

    public ArrayList<Customer> getAllCustomers() {
        return customers;
    }

    public Claim getSpecificClaim(String claimId) {
        for (Claim claim : claims) {
            if (claim.getId().equals(claimId)) {
                return claim;
            }
        }
        return null; // Return null if claim with the specified ID is not found
    }

    public Customer getSpecificCustomer(String customerName) {
        for (Customer customer : customers) {
            if (customer.getFullName().equals(customerName)) {
                return customer;
            }
        }
        return null; // Return null if customer with the specified name is not found
    }

    // Function to arrange claims from the latest to earliest creation date
    public List<Claim> claimLatestToEarliest(List<Claim> claims) {
        claims.sort(Comparator.comparing(Claim::getClaimDate).reversed());
        return claims;
    }

    // Function to arrange claims from the earliest to latest creation date
    public List<Claim> claimEarliestToLatest(List<Claim> claims) {
        claims.sort(Comparator.comparing(Claim::getClaimDate));
        return claims;
    }
}
