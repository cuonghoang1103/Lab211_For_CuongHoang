/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import constants.TaskType;
import dto.TaskRequestDTO;
import dto.TaskResponseDTO;
import java.util.ArrayList;
import java.util.List;
import model.Task;
import repository.TaskRepository;

/**
 *
 * @author pc
 */
public class TaskService {

    private TaskRepository taskRepository;

    public TaskService() {
        taskRepository = new TaskRepository();
    }

    //them task
    public void addTask(TaskRequestDTO dto) {
        taskRepository.addTask(dto);
    }

    //xoa task
    public void deleteTask(int taskId) throws Exception {
        taskRepository.deleteTask(taskId);
    }

//lay danh sach da chuyen sang responseDTO de hien thi    
    public List<TaskResponseDTO> getTaskList() {
        List<TaskResponseDTO> taskResponseList = new ArrayList<>();

//chuyen tung task sang responseDTO      
        for (Task t : taskRepository.getTasks()) {
            taskResponseList.add(convertToResponseDTO(t));

        }
        return taskResponseList;
    }
//ham chuyen doi 2 truong hop taskType thanh taskResponseDTO

    private TaskResponseDTO convertToResponseDTO(Task t) {

        TaskType type = TaskType.fromId(t.getTaskTypeId());

        StringBuilder strb = new StringBuilder();
        String time = String.valueOf(t.getTo() - t.getFrom());

        return new TaskResponseDTO(
                t.getId(),
                t.getRequirementName(),
                type.getName(),
                t.getDate(),
                time,
                t.getAssignee(),
                t.getReviewer()
        );
    }
}
