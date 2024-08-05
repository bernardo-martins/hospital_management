package com.example.hospital_management.entities;

import com.example.hospital_management.enums.Drug;
import com.example.hospital_management.enums.HealthState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DrugEffectTest {

    private DrugEffect drugEffect;
    private DrugIntake drugIntake;

    @BeforeEach
    void setUp() {
        drugIntake = new DrugIntake(Drug.I);
        drugEffect = new DrugEffect(HealthState.D, false, drugIntake, HealthState.D);
    }

    @Test
    void GIVEN_drugEffect_WHEN_getDrugIntake_method_is_called_THEN_return_correct_value() {
        assertEquals(drugEffect.getDrugIntake(), drugIntake);
    }

    @Test
    void GIVEN_drugEffect_WHEN_getCurrHealthState_method_is_called_THEN_return_correct_value() {
        assertEquals(drugEffect.getCurrHealthState(), HealthState.D);
    }

    @Test
    void GIVEN_drugEffect_WHEN_getNextHealthState_method_is_called_THEN_return_correct_value() {
        assertEquals(drugEffect.getNextHealthState(), HealthState.D);
    }

    @Test
    void GIVEN_drugEffect_WHEN_getIsDrugAbsenceEffect_method_is_called_THEN_return_correct_value() {
        assertFalse(drugEffect.isDrugAbsenceEffect());
    }
}