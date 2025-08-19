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

    private static ArrayList<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        String logo = " __  __      _           _       \n"
                + "|  \\/  | ___| | ___   __| |_   _ \n"
                + "| |\\/| |/ _ \\ |/ _ \\ / _` | | | |\n"
                + "| |  | |  __/ | (_) | (_| | |_| |\n"
                + "|_|  |_|\\___|_|\\___/ \\__,_|\\__, |\n"
                + "                          |___/ \n";
        String exitLine = "Toodles! See you next time~";
        System.out.println("Hello! I'm\n" + logo + "\n" + "What can I do for you?\n" + " ______");

        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            input = scanner.nextLine();

            if (input.equals("bye")) {
                System.out.println("  " + exitLine);
                System.out.println("______");
                break;
            } else if (input.equals("list")) {
                listTasks();
            } else if (input.contains("unmark ")) {
                int taskNumber = Integer.parseInt(input.substring(7).trim());
                markTask(taskNumber, false);
            } else if (input.contains("mark ")) {
                int taskNumber = Integer.parseInt(input.substring(5).trim());
                markTask(taskNumber, true); //whatever is at position 5 is the task no.
            } else if (input.contains("deadline")) {
                int byIndex = input.indexOf(" /by ");
                String ddl = input.substring(byIndex + 4); //gets Sunday
                String desc = input.substring(9, byIndex).trim();
                addDeadline(desc, ddl);
            } else if (input.contains("todo ")) {
                String desc = input.substring(5);
                addTodo(desc);
            } else if (input.contains("event ")) {
                int fromIndex = input.indexOf(" /from ");
                int toIndex = input.indexOf(" /to ");
                String fromTime = input.substring((fromIndex + 6), toIndex);
                String toTime = input.substring(toIndex + 4);
                String desc = input.substring(6, fromIndex).trim();
                addEvent(desc, fromTime, toTime);
            } else {
                addTask(input);
            }

        }

        scanner.close();

    }

    private static void addTask(String desc) {
        Task newTask = new Task(desc);
        tasks.add(newTask);
        System.out.println("  added: " + desc);
        System.out.println("______");
    }

    private static void listTasks() {
        System.out.println("Here are the tasks in your list: \n");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            System.out.println((i + 1) + ". [" + task.getType() + "] " + "[" + task.getStatusIcon() + "] " + task.description);
        }
        System.out.println("______");
    }

    private static void markTask(int taskNumber, boolean isDone) {
        System.out.println(isDone ? "  Okie! I've marked it as completed!" : "  Alright! It's unmarked!");
        Task task = tasks.get(taskNumber - 1);
        task.isDone = isDone;
        System.out.println("    [" + task.getStatusIcon() + "] " + task.description);
        System.out.println("______");
    }

    private static void addDeadline(String description, String date) {
        Deadline newDeadline = new Deadline(description, date);
        tasks.add(newDeadline);
        System.out.println("Got it! I've added this task: ");
        System.out.println("[D] [ ] " + description + " (by:" + date + ")");
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println("______");

    }

    private static void addTodo(String description) {
        Todo newTodo = new Todo(description);
        tasks.add(newTodo);
        System.out.println("Got it! I've added this task: ");
        System.out.println("[T] [ ] " + description);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println("______");

    }

    private static void addEvent(String description, String from, String to) {
        Event newEvent = new Event(description, from, to);
        tasks.add(newEvent);
        System.out.println("Got it! I've added this task: ");
        System.out.println("[E] [ ] " + description + " (from:" + from + " to:" + to + ")");
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println("______");

    }

}