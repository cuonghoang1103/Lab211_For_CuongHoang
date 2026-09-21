package model;

import constants.TaskType;
import java.util.Date;

/**
 * BUILDER (design pattern): assembles a Task step by step. Every step starts with the verb
 * "set" and returns the builder, so the steps chain: new TaskBuilder().setId(1)...build().
 *
 * @author HE176322
 */
public class TaskBuilder {

    // The task being assembled; handed out by build().
    private Task task;

    // Starts a new, empty task.
    public TaskBuilder() {
        task = new Task();
    }

    // Sets the ID.
    public TaskBuilder setId(int id) {
        task.setId(id);
        return this;
    }

    // Sets the task type.
    public TaskBuilder setTaskType(TaskType taskType) {
        task.setTaskType(taskType);
        return this;
    }

    // Sets the requirement name.
    public TaskBuilder setRequirementName(String requirementName) {
        task.setRequirementName(requirementName);
        return this;
    }

    // Sets the date.
    public TaskBuilder setDate(Date date) {
        task.setDate(date);
        return this;
    }

    // Sets the start time.
    public TaskBuilder setPlanFrom(double planFrom) {
        task.setPlanFrom(planFrom);
        return this;
    }

    // Sets the end time.
    public TaskBuilder setPlanTo(double planTo) {
        task.setPlanTo(planTo);
        return this;
    }

    // Sets the assignee.
    public TaskBuilder setAssignee(String assignee) {
        task.setAssignee(assignee);
        return this;
    }

    // Sets the reviewer.
    public TaskBuilder setReviewer(String reviewer) {
        task.setReviewer(reviewer);
        return this;
    }

    // Finishes the task.
    public Task build() {
        return task;
    }
}
