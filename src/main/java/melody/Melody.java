package melody;

import melody.command.CommandType;
import melody.exception.MelodyException;
import melody.parser.Parser;
import melody.storage.Storage;
import melody.task.*;
import melody.ui.Ui;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class
 * Provides methods
 */

public class Melody {

    private static TaskList tasks = new TaskList();
    private static Storage storage = new Storage("./data/melody.Melody.txt");
    private static Ui ui = new Ui();
    private String commandType;

    public static void main(String[] args) {
        ui.showWelcome();

        try {
            ArrayList<Task> loadedTasks = storage.loadTasks();
            tasks = new TaskList(loadedTasks);
            String wordy = tasks.size() == 1 ? " task" : " tasks" ;
            ui.showMessage("Loaded " + tasks.size() + wordy + " from storage.");
        } catch (Exception e) {
            ui.showLoadingError(e.getMessage());
            tasks = new TaskList();
        }

        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            try {
                input = ui.readCommand();
                handleCommand(input);
            } catch (MelodyException e) {
                ui.showError(e.getMessage());
            } catch (Exception e) {
                ui.showError("  ☹ Oops! Something went wrong: " + e.getMessage());
            }
        }
    }

    public String getResponse(String input) {
        try {
            CommandType cmdType = Parser.parseCommand(input);
            commandType = cmdType.toString(); // Store the command type

            // Handle the command and return appropriate response
            switch (cmdType) {
                case BYE:
                    return "byeee~ cya next time!";

                case LIST:
                    return tasks.getTasksAsString();

                case MARK:
                    int markTaskNumber = Parser.parseTaskNumber(input);
                    tasks.markTask(markTaskNumber, true);
                    Task markedTask = tasks.getTask(markTaskNumber);
                    saveTasksToFile();
                    return "okie! good jobbb! i've marked this task as done:\n  " + markedTask.toString();

                case UNMARK:
                    int unmarkTaskNumber = Parser.parseTaskNumber(input);
                    tasks.markTask(unmarkTaskNumber, false);
                    Task unmarkedTask = tasks.getTask(unmarkTaskNumber);
                    saveTasksToFile();
                    return "okk, i've marked this task as not done yet:\n  " + unmarkedTask.toString();

                case TODO:
                    String todoDesc = Parser.parseTodo(input);
                    Todo newTodo = new Todo(todoDesc);
                    tasks.addTask(newTodo);
                    saveTasksToFile();
                    return "alrighty i've added this task:\n  " + newTodo.toString() +
                            "\nNow you have " + tasks.size() + " tasks in the list hehe";

                case DEADLINE:
                    String[] deadlineParts = Parser.parseDeadline(input);
                    Deadline newDeadline = new Deadline(deadlineParts[0], deadlineParts[1]);
                    tasks.addTask(newDeadline);
                    saveTasksToFile();
                    return "alrighty i've added this task:\n  " + newDeadline.toString() +
                            "\nNow you have " + tasks.size() + " tasks in the list hehe";

                case EVENT:
                    String[] eventParts = Parser.parseEvent(input);
                    Event newEvent = new Event(eventParts[0], eventParts[1], eventParts[2]);
                    tasks.addTask(newEvent);
                    saveTasksToFile();
                    return "alrighty i've added this task:\n  " + newEvent.toString() +
                            "\nNow you have " + tasks.size() + " tasks in the list hehe";

                case FIND:
                    String keyword = Parser.parseFind(input);
                    ArrayList<Task> tasksFound = tasks.findTasks(keyword);
                    if (tasksFound.isEmpty()) {
                        return "No matching tasks found.";
                    } else {
                        StringBuilder result = new StringBuilder("here are the matching tasks in your list:\n");
                        for (int i = 0; i < tasksFound.size(); i++) {
                            result.append((i + 1)).append(". ").append(tasksFound.get(i).toString()).append("\n");
                        }
                        return result.toString();
                    }

                case DELETE:
                    int deleteTaskNumber = Parser.parseTaskNumber(input);
                    Task removedTask = tasks.removeTask(deleteTaskNumber);
                    saveTasksToFile();
                    return "got it! i've removed this task:\n  " + removedTask.toString() +
                            "\nNow you have " + tasks.size() + " tasks in the list.";

                case UPDATE:
                    String[] updateArgs = Parser.parseUpdate(input);
                    int taskNumber = Integer.parseInt(updateArgs[0]);
                    String field = updateArgs[1];
                    String newValue = updateArgs[2];
                    String result = tasks.updateTask(taskNumber, field, newValue);
                    saveTasksToFile();
                    return result;

                default:
                    return "I don't understand that command :(";
            }
        } catch (MelodyException e) {
            return "Error: " + e.getMessage();
        }
    }

    private static void handleCommand(String input) throws MelodyException {
        CommandType commandType = Parser.parseCommand(input);

        switch (commandType) {
            case BYE:
                ui.showGoodbye();
                System.exit(0);
                break;

            case LIST:
                ui.showTaskList(tasks.getTasksAsString());
                break;

            case MARK:
                int markTaskNumber = Parser.parseTaskNumber(input);
                markTask(markTaskNumber, true);
                break;

            case UNMARK:
                int unmarkTaskNumber = Parser.parseTaskNumber(input);
                markTask(unmarkTaskNumber, false);
                break;

            case TODO:
                String todoDesc = Parser.parseTodo(input);
                addTodo(todoDesc);
                break;

            case DEADLINE:
                String[] deadlineParts = Parser.parseDeadline(input);
                addDeadline(deadlineParts[0], deadlineParts[1]);
                break;

            case EVENT:
                String[] eventParts = Parser.parseEvent(input);
                addEvent(eventParts[0], eventParts[1], eventParts[2]);
                break;

            case FIND:
                String keyword = Parser.parseFind(input);
                findTasks(keyword);
                break;

            case DELETE:
                int deleteTaskNumber = Parser.parseTaskNumber(input);
                Task removedTask = tasks.removeTask(deleteTaskNumber);
                ui.showTaskRemoved(removedTask, tasks.size());
                saveTasksToFile();
                break;

            case UPDATE:
                try {
                    String[] updateArgs = Parser.parseUpdate(input);
                    int taskNumber = Integer.parseInt(updateArgs[0]);
                    String field = updateArgs[1];
                    String newValue = updateArgs[2];

                    String result = tasks.updateTask(taskNumber, field, newValue);
                    ui.showMessage(result);
                } catch (MelodyException e) {
                    ui.showError(e.getMessage());
                }
                break;
        }
    }

    private static void markTask(int taskNumber, boolean isDone) throws MelodyException {
        tasks.markTask(taskNumber, isDone);
        Task task = tasks.getTask(taskNumber);
        ui.showTaskMarked(task, isDone);
        saveTasksToFile();
    }

    private static void addDeadline(String description, String date) {
        Deadline newDeadline = new Deadline(description, date);
        tasks.addTask(newDeadline);
        ui.showTaskAdded(newDeadline, tasks.size());
        saveTasksToFile();
    }

    private static void addTodo(String description) {
        Todo newTodo = new Todo(description);
        tasks.addTask(newTodo);
        ui.showTaskAdded(newTodo, tasks.size());
        saveTasksToFile();
    }

    private static void addEvent(String description, String from, String to) {
        Event newEvent = new Event(description, from, to);
        tasks.addTask(newEvent);
        ui.showTaskAdded(newEvent, tasks.size());
        saveTasksToFile();
    }

    private static void saveTasksToFile() {
        try {
            storage.saveTasks(tasks.getTasks());
        } catch (Exception e) {
            System.out.println("  Warning: Could not save tasks to file: " + e.getMessage());
        }
    }

    private static void findTasks(String keyword) {
        ArrayList<Task> tasksFound = tasks.findTasks(keyword);
        ui.showTasksFound(tasksFound);
    }

    public String getCommandType() {
        return commandType;
    }

}