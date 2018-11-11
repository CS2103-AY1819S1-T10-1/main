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

    public static final CalendarEvent LECTURE = new CalendarEventBuilder().withTitle("CS2103 Lecture")
        .withStart("2018-11-15 16:00").withEnd("2018-11-15 18:00")
        .withVenue("i3 Auditorium")
        .withDescription("Abstraction, Gradle, JUnit")
        .withTags("lecture", "CS2103").build();
    public static final CalendarEvent BENSON = new CalendarEventBuilder().withTitle("CS2104 Tutorial")
        .withStart("2018-11-13 14:00").withEnd("2018-11-13 16:00")
        .withVenue("AS6 04-21")
        .withDescription("Monadic Parsers")
        .withTags("tutorial", "CS2104").build();
    public static final CalendarEvent CARL = new CalendarEventBuilder().withTitle("CS2040 Lab")
        .withStart("2018-11-14 08:00")
        .withEnd("2018-11-14 10:00")
        .withDescription("Linked Lists")
        .withVenue("COM1 02-09").build();
    public static final CalendarEvent DANIEL = new CalendarEventBuilder().withTitle("FIN3101 Seminar")
        .withStart("2018-11-16 10:00")
        .withEnd("2018-11-16 15:00")
        .withDescription("One-Fund Theorem")
        .withVenue("Marina Boulevard")
        .withTags("seminar", "FIN3101").build();
    public static final CalendarEvent ELLE = new CalendarEventBuilder().withTitle("Choir Practice")
        .withStart("2018-11-16 19:00")
        .withEnd("2018-11-16 22:00")
        .withDescription("Bring songbook")
        .withVenue("Little Tokyo").build();
    public static final CalendarEvent FIONA = new CalendarEventBuilder().withTitle("Career Fair")
        .withStart("2018-11-17 09:00")
        .withEnd("2018-11-17 18:00")
        .withDescription("Bring resume")
        .withVenue("MPSH 1").build();
    public static final CalendarEvent GEORGE = new CalendarEventBuilder().withTitle("Google Interview")
        .withStart("2018-11-14 14:00")
        .withEnd("2018-11-14 16:00")
        .withDescription("Bring water bottle")
        .withVenue("Mountain View").build();

    // Manually added
    public static final CalendarEvent HOON = new CalendarEventBuilder().withTitle("GEQ1000 Lecture")
        .withDescription("Design Wallet")
        .withVenue("SRC 02-07").build();
    public static final CalendarEvent IDA = new CalendarEventBuilder().withTitle("Lunch At McDs").withDescription(
        "Bring girlfriend")
        .withVenue("Hanbaobao Avenue").build();

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

    public static final String KEYWORD_MATCHING_LECTURE = "Lecture"; // A keyword that matches MEIER
    public static final String KEYWORD_MATCHING_EXACT_TUTORIAL = "JS1011 Tutorial";

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
