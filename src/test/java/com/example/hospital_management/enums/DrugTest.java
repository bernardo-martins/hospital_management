package com.example.hospital_management.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DrugTest {

    @Test
    public void testDrugEnumValues() {
        assertEquals("Aspirin", Drug.As.getName());
        assertEquals("Antibiotic", Drug.An.getName());
        assertEquals("Insulin", Drug.I.getName());
        assertEquals("Paracetamol", Drug.P.getName());
    }

}