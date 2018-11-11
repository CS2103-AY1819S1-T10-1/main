package seedu.address.testutil;

import seedu.address.model.Scheduler;
import seedu.address.model.calendarevent.CalendarEvent;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 * {@code Scheduler ab = new SchedulerBuilder().withCalendarEvent("John", "Doe").build();}
 */
public class SchedulerBuilder {

    private Scheduler scheduler;

    public SchedulerBuilder() {
        scheduler = new Scheduler();
    }

    public SchedulerBuilder(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * Adds a new {@code CalendarEvent} to the {@code Scheduler} that we are building.
     */
    public SchedulerBuilder withCalendarEvent(CalendarEvent calendarEvent) {
        scheduler.addCalendarEvent(calendarEvent);
        return this;
    }

    public Scheduler build() {
        return scheduler;
    }
}
