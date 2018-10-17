package seedu.address.model.calendarevent;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code CalendarEvent}'s {@code Title} matches any of the keywords given.
 */
public class TitleContainsKeywordsPredicate implements Predicate<CalendarEvent> {
    private final List<String> keywords;

    public TitleContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(CalendarEvent calendarEvent) {
        return keywords.stream()
            .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(calendarEvent.getTitle().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof TitleContainsKeywordsPredicate // instanceof handles nulls
            && keywords.equals(((TitleContainsKeywordsPredicate) other).keywords)); // state check
    }

}
