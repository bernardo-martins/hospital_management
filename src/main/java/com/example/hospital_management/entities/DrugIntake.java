package com.example.hospital_management.entities;

import com.example.hospital_management.enums.Drug;

import java.util.List;

public class DrugIntake {

    private List<Drug> drugs;

    public DrugIntake(Drug... drug) {
        this.drugs = List.of(drug);
    }

    public List<Drug> getDrugs() {
        return drugs;
    }

}
