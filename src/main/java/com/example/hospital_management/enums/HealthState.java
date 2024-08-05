package com.example.hospital_management.enums;

public enum HealthState {

    F(2, "Fever"),
    H(1, "Healthy"),
    D(3, "Diabetes"),
    T(4, "Tuberculosis"),
    X(5, "Dead"),
    ANY(-1, "Any");

    private String name;
    private int severity;

    private HealthState(int severity, String name) {
        this.severity = severity;
        this.name = name;
    }

    public int getSeverity() {
        return severity;
    }

    public String getName() {
        return name;
    }

}
