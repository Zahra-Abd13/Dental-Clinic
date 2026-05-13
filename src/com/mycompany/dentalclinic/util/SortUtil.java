package com.mycompany.dentalclinic.util;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author My Pc
 */
import java.util.Collections;
import java.util.List;

public class SortUtil {
    public static <T extends Comparable<T>> void genericSort(List<T> list) {
        Collections.sort(list);
    }
}