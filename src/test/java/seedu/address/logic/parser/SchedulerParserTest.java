package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ELEMENT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.AddToDoCommand;
import seedu.address.logic.commands.ClearCalendarCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.DeleteToDoCommand;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.commands.EditEventCommand.EditCalendarEventDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindEventCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListEventCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.ShowDescriptionCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.calendarevent.CalendarEvent;
import seedu.address.model.calendarevent.DatePredicate;
import seedu.address.model.calendarevent.FuzzySearchComparator;
import seedu.address.model.calendarevent.FuzzySearchFilterPredicate;
import seedu.address.model.calendarevent.TagsPredicate;
import seedu.address.model.todolist.ToDoListEvent;
import seedu.address.testutil.CalendarEventBuilder;
import seedu.address.testutil.EditCalendarEventDescriptorBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.ToDoListEventBuilder;
import seedu.address.testutil.ToDoListEventUtil;

public class SchedulerParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private final SchedulerParser parser = new SchedulerParser();

    @Test
    public void parseCommand_add() throws Exception {
        CalendarEvent calendarEvent = new CalendarEventBuilder().build();
        AddEventCommand command = (AddEventCommand) parser.parseCommand(PersonUtil.getAddCommand(calendarEvent));
        assertEquals(new AddEventCommand(calendarEvent), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCalendarCommand.COMMAND_WORD) instanceof ClearCalendarCommand);
        assertTrue(parser.parseCommand(ClearCalendarCommand.COMMAND_WORD + " 3") instanceof ClearCalendarCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteEventCommand command = (DeleteEventCommand) parser.parseCommand(
            DeleteEventCommand.COMMAND_WORD + " " + INDEX_FIRST_ELEMENT.getOneBased());
        assertEquals(new DeleteEventCommand(INDEX_FIRST_ELEMENT), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        CalendarEvent calendarEvent = new CalendarEventBuilder().build();
        EditCalendarEventDescriptor descriptor = new EditCalendarEventDescriptorBuilder(calendarEvent).build();
        EditEventCommand command = (EditEventCommand) parser.parseCommand(EditEventCommand.COMMAND_WORD + " "
            + INDEX_FIRST_ELEMENT.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditEventCommand(INDEX_FIRST_ELEMENT, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindEventCommand command = (FindEventCommand) parser.parseCommand(
            FindEventCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindEventCommand(new FuzzySearchFilterPredicate(keywords),
            new FuzzySearchComparator(keywords), new DatePredicate(null, null),
            new TagsPredicate(new ArrayList<>())), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListEventCommand.COMMAND_WORD) instanceof ListEventCommand);
        assertTrue(parser.parseCommand(ListEventCommand.COMMAND_WORD + " 3") instanceof ListEventCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
            SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_ELEMENT.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_ELEMENT), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }

    @Test
    public void parseCommand_addToDo() throws Exception {
        ToDoListEvent toDoListEvent = new ToDoListEventBuilder().build();
        AddToDoCommand commandToDo = (AddToDoCommand) parser
            .parseCommand(ToDoListEventUtil.getAddToDoCommand(toDoListEvent));
        assertEquals(new AddToDoCommand(toDoListEvent), commandToDo);
    }

    @Test
    public void parseCommand_deleteToDo() throws Exception {
        DeleteToDoCommand commandToDo = (DeleteToDoCommand) parser.parseCommand(
            DeleteToDoCommand.COMMAND_WORD + " " + INDEX_FIRST_ELEMENT.getOneBased());
        assertEquals(new DeleteToDoCommand(INDEX_FIRST_ELEMENT), commandToDo);
    }

    @Test
    public void parseCommand_showDescription() throws Exception {
        ShowDescriptionCommand commandToDo = (ShowDescriptionCommand) parser.parseCommand(
                ShowDescriptionCommand.COMMAND_WORD + " " + INDEX_FIRST_ELEMENT.getOneBased());
        assertEquals(new ShowDescriptionCommand(INDEX_FIRST_ELEMENT), commandToDo);
    }
}
