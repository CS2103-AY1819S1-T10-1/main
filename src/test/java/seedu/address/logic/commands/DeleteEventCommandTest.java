package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showCalendarEventAtIndex;
import static seedu.address.testutil.TypicalEvents.getTypicalScheduler;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ELEMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ELEMENT;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.calendarevent.CalendarEvent;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteEventCommand}.
 */
public class DeleteEventCommandTest {

    private Model model = new ModelManager(getTypicalScheduler(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        CalendarEvent calendarEventToDelete =
            model.getFilteredCalendarEventList().get(INDEX_FIRST_ELEMENT.getZeroBased());
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(INDEX_FIRST_ELEMENT);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_DELETE_CALENDAR_EVENT_SUCCESS,
            calendarEventToDelete);

        ModelManager expectedModel = new ModelManager(model.getScheduler(), new UserPrefs());
        expectedModel.deleteCalendarEvent(calendarEventToDelete);
        expectedModel.commitScheduler();

        assertCommandSuccess(deleteEventCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCalendarEventList().size() + 1);
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(outOfBoundIndex);

        assertCommandFailure(deleteEventCommand, model, commandHistory,
            Messages.MESSAGE_INVALID_CALENDAR_EVENTS_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showCalendarEventAtIndex(model, INDEX_FIRST_ELEMENT);

        CalendarEvent calendarEventToDelete =
            model.getFilteredCalendarEventList().get(INDEX_FIRST_ELEMENT.getZeroBased());
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(INDEX_FIRST_ELEMENT);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_DELETE_CALENDAR_EVENT_SUCCESS,
            calendarEventToDelete);

        Model expectedModel = new ModelManager(model.getScheduler(), new UserPrefs());
        expectedModel.deleteCalendarEvent(calendarEventToDelete);
        expectedModel.commitScheduler();
        showNoCalendarEvent(expectedModel);

        assertCommandSuccess(deleteEventCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showCalendarEventAtIndex(model, INDEX_FIRST_ELEMENT);

        Index outOfBoundIndex = INDEX_SECOND_ELEMENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getScheduler().getCalendarEventList().size());

        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(outOfBoundIndex);

        assertCommandFailure(deleteEventCommand, model, commandHistory,
            Messages.MESSAGE_INVALID_CALENDAR_EVENTS_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        CalendarEvent calendarEventToDelete =
            model.getFilteredCalendarEventList().get(INDEX_FIRST_ELEMENT.getZeroBased());
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(INDEX_FIRST_ELEMENT);
        Model expectedModel = new ModelManager(model.getScheduler(), new UserPrefs());
        expectedModel.deleteCalendarEvent(calendarEventToDelete);
        expectedModel.commitScheduler();

        // delete -> first calendarevent deleted
        deleteEventCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered calendarevent list to show all persons
        expectedModel.undoScheduler();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first calendarevent deleted again
        expectedModel.redoScheduler();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCalendarEventList().size() + 1);
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(outOfBoundIndex);

        // execution failed -> address book state not added into model
        assertCommandFailure(deleteEventCommand, model, commandHistory,
            Messages.MESSAGE_INVALID_CALENDAR_EVENTS_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code CalendarEvent} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted calendar event in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the calendar event object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameCalendarEventDeleted() throws Exception {
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(INDEX_FIRST_ELEMENT);
        Model expectedModel = new ModelManager(model.getScheduler(), new UserPrefs());

        showCalendarEventAtIndex(model, INDEX_SECOND_ELEMENT);
        CalendarEvent calendarEventToDelete =
            model.getFilteredCalendarEventList().get(INDEX_FIRST_ELEMENT.getZeroBased());
        expectedModel.deleteCalendarEvent(calendarEventToDelete);
        expectedModel.commitScheduler();

        // delete -> deletes second calendar event in unfiltered calendar event list / first calendar event in filtered
        // calendar event list
        deleteEventCommand.execute(model, commandHistory);

        // undo -> reverts scheduler back to previous state and filtered calendar event list to show all events
        expectedModel.undoScheduler();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(calendarEventToDelete,
            model.getFilteredCalendarEventList().get(INDEX_FIRST_ELEMENT.getZeroBased()));
        // redo -> deletes same second calendar event in unfiltered calendar event list
        expectedModel.redoScheduler();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteEventCommand deleteFirstCommand = new DeleteEventCommand(INDEX_FIRST_ELEMENT);
        DeleteEventCommand deleteSecondCommand = new DeleteEventCommand(INDEX_SECOND_ELEMENT);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteEventCommand deleteFirstCommandCopy = new DeleteEventCommand(INDEX_FIRST_ELEMENT);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different calendarevent -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoCalendarEvent(Model model) {
        model.updateFilteredCalendarEventList(p -> false);

        assertTrue(model.getFilteredCalendarEventList().isEmpty());
    }
}
