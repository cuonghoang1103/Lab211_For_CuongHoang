package view;

import constants.Constants;
import constants.Message;
import dto.TaskResponseDTO;
import java.util.ArrayList;

/**
 * VIEW: the only place (with main) allowed to print results.
 *
 * @author HE176322
 */
public class TaskView {

    // The rows to display, handed over by the controller.
    private ArrayList<TaskResponseDTO> tasks;

    // Receives the rows the next display() call will print.
    public void setTasks(ArrayList<TaskResponseDTO> tasks) {
        this.tasks = tasks;
    }

    // Prints the task table of the brief, or "There is no task yet.".
    public void display() {
        System.out.println(Message.TITLE_TASK);
        // nothing added yet (or everything deleted)
        if (tasks == null || tasks.isEmpty()) {
            System.out.println(Message.NO_TASK);
            return;
        }
        System.out.println(String.format(Constants.HEADER_FORMAT, Message.LABEL_ID,
                Message.LABEL_NAME, Message.LABEL_TASK_TYPE, Message.LABEL_DATE,
                Message.LABEL_TIME, Message.LABEL_ASSIGNEE, Message.LABEL_REVIEWER));
        // one line per task; toString() of the DTO is already padded
        for (TaskResponseDTO task : tasks) {
            System.out.println(task);
        }
    }

    // Prints a one-line result such as "Task [1] has been added.".
    public void showMessage(String message) {
        System.out.println(message);
    }
}
