package com.example.claimapp.Provider;

public class Provider {

    private String id;
    private String fullName;
    private String phone;
    private String address;
    private String email;
    private String password;

    public Provider() {
        this.id = "default";
        this.fullName = "default";
        this.phone = null;
        this.address = null;
        this.email = null;
        this.password = null;
    }

    public Provider(String id, String fullName, String phone, String address, String email, String password) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}