package com.example.claimapp.Provider;

import com.example.claimapp.Claim;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.example.claimapp.Customer.Customer;
import com.google.gson.Gson;


public class ProviderManager {

    private static final String SUPABASE_URL = "https://byvqoczsxkvoqxbftqqb.supabase.co";
    private static final String SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJ5dnFvY3pzeGt2b3F4YmZ0cX";

    // Rest of the class implementation...

    public List<Claim> getAllClaims() {
        List<Claim> claims = new ArrayList<>();

        try {
            // Construct the URL for the API endpoint
            String apiUrl = SUPABASE_URL + "/rest/v1/Claim"; // Assuming "claims" is the table name

            // Open a connection to the Supabase API
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("apikey", SUPABASE_KEY);

            // Retrieve the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse the response JSON using Gson
            Gson gson = new Gson();
            Claim[] claimsArray = gson.fromJson(response.toString(), Claim[].class);
            claims.addAll(Arrays.asList(claimsArray));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return claims;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();

        try {
            // Construct the URLs for the API endpoints
            String policyHoldersUrl = SUPABASE_URL + "/rest/v1/PolicyHolder";
            String dependentsUrl = SUPABASE_URL + "/rest/v1/Dependent";

            // Retrieve policy holders
            List<Customer> policyHolders = queryCustomers(policyHoldersUrl);

            // Retrieve dependents
            List<Customer> dependents = queryCustomers(dependentsUrl);

            // Combine the lists
            customers.addAll(policyHolders);
            customers.addAll(dependents);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return customers;
    }

    private List<Customer> queryCustomers(String apiUrl) throws Exception {
        List<Customer> customers = new ArrayList<>();

        // Open a connection to the Supabase API
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("apikey", SUPABASE_KEY);

        // Retrieve the response
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        // Parse the response JSON using Gson
        Gson gson = new Gson();
        Customer[] customersArray = gson.fromJson(response.toString(), Customer[].class);
        customers.addAll(Arrays.asList(customersArray));

        return customers;
    }

    public Claim getSpecificClaim(String claimId) {
        Claim claim = null;

        try {
            // Construct the URL for the API endpoint to fetch the specific claim
            String apiUrl = SUPABASE_URL + "/rest/v1/Claim?id=eq." + claimId; // Assuming "Claim" is the table name

            // Open a connection to the Supabase API
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("apikey", SUPABASE_KEY);

            // Retrieve the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse the response JSON using Gson
            Gson gson = new Gson();
            claim = gson.fromJson(response.toString(), Claim.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return claim;
    }

    public Customer getSpecificCustomer(String customerName) {
        Customer specificCustomer = null;

        try {
            // Construct the URL for the API endpoint
            String apiUrl = SUPABASE_URL + "/rest/v1/Customer?select=*&customerName=eq." + customerName;

            // Open a connection to the Supabase API
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("apikey", SUPABASE_KEY);

            // Retrieve the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse the response JSON using Gson
            Gson gson = new Gson();
            specificCustomer = gson.fromJson(response.toString(), Customer.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return specificCustomer;
    }

}
