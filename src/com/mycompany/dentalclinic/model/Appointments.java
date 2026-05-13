package com.mycompany.dentalclinic.model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author My Pc
 */
import java.time.LocalDateTime;


public class Appointments implements Comparable<Appointments> {

    // ===== Fields =====
    private String id;               
    private Patient patient;         
    private Doctor doctor;           
    private LocalDateTime dateTime;  
    private double price; 
     private Prescriptions prescription;
    private Payment payment;

    private static int noAppointments; // Counter for total number of appointments


    
    public Appointments(String id, Patient patient, Doctor doctor, LocalDateTime dateTime) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.dateTime = dateTime;
        this.price = doctor.getFee(); // Set the price based on the doctor's fee

        noAppointments++; // Increment the total appointment counter
    }

    //Getters 
    public String getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public double getPrice() {
        return price;
    }

    public static int getNoAppointments() {
        return noAppointments;
    }

    public Prescriptions getPrescription() {
        return prescription;
    }

    public Payment getPayment() {
        return payment;
    }

    // Setters
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
        this.price = doctor.getFee(); // Update price if doctor changes
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public static void setNoAppointments(int noAppointments) {
        Appointments.noAppointments = noAppointments;
    }

    // Comparable Implementation
   
    @Override
    public int compareTo(Appointments other) {
        return this.dateTime.compareTo(other.dateTime);
    }

    //Utility Methods 
    
    public boolean overlaps(Appointments other) {
        return this.dateTime.equals(other.getDateTime()) &&
               this.doctor.equals(other.getDoctor());
    }

   
    @Override
    public String toString() {
        return id + " | " + patient.getName() + " with Dr. " + doctor.getName() +
               " at " + dateTime.toString();
    }
}
