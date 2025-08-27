package melody.task;
import melody.exception.MelodyException;

/**
 * Represents a todo that inherits from task, with a description.
 *
 */

/**
 * Represents a todo that inherits from task, with a description.
 *
 */

public class Todo extends Task {

    public Todo(String description) {
        super(description, TaskType.TODO);
    }

    @Override
    public String getAvailableUpdateFields() {
        return "Available fields for todos: /description";
    }

    @Override
    public String updateField(String field, String newValue) throws MelodyException {
        if ("description".equals(field)) {
            this.setDescription(newValue);
            return "Changed description to: " + newValue;
        } else {
            throw new MelodyException("Cannot update field '" + field +
                    "' for a todo.\n" + getAvailableUpdateFields());
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

}