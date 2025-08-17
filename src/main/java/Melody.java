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

    private static ArrayList<String> tasks = new ArrayList<>();

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
            } else {
                addTask(input);
            }

        }

        scanner.close();

    }

    private static void addTask(String task) {
        tasks.add(task);
        System.out.println("  added: " + task);
        System.out.println("______");
    }

    private static void listTasks() {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
        System.out.println("______");
    }
}