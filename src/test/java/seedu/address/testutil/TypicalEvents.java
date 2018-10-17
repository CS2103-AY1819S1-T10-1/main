package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATETIME_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATETIME_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATETIME_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATETIME_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_TUTORIAL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Scheduler;
import seedu.address.model.calendarevent.CalendarEvent;

/**
 * A utility class containing a list of {@code CalendarEvent} objects to be used in tests.
 */
public class TypicalEvents {

    public static final CalendarEvent LECTURE = new CalendarEventBuilder().withTitle("Alice Pauline")
        .withStart("2018-10-16 14:00").withEnd("2018-10-16 16:00")
        .withVenue("123, Jurong West Ave 6, #08-111")
        .withDescription("94351253")
        .withTags("friends").build();
    public static final CalendarEvent BENSON = new CalendarEventBuilder().withTitle("Benson Meier")
        .withStart("2018-10-16 14:00").withEnd("2018-10-16 16:00")
        .withVenue("311, Clementi Ave 2, #02-25")
        .withDescription("98765432")
        .withTags("owesMoney", "friends").build();
    public static final CalendarEvent CARL = new CalendarEventBuilder().withTitle("Carl Kurz").withDescription(
        "95352563").withStart("2018-10-16 14:00").withEnd("2018-10-16 16:00")
        .withVenue("wall street").build();
    public static final CalendarEvent DANIEL = new CalendarEventBuilder().withTitle("Daniel Meier").withDescription(
        "87652533").withStart("2018-10-16 14:00").withEnd("2018-10-16 16:00")
        .withVenue("10th street").withTags("friends").build();
    public static final CalendarEvent ELLE = new CalendarEventBuilder().withTitle("Elle Meyer").withDescription(
        "9482224").withStart("2018-10-16 14:00").withEnd("2018-10-16 16:00")
        .withVenue("michegan ave").build();
    public static final CalendarEvent FIONA = new CalendarEventBuilder().withTitle("Fiona Kunz").withDescription(
        "9482427").withStart("2018-10-16 14:00").withEnd("2018-10-16 16:00")
        .withVenue("little tokyo").build();
    public static final CalendarEvent GEORGE = new CalendarEventBuilder().withTitle("George Best").withDescription(
        "9482442").withStart("2018-10-16 14:00").withEnd("2018-10-16 16:00")
        .withVenue("4th street").build();

    // Manually added
    public static final CalendarEvent HOON = new CalendarEventBuilder().withTitle("Hoon Meier").withDescription(
        "8482424")
        .withVenue("little india").build();
    public static final CalendarEvent IDA = new CalendarEventBuilder().withTitle("Ida Mueller").withDescription(
        "8482131")
        .withVenue("chicago ave").build();

    // Manually added - CalendarEvent's details found in {@code CommandTestUtil}
    public static final CalendarEvent AMY =
        new CalendarEventBuilder().withTitle(VALID_TITLE_LECTURE).withDescription(VALID_DESCRIPTION_LECTURE)
            .withStart(VALID_START_DATETIME_LECTURE).withEnd(VALID_END_DATETIME_LECTURE)
            .withVenue(VALID_VENUE_LECTURE).withTags(VALID_TAG_FRIEND).build();
    public static final CalendarEvent TUTORIAL =
        new CalendarEventBuilder().withTitle(VALID_TITLE_TUTORIAL).withDescription(VALID_DESCRIPTION_TUTORIAL)
            .withStart(VALID_START_DATETIME_TUTORIAL).withEnd(VALID_END_DATETIME_TUTORIAL)
            .withVenue(VALID_VENUE_TUTORIAL).withTags(VALID_TAG_HUSBAND,
            VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalEvents() {
    } // prevents instantiation

    /**
     * Returns an {@code Scheduler} with all the typical persons.
     */
    public static Scheduler getTypicalScheduler() {
        Scheduler ab = new Scheduler();
        for (CalendarEvent calendarEvent : getTypicalCalendarEvents()) {
            ab.addCalendarEvent(calendarEvent);
        }
        return ab;
    }

    public static List<CalendarEvent> getTypicalCalendarEvents() {
        return new ArrayList<>(Arrays.asList(LECTURE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
