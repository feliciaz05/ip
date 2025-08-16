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

import java.util.Scanner;

public class Melody {

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
            }

            System.out.println("  " + input);
            System.out.println("______");
        }

        scanner.close();

    }
}