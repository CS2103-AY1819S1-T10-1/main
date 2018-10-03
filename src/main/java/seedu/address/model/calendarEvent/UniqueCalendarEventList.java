package seedu.address.model.calendarEvent;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.calendarEvent.exceptions.DuplicatePersonException;
import seedu.address.model.calendarEvent.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A calendarEvent is considered unique by comparing using {@code CalendarEvent#isSamePerson(CalendarEvent)}. As such, adding and updating of
 * persons uses CalendarEvent#isSamePerson(CalendarEvent) for equality so as to ensure that the calendarEvent being added or updated is
 * unique in terms of identity in the UniqueCalendarEventList. However, the removal of a calendarEvent uses CalendarEvent#equals(Object) so
 * as to ensure that the calendarEvent with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see CalendarEvent#isSamePerson(CalendarEvent)
 */
public class UniqueCalendarEventList implements Iterable<CalendarEvent> {

    private final ObservableList<CalendarEvent> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent calendarEvent as the given argument.
     */
    public boolean contains(CalendarEvent toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a calendarEvent to the list.
     * The calendarEvent must not already exist in the list.
     */
    public void add(CalendarEvent toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the calendarEvent {@code target} in the list with {@code editedCalendarEvent}.
     * {@code target} must exist in the list.
     * The calendarEvent identity of {@code editedCalendarEvent} must not be the same as another existing calendarEvent in the list.
     */
    public void setCalendarEvent(CalendarEvent target, CalendarEvent editedCalendarEvent) {
        requireAllNonNull(target, editedCalendarEvent);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSamePerson(editedCalendarEvent) && contains(editedCalendarEvent)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedCalendarEvent);
    }

    /**
     * Removes the equivalent calendarEvent from the list.
     * The calendarEvent must exist in the list.
     */
    public void remove(CalendarEvent toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setCalendarEvents(UniqueCalendarEventList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code calendarEvents}.
     * {@code calendarEvents} must not contain duplicate calendarEvents.
     */
    public void setCalendarEvents(List<CalendarEvent> calendarEvents) {
        requireAllNonNull(calendarEvents);
        if (!personsAreUnique(calendarEvents)) {
            throw new DuplicatePersonException();
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
     * Returns true if {@code calendarEvents} contains only unique calendarEvents.
     */
    private boolean personsAreUnique(List<CalendarEvent> calendarEvents) {
        for (int i = 0; i < calendarEvents.size() - 1; i++) {
            for (int j = i + 1; j < calendarEvents.size(); j++) {
                if (calendarEvents.get(i).isSamePerson(calendarEvents.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
