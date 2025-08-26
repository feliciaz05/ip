package melody.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {

    protected String startTime;
    protected String endTime;
    protected LocalDateTime fromDateTime;
    protected LocalDateTime toDateTime;

    public Event(String description, String startTime, String endTime) {
        super(description, TaskType.EVENT);
        this.startTime = startTime;
        this.endTime = endTime;
        this.fromDateTime = parseDateTime(startTime);
        this.toDateTime = parseDateTime(endTime);
    }

    private LocalDateTime parseDateTime(String dateTimeString) {
        String[] formats = {
                "yyyy-MM-dd HH:mm",      // 2019-12-01 18:00
                "yyyy-MM-dd HHmm",       // 2019-12-01 1800
                "yyyy-MM-dd h:mma",      // 2019-12-01 6:00PM
                "yyyy/MM/dd HH:mm",      // 2019/12/01 18:00
                "dd/MM/yyyy HH:mm",      // 01/12/2019 18:00
                "dd/MM/yyyy HHmm",       // 01/12/2019 1800
                "yyyy-MM-dd",            // 2019-12-01 (time defaults to 00:00)
                "yyyy/MM/dd",            // 2019/12/01
                "dd/MM/yyyy",            // 01/12/2019
                "d/M/yyyy",              // 1/12/2019
                "d-M-yyyy"               // 1-12-2019
        };

        for (String format : formats) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                if (format.contains("HH") || format.contains("hh") || format.contains(":") || format.contains("a")) {
                    // Format includes time component
                    return LocalDateTime.parse(dateTimeString.trim(), formatter);
                } else {
                    // Date-only format - set time to midnight
                    return LocalDate.parse(dateTimeString.trim(), formatter).atStartOfDay();
                }
            } catch (DateTimeParseException e) {
                // Try next format
            }
        }

        // Special handling for time-only inputs (assume today's date)
        try {
            // Try 24-hour time (e.g., "1800")
            LocalTime time = LocalTime.parse(dateTimeString.trim(),
                    DateTimeFormatter.ofPattern("HHmm"));
            return LocalDateTime.now().with(time);
        } catch (DateTimeParseException e1) {
            try {
                // Try 12-hour time with AM/PM (e.g., "6:00PM")
                LocalTime time = LocalTime.parse(dateTimeString.trim(),
                        DateTimeFormatter.ofPattern("h:mma"));
                return LocalDateTime.now().with(time);
            } catch (DateTimeParseException e2) {
                // Try other time formats if needed
            }
        }

        return null;
    }


    @Override
    public String toString() {
        String fromFormatted = startTime;
        String toFormatted = endTime;

        // If we successfully parsed datetime, format them nicely
        if (fromDateTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
            fromFormatted = fromDateTime.format(formatter);
        }
        if (toDateTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
            toFormatted = toDateTime.format(formatter);
        }

        return super.toString() +
                " (from: " + fromFormatted + " to: " + toFormatted + ")";
    }

}