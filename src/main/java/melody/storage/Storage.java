package melody.storage;

import melody.task.Deadline;
import melody.task.Event;
import melody.task.Task;
import melody.task.Todo;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the file and returns them as an ArrayList
     * @return ArrayList of tasks loaded from file
     * @throws Exception if file cannot be read or data is corrupted
     */
    public ArrayList<Task> loadTasks() throws Exception {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        // If file doesn't exist, return empty list
        if (!file.exists()) {
            return tasks;
        }

        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue; // Skip empty lines
            }

            try {
                Task task = parseTaskFromLine(line);
                if (task != null) {
                    tasks.add(task);
                }
            } catch (Exception e) {
                // Skip corrupted lines but continue reading
                System.out.println("Warning: Skipping corrupted line: " + line);
            }
        }
        scanner.close();
        return tasks;
    }

    /**
     * Saves the task list to the file
     * @param tasks ArrayList of tasks to save
     * @throws Exception if file cannot be written
     */
    public void saveTasks(ArrayList<Task> tasks) throws Exception {
        // Create directory if it doesn't exist
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        FileWriter writer = new FileWriter(filePath);
        for (Task task : tasks) {
            writer.write(convertTaskToFileFormat(task) + "\n");
        }
        writer.close();
    }

    /**
     * Parses a line from the file and creates the corresponding melody.task.Task object
     * @param line Line from the file in format: T|1|description or D|0|description|date or E|1|description|from|to
     * @return melody.task.Task object or null if line is invalid
     */
    private Task parseTaskFromLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 3) {
            return null; // Invalid format
        }

        String taskType = parts[0].trim();
        boolean isDone = parts[1].trim().equals("1");

        Task task = null;

        switch (taskType) {
            case "T":
                if (parts.length >= 3) {
                    String description = parts[2].trim();
                    task = new Todo(description);
                }
                break;
            case "D":
                if (parts.length >= 4) {
                    String description = parts[2].trim();
                    String by = parts[3].trim();
                    task = new Deadline(description, by);
                }
                break;
            case "E":
                if (parts.length >= 5) {
                    String description = parts[2].trim();
                    String from = parts[3].trim();
                    String to = parts[4].trim();
                    task = new Event(description, from, to);
                }
                break;
            default:
                return null; // Unknown task type
        }
        if (task != null) {
            task.setDone(isDone);
        }
        return task;
    }

    /**
     * Converts a melody.task.Task object to the file format string
     * @param task melody.task.Task to convert
     * @return String in format: T|1|description or D|0|description|date or E|1|description|from|to
     */
    private String convertTaskToFileFormat(Task task) {
        String doneStatus = task.isDone() ? "1" : "0";

        if (task instanceof Todo) {
            return "T | " + doneStatus + " | " + task.getDescription();
        } else if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            return "D | " + doneStatus + " | " + task.getDescription() + " | " + deadline.getBy();
        } else if (task instanceof Event) {
            Event event = (Event) task;
            return "E | " + doneStatus + " | " + task.getDescription() + " | " + event.getStartTime() + "-" + event.getEndTime();
        }
        return ""; // Should not reach here
    }
}