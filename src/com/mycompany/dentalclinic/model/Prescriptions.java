package com.mycompany.dentalclinic.model;


import com.mycompany.dentalclinic.model.Doctor;
import com.mycompany.dentalclinic.model.Appointments;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author My Pc
 */
import java.util.ArrayList;
import java.util.List;


public class Prescriptions implements Identifiable {

    // Fields 
    private String id;                 
    private Patient patient;            
    private Doctor doctor;              
    private Appointments appointment;   
    private List<String> medicines;     
    private String notes;              

    //Constructor 
    
    public Prescriptions(Patient patient, Doctor doctor, String id, Appointments appointment, String notes) {
        this.patient = patient;
        this.doctor = doctor;
        this.medicines = new ArrayList<>(); // Initialize empty medicine list
        this.id = id;
        this.appointment = appointment;
        this.notes = notes;
    }

    //Medicine Management
    
    public void addMedicine(String medicine) {
        medicines.add(medicine);
    }

    
    public void removeMedicine(String medicine) {
        medicines.remove(medicine);
    }

    //Getters 
    @Override
    public String getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Appointments getAppointment() {
        return appointment;
    }

    public List<String> getMedicines() {
        return medicines;
    }

    public String getNotes() {
        return notes;
    }
    

    //Setters
    public void setMedicines(List<String> medicines) {
        this.medicines = medicines;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    
   
    public void printPrescription() {
        System.out.println("ID: " + id);
        System.out.println("Prescription for: " + patient.getName());
        System.out.println("Doctor: " + doctor.getName());
        System.out.println("Notes: " + notes);
        System.out.println("Appointment ID: " + appointment.getId());
        System.out.println("Medicines:");

        if (medicines.isEmpty()) {
            System.out.println("No medicines added.");
        } else {
            for (String med : medicines) {
                System.out.println("- " + med);
            }
        }
    }

    
}
