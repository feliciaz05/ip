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
import java.util.List;
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
            System.out.println((i + 1) + ". [" + task.getStatusIcon() + "] " + task.description);
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

    public static class Task {
        protected String description;
        protected boolean isDone;

        public Task(String description) {
            this.description = description;
            this.isDone = false;
        }

        public String getStatusIcon() {
            return (isDone ? "X" : " "); // mark done task with X
        }

            //...
    }

}