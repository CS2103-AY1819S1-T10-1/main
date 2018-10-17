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
    private final Title title;

    // Data fields
    private final Description description;
    private final Venue venue;
    private final DateTimeInfo dateTimeInfo;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public CalendarEvent(Title title, Description description, DateTimeInfo dateTimeInfo, Venue venue, Set<Tag> tags) {
        requireAllNonNull(title, description, venue, tags);
        this.title = title;
        this.description = description;
        this.dateTimeInfo = dateTimeInfo;
        this.venue = venue;
        this.tags.addAll(tags);
    }

    public Title getTitle() {
        return title;
    }

    public Description getDescription() {
        return description;
    }

    public DateTime getStart() {
        return dateTimeInfo.start;
    }

    public DateTime getEnd() {
        return dateTimeInfo.end;
    }

    public Venue getVenue() {
        return venue;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both calendar events of the same title also have the same start and end times.
     * This defines a weaker notion of equality between two calendar events.
     */
    public boolean isSameCalendarEvent(CalendarEvent otherCalendarEvent) {
        if (otherCalendarEvent == this) {
            return true;
        }

        return otherCalendarEvent != null
            && otherCalendarEvent.getTitle().equals(getTitle())
            && otherCalendarEvent.getStart().equals(getStart())
            && otherCalendarEvent.getEnd().equals(getEnd());
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
        return otherCalendarEvent.getTitle().equals(getTitle())
            && otherCalendarEvent.getDescription().equals(getDescription())
            && otherCalendarEvent.getStart().equals(getStart())
            && otherCalendarEvent.getEnd().equals(getEnd())
            && otherCalendarEvent.getVenue().equals(getVenue())
            && otherCalendarEvent.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, description, venue, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Title: ")
            .append(getTitle())
            .append(" Description: ")
            .append(getDescription())
            .append(" Start Date & Time: ")
            .append(getStart())
            .append(" End Date & Time: ")
            .append(getEnd())
            .append(" Venue: ")
            .append(getVenue())
            .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
