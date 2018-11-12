package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.Set;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.model.calendarevent.CalendarEvent;
import seedu.address.model.tag.Tag;

/**
 * A utility class for CalendarEvent.
 */
public class CalendarEventUtil {

    /**
     * Returns an add command string for adding the {@code calendarevent}.
     */
    public static String getAddCommand(CalendarEvent calendarEvent) {
        return AddEventCommand.COMMAND_WORD + " " + getCalendarEventDetails(calendarEvent);
    }

    /**
     * Returns the part of command string for the given {@code calendarevent}'s details.
     */
    public static String getCalendarEventDetails(CalendarEvent calendarEvent) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TITLE + calendarEvent.getTitle().value + " ");
        sb.append(PREFIX_DESCRIPTION + calendarEvent.getDescriptionObject().value + " ");
        sb.append(PREFIX_START + calendarEvent.getStart().toInputFormat() + " ");
        sb.append(PREFIX_END + calendarEvent.getEnd().toInputFormat() + " ");
        sb.append(PREFIX_VENUE + calendarEvent.getVenue().value + " ");
        calendarEvent.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditCalendarEventDescriptor}'s details.
     */
    public static String getEditCalendarEventDescriptorDetails(EditEventCommand.EditCalendarEventDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getTitle().ifPresent(name -> sb.append(PREFIX_TITLE).append(name.value).append(" "));
        descriptor.getDescription().ifPresent(phone -> sb.append(PREFIX_DESCRIPTION).append(phone.value).append(" "));
        descriptor.getStart().ifPresent(start -> sb.append(PREFIX_START).append(start.toInputFormat()).append(" "));
        descriptor.getEnd().ifPresent(end -> sb.append(PREFIX_END).append(end.toInputFormat()).append(" "));
        descriptor.getVenue().ifPresent(address -> sb.append(PREFIX_VENUE).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
