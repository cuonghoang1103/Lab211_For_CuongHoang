package repository;

import constants.Constants;
import constants.Message;
import constants.TaskType;
import dto.TaskRequestDTO;
import dto.TaskResponseDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import model.Task;
import model.TaskBuilder;
import utils.FormatUtils;
import utils.Validation;

/**
 * REPOSITORY: holds the tasks and performs the brief's three functions on them - addTask,
 * deleteTask, getDataTasks.
 *
 * @author HE176322
 */
public class TaskRepository {

    // The "database" of tasks, in the order they were added.
    private ArrayList<Task> tasks = new ArrayList<>();
    // The ID the next task will get.
    private int nextId = Constants.FIRST_ID;

    // Creates an empty repository.
    public TaskRepository() {
    }

    // The brief's addTask: checks every value, then adds the task.
    public int addTask(TaskRequestDTO requestDTO) throws Exception {
        String name = Validation.getRequired(requestDTO.getRequirementName(),
                Message.NAME_EMPTY);
        String assignee = Validation.getRequired(requestDTO.getAssignee(),
                Message.ASSIGNEE_EMPTY);
        String reviewer = Validation.getRequired(requestDTO.getReviewer(),
                Message.REVIEWER_EMPTY);
        TaskType type = Validation.getTaskType(requestDTO.getTaskTypeId());
        Date date = Validation.getDate(requestDTO.getDate());
        double planFrom = Validation.getPlanTime(requestDTO.getPlanFrom(),
                Message.LABEL_PLAN_FROM);
        double planTo = Validation.getPlanTime(requestDTO.getPlanTo(),
                Message.LABEL_PLAN_TO);
        Validation.checkPlanOrder(planFrom, planTo);
        Task task = new TaskBuilder()
                .withId(nextId)
                .withTaskType(type)
                .withRequirementName(name)
                .withDate(date)
                .withPlanFrom(planFrom)
                .withPlanTo(planTo)
                .withAssignee(assignee)
                .withReviewer(reviewer)
                .build();
        tasks.add(task);
        nextId++;
        return task.getId();
    }

    // The brief's deleteTask: removes the task with the typed ID.
    public void deleteTask(TaskRequestDTO requestDTO) throws Exception {
        int id = Validation.getId(requestDTO.getId());
        // look for the task with this ID
        for (int i = 0; i < tasks.size(); i++) {
            // found: remove it and stop
            if (tasks.get(i).getId() == id) {
                tasks.remove(i);
                return;
            }
        }
        throw new Exception(String.format(Message.TASK_NOT_EXIST, id));
    }

    // The brief's getDataTasks: every task, ascending by ID.
    public ArrayList<TaskResponseDTO> getDataTasks() {
        ArrayList<Task> sorted = new ArrayList<>(tasks);
        Collections.sort(sorted, new Comparator<Task>() {
            // Orders two tasks by ID.
            @Override
            public int compare(Task first, Task second) {
                return Integer.compare(first.getId(), second.getId());
            }
        });
        ArrayList<TaskResponseDTO> rows = new ArrayList<>();
        // turn every task into the row the view is allowed to see
        for (Task task : sorted) {
            rows.add(toResponse(task));
        }
        return rows;
    }

    // Copies a model object into the DTO the view may see, with the date and the time
    // already written as text.
    private TaskResponseDTO toResponse(Task task) {
        TaskResponseDTO row = new TaskResponseDTO();
        row.setId(task.getId());
        row.setRequirementName(task.getRequirementName());
        row.setTaskType(task.getTaskType().getName());
        row.setDate(FormatUtils.formatDate(task.getDate()));
        row.setTime(FormatUtils.formatTime(task.getPlanFrom(), task.getPlanTo()));
        row.setAssignee(task.getAssignee());
        row.setReviewer(task.getReviewer());
        return row;
    }
}
