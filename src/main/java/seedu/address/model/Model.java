package seedu.address.model;

import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.calendarevent.CalendarEvent;
import seedu.address.model.calendarevent.FsList;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<CalendarEvent> PREDICATE_SHOW_ALL_CALENDAR_EVENTS = unused -> true;

    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetData(ReadOnlyScheduler newData);

    /**
     * Returns the Scheduler
     */
    ReadOnlyScheduler getScheduler();

    /**
     * Returns true if a calendar event with the same identity as {@code calendarevent} exists in the scheduler.
     */
    boolean hasCalendarEvent(CalendarEvent calendarEvent);

    /**
     * Deletes the given calendar event.
     * The calendar event must exist in the scheduler.
     */
    void deleteCalendarEvent(CalendarEvent target);

    /**
     * Adds the given calendar event.
     * {@code calendarevent} must not already exist in the scheduler.
     */
    void addCalendarEvent(CalendarEvent calendarEvent);

    /**
     * Replaces the given calendar event {@code target} with {@code editedCalendarEvent}.
     * {@code target} must exist in the scheduler.
     * The calendar event identity of {@code editedCalendarEvent} must not be the same as another existing calendar
     * event in the scheduler.
     */
    void updateCalendarEvent(CalendarEvent target, CalendarEvent editedCalendarEvent);

    /**
     * Returns an unmodifiable view of the full calendar event list
     */
    ObservableList<CalendarEvent> getFullCalendarEventList();

    /**
     * Returns an unmodifiable view of the FsList of calendar events
     */
    ObservableList<CalendarEvent> getFilteredCalendarEventList();

    /**
     * Updates the filter of the filtered calendar event list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCalendarEventList(Predicate<CalendarEvent> predicate);

    /**
     * Filters the {@code FsList} calendar event list by an additional {@code predicate}.
     */
    void addPredicate(Predicate<CalendarEvent> predicate);

    /**
     * Removes all predicates from the {@code FsList} of calendar events.
     */
    void clearAllPredicatesAndComparators();

    /**
     * Return a defensive copy of the {@code FsList}.
     */
    FsList getFsList();

    /**
     * Sorts the filtered calendar event list by the given {@code comparator}.
     *
     * @throws NullPointerException if {@code comparator} is null.
     */
    void sortFilteredCalendarEventList(Comparator<CalendarEvent> comparator);

    /**
     * Returns true if the model has previous scheduler states to restore.
     */
    boolean canUndoScheduler();

    /**
     * Returns true if the model has undone scheduler states to restore.
     */
    boolean canRedoScheduler();

    /**
     * Restores the model's scheduler to its previous state.
     */
    void undoScheduler();

    /**
     * Restores the model's scheduler to its previously undone state.
     */
    void redoScheduler();

    /**
     * Saves the current scheduler state for undo/redo.
     */
    void commitScheduler();
}
