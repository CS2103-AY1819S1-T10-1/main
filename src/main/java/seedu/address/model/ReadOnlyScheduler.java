package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.calendarevent.CalendarEvent;

/**
 * Unmodifiable view of an scheduler
 */
public interface ReadOnlyScheduler {

    /**
     * Returns an unmodifiable view of the calendar event list.
     * This list will not contain any duplicate calendar events.
     */
    ObservableList<CalendarEvent> getCalendarEventList();

}
