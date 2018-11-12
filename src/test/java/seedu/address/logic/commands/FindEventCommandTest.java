package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_CALENDAR_EVENTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.CAREER_FAIR;
import static seedu.address.testutil.TypicalEvents.CHOIR_PRACTICE;
import static seedu.address.testutil.TypicalEvents.CS2040_LAB;
import static seedu.address.testutil.TypicalEvents.CS2103_LECTURE;
import static seedu.address.testutil.TypicalEvents.CS2104_TUTORIAL;
import static seedu.address.testutil.TypicalEvents.FIN3101_SEMINAR;
import static seedu.address.testutil.TypicalEvents.GOOGLE_INTERVIEW;
import static seedu.address.testutil.TypicalEvents.getTypicalScheduler;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

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
        DatePredicate firstDatePredicate =
            new DatePredicate(new DateTime("2018-11-10 20:00"), new DateTime("2018-11-10 22:00"));
        DatePredicate secondDatePredicate =
            new DatePredicate(new DateTime("2018-11-11 20:00"), new DateTime("2018-11-11 22:00"));
        TagsPredicate firstTagsPredicate = new TagsPredicate(new HashSet<>(Arrays.asList("tag1", "tag2")));
        TagsPredicate secondTagsPredicate = new TagsPredicate(new HashSet<>(Arrays.asList("tag3", "tag4")));

        FindEventCommand findFirstCommand =
                new FindEventCommand(firstPredicate, firstComparator, firstDatePredicate, firstTagsPredicate);
        FindEventCommand findSecondCommand =
                new FindEventCommand(secondPredicate, secondComparator, secondDatePredicate, secondTagsPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindEventCommand findFirstCommandCopy =
                new FindEventCommand(firstPredicate, firstComparator, firstDatePredicate, firstTagsPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different find event command -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_onlyKeywords_multipleCalendarEventsFound() {
        String expectedMessage = String.format(MESSAGE_CALENDAR_EVENTS_LISTED_OVERVIEW, 3);
        FuzzySearchFilterPredicate predicate = preparePredicate("Lab Practice Fair");
        FuzzySearchComparator comparator = prepareComparator("Lab Practice Fair");
        DatePredicate datePredicate = prepareDatePredicate("", "");
        TagsPredicate tagsPredicate = prepareTagsPredicate("");
        FindEventCommand command = new FindEventCommand(predicate, comparator, datePredicate, tagsPredicate);
        expectedModel.updateFilteredCalendarEventList(predicate, datePredicate, tagsPredicate);
        expectedModel.sortFilteredCalendarEventList(comparator);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CS2040_LAB, CHOIR_PRACTICE, CAREER_FAIR),
                                model.getFilteredAndSortedCalendarEventList());
    }

    @Test
    public void execute_onlyDates_multipleCalendarEventsFound() {
        String expectedMessage = String.format(MESSAGE_CALENDAR_EVENTS_LISTED_OVERVIEW, 3);
        FuzzySearchFilterPredicate predicate = preparePredicate("");
        FuzzySearchComparator comparator = prepareComparator("");
        DatePredicate datePredicate = prepareDatePredicate("16 nov 2018 0800", "");
        TagsPredicate tagsPredicate = prepareTagsPredicate("");
        FindEventCommand command = new FindEventCommand(predicate, comparator, datePredicate, tagsPredicate);
        expectedModel.updateFilteredCalendarEventList(predicate, datePredicate, tagsPredicate);
        expectedModel.sortFilteredCalendarEventList(comparator);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(FIN3101_SEMINAR, CHOIR_PRACTICE, CAREER_FAIR),
                                model.getFilteredAndSortedCalendarEventList());
    }

    @Test
    public void execute_onlyTag_singleCalendarEventFound() {
        String expectedMessage = String.format(MESSAGE_CALENDAR_EVENTS_LISTED_OVERVIEW, 1);
        FuzzySearchFilterPredicate predicate = preparePredicate("");
        FuzzySearchComparator comparator = prepareComparator("");
        DatePredicate datePredicate = prepareDatePredicate("", "");
        TagsPredicate tagsPredicate = prepareTagsPredicate("Lecture");
        FindEventCommand command = new FindEventCommand(predicate, comparator, datePredicate, tagsPredicate);
        expectedModel.updateFilteredCalendarEventList(predicate, datePredicate, tagsPredicate);
        expectedModel.sortFilteredCalendarEventList(comparator);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CS2103_LECTURE), model.getFilteredAndSortedCalendarEventList());
    }

    @Test
    public void execute_keywordsAndDates_multipleCalendarEventFound() {
        String expectedMessage = String.format(MESSAGE_CALENDAR_EVENTS_LISTED_OVERVIEW, 2);
        FuzzySearchFilterPredicate predicate = preparePredicate("lecture Tutorial lab");
        FuzzySearchComparator comparator = prepareComparator("lecture Tutorial lab");
        DatePredicate datePredicate = prepareDatePredicate("", "14 nov 2018 9am");
        TagsPredicate tagsPredicate = prepareTagsPredicate("");
        FindEventCommand command = new FindEventCommand(predicate, comparator, datePredicate, tagsPredicate);
        expectedModel.updateFilteredCalendarEventList(predicate, datePredicate, tagsPredicate);
        expectedModel.sortFilteredCalendarEventList(comparator);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CS2104_TUTORIAL, CS2040_LAB), model.getFilteredAndSortedCalendarEventList());
    }

    @Test
    public void execute_keywordsAndTag_singleCalendarEventFound() {
        String expectedMessage = String.format(MESSAGE_CALENDAR_EVENTS_LISTED_OVERVIEW, 1);
        FuzzySearchFilterPredicate predicate = preparePredicate("CS2104");
        FuzzySearchComparator comparator = prepareComparator("CS2104");
        DatePredicate datePredicate = prepareDatePredicate("", "");
        TagsPredicate tagsPredicate = prepareTagsPredicate("tutorial");
        FindEventCommand command = new FindEventCommand(predicate, comparator, datePredicate, tagsPredicate);
        expectedModel.updateFilteredCalendarEventList(predicate, datePredicate, tagsPredicate);
        expectedModel.sortFilteredCalendarEventList(comparator);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CS2104_TUTORIAL), model.getFilteredAndSortedCalendarEventList());
    }

    @Test
    public void execute_keywordsDatesAndTag_singleCalendarEventFound() {
        String expectedMessage = String.format(MESSAGE_CALENDAR_EVENTS_LISTED_OVERVIEW, 1);
        FuzzySearchFilterPredicate predicate = preparePredicate("google");
        FuzzySearchComparator comparator = prepareComparator("google");
        DatePredicate datePredicate = prepareDatePredicate("11 nov 18 5pm", "18 nov 18 5pm");
        TagsPredicate tagsPredicate = prepareTagsPredicate("interview");
        FindEventCommand command = new FindEventCommand(predicate, comparator, datePredicate, tagsPredicate);
        expectedModel.updateFilteredCalendarEventList(predicate, datePredicate, tagsPredicate);
        expectedModel.sortFilteredCalendarEventList(comparator);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(GOOGLE_INTERVIEW), model.getFilteredAndSortedCalendarEventList());
    }

    /**
     * Parses {@code userInput} into a {@code FuzzySearchFilterPredicate}.
     */
    private FuzzySearchFilterPredicate preparePredicate(String userInput) {
        if (!userInput.isEmpty()) {
            return new FuzzySearchFilterPredicate(Arrays.asList(userInput.split("\\s+")));
        } else {
            return new FuzzySearchFilterPredicate(Collections.emptyList());
        }
    }

    /**
     * Parses {@code userInput} into a {@code FuzzySearchComparator}.
     */
    private FuzzySearchComparator prepareComparator(String userInput) {
        if (!userInput.isEmpty()) {
            return new FuzzySearchComparator(Arrays.asList(userInput.split("\\s+")));
        } else {
            return new FuzzySearchComparator(Collections.emptyList());
        }
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
        userInput = userInput.trim();
        if (userInput.isEmpty()) {
            return new TagsPredicate(Collections.emptySet());
        } else {
            return new TagsPredicate(new HashSet<>(Arrays.asList(userInput.split("\\s+"))));
        }
    }
}
