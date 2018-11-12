package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_CALENDAR_EVENTS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.address.testutil.TypicalEvents.CAREER_FAIR;
import static seedu.address.testutil.TypicalEvents.CHOIR_PRACTICE;
import static seedu.address.testutil.TypicalEvents.CS2040_LAB;
import static seedu.address.testutil.TypicalEvents.CS2103_LECTURE;
import static seedu.address.testutil.TypicalEvents.CS2104_TUTORIAL;
import static seedu.address.testutil.TypicalEvents.FIN3101_SEMINAR;
import static seedu.address.testutil.TypicalEvents.GOOGLE_INTERVIEW;
import static seedu.address.testutil.TypicalEvents.KEYWORD_MATCHING_LECTURE;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.FindEventCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

public class FindEventCommandSystemTest extends SchedulerSystemTest {

    @Test
    public void find() {
        /* Case: find calendar event in scheduler, command with leading spaces and trailing spaces
         * -> 1 calendar events found
         */
        String command = "   " + FindEventCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_LECTURE + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredAndSortedList(expectedModel, CS2103_LECTURE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where calendar event list is displaying the calendar event we are finding
         * -> 1 calendar event found
         */
        command = FindEventCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_LECTURE;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find calendar event where calendar event list is not displaying the calendar event we are finding
         * -> 1 calendar event found
         */
        command = FindEventCommand.COMMAND_WORD + " Lab";
        ModelHelper.setFilteredAndSortedList(expectedModel, CS2040_LAB);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple calendar events  in scheduler, 2 keywords -> 2 calendar events found */
        command = FindEventCommand.COMMAND_WORD + " Tutorial Seminar";
        ModelHelper.setFilteredAndSortedList(expectedModel, CS2104_TUTORIAL, FIN3101_SEMINAR);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple calendar events in scheduler, 2 keywords in reversed order
         * -> same 2 calendar events found
         */
        command = FindEventCommand.COMMAND_WORD + " Seminar Tutorial";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple calendar events in scheduler, 2 keywords with 1 repeat -> 2 calendar events found */
        command = FindEventCommand.COMMAND_WORD + " Tutorial Seminar Tutorial";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple calendar events in scheduler, 2 matching keywords and 1 non-matching keyword
         * -> 2 calendar events found
         */
        command = FindEventCommand.COMMAND_WORD + " Tutorial Seminar NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find calendar event in scheduler, keyword is same as title but has different case
         * -> 1 calendar event found
         */
        command = FindEventCommand.COMMAND_WORD + " gOoGlE InTErVieW";
        ModelHelper.setFilteredAndSortedList(expectedModel, GOOGLE_INTERVIEW);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find calendar event in scheduler, keyword is substring of title -> 1 calendar event found */
        command = FindEventCommand.COMMAND_WORD + " Inter";
        ModelHelper.setFilteredAndSortedList(expectedModel, GOOGLE_INTERVIEW);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find calendar event in scheduler, name is substring of keyword -> 3 calendar events found */
        command = FindEventCommand.COMMAND_WORD + " Bring";
        ModelHelper.setFilteredAndSortedList(expectedModel, GOOGLE_INTERVIEW, CHOIR_PRACTICE, CAREER_FAIR);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find calendar event not in scheduler -> 0 calendar events found */
        command = FindEventCommand.COMMAND_WORD + " EventNotInCalendar";
        ModelHelper.setFilteredAndSortedList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find description of calendar event in scheduler -> 1 calendar event found */
        command = FindEventCommand.COMMAND_WORD + " " + FIN3101_SEMINAR.getDescriptionObject().value;
        ModelHelper.setFilteredAndSortedList(expectedModel, FIN3101_SEMINAR);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find venue of calendar event in scheduler -> 1 calendar event found */
        command = FindEventCommand.COMMAND_WORD + " " + FIN3101_SEMINAR.getVenue().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter calendar event in scheduler by tag -> 1 calendar event found */
        List<Tag> tags = new ArrayList<>(FIN3101_SEMINAR.getTags());
        command = FindEventCommand.COMMAND_WORD + " " + PREFIX_TAG.getPrefix() + tags.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter calendar events in scheduler from date/time -> 2 calendar event found */
        command = FindEventCommand.COMMAND_WORD + " " + PREFIX_FROM.getPrefix() + " from/16 nov 2018 5pm";
        ModelHelper.setFilteredAndSortedList(expectedModel, CHOIR_PRACTICE, CAREER_FAIR);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter calendar events in scheduler before date/time -> 3 calendar events found */
        command = FindEventCommand.COMMAND_WORD + " " + PREFIX_TO.getPrefix() + "before/15 nov 2018 3pm";
        ModelHelper.setFilteredAndSortedList(expectedModel, CS2104_TUTORIAL, CS2040_LAB, GOOGLE_INTERVIEW);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter calendar events in scheduler between 2 date/times -> 3 calendar events found */
        command = FindEventCommand.COMMAND_WORD + " " + PREFIX_FROM.getPrefix() + "15 nov 2018 8am "
                + PREFIX_TO.getPrefix() + " 16 nov 2018 8pm";
        ModelHelper.setFilteredAndSortedList(expectedModel, CS2103_LECTURE, FIN3101_SEMINAR, CHOIR_PRACTICE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: search keywords and filter calendar events in scheduler before date/time -> 2 calendar event found */
        command = FindEventCommand.COMMAND_WORD + " lecture tutorial lab "
                + PREFIX_TO.getPrefix() + "14 nov 2018 5pm";
        ModelHelper.setFilteredAndSortedList(expectedModel, CS2104_TUTORIAL, CS2040_LAB);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find same calendar events in scheduler after deleting 1 of them -> 1 calendar event found */
        executeCommand(DeleteEventCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getScheduler().getCalendarEventList().contains(CS2104_TUTORIAL));
        command = FindEventCommand.COMMAND_WORD + " " + "Tutorial Seminar";
        expectedModel = getModel();
        ModelHelper.setFilteredAndSortedList(expectedModel, FIN3101_SEMINAR);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a calendar event is selected -> selected card deselected */
        showAllCalendarEvents();
        selectCalendarEvent(Index.fromOneBased(1));
        assertFalse(getCalendarEventListPanel().getHandleToSelectedCard().getTitle()
                                            .equals(FIN3101_SEMINAR.getTitle().value));
        command = FindEventCommand.COMMAND_WORD + " One-Fund Theorem";
        ModelHelper.setFilteredAndSortedList(expectedModel, FIN3101_SEMINAR);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find calendar event in empty scheduler -> 0 persons found */
        deleteAllCalendarEvents();
        command = FindEventCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_LECTURE;
        expectedModel = getModel();
        ModelHelper.setFilteredAndSortedList(expectedModel, FIN3101_SEMINAR);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd EvENt proJECt";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_CALENDAR_EVENTS_LISTED_OVERVIEW} with the number of people in the
     * filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code SchedulerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     *
     * @see SchedulerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
            MESSAGE_CALENDAR_EVENTS_LISTED_OVERVIEW, expectedModel.getFilteredAndSortedCalendarEventList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code SchedulerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
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
