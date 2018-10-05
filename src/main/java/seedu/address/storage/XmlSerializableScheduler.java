package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Scheduler;
import seedu.address.model.ReadOnlyScheduler;
import seedu.address.model.calendarevent.CalendarEvent;

/**
 * An Immutable Scheduler that is serializable to XML format
 */
@XmlRootElement(name = "scheduler")
public class XmlSerializableScheduler {

    public static final String MESSAGE_DUPLICATE_CALENDAR_EVENT = "Calendar Events list contains duplicate event(s).";

    @XmlElement
    private List<XmlAdaptedCalendarEvent> calendarEvents;

    /**
     * Creates an empty XmlSerializableScheduler.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableScheduler() {
        calendarEvents = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableScheduler(ReadOnlyScheduler src) {
        this();
        calendarEvents.addAll(src.getCalendarEventList().stream().map(XmlAdaptedCalendarEvent::new).collect(Collectors.toList()));
    }

    /**
     * Converts this scheduler into the model's {@code Scheduler} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedCalendarEvent}.
     */
    public Scheduler toModelType() throws IllegalValueException {
        Scheduler scheduler = new Scheduler();
        for (XmlAdaptedCalendarEvent p : calendarEvents) {
            CalendarEvent calendarEvent = p.toModelType();
            if (scheduler.hasCalendarEvent(calendarEvent)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CALENDAR_EVENT);
            }
            scheduler.addCalendarEvent(calendarEvent);
        }
        return scheduler;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableScheduler)) {
            return false;
        }
        return calendarEvents.equals(((XmlSerializableScheduler) other).calendarEvents);
    }
}
