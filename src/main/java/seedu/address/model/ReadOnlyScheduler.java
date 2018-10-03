package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.calendarEvent.CalendarEvent;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyScheduler {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<CalendarEvent> getCalendarEventList();

}
