package com.mycompany.dentalclinic.service;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author My Pc
 */
import com.mycompany.dentalclinic.exceptions.NotFoundException;
import com.mycompany.dentalclinic.exceptions.InvalidDataException;
import com.mycompany.dentalclinic.model.Patient;
import com.mycompany.dentalclinic.util.SortUtil;
import java.util.ArrayList;
import java.util.List;


public class PatientService {

    //Fields 

    private static List<Patient> patients = new ArrayList<>();

    
    
    
    
    
    // Methods

    
    public void addPatient(Patient p) throws InvalidDataException {
        if (p.getName() == null || p.getName().isEmpty()) {
            throw new InvalidDataException("Patient name cannot be empty.");
        }
        patients.add(p);
    }

    
    public void removePatient(String id) throws NotFoundException {
        Patient p = findPatientById(id);
        patients.remove(p);
    }

    
    public static Patient findPatientById(String id) throws NotFoundException {
        for (Patient p : patients) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        throw new NotFoundException("Patient with ID " + id + " not found.");
    }

    
    public Patient findPatientByName(String name) throws NotFoundException {
        for (Patient p : patients) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        throw new NotFoundException("Patient with name " + name + " not found.");
    }

    
    public int getPatientCount() {
        return patients.size();
    }

   
    public List<Patient> getAllPatients() {
        return patients;
    }

    
    public void printAllPatients() {
        System.out.println("---- List of Patients ----");
        for (Patient p : patients) {
            p.printInfo();
        }
        System.out.println("Total Patients: " + patients.size());
    }

   
    public void sortPatients() {
        SortUtil.genericSort(patients);
    }
}
