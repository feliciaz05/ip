/*public class Duke {
    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
    }
}*/

import java.util.ArrayList;
import java.util.Scanner;

public class Melody {

    private static TaskList tasks = new TaskList();
    private static Storage storage = new Storage("./data/Melody.txt");

    public static void main(String[] args) {
        String logo = " __  __      _           _       \n"
                + "|  \\/  | ___| | ___   __| |_   _ \n"
                + "| |\\/| |/ _ \\ |/ _ \\ / _` | | | |\n"
                + "| |  | |  __/ | (_) | (_| | |_| |\n"
                + "|_|  |_|\\___|_|\\___/ \\__,_|\\__, |\n"
                + "                          |___/ \n";
        System.out.println("Hello! I'm\n" + logo + "\n" + "What can I do for you?\n" + " ______");

        try {
            ArrayList<Task> loadedTasks = storage.loadTasks();
            tasks = new TaskList(loadedTasks);
            System.out.println("Loaded " + tasks.size() + " tasks from storage.");
        } catch (Exception e) {
            System.out.println("No existing data found or error loading data. Starting with empty task list.");
            tasks = new TaskList();
        }

        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {

            try {
                input = scanner.nextLine();
                handleCommand(input);

            } catch (MelodyException e) {
                System.out.println(e.getMessage());
                System.out.println("______");
            } catch (Exception e) {
                System.out.println("  ☹ Oops! Something went wrong: " + e.getMessage());
                System.out.println("______");
            }
        }
    }

    private static void handleCommand(String input) throws MelodyException {
        if (input.equals("bye")) {
            System.out.println("  " + "Toodles! See you next time~");
            System.out.println("______");
            System.exit(0);
        } else if (input.equals("list")) {
            listTasks();
        } else if (input.startsWith("unmark ")) {
            handleMarkCommand(input, false);
        } else if (input.startsWith("mark ")) {
            handleMarkCommand(input, true);
        } else if (input.startsWith("deadline ")) {
            handleDeadline(input);
        } else if (input.startsWith("todo ")) {
            handleTodo(input);
        } else if (input.startsWith("event ")) {
            handleEvent(input);
        } else if (input.startsWith("delete ")) {
            handleDeleteCommand(input);
        } else {
            throw new MelodyException("I don't understand that command. Try: todo, deadline, event, list, mark, unmark, delete, or bye!");
        }
    }

    private static void handleMarkCommand(String input, boolean isDone) throws MelodyException {
        try {
            String numberStr = input.split(" ")[1];
            int taskNumber = Integer.parseInt(numberStr);
            markTask(taskNumber, isDone);
        } catch (Exception e) {
            throw new MelodyException("Please enter a valid task number after '" + (isDone ? "mark" : "unmark") + "'");
        }
    }

    private static void handleDeadline(String input) throws MelodyException {
        try {
            if (input.equals("deadline")) {
                throw new MelodyException("A deadline needs both a description and time. Try: 'deadline <task> /by <time>'");
            }
            int byIndex = input.indexOf(" /by ");
            if (byIndex == -1) {
                throw new MelodyException("Missing '/by' in deadline. Try: 'deadline <task> /by <time>'");
            }
            String ddl = input.substring(byIndex + 5).trim();
            String desc = input.substring(9, byIndex).trim();
            if (desc.isEmpty()) {
                throw new MelodyException("The description of a deadline cannot be empty.f");
            }
            addDeadline(desc, ddl);
        } catch (StringIndexOutOfBoundsException e) {
            throw new MelodyException("Invalid deadline format. Try: 'deadline <task> /by <time>'");
        }
    }

    private static void handleTodo(String input) throws MelodyException {
        String desc = input.substring(5).trim();
        if (desc.isEmpty()) {
            throw new MelodyException("The description of a todo cannot be empty. Try: 'todo <task>'");
        }
        addTodo(desc);
    }

    private static void handleEvent(String input) throws MelodyException {
        try {
            if (input.equals("event")) {
                throw new MelodyException("An event needs description, start and end times. Try: 'event <task> /from <start> /to <end>'");
            }
            int fromIndex = input.indexOf(" /from ");
            int toIndex = input.indexOf(" /to ");
            if (fromIndex == -1 || toIndex == -1) {
                throw new MelodyException("Missing '/from' or '/to' in event. Try: 'event <task> /from <start> /to <end>'");
            }
            String fromTime = input.substring(fromIndex + 7, toIndex).trim();
            String toTime = input.substring(toIndex + 5).trim();
            String desc = input.substring(6, fromIndex).trim();
            if (desc.isEmpty()) {
                throw new MelodyException("The description of an event cannot be empty");
            }
            addEvent(desc, fromTime, toTime);
        } catch (StringIndexOutOfBoundsException e) {
            throw new MelodyException("Invalid event format. Try: 'event <task> /from <start> /to <end>'");
        }
    }

    private static void handleDeleteCommand(String input) throws MelodyException {
        try {
            String numberStr = input.split(" ")[1];
            int taskNumber = Integer.parseInt(numberStr);

            Task removedTask = tasks.removeTask(taskNumber);
            System.out.println("  Noted. I've removed this task:");

            // This will now use the proper toString() format
            System.out.println("    " + removedTask.toString());

            System.out.println("  Now you have " + tasks.size() + " tasks in the list.");
            System.out.println("______");
            saveTasksToFile();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new MelodyException("Please specify a task number to delete.");
        } catch (NumberFormatException e) {
            throw new MelodyException("Please enter a valid task number.");
        }
    }

    private static void listTasks() {
        System.out.println(tasks.getTasksAsString());
    }

    private static void markTask(int taskNumber, boolean isDone) throws MelodyException {
        System.out.println("DEBUG: Marking task " + taskNumber);
        tasks.markTask(taskNumber, isDone);
        Task task = tasks.getTask(taskNumber);
        System.out.println(isDone ? "  Okie! I've marked it as completed!" : "  Alright! It's unmarked!");
        System.out.println("    [" + task.getStatusIcon() + "] " + task.description);
        System.out.println("______");
        saveTasksToFile();
    }

    private static void addDeadline(String description, String date) {
        Deadline newDeadline = new Deadline(description, date);
        tasks.addTask(newDeadline);
        System.out.println("  Got it! I've added this task: ");
        System.out.println("    " + newDeadline.toString());
        System.out.println("  Now you have " + tasks.size() + " tasks in the list.");
        System.out.println("______");
        saveTasksToFile();

    }

    private static void addTodo(String description) {
        Todo newTodo = new Todo(description);
        tasks.addTask(newTodo);
        System.out.println("  Got it! I've added this task: ");
        System.out.println("    " + newTodo.toString());
        System.out.println("  Now you have " + tasks.size() + " tasks in the list.");
        System.out.println("______");
        saveTasksToFile();

    }

    private static void addEvent(String description, String from, String to) {
        Event newEvent = new Event(description, from, to);
        tasks.addTask(newEvent);
        System.out.println("  Got it! I've added this task: ");
        System.out.println("    " + newEvent.toString());
        System.out.println("  Now you have " + tasks.size() + " tasks in the list.");
        System.out.println("______");
        saveTasksToFile();

    }

    private static void saveTasksToFile() {
        try {
            storage.saveTasks(tasks.getTasks());
        } catch (Exception e) {
            System.out.println("  Warning: Could not save tasks to file: " + e.getMessage());
        }
    }

}