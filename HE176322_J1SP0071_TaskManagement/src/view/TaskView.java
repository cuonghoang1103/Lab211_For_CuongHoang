package view;

import constants.Constants;
import constants.Message;
import dto.TaskDTO;
import dto.TaskResponseDTO;

/**
 * VIEW: the only place (with main) allowed to print results. It receives the data through
 * its attribute (the ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class TaskView {

    // The answer to print, handed over by the controller.
    private TaskResponseDTO responseDTO;

    // Receives the answer the next display() call will print.
    public void setResponseDTO(TaskResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints what the controller set: the line of an add or a delete, or the task table of
    // the brief (its title, then "There is no task yet." or the header and one row per
    // task).
    public void display() {
        // options 1 and 2: only the line of the result was set
        if (responseDTO.getTaskList() == null) {
            System.out.println(responseDTO.getMessage());
            return;
        }

        // option 3: the title of the table comes first, even when there is no task
        System.out.println(Message.TITLE_TASK);

        // nothing added yet (or everything deleted)
        if (responseDTO.getTaskList().isEmpty()) {
            System.out.println(Message.NO_TASK);
            return;
        }

        // the header, with the same column widths as the rows
        System.out.println(String.format(Constants.HEADER_FORMAT, Message.LABEL_ID,
                Message.LABEL_NAME, Message.LABEL_TASK_TYPE, Message.LABEL_DATE,
                Message.LABEL_TIME, Message.LABEL_ASSIGNEE, Message.LABEL_REVIEWER));

        // one line per task; toString() of the row is already padded
        for (TaskDTO row : responseDTO.getTaskList()) {
            System.out.println(row);
        }
    }
}
