package seedu.address.model.calendarevent;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code CalendarEvent}'s {@code Title}, {@code Venue} and {@code Description} match any of the
 * keywords given.
 */
public class FuzzySearchFilterPredicate implements Predicate<CalendarEvent> {
    private final int tolerance = 70;
    private final List<String> keywords;

    public FuzzySearchFilterPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(CalendarEvent calendarEvent) {
        return keywords.stream()
            .anyMatch(keyword -> StringUtil.containsWordFuzzy(calendarEvent.getTitle().value, keyword, tolerance)
                                || StringUtil.containsWordFuzzy(calendarEvent.getDescription(), keyword, tolerance)
                                || StringUtil.containsWordFuzzy(calendarEvent.getVenue().value, keyword, tolerance));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof FuzzySearchFilterPredicate // instanceof handles nulls
            && keywords.equals(((FuzzySearchFilterPredicate) other).keywords)); // state check
    }

}
