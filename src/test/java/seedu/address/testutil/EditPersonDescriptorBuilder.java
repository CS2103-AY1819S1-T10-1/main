package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditCalendarEventDescriptor;
import seedu.address.model.calendarevent.CalendarEvent;
import seedu.address.model.calendarevent.Email;
import seedu.address.model.calendarevent.Phone;
import seedu.address.model.calendarevent.Title;
import seedu.address.model.calendarevent.Venue;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditCalendarEventDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditCalendarEventDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditCalendarEventDescriptor();
    }

    public EditPersonDescriptorBuilder(EditCalendarEventDescriptor descriptor) {
        this.descriptor = new EditCalendarEventDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditCalendarEventDescriptor} with fields containing {@code calendarevent}'s details
     */
    public EditPersonDescriptorBuilder(CalendarEvent calendarEvent) {
        descriptor = new EditCalendarEventDescriptor();
        descriptor.setName(calendarEvent.getName());
        descriptor.setPhone(calendarEvent.getPhone());
        descriptor.setEmail(calendarEvent.getEmail());
        descriptor.setVenue(calendarEvent.getVenue());
        descriptor.setTags(calendarEvent.getTags());
    }

    /**
     * Sets the {@code Title} of the {@code EditCalendarEventDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Title(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditCalendarEventDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditCalendarEventDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Venue} of the {@code EditCalendarEventDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        descriptor.setVenue(new Venue(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditCalendarEventDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditCalendarEventDescriptor build() {
        return descriptor;
    }
}
