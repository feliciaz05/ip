package melody;

import melody.exception.MelodyException;
import melody.task.Event;
import melody.task.TaskType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EventTest {

    @Test
    public void testEventCreation() {
        Event event = new Event("Meeting", "2023-12-01 14:00", "2023-12-01 16:00");

        assertEquals("Meeting", event.getDescription());
        assertEquals(TaskType.EVENT, event.getType());
        assertEquals("2023-12-01 14:00", event.getStartTime());
        assertEquals("2023-12-01 16:00", event.getEndTime());
        assertFalse(event.isDone());
    }

    @Test
    public void testEventToString() {
        Event event = new Event("Conference", "2023-12-01 09:00", "2023-12-01 17:00");

        String result = event.toString();
        assertTrue(result.contains("[E]"));
        assertTrue(result.contains("[ ]"));
        assertTrue(result.contains("Conference"));
        assertTrue(result.contains("from:"));
        assertTrue(result.contains("to:"));
    }

    @Test
    public void testEventUpdateFields() throws MelodyException {
        Event event = new Event("Old event", "2023-01-01", "2023-01-02");

        // Test description update
        String result = event.updateField("description", "New event");
        assertEquals("Changed description to: New event", result);
        assertEquals("New event", event.getDescription());

        // Test from time update
        result = event.updateField("from", "2024-01-01");
        assertEquals("Changed 'from' time to: 2024-01-01", result);
        assertEquals("2024-01-01", event.getStartTime());

        // Test to time update
        result = event.updateField("to", "2024-01-02");
        assertEquals("Changed 'to' time to: 2024-01-02", result);
        assertEquals("2024-01-02", event.getEndTime());
    }

    @Test
    public void testEventInvalidUpdateField() {
        Event event = new Event("Event", "2023-01-01", "2023-01-02");

        assertThrows(MelodyException.class, () -> {
            event.updateField("invalid", "value");
        });
    }

    @Test
    public void testEventGetAvailableUpdateFields() {
        Event event = new Event("Event", "2023-01-01", "2023-01-02");

        String fields = event.getAvailableUpdateFields();
        assertTrue(fields.contains("/description"));
        assertTrue(fields.contains("/from"));
        assertTrue(fields.contains("/to"));
    }

    @Test
    public void testEventDateTimeParsing() {
        Event event = new Event("Test", "2023-12-01 14:00", "2023-12-01 16:00");

        // Should parse datetime successfully
        assertNotNull(event.getStartTime());
        assertNotNull(event.getEndTime());
    }

    @Test
    public void testEventAssertions() {
        assertThrows(AssertionError.class, () -> {
            new Event(null, "2023-01-01", "2023-01-02");
        });

        assertThrows(AssertionError.class, () -> {
            new Event("", "2023-01-01", "2023-01-02");
        });

        assertThrows(AssertionError.class, () -> {
            new Event("Event", null, "2023-01-02");
        });

        assertThrows(AssertionError.class, () -> {
            new Event("Event", "2023-01-01", null);
        });
    }
}