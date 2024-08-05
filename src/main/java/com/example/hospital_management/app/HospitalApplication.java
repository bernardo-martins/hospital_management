package com.example.hospital_management.app;

import com.example.hospital_management.entities.DrugEffect;
import com.example.hospital_management.entities.DrugIntake;
import com.example.hospital_management.enums.Drug;
import com.example.hospital_management.enums.HealthState;
import com.example.hospital_management.services.DrugAdministrationCenterService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class HospitalApplication {

	public static void main(String[] args) {
        try {
            if(args.length == 0)
                return;

            String[] states = args[0].split(",");
            String[] drugs = null;
            if(args.length > 1)
                drugs = args[1].split(",");

            List<HealthState> healthStateList = extractHealthStates(states);
            List<Drug> drugsList = null;
            if(args.length > 1)
               drugsList = extractDrugs(drugs);

            List<DrugEffect> drugEffects = createDrugEffectsBenchmark();

            StringJoiner output = new StringJoiner(",");
            Map<HealthState, Integer> results = DrugAdministrationCenterService.administratePatients(drugEffects, healthStateList, drugsList);
            for (HealthState state : HealthState.values()) {
                if (state.getSeverity() < 0)
                    continue;
                output.add(state + ":" + results.get(state));
            }
            System.out.println(output.toString());

        } catch(IllegalArgumentException | NullPointerException e) {
            System.out.println(e.toString());
            System.out.println("There was a problem with the input, please retry with a different input!");
        }
	}

    private static List<Drug> extractDrugs(String[] drugs) {
        List<Drug> drugsList = new ArrayList<Drug>();
        for (String drug : drugs) {
            drugsList.add(Enum.valueOf(Drug.class, drug));
        }
        return drugsList;
    }

    private static List<HealthState> extractHealthStates(String[] states) {
        List<HealthState> healthStateList = new ArrayList<HealthState>();
        for (String state : states) {
            healthStateList.add(Enum.valueOf(HealthState.class, state));
        }
        return healthStateList;
    }

    public static List<DrugEffect> createDrugEffectsBenchmark() {
        return List.of(
          new DrugEffect(HealthState.F, false, new DrugIntake(Drug.As), HealthState.H),
          new DrugEffect(HealthState.T, false, new DrugIntake(Drug.An), HealthState.H),
          new DrugEffect(HealthState.H, false, new DrugIntake(Drug.I, Drug.An), HealthState.F),
          new DrugEffect(HealthState.F, false, new DrugIntake(Drug.P), HealthState.H),
          new DrugEffect(HealthState.ANY, false, new DrugIntake(Drug.P, Drug.As), HealthState.X),
          new DrugEffect(HealthState.D, true, new DrugIntake(Drug.I), HealthState.X)
        );
    }

}
