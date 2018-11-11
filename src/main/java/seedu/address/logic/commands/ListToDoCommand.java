package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.SwitchToTasksTabEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelToDo;

/**
 * Lists all events in the task list of the scheduler to the user.
 */
public class ListToDoCommand extends Command {

    public static final String COMMAND_WORD = "list todo";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    public ListToDoCommand() {
        super.isToDoCommand = true;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(MESSAGE_INCORRECT_MODEL_TODO);
    }

    @Override
    public CommandResult execute(ModelToDo modelToDo, CommandHistory history) {
        requireNonNull(modelToDo);

        EventsCenter.getInstance().post(new SwitchToTasksTabEvent());

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
