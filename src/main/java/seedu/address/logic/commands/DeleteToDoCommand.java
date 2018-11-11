package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.SwitchToTasksTabEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelToDo;
import seedu.address.model.todolist.ToDoListEvent;

/**
 * Deletes a todolist event identified using it's displayed index from the todolist.
 */
public class DeleteToDoCommand extends Command {
    public static final String COMMAND_WORD = "delete todo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the event identified by the index number used in the displayed todo list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TODOLIST_EVENT_SUCCESS = "Deleted ToDo Event: %1$s";

    private final Index targetIndex;

    public DeleteToDoCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        super.isToDoCommand = true;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(MESSAGE_INCORRECT_MODEL_TODO);
    }

    @Override
    public CommandResult execute(ModelToDo modelToDo, CommandHistory history) throws CommandException {
        requireNonNull(modelToDo);
        List<ToDoListEvent> lastShownList = modelToDo.getFilteredToDoListEventList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TODOLIST_EVENTS_DISPLAYED_INDEX);
        }

        ToDoListEvent toDoListEventToDelete = lastShownList.get(targetIndex.getZeroBased());
        modelToDo.deleteToDoListEvent(toDoListEventToDelete);
        modelToDo.commitToDoList();

        EventsCenter.getInstance().post(new SwitchToTasksTabEvent());

        return new CommandResult(String.format(MESSAGE_DELETE_TODOLIST_EVENT_SUCCESS, toDoListEventToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DeleteToDoCommand // instanceof handles nulls
            && targetIndex.equals(((DeleteToDoCommand) other).targetIndex)); // state check
    }
}
