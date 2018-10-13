package seedu.address.storage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.calendarevent.*;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Calendar Event.
 */
public class XmlAdaptedCalendarEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private LocalDateTime dateTime;
    @XmlElement(required = true)
    private String location;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedCalendarEvent.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedCalendarEvent() {
    }

    /**
     * Constructs an {@code XmlAdaptedCalendarEvent} with the given calendar event details.
     */
    public XmlAdaptedCalendarEvent(String name, String phone, String email, LocalDateTime dateTime, String location,
                                   List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.dateTime = dateTime;
        this.location = location;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Calendar Event into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedCalendarEvent
     */
    public XmlAdaptedCalendarEvent(CalendarEvent source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        dateTime = source.getDateTime().localDateTime;
        location = source.getLocation().value;
        tagged = source.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted calendarevent object into the model's CalendarEvent object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted calendar event
     */
    public CalendarEvent toModelType() throws IllegalValueException {
        final List<Tag> calendarEventTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            calendarEventTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (dateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, DateTime.class.getSimpleName()));
        }
        if (!DateTime.isValidDateTime(dateTime.getYear(),dateTime.getMonthValue(),dateTime.getDayOfMonth(),dateTime.getHour(),dateTime.getMinute())) {
            throw new IllegalValueException(DateTime.MESSAGE_DATETIME_CONSTRAINTS);
        }
        final DateTime modelDateTime = new DateTime(dateTime);

        if (location == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Location.class.getSimpleName()));
        }
        if (!Location.isValidLocation(location)) {
            throw new IllegalValueException(Location.MESSAGE_LOCATION_CONSTRAINTS);
        }
        final Location modelLocation = new Location(location);

        final Set<Tag> modelTags = new HashSet<>(calendarEventTags);
        return new CalendarEvent(modelName, modelPhone, modelEmail, modelDateTime, modelLocation, modelTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedCalendarEvent)) {
            return false;
        }

        XmlAdaptedCalendarEvent otherCalendarEvent = (XmlAdaptedCalendarEvent) other;
        return Objects.equals(name, otherCalendarEvent.name)
            && Objects.equals(phone, otherCalendarEvent.phone)
            && Objects.equals(email, otherCalendarEvent.email)
            && Objects.equals(dateTime, otherCalendarEvent.dateTime)
            && Objects.equals(location, otherCalendarEvent.location)
            && tagged.equals(otherCalendarEvent.tagged);
    }
}
