package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CALENDAR_EVENTS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all events in the calendar of the scheduler to the user.
 */
public class ListEventCommand extends Command {

    public static final String COMMAND_WORD = "list event";

    public static final String MESSAGE_SUCCESS = "Listed all events";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredCalendarEventList(PREDICATE_SHOW_ALL_CALENDAR_EVENTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    // TODO this will have to switch tabs
}