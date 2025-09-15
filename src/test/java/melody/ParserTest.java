package melody;

import melody.command.CommandType;
import melody.exception.MelodyException;
import melody.parser.Parser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void testParseCommand_validCommands() throws MelodyException {
        assertEquals(CommandType.BYE, Parser.parseCommand("bye"));
        assertEquals(CommandType.LIST, Parser.parseCommand("list"));
        assertEquals(CommandType.UNMARK, Parser.parseCommand("unmark 1"));
        assertEquals(CommandType.MARK, Parser.parseCommand("mark 1"));
        assertEquals(CommandType.DEADLINE, Parser.parseCommand("deadline task /by time"));
        assertEquals(CommandType.TODO, Parser.parseCommand("todo task"));
        assertEquals(CommandType.EVENT, Parser.parseCommand("event task /from start /to end"));
        assertEquals(CommandType.DELETE, Parser.parseCommand("delete 1"));
        assertEquals(CommandType.FIND, Parser.parseCommand("find keyword"));
        assertEquals(CommandType.UPDATE, Parser.parseCommand("update 1 /description new task"));
    }

    @Test
    public void testParseCommand_invalidCommand_throwsException() {
        assertThrows(MelodyException.class, () -> Parser.parseCommand("invalid command"));
        assertThrows(MelodyException.class, () -> Parser.parseCommand(""));
        assertThrows(MelodyException.class, () -> Parser.parseCommand("   "));
    }

    @Test
    public void testParseTaskNumber_validNumbers() throws MelodyException {
        assertEquals(1, Parser.parseTaskNumber("mark 1"));
        assertEquals(5, Parser.parseTaskNumber("unmark 5"));
        assertEquals(10, Parser.parseTaskNumber("delete 10"));
    }

    @Test
    public void testParseTaskNumber_invalidInput_throwsException() {
        assertThrows(MelodyException.class, () -> Parser.parseTaskNumber("mark"));
        assertThrows(MelodyException.class, () -> Parser.parseTaskNumber("mark abc"));
        assertThrows(MelodyException.class, () -> Parser.parseTaskNumber("mark -1"));
        assertThrows(MelodyException.class, () -> Parser.parseTaskNumber("mark 0"));
    }

    @Test
    public void testParseTodo_validInput() throws MelodyException {
        assertEquals("read book", Parser.parseTodo("todo read book"));
        assertEquals("read book", Parser.parseTodo("todo   read book   "));
    }

    @Test
    public void testParseTodo_invalidInput_throwsException() {
        assertThrows(MelodyException.class, () -> Parser.parseTodo("todo"));
        assertThrows(MelodyException.class, () -> Parser.parseTodo("todo   "));
    }

    @Test
    public void testParseDeadline_validInput() throws MelodyException {
        String[] result = Parser.parseDeadline("deadline submit assignment /by 2023-12-31");
        assertEquals("submit assignment", result[0]);
        assertEquals("2023-12-31", result[1]);
    }

    @Test
    public void testParseDeadline_invalidInput_throwsException() {
        assertThrows(MelodyException.class, () -> Parser.parseDeadline("deadline"));
        assertThrows(MelodyException.class, () -> Parser.parseDeadline("deadline /by 2023-12-31"));
        assertThrows(MelodyException.class, () -> Parser.parseDeadline("deadline submit assignment"));
        assertThrows(MelodyException.class, () -> Parser.parseDeadline("deadline submit assignment /by"));
    }

    @Test
    public void testParseEvent_validInput() throws MelodyException {
        String[] result = Parser.parseEvent("event meeting /from 14:00 /to 15:00");
        assertEquals("meeting", result[0]);
        assertEquals("14:00", result[1]);
        assertEquals("15:00", result[2]);
    }

    @Test
    public void testParseEvent_invalidInput_throwsException() {
        assertThrows(MelodyException.class, () -> Parser.parseEvent("event"));
        assertThrows(MelodyException.class, () -> Parser.parseEvent("event meeting"));
        assertThrows(MelodyException.class, () -> Parser.parseEvent("event meeting /from"));
        assertThrows(MelodyException.class, () -> Parser.parseEvent("event meeting /from 14:00"));
        assertThrows(MelodyException.class, () -> Parser.parseEvent("event meeting /to 15:00"));
    }

    @Test
    public void testParseFind_validInput() throws MelodyException {
        assertEquals("book", Parser.parseFind("find book"));
        assertEquals("important", Parser.parseFind("find   important   "));
    }

    @Test
    public void testParseFind_invalidInput_throwsException() {
        assertThrows(MelodyException.class, () -> Parser.parseFind("find"));
        assertThrows(MelodyException.class, () -> Parser.parseFind("find   "));
    }

    @Test
    public void testParseUpdate_validInput() throws MelodyException {
        String[] result = Parser.parseUpdate("update 1 /description new task");
        assertEquals("1", result[0]);
        assertEquals("description", result[1]);
        assertEquals("new task", result[2]);

        result = Parser.parseUpdate("update 2 /from 14:00");
        assertEquals("2", result[0]);
        assertEquals("from", result[1]);
        assertEquals("14:00", result[2]);
    }

    @Test
    public void testParseUpdate_invalidInput_throwsException() {
        assertThrows(MelodyException.class, () -> Parser.parseUpdate("update"));
        assertThrows(MelodyException.class, () -> Parser.parseUpdate("update 1"));
        assertThrows(MelodyException.class, () -> Parser.parseUpdate("update 1 /description"));
        assertThrows(MelodyException.class, () -> Parser.parseUpdate("update abc /description task"));
        assertThrows(MelodyException.class, () -> Parser.parseUpdate("update -1 /description task"));
    }
}