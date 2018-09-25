package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VENUE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_DIFFICULT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_IMPORTANT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_IMPORTANT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static seedu.address.testutil.TypicalPersons.MIDTERM;
import static seedu.address.testutil.TypicalPersons.WORKSHOP;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.*;
import seedu.address.model.person.Event;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.PersonUtil;

public class EditCommandSystemTest extends VenueBookSystemTest {

    @Test
    public void edit() {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_EVENT;
        String command = " " + EditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + NAME_DESC_WORKSHOP + "  "
                + DATE_DESC_WORKSHOP + " " + TIME_DESC_WORKSHOP + "  " + VENUE_DESC_WORKSHOP + " " + TAG_DESC_IMPORTANT + " ";
        Event editedEvent = new EventBuilder(WORKSHOP).withTags(VALID_TAG_IMPORTANT).build();
        assertCommandSuccess(command, index, editedEvent);

        /* Case: undo editing the last event in the list -> last event restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last event in the list -> last event edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateEvent(
                getModel().getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased()), editedEvent);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a event with new values same as existing values -> edited */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_WORKSHOP + DATE_DESC_WORKSHOP + TIME_DESC_WORKSHOP
                + VENUE_DESC_WORKSHOP + TAG_DESC_DIFFICULT + TAG_DESC_IMPORTANT;
        assertCommandSuccess(command, index, WORKSHOP);

        /* Case: edit a event with new values same as another event's values but with different name -> edited */
        assertTrue(getModel().getSchedule().getEventList().contains(WORKSHOP));
        index = INDEX_SECOND_EVENT;
        assertNotEquals(getModel().getFilteredEventList().get(index.getZeroBased()), WORKSHOP);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_MIDTERM + DATE_DESC_WORKSHOP + TIME_DESC_WORKSHOP
                + VENUE_DESC_WORKSHOP + TAG_DESC_DIFFICULT + TAG_DESC_IMPORTANT;
        editedEvent = new EventBuilder(WORKSHOP).withName(VALID_NAME_MIDTERM).build();
        assertCommandSuccess(command, index, editedEvent);

        /* Case: edit a event with new values same as another event's values but with different phone and email
         * -> edited
         */
        index = INDEX_SECOND_EVENT;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_WORKSHOP + DATE_DESC_MIDTERM + TIME_DESC_MIDTERM
                + VENUE_DESC_WORKSHOP + TAG_DESC_DIFFICULT + TAG_DESC_IMPORTANT;
        editedEvent = new EventBuilder(WORKSHOP).withDate(VALID_DATE_MIDTERM).withEmail(VALID_TIME_MIDTERM).build();
        assertCommandSuccess(command, index, editedEvent);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_EVENT;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        Event eventToEdit = getModel().getFilteredEventList().get(index.getZeroBased());
        editedEvent = new EventBuilder(eventToEdit).withTags().build();
        assertCommandSuccess(command, index, editedEvent);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered event list, edit index within bounds of schedule and event list -> edited */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        index = INDEX_FIRST_EVENT;
        assertTrue(index.getZeroBased() < getModel().getFilteredEventList().size());
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + NAME_DESC_WORKSHOP;
        eventToEdit = getModel().getFilteredEventList().get(index.getZeroBased());
        editedEvent = new EventBuilder(eventToEdit).withName(VALID_NAME_WORKSHOP).build();
        assertCommandSuccess(command, index, editedEvent);

        /* Case: filtered event list, edit index within bounds of schedule but out of bounds of event list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getSchedule().getEventList().size();
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_WORKSHOP,
                Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a event card is selected -------------------------- */

        /* Case: selects first card in the event list, edit a event -> edited, card selection remains unchanged but
         * browser url changes
         */
        showAllPersons();
        index = INDEX_FIRST_EVENT;
        selectPerson(index);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_MIDTERM + DATE_DESC_MIDTERM + TIME_DESC_MIDTERM
                + VENUE_DESC_MIDTERM + TAG_DESC_DIFFICULT;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new event's name
        assertCommandSuccess(command, index, MIDTERM, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + NAME_DESC_WORKSHOP,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1" + NAME_DESC_WORKSHOP,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredEventList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_WORKSHOP,
                Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + NAME_DESC_WORKSHOP,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_EVENT.getOneBased(),
                EditCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_EVENT.getOneBased() + INVALID_NAME_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_EVENT.getOneBased() + INVALID_DATE_DESC,
                Date.MESSAGE_DATE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_EVENT.getOneBased() + INVALID_TIME_DESC,
                Time.MESSAGE_TIME_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_EVENT.getOneBased() + INVALID_VENUE_DESC,
                Venue.MESSAGE_VENUE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_EVENT.getOneBased() + INVALID_TAG_DESC,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: edit a event with new values same as another event's values -> rejected */
        executeCommand(PersonUtil.getAddCommand(WORKSHOP));
        assertTrue(getModel().getSchedule().getEventList().contains(WORKSHOP));
        index = INDEX_FIRST_EVENT;
        assertFalse(getModel().getFilteredEventList().get(index.getZeroBased()).equals(WORKSHOP));
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_WORKSHOP + DATE_DESC_WORKSHOP + TIME_DESC_WORKSHOP
                + VENUE_DESC_WORKSHOP + TAG_DESC_DIFFICULT + TAG_DESC_IMPORTANT;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_EVENT);

        /* Case: edit a event with new values same as another event's values but with different tags -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_WORKSHOP + DATE_DESC_WORKSHOP + TIME_DESC_WORKSHOP
                + VENUE_DESC_WORKSHOP + TAG_DESC_IMPORTANT;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_EVENT);

        /* Case: edit a event with new values same as another event's values but with different address -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_WORKSHOP + DATE_DESC_WORKSHOP + TIME_DESC_WORKSHOP
                + VENUE_DESC_MIDTERM + TAG_DESC_DIFFICULT + TAG_DESC_IMPORTANT;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_EVENT);

        /* Case: edit a event with new values same as another event's values but with different phone -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_WORKSHOP + DATE_DESC_MIDTERM + TIME_DESC_WORKSHOP
                + VENUE_DESC_WORKSHOP + TAG_DESC_DIFFICULT + TAG_DESC_IMPORTANT;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_EVENT);

        /* Case: edit a event with new values same as another event's values but with different email -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_WORKSHOP + DATE_DESC_WORKSHOP + TIME_DESC_MIDTERM
                + VENUE_DESC_WORKSHOP + TAG_DESC_DIFFICULT + TAG_DESC_IMPORTANT;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_EVENT);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Event, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, Event, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Event editedEvent) {
        assertCommandSuccess(command, toEdit, editedEvent, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the event at index {@code toEdit} being
     * updated to values specified {@code editedEvent}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Event editedEvent,
            Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        expectedModel.updateEvent(expectedModel.getFilteredEventList().get(toEdit.getZeroBased()), editedEvent);
        expectedModel.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);

        assertCommandSuccess(command, expectedModel,
                String.format(EditCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code VenueBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see VenueBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see VenueBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code VenueBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see VenueBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
