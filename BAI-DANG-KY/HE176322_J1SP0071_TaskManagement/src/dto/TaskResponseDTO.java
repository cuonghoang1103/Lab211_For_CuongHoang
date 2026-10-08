/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author pc
 */
public class TaskResponseDTO {

    private int id;
    private String requirementName;
    private String taskType;
    private Date date;
    private String time;
    private String assignee;
    private String reviewer;
//contructor chua tham so

    public TaskResponseDTO(int id, String requirementName,
            String taskType, Date date, String time,
            String assignee, String reviewer) {
        this.id = id;
        this.requirementName = requirementName;
        this.taskType = taskType;
        this.date = date;
        this.time = time;
        this.assignee = assignee;
        this.reviewer = reviewer;
    }
//hien thi danh sach task

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return String.format("%-5d%-15s%-12s%-15s%-15s%-10s%-10s", id,
                requirementName, taskType, sdf.format(date),
                time, assignee, reviewer);
    }

}
