package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.ToDoListChangedEvent;
import seedu.address.model.todolist.ToDoListEvent;

/**
 * Represents the in-memory model of the todolist data.
 */
public class ModelManagerToDo extends ComponentManager implements ModelToDo {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedToDoList versionedToDoList;
    private final FilteredList<ToDoListEvent> filteredToDoListEvents;

    /**
     * Initializes a ModelManagerToDo with the given todolist and userPrefs.
     * todo: remove the userPrefs from this class (since it is completely unused)
     */
    public ModelManagerToDo(ReadOnlyToDoList toDoList, UserPrefs userPrefs) {
        super();
        requireAllNonNull(toDoList, userPrefs);

        logger.fine("Initializing with scheduler: " + toDoList + " and user prefs " + userPrefs);

        versionedToDoList = new VersionedToDoList(toDoList);
        filteredToDoListEvents = new FilteredList<>(versionedToDoList.getToDoList());
    }

    public ModelManagerToDo() {
        this(new ToDoList(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyToDoList newData) {
        versionedToDoList.resetData(newData);
        indicateToDoListChanged();
    }

    @Override
    public ReadOnlyToDoList getToDoList() {
        return versionedToDoList;
    }

    /**
     * Raises an event to indicate the modelToDo has changed
     */
    private void indicateToDoListChanged() {
        raise(new ToDoListChangedEvent(versionedToDoList));
    }

    @Override
    public boolean hasToDoListEvent(ToDoListEvent toDoListEvent) {
        requireNonNull(toDoListEvent);
        return versionedToDoList.hasToDoListEvent(toDoListEvent);
    }

    @Override
    public void deleteToDoListEvent(ToDoListEvent target) {
        versionedToDoList.removeToDoListEvent(target);
        indicateToDoListChanged();
    }

    @Override
    public void addToDoListEvent(ToDoListEvent toDoListEvent) {
        versionedToDoList.addToDoListEvent(toDoListEvent);
        updateFilteredToDoListEventList(PREDICATE_SHOW_ALL_TODOLIST_EVENTS);
        indicateToDoListChanged();
    }

    @Override
    public void updateToDoListEvent(ToDoListEvent target, ToDoListEvent editedToDoListEvent) {
        requireAllNonNull(target, editedToDoListEvent);

        versionedToDoList.updateToDoListEvent(target, editedToDoListEvent);
        indicateToDoListChanged();
    }

    //=========== Filtered CalendarEvent List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code CalendarEvent} backed by the internal list of
     * {@code versionedScheduler}
     */
    @Override
    public ObservableList<ToDoListEvent> getFilteredToDoListEventList() {
        return FXCollections.unmodifiableObservableList(filteredToDoListEvents);
    }

    @Override
    public void updateFilteredToDoListEventList(Predicate<ToDoListEvent> predicate) {
        requireNonNull(predicate);
        filteredToDoListEvents.setPredicate(predicate);
    }

    @Override
    public void commitToDoList() {
        versionedToDoList.commit();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManagerToDo)) {
            return false;
        }

        // state check
        ModelManagerToDo other = (ModelManagerToDo) obj;
        return versionedToDoList.equals(other.versionedToDoList)
            && filteredToDoListEvents.equals(other.filteredToDoListEvents);
    }

}
