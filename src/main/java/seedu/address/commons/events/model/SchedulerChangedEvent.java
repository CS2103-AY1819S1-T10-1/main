package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyScheduler;

/**
 * Indicates the Scheduler in the model has changed
 */
public class SchedulerChangedEvent extends BaseEvent {

    public final ReadOnlyScheduler data;

    public SchedulerChangedEvent(ReadOnlyScheduler data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of events " + data.getCalendarEventList().size();
    }

}
