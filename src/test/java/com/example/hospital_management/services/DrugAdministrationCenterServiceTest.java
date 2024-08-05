package com.example.hospital_management.services;

import com.example.hospital_management.entities.DrugEffect;
import com.example.hospital_management.entities.DrugIntake;
import com.example.hospital_management.enums.Drug;
import com.example.hospital_management.enums.HealthState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

public class DrugAdministrationCenterServiceTest {

    private List<DrugEffect> mockEffects;
    private List<HealthState> mockHealthStates;
    private List<Drug> mockAvailableDrugs;

    @Mock
    private DrugAdministrationCenterService drugAdministrationCenterService;

    private DrugEffectBenchmarkService drugEffectBenchmarkService;

    private MockedStatic<DrugEffectBenchmarkService> drugEffectBenchmarkServiceMockedStatic;


    @BeforeEach
    public void setUp() {
        drugEffectBenchmarkServiceMockedStatic = mockStatic(DrugEffectBenchmarkService.class);
    }

    @Test
    public void GIVEN_drug_effects_and_transitive_relation_WHEN_patients_are_administrated_THEN_return_correct_results() {

        mockHealthStates = Arrays.asList(HealthState.F, HealthState.T);
        mockAvailableDrugs = Arrays.asList(Drug.As, Drug.I);

        drugEffectBenchmarkServiceMockedStatic.when(() -> DrugEffectBenchmarkService.getAllEffectsGivenHealthState(HealthState.F))
                .thenReturn(List.of(
                        new DrugEffect(HealthState.F, false, new DrugIntake(Drug.As), HealthState.H)));
        drugEffectBenchmarkServiceMockedStatic.when(() -> DrugEffectBenchmarkService.getAllEffectsGivenHealthState(HealthState.T))
                .thenReturn(List.of(
                        new DrugEffect(HealthState.T, false, new DrugIntake(Drug.I), HealthState.F)));

        Map<HealthState, Integer> results = DrugAdministrationCenterService.administratePatients(mockEffects, mockHealthStates, mockAvailableDrugs);

        assertEquals(2, results.get(HealthState.H));
        assertEquals(0, results.get(HealthState.F));

        assertEquals(0, results.get(HealthState.D));
        assertEquals(0, results.get(HealthState.T));
        assertEquals(0, results.get(HealthState.X));

        drugEffectBenchmarkServiceMockedStatic.close();
    }

    @Test
    public void GIVEN_drug_effects_and_circular_relation_WHEN_patients_are_administrated_THEN_patients_state_does_not_change() {

        mockHealthStates = Arrays.asList(HealthState.F);
        mockAvailableDrugs = Arrays.asList(Drug.As, Drug.I);

        drugEffectBenchmarkServiceMockedStatic.when(() -> DrugEffectBenchmarkService.getAllEffectsGivenHealthState(HealthState.F))
                .thenReturn(List.of(
                        new DrugEffect(HealthState.F, false, new DrugIntake(Drug.As), HealthState.H)));
        drugEffectBenchmarkServiceMockedStatic.when(() -> DrugEffectBenchmarkService.getAllEffectsGivenHealthState(HealthState.H))
                .thenReturn(List.of(
                        new DrugEffect(HealthState.H, false, new DrugIntake(Drug.I), HealthState.F)));

        Map<HealthState, Integer> results = DrugAdministrationCenterService.administratePatients(mockEffects, mockHealthStates, mockAvailableDrugs);

        assertEquals(0, results.get(HealthState.H));
        assertEquals(1, results.get(HealthState.F));

        assertEquals(0, results.get(HealthState.D));
        assertEquals(0, results.get(HealthState.T));
        assertEquals(0, results.get(HealthState.X));

        drugEffectBenchmarkServiceMockedStatic.close();

    }

    @Test
    public void GIVEN_drug_effects_and_absence_effect_for_diabetes_WHEN_patients_are_administrated_THEN_one_patient_dies() {

        mockHealthStates = Arrays.asList(HealthState.D, HealthState.T);
        mockAvailableDrugs = Arrays.asList(Drug.As, Drug.An);

        drugEffectBenchmarkServiceMockedStatic.when(() -> DrugEffectBenchmarkService.getAllEffectsGivenHealthState(HealthState.D))
                .thenReturn(List.of(
                        new DrugEffect(HealthState.D, true, new DrugIntake(Drug.I), HealthState.X)));
        drugEffectBenchmarkServiceMockedStatic.when(() -> DrugEffectBenchmarkService.getAllEffectsGivenHealthState(HealthState.T))
                .thenReturn(List.of(
                        new DrugEffect(HealthState.T, false, new DrugIntake(Drug.As), HealthState.H)));

        Map<HealthState, Integer> results = DrugAdministrationCenterService.administratePatients(mockEffects, mockHealthStates, mockAvailableDrugs);

        assertEquals(1, results.get(HealthState.H));
        assertEquals(0, results.get(HealthState.F));

        assertEquals(0, results.get(HealthState.D));
        assertEquals(0, results.get(HealthState.T));
        assertEquals(1, results.get(HealthState.X));

        drugEffectBenchmarkServiceMockedStatic.close();

    }

    @Test
    public void GIVEN_drug_effects_and_no_matching_effects_WHEN_patients_are_administrated_THEN_states_do_not_change() {

        mockHealthStates = Arrays.asList(HealthState.D, HealthState.T);
        mockAvailableDrugs = Arrays.asList(Drug.As, Drug.I);

        drugEffectBenchmarkServiceMockedStatic.when(() -> DrugEffectBenchmarkService.getAllEffectsGivenHealthState(HealthState.F))
                .thenReturn(List.of(
                        new DrugEffect(HealthState.D, true, new DrugIntake(Drug.P), HealthState.X)));
        drugEffectBenchmarkServiceMockedStatic.when(() -> DrugEffectBenchmarkService.getAllEffectsGivenHealthState(HealthState.T))
                .thenReturn(List.of(
                        new DrugEffect(HealthState.T, false, new DrugIntake(Drug.An), HealthState.F)));

        Map<HealthState, Integer> results = DrugAdministrationCenterService.administratePatients(mockEffects, mockHealthStates, mockAvailableDrugs);

        assertEquals(0, results.get(HealthState.H));
        assertEquals(0, results.get(HealthState.F));

        assertEquals(1, results.get(HealthState.D));
        assertEquals(1, results.get(HealthState.T));
        assertEquals(0, results.get(HealthState.X));

        drugEffectBenchmarkServiceMockedStatic.close();

    }

}
