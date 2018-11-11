package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelToDo;
import seedu.address.model.calendarevent.CalendarEvent;

/**
 * Adds a calendar event to the calendar of the scheduler.
 */
public class AddEventCommand extends Command {
    public static final String COMMAND_WORD = "add event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the calendar. "
        + "Parameters: "
        + PREFIX_TITLE + "TITLE "
        + PREFIX_DESCRIPTION + "DESCRIPTION "
        + PREFIX_START + "START DATE & TIME "
        + PREFIX_END + "END DATE & TIME "
        + PREFIX_VENUE + "VENUE "
        + "[" + PREFIX_TAG + "TAG]...\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_TITLE + "CS2103 Final Exam "
        + PREFIX_DESCRIPTION + "Bring calculator "
        + PREFIX_START + "2018-12-05 17:00 "
        + PREFIX_END + "2018-12-05 19:00 "
        + PREFIX_VENUE + "MPSH4 "
        + PREFIX_TAG + "CS2103 "
        + PREFIX_TAG + "Final";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_CALENDAR_EVENT = "This event already exists in the calendar";

    private final CalendarEvent toAdd;

    /**
     * Creates an AddEventCommand to add the specified {@code CalendarEvent}
     */
    public AddEventCommand(CalendarEvent calendarEvent) {
        requireNonNull(calendarEvent);
        toAdd = calendarEvent;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasCalendarEvent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CALENDAR_EVENT);
        }

        model.addCalendarEvent(toAdd);
        model.commitScheduler();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public CommandResult execute(ModelToDo modelToDo, CommandHistory history) throws CommandException {
        throw new CommandException(MESSAGE_INCORRECT_MODEL_CALENDAR);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddEventCommand // instanceof handles nulls
            && toAdd.equals(((AddEventCommand) other).toAdd));
    }
}
