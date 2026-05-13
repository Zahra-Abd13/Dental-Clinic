package com.mycompany.dentalclinic.service;


import com.mycompany.dentalclinic.exceptions.NotFoundException;
import com.mycompany.dentalclinic.exceptions.InvalidDataException;
import com.mycompany.dentalclinic.model.Doctor;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author My Pc
 */

public class DoctorService {

    //Fields
  
    private static List<Doctor> doctors = new ArrayList<>();

    //Methods 

    
    public void addDoctor(Doctor d) throws InvalidDataException {
        if (d.getName() == null || d.getName().isEmpty()) {
            throw new InvalidDataException("Doctor name cannot be empty.");
        }
        doctors.add(d);
    }

    
    public void removeDoctor(String id) throws NotFoundException {
        Doctor d = findDoctorById(id);
        doctors.remove(d);
    }

    
    public static Doctor findDoctorById(String id) throws NotFoundException {
        for (Doctor d : doctors) {
            if (d.getId().equals(id)) {
                return d;
            }
        }
        throw new NotFoundException("Doctor with ID " + id + " not found.");
    }

    
    public Doctor findDoctorByName(String name) throws NotFoundException {
        for (Doctor d : doctors) {
            if (d.getName().equalsIgnoreCase(name)) {
                return d;
            }
        }
        throw new NotFoundException("Doctor with name " + name + " not found.");
    }

    
    public int getDoctorCount() {
        return doctors.size();
    }

    
    public List<Doctor> getAllDoctors() {
        return doctors;
    }

    
    public void printAllDoctors() {
        System.out.println("---- List of Doctors ----");
        for (Doctor d : doctors) {
            d.printInfo();
        }
        System.out.println("Total Doctors: " + doctors.size());
    }
}
