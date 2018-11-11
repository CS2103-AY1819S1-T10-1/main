package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandToDoFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandToDoSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showToDoListEventAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ELEMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ELEMENT;
import static seedu.address.testutil.TypicalTodoListEvents.getTypicalToDoList;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.ModelManagerToDo;
import seedu.address.model.ModelToDo;
import seedu.address.model.UserPrefs;
import seedu.address.model.todolist.ToDoListEvent;

public class DeleteToDoCommandTest {

    private ModelToDo model = new ModelManagerToDo(getTypicalToDoList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        ToDoListEvent toDoListEventToDelete =
            model.getFilteredToDoListEventList().get(INDEX_FIRST_ELEMENT.getZeroBased());
        DeleteToDoCommand deleteToDoCommand = new DeleteToDoCommand(INDEX_FIRST_ELEMENT);

        String expectedMessage = String.format(DeleteToDoCommand.MESSAGE_DELETE_TODOLIST_EVENT_SUCCESS,
            toDoListEventToDelete);

        ModelManagerToDo expectedModel = new ModelManagerToDo(model.getToDoList(), new UserPrefs());
        expectedModel.deleteToDoListEvent(toDoListEventToDelete);
        expectedModel.commitToDoList();

        assertCommandToDoSuccess(deleteToDoCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredToDoListEventList().size() + 1);
        DeleteToDoCommand deleteToDoCommand = new DeleteToDoCommand(outOfBoundIndex);

        assertCommandToDoFailure(deleteToDoCommand, model, commandHistory,
            Messages.MESSAGE_INVALID_TODOLIST_EVENTS_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showToDoListEventAtIndex(model, INDEX_FIRST_ELEMENT);

        ToDoListEvent toDoListEventToDelete =
            model.getFilteredToDoListEventList().get(INDEX_FIRST_ELEMENT.getZeroBased());
        DeleteToDoCommand deleteToDoCommand = new DeleteToDoCommand(INDEX_FIRST_ELEMENT);

        String expectedMessage = String.format(DeleteToDoCommand.MESSAGE_DELETE_TODOLIST_EVENT_SUCCESS,
            toDoListEventToDelete);

        ModelToDo expectedModel = new ModelManagerToDo(model.getToDoList(), new UserPrefs());
        System.out.printf(toDoListEventToDelete.toString());
        expectedModel.deleteToDoListEvent(toDoListEventToDelete);
        expectedModel.commitToDoList();
        showNoToDoListEvent(expectedModel);

        assertCommandToDoSuccess(deleteToDoCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showToDoListEventAtIndex(model, INDEX_FIRST_ELEMENT);

        Index outOfBoundIndex = INDEX_SECOND_ELEMENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getToDoList().getToDoList().size());

        DeleteToDoCommand deleteToDoCommand = new DeleteToDoCommand(outOfBoundIndex);

        assertCommandToDoFailure(deleteToDoCommand, model, commandHistory,
            Messages.MESSAGE_INVALID_TODOLIST_EVENTS_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteToDoCommand deleteFirstToDoCommand = new DeleteToDoCommand(INDEX_FIRST_ELEMENT);
        DeleteToDoCommand deleteSecondToDoCommand = new DeleteToDoCommand(INDEX_SECOND_ELEMENT);

        // same object -> returns true
        assertTrue(deleteFirstToDoCommand.equals(deleteFirstToDoCommand));

        // same values -> returns true
        DeleteToDoCommand deleteFirstToDoCommandCopy = new DeleteToDoCommand(INDEX_FIRST_ELEMENT);
        assertTrue(deleteFirstToDoCommand.equals(deleteFirstToDoCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstToDoCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstToDoCommand.equals(null));

        // different todolist event -> returns false
        assertFalse(deleteFirstToDoCommand.equals(deleteSecondToDoCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoToDoListEvent(ModelToDo modelToDo) {
        modelToDo.updateFilteredToDoListEventList(p -> false);

        assertTrue(modelToDo.getFilteredToDoListEventList().isEmpty());
    }
}
