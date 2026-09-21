package dto;

import java.util.ArrayList;

/**
 * DTO carrying the answer of one flow FROM the controller OUT TO the view: the line of an
 * add or a delete, or the rows of the task table. The view keeps it as its attribute and
 * prints what was set.
 *
 * @author HE176322
 */
public class TaskResponseDTO {

    // Options 1 and 2: the one line of the result, e.g. "Task [1] has been added.".
    private String message;

    // Option 3: the rows of the table, ascending by ID; null for options 1 and 2.
    private ArrayList<TaskDTO> taskList;

    // JavaBean constructor: an empty answer, filled through the setters.
    public TaskResponseDTO() {
    }

    // Returns the line of the result.
    public String getMessage() {
        return message;
    }

    // Sets the line of the result.
    public void setMessage(String message) {
        this.message = message;
    }

    // Returns the rows of the table.
    public ArrayList<TaskDTO> getTaskList() {
        return taskList;
    }

    // Sets the rows of the table.
    public void setTaskList(ArrayList<TaskDTO> taskList) {
        this.taskList = taskList;
    }
}
