package dto;

import constants.TaskType;
import java.util.Date;

/**
 * DTO carrying what the user typed, FROM main INTO the controller - already checked by
 * Main through Validation, so every value has its real type (a Date, a double...).
 *
 * @author HE176322
 */
public class TaskRequestDTO {

    // Requirement name typed, not blank.
    private String requirementName;

    // Task type chosen by its ID (1..4).
    private TaskType taskType;

    // Date typed, a real dd-MM-yyyy date.
    private Date date;

    // Plan from typed, 8.0..17.5 on a half hour.
    private double planFrom;

    // Plan to typed, 8.0..17.5 on a half hour, after plan from.
    private double planTo;

    // Assignee typed, not blank.
    private String assignee;

    // Reviewer typed, not blank.
    private String reviewer;

    // ID typed on the delete screen, a whole number.
    private int id;

    // Creates an empty request; main fills it through the setters.
    public TaskRequestDTO() {
    }

    // Returns the requirement name.
    public String getRequirementName() {
        return requirementName;
    }

    // Sets the requirement name.
    public void setRequirementName(String requirementName) {
        this.requirementName = requirementName;
    }

    // Returns the task type.
    public TaskType getTaskType() {
        return taskType;
    }

    // Sets the task type.
    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    // Returns the date.
    public Date getDate() {
        return date;
    }

    // Sets the date.
    public void setDate(Date date) {
        this.date = date;
    }

    // Returns the plan from time.
    public double getPlanFrom() {
        return planFrom;
    }

    // Sets the plan from time.
    public void setPlanFrom(double planFrom) {
        this.planFrom = planFrom;
    }

    // Returns the plan to time.
    public double getPlanTo() {
        return planTo;
    }

    // Sets the plan to time.
    public void setPlanTo(double planTo) {
        this.planTo = planTo;
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

    // Returns the ID to delete.
    public int getId() {
        return id;
    }

    // Sets the ID to delete.
    public void setId(int id) {
        this.id = id;
    }
}
