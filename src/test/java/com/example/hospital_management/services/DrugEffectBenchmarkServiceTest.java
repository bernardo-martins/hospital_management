package com.example.hospital_management.services;

import com.example.hospital_management.entities.DrugEffect;
import com.example.hospital_management.entities.DrugIntake;
import com.example.hospital_management.enums.Drug;
import com.example.hospital_management.enums.HealthState;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DrugEffectBenchmarkServiceTest {

    @Test
    public void testGetAllEffectsGivenHealthState_F() {
        HealthState healthState = HealthState.F;
        List<DrugEffect> effects = DrugEffectBenchmarkService.getAllEffectsGivenHealthState(healthState);

        assertEquals(3, effects.size()); // 2 effects for F + 1 for ANY
        assertTrue(containsDrugEffect(effects, new DrugEffect(HealthState.F, false, new DrugIntake(Drug.As), HealthState.H)));
        assertTrue(containsDrugEffect(effects, new DrugEffect(HealthState.F, false, new DrugIntake(Drug.P), HealthState.H)));
        assertTrue(containsDrugEffect(effects, new DrugEffect(HealthState.ANY, false, new DrugIntake(Drug.P, Drug.As), HealthState.X)));
    }

    @Test
    public void testGetAllEffectsGivenHealthState_T() {
        HealthState healthState = HealthState.T;
        List<DrugEffect> effects = DrugEffectBenchmarkService.getAllEffectsGivenHealthState(healthState);

        assertEquals(2, effects.size()); // 1 effect for T + 1 for ANY
        assertTrue(containsDrugEffect(effects, new DrugEffect(HealthState.T, false, new DrugIntake(Drug.An), HealthState.H)));
        assertTrue(containsDrugEffect(effects, new DrugEffect(HealthState.ANY, false, new DrugIntake(Drug.P, Drug.As), HealthState.X)));
    }

    @Test
    public void testGetAllEffectsGivenHealthState_H() {
        HealthState healthState = HealthState.H;
        List<DrugEffect> effects = DrugEffectBenchmarkService.getAllEffectsGivenHealthState(healthState);

        assertEquals(2, effects.size()); // 1 effect for H + 1 for ANY
        assertTrue(containsDrugEffect(effects, new DrugEffect(HealthState.H, false, new DrugIntake(Drug.I, Drug.An), HealthState.F)));
        assertTrue(containsDrugEffect(effects, new DrugEffect(HealthState.ANY, false, new DrugIntake(Drug.P, Drug.As), HealthState.X)));
    }

    @Test
    public void testGetAllEffectsGivenHealthState_D() {
        HealthState healthState = HealthState.D;
        List<DrugEffect> effects = DrugEffectBenchmarkService.getAllEffectsGivenHealthState(healthState);

        assertEquals(2, effects.size()); // 1 effect for D + 1 for ANY
        assertTrue(containsDrugEffect(effects, new DrugEffect(HealthState.D, true, new DrugIntake(Drug.I), HealthState.X)));
        assertTrue(containsDrugEffect(effects, new DrugEffect(HealthState.ANY, false, new DrugIntake(Drug.P, Drug.As), HealthState.X)));
    }

    @Test
    public void testGetAllEffectsGivenHealthState_X() {
        HealthState healthState = HealthState.X;
        List<DrugEffect> effects = DrugEffectBenchmarkService.getAllEffectsGivenHealthState(healthState);

        assertEquals(1, effects.size()); // only the ANY effect applies
        assertTrue(containsDrugEffect(effects, new DrugEffect(HealthState.ANY, false, new DrugIntake(Drug.P, Drug.As), HealthState.X)));
    }

    private boolean containsDrugEffect(List<DrugEffect> effects, DrugEffect expectedEffect) {
        return effects.stream().anyMatch(effect ->
                effect.getCurrHealthState() == expectedEffect.getCurrHealthState() &&
                        effect.getDrugIntake().equals(effect.getDrugIntake()) &&
                        effect.getNextHealthState() == expectedEffect.getNextHealthState()
        );
    }

}