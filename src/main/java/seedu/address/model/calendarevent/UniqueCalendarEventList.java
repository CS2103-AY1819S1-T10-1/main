package seedu.address.model.calendarevent;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.calendarevent.exceptions.CalendarEventNotFoundException;
import seedu.address.model.calendarevent.exceptions.DuplicateCalendarEventException;

/**
 * A list of {@code CalendarEvent}s that enforces uniqueness between its elements and does not allow nulls.
 * A {@code CalendarEvent}s is considered unique by comparing using
 * {@code CalendarEvent#isSameCalendarEvent(CalendarEvent)}.
 * As such, adding and updating of {@code CalendarEvent}s uses CalendarEvent#isSameCalendarEvent(CalendarEvent) for
 * equality so as to ensure that the calendar event being added or updated is unique in terms of identity in the
 * UniqueCalendarEventList.
 * However, the removal of a calendar event uses CalendarEvent#equals(Object) so
 * as to ensure that the calendar event with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see CalendarEvent#isSameCalendarEvent(CalendarEvent)
 */
public class UniqueCalendarEventList implements Iterable<CalendarEvent> {

    private final ObservableList<CalendarEvent> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent calendar event as the given argument.
     */
    public boolean contains(CalendarEvent toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameCalendarEvent);
    }

    /**
     * Adds a calendar event to the list.
     * The calendar event must not already exist in the list.
     */
    public void add(CalendarEvent toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateCalendarEventException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the calendar event {@code target} in the list with {@code editedCalendarEvent}.
     * {@code target} must exist in the list.
     * The calendar event identity of {@code editedCalendarEvent} must not be the same as another existing calendar
     * event in the list.
     */
    public void setCalendarEvent(CalendarEvent target, CalendarEvent editedCalendarEvent) {
        requireAllNonNull(target, editedCalendarEvent);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new CalendarEventNotFoundException();
        }

        if (!target.isSameCalendarEvent(editedCalendarEvent) && contains(editedCalendarEvent)) {
            throw new DuplicateCalendarEventException();
        }

        internalList.set(index, editedCalendarEvent);
    }

    /**
     * Removes the equivalent calendar event from the list.
     * The calendar event must exist in the list.
     */
    public void remove(CalendarEvent toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new CalendarEventNotFoundException();
        }
    }

    public void setCalendarEvents(UniqueCalendarEventList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code calendarEvents}.
     * {@code calendarEvents} must not contain duplicate calendar events.
     */
    public void setCalendarEvents(List<CalendarEvent> calendarEvents) {
        requireAllNonNull(calendarEvents);
        if (!calendarEventsAreUnique(calendarEvents)) {
            throw new DuplicateCalendarEventException();
        }

        internalList.setAll(calendarEvents);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<CalendarEvent> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<CalendarEvent> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UniqueCalendarEventList // instanceof handles nulls
            && internalList.equals(((UniqueCalendarEventList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code calendarEvents} contains only unique calendar events.
     */
    private boolean calendarEventsAreUnique(List<CalendarEvent> calendarEvents) {
        for (int i = 0; i < calendarEvents.size() - 1; i++) {
            for (int j = i + 1; j < calendarEvents.size(); j++) {
                if (calendarEvents.get(i).isSameCalendarEvent(calendarEvents.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
