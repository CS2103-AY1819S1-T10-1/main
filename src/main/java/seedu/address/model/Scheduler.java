package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.calendarEvent.CalendarEvent;
import seedu.address.model.calendarEvent.UniqueCalendarEventList;

/**
 * Wraps all data at the scheduler level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class Scheduler implements ReadOnlyScheduler {

    private final UniqueCalendarEventList calendarEvents;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        calendarEvents = new UniqueCalendarEventList();
    }

    public Scheduler() {}

    /**
     * Creates an Scheduler using the Persons in the {@code toBeCopied}
     */
    public Scheduler(ReadOnlyScheduler toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the calendarEvent list with {@code calendarEvents}.
     * {@code calendarEvents} must not contain duplicate calendarEvents.
     */
    public void setCalendarEvents(List<CalendarEvent> calendarEvents) {
        this.calendarEvents.setCalendarEvents(calendarEvents);
    }

    /**
     * Resets the existing data of this {@code Scheduler} with {@code newData}.
     */
    public void resetData(ReadOnlyScheduler newData) {
        requireNonNull(newData);

        setCalendarEvents(newData.getCalendarEventList());
    }

    //// calendarEvent-level operations

    /**
     * Returns true if a calendarEvent with the same identity as {@code calendarEvent} exists in the scheduler
     *
     * .
     */
    public boolean hasCalendarEvent(CalendarEvent calendarEvent) {
        requireNonNull(calendarEvent);
        return calendarEvents.contains(calendarEvent);
    }

    /**
     * Adds a calendarEvent to the scheduler
     *
     * .
     * The calendarEvent must not already exist in the scheduler
     *
     * .
     */
    public void addCalendarEvent(CalendarEvent p) {
        calendarEvents.add(p);
    }

    /**
     * Replaces the given calendarEvent {@code target} in the list with {@code editedCalendarEvent}.
     * {@code target} must exist in the scheduler.
     * The calendarEvent identity of {@code editedCalendarEvent} must not be the same as another existing calendarEvent in the scheduler
     *
     * .
     */
    public void updateCalendarEvent(CalendarEvent target, CalendarEvent editedCalendarEvent) {
        requireNonNull(editedCalendarEvent);

        calendarEvents.setCalendarEvent(target, editedCalendarEvent);
    }

    /**
     * Removes {@code key} from this {@code Scheduler}.
     * {@code key} must exist in the scheduler
     *
     * .
     */
    public void removeCalendarEvent(CalendarEvent key) {
        calendarEvents.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return calendarEvents.asUnmodifiableObservableList().size() + " events";
        // TODO: refine later
    }

    @Override
    public ObservableList<CalendarEvent> getCalendarEventList() {
        return calendarEvents.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Scheduler // instanceof handles nulls
                && calendarEvents.equals(((Scheduler) other).calendarEvents));
    }

    @Override
    public int hashCode() {
        return calendarEvents.hashCode();
    }
}
