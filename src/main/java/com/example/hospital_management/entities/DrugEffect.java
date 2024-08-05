package com.example.hospital_management.entities;

import com.example.hospital_management.enums.HealthState;

public class DrugEffect {

    private DrugIntake drugIntake;

    private HealthState nextHealthState;

    private HealthState currHealthState;

    private boolean drugAbsence;

    public DrugEffect(HealthState currHealthState, boolean drugAbsence, DrugIntake drugIntake, HealthState nextHealthState) {
        this.currHealthState = currHealthState;
        this.drugIntake = drugIntake;
        this.nextHealthState = nextHealthState;
        this.drugAbsence = drugAbsence;
    }

    public DrugIntake getDrugIntake() {
        return drugIntake;
    }

    public HealthState getNextHealthState() {
        return nextHealthState;
    }

    public HealthState getCurrHealthState() {
        return currHealthState;
    }

    public boolean isDrugAbsenceEffect() {
        return drugAbsence;
    }

}
