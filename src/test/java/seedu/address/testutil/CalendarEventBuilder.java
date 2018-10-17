package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.calendarevent.CalendarEvent;
import seedu.address.model.calendarevent.DateTime;
import seedu.address.model.calendarevent.DateTimeInfo;
import seedu.address.model.calendarevent.Description;
import seedu.address.model.calendarevent.Title;
import seedu.address.model.calendarevent.Venue;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building CalendarEvent objects.
 */
public class CalendarEventBuilder {

    public static final String DEFAULT_TITLE = "CS2103 Lecture";
    public static final String DEFAULT_DESCRIPTION = "Abstraction, IntelliJ, Gradle";
    public static final String DEFAULT_START_DATETIME = "2018-10-16 14:00";
    public static final String DEFAULT_END_DATETIME = "2018-10-16 16:00";
    public static final String DEFAULT_VENUE = "i3 Auditorium";

    private Title title;
    private Description description;
    private DateTime start;
    private DateTime end;
    private Venue venue;
    private Set<Tag> tags;

    public CalendarEventBuilder() {
        title = new Title(DEFAULT_TITLE);
        description = new Description(DEFAULT_DESCRIPTION);
        start = new DateTime(DEFAULT_START_DATETIME);
        end = new DateTime(DEFAULT_END_DATETIME);
        venue = new Venue(DEFAULT_VENUE);
        tags = new HashSet<>();
    }

    /**
     * Initializes the CalendarEventBuilder with the data of {@code calendarEventToCopy}.
     */
    public CalendarEventBuilder(CalendarEvent calendarEventToCopy) {
        title = calendarEventToCopy.getTitle();
        description = calendarEventToCopy.getDescription();
        start = calendarEventToCopy.getStart();
        end = calendarEventToCopy.getEnd();
        venue = calendarEventToCopy.getVenue();
        tags = new HashSet<>(calendarEventToCopy.getTags());
    }

    /**
     * Sets the {@code Title} of the {@code CalendarEvent} that we are building.
     */
    public CalendarEventBuilder withTitle(String name) {
        this.title = new Title(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code CalendarEvent} that we are building.
     */
    public CalendarEventBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Venue} of the {@code CalendarEvent} that we are building.
     */
    public CalendarEventBuilder withVenue(String venue) {
        this.venue = new Venue(venue);
        return this;
    }

    /**
     * Sets the start {@code DateTime} of the {@code CalendarEvent} that we are building.
     */
    public CalendarEventBuilder withStart(String start) {
        this.start = new DateTime(start);
        return this;
    }


    /**
     * Sets the end {@code DateTime} of the {@code CalendarEvent} that we are building.
     */
    public CalendarEventBuilder withEnd(String end) {
        this.end = new DateTime(end);
        return this;
    }


    /**
     * Sets the {@code Description} of the {@code CalendarEvent} that we are building.
     */
    public CalendarEventBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }


    public CalendarEvent build() {
        return new CalendarEvent(title, description, new DateTimeInfo(start, end), venue, tags);
    }

}
