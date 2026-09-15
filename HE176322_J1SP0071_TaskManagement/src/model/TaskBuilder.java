package model;

import constants.TaskType;
import java.util.Date;

/**
 * BUILDER (design pattern): assembles a Task step by step.
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
    public TaskBuilder withId(int id) {
        task.setId(id);
        return this;
    }

    // Sets the task type.
    public TaskBuilder withTaskType(TaskType taskType) {
        task.setTaskType(taskType);
        return this;
    }

    // Sets the requirement name.
    public TaskBuilder withRequirementName(String requirementName) {
        task.setRequirementName(requirementName);
        return this;
    }

    // Sets the date.
    public TaskBuilder withDate(Date date) {
        task.setDate(date);
        return this;
    }

    // Sets the start time.
    public TaskBuilder withPlanFrom(double planFrom) {
        task.setPlanFrom(planFrom);
        return this;
    }

    // Sets the end time.
    public TaskBuilder withPlanTo(double planTo) {
        task.setPlanTo(planTo);
        return this;
    }

    // Sets the assignee.
    public TaskBuilder withAssignee(String assignee) {
        task.setAssignee(assignee);
        return this;
    }

    // Sets the reviewer.
    public TaskBuilder withReviewer(String reviewer) {
        task.setReviewer(reviewer);
        return this;
    }

    // Finishes the task.
    public Task build() {
        return task;
    }
}
