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

        // different calendarevent -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        FuzzySearchFilterPredicate predicate = new FuzzySearchFilterPredicate(Collections.singletonList(
            "Alice"));
        assertTrue(predicate.test(new CalendarEventBuilder().withTitle("Alice Bob").build()));

        // Multiple keywords
        predicate = new FuzzySearchFilterPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new CalendarEventBuilder().withTitle("Alice Bob").build()));

        // Only one matching keyword
        predicate = new FuzzySearchFilterPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new CalendarEventBuilder().withTitle("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new FuzzySearchFilterPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new CalendarEventBuilder().withTitle("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        FuzzySearchFilterPredicate predicate = new FuzzySearchFilterPredicate(Collections.emptyList());
        assertFalse(predicate.test(new CalendarEventBuilder().withTitle("Alice").build()));

        // Non-matching keyword
        predicate = new FuzzySearchFilterPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new CalendarEventBuilder().withTitle("Alice Bob").build()));

        // Keywords do not match title, venue or description
        predicate = new FuzzySearchFilterPredicate(Arrays.asList("12345", "boba@email.com", "Main", "Street"));
        assertFalse(predicate.test(new CalendarEventBuilder().withTitle("Lecture").withDescription("Daydream")
            .withVenue("LT19").build()));
    }
}
