package seedu.address.model.calendarevent;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Calendar Event in the scheduler.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class CalendarEvent {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public CalendarEvent(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both calendar events of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two calendar events.
     */
    public boolean isSameCalendarEvent(CalendarEvent otherCalendarEvent) {
        if (otherCalendarEvent == this) {
            return true;
        }

        return otherCalendarEvent != null
                && otherCalendarEvent.getName().equals(getName())
                && (otherCalendarEvent.getPhone().equals(getPhone()) || otherCalendarEvent.getEmail().equals(getEmail()));
    }

    /**
     * Returns true if both calendar events have the same identity and data fields.
     * This defines a stronger notion of equality between two calendar events.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CalendarEvent)) {
            return false;
        }

        CalendarEvent otherCalendarEvent = (CalendarEvent) other;
        return otherCalendarEvent.getName().equals(getName())
                && otherCalendarEvent.getPhone().equals(getPhone())
                && otherCalendarEvent.getEmail().equals(getEmail())
                && otherCalendarEvent.getAddress().equals(getAddress())
                && otherCalendarEvent.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
