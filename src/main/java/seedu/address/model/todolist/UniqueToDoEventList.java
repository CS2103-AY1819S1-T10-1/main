package seedu.address.model.todolist;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.todolist.exceptions.DuplicateToDoListEventException;
import seedu.address.model.todolist.exceptions.ToDoListEventNotFoundException;

/**
 * A list of todoEvents that enforces uniqueness between its elements and does not allow nulls.
 * A todoListEvent is considered unique by comparing using {@code ToDoListEvent#isSameToDoListEvent(ToDoListEvent)}.
 * As such, adding and updating of todoListEvents uses ToDoListEvent#isSameToDoListEvent(ToDoListEvent)
 * for equality so as to ensure that the todolist event being added or updated is unique
 * in terms of identity in the UniqueToDoEventList.
 * However, the removal of a todolist event uses ToDoListEvent#equals(Object) so
 * as to ensure that the todolist event with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see ToDoListEvent#isSameToDoListEvent(ToDoListEvent)
 */
public class UniqueToDoEventList implements Iterable<ToDoListEvent> {
    private final ObservableList<ToDoListEvent> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent todolist event as the given argument.
     */
    public boolean contains(ToDoListEvent toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameToDoListEvent);
    }

    /**
     * Adds a todolist event to the list.
     * The todolist event must not already exist in the list.
     */
    public void add(ToDoListEvent toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateToDoListEventException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the todolist event {@code target} in the list with {@code editedToDoListEvent}.
     * {@code target} must exist in the list.
     * The todolist event identity of {@code editedToDoListEvent} must not be the same as another existing
     * todolist event in the list.
     */
    public void setToDoListEvent(ToDoListEvent target, ToDoListEvent editedToDoListEvent) {
        requireAllNonNull(target, editedToDoListEvent);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ToDoListEventNotFoundException();
        }

        if (!target.isSameToDoListEvent(editedToDoListEvent) && contains(editedToDoListEvent)) {
            throw new DuplicateToDoListEventException();
        }

        internalList.set(index, editedToDoListEvent);
    }

    /**
     * Removes the equivalent todolist event from the list.
     * The todolist event must exist in the list.
     */
    public void remove(ToDoListEvent toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ToDoListEventNotFoundException();
        }
    }

    public void setToDoListEvents(UniqueToDoEventList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code toDoListEvents}.
     * {@code toDoListEvents} must not contain duplicate todolist events.
     */
    public void setToDoListEvents(List<ToDoListEvent> toDoListEvents) {
        requireAllNonNull(toDoListEvents);
        if (!toDoListEventsAreUnique(toDoListEvents)) {
            throw new DuplicateToDoListEventException();
        }

        internalList.setAll(toDoListEvents);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ToDoListEvent> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<ToDoListEvent> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UniqueToDoEventList // instanceof handles nulls
            && internalList.equals(((UniqueToDoEventList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code calendarEvents} contains only unique todolist events.
     */
    private boolean toDoListEventsAreUnique(List<ToDoListEvent> toDoListEvents) {
        for (int i = 0; i < toDoListEvents.size() - 1; i++) {
            for (int j = i + 1; j < toDoListEvents.size(); j++) {
                if (toDoListEvents.get(i).isSameToDoListEvent(toDoListEvents.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
