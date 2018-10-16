package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.END_DESC_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VENUE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.START_DESC_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.START_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_TUTORIAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalEvents.AMY;
import static seedu.address.testutil.TypicalEvents.CARL;
import static seedu.address.testutil.TypicalEvents.HOON;
import static seedu.address.testutil.TypicalEvents.IDA;
import static seedu.address.testutil.TypicalEvents.KEYWORD_MATCHING_MEIER;
import static seedu.address.testutil.TypicalEvents.LECTURE;
import static seedu.address.testutil.TypicalEvents.TUTORIAL;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
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

public class AddCommandSystemTest extends SchedulerSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a calendar event without tags to a non-empty scheduler, command with leading spaces and trailing
         spaces
         * -> added
         */
        CalendarEvent toAdd = AMY;
        String command =
            "   " + AddCommand.COMMAND_WORD + "  " + TITLE_DESC_LECTURE + "  " + DESCRIPTION_DESC_LECTURE + " "
            + START_DESC_LECTURE + " " + END_DESC_LECTURE + " " + VENUE_DESC_LECTURE + "   " + TAG_DESC_FRIEND + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addCalendarEvent(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a calendar event with all fields same as another calendar event in the scheduler except name ->
        added */
        toAdd = new CalendarEventBuilder(AMY).withTitle(VALID_TITLE_TUTORIAL).build();
        command = AddCommand.COMMAND_WORD + TITLE_DESC_TUTORIAL + DESCRIPTION_DESC_LECTURE + START_DESC_LECTURE
                + END_DESC_LECTURE + VENUE_DESC_LECTURE + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a calendar event with all fields same as another calendar event in the scheduler except phone
        and email
         * -> added
         */
        toAdd = new CalendarEventBuilder(AMY).withDescription(VALID_DESCRIPTION_TUTORIAL).build();
        command = PersonUtil.getAddCommand(toAdd);
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty address book -> added */
        deleteAllPersons();
        assertCommandSuccess(LECTURE);

        /* Case: add a calendarevent with tags, command with parameters in random order -> added */
        toAdd = TUTORIAL;
        command =
            AddCommand.COMMAND_WORD + TAG_DESC_FRIEND + DESCRIPTION_DESC_TUTORIAL + START_DESC_LECTURE
                    + END_DESC_LECTURE + VENUE_DESC_TUTORIAL + TITLE_DESC_TUTORIAL + TAG_DESC_HUSBAND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a calendarevent, missing tags -> added */
        assertCommandSuccess(HOON);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the calendarevent list before adding -> added */
        showPersonsWithTitle(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(IDA);

        /* ------------------------ Perform add operation while a calendarevent card is selected
        --------------------------- */

        /* Case: selects first card in the calendarevent list, add a calendarevent -> added, card selection remains
        unchanged */
        selectPerson(Index.fromOneBased(1));
        assertCommandSuccess(CARL);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate calendarevent -> rejected */
        command = PersonUtil.getAddCommand(HOON);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_CALENDAR_EVENT);

        /* Case: add a duplicate calendarevent except with different tags -> rejected */
        command = PersonUtil.getAddCommand(HOON) + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_CALENDAR_EVENT);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + DESCRIPTION_DESC_LECTURE + VENUE_DESC_LECTURE
                + START_DESC_LECTURE + END_DESC_LECTURE;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing description -> rejected */
        command = AddCommand.COMMAND_WORD + TITLE_DESC_LECTURE + VENUE_DESC_LECTURE
                + START_DESC_LECTURE + END_DESC_LECTURE;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing start -> rejected */
        command = AddCommand.COMMAND_WORD + TITLE_DESC_LECTURE + DESCRIPTION_DESC_LECTURE + VENUE_DESC_LECTURE
                + END_DESC_LECTURE;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing end -> rejected */
        command = AddCommand.COMMAND_WORD + TITLE_DESC_LECTURE + DESCRIPTION_DESC_LECTURE + VENUE_DESC_LECTURE
                + START_DESC_LECTURE;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing venue -> rejected */
        command = AddCommand.COMMAND_WORD + TITLE_DESC_LECTURE + DESCRIPTION_DESC_LECTURE
                + START_DESC_LECTURE + END_DESC_LECTURE;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + PersonUtil.getPersonDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_TITLE_DESC + DESCRIPTION_DESC_LECTURE + VENUE_DESC_LECTURE;
        assertCommandFailure(command, Title.MESSAGE_CONSTRAINTS);

        /* Case: invalid description -> rejected */
        command = AddCommand.COMMAND_WORD + TITLE_DESC_LECTURE + INVALID_DESCRIPTION_DESC + VENUE_DESC_LECTURE
                + START_DESC_LECTURE + END_DESC_LECTURE;
        assertCommandFailure(command, Description.MESSAGE_CONSTRAINTS);

        /* Case: invalid start -> rejected */
        command = AddCommand.COMMAND_WORD + TITLE_DESC_LECTURE + DESCRIPTION_DESC_LECTURE + VENUE_DESC_LECTURE
                + INVALID_START_DESC + END_DESC_LECTURE;
        assertCommandFailure(command, Venue.MESSAGE_CONSTRAINTS);

        /* Case: invalid end -> rejected */
        command = AddCommand.COMMAND_WORD + TITLE_DESC_LECTURE + DESCRIPTION_DESC_LECTURE + VENUE_DESC_LECTURE
                + START_DESC_LECTURE + INVALID_END_DESC;
        assertCommandFailure(command, Venue.MESSAGE_CONSTRAINTS);

        /* Case: invalid venue -> rejected */
        command = AddCommand.COMMAND_WORD + TITLE_DESC_LECTURE + DESCRIPTION_DESC_LECTURE + INVALID_VENUE_DESC
                + START_DESC_LECTURE + END_DESC_LECTURE;
        assertCommandFailure(command, Venue.MESSAGE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + TITLE_DESC_LECTURE + DESCRIPTION_DESC_LECTURE + VENUE_DESC_LECTURE
            + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code CalendarEventListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code SchedulerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see SchedulerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(CalendarEvent toAdd) {
        assertCommandSuccess(PersonUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(CalendarEvent)}. Executes {@code command}
     * instead.
     *
     * @see AddCommandSystemTest#assertCommandSuccess(CalendarEvent)
     */
    private void assertCommandSuccess(String command, CalendarEvent toAdd) {
        Model expectedModel = getModel();
        expectedModel.addCalendarEvent(toAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, CalendarEvent)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code CalendarEventListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     *
     * @see AddCommandSystemTest#assertCommandSuccess(String, CalendarEvent)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code CalendarEventListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
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
        assertStatusBarUnchanged();
    }
}
