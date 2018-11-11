package seedu.address.model.todolist;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ToDoListEvent}'s {@code Title} matches any of the keywords given.
 */
public class TitleToDoContainsKeywordsPredicate implements Predicate<ToDoListEvent> {
    private final int tolerance = 70;
    private final List<String> keywords;

    public TitleToDoContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ToDoListEvent toDoListEvent) {
        return keywords.stream()
            .anyMatch(keyword -> StringUtil.containsWordFuzzy(toDoListEvent.getTitle().value, keyword, tolerance));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof TitleToDoContainsKeywordsPredicate // instanceof handles nulls
            && keywords.equals(((TitleToDoContainsKeywordsPredicate) other).keywords)); // state check
    }

}
