package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelToDo;
import seedu.address.model.todolist.ToDoListEvent;
import seedu.address.ui.DescriptionDisplay;

/**
 * Shows description of a todolist event identified using its displayed index
 * from the calendar event list in the toDoList.
 */
public class ShowDescriptionCommand extends Command {

    public static final String COMMAND_WORD = "show todo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows description of the todo event identified by the index number used in the displayed event list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SHOW_DESCRIPTION_TODO_SUCCESS = "Showed Event's Description: %1$s";

    private final Index targetIndex;

    public ShowDescriptionCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        super.isToDoCommand = true;
    }

    /**
     * display a descriptionDisplay UI component {@code DescriptionDisplay}
     * @param root
     */
    private void display (Parent root) {
        Scene scene = new Scene(root, 200, 150);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(MESSAGE_INCORRECT_MODEL_TODO);
    }

    @Override
    public CommandResult execute(ModelToDo modelToDo, CommandHistory history) throws CommandException {
        requireNonNull(modelToDo);

        List<ToDoListEvent> filteredToDoListEventList = modelToDo.getFilteredToDoListEventList();

        if (targetIndex.getZeroBased() >= filteredToDoListEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TODOLIST_EVENTS_DISPLAYED_INDEX);
        }

        DescriptionDisplay descriptionDisplay =
                    new DescriptionDisplay(filteredToDoListEventList.get(targetIndex.getZeroBased()),
                            targetIndex.getZeroBased());
        display(descriptionDisplay.getRoot());

        return new CommandResult(String.format(MESSAGE_SHOW_DESCRIPTION_TODO_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowDescriptionCommand // instanceof handles nulls
                && targetIndex.equals(((ShowDescriptionCommand) other).targetIndex)); // state check
    }
}
