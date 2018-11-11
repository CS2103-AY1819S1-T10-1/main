package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.calendarevent.CalendarEvent;

/**
 * Represents a selection change in the Calendar Event List Panel
 */
public class CalendarPanelSelectionChangedEvent extends BaseEvent {

    public final CalendarEvent newSelection;

    public CalendarPanelSelectionChangedEvent(CalendarEvent newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
