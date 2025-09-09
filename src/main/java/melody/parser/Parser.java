package melody.parser;

import melody.command.CommandType;
import melody.exception.MelodyException;

public class Parser {

    /**
     * Parses the user input and returns the corresponding command type.
     *
     * @param input The user input string
     * @return The command type
     * @throws MelodyException If the command is not recognized
     */
    public static CommandType parseCommand(String input) throws MelodyException {
        assert input != null : "Input cannot be null";

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
        } else if (input.startsWith("find ")) {
            return CommandType.FIND;
        } else if (input.startsWith("update ")) {
            return CommandType.UPDATE;
        } else {
            throw new MelodyException("I don't understand that command. Try: todo, deadline, event, list, mark, unmark, delete, update or bye!");
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
        assert input.startsWith("mark ") || input.startsWith("unmark ") || input.startsWith("delete ") :
                "Input must start with mark, unmark, or delete";

        try {
            String[] parts = input.split(" ", 2);
            if (parts.length < 2) {
                throw new MelodyException("Please specify a task number.");
            }
            int taskNumber = Integer.parseInt(parts[1]);
            assert taskNumber > 0 : "Task number must be positive";
            return taskNumber;
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
        assert input.startsWith("todo ") : "Input must start with 'todo '";

        if (input.length() <= 5) {
            throw new MelodyException("The description of a todo cannot be empty. Try: 'todo <task>'");
        }
        String desc = input.substring(5).trim();
        if (desc.isEmpty()) {
            throw new MelodyException("The description of a todo cannot be empty. Try: 'todo <task>'");
        }

        assert !desc.isEmpty() : "Parsed todo description should not be empty";
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
        assert input.startsWith("deadline") : "Input must start with 'deadline'";
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

            assert !desc.isEmpty() : "Deadline description should not be empty";
            assert !ddl.isEmpty() : "Deadline due date should not be empty";
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

        assert input.startsWith("event") : "Input must start with 'event'";
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

            assert !desc.isEmpty() : "Event description should not be empty";
            assert !fromTime.isEmpty() : "Event start time should not be empty";
            assert !toTime.isEmpty() : "Event end time should not be empty";
            assert fromIndex < toIndex : "/from should come before /to";
            return new String[]{desc, fromTime, toTime};
        } catch (StringIndexOutOfBoundsException e) {
            throw new MelodyException("Invalid event format. Try: 'event <task> /from <start> /to <end>'");
        }
    }

    public static String parseFind(String input) throws MelodyException {
        assert input.startsWith("find ") : "Input must start with 'find '";
        if (input.length() <= 5) { // "find ".length() = 5
            throw new MelodyException("Please provide a keyword to search for.");
        }
        String keyword = input.substring(5).trim();
        assert !keyword.isEmpty() : "Search keyword should not be empty";
        return keyword;
    }

    /**
     * Parses an update command and extracts the task number, field to update, and new value.
     * Format: update [task number] /[field] [new value]
     * Example: update 3 /from 14:00
     *
     * @param input The user input string
     * @return A String array containing [taskNumber, field, newValue]
     * @throws MelodyException If the format is invalid
     */
    public static String[] parseUpdate(String input) throws MelodyException {
        assert input.startsWith("update ") : "Input must start with 'update '";

        // Remove the "update " part and split the remaining string by spaces
        String args = input.substring(7).trim();
        if (args.isEmpty()) {
            throw new MelodyException("Please specify a task number, field, and new value.\n" +
                    "Format: update [number] /[field] [new value]\n" +
                    "Example: update 3 /from 14:00");
        }

        // Split into parts: [number, /field, value...]
        String[] parts = args.split("\\s+", 3); // Split into max 3 parts
        if (parts.length < 3) {
            throw new MelodyException("Invalid update format.\n" +
                    "Format: update [number] /[field] [new value]\n" +
                    "Example: update 3 /from 14:00");
        }

        try {
            // Validate task number
            int taskNumber = Integer.parseInt(parts[0]);
            if (taskNumber < 1) {
                throw new MelodyException("Task number must be a positive integer.");
            }

            // Validate field format (should start with '/')
            String field = parts[1];
            if (!field.startsWith("/")) {
                throw new MelodyException("Field must start with '/'.\n" +
                        "Available fields: /description, /from, /to, /by");
            }
            // Remove the leading '/' to get the field name
            String fieldName = field.substring(1);

            // The rest is the new value
            String newValue = parts[2].trim();
            if (newValue.isEmpty()) {
                throw new MelodyException("The new value cannot be empty.");
            }

            return new String[]{parts[0], fieldName, newValue};

        } catch (NumberFormatException e) {
            throw new MelodyException("Please provide a valid task number.");
        }
    }

}