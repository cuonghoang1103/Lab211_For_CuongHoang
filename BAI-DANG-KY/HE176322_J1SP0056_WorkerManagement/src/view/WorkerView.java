/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import dto.SalaryHistoryResponseDTO;
import java.util.List;

/**
 *
 * @author LENOVOLOQ
 */
public class WorkerView {

    //Khai bao du lieu da duoc xu ly
    private List<SalaryHistoryResponseDTO> data;

    //Constuctor
    public WorkerView() {
    }

    //Setter
    public void setData(List<SalaryHistoryResponseDTO> data) {
        this.data = data;
    }

    //In ra theo format cua de bai
    public void display() {
        System.out.printf("%-10s%-15s%-10s%-15s%-10s%-12s\n", "ID", "Name", "Age", "Salary", "Status", "Date");
        
        //Duyet du lieu roi in ra
        for (SalaryHistoryResponseDTO h : this.data) {
            System.out.println(h);
        }
    }
}
