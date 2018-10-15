package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.calendarevent.CalendarEvent;
import seedu.address.model.calendarevent.Email;
import seedu.address.model.calendarevent.Phone;
import seedu.address.model.calendarevent.Title;
import seedu.address.model.calendarevent.Venue;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building CalendarEvent objects.
 */
public class CalendarEventBuilder {

    public static final String DEFAULT_TITLE = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_VENUE = "123, Jurong West Ave 6, #08-111";

    private Title title;
    private Phone phone;
    private Email email;
    private Venue venue;
    private Set<Tag> tags;

    public CalendarEventBuilder() {
        title = new Title(DEFAULT_TITLE);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        venue = new Venue(DEFAULT_VENUE);
        tags = new HashSet<>();
    }

    /**
     * Initializes the CalendarEventBuilder with the data of {@code calendarEventToCopy}.
     */
    public CalendarEventBuilder(CalendarEvent calendarEventToCopy) {
        title = calendarEventToCopy.getName();
        phone = calendarEventToCopy.getPhone();
        email = calendarEventToCopy.getEmail();
        venue = calendarEventToCopy.getVenue();
        tags = new HashSet<>(calendarEventToCopy.getTags());
    }

    /**
     * Sets the {@code Title} of the {@code CalendarEvent} that we are building.
     */
    public CalendarEventBuilder withTitle(String title) {
        this.title = new Title(title);
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

    public CalendarEvent build() {
        return new CalendarEvent(title, phone, email, venue, tags);
    }

}
