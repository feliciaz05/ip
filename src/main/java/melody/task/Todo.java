package melody.task;

/**
 * Represents a todo that inherits from task, with a description.
 *
 */

public class Todo extends Task {

    public Todo(String description) {
        super(description, TaskType.TODO);
    }

    @Override
    public String toString() {
        return super.toString();
    }

}