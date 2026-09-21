package model;

import constants.Constants;
import constants.TaskType;
import java.util.Date;

/**
 * MODEL: one task of the brief - ID, type, requirement name, date, plan from, plan to,
 * assignee, reviewer - and nothing else.
 *
 * @author HE176322
 */
public class Task {

    // Unique ID; the repository gives it (last ID + 1).
    private int id;

    // One of the four fixed types.
    private TaskType taskType;

    // Name of the requirement.
    private String requirementName;

    // Day the task is done; a real Date, parsed from dd-MM-yyyy.
    private Date date;

    // Start time, 8.0 ..
    private double planFrom;

    // End time, 8.0 ..
    private double planTo;

    // Person who does the task.
    private String assignee;

    // Person who reviews the task.
    private String reviewer;

    // JavaBean constructor: an empty task, filled by TaskBuilder.
    public Task() {
    }

    // Returns the ID.
    public int getId() {
        return id;
    }

    // Sets the ID.
    public void setId(int id) {
        this.id = id;
    }

    // Returns the task type.
    public TaskType getTaskType() {
        return taskType;
    }

    // Sets the task type.
    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    // Returns the requirement name.
    public String getRequirementName() {
        return requirementName;
    }

    // Sets the requirement name.
    public void setRequirementName(String requirementName) {
        this.requirementName = requirementName;
    }

    // Returns the date.
    public Date getDate() {
        return date;
    }

    // Sets the date.
    public void setDate(Date date) {
        this.date = date;
    }

    // Returns the start time.
    public double getPlanFrom() {
        return planFrom;
    }

    // Sets the start time.
    public void setPlanFrom(double planFrom) {
        this.planFrom = planFrom;
    }

    // Returns the end time.
    public double getPlanTo() {
        return planTo;
    }

    // Sets the end time.
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

    // Returns how many hours the task is planned for: plan to - plan from (the brief's Time
    // column: 9.5 to 17.5 is 8.0).
    public double calculateTime() {
        return planTo - planFrom;
    }

    // Polymorphism: overrides Object.toString() so a task reads as one line in the
    // debugger (String.format, no string concatenation).
    @Override
    public String toString() {
        return String.format(Constants.TASK_TEXT_FORMAT, id, requirementName,
                taskType.getName());
    }
}
