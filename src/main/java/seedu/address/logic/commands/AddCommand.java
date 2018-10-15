package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.calendarevent.CalendarEvent;

/**
 * Adds a calendar event to the scheduler.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a event to the scheduler. "
        + "Parameters: "
        + PREFIX_TITLE + "TITLE "
        + PREFIX_DESCRIPTION + "DESCRIPTION "
        + PREFIX_VENUE + "VENUE "
        + "[" + PREFIX_TAG + "TAG]...\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_TITLE + "John Doe "
        + PREFIX_DESCRIPTION + "98765432 "
        + PREFIX_VENUE + "311, Clementi Ave 2, #02-25 "
        + PREFIX_TAG + "friends "
        + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This event already exists in the scheduler";

    private final CalendarEvent toAdd;

    /**
     * Creates an AddCommand to add the specified {@code CalendarEvent}
     */
    public AddCommand(CalendarEvent calendarEvent) {
        requireNonNull(calendarEvent);
        toAdd = calendarEvent;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasCalendarEvent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addCalendarEvent(toAdd);
        model.commitScheduler();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddCommand // instanceof handles nulls
            && toAdd.equals(((AddCommand) other).toAdd));
    }
}
