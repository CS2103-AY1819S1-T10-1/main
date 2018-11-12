package systemtests;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.calendarevent.CalendarEvent;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<CalendarEvent> PREDICATE_MATCHING_NO_PERSONS = unused -> false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredAndSortedList(Model model, List<CalendarEvent> toDisplay) {
        Optional<Predicate<CalendarEvent>> predicate =
            toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredCalendarEventList(predicate.orElse(PREDICATE_MATCHING_NO_PERSONS));
        model.sortFilteredCalendarEventList(Comparator.comparing(toDisplay::indexOf));
    }

    /**
     * @see ModelHelper#setFilteredAndSortedList(Model, List)
     */
    public static void setFilteredAndSortedList(Model model, CalendarEvent... toDisplay) {
        setFilteredAndSortedList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code CalendarEvent} equals to {@code other}.
     */
    private static Predicate<CalendarEvent> getPredicateMatching(CalendarEvent other) {
        return person -> person.equals(other);
    }
}
