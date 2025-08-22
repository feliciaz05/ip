public abstract class Task {
    protected String description;
    protected boolean isDone;
    protected TaskType type;

    public Task(String description, TaskType type) {
        this.description = description;
        this.type = type;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public TaskType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "[" + type.getCode() + "] " + "[" + getStatusIcon() + "] " + description;
    }
    //...
}