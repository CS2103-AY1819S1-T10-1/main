package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.END_DESC_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.END_DESC_LECTURE_2;
import static seedu.address.logic.commands.CommandTestUtil.END_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VENUE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.START_DESC_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.START_DESC_LECTURE_2;
import static seedu.address.logic.commands.CommandTestUtil.START_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATETIME_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATETIME_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_SEMINAR;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_TUTORIAL;

import static seedu.address.testutil.TypicalEvents.LECTURE;
import static seedu.address.testutil.TypicalEvents.TUTORIAL;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ELEMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ELEMENT;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.calendarevent.CalendarEvent;
import seedu.address.model.calendarevent.Description;
import seedu.address.model.calendarevent.Title;
import seedu.address.model.calendarevent.Venue;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.CalendarEventBuilder;
import seedu.address.testutil.PersonUtil;

public class EditEventCommandSystemTest extends SchedulerSystemTest {

    @Test
    public void edit() {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_ELEMENT;
        String command = " " + EditEventCommand.COMMAND_WORD + "  " + index.getOneBased() + "  "
            + TITLE_DESC_TUTORIAL + "  " + DESCRIPTION_DESC_TUTORIAL + " " + START_DESC_TUTORIAL + " "
            + END_DESC_TUTORIAL + " " + VENUE_DESC_TUTORIAL + " " + TAG_DESC_HUSBAND + " ";
        CalendarEvent editedCalendarEvent = new CalendarEventBuilder(TUTORIAL).withTags(VALID_TAG_HUSBAND).build();
        assertCommandSuccess(command, index, editedCalendarEvent);

        /* Case: undo editing the last calendar event in the list -> last calendar event restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last calendar event in the list -> last calendar event edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateCalendarEvent(
                getModel().getFilteredCalendarEventList().get(INDEX_FIRST_ELEMENT.getZeroBased()), editedCalendarEvent);
        assertCommandSuccess(command, model, expectedResultMessage);

        // TODO: decide correct response
        /* Case: edit a calendar event with new values same as existing values -> edited */
        command = EditEventCommand.COMMAND_WORD + " " + index.getOneBased() + TITLE_DESC_TUTORIAL
            + DESCRIPTION_DESC_TUTORIAL + START_DESC_TUTORIAL + END_DESC_TUTORIAL + VENUE_DESC_TUTORIAL
            + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandSuccess(command, index, TUTORIAL);

        /* Case: edit a calendar event with new values same as another calendar event's values but with different title
        -> edited */
        assertTrue(getModel().getScheduler().getCalendarEventList().contains(TUTORIAL));
        index = INDEX_SECOND_ELEMENT;
        assertNotEquals(getModel().getFilteredCalendarEventList().get(index.getZeroBased()), TUTORIAL);
        command = EditEventCommand.COMMAND_WORD + " " + index.getOneBased() + TITLE_DESC_LECTURE
            + DESCRIPTION_DESC_TUTORIAL + START_DESC_TUTORIAL + END_DESC_TUTORIAL + VENUE_DESC_TUTORIAL
            + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedCalendarEvent = new CalendarEventBuilder(TUTORIAL).withTitle(VALID_TITLE_LECTURE).build();
        assertCommandSuccess(command, index, editedCalendarEvent);

        /* Case: edit a calendar event with new values same as another calendar event's values but with different
        start and end date/time
         * -> edited
         */
        index = INDEX_SECOND_ELEMENT;
        command = EditEventCommand.COMMAND_WORD + " " + index.getOneBased() + TITLE_DESC_TUTORIAL
            + DESCRIPTION_DESC_TUTORIAL + START_DESC_LECTURE + END_DESC_LECTURE + VENUE_DESC_TUTORIAL
            + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedCalendarEvent = new CalendarEventBuilder(TUTORIAL).withStart(VALID_START_DATETIME_LECTURE)
                .withEnd(VALID_END_DATETIME_LECTURE).build();
        assertCommandSuccess(command, index, editedCalendarEvent);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_ELEMENT;
        command = EditEventCommand.COMMAND_WORD + " " + index.getOneBased() + " " + TAG_EMPTY;
        CalendarEvent calendarEventToEdit = getModel().getFilteredCalendarEventList().get(index.getZeroBased());
        editedCalendarEvent = new CalendarEventBuilder(calendarEventToEdit).withTags().build();
        assertCommandSuccess(command, index, editedCalendarEvent);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered calendar event list, edit index within bounds of address book and calendar event list ->
        edited */

        showCalendarEventsWithTitle(VALID_TITLE_SEMINAR);
        index = INDEX_FIRST_ELEMENT;
        assertTrue(index.getZeroBased() < getModel().getFilteredCalendarEventList().size());
        command = EditEventCommand.COMMAND_WORD + " " + index.getOneBased() + " " + TITLE_DESC_LECTURE;
        calendarEventToEdit = getModel().getFilteredCalendarEventList().get(index.getZeroBased());
        editedCalendarEvent = new CalendarEventBuilder(calendarEventToEdit).withTitle(VALID_TITLE_LECTURE).build();
        assertCommandSuccess(command, index, editedCalendarEvent);

        /* Case: filtered calendar event list, edit index within bounds of address book but out of bounds of
        calendar event list
         * -> rejected
         */
        //showCalendarEventsWithTitle(KEYWORD_MATCHING_EXACT_TUTORIAL);
        showCalendarEventsWithTitle(VALID_TITLE_SEMINAR);
        int invalidIndex = getModel().getScheduler().getCalendarEventList().size();
        assertCommandFailure(EditEventCommand.COMMAND_WORD + " " + invalidIndex + TITLE_DESC_TUTORIAL,
            Messages.MESSAGE_INVALID_CALENDAR_EVENTS_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a calendar event card is selected ----------------- */

        /* Case: selects first card in the calendar event list, edit a calendar event -> edited, card selection remains
         unchanged */
        showAllCalendarEvents();
        index = INDEX_FIRST_ELEMENT;
        selectCalendarEvent(index);
        command = EditEventCommand.COMMAND_WORD + " " + index.getOneBased() + TITLE_DESC_TUTORIAL
            + DESCRIPTION_DESC_TUTORIAL + START_DESC_TUTORIAL + END_DESC_TUTORIAL
            + VENUE_DESC_TUTORIAL + TAG_DESC_HUSBAND + TAG_DESC_FRIEND;
        assertCommandSuccess(command, index, TUTORIAL, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditEventCommand.COMMAND_WORD + " 0" + TITLE_DESC_TUTORIAL,
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditEventCommand.COMMAND_WORD + " -1" + TITLE_DESC_TUTORIAL,
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredCalendarEventList().size() + 1;
        assertCommandFailure(EditEventCommand.COMMAND_WORD + " " + invalidIndex + TITLE_DESC_TUTORIAL,
            Messages.MESSAGE_INVALID_CALENDAR_EVENTS_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditEventCommand.COMMAND_WORD + TITLE_DESC_TUTORIAL,
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditEventCommand.COMMAND_WORD + " " + INDEX_FIRST_ELEMENT.getOneBased(),
            EditEventCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid title -> rejected */
        assertCommandFailure(
                EditEventCommand.COMMAND_WORD + " " + INDEX_FIRST_ELEMENT.getOneBased() + INVALID_TITLE_DESC,
                Title.MESSAGE_CONSTRAINTS);

        /* Case: invalid description -> rejected */
        assertCommandFailure(
                EditEventCommand.COMMAND_WORD + " " + INDEX_FIRST_ELEMENT.getOneBased() + INVALID_DESCRIPTION_DESC,
                Description.MESSAGE_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        assertCommandFailure(
                EditEventCommand.COMMAND_WORD + " " + INDEX_FIRST_ELEMENT.getOneBased() + INVALID_VENUE_DESC,
                Venue.MESSAGE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(
                EditEventCommand.COMMAND_WORD + " " + INDEX_FIRST_ELEMENT.getOneBased() + INVALID_TAG_DESC,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: edit a calendar event with new values same as another calendar event's values -> rejected */
        executeCommand(PersonUtil.getAddCommand(LECTURE));
        assertTrue(getModel().getScheduler().getCalendarEventList().contains(LECTURE));
        index = INDEX_FIRST_ELEMENT;
        assertFalse(getModel().getFilteredCalendarEventList().get(index.getZeroBased()).equals(LECTURE));
        command = EditEventCommand.COMMAND_WORD + " " + index.getOneBased() + TITLE_DESC_LECTURE
            + DESCRIPTION_DESC_LECTURE + START_DESC_LECTURE_2 + END_DESC_LECTURE_2
            + VENUE_DESC_LECTURE + TAG_DESC_FRIEND;
        assertCommandFailure(command, EditEventCommand.MESSAGE_DUPLICATE_CALENDAR_EVENT);

        /* Case: edit a calendar event with new values same as another calendar event's values but with different tags
        -> rejected */
        command = EditEventCommand.COMMAND_WORD + " " + index.getOneBased() + TITLE_DESC_LECTURE
            + DESCRIPTION_DESC_LECTURE + START_DESC_LECTURE_2 + END_DESC_LECTURE_2
            + VENUE_DESC_LECTURE + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditEventCommand.MESSAGE_DUPLICATE_CALENDAR_EVENT);

        /* Case: edit a calendarevent with new values same as another calendarevent's values but with different
        description -> rejected */
        command = EditEventCommand.COMMAND_WORD + " " + index.getOneBased() + TITLE_DESC_LECTURE
            + DESCRIPTION_DESC_TUTORIAL + START_DESC_LECTURE_2 + END_DESC_LECTURE_2
            + VENUE_DESC_LECTURE + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditEventCommand.MESSAGE_DUPLICATE_CALENDAR_EVENT);

    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, CalendarEvent, Index)} except that
     * the selected card remain unchanged.
     *
     * @param toEdit the index of the current model's filtered list
     * @see EditEventCommandSystemTest#assertCommandSuccess(String, Index, CalendarEvent, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, CalendarEvent editedCalendarEvent) {
        assertCommandSuccess(command, toEdit, editedCalendarEvent, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditEventCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the calendar event at index {@code toEdit}
     * being updated to values specified {@code editedCalendarEvent}.<br>
     *
     * @param toEdit the index of the current model's filtered list.
     * @see EditEventCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, CalendarEvent editedCalendarEvent,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();

        expectedModel.updateCalendarEvent(
            expectedModel.getFilteredCalendarEventList().get(toEdit.getZeroBased()), editedCalendarEvent);

        assertCommandSuccess(command, expectedModel,
            String.format(EditEventCommand.MESSAGE_EDIT_CALENDAR_EVENT_SUCCESS, editedCalendarEvent),
            expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * selected card remains unchanged.
     *
     * @see EditEventCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the command box has the default style class.<br>
     * 5. Asserts that the calendar events displayed match the calendar events list
     * Verifications 1 and 2 are performed by
     * {@code SchedulerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see SchedulerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see SchedulerSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code SchedulerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see SchedulerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();
        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
    }
}
