package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.Event;

/**
 * Unmodifiable view of an schedule
 */
public interface ReadOnlySchedule {

    /**
     * Returns an unmodifiable view of the events list.
     * This list will not contain any duplicate events.
     */
    ObservableList<Event> getEventList();

}
