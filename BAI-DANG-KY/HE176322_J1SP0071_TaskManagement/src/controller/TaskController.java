/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dto.TaskRequestDTO;
import service.TaskService;
import view.TaskView;

/**
 *
 * @author pc
 */
public class TaskController {

    private TaskService taskService;
    private TaskView taskView;

    public TaskController() {
        taskView = new TaskView();
        taskService = new TaskService();

    }

    //them task
    public void addTask(TaskRequestDTO dto) {
        taskService.addTask(dto);
    }
    //xoa task

    public void deleteTask(int taskId) throws Exception {
        taskService.deleteTask(taskId);
    }
    //hien thi danh sach task

    public void displayTask() {
        taskView.setData(taskService.getTaskList());
        taskView.display();
    }
}
