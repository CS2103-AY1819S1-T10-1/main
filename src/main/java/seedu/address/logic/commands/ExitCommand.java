package seedu.scheduler.logic.commands;

import seedu.scheduler.commons.core.EventsCenter;
import seedu.scheduler.commons.events.ui.ExitAppRequestEvent;
import seedu.scheduler.logic.CommandHistory;
import seedu.scheduler.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Scheduler as requested ...";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
