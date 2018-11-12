package seedu.address.model.calendarevent;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.testutil.CalendarEventBuilder;

public class TagsPredicateTest {

    @Test
    public void equals() {
        Set<String> firstTagsSet = new HashSet<>(Arrays.asList("first"));
        Set<String> secondTagsSet = new HashSet<>(Arrays.asList("first", "second"));

        TagsPredicate firstPredicate = new TagsPredicate(firstTagsSet);
        TagsPredicate secondPredicate = new TagsPredicate(secondTagsSet);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagsPredicate firstPredicateCopy = new TagsPredicate(firstTagsSet);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different tag list -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_hasTags_returnsTrue() {
        // Single tag in list, matches event with only that tag
        TagsPredicate predicate = new TagsPredicate(new HashSet<>(Collections.singletonList("CS2103")));
        assertTrue(predicate.test(new CalendarEventBuilder().withTags("CS2103").build()));

        // Multiple tags in list, matches event with all of the tags
        predicate = new TagsPredicate(new HashSet<>(Arrays.asList("CS2103", "Lecture", "Difficult")));
        assertTrue(predicate.test(new CalendarEventBuilder().withTags("CS2103", "Lecture", "Difficult").build()));

        // Multiple tags in list, matches event with all of the tags in different order
        predicate = new TagsPredicate(new HashSet<>(Arrays.asList("CS2103", "Lecture", "Difficult")));
        assertTrue(predicate.test(new CalendarEventBuilder().withTags("Lecture", "CS2103", "Difficult").build()));

        // Single tag in list, matches event which has multiple tags including the given tag
        predicate = new TagsPredicate(new HashSet<>(Arrays.asList("CS2103")));
        assertTrue(predicate.test(new CalendarEventBuilder().withTags("CS2103", "Lecture").build()));

        // Multiple mixed-case tags in list, matches event with all of the tags in different case
        predicate = new TagsPredicate(new HashSet<>(Arrays.asList("cs2103", "leCTurE")));
        assertTrue(predicate.test(new CalendarEventBuilder().withTags("CS2103", "Lecture").build()));

        // Zero tags in list -> always matches all events
        predicate = new TagsPredicate(Collections.emptySet());
        assertTrue(predicate.test(new CalendarEventBuilder().withTags("CS2103").build()));
    }

    @Test
    public void test_doesNotHaveTags_returnsFalse() {
        // Single tag in list, does not match event with only 1 different tag
        TagsPredicate predicate = new TagsPredicate(new HashSet<>(Collections.singletonList("CS2014")));
        assertFalse(predicate.test(new CalendarEventBuilder().withTags("CS2103").build()));

        // Multiple tags in list, does not match event which does not have 1 of the tags
        predicate = new TagsPredicate(new HashSet<>(Arrays.asList("CS2014", "Lecture")));
        assertFalse(predicate.test(new CalendarEventBuilder().withTags("CS2103", "Lecture").build()));
    }
}
