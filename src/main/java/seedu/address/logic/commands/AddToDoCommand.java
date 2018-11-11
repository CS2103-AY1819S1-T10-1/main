package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.SwitchToTasksTabEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelToDo;
import seedu.address.model.todolist.ToDoListEvent;

/**
 * Adds a todolist event to the toDoList.
 */
public class AddToDoCommand extends Command {
    public static final String COMMAND_WORD = "add todo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a event to the todo list. "
        + "Parameters: "
        + PREFIX_TITLE + "TITLE "
        + PREFIX_DESCRIPTION + "DESCRIPTION "
        + PREFIX_PRIORITY + "PRIORITY "
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_TITLE + "CS2103 Final Exam "
        + PREFIX_DESCRIPTION + "Bring calculator "
        + PREFIX_PRIORITY + "H ";

    public static final String MESSAGE_SUCCESS = "New todo event added: %1$s";
    public static final String MESSAGE_DUPLICATE_TODO_EVENT = "This todo event already exists in the todolist";

    private final ToDoListEvent toAdd;

    /**
     * Creates an AddToDoCommand to add the specified {@code ToDoListEvent}
     */
    public AddToDoCommand(ToDoListEvent toDoListEvent) {
        requireNonNull(toDoListEvent);
        toAdd = toDoListEvent;
        super.isToDoCommand = true;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(MESSAGE_INCORRECT_MODEL_TODO);
    }

    @Override
    public CommandResult execute(ModelToDo modelToDo, CommandHistory history) throws CommandException {
        requireNonNull(modelToDo);

        if (modelToDo.hasToDoListEvent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TODO_EVENT);
        }

        modelToDo.addToDoListEvent(toAdd);

        EventsCenter.getInstance().post(new SwitchToTasksTabEvent());

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddToDoCommand // instanceof handles nulls
            && toAdd.equals(((AddToDoCommand) other).toAdd));
    }

}
