package seedu.address.model.todolist;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.ToDoListEventBuilder;

public class TitleToDoContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TitleToDoContainsKeywordsPredicate firstPredicate =
            new TitleToDoContainsKeywordsPredicate(firstPredicateKeywordList);
        TitleToDoContainsKeywordsPredicate secondPredicate =
            new TitleToDoContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TitleToDoContainsKeywordsPredicate firstPredicateCopy =
            new TitleToDoContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different todolist event -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_titleContainsKeywords_returnsTrue() {
        // One keyword
        TitleToDoContainsKeywordsPredicate predicate = new TitleToDoContainsKeywordsPredicate(Collections.singletonList(
            "Alice"));
        assertTrue(predicate.test(new ToDoListEventBuilder().withTitle("Alice Bob").build()));

        // Multiple keywords
        predicate = new TitleToDoContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new ToDoListEventBuilder().withTitle("Alice Bob").build()));

        // Only one matching keyword
        predicate = new TitleToDoContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new ToDoListEventBuilder().withTitle("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new TitleToDoContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new ToDoListEventBuilder().withTitle("Alice Bob").build()));
    }

    @Test
    public void test_titleDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TitleToDoContainsKeywordsPredicate predicate = new TitleToDoContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ToDoListEventBuilder().withTitle("Alice").build()));

        // Non-matching keyword
        predicate = new TitleToDoContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new ToDoListEventBuilder().withTitle("Alice Bob").build()));

        // Keywords match description but does not match name
        predicate = new TitleToDoContainsKeywordsPredicate(Arrays.asList("12345"));
        System.out.printf(predicate.toString());
        assertFalse(predicate.test(new ToDoListEventBuilder().withTitle("Alice").withDescription("12345").build()));
    }
}
