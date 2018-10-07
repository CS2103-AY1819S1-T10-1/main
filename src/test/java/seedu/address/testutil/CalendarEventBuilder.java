package seedu.address.testutil;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.calendarevent.*;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building CalendarEvent objects.
 */
public class CalendarEventBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final LocalDateTime DEFAULT_DATETIME = LocalDateTime.of(2018,10,07,18,00);
    public static final String DEFAULT_LOCATION = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Phone phone;
    private Email email;
    private DateTime dateTime;
    private Location location;
    private Set<Tag> tags;

    public CalendarEventBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        dateTime = new DateTime(DEFAULT_DATETIME);
        location = new Location(DEFAULT_LOCATION);
        tags = new HashSet<>();
    }

    /**
     * Initializes the CalendarEventBuilder with the data of {@code calendarEventToCopy}.
     */
    public CalendarEventBuilder(CalendarEvent calendarEventToCopy) {
        name = calendarEventToCopy.getName();
        phone = calendarEventToCopy.getPhone();
        email = calendarEventToCopy.getEmail();
        dateTime = calendarEventToCopy.getDateTime();
        location = calendarEventToCopy.getLocation();
        tags = new HashSet<>(calendarEventToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code CalendarEvent} that we are building.
     */
    public CalendarEventBuilder withName(String name) {
        this.name = new Name(name);
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
     * Sets the {@code Location} of the {@code CalendarEvent} that we are building.
     */
    public CalendarEventBuilder withLocation(String location) {
        this.location = new Location(location);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code CalendarEvent} that we are building.
     */
    public CalendarEventBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code CalendarEvent} that we are building.
     */
    public CalendarEventBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code CalendarEvent} that we are building.
     */
    public CalendarEventBuilder withDateTime(LocalDateTime dateTime) {
        this.dateTime = new DateTime(dateTime);
        return this;
    }

    public CalendarEvent build() {
        return new CalendarEvent(name, phone, email, dateTime, location, tags);
    }

}
