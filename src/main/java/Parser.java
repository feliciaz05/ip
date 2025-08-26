public class Parser {

    /**
     * Parses the user input and returns the corresponding command type.
     *
     * @param input The user input string
     * @return The command type
     * @throws MelodyException If the command is not recognized
     */
    public static CommandType parseCommand(String input) throws MelodyException {
        if (input.equals("bye")) {
            return CommandType.BYE;
        } else if (input.equals("list")) {
            return CommandType.LIST;
        } else if (input.startsWith("unmark ")) {
            return CommandType.UNMARK;
        } else if (input.startsWith("mark ")) {
            return CommandType.MARK;
        } else if (input.startsWith("deadline ")) {
            return CommandType.DEADLINE;
        } else if (input.startsWith("todo ")) {
            return CommandType.TODO;
        } else if (input.startsWith("event ")) {
            return CommandType.EVENT;
        } else if (input.startsWith("delete ")) {
            return CommandType.DELETE;
        } else {
            throw new MelodyException("I don't understand that command. Try: todo, deadline, event, list, mark, unmark, delete, or bye!");
        }
    }

    /**
     * Extracts the task number from mark/unmark/delete commands.
     *
     * @param input The user input string
     * @return The task number (1-indexed)
     * @throws MelodyException If the task number is invalid
     */
    public static int parseTaskNumber(String input) throws MelodyException {
        try {
            String[] parts = input.split(" ", 2);
            if (parts.length < 2) {
                throw new MelodyException("Please specify a task number.");
            }
            return Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            throw new MelodyException("Please enter a valid task number.");
        }
    }

    /**
     * Parses a todo command and extracts the description.
     *
     * @param input The user input string
     * @return The todo description
     * @throws MelodyException If the description is empty
     */
    public static String parseTodo(String input) throws MelodyException {
        if (input.length() <= 5) {
            throw new MelodyException("The description of a todo cannot be empty. Try: 'todo <task>'");
        }
        String desc = input.substring(5).trim();
        if (desc.isEmpty()) {
            throw new MelodyException("The description of a todo cannot be empty. Try: 'todo <task>'");
        }
        return desc;
    }

    /**
     * Parses a deadline command and extracts description and due date.
     *
     * @param input The user input string
     * @return A String array containing [description, dueDate]
     * @throws MelodyException If the format is invalid
     */
    public static String[] parseDeadline(String input) throws MelodyException {
        if (input.equals("deadline")) {
            throw new MelodyException("A deadline needs both a description and time. Try: 'deadline <task> /by <time>'");
        }

        int byIndex = input.indexOf(" /by ");
        if (byIndex == -1) {
            throw new MelodyException("Missing '/by' in deadline. Try: 'deadline <task> /by <time>'");
        }

        try {
            String ddl = input.substring(byIndex + 5).trim();
            String desc = input.substring(9, byIndex).trim();

            if (desc.isEmpty()) {
                throw new MelodyException("The description of a deadline cannot be empty.");
            }
            if (ddl.isEmpty()) {
                throw new MelodyException("The due date of a deadline cannot be empty.");
            }

            return new String[]{desc, ddl};
        } catch (StringIndexOutOfBoundsException e) {
            throw new MelodyException("Invalid deadline format. Try: 'deadline <task> /by <time>'");
        }
    }

    /**
     * Parses an event command and extracts description, start time, and end time.
     *
     * @param input The user input string
     * @return A String array containing [description, fromTime, toTime]
     * @throws MelodyException If the format is invalid
     */
    public static String[] parseEvent(String input) throws MelodyException {
        if (input.equals("event")) {
            throw new MelodyException("An event needs description, start and end times. Try: 'event <task> /from <start> /to <end>'");
        }

        int fromIndex = input.indexOf(" /from ");
        int toIndex = input.indexOf(" /to ");

        if (fromIndex == -1 || toIndex == -1) {
            throw new MelodyException("Missing '/from' or '/to' in event. Try: 'event <task> /from <start> /to <end>'");
        }

        try {
            String fromTime = input.substring(fromIndex + 7, toIndex).trim();
            String toTime = input.substring(toIndex + 5).trim();
            String desc = input.substring(6, fromIndex).trim();

            if (desc.isEmpty()) {
                throw new MelodyException("The description of an event cannot be empty");
            }
            if (fromTime.isEmpty()) {
                throw new MelodyException("The start time of an event cannot be empty");
            }
            if (toTime.isEmpty()) {
                throw new MelodyException("The end time of an event cannot be empty");
            }

            return new String[]{desc, fromTime, toTime};
        } catch (StringIndexOutOfBoundsException e) {
            throw new MelodyException("Invalid event format. Try: 'event <task> /from <start> /to <end>'");
        }
    }

}