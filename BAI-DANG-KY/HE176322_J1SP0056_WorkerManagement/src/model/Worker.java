/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import constants.SalaryStatus;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVOLOQ
 */
public class Worker {
    
    //Khai bao thuoc tinh
    private String id;
    private String name;
    private int age;
    private double currentSalary;
    private String workLocation;
    private List<SalaryHistory> history;
    

    //Consturctor co thuoc tinh
    public Worker(String id, String name, int age, double currentSalary, String workLocation) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.currentSalary = currentSalary;
        this.workLocation = workLocation;
        this.history = new ArrayList<>();
        this.addHistory(new SalaryHistory(currentSalary, SalaryStatus.UP, LocalDate.now()));
    }

    //Getter
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getCurrentSalary() {
        return currentSalary;
    }

    public String getWorkLocation() {
        return workLocation;
    }

    public List<SalaryHistory> getHistory() {
        return history;
    }

    //Setter de chinh sua luong
    public void setCurrentSalary(double currentSalary) {
        this.currentSalary = currentSalary;
    }
    
    //Tao ban ghi lich su moi
    public void addHistory(SalaryHistory salaryHistory)
    {
        this.history.add(salaryHistory);
    }
}
