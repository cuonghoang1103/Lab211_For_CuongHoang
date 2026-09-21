package controller;

import constants.Message;
import dto.TaskRequestDTO;
import dto.TaskResponseDTO;
import repository.TaskRepository;
import view.TaskView;

/**
 * CONTROLLER (and FACADE for main): receives a request DTO from main, asks the repository
 * to do the work, and hands the result to the view - once per flow. No Scanner, no print,
 * no model.
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

    // Option 1: the brief's addTask stores the task Main has checked; the view shows its new
    // ID.
    public void addTask(TaskRequestDTO requestDTO) {
        TaskResponseDTO responseDTO = new TaskResponseDTO();
        int taskId = taskRepository.addTask(requestDTO);

        // hand the answer to the view, then render it - once for the whole flow
        responseDTO.setMessage(String.format(Message.ADD_SUCCESS, taskId));
        taskView.setResponseDTO(responseDTO);
        taskView.display();
    }

    // Option 2: the brief's deleteTask removes the task (or throws "Task [9] does not
    // exist."); the view shows which ID was deleted.
    public void deleteTask(TaskRequestDTO requestDTO) throws Exception {
        TaskResponseDTO responseDTO = new TaskResponseDTO();

        // an unknown ID stops the flow here: main prints the message
        taskRepository.deleteTask(requestDTO);

        // deleted: hand the answer to the view, then render it - once for the whole flow
        responseDTO.setMessage(String.format(Message.DELETE_SUCCESS, requestDTO.getId()));
        taskView.setResponseDTO(responseDTO);
        taskView.display();
    }

    // Option 3: the brief's getDataTasks gives every task ascending by ID; the view shows
    // the table.
    public void displayTasks() {
        TaskResponseDTO responseDTO = new TaskResponseDTO();

        // hand the rows to the view, then render them - once for the whole flow
        responseDTO.setTaskList(taskRepository.getDataTasks());
        taskView.setResponseDTO(responseDTO);
        taskView.display();
    }
}
