package seedu.address.model.calendarevent;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATETIME_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATETIME_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_TUTORIAL;
import static seedu.address.testutil.TypicalEvents.CS2103_LECTURE;
import static seedu.address.testutil.TypicalEvents.TUTORIAL;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.CalendarEventBuilder;

public class CalendarEventTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        CalendarEvent calendarEvent = new CalendarEventBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        calendarEvent.getTags().remove(0);
    }

    @Test
    public void isSameEvent() {
        // same object -> returns true
        assertTrue(CS2103_LECTURE.isSameCalendarEvent(CS2103_LECTURE));

        // null -> returns false
        assertFalse(CS2103_LECTURE.isSameCalendarEvent(null));

        // different start date/time -> returns false
        CalendarEvent editedLecture =
            new CalendarEventBuilder(CS2103_LECTURE).withStart(VALID_START_DATETIME_TUTORIAL).build();
        assertFalse(CS2103_LECTURE.isSameCalendarEvent(editedLecture));

        // different end date/time -> returns false
        editedLecture = new CalendarEventBuilder(CS2103_LECTURE).withEnd(VALID_END_DATETIME_TUTORIAL).build();
        assertFalse(CS2103_LECTURE.isSameCalendarEvent(editedLecture));

        // same title, same start date/time,same end date/time,
        // different venue, different description, different attributes -> returns true
        editedLecture =
            new CalendarEventBuilder(CS2103_LECTURE)
                .withDescription(VALID_DESCRIPTION_TUTORIAL)
                .withVenue(VALID_VENUE_TUTORIAL)
                .withTags(VALID_TAG_LECTURE)
                .build();
        assertTrue(CS2103_LECTURE.isSameCalendarEvent(editedLecture));

        // same title, same description, same venue, same attributes
        // different start date/time, different end date/time -> returns false
        editedLecture =
            new CalendarEventBuilder(CS2103_LECTURE)
                .withStart(VALID_START_DATETIME_TUTORIAL)
                .withEnd(VALID_END_DATETIME_TUTORIAL)
                .build();
        assertFalse(CS2103_LECTURE.isSameCalendarEvent(editedLecture));

        // TODO redefine what is considered sameEvent or decide whether this test is necessary
        // same title, same description, same start date/time, same end date/time,
        // different venue, different attributes                               -> returns true
        editedLecture =
            new CalendarEventBuilder(CS2103_LECTURE).withVenue(VALID_VENUE_TUTORIAL)
                                    .withTags(VALID_TAG_LECTURE).build();
        assertTrue(CS2103_LECTURE.isSameCalendarEvent(editedLecture));
    }

    @Test
    public void equals() {
        // same values -> returns true
        CalendarEvent lectureCopy = new CalendarEventBuilder(CS2103_LECTURE).build();
        assertTrue(CS2103_LECTURE.equals(lectureCopy));

        // same object -> returns true
        assertTrue(CS2103_LECTURE.equals(CS2103_LECTURE));

        // null -> returns false
        assertFalse(CS2103_LECTURE.equals(null));

        // different type -> returns false
        assertFalse(CS2103_LECTURE.equals(5));

        // different calendarevent -> returns false
        assertFalse(CS2103_LECTURE.equals(TUTORIAL));

        // different title -> returns false
        CalendarEvent editedLecture = new CalendarEventBuilder(CS2103_LECTURE).withTitle(VALID_TITLE_TUTORIAL).build();
        assertFalse(CS2103_LECTURE.equals(editedLecture));

        // different description -> returns false
        editedLecture = new CalendarEventBuilder(CS2103_LECTURE).withDescription(VALID_DESCRIPTION_TUTORIAL).build();
        assertFalse(CS2103_LECTURE.equals(editedLecture));

        // different venue -> returns false
        editedLecture = new CalendarEventBuilder(CS2103_LECTURE).withVenue(VALID_VENUE_TUTORIAL).build();
        assertFalse(CS2103_LECTURE.equals(editedLecture));

        // different tags -> returns false
        editedLecture = new CalendarEventBuilder(CS2103_LECTURE).withTags(VALID_TAG_LECTURE).build();
        assertFalse(CS2103_LECTURE.equals(editedLecture));
    }
}
