package seedu.address.commons.events.ui;

import java.time.LocalDateTime;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of persons
 */
public class CalendarDisplayTimeChangedEvent extends BaseEvent {

    public final LocalDateTime newLocalDateTime;

    public CalendarDisplayTimeChangedEvent(LocalDateTime newLocalDateTime) {
        this.newLocalDateTime = newLocalDateTime;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
