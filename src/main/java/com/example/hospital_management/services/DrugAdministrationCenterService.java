package com.example.hospital_management.services;

import com.example.hospital_management.entities.DrugEffect;
import com.example.hospital_management.entities.DrugIntake;
import com.example.hospital_management.enums.Drug;
import com.example.hospital_management.enums.HealthState;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class DrugAdministrationCenterService {

    public static Map<HealthState, Integer> administratePatients(List<DrugEffect> effects, List<HealthState> healthStates, List<Drug> availableDrugs) {
        Map<HealthState, Integer> results = initializeResults();
        for(HealthState healthState : healthStates) {

            if(isOneInAMillion()) {
                saveHealthStateIntoResults(HealthState.H, results);
            }
            HealthState finalHealthState = retrieveFinalHealthState(healthState, effects, availableDrugs);
            saveHealthStateIntoResults(finalHealthState, results);
        }
        return results;
    }

    public static boolean isOneInAMillion() {
        double randomNumber = Math.random();
        return randomNumber < 1.0 / 1000000;
    }

    private static void saveHealthStateIntoResults(HealthState nextHealthState, Map<HealthState, Integer> results) {
        Integer oldValue = results.get(nextHealthState);
        results.put(nextHealthState, oldValue + 1);
    }

    private static Map<HealthState, Integer> initializeResults() {
        return new LinkedHashMap<>(Map.ofEntries(
                Map.entry(HealthState.H, 0),
                Map.entry(HealthState.F, 0),
                Map.entry(HealthState.D, 0),
                Map.entry(HealthState.T, 0),
                Map.entry(HealthState.X, 0)
        ));
    }

    static HealthState retrieveFinalHealthState(HealthState healthState, List<DrugEffect> effects, List<Drug> availableDrugs) {
        Stack<HealthState> healthStates = new Stack<>();
        HealthState currHealthState = healthState;
        healthStates.add(currHealthState);
        List<HealthState> processedHealthStates = new ArrayList<HealthState>();
        while(!healthStates.isEmpty()) {
            currHealthState = healthStates.pop();

            if(processedHealthStates.contains(currHealthState)) continue;

            processedHealthStates.add(currHealthState);
            List<DrugEffect> outcomes = DrugEffectBenchmarkService.getAllEffectsGivenHealthState(currHealthState);
            for(DrugEffect effect : outcomes) {
                if (isCurable(currHealthState, availableDrugs, effect)) {
                    healthStates.add(HealthState.H);
                    continue;
                }

                if (hasSideEffects(currHealthState, availableDrugs, effect)) {
                    healthStates.add(effect.getNextHealthState());
                }

                if (hasAbsenceEffects(currHealthState, availableDrugs, effect)) {
                    healthStates.add(effect.getNextHealthState());
                }
            }
        }
        return currHealthState;
    }

    private static boolean hasSideEffects(HealthState currHealthState, List<Drug> availableDrugs, DrugEffect effect) {
        return (effect.getCurrHealthState().equals(currHealthState) || effect.getCurrHealthState().equals(HealthState.ANY))
                && drugsAreAvailable(availableDrugs, effect.getDrugIntake())
                && !effect.isDrugAbsenceEffect();
    }

    private static boolean hasAbsenceEffects(HealthState currHealthState, List<Drug> availableDrugs, DrugEffect effect) {
        return (effect.getCurrHealthState().equals(currHealthState) || effect.getCurrHealthState().equals(HealthState.ANY))
                && !drugsAreAvailable(availableDrugs, effect.getDrugIntake())
                && effect.isDrugAbsenceEffect();
    }

    private static boolean isCurable(HealthState currHealthState, List<Drug> availableDrugs, DrugEffect effect) {
        return (effect.getCurrHealthState().equals(currHealthState) || effect.getCurrHealthState().equals(HealthState.ANY))
                && drugsAreAvailable(availableDrugs, effect.getDrugIntake())
                && effect.getNextHealthState().equals(HealthState.H)
                && !effect.isDrugAbsenceEffect();
    }

    private static boolean drugsAreAvailable(List<Drug> availableDrugs, DrugIntake drugIntake) {
        if (availableDrugs == null || availableDrugs.isEmpty())
            return false;
        for(Drug drug : drugIntake.getDrugs())
            if(!availableDrugs.contains(drug))
                return false;
        return true;
    }

}
