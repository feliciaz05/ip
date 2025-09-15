package melody;

import melody.task.TaskType;
import melody.task.Todo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    public void testTaskCreation() {
        // Test with a simple task (using Todo as concrete implementation)
        Todo task = new Todo("Test task");

        assertEquals("Test task", task.getDescription());
        assertEquals(TaskType.TODO, task.getType());
        assertFalse(task.isDone());
    }

    @Test
    public void testTaskStatusIcon() {
        Todo task = new Todo("Test task");

        // Initially should be empty space
        assertEquals(" ", task.getStatusIcon());

        // After marking as done, should be "X"
        task.setDone(true);
        assertEquals("X", task.getStatusIcon());

        // After marking as not done, should be space again
        task.setDone(false);
        assertEquals(" ", task.getStatusIcon());
    }

    @Test
    public void testTaskToString() {
        Todo task = new Todo("Test task");

        // Should contain type code and status icon
        String result = task.toString();
        assertTrue(result.contains("[T]"));
        assertTrue(result.contains("[ ]"));
        assertTrue(result.contains("Test task"));

        // After marking as done
        task.setDone(true);
        result = task.toString();
        assertTrue(result.contains("[T]"));
        assertTrue(result.contains("[X]"));
        assertTrue(result.contains("Test task"));
    }

    @Test
    public void testTaskDescriptionUpdate() {
        Todo task = new Todo("Original description");

        assertEquals("Original description", task.getDescription());

        task.setDescription("Updated description");
        assertEquals("Updated description", task.getDescription());
    }

    @Test
    public void testTaskAssertions() {
        // Test that assertions work for invalid inputs
        assertThrows(AssertionError.class, () -> {
            new Todo(null);
        });

        assertThrows(AssertionError.class, () -> {
            new Todo("");
        });

        assertThrows(AssertionError.class, () -> {
            new Todo("   ");
        });
    }
}