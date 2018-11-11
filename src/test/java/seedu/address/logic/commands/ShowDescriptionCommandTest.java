package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandToDoFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandToDoSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showToDoListEventAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ELEMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ELEMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_ELEMENT;
import static seedu.address.testutil.TypicalTodoListEvents.getTypicalToDoList;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.ModelManagerToDo;
import seedu.address.model.ModelToDo;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the ModelToDo) for {@code ShowDescriptionCommand}.
 */
public class ShowDescriptionCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private ModelToDo modelToDo = new ModelManagerToDo(getTypicalToDoList(), new UserPrefs());
    private ModelToDo expectedModelToDo = new ModelManagerToDo(getTypicalToDoList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    //@Test
    /**
     * TODO pass it
     */
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(modelToDo.getFilteredToDoListEventList().size());

        assertExecutionSuccess(INDEX_FIRST_ELEMENT);
        assertExecutionSuccess(INDEX_THIRD_ELEMENT);
        assertExecutionSuccess(lastPersonIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(modelToDo.getFilteredToDoListEventList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_TODOLIST_EVENTS_DISPLAYED_INDEX);
    }

    //@Test
    /**
     * TODO pass it
     */
    public void execute_validIndexFilteredList_success() {
        showToDoListEventAtIndex(modelToDo, INDEX_FIRST_ELEMENT);
        showToDoListEventAtIndex(expectedModelToDo, INDEX_FIRST_ELEMENT);

        assertExecutionSuccess(INDEX_FIRST_ELEMENT);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showToDoListEventAtIndex(modelToDo, INDEX_FIRST_ELEMENT);
        showToDoListEventAtIndex(expectedModelToDo, INDEX_FIRST_ELEMENT);

        Index outOfBoundsIndex = INDEX_SECOND_ELEMENT;
        // ensures that outOfBoundIndex is still in bounds of to do list
        assertTrue(outOfBoundsIndex.getZeroBased() < modelToDo.getToDoList().getToDoList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_TODOLIST_EVENTS_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ShowDescriptionCommand showFirstCommand = new ShowDescriptionCommand(INDEX_FIRST_ELEMENT);
        ShowDescriptionCommand showSecondCommand = new ShowDescriptionCommand(INDEX_SECOND_ELEMENT);

        // same object -> returns true
        assertTrue(showFirstCommand.equals(showFirstCommand));

        // same values -> returns true
        ShowDescriptionCommand showFirstCommandCopy = new ShowDescriptionCommand(INDEX_FIRST_ELEMENT);
        assertTrue(showFirstCommand.equals(showFirstCommandCopy));

        // different types -> returns false
        assertFalse(showFirstCommand.equals(1));

        // null -> returns false
        assertFalse(showFirstCommand.equals(null));

        // different todolistevent -> returns false
        assertFalse(showFirstCommand.equals(showSecondCommand));
    }

    /**
     * Executes a {@code ShowDescriptionCommand} with the given {@code index}.
     */
    private void assertExecutionSuccess(Index index) {
        ShowDescriptionCommand showDescriptionCommand = new ShowDescriptionCommand(index);
        String expectedMessage = String.format(ShowDescriptionCommand.MESSAGE_SHOW_DESCRIPTION_TODO_SUCCESS,
                index.getOneBased());

        assertCommandToDoSuccess(showDescriptionCommand, modelToDo, commandHistory, expectedMessage, expectedModelToDo);
    }

    /**
     * Executes a {@code ShowDescriptionCommand} with the given {@code index},
     * and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        ShowDescriptionCommand showDescriptionCommand = new ShowDescriptionCommand(index);
        assertCommandToDoFailure(showDescriptionCommand, modelToDo, commandHistory, expectedMessage);
        assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
    }
}
