package melody.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a deadline that inherits from task, with a due date/time and description.
 * Provides methods to format due date/time.
 */

public class Deadline extends Task {

    protected String by;
    protected LocalDateTime byDateTime;

    public Deadline(String description, String by) {
        super(description, TaskType.DEADLINE);

        assert by != null : "Deadline 'by' parameter cannot be null";
        assert !by.trim().isEmpty() : "Deadline 'by' parameter cannot be empty";
        assert description != null : "Description cannot be null";
        assert !description.trim().isEmpty() : "Description cannot be empty";

        this.by = by;
        this.byDateTime = parseDateTime(by);
    }

    public String getBy() {
        return by;
    }

    private LocalDateTime parseDateTime(String dateTimeString) {
        assert dateTimeString != null : "DateTime string cannot be null";
        assert !dateTimeString.trim().isEmpty() : "DateTime string cannot be empty";

        String[] formats = {
                "yyyy-MM-dd HHmm",    // 2019-12-01 1800 (most specific)
                "dd/MM/yyyy HHmm",    // 01/12/2019 1800
                "d/M/yyyy HHmm",      // 1/12/2019 1800
                "d-M-yyyy HHmm",      // 1-12-2019 1800
                "MM-dd-yyyy HHmm",    // 12-01-2019 1800
                "yyyy-MM-dd",         // 2019-12-01 → 2019-12-01 0000
                "dd/MM/yyyy",         // 01/12/2019 → 2019-12-01 0000
                "MM-dd-yyyy",         // 12-01-2019 → 2019-12-01 0000
                "d/M/yyyy",           // 1/12/2019
                "d-M-yyyy"
        };

        for (String format : formats) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                if (format.contains("HHmm") || format.contains("HH:mm")) {
                    return LocalDateTime.parse(dateTimeString.trim(), formatter);
                } else {
                    // Date-only format - set time to midnight
                    return LocalDate.parse(dateTimeString.trim(), formatter).atStartOfDay();
                }
            } catch (DateTimeParseException e) {
                // Try next format
            }
        }

        // Special handling for time-only inputs like "1800" or "3pm"
        try {
            // Try to parse as time only (assume today's date)
            LocalTime time = LocalTime.parse(dateTimeString.trim(),
                    DateTimeFormatter.ofPattern("HHmm"));
            return LocalDateTime.now().with(time);
        } catch (DateTimeParseException e) {
            // Try other time formats if needed
        }
        return null;
    }

    @Override
    public String toString() {
       if (byDateTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
            return super.toString() + " (by: " + byDateTime.format(formatter) + ")";
        } else {
            return super.toString() + " (by: " + by + ")";
        }
    }

}