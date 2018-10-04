package seedu.scheduler.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.scheduler.logic.CommandHistory;
import seedu.scheduler.model.Scheduler;
import seedu.scheduler.model.Model;

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
