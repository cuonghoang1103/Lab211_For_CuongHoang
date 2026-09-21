package dto;

import constants.Constants;

/**
 * DTO carrying one row of the task table FROM the repository OUT TO the view (inside
 * TaskResponseDTO) - a JavaBean (private fields, public no-argument constructor,
 * getters/setters). The view never sees the model Task itself.
 *
 * @author HE176322
 */
public class TaskDTO {

    // ID column.
    private int id;

    // Name column (requirement name).
    private String requirementName;

    // Task Type column (type name).
    private String taskType;

    // Date column, dd-MM-yyyy.
    private String date;

    // Time column, the hours of the task, e.g. "8.0".
    private String time;

    // Assignee column.
    private String assignee;

    // Reviewer column.
    private String reviewer;

    // JavaBean constructor: an empty row, filled through the setters.
    public TaskDTO() {
    }

    // Returns the ID.
    public int getId() {
        return id;
    }

    // Sets the ID.
    public void setId(int id) {
        this.id = id;
    }

    // Returns the requirement name.
    public String getRequirementName() {
        return requirementName;
    }

    // Sets the requirement name.
    public void setRequirementName(String requirementName) {
        this.requirementName = requirementName;
    }

    // Returns the type name.
    public String getTaskType() {
        return taskType;
    }

    // Sets the type name.
    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    // Returns the date text.
    public String getDate() {
        return date;
    }

    // Sets the date text.
    public void setDate(String date) {
        this.date = date;
    }

    // Returns the time text.
    public String getTime() {
        return time;
    }

    // Sets the time text.
    public void setTime(String time) {
        this.time = time;
    }

    // Returns the assignee.
    public String getAssignee() {
        return assignee;
    }

    // Sets the assignee.
    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    // Returns the reviewer.
    public String getReviewer() {
        return reviewer;
    }

    // Sets the reviewer.
    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    // One table row, already padded into fixed-width columns.
    @Override
    public String toString() {
        return String.format(Constants.ROW_FORMAT, id, requirementName, taskType,
                date, time, assignee, reviewer);
    }
}
