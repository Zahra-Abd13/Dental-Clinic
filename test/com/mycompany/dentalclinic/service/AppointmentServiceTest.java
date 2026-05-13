/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.dentalclinic.service;

import com.mycompany.dentalclinic.model.*;
import com.mycompany.dentalclinic.exceptions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class AppointmentServiceTest {

    private AppointmentService service;
    private Doctor doctor;
    private Patient patient;

    /**
     * Runs before each test case
     * Initializes service, doctor, and patient objects
     */
    @BeforeEach
    void setup() {
        service = new AppointmentService();

        doctor = new Doctor(
                "D1",
                "Dr Ahmed",
                "0100000000",
                "ahmed@mail.com",
                500
        );

        patient = new Patient(
                "P1",
                "Sara",
                20,
                "0111111111",
                "sara@mail.com",
                "No history"
        );
    }

    /**
     * Test booking a valid appointment successfully
     */
    @Test
    void testBookAppointmentSuccess() throws SchedulingException {

        Appointments appointment = new Appointments(
                "A1",
                patient,
                doctor,
                LocalDateTime.of(2025, 5, 20, 10, 0)
        );

        service.bookAppointment(appointment);

        assertEquals(
                1,
                service.getAllAppointments().size(),
                "Appointment should be added successfully"
        );
    }

    /**
     * Test booking two appointments at the same time
     * for the same doctor (should throw exception)
     */
    @Test
    void testBookAppointmentConflict() {

        LocalDateTime time =
                LocalDateTime.of(2025, 5, 20, 10, 0);

        Appointments a1 =
                new Appointments("A1", patient, doctor, time);

        Appointments a2 =
                new Appointments("A2", patient, doctor, time);

        assertThrows(
                SchedulingException.class,
                () -> {
                    service.bookAppointment(a1);
                    service.bookAppointment(a2);
                },
                "Doctor should not be double-booked at the same time"
        );
    }
}
