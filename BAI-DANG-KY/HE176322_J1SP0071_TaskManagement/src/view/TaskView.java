/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author pc
 */
import dto.TaskResponseDTO;
import java.util.List;

public class TaskView {

    private List<TaskResponseDTO> data;

//nhan du lieu tu controller   
    public void setData(List<TaskResponseDTO> data) {
        this.data = data;
    }

    //hien thi danh sach task
    public void display() {
        System.out.printf("%-5s%-15s%-12s%-15s%-15s%-10s%-10s\n", "ID", "Name", "Type", "Date", "Time", "Assignee", "Reviewer");
        for (TaskResponseDTO t : data) {
            System.out.println(t);

        }
    }
}
