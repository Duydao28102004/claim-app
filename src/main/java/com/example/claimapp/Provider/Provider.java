package com.example.claimapp.Provider;

public class Provider {

    private String id;

    public Provider() {
        this.id = "default";
    }

    public Provider(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }


}