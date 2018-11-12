package seedu.address.model.calendarevent;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code CalendarEvent}'s {@code Tags} match all of the tags given.
 */
public class TagsPredicate implements Predicate<CalendarEvent> {
    private final Set<String> tags;

    public TagsPredicate(Set<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean test(CalendarEvent calendarEvent) {
        return !hasTags()
                || tags.stream().allMatch(tag -> StringUtil.containsIgnoreCase(calendarEvent.getTagStrings(), tag));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof TagsPredicate // instanceof handles nulls
            && tags.equals(((TagsPredicate) other).tags)); // state check
    }

    /**
     * Returns whether {@code TagsPredicate} has any {@code tags}
     */
    public boolean hasTags() {
        return !tags.isEmpty();
    }

}
