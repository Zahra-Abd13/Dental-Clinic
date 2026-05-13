package com.mycompany.dentalclinic.model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author My Pc
 */

public class Doctor extends User  {

    // Fields 
    private double fee; 

    
    public Doctor(String id, String name, String phone, String email, double fee) {
        super(id, name, phone, email);
        this.fee = fee;
    }

    // Getters
    public double getFee() {
        return fee;
    }
    
    

    //Setters
    public void setFee(double fee) {
        this.fee = fee;
    }

    
    @Override
    public void printInfo() {
        System.out.println("ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Fee: " + fee);
        System.out.println("Phone: " + getPhone());
        System.out.println("Email: " + getEmail());
    }
    
    
}
