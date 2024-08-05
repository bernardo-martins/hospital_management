package com.example.hospital_management.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HealthStateTest {

    @Test
    public void testHealthStateEnumValues() {
        assertEquals(2, HealthState.F.getSeverity());
        assertEquals("Fever", HealthState.F.getName());

        assertEquals(1, HealthState.H.getSeverity());
        assertEquals("Healthy", HealthState.H.getName());

        assertEquals(3, HealthState.D.getSeverity());
        assertEquals("Diabetes", HealthState.D.getName());

        assertEquals(4, HealthState.T.getSeverity());
        assertEquals("Tuberculosis", HealthState.T.getName());

        assertEquals(5, HealthState.X.getSeverity());
        assertEquals("Dead", HealthState.X.getName());

        assertEquals(-1, HealthState.ANY.getSeverity());
        assertEquals("Any", HealthState.ANY.getName());
    }

}