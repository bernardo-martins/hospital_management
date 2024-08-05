package com.example.hospital_management.app;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HospitalApplicationTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void GIVEN_diabetes_with_insulin_and_antibiotic_WHEN_administrate_drugs_THEN_patient_still_has_diabetes() {

        HospitalApplication.main(new String[]{"D","An,I"});

        String output = outContent.toString().trim();
        String expectedOutput = "F:0,H:0,D:1,T:0,X:0";  // Adjust this to match the actual expected output
        assertEquals(expectedOutput, output);
    }

    @Test
    public void GIVEN_fever_and_drugs_are_paracetamol_WHEN_administrate_drugs_THEN_patient_is_healthy() {

        HospitalApplication.main(new String[]{"F","P"});

        String output = outContent.toString().trim();
        String expectedOutput = "F:0,H:1,D:0,T:0,X:0";  // Adjust this to match the actual expected output
        assertEquals(expectedOutput, output);
    }

    @Test
    public void GIVEN_diabetes_tuberculose_fever_with_drugs_antibiotic_and_insulin_WHEN_administrate_drugs_THEN_two_patients_with_fever_and_one_diabetes() {

        HospitalApplication.main(new String[]{"T,F,D", "An,I"});

        String output = outContent.toString().trim();
        String expectedOutput = "F:2,H:0,D:1,T:0,X:0";
        assertEquals(expectedOutput, output);
    }
}
