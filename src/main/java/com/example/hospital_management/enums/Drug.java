package com.example.hospital_management.enums;

public enum Drug {

    As("Aspirin"),
    An("Antibiotic"),
    I("Insulin"),
    P("Paracetamol");

    private String name;

    private Drug(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
