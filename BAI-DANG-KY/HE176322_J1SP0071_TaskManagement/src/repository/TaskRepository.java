/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import constants.Message;
import dto.TaskRequestDTO;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.Task;

/**
 *
 * @author pc
 */
public class TaskRepository {
//danh sach luu tru  cac task trong bo nho

    private List<Task> taskList;
//bien luu id cuoi cung de tu dong tang 
    private int lastId;
//khoi tao taskList va lastId   

    public TaskRepository() {
        taskList = new ArrayList<>();
        lastId = 0;
    }
// them task   

    public int addTask(TaskRequestDTO dto) {
//tao doi tuong task tu dto        
        Task task = new Task(++lastId, dto.getTaskTypeID(),
                dto.getRequirementName(), dto.getDate(), dto.getFrom(),
                dto.getTo(), dto.getAssignee(), dto.getReviewer());
//them task vao danh sach        
        taskList.add(task);
//tra ve Id vua tao
        return lastId;
    }
//tim task bang id

    private Task findTaskById(int taskId) throws Exception {
//duyet tung task bang danh sach        
        for (Task t : taskList) {
//neu id trung nhau thi tra ve task            
            if (t.getId() == taskId) {
                return t;
            }
        }
        throw new Exception(Message.TASK_NOT_EXIST);

    }
//xoa task    

    public void deleteTask(int taskId) throws Exception {
        Task task = findTaskById(taskId);
        taskList.remove(task);
    }

    public List<Task> getTasks() {
//sap xep theo id tang dan
        taskList.sort(Comparator.comparing(Task::getId));
        return new ArrayList<>(taskList);
    }

}
