package com.example.hospital_management.services;

import com.example.hospital_management.entities.DrugEffect;
import com.example.hospital_management.entities.DrugIntake;
import com.example.hospital_management.enums.Drug;
import com.example.hospital_management.enums.HealthState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DrugEffectBenchmarkService {

    private static final Map<HealthState, List<DrugEffect>> drugEffectsBenchmark = Map.of(
            HealthState.F, new ArrayList<>() {{
                add(new DrugEffect(HealthState.F, false, new DrugIntake(Drug.As), HealthState.H));
                add(new DrugEffect(HealthState.F, false, new DrugIntake(Drug.P), HealthState.H));
            }},
            HealthState.T, new ArrayList<>() {{
                add(new DrugEffect(HealthState.T, false, new DrugIntake(Drug.An), HealthState.H));
            }},
            HealthState.H, new ArrayList<>() {{
                add(new DrugEffect(HealthState.H, false, new DrugIntake(Drug.I, Drug.An), HealthState.F));
            }},
            HealthState.ANY, new ArrayList<>() {{
                add(new DrugEffect(HealthState.ANY, false, new DrugIntake(Drug.P, Drug.As), HealthState.X));
            }},
            HealthState.D, new ArrayList<>() {{
                add(new DrugEffect(HealthState.D, true, new DrugIntake(Drug.I), HealthState.X));
            }}
    );


    public static List<DrugEffect> getAllEffectsGivenHealthState(HealthState healthState) {
        List<DrugEffect> effects = new ArrayList<>();

        if (drugEffectsBenchmark.containsKey(healthState)) {
            effects.addAll(drugEffectsBenchmark.get(healthState));
        }

        if (drugEffectsBenchmark.containsKey(HealthState.ANY)) {
            effects.addAll(drugEffectsBenchmark.get(HealthState.ANY));
        }

        return effects;    }

}
