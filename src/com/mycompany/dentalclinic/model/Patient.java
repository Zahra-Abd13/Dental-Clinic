/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dentalclinic.model;

/**
 *
 * @author My Pc
 */

public class Patient extends User implements Comparable<Patient> {

    //Fields
    private int age;                   
    private String medicalHistory;     
    private static int noPatients;    

    // Constructor
    
    public Patient(String id, String name, int age, String phone, String email, String medicalHistory) {
        super(id, name, phone, email);
        this.age = age;
        this.medicalHistory = medicalHistory;
        noPatients++; 
    }

    //Getters
    public int getAge() {
        return age;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public static int getNoPatients() {
        return noPatients;
    }

    //Setters
    public void setAge(int age) {
        this.age = age;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public static void setNoPatients(int noPatients) {
        Patient.noPatients = noPatients;
    }

    //Comparable Implementation
    
    @Override
    public int compareTo(Patient other) {
        return this.name.compareToIgnoreCase(other.name);
    }

   
    @Override
    public void printInfo() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email);
        System.out.println("Medical History: " + medicalHistory);
    }
}
