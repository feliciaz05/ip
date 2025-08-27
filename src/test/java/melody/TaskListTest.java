package melody;

import melody.task.TaskList;
import melody.task.Todo;
import melody.task.Task;
import melody.exception.MelodyException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TaskListTest {

    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
    }

    @Test
    public void testAddAndGetTask() throws MelodyException {
        Todo todo = new Todo("Test task");
        taskList.addTask(todo);
        Task task = taskList.getTask(1); // 1-based indexing
        assertEquals("Test task", task.getDescription());
        assertFalse(task.isDone());
    }

    @Test
    public void testMarkTask() throws MelodyException {
        // Add a task
        Todo todo = new Todo("Test task");
        taskList.addTask(todo);
        int taskIndex = 1; // 1-based indexing

        // Test initial state
        assertFalse(taskList.getTask(taskIndex).isDone());

        // Test marking as done
        taskList.markTask(taskIndex, true);
        assertTrue(taskList.getTask(taskIndex).isDone());

        // Test marking as not done
        taskList.markTask(taskIndex, false);
        assertFalse(taskList.getTask(taskIndex).isDone());
    }

    @Test
    public void testMarkTask_invalidIndex_throwsException() {
        // Test with index 0 (should be invalid as indexing starts from 1)
        assertThrows(MelodyException.class, () -> taskList.markTask(0, true));

        // Test with index greater than list size
        assertThrows(MelodyException.class, () -> taskList.markTask(100, true));

        // Test with negative index
        assertThrows(MelodyException.class, () -> taskList.markTask(-1, true));
    }

    @Test
    public void testRemoveTask() throws MelodyException {
        // Add multiple tasks
        Todo todo1 = new Todo("Task 1");
        Todo todo2 = new Todo("Task 2");
        taskList.addTask(todo1);
        taskList.addTask(todo2);
        int initialSize = taskList.size();

        // Remove first task
        Task removedTask = taskList.removeTask(1);
        assertEquals(initialSize - 1, taskList.size());
        assertEquals("Task 1", removedTask.getDescription());

        // Verify the remaining task is Task 2
        assertEquals("Task 2", taskList.getTask(1).getDescription());
    }

    @Test
    public void testRemoveTask_invalidIndex_throwsException() {
        assertThrows(MelodyException.class, () -> taskList.removeTask(1));
        assertThrows(MelodyException.class, () -> taskList.removeTask(0));
        assertThrows(MelodyException.class, () -> taskList.removeTask(-1));
    }

    @Test
    public void testSize() throws MelodyException {
        assertEquals(0, taskList.size());

        Todo todo1 = new Todo("Task 1");
        taskList.addTask(todo1);
        assertEquals(1, taskList.size());

        Todo todo2 = new Todo("Task 2");
        taskList.addTask(todo2);
        assertEquals(2, taskList.size());

        taskList.removeTask(1);
        assertEquals(1, taskList.size());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(taskList.isEmpty());

        Todo todo = new Todo("Task");
        taskList.addTask(todo);
        assertFalse(taskList.isEmpty());

        taskList.clear();
        assertTrue(taskList.isEmpty());
    }

    @Test
    public void testGetAllTasks() {
        Todo todo1 = new Todo("Task 1");
        Todo todo2 = new Todo("Task 2");
        taskList.addTask(todo1);
        taskList.addTask(todo2);

        assertEquals(2, taskList.getAllTasks().size());
        assertEquals("Task 1", taskList.getAllTasks().get(0).getDescription());
        assertEquals("Task 2", taskList.getAllTasks().get(1).getDescription());
    }

    @Test
    public void testClear() {
        Todo todo1 = new Todo("Task 1");
        Todo todo2 = new Todo("Task 2");
        taskList.addTask(todo1);
        taskList.addTask(todo2);

        assertEquals(2, taskList.size());
        taskList.clear();
        assertEquals(0, taskList.size());
        assertTrue(taskList.isEmpty());
    }

    @Test
    public void testGetTasksAsString_emptyList() {
        String result = taskList.getTasksAsString();
        assertTrue(result.contains("You have no tasks"));
    }

    @Test
    public void testGetTasksAsString_withTasks() throws MelodyException {
        Todo todo = new Todo("Test task");
        taskList.addTask(todo);

        String result = taskList.getTasksAsString();
        assertTrue(result.contains("Test task"));
        assertTrue(result.contains("1."));
    }
}