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
        return (isDone ? "X" : " "); // mark done task with X
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