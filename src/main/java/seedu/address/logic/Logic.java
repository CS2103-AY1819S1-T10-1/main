package seedu.scheduler.logic;

import javafx.collections.ObservableList;
import seedu.scheduler.logic.commands.CommandResult;
import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.logic.parser.exceptions.ParseException;
import seedu.scheduler.model.calendarEvent.CalendarEvent;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /** Returns an unmodifiable view of the filtered list of calendar events */
    ObservableList<CalendarEvent> getFilteredCalendarEventList();

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();
}
