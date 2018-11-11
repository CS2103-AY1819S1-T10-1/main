package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelToDo;
import seedu.address.model.calendarevent.CalendarEvent;

/**
 * Deletes a calendar event identified using it's displayed index from the calendar.
 */
public class DeleteEventCommand extends Command {

    public static final String COMMAND_WORD = "delete event";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the event identified by the index number used in the displayed event list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_CALENDAR_EVENT_SUCCESS = "Deleted Event: %1$s";

    private final Index targetIndex;

    public DeleteEventCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<CalendarEvent> lastShownList = model.getFilteredCalendarEventList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CALENDAR_EVENTS_DISPLAYED_INDEX);
        }

        CalendarEvent calendarEventToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteCalendarEvent(calendarEventToDelete);
        model.commitScheduler();
        return new CommandResult(String.format(MESSAGE_DELETE_CALENDAR_EVENT_SUCCESS, calendarEventToDelete));
    }

    @Override
    public CommandResult execute(ModelToDo modelToDo, CommandHistory history) throws CommandException {
        throw new CommandException(MESSAGE_INCORRECT_MODEL_CALENDAR);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DeleteEventCommand // instanceof handles nulls
            && targetIndex.equals(((DeleteEventCommand) other).targetIndex)); // state check
    }
}
