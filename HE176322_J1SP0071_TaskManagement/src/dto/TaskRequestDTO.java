package dto;

/**
 * DTO carrying what the user typed, FROM main INTO the controller.
 *
 * @author HE176322
 */
public class TaskRequestDTO {

    // Requirement name typed.
    private String requirementName;
    // Task type ID typed (should be 1..4).
    private String taskTypeId;
    // Date typed (should be dd-MM-yyyy).
    private String date;
    // Plan from typed (should be 8.0..17.5).
    private String planFrom;
    // Plan to typed (should be 8.0..17.5).
    private String planTo;
    // Assignee typed.
    private String assignee;
    // Reviewer typed.
    private String reviewer;
    // ID typed on the delete screen.
    private String id;

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

    // Returns the task type ID.
    public String getTaskTypeId() {
        return taskTypeId;
    }

    // Sets the task type ID.
    public void setTaskTypeId(String taskTypeId) {
        this.taskTypeId = taskTypeId;
    }

    // Returns the date.
    public String getDate() {
        return date;
    }

    // Sets the date.
    public void setDate(String date) {
        this.date = date;
    }

    // Returns the plan from time.
    public String getPlanFrom() {
        return planFrom;
    }

    // Sets the plan from time.
    public void setPlanFrom(String planFrom) {
        this.planFrom = planFrom;
    }

    // Returns the plan to time.
    public String getPlanTo() {
        return planTo;
    }

    // Sets the plan to time.
    public void setPlanTo(String planTo) {
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
    public String getId() {
        return id;
    }

    // Sets the ID to delete.
    public void setId(String id) {
        this.id = id;
    }
}
