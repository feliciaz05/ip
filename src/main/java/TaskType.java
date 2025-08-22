// TaskType.java
public enum TaskType {
    TODO("T", "To-do task"),
    DEADLINE("D", "Task with deadline"),
    EVENT("E", "Event with start/end time");

    private final String code;
    private final String description;

    TaskType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}