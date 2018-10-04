package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Scheduler;
import seedu.address.model.Model;

/**
 * Clears the scheduler.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Scheduler has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetData(new Scheduler());
        model.commitScheduler();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
