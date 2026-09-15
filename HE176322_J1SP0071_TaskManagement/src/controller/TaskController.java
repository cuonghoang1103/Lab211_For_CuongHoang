package controller;

import constants.Message;
import dto.TaskRequestDTO;
import repository.TaskRepository;
import view.TaskView;

/**
 * CONTROLLER (and FACADE for main): receives a request DTO from main, asks the repository
 * to do the work, and hands the result to the view.
 *
 * @author HE176322
 */
public class TaskController {

    // Where the tasks are stored.
    private TaskRepository taskRepository;
    // Where the results are printed.
    private TaskView taskView;

    // Creates the controller together with its repository and view.
    public TaskController() {
        taskRepository = new TaskRepository();
        taskView = new TaskView();
    }

    // Option 1: adds a task and shows its new ID.
    public void addTask(TaskRequestDTO requestDTO) throws Exception {
        int id = taskRepository.addTask(requestDTO);
        taskView.showMessage(String.format(Message.ADD_SUCCESS, id));
    }

    // Option 2: deletes the task with the typed ID.
    public void deleteTask(TaskRequestDTO requestDTO) throws Exception {
        taskRepository.deleteTask(requestDTO);
        taskView.showMessage(String.format(Message.DELETE_SUCCESS, requestDTO.getId()));
    }

    // Option 3: shows every task, ascending by ID.
    public void displayTasks() {
        taskView.setTasks(taskRepository.getDataTasks());
        taskView.display();
    }
}
