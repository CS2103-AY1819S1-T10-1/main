package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.todolist.ToDoListEvent;

/**
 * The API of the Model component of todolist.
 */
public interface ModelToDo {

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<ToDoListEvent> PREDICATE_SHOW_ALL_TODOLIST_EVENTS = unused -> true;

    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetData(ReadOnlyToDoList newData);

    /**
     * Returns the ToDoList
     */
    ReadOnlyToDoList getToDoList();

    /**
     * Returns true if a todolist event with the same identity as {@code todolistevent} exists in the todolist.
     */
    boolean hasToDoListEvent(ToDoListEvent toDoListEvent);

    /**
     * Deletes the given todolist event.
     * The todolist event must exist in the todolist.
     */
    void deleteToDoListEvent(ToDoListEvent target);

    /**
     * Adds the given todolist event.
     * {@code todolistevent} must not already exist in the todolist.
     */
    void addToDoListEvent(ToDoListEvent toDoListEvent);

    /**
     * Replaces the given todolist event {@code target} with {@code editedToDoListEvent}.
     * {@code target} must exist in the todolist.
     * The todolist event identity of {@code editedToDoListEvent} must not be the same as another existing todolist
     * event in the scheduler.
     */
    void updateToDoListEvent(ToDoListEvent target, ToDoListEvent editedToDoListEvent);

    /**
     * Returns an unmodifiable view of the filtered todolist event list
     */
    ObservableList<ToDoListEvent> getFilteredToDoListEventList();

    /**
     * Updates the filter of the filtered todolist event list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredToDoListEventList(Predicate<ToDoListEvent> predicate);

    /**
     * Saves the current toDoList state.
     */
    void commitToDoList();
}
