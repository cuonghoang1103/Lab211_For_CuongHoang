/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.time.LocalDate;

/**
 *
 * @author LENOVOLOQ
 */
public class SalaryHistoryResponseDTO {
    private String id;
    private String name;
    private int age;
    private double salary;
    private String status;
    private LocalDate date;

    public SalaryHistoryResponseDTO(String id, String name, int age, double salary, String status, LocalDate date) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.status = status;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getSalary() {
        return salary;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return String.format("%-10s%-15s%-10d%-15.2f%-10s%-15s",id,name,age,salary,status,date);
    
    }
    
    
}
