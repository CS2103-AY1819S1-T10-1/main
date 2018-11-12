package seedu.address.model.calendarevent;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.CalendarEventBuilder;

public class FuzzySearchFilterPredicateTest {

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
    public void test_nameContainsKeywords_returnsTrue() {
        // Matching Title:
        // One keyword
        FuzzySearchFilterPredicate predicate = new FuzzySearchFilterPredicate(Collections.singletonList("Lecture"));
        assertTrue(predicate.test(new CalendarEventBuilder().withTitle("CS2103 Lecture").build()));

        // Multiple keywords
        predicate = new FuzzySearchFilterPredicate(Arrays.asList("CS2103", "Lecture"));
        assertTrue(predicate.test(new CalendarEventBuilder().withTitle("CS2103 Lecture").build()));

        // One keyword
        predicate = new FuzzySearchFilterPredicate(Collections.singletonList("CS2103"));
        assertTrue(predicate.test(new CalendarEventBuilder().withVenue("CS2103 Lecture").build()));

        // Only one matching keyword
        predicate = new FuzzySearchFilterPredicate(Arrays.asList("MA1101", "Lecture"));
        assertTrue(predicate.test(new CalendarEventBuilder().withTitle("CS2103 Lecture").build()));

        // Mixed-case keywords
        predicate = new FuzzySearchFilterPredicate(Arrays.asList("cs2103", "lECtuRe"));
        assertTrue(predicate.test(new CalendarEventBuilder().withTitle("CS2103 Lecture").build()));


        // Matching Venue:
        // One keyword
        predicate = new FuzzySearchFilterPredicate(Collections.singletonList("COM2"));
        assertTrue(predicate.test(new CalendarEventBuilder().withVenue("COM2").build()));

        // Multiple keywords
        predicate = new FuzzySearchFilterPredicate(Arrays.asList("COM2", "02-10"));
        assertTrue(predicate.test(new CalendarEventBuilder().withVenue("COM2 02-10").build()));

        // One keyword
        predicate = new FuzzySearchFilterPredicate(Collections.singletonList("COM2"));
        assertTrue(predicate.test(new CalendarEventBuilder().withVenue("COM2 02-10").build()));

        // Only one matching keyword
        predicate = new FuzzySearchFilterPredicate(Arrays.asList("COM2", "10-05"));
        assertTrue(predicate.test(new CalendarEventBuilder().withVenue("COM2 02-10").build()));

        // Mixed-case keywords
        predicate = new FuzzySearchFilterPredicate(Arrays.asList("cOm2"));
        assertTrue(predicate.test(new CalendarEventBuilder().withVenue("cOm2 02-10").build()));


        // Matching Description:
        // One keyword
        predicate = new FuzzySearchFilterPredicate(Collections.singletonList("Demo"));
        assertTrue(predicate.test(new CalendarEventBuilder().withDescription("Demo").build()));

        // Multiple keywords
        predicate = new FuzzySearchFilterPredicate(Arrays.asList("App", "Demo"));
        assertTrue(predicate.test(new CalendarEventBuilder().withDescription("App Demo").build()));

        // One keyword
        predicate = new FuzzySearchFilterPredicate(Collections.singletonList("Demo"));
        assertTrue(predicate.test(new CalendarEventBuilder().withDescription("App Demo").build()));

        // Only one matching keyword
        predicate = new FuzzySearchFilterPredicate(Arrays.asList("Program", "Demo"));
        assertTrue(predicate.test(new CalendarEventBuilder().withDescription("App Demo").build()));

        // Mixed-case keywords
        predicate = new FuzzySearchFilterPredicate(Arrays.asList("aPp DEmO"));
        assertTrue(predicate.test(new CalendarEventBuilder().withDescription("App Demo").build()));


        CalendarEvent testEvent = new CalendarEventBuilder().withTitle("CS2103 Tutorial")
                                        .withVenue("COM2 02-10").withDescription("App Demo").build();
        // Matches Title, but not Venue or Description
        predicate = new FuzzySearchFilterPredicate(Arrays.asList("CS2103", "Tutorial"));
        assertTrue(predicate.test(testEvent));

        // Matches Venue, but not Title or Description
        predicate = new FuzzySearchFilterPredicate(Arrays.asList("COM2", "02-10"));
        assertTrue(predicate.test(testEvent));

        // Matches Description, but not Title or Venue
        predicate = new FuzzySearchFilterPredicate(Arrays.asList("App", "Demo"));
        assertTrue(predicate.test(testEvent));

        // Zero keywords -> always matches
        predicate = new FuzzySearchFilterPredicate(Collections.emptyList());
        assertTrue(predicate.test(testEvent));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Keywords do not match title, venue or description
        FuzzySearchFilterPredicate predicate = new FuzzySearchFilterPredicate(Arrays.asList("Random", "Keywords"));
        assertFalse(predicate.test(new CalendarEventBuilder().withTitle("Lecture").withDescription("Daydream")
            .withVenue("LT19").build()));
    }
}
