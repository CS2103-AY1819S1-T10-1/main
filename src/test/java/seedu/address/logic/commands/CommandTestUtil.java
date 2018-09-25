package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Schedule;
import seedu.address.model.Model;
import seedu.address.model.person.Event;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.testutil.EditEventDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_MIDTERM = "CS2103 Midterm";
    public static final String VALID_NAME_WORKSHOP = "Movie Workshop";
    public static final String VALID_DATE_MIDTERM = "2018.09.25";
    public static final String VALID_DATE_WORKSHOP = "2018-09-24";
    public static final String VALID_TIME_MIDTERM = "9am-10am";
    public static final String VALID_TIME_WORKSHOP = "10am-11am";
    public static final String VALID_VENUE_MIDTERM = "Com2 209";
    public static final String VALID_VENUE_WORKSHOP = "I3 AUD";
    public static final String VALID_TAG_IMPORTANT = "important";
    public static final String VALID_TAG_DIFFICULT = "difficult";

    public static final String NAME_DESC_MIDTERM = " " + PREFIX_NAME + VALID_NAME_MIDTERM;
    public static final String NAME_DESC_WORKSHOP = " " + PREFIX_NAME + VALID_NAME_WORKSHOP;
    public static final String DATE_DESC_MIDTERM = " " + PREFIX_DATE + VALID_DATE_MIDTERM;
    public static final String DATE_DESC_WORKSHOP = " " + PREFIX_DATE + VALID_DATE_WORKSHOP;
    public static final String TIME_DESC_MIDTERM = " " + PREFIX_TIME + VALID_TIME_MIDTERM;
    public static final String TIME_DESC_WORKSHOP = " " + PREFIX_TIME + VALID_TIME_WORKSHOP;
    public static final String VENUE_DESC_MIDTERM = " " + PREFIX_VENUE + VALID_VENUE_MIDTERM;
    public static final String VENUE_DESC_WORKSHOP = " " + PREFIX_VENUE + VALID_VENUE_WORKSHOP;
    public static final String TAG_DESC_DIFFICULT = " " + PREFIX_TAG + VALID_TAG_DIFFICULT;
    public static final String TAG_DESC_IMPORTANT = " " + PREFIX_TAG + VALID_TAG_IMPORTANT;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "Play&"; // '&' not allowed in names
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_TIME_DESC = " " + PREFIX_TIME + "9am"; // missing end time
    public static final String INVALID_VENUE_DESC = " " + PREFIX_VENUE; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditEventDescriptor DESC_MIDTERM;
    public static final EditCommand.EditEventDescriptor DESC_WORKSHOP;

    static {
        DESC_MIDTERM = new EditEventDescriptorBuilder().withName(VALID_NAME_MIDTERM)
                .withDate(VALID_DATE_MIDTERM).withTime(VALID_TIME_MIDTERM).withVenue(VALID_VENUE_MIDTERM)
                .withTags(VALID_TAG_DIFFICULT).build();
        DESC_WORKSHOP = new EditEventDescriptorBuilder().withName(VALID_NAME_WORKSHOP)
                .withDate(VALID_DATE_WORKSHOP).withTime(VALID_TIME_WORKSHOP).withVenue(VALID_VENUE_WORKSHOP)
                .withTags(VALID_TAG_IMPORTANT, VALID_TAG_DIFFICULT).build();
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
     * - the schedule and the filtered event list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Schedule expectedAddressBook = new Schedule(actualModel.getSchedule());
        List<Event> expectedFilteredList = new ArrayList<>(actualModel.getFilteredEventList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getSchedule());
            assertEquals(expectedFilteredList, actualModel.getFilteredEventList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the event at the given {@code targetIndex} in the
     * {@code model}'s schedule.
     */
    public static void showEventAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredEventList().size());

        Event event = model.getFilteredEventList().get(targetIndex.getZeroBased());
        final String[] splitName = event.getName().fullName.split("\\s+");
        model.updateFilteredEventList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredEventList().size());
    }

    /**
     * Deletes the first event in {@code model}'s filtered list from {@code model}'s schedule.
     */
    public static void deleteFirstEvent(Model model) {
        Event firstEvent = model.getFilteredEventList().get(0);
        model.deleteEvent(firstEvent);
        model.commitSchedule();
    }

}
