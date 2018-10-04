package seedu.scheduler.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.scheduler.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.scheduler.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.scheduler.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.scheduler.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.scheduler.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.scheduler.testutil.TypicalPersons.getTypicalScheduler;

import org.junit.Test;

import seedu.scheduler.commons.core.Messages;
import seedu.scheduler.commons.core.index.Index;
import seedu.scheduler.logic.CommandHistory;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.ModelManager;
import seedu.scheduler.model.UserPrefs;
import seedu.scheduler.model.calendarEvent.CalendarEvent;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalScheduler(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        CalendarEvent calendarEventToDelete = model.getFilteredCalendarEventList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_CALENDAR_EVENT_SUCCESS, calendarEventToDelete);

        ModelManager expectedModel = new ModelManager(model.getScheduler(), new UserPrefs());
        expectedModel.deleteCalendarEvent(calendarEventToDelete);
        expectedModel.commitScheduler();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCalendarEventList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_CALENDAR_EVENTS_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        CalendarEvent calendarEventToDelete = model.getFilteredCalendarEventList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_CALENDAR_EVENT_SUCCESS, calendarEventToDelete);

        Model expectedModel = new ModelManager(model.getScheduler(), new UserPrefs());
        expectedModel.deleteCalendarEvent(calendarEventToDelete);
        expectedModel.commitScheduler();
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of scheduler book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getScheduler().getCalendarEventList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_CALENDAR_EVENTS_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        CalendarEvent calendarEventToDelete = model.getFilteredCalendarEventList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getScheduler(), new UserPrefs());
        expectedModel.deleteCalendarEvent(calendarEventToDelete);
        expectedModel.commitScheduler();

        // delete -> first calendarEvent deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered calendarEvent list to show all persons
        expectedModel.undoScheduler();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first calendarEvent deleted again
        expectedModel.redoScheduler();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCalendarEventList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        // execution failed -> scheduler book state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_CALENDAR_EVENTS_DISPLAYED_INDEX);

        // single scheduler book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code CalendarEvent} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted calendarEvent in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the calendarEvent object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonDeleted() throws Exception {
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getScheduler(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        CalendarEvent calendarEventToDelete = model.getFilteredCalendarEventList().get(INDEX_FIRST_PERSON.getZeroBased());
        expectedModel.deleteCalendarEvent(calendarEventToDelete);
        expectedModel.commitScheduler();

        // delete -> deletes second calendarEvent in unfiltered calendarEvent list / first calendarEvent in filtered calendarEvent list
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered calendarEvent list to show all persons
        expectedModel.undoScheduler();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(calendarEventToDelete, model.getFilteredCalendarEventList().get(INDEX_FIRST_PERSON.getZeroBased()));
        // redo -> deletes same second calendarEvent in unfiltered calendarEvent list
        expectedModel.redoScheduler();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different calendarEvent -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredCalendarEventList(p -> false);

        assertTrue(model.getFilteredCalendarEventList().isEmpty());
    }
}
