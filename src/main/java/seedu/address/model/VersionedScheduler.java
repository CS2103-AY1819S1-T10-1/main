package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code Scheduler} that keeps track of its own history.
 */
public class VersionedScheduler extends Scheduler {

    private final List<ReadOnlyScheduler> schedulerStateList;
    private int currentStatePointer;

    public VersionedScheduler(ReadOnlyScheduler initialState) {
        super(initialState);

        schedulerStateList = new ArrayList<>();
        schedulerStateList.add(new Scheduler(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code Scheduler} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        schedulerStateList.add(new Scheduler(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        schedulerStateList.subList(currentStatePointer + 1, schedulerStateList.size()).clear();
    }

    /**
     * Restores the scheduler to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(schedulerStateList.get(currentStatePointer));
    }

    /**
     * Restores the scheduler to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(schedulerStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has scheduler states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has scheduler states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < schedulerStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedScheduler)) {
            return false;
        }

        VersionedScheduler otherVersionedScheduler = (VersionedScheduler) other;

        // state check
        return super.equals(otherVersionedScheduler)
                && schedulerStateList.equals(otherVersionedScheduler.schedulerStateList)
                && currentStatePointer == otherVersionedScheduler.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of schedulerState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of schedulerState list, unable to redo.");
        }
    }
}
