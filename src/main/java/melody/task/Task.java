package melody.task;

/**
 * Represents a task with a description, completion status and type.
 * Provides methods to manage task state and getters and setters.
 */

public abstract class Task {
    private String description;
    private boolean isDone;
    private TaskType type;

    /**
     * Creates a new task with the given description and type
     *
     * @param description
     * @param type
     */
    public Task(String description, TaskType type) {
        assert description != null : "Task description cannot be null";
        assert !description.trim().isEmpty() : "Task description cannot be empty";
        assert type != null : "Task type cannot be null";

        this.description = description;
        this.type = type;
        this.isDone = false;
    }

    /**
     * Marks task as done and updates completion status.
     *
     * @return X if the task was successfully marked as done, otherwise
     */
    public String getStatusIcon() {
        String icon = (isDone ? "X" : " "); // mark done task with X
        assert icon != null : "Status icon should not be null";
        assert icon.length() == 1 : "Status icon should be exactly 1 character";
        assert (isDone && icon.equals("X")) || (!isDone && icon.equals(" ")) :
                "Status icon should match done state";
        return icon;
    }

    /**
     * Sets the completion status of a task.
     *
     * @param isDone
     */
    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    public boolean isDone() {
        return isDone;
    }

    public TaskType getType() {
        return this.type;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "[" + type.getCode() + "]" + "[" + getStatusIcon() + "] " + description;
    }

}