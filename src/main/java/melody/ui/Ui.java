package melody.ui;

import melody.task.Task;

import java.util.Scanner;

/**
 * Handles all user interface interactions for the melody.Melody chatbot.
 */
public class Ui {
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message with melody.Melody's logo.
     */
    public void showWelcome() {
        String logo = " __  __      _           _       \n"
                + "|  \\/  | ___| | ___   __| |_   _ \n"
                + "| |\\/| |/ _ \\ |/ _ \\ / _` | | | |\n"
                + "| |  | |  __/ | (_) | (_| | |_| |\n"
                + "|_|  |_|\\___|_|\\___/ \\__,_|\\__, |\n"
                + "                          |___/ \n";
        System.out.println("Hello! I'm\n" + logo + "\n" + "What can I do for you?\n" + " ______");
    }

    /**
     * Reads a command from the user.
     * @return the user's input string
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays a formatted message with proper indentation.
     * @param message the message to display
     */
    public void showMessage(String message) {
        System.out.println("  " + message);
    }

    /**
     * Displays a formatted message with a divider line.
     * @param message the message to display
     */
    public void showMessageWithDivider(String message) {
        showMessage(message);
        showDivider();
    }

    /**
     * Displays a divider line.
     */
    public void showDivider() {
        System.out.println("______");
    }

    /**
     * Displays an error message.
     * @param errorMessage the error message to display
     */
    public void showError(String errorMessage) {
        System.out.println("  ☹ Oops! " + errorMessage);
        showDivider();
    }

    /**
     * Displays the goodbye message.
     */
    public void showGoodbye() {
        showMessage("  Toodles! See you next time~");
        showDivider();
    }

    /**
     * Displays a loading error message.
     * @param message the error message
     */
    public void showLoadingError(String message) {
        showMessage("  No existing data found or error loading data: " + message);
        showMessage("  Starting with empty task list.");
    }

    /**
     * Displays a task added confirmation.
     * @param task the task that was added
     * @param totalTasks the total number of tasks
     */
    public void showTaskAdded(Task task, int totalTasks) {
        showMessage("  Got it! I've added this task:");
        showMessage("  " + task.toString());
        String wordy = totalTasks == 1 ? " task" : " tasks" ;
        showMessage("  Now you have " + totalTasks + wordy + " in the list.");
        showDivider();
    }

    /**
     * Displays a task removed confirmation.
     * @param task the task that was removed
     * @param totalTasks the total number of tasks remaining
     */
    public void showTaskRemoved(Task task, int totalTasks) {
        showMessage("  Noted. I've removed this task:");
        showMessage("  " + task.toString());
        String wordy = totalTasks == 1 ? " task" : " tasks" ;
        showMessage("  Now you have " + totalTasks + wordy + " in the list.");
        showDivider();
    }

    /**
     * Displays a task marking confirmation.
     * @param task the task that was marked
     * @param isDone whether the task was marked as done
     */
    public void showTaskMarked(Task task, boolean isDone) {
        showMessage(isDone ? "  Okie! I've marked it as completed!" : "Alright! It's unmarked!");
        showMessage("  [" + task.getStatusIcon() + "] " + task.getDescription());
        showDivider();
    }

    /**
     * Displays a list of tasks.
     * @param tasksString the formatted string of tasks
     */
    public void showTaskList(String tasksString) {
        if (tasksString.isEmpty()) {
            showMessage("  You have no tasks in your list!");
        } else {
            System.out.println(tasksString);
        }
        showDivider();
    }

    /**
     * Displays a save error message.
     * @param errorMessage the error message
     */
    public void showSaveError(String errorMessage) {
        showMessage("  Warning: Could not save tasks to file: " + errorMessage);
    }

    /**
     * Closes the scanner resource.
     */
    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }
}