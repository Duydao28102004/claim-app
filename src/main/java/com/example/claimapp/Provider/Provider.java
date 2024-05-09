package com.example.claimapp.Provider;

public class Provider {

    private String id;
    private String fullName;
    private String phone;
    private String address;
    private String email;

    public Provider() {
        this.id = "default";
        this.fullName = "default";
        this.phone = null;
        this.address = null;
        this.email = null;
    }

    public Provider(String id, String fullName, String phone, String address, String email) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}