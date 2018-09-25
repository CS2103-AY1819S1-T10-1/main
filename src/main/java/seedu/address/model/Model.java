package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.person.Event;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Event> PREDICATE_SHOW_ALL_EVENTS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlySchedule newData);

    /** Returns the Schedule */
    ReadOnlySchedule getSchedule();

    /**
     * Returns true if a event with the same identity as {@code event} exists in the schedule.
     */
    boolean hasEvent(Event event);

    /**
     * Deletes the given event.
     * The event must exist in the schedule.
     */
    void deleteEvent(Event target);

    /**
     * Adds the given event.
     * {@code event} must not already exist in the schedule.
     */
    void addEvent(Event event);

    /**
     * Replaces the given event {@code target} with {@code editedEvent}.
     * {@code target} must exist in the schedule.
     * The event identity of {@code editedEvent} must not be the same as another existing event in the schedule.
     */
    void updateEvent(Event target, Event editedEvent);

    /** Returns an unmodifiable view of the filtered event list */
    ObservableList<Event> getFilteredEventList();

    /**
     * Updates the filter of the filtered event list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEventList(Predicate<Event> predicate);

    /**
     * Returns true if the model has previous schedule states to restore.
     */
    boolean canUndoSchedule();

    /**
     * Returns true if the model has undone schedule states to restore.
     */
    boolean canRedoSchedule();

    /**
     * Restores the model's schedule to its previous state.
     */
    void undoSchedule();

    /**
     * Restores the model's schedule to its previously undone state.
     */
    void redoSchedule();

    /**
     * Saves the current schedule state for undo/redo.
     */
    void commitSchedule();
}
