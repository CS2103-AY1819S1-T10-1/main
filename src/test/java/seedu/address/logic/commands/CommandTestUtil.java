package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.Scheduler;
import seedu.address.model.calendarevent.CalendarEvent;
import seedu.address.model.calendarevent.TitleContainsKeywordsPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_TITLE_LECTURE = "CS2103 Lecture";
    public static final String VALID_TITLE_TUTORIAL = "CS2104 Tutorial";
    public static final String VALID_DESCRIPTION_LECTURE = "Abstraction, IntelliJ, Gradle";
    public static final String VALID_DESCRIPTION_TUTORIAL = "Monadic parsers";
    public static final String VALID_VENUE_LECTURE = "Block 312, Amy Street 1";
    public static final String VALID_VENUE_TUTORIAL = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_START_DATETIME_LECTURE = "2018-10-16 14:00";
    public static final String VALID_END_DATETIME_LECTURE = "2018-10-16 16:00";
    public static final String VALID_START_DATETIME_TUTORIAL = "2018-10-18 10:00";
    public static final String VALID_END_DATETIME_TUTORIAL = "2018-10-18 11:00";
    public static final String VALID_PRIORITY_TUTORIAL = "L";
    public static final String VALID_PRIORITY_LECTURE = "H";

    public static final String TITLE_DESC_LECTURE = " " + PREFIX_TITLE + VALID_TITLE_LECTURE;
    public static final String TITLE_DESC_TUTORIAL = " " + PREFIX_TITLE + VALID_TITLE_TUTORIAL;
    public static final String DESCRIPTION_DESC_LECTURE = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_LECTURE;
    public static final String DESCRIPTION_DESC_TUTORIAL = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_TUTORIAL;
    public static final String START_DESC_LECTURE = " " + PREFIX_START + VALID_START_DATETIME_LECTURE;
    public static final String START_DESC_TUTORIAL = " " + PREFIX_START + VALID_START_DATETIME_TUTORIAL;
    public static final String END_DESC_LECTURE = " " + PREFIX_END + VALID_END_DATETIME_LECTURE;
    public static final String END_DESC_TUTORIAL = " " + PREFIX_END + VALID_END_DATETIME_TUTORIAL;
    public static final String VENUE_DESC_LECTURE = " " + PREFIX_VENUE + VALID_VENUE_LECTURE;
    public static final String VENUE_DESC_TUTORIAL = " " + PREFIX_VENUE + VALID_VENUE_TUTORIAL;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_TITLE_DESC = " " + PREFIX_TITLE + " q"; // start with whitespace not allowed
    public static final String INVALID_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION + " "; // empty string not allowed
    public static final String INVALID_START_DESC = " " + PREFIX_START + "2018-10-16 2.00pm"; // wrong format for time
    public static final String INVALID_END_DESC = " " + PREFIX_END + "10/16/2018 16:00"; // wrong format for date
    public static final String INVALID_VENUE_DESC = " " + PREFIX_VENUE; // empty string not allowed
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditCalendarEventDescriptor DESC_LECTURE;
    public static final EditCommand.EditCalendarEventDescriptor DESC_TUTORIAL;

    static {
        DESC_LECTURE = new EditPersonDescriptorBuilder().withTitle(VALID_TITLE_LECTURE)
            .withDescription(VALID_DESCRIPTION_LECTURE).withStart(VALID_START_DATETIME_LECTURE)
            .withEnd(VALID_END_DATETIME_LECTURE).withVenue(VALID_VENUE_LECTURE)
            .withTags(VALID_TAG_FRIEND).build();
        DESC_TUTORIAL = new EditPersonDescriptorBuilder().withTitle(VALID_TITLE_TUTORIAL)
            .withDescription(VALID_DESCRIPTION_TUTORIAL).withStart(VALID_START_DATETIME_TUTORIAL)
                .withEnd(VALID_END_DATETIME_TUTORIAL).withVenue(VALID_VENUE_TUTORIAL)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered calendarevent list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Scheduler expectedScheduler = new Scheduler(actualModel.getScheduler());
        List<CalendarEvent> expectedFilteredList = new ArrayList<>(actualModel.getFilteredCalendarEventList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedScheduler, actualModel.getScheduler());
            assertEquals(expectedFilteredList, actualModel.getFilteredCalendarEventList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the calendar event at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredCalendarEventList().size());

        CalendarEvent calendarEvent = model.getFilteredCalendarEventList().get(targetIndex.getZeroBased());
        final String[] splitTitle = calendarEvent.getTitle().value.split("\\s+");
        model.updateFilteredCalendarEventList(new TitleContainsKeywordsPredicate(Arrays.asList(splitTitle[0])));

        assertEquals(1, model.getFilteredCalendarEventList().size());
    }

    /**
     * Deletes the first calendar event in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstPerson(Model model) {
        CalendarEvent firstCalendarEvent = model.getFilteredCalendarEventList().get(0);
        model.deleteCalendarEvent(firstCalendarEvent);
        model.commitScheduler();
    }

}
