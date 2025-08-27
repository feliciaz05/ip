package melody;

import melody.task.Todo;
import melody.task.TaskType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TodoTest {

    private Todo todo;

    @BeforeEach
    public void setUp() {
        todo = new Todo("Buy groceries");
    }

    @Test
    public void testToString() {
        // Test initial state (not done)
        assertEquals("[T][ ] Buy groceries", todo.toString());

        // Test after marking as done
        todo.setDone(true);
        assertEquals("[T][X] Buy groceries", todo.toString());

        // Test after marking as not done
        todo.setDone(false);
        assertEquals("[T][ ] Buy groceries", todo.toString());
    }

    @Test
    public void testSetDone() {
        // Test initial state
        assertFalse(todo.isDone());

        // Test setting to done
        todo.setDone(true);
        assertTrue(todo.isDone());

        // Test setting back to not done
        todo.setDone(false);
        assertFalse(todo.isDone());
    }

    @Test
    public void testGetDescription() {
        assertEquals("Buy groceries", todo.getDescription());

        // Test with different description
        Todo anotherTodo = new Todo("Read book");
        assertEquals("Read book", anotherTodo.getDescription());
    }

    @Test
    public void testGetStatusIcon() {
        // Test initial state
        assertEquals(" ", todo.getStatusIcon());

        // Test when done
        todo.setDone(true);
        assertEquals("X", todo.getStatusIcon());

        // Test when not done
        todo.setDone(false);
        assertEquals(" ", todo.getStatusIcon());
    }

    @Test
    public void testTaskType() {
        assertEquals(TaskType.TODO, todo.getType());
    }

    @Test
    public void testConstructor() {
        Todo testTodo = new Todo("Test description");
        assertEquals("Test description", testTodo.getDescription());
        assertFalse(testTodo.isDone());
        assertEquals(TaskType.TODO, testTodo.getType());
    }
}