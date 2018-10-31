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
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.calendarevent.FuzzySearchComparator;
import seedu.address.model.calendarevent.TagsPredicate;
import seedu.address.model.calendarevent.TitleContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindEventCommand}.
 */
public class FindEventCommandTest {
    private Model model = new ModelManager(getTypicalScheduler(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalScheduler(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        TitleContainsKeywordsPredicate firstPredicate =
            new TitleContainsKeywordsPredicate(Collections.singletonList("first"));
        TitleContainsKeywordsPredicate secondPredicate =
            new TitleContainsKeywordsPredicate(Collections.singletonList("second"));
        FuzzySearchComparator firstComparator =
            new FuzzySearchComparator(Collections.singletonList("first"));
        FuzzySearchComparator secondComparator =
            new FuzzySearchComparator(Collections.singletonList("second"));
        TagsPredicate tagsPredicate =
            new TagsPredicate(new ArrayList<String>());

        FindEventCommand findFirstCommand = new FindEventCommand(firstPredicate, firstComparator, tagsPredicate);
        FindEventCommand findSecondCommand = new FindEventCommand(secondPredicate, secondComparator, tagsPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindEventCommand findFirstCommandCopy = new FindEventCommand(firstPredicate, firstComparator, tagsPredicate);
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
        TitleContainsKeywordsPredicate predicate = preparePredicate(" ");
        FuzzySearchComparator comparator = prepareComparator(" ");
        TagsPredicate tagsPredicate = prepareTagsPredicate(" ");
        FindEventCommand command = new FindEventCommand(predicate, comparator, tagsPredicate);
        expectedModel.updateFilteredCalendarEventList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredCalendarEventList());
    }

    @Test
    public void execute_multipleKeywords_multipleCalendarEventsFound() {
        String expectedMessage = String.format(MESSAGE_CALENDAR_EVENTS_LISTED_OVERVIEW, 3);
        TitleContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FuzzySearchComparator comparator = prepareComparator("Kurz Elle Kunz");
        TagsPredicate tagsPredicate = prepareTagsPredicate("");
        FindEventCommand command = new FindEventCommand(predicate, comparator, tagsPredicate);
        expectedModel.updateFilteredCalendarEventList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredCalendarEventList());
    }

    /**
     * Parses {@code userInput} into a {@code TitleContainsKeywordsPredicate}.
     */
    private TitleContainsKeywordsPredicate preparePredicate(String userInput) {
        return new TitleContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code FuzzySearchComparator}.
     */
    private FuzzySearchComparator prepareComparator(String userInput) {
        return new FuzzySearchComparator(Arrays.asList(userInput.split("\\s+")));
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
