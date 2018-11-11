package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_CALENDAR_EVENTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.CARL;
import static seedu.address.testutil.TypicalEvents.ELLE;
import static seedu.address.testutil.TypicalEvents.FIONA;
import static seedu.address.testutil.TypicalEvents.getTypicalScheduler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.calendarevent.DatePredicate;
import seedu.address.model.calendarevent.DateTime;
import seedu.address.model.calendarevent.FuzzySearchComparator;
import seedu.address.model.calendarevent.FuzzySearchFilterPredicate;
import seedu.address.model.calendarevent.TagsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindEventCommand}.
 */
public class FindEventCommandTest {
    private Model model = new ModelManager(getTypicalScheduler(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalScheduler(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        FuzzySearchFilterPredicate firstPredicate =
            new FuzzySearchFilterPredicate(Collections.singletonList("first"));
        FuzzySearchFilterPredicate secondPredicate =
            new FuzzySearchFilterPredicate(Collections.singletonList("second"));
        FuzzySearchComparator firstComparator =
            new FuzzySearchComparator(Collections.singletonList("first"));
        FuzzySearchComparator secondComparator =
            new FuzzySearchComparator(Collections.singletonList("second"));
        DatePredicate datePredicate = new DatePredicate(null, null);
        TagsPredicate tagsPredicate = new TagsPredicate(new ArrayList<String>());

        FindEventCommand findFirstCommand =
                new FindEventCommand(firstPredicate, firstComparator, datePredicate, tagsPredicate);
        FindEventCommand findSecondCommand =
                new FindEventCommand(secondPredicate, secondComparator, datePredicate, tagsPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindEventCommand findFirstCommandCopy =
                new FindEventCommand(firstPredicate, firstComparator, datePredicate, tagsPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different calendarevent -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noCalendarEventFound() {
        String expectedMessage = String.format(MESSAGE_CALENDAR_EVENTS_LISTED_OVERVIEW, 0);
        FuzzySearchFilterPredicate predicate = preparePredicate(" ");
        FuzzySearchComparator comparator = prepareComparator(" ");
        DatePredicate datePredicate = prepareDatePredicate(" ", " ");
        TagsPredicate tagsPredicate = prepareTagsPredicate(" ");
        FindEventCommand command = new FindEventCommand(predicate, comparator, datePredicate, tagsPredicate);
        expectedModel.updateFilteredCalendarEventList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredCalendarEventList());
    }

    @Test
    public void execute_multipleKeywords_multipleCalendarEventsFound() {
        String expectedMessage = String.format(MESSAGE_CALENDAR_EVENTS_LISTED_OVERVIEW, 3);
        FuzzySearchFilterPredicate predicate = preparePredicate("Lab Practice Fair");
        FuzzySearchComparator comparator = prepareComparator("Lab Practice Fair");
        DatePredicate datePredicate = prepareDatePredicate(" ", " ");
        TagsPredicate tagsPredicate = prepareTagsPredicate("");
        FindEventCommand command = new FindEventCommand(predicate, comparator, datePredicate, tagsPredicate);
        expectedModel.updateFilteredCalendarEventList(predicate);
        expectedModel.sortFilteredCalendarEventList(comparator); // added because FindEventCommand sorts as well
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredCalendarEventList());
    }

    /**
     * Parses {@code userInput} into a {@code FuzzySearchFilterPredicate}.
     */
    private FuzzySearchFilterPredicate preparePredicate(String userInput) {
        return new FuzzySearchFilterPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code FuzzySearchComparator}.
     */
    private FuzzySearchComparator prepareComparator(String userInput) {
        return new FuzzySearchComparator(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code DatePredicate}.
     */
    private DatePredicate prepareDatePredicate(String userInputFrom, String userInputTo) {
        DateTime dateFrom = null;
        DateTime dateTo = null;
        try {
            if (!userInputFrom.isEmpty()) {
                dateFrom = ParserUtil.parseDateTime(userInputFrom);
            }
            if (!userInputTo.isEmpty()) {
                dateTo = ParserUtil.parseDateTime(userInputTo);
            }
        } catch (ParseException E) {
            return new DatePredicate(null, null);
        }

        return new DatePredicate(dateFrom, dateTo);
    }

    /**
     * Parses {@code userInput} into a {@code TagsPredicate}.
     */
    private TagsPredicate prepareTagsPredicate(String userInput) {
        if (userInput.isEmpty()) {
            return new TagsPredicate(new ArrayList<>());
        } else {
            return new TagsPredicate(Arrays.asList(userInput.split("\\s+")));
        }
    }
}
