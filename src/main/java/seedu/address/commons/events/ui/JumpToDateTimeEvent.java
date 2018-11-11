package seedu.address.commons.events.ui;

import java.time.LocalDateTime;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request for the calendar ui to display a particular localDateTime
 */
public class JumpToDateTimeEvent extends BaseEvent {

    public final LocalDateTime targetLocalDateTime;

    public JumpToDateTimeEvent(LocalDateTime targetLocalDateTime) {
        this.targetLocalDateTime = targetLocalDateTime;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
