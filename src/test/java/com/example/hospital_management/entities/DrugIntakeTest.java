package com.example.hospital_management.entities;

import com.example.hospital_management.enums.Drug;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DrugIntakeTest {

    private DrugIntake drugIntake;

    @BeforeEach
    void setUp() {
        drugIntake = new DrugIntake(Drug.An, Drug.I);;
    }

    @Test
    public void GIVEN_drugIntakeObject_WHEN_getter_method_is_called_THEN_return_correct_information() {
        assertEquals(drugIntake.getDrugs().size(), 2);
        assertEquals(drugIntake.getDrugs(), List.of(Drug.An, Drug.I));
    }
}