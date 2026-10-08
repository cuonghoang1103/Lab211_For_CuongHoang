/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import constants.SalaryStatus;
import java.time.LocalDate;

/**
 *
 * @author LENOVOLOQ
 */
public class SalaryHistory {
    //Khai bao thuoc tinh
    private double salaryAfter;
    private SalaryStatus status;
    private LocalDate date;

    //Constructor khong co thuoc tinh
    public SalaryHistory() {
    }

    //Constuctor co thuoc tinh
    public SalaryHistory(double salaryAfter, SalaryStatus status, LocalDate date) {
        this.salaryAfter = salaryAfter;
        this.status = status;
        this.date = date;
    }

    //Getter
    public double getSalaryAfter() {
        return salaryAfter;
    }

    public SalaryStatus getStatus() {
        return status;
    }

    public LocalDate getDate() {
        return date;
    }
    
    
    
}
