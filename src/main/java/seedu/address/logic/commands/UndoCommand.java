package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelToDo;

/**
 * Reverts the {@code model}'s scheduler to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoScheduler()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.undoScheduler();
        model.clearAllPredicatesAndComparators();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public CommandResult execute(ModelToDo modelToDo, CommandHistory history) throws CommandException {
        throw new CommandException(MESSAGE_INCORRECT_MODEL_CALENDAR);
    }
}
