package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.SchedulerChangedEvent;
import seedu.address.model.calendarevent.CalendarEvent;
import seedu.address.model.calendarevent.FsList;

/**
 * Represents the in-memory model of the scheduler data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedScheduler versionedScheduler;
    private FsList fsList;

    /**
     * Initializes a ModelManager with the given scheduler and userPrefs.
     */
    public ModelManager(ReadOnlyScheduler scheduler, UserPrefs userPrefs) {
        super();
        requireAllNonNull(scheduler, userPrefs);

        logger.fine("Initializing with scheduler: " + scheduler + " and user prefs " + userPrefs);

        versionedScheduler = new VersionedScheduler(scheduler);
        fsList = new FsList(versionedScheduler.getCalendarEventList());
    }

    /**
     * Initializes a ModelManager with the given scheduler, userPrefs, and fsList
     */
    public ModelManager(ReadOnlyScheduler scheduler, UserPrefs userPrefs, FsList fsList) {
        super();
        requireAllNonNull(scheduler, userPrefs);

        logger.fine("Initializing with scheduler: " + scheduler + " and user prefs " + userPrefs);

        versionedScheduler = new VersionedScheduler(scheduler);
        this.fsList = fsList;
    }

    public ModelManager() {
        this(new Scheduler(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyScheduler newData) {
        versionedScheduler.resetData(newData);
        indicateSchedulerChanged();
    }

    @Override
    public ReadOnlyScheduler getScheduler() {
        return versionedScheduler;
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateSchedulerChanged() {
        raise(new SchedulerChangedEvent(versionedScheduler));
    }

    @Override
    public boolean hasCalendarEvent(CalendarEvent calendarEvent) {
        requireNonNull(calendarEvent);
        return versionedScheduler.hasCalendarEvent(calendarEvent);
    }

    @Override
    public void deleteCalendarEvent(CalendarEvent target) {
        versionedScheduler.removeCalendarEvent(target);
        indicateSchedulerChanged();
    }

    @Override
    public void addCalendarEvent(CalendarEvent calendarEvent) {
        versionedScheduler.addCalendarEvent(calendarEvent);
        indicateSchedulerChanged();
    }

    @Override
    public void updateCalendarEvent(CalendarEvent target, CalendarEvent editedCalendarEvent) {
        requireAllNonNull(target, editedCalendarEvent);

        versionedScheduler.updateCalendarEvent(target, editedCalendarEvent);
        indicateSchedulerChanged();
    }

    //=========== FsList Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code CalendarEvent} backed by the internal list of
     * {@code versionedScheduler}
     */
    @Override
    public ObservableList<CalendarEvent> getFullCalendarEventList() {
        return FXCollections.unmodifiableObservableList(versionedScheduler.getCalendarEventList());
    }

    /**
     * Returns an unmodifiable view of the {@code FsList} of {@code CalendarEvent} backed by the internal list of
     * {@code versionedScheduler}
     */
    @Override
    public ObservableList<CalendarEvent> getFilteredCalendarEventList() {
        return FXCollections.unmodifiableObservableList(fsList.getFsList());
    }

    @Override
    public void updateFilteredCalendarEventList(Predicate<CalendarEvent> predicate) {
        requireNonNull(predicate);
        fsList.setPredicate(predicate);
    }

    @Override
    public void sortFilteredCalendarEventList(Comparator<CalendarEvent> comparator) {
        requireNonNull(comparator);
        fsList.setComparator(comparator);
    }

    @Override
    public void addPredicate(Predicate<CalendarEvent> predicate) {
        requireNonNull(predicate);
        fsList.addPredicate(predicate);
    }

    @Override
    public void clearAllPredicatesAndComparators() {
        fsList.clearPredicates();
        fsList.clearComparator();
    }

    @Override
    public FsList getFsList() {
        return fsList.copy(versionedScheduler.getCalendarEventList());
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoScheduler() {
        return versionedScheduler.canUndo();
    }

    @Override
    public boolean canRedoScheduler() {
        return versionedScheduler.canRedo();
    }

    @Override
    public void undoScheduler() {
        versionedScheduler.undo();
        indicateSchedulerChanged();
    }

    @Override
    public void redoScheduler() {
        versionedScheduler.redo();
        indicateSchedulerChanged();
    }

    @Override
    public void commitScheduler() {
        versionedScheduler.commit();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedScheduler.equals(other.versionedScheduler);
    }

}
