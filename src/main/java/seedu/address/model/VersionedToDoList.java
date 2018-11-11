package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code ToDoList} that keeps track of its own history.
 */
public class VersionedToDoList extends ToDoList {
    private final List<ReadOnlyToDoList> toDoListStateList;
    private int currentStatePointer;

    public VersionedToDoList(ReadOnlyToDoList initialState) {
        super(initialState);

        toDoListStateList = new ArrayList<>();
        toDoListStateList.add(new ToDoList(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code Scheduler} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        toDoListStateList.add(new ToDoList(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        toDoListStateList.subList(currentStatePointer + 1, toDoListStateList.size()).clear();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedToDoList)) {
            return false;
        }

        VersionedToDoList otherVersionedToDoList = (VersionedToDoList) other;

        // state check
        return super.equals(otherVersionedToDoList)
            && toDoListStateList.equals(otherVersionedToDoList.toDoListStateList)
            && currentStatePointer == otherVersionedToDoList.currentStatePointer;
    }
}
