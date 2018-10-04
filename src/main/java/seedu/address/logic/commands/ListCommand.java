package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CALENDAR_EVENTS;

/**
 * Lists all calendar events in the scheduler to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all events";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredCalendarEventList(PREDICATE_SHOW_ALL_CALENDAR_EVENTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
