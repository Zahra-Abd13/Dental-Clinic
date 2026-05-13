package com.mycompany.dentalclinic.service;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author My Pc
 */


import com.mycompany.dentalclinic.exceptions.*;
import com.mycompany.dentalclinic.model.Appointments;
import java.util.ArrayList;
import java.util.List;






import com.mycompany.dentalclinic.model.*;



public class AppointmentService {

    // Fields


    private static List<Appointments> appointments = new ArrayList<>();

   
    private static List<Payment> payments = new ArrayList<>();
    private static List<Prescriptions> prescriptions = new ArrayList<>();

    //Methods

    
    public void bookAppointment(Appointments appt) throws SchedulingException {

        if (appt == null)
            throw new IllegalArgumentException("Appointment cannot be null");

        if (appt.getDoctor() == null)
            throw new IllegalArgumentException("Appointment must have a Doctor");

        if (appt.getPatient() == null)
            throw new IllegalArgumentException("Appointment must have a Patient");

        // Check doctor availability
        for (Appointments a : appointments) {
            if (a.getDoctor() != null &&
                a.getDoctor().equals(appt.getDoctor()) &&
                a.overlaps(appt)) {

                throw new SchedulingException(
                        "Doctor is not available at this time."
                );
            }
        }

        appointments.add(appt);
    }



    public void addPayment(Payment payment) {
        payments.add(payment);
    }

    public void addPrescription(Prescriptions prescription) {
        prescriptions.add(prescription);
    }

    
    public void removeAppointment(Appointments a) {

    if (a == null)
        throw new IllegalArgumentException("Appointment is null");

  
    PaymentService.removeByAppointment(a.getId());
    PrescriptionService.removeByAppointment(a.getId());

    appointments.remove(a);
}


    
    public List<Appointments> getAll() {
        return new ArrayList<>(appointments);
    }

    
    public List<Appointments> getAllAppointments() {
        return appointments;
    }

    
    public static Appointments findAppointmentById(String id)
            throws NotFoundException {

        for (Appointments a : appointments) {
            if (a != null && a.getId() != null &&
                a.getId().equals(id)) {
                return a;
            }
        }
        throw new NotFoundException(
                "Appointment with ID " + id + " not found."
        );
    }
}
