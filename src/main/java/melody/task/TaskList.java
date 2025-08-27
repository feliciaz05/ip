package melody.task;

import melody.exception.MelodyException;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of tasks and provides operations to manipulate them.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs an empty melody.task.TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a melody.task.TaskList with existing tasks.
     *
     * @param tasks the initial list of tasks
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param task the task to add
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Removes a task from the list by index.
     *
     * @param index the index of the task to remove (1-based)
     * @return the removed task
     * @throws MelodyException if the index is invalid
     */
    public Task removeTask(int index) throws MelodyException {
        if (index < 1 || index > tasks.size()) {
            throw new MelodyException("melody.task.Task number " + index + " doesn't exist.");
        }
        return tasks.remove(index - 1);
    }

    /**
     * Marks a task as done or not done.
     *
     * @param index the index of the task to mark (1-based)
     * @param isDone true to mark as done, false to mark as not done
     * @throws MelodyException if the index is invalid
     */
    public void markTask(int index, boolean isDone) throws MelodyException {
        if (index < 1 || index > tasks.size()) {
            throw new MelodyException("melody.task.Task number " + index + " doesn't exist.");
        }
        Task task = tasks.get(index - 1);
        task.setDone(isDone);
    }

    /**
     * Gets a task by index.
     *
     * @param index the index of the task (1-based)
     * @return the task at the specified index
     * @throws MelodyException if the index is invalid
     */
    public Task getTask(int index) throws MelodyException {
        if (index < 1 || index > tasks.size()) {
            throw new MelodyException("melody.task.Task number " + index + " doesn't exist.");
        }
        return tasks.get(index - 1);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the size of the task list
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Checks if the task list is empty.
     *
     * @return true if the list is empty, false otherwise
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns all tasks as a list.
     *
     * @return a list of all tasks
     */
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks); // Return a copy to prevent external modification
    }

    /**
     * Returns the underlying ArrayList of tasks.
     * This is useful for storage operations.
     *
     * @return the ArrayList of tasks
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Clears all tasks from the list.
     */
    public void clear() {
        tasks.clear();
    }

    /**
     * Returns a string representation of all tasks for display.
     *
     * @return a formatted string listing all tasks
     */
    public String getTasksAsString() {
        if (tasks.isEmpty()) {
            return "  You have no tasks in your list!\n______";
        }

        StringBuilder sb = new StringBuilder("  Here are the tasks in your list: \n\n");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            sb.append((i + 1)).append(". ").append(task.toString()).append("\n");
        }
        sb.append("______");
        return sb.toString();
    }
}