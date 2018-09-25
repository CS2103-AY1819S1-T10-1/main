package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
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
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_WORKSHOP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.MIDTERM;
import static seedu.address.testutil.TypicalPersons.WORKSHOP;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Venue;
import seedu.address.model.person.Time;
import seedu.address.model.person.Name;
import seedu.address.model.person.Event;
import seedu.address.model.person.Date;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.PersonUtil;

public class AddCommandSystemTest extends VenueBookSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a event without tags to a non-empty schedule, command with leading spaces and trailing spaces
         * -> added
         */
        Event toAdd = MIDTERM;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_MIDTERM + "  " + DATE_DESC_MIDTERM + " "
                + TIME_DESC_MIDTERM + "   " + VENUE_DESC_MIDTERM + "   " + TAG_DESC_DIFFICULT + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addEvent(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a event with all fields same as another event in the schedule except name -> added */
        toAdd = new EventBuilder(MIDTERM).withName(VALID_NAME_WORKSHOP).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_WORKSHOP + DATE_DESC_MIDTERM + TIME_DESC_MIDTERM + VENUE_DESC_MIDTERM
                + TAG_DESC_DIFFICULT;
        assertCommandSuccess(command, toAdd);

        /* Case: add a event with all fields same as another event in the schedule except phone and email
         * -> added
         */
        toAdd = new EventBuilder(MIDTERM).withDate(VALID_DATE_WORKSHOP).withEmail(VALID_TIME_WORKSHOP).build();
        command = PersonUtil.getAddCommand(toAdd);
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty schedule -> added */
        deleteAllPersons();
        assertCommandSuccess(ALICE);

        /* Case: add a event with tags, command with parameters in random order -> added */
        toAdd = WORKSHOP;
        command = AddCommand.COMMAND_WORD + TAG_DESC_DIFFICULT + DATE_DESC_WORKSHOP + VENUE_DESC_WORKSHOP + NAME_DESC_WORKSHOP
                + TAG_DESC_IMPORTANT + TIME_DESC_WORKSHOP;
        assertCommandSuccess(command, toAdd);

        /* Case: add a event, missing tags -> added */
        assertCommandSuccess(HOON);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the event list before adding -> added */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(IDA);

        /* ------------------------ Perform add operation while a event card is selected --------------------------- */

        /* Case: selects first card in the event list, add a event -> added, card selection remains unchanged */
        selectPerson(Index.fromOneBased(1));
        assertCommandSuccess(CARL);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate event -> rejected */
        command = PersonUtil.getAddCommand(HOON);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_EVENT);

        /* Case: add a duplicate event except with different phone -> rejected */
        toAdd = new EventBuilder(HOON).withDate(VALID_DATE_WORKSHOP).build();
        command = PersonUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_EVENT);

        /* Case: add a duplicate event except with different email -> rejected */
        toAdd = new EventBuilder(HOON).withEmail(VALID_TIME_WORKSHOP).build();
        command = PersonUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_EVENT);

        /* Case: add a duplicate event except with different address -> rejected */
        toAdd = new EventBuilder(HOON).withAddress(VALID_VENUE_WORKSHOP).build();
        command = PersonUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_EVENT);

        /* Case: add a duplicate event except with different tags -> rejected */
        command = PersonUtil.getAddCommand(HOON) + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_EVENT);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + DATE_DESC_MIDTERM + TIME_DESC_MIDTERM + VENUE_DESC_MIDTERM;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_MIDTERM + TIME_DESC_MIDTERM + VENUE_DESC_MIDTERM;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing email -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_MIDTERM + DATE_DESC_MIDTERM + VENUE_DESC_MIDTERM;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing address -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_MIDTERM + DATE_DESC_MIDTERM + TIME_DESC_MIDTERM;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + PersonUtil.getPersonDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC + DATE_DESC_MIDTERM + TIME_DESC_MIDTERM + VENUE_DESC_MIDTERM;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_MIDTERM + INVALID_DATE_DESC + TIME_DESC_MIDTERM + VENUE_DESC_MIDTERM;
        assertCommandFailure(command, Date.MESSAGE_DATE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_MIDTERM + DATE_DESC_MIDTERM + INVALID_TIME_DESC + VENUE_DESC_MIDTERM;
        assertCommandFailure(command, Time.MESSAGE_TIME_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_MIDTERM + DATE_DESC_MIDTERM + TIME_DESC_MIDTERM + INVALID_VENUE_DESC;
        assertCommandFailure(command, Venue.MESSAGE_VENUE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_MIDTERM + DATE_DESC_MIDTERM + TIME_DESC_MIDTERM + VENUE_DESC_MIDTERM
                + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code VenueBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see VenueBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Event toAdd) {
        assertCommandSuccess(PersonUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Event)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Event)
     */
    private void assertCommandSuccess(String command, Event toAdd) {
        Model expectedModel = getModel();
        expectedModel.addEvent(toAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Event)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Event)
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
     * 4. {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
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
