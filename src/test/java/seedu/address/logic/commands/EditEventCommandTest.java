package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_TUTORIAL;
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
import seedu.address.logic.commands.EditEventCommand.EditCalendarEventDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Scheduler;
import seedu.address.model.UserPrefs;
import seedu.address.model.calendarevent.CalendarEvent;
import seedu.address.testutil.CalendarEventBuilder;
import seedu.address.testutil.EditCalendarEventDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for EditEventCommand.
 */
public class EditEventCommandTest {

    private Model model = new ModelManager(getTypicalScheduler(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        CalendarEvent editedCalendarEvent = new CalendarEventBuilder().build();
        EditCalendarEventDescriptor descriptor = new EditCalendarEventDescriptorBuilder(editedCalendarEvent).build();
        EditEventCommand editEventCommand = new EditEventCommand(INDEX_FIRST_ELEMENT, descriptor);

        String expectedMessage = String.format(
            EditEventCommand.MESSAGE_EDIT_CALENDAR_EVENT_SUCCESS, editedCalendarEvent);

        Model expectedModel = new ModelManager(new Scheduler(model.getScheduler()), new UserPrefs());
        expectedModel.updateCalendarEvent(model.getFilteredCalendarEventList().get(0), editedCalendarEvent);
        expectedModel.commitScheduler();

        assertCommandSuccess(editEventCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredCalendarEventList().size());
        CalendarEvent lastCalendarEvent = model.getFilteredCalendarEventList().get(indexLastPerson.getZeroBased());

        CalendarEventBuilder personInList = new CalendarEventBuilder(lastCalendarEvent);
        CalendarEvent editedCalendarEvent =
            personInList.withTitle(VALID_TITLE_TUTORIAL).withDescription(VALID_DESCRIPTION_TUTORIAL)
                .withTags(VALID_TAG_HUSBAND).build();

        EditCalendarEventDescriptor descriptor =
            new EditCalendarEventDescriptorBuilder().withTitle(VALID_TITLE_TUTORIAL)
                .withDescription(VALID_DESCRIPTION_TUTORIAL).withTags(VALID_TAG_HUSBAND).build();
        EditEventCommand editEventCommand = new EditEventCommand(indexLastPerson, descriptor);

        String expectedMessage =
            String.format(EditEventCommand.MESSAGE_EDIT_CALENDAR_EVENT_SUCCESS, editedCalendarEvent);

        Model expectedModel = new ModelManager(new Scheduler(model.getScheduler()), new UserPrefs());
        expectedModel.updateCalendarEvent(lastCalendarEvent, editedCalendarEvent);
        expectedModel.commitScheduler();

        assertCommandSuccess(editEventCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditEventCommand editEventCommand =
            new EditEventCommand(INDEX_FIRST_ELEMENT, new EditCalendarEventDescriptor());
        CalendarEvent editedCalendarEvent =
            model.getFilteredCalendarEventList().get(INDEX_FIRST_ELEMENT.getZeroBased());

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_CALENDAR_EVENT_SUCCESS,
            editedCalendarEvent);

        Model expectedModel = new ModelManager(new Scheduler(model.getScheduler()), new UserPrefs());
        expectedModel.commitScheduler();

        assertCommandSuccess(editEventCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showCalendarEventAtIndex(model, INDEX_FIRST_ELEMENT);

        CalendarEvent calendarEventInFilteredList =
            model.getFilteredCalendarEventList().get(INDEX_FIRST_ELEMENT.getZeroBased());
        CalendarEvent editedCalendarEvent =
            new CalendarEventBuilder(calendarEventInFilteredList).withTitle(VALID_TITLE_TUTORIAL).build();
        EditEventCommand editEventCommand = new EditEventCommand(INDEX_FIRST_ELEMENT,
            new EditCalendarEventDescriptorBuilder().withTitle(VALID_TITLE_TUTORIAL).build());

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_CALENDAR_EVENT_SUCCESS,
            editedCalendarEvent);

        Model expectedModel = new ModelManager(new Scheduler(model.getScheduler()), new UserPrefs());
        expectedModel.updateCalendarEvent(model.getFilteredCalendarEventList().get(0), editedCalendarEvent);
        expectedModel.commitScheduler();

        assertCommandSuccess(editEventCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateCalendarEventUnfilteredList_failure() {
        CalendarEvent firstCalendarEvent = model.getFilteredCalendarEventList().get(INDEX_FIRST_ELEMENT.getZeroBased());
        EditCalendarEventDescriptor descriptor = new EditCalendarEventDescriptorBuilder(firstCalendarEvent).build();
        EditEventCommand editEventCommand = new EditEventCommand(INDEX_SECOND_ELEMENT, descriptor);

        assertCommandFailure(editEventCommand, model, commandHistory,
            EditEventCommand.MESSAGE_DUPLICATE_CALENDAR_EVENT);
    }

    @Test
    public void execute_duplicateCalendarEventFilteredList_failure() {
        showCalendarEventAtIndex(model, INDEX_FIRST_ELEMENT);

        // edit calendarevent in filtered list into a duplicate in address book
        CalendarEvent calendarEventInList =
            model.getScheduler().getCalendarEventList().get(INDEX_SECOND_ELEMENT.getZeroBased());
        EditEventCommand editEventCommand = new EditEventCommand(INDEX_FIRST_ELEMENT,
            new EditCalendarEventDescriptorBuilder(calendarEventInList).build());

        assertCommandFailure(editEventCommand, model, commandHistory,
            EditEventCommand.MESSAGE_DUPLICATE_CALENDAR_EVENT);
    }

    @Test
    public void execute_invalidCalendarEventIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCalendarEventList().size() + 1);
        EditCalendarEventDescriptor descriptor =
            new EditCalendarEventDescriptorBuilder().withTitle(VALID_TITLE_TUTORIAL).build();
        EditEventCommand editEventCommand = new EditEventCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editEventCommand, model, commandHistory,
            Messages.MESSAGE_INVALID_CALENDAR_EVENTS_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidCalendarEventIndexFilteredList_failure() {
        showCalendarEventAtIndex(model, INDEX_FIRST_ELEMENT);
        Index outOfBoundIndex = INDEX_SECOND_ELEMENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getScheduler().getCalendarEventList().size());

        EditEventCommand editEventCommand = new EditEventCommand(outOfBoundIndex,
            new EditCalendarEventDescriptorBuilder().withTitle(VALID_TITLE_TUTORIAL).build());

        assertCommandFailure(editEventCommand, model, commandHistory,
            Messages.MESSAGE_INVALID_CALENDAR_EVENTS_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        CalendarEvent editedCalendarEvent = new CalendarEventBuilder().build();
        CalendarEvent calendarEventToEdit =
            model.getFilteredCalendarEventList().get(INDEX_FIRST_ELEMENT.getZeroBased());
        EditCalendarEventDescriptor descriptor = new EditCalendarEventDescriptorBuilder(editedCalendarEvent).build();
        EditEventCommand editEventCommand = new EditEventCommand(INDEX_FIRST_ELEMENT, descriptor);
        Model expectedModel = new ModelManager(new Scheduler(model.getScheduler()), new UserPrefs());
        expectedModel.updateCalendarEvent(calendarEventToEdit, editedCalendarEvent);
        expectedModel.commitScheduler();

        // edit -> first calendarevent edited
        editEventCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered calendarevent list to show all persons
        expectedModel.undoScheduler();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first calendarevent edited again
        expectedModel.redoScheduler();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCalendarEventList().size() + 1);
        EditCalendarEventDescriptor descriptor =
            new EditCalendarEventDescriptorBuilder().withTitle(VALID_TITLE_TUTORIAL).build();
        EditEventCommand editEventCommand = new EditEventCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editEventCommand, model, commandHistory,
            Messages.MESSAGE_INVALID_CALENDAR_EVENTS_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code CalendarEvent} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited calendarevent in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the calendarevent object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonEdited() throws Exception {
        CalendarEvent editedCalendarEvent = new CalendarEventBuilder().build();
        EditCalendarEventDescriptor descriptor = new EditCalendarEventDescriptorBuilder(editedCalendarEvent).build();
        EditEventCommand editEventCommand = new EditEventCommand(INDEX_FIRST_ELEMENT, descriptor);
        Model expectedModel = new ModelManager(new Scheduler(model.getScheduler()), new UserPrefs());

        showCalendarEventAtIndex(model, INDEX_SECOND_ELEMENT);
        CalendarEvent calendarEventToEdit =
            model.getFilteredCalendarEventList().get(INDEX_FIRST_ELEMENT.getZeroBased());
        expectedModel.updateCalendarEvent(calendarEventToEdit, editedCalendarEvent);
        expectedModel.commitScheduler();

        // edit -> edits second calendarevent in unfiltered calendarevent list / first calendarevent in filtered
        // calendarevent list
        editEventCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered calendarevent list to show all persons
        expectedModel.undoScheduler();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredCalendarEventList().get(INDEX_FIRST_ELEMENT.getZeroBased()),
            calendarEventToEdit);
        // redo -> edits same second calendarevent in unfiltered calendarevent list
        expectedModel.redoScheduler();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditEventCommand standardCommand = new EditEventCommand(INDEX_FIRST_ELEMENT, DESC_LECTURE);

        // same values -> returns true
        EditCalendarEventDescriptor copyDescriptor = new EditCalendarEventDescriptor(DESC_LECTURE);
        EditEventCommand commandWithSameValues = new EditEventCommand(INDEX_FIRST_ELEMENT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCalendarCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditEventCommand(INDEX_SECOND_ELEMENT, DESC_LECTURE)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditEventCommand(INDEX_FIRST_ELEMENT, DESC_TUTORIAL)));
    }

}
