package com.mycompany.dentalclinic.model;


import com.mycompany.dentalclinic.model.Identifiable;
import com.mycompany.dentalclinic.model.Appointments;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author My Pc
 */



public class Payment implements Identifiable {

    //  Fields 
    private String id;                 
    private Appointments appointment;  
    private double amount;             
    private String method;             

    // Constructor 
    
    public Payment(String id, Appointments appointment, String method) {
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment cannot be null");
        }
        if (appointment.getDoctor() == null) {
            throw new IllegalArgumentException("Appointment must have a Doctor");
        }
        if (appointment.getPatient() == null) {
            throw new IllegalArgumentException("Appointment must have a Patient");
        }

        this.id = id;
        this.appointment = appointment;
        this.amount = appointment.getDoctor().getFee(); // Set amount based on doctor's fee
        this.method = method;
    }

    //Getters 
    @Override
    public String getId() {
        return id;
    }

    public Appointments getAppointment() {
        return appointment;
    }

    public double getAmount() {
        return amount;
    }

    public String getMethod() {
        return method;
    }

    //Setters
    public void setMethod(String method) {
        this.method = method;
    }
}
