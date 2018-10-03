package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.calendarEvent.CalendarEvent;

/**
 * Represents a selection change in the CalendarEvent List Panel
 */
public class PersonPanelSelectionChangedEvent extends BaseEvent {


    private final CalendarEvent newSelection;

    public PersonPanelSelectionChangedEvent(CalendarEvent newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public CalendarEvent getNewSelection() {
        return newSelection;
    }
}
