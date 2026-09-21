package repository;

import constants.Constants;
import constants.Message;
import dto.TaskDTO;
import dto.TaskRequestDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import model.Task;
import model.TaskBuilder;
import utils.FormatUtils;

/**
 * REPOSITORY: holds the data of the program - the tasks - and the brief's three functions
 * on them, which are simple CRUD: addTask (create), deleteTask (delete), getDataTasks
 * (read). No check of what was typed (Main did it), no print.
 *
 * @author HE176322
 */
public class TaskRepository {

    // The "database" of tasks, in the order they were added.
    private ArrayList<Task> taskList;

    // The ID the next task will get.
    private int nextId;

    // Creates an empty repository; the first task will get ID 1.
    public TaskRepository() {
        taskList = new ArrayList<>();
        nextId = Constants.FIRST_ID;
    }

    // The brief's addTask: stores the task Main has already checked, with the next ID
    // (last ID + 1), and returns that ID.
    public int addTask(TaskRequestDTO requestDTO) {
        Task task = new TaskBuilder()
                .setId(nextId)
                .setTaskType(requestDTO.getTaskType())
                .setRequirementName(requestDTO.getRequirementName())
                .setDate(requestDTO.getDate())
                .setPlanFrom(requestDTO.getPlanFrom())
                .setPlanTo(requestDTO.getPlanTo())
                .setAssignee(requestDTO.getAssignee())
                .setReviewer(requestDTO.getReviewer())
                .build();

        // keep the task, then move the counter on for the next one
        taskList.add(task);
        nextId++;
        return task.getId();
    }

    // The brief's deleteTask: removes the task with the typed ID ("Id must exist in the
    // DB").
    public void deleteTask(TaskRequestDTO requestDTO) throws Exception {
        // look for the task with this ID
        for (int i = 0; i < taskList.size(); i++) {
            // found: remove it and stop
            if (taskList.get(i).getId() == requestDTO.getId()) {
                taskList.remove(i);
                return;
            }
        }

        // no task has this ID
        throw new Exception(String.format(Message.TASK_NOT_EXIST, requestDTO.getId()));
    }

    // The brief's getDataTasks: every task as a row of the table, ascending by ID.
    public ArrayList<TaskDTO> getDataTasks() {
        ArrayList<Task> sortedList = new ArrayList<>(taskList);
        ArrayList<TaskDTO> rowList = new ArrayList<>();

        // ascending by ID (the brief), on a copy so the stored list is untouched
        Collections.sort(sortedList, new Comparator<Task>() {
            // Orders two tasks by ID.
            @Override
            public int compare(Task first, Task second) {
                return Integer.compare(first.getId(), second.getId());
            }
        });

        // turn every task into the row the view is allowed to see
        for (Task task : sortedList) {
            rowList.add(convertToTaskDTO(task));
        }

        return rowList;
    }

    // Copies a model object into the row the view may see, with the date and the time
    // already written as text.
    private TaskDTO convertToTaskDTO(Task task) {
        TaskDTO row = new TaskDTO();

        // one setter per column of the brief's table
        row.setId(task.getId());
        row.setRequirementName(task.getRequirementName());
        row.setTaskType(task.getTaskType().getName());
        row.setDate(FormatUtils.formatDate(task.getDate()));
        row.setTime(FormatUtils.formatTime(task.calculateTime()));
        row.setAssignee(task.getAssignee());
        row.setReviewer(task.getReviewer());
        return row;
    }
}
