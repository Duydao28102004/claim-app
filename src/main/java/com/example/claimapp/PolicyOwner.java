package com.example.claimapp;
import com.example.claimapp.Customer.Customer;

import java.util.ArrayList;

public class PolicyOwner {
    private String id;
    private String fullName;

    public PolicyOwner() {
        id = "default";
        fullName = "default";
    }

    public PolicyOwner(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "PolicyOwner: " + "id: " + id + ' ' + "fullName: " + fullName + '\n';
    }
}
