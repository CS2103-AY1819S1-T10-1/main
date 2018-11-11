package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.todolist.ToDoListEvent;
import seedu.address.model.todolist.UniqueToDoEventList;

/**
 * Wraps all data at the toDoList level
 * Duplicates are not allowed (by .isSameCalendarEvent comparison)
 */
public class ToDoList implements ReadOnlyToDoList {

    private final UniqueToDoEventList toDoListEvents;

    {
        toDoListEvents = new UniqueToDoEventList();
    }

    public ToDoList() {
    }

    /**
     * Creates an ToDoList using the ToDoList Events in the {@code toBeCopied}
     */
    public ToDoList(ReadOnlyToDoList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the todolist event list with {@code toDoListEvents}.
     * {@code calendarEvents} must not contain duplicate calendarEvents.
     */
    public void setToDoListEvents(List<ToDoListEvent> toDoListEvents) {
        this.toDoListEvents.setToDoListEvents(toDoListEvents);
    }

    /**
     * Resets the existing data of this {@code ToDoList} with {@code newData}.
     */
    public void resetData(ReadOnlyToDoList newData) {
        requireNonNull(newData);

        setToDoListEvents(newData.getToDoList());
    }

    //// todolistevent-level operations

    /**
     * Returns true if a todolist event with the same identity as {@code toDoListEvent} exists in the ToDoList
     * <p>
     * .
     */
    public boolean hasToDoListEvent(ToDoListEvent toDoListEvent) {
        requireNonNull(toDoListEvent);
        return toDoListEvents.contains(toDoListEvent);
    }

    /**
     * Adds a todolist event to the todolist
     * <p>
     * .
     * The todolist event must not already exist in the todolist
     * <p>
     * .
     */
    public void addToDoListEvent(ToDoListEvent p) {
        toDoListEvents.add(p);
    }

    /**
     * Replaces the given todolist event {@code target} in the list with {@code editedToDoListEvent}.
     * {@code target} must exist in the todolist.
     * The todolist event identity of {@code editedToDoListEvent} must not be the same as another existing
     * todolist event in the todolist
     * <p>
     * .
     */
    public void updateToDoListEvent(ToDoListEvent target, ToDoListEvent editedToDoListEvent) {
        requireNonNull(editedToDoListEvent);

        toDoListEvents.setToDoListEvent(target, editedToDoListEvent);
    }

    /**
     * Removes {@code key} from this {@code ToDoList}.
     * {@code key} must exist in the todolist
     * <p>
     * .
     */
    public void removeToDoListEvent(ToDoListEvent key) {
        toDoListEvents.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return toDoListEvents.asUnmodifiableObservableList().size() + " events";
        // TODO: refine later
    }

    @Override
    public ObservableList<ToDoListEvent> getToDoList() {
        return toDoListEvents.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ToDoList // instanceof handles nulls
            && toDoListEvents.equals(((ToDoList) other).toDoListEvents));
    }

    @Override
    public int hashCode() {
        return toDoListEvents.hashCode();
    }
}
