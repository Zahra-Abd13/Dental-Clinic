/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dentalclinic.service;
import com.mycompany.dentalclinic.model.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author My Pc
 */
public class PrescriptionService {

    private static List<Prescriptions> prescriptions = new ArrayList<>();

    public static void removeByAppointment(String appointmentId) {
        prescriptions.removeIf(
            p -> p.getAppointment().getId().equals(appointmentId)
        );
    }
}
