package seedu.scheduler.testutil;

import seedu.scheduler.model.Scheduler;
import seedu.scheduler.model.calendarEvent.CalendarEvent;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code Scheduler ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private Scheduler scheduler;

    public AddressBookBuilder() {
        scheduler = new Scheduler();
    }

    public AddressBookBuilder(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * Adds a new {@code CalendarEvent} to the {@code Scheduler} that we are building.
     */
    public AddressBookBuilder withPerson(CalendarEvent calendarEvent) {
        scheduler.addCalendarEvent(calendarEvent);
        return this;
    }

    public Scheduler build() {
        return scheduler;
    }
}
