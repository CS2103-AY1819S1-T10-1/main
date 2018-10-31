package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.Scheduler;

/**
 * Clears the calendar.
 */
public class ClearCalendarCommand extends Command {

    public static final String COMMAND_WORD = "clear calendar";
    public static final String MESSAGE_SUCCESS = "Calendar has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetData(new Scheduler());
        model.commitScheduler();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
