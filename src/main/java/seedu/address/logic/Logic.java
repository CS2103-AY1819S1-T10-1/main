package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.calendarevent.CalendarEvent;
import seedu.address.model.todolist.ToDoListEvent;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Executes the commandToDo and returns the result.
     *
     * @param commandText The commandToDo as entered by the user.
     * @return the result of the commandToDo execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult executeToDo(String commandText) throws CommandException, ParseException;

    /**
     * Returns an unmodifiable view of the filtered list of calendar events
     */
    ObservableList<CalendarEvent> getFilteredCalendarEventList();

    /**
     * Returns an unmodifiable view of the filtered list of todolist events
     */
    ObservableList<ToDoListEvent> getFilteredToDoListEventList();

    /**
     * Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object
     */
    ListElementPointer getHistorySnapshot();
}
