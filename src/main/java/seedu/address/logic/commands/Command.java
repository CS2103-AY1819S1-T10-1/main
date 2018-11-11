package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelToDo;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    public static final String MESSAGE_INCORRECT_MODEL_TODO =
        "Incorrect Model: To Do Commands should operate on the ModelTodo";
    public static final String MESSAGE_INCORRECT_MODEL_CALENDAR =
        "Incorrect Model: Calendar Commands should operate on the Model";

    protected boolean isToDoCommand;

    /**
     * Returns whether command is a todolist command
     */
    public boolean isToDoCommand() {
        return isToDoCommand;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model   {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model, CommandHistory history) throws CommandException;

    /**
     * Executes the todolist command and returns the result message.
     *
     * @param modelToDo {@code ModelToDo} which the todolist command should operate on.
     * @param history   {@code CommandHistory} which the todolist command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(ModelToDo modelToDo, CommandHistory history) throws CommandException;

}
