package seedu.address.model.calendarevent;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.CalendarEventBuilder;

public class FuzzySearchComparatorTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        FuzzySearchFilterPredicate firstPredicate = new FuzzySearchFilterPredicate(firstPredicateKeywordList);
        FuzzySearchFilterPredicate secondPredicate = new FuzzySearchFilterPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FuzzySearchFilterPredicate firstPredicateCopy =
                new FuzzySearchFilterPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keyword list -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_compare() {
        CalendarEvent testEvent1 = new CalendarEventBuilder().withTitle("CS2103 Tutorial")
                .withVenue("COM2 02-10").withDescription("App Demo").build();
        CalendarEvent testEvent2 = new CalendarEventBuilder().withTitle("CS2104 Lecture")
                .withVenue("COM2 02-10").withDescription("Monadic Parsers").build();
        CalendarEvent testEvent3 = new CalendarEventBuilder().withTitle("CS2020 Lecture")
                .withVenue("LT19").withDescription("Daydream").build();
        CalendarEvent testEvent4 = new CalendarEventBuilder().withTitle("Some Other Lecture")
                .withVenue("LT17").withDescription("Natural Language Parsing").build();

        // No keywords -> always equal
        FuzzySearchComparator comparator = new FuzzySearchComparator(Collections.emptyList());
        assertTrue(comparator.compare(testEvent1, testEvent2) == 0);

        // Keyword perfectly matching both events -> equal
        comparator = new FuzzySearchComparator(Arrays.asList("COM2", "02-10"));
        assertTrue(comparator.compare(testEvent1, testEvent2) == 0);

        // Keyword perfectly matching first event and not matching second event -> 1st event smaller
        comparator = new FuzzySearchComparator(Collections.singletonList("Tutorial"));
        assertTrue(comparator.compare(testEvent1, testEvent3) < 0);

        // Keyword matching both events, but first event has a closer match -> 1st event smaller
        comparator = new FuzzySearchComparator(Collections.singletonList("parse"));
        assertTrue(comparator.compare(testEvent2, testEvent4) < 0);
    }
}
