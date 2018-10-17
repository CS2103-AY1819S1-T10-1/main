package seedu.address.model.calendarevent;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATETIME_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATETIME_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_TUTORIAL;
import static seedu.address.testutil.TypicalEvents.LECTURE;
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
        assertTrue(LECTURE.isSameCalendarEvent(LECTURE));

        // null -> returns false
        assertFalse(LECTURE.isSameCalendarEvent(null));

        // different start date/time -> returns false
        CalendarEvent editedLecture =
            new CalendarEventBuilder(LECTURE).withStart(VALID_START_DATETIME_TUTORIAL).build();
        assertFalse(LECTURE.isSameCalendarEvent(editedLecture));

        // different end date/time -> returns false
        editedLecture = new CalendarEventBuilder(LECTURE).withEnd(VALID_END_DATETIME_TUTORIAL).build();
        assertFalse(LECTURE.isSameCalendarEvent(editedLecture));

        // same title, same start date/time,same end date/time,
        // different venue, different description, different attributes -> returns true
        editedLecture =
            new CalendarEventBuilder(LECTURE)
                .withDescription(VALID_DESCRIPTION_TUTORIAL)
                .withVenue(VALID_VENUE_TUTORIAL)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(LECTURE.isSameCalendarEvent(editedLecture));

        // same title, same description, same venue, same attributes
        // different start date/time, different end date/time -> returns false
        editedLecture =
            new CalendarEventBuilder(LECTURE)
                .withStart(VALID_START_DATETIME_TUTORIAL)
                .withEnd(VALID_END_DATETIME_TUTORIAL)
                .build();
        assertFalse(LECTURE.isSameCalendarEvent(editedLecture));

        // TODO redefine what is considered sameEvent or decide whether this test is necessary
        // same title, same description, same start date/time, same end date/time,
        // different venue, different attributes                               -> returns true
        editedLecture =
            new CalendarEventBuilder(LECTURE).withVenue(VALID_VENUE_TUTORIAL).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(LECTURE.isSameCalendarEvent(editedLecture));
    }

    @Test
    public void equals() {
        // same values -> returns true
        CalendarEvent lectureCopy = new CalendarEventBuilder(LECTURE).build();
        assertTrue(LECTURE.equals(lectureCopy));

        // same object -> returns true
        assertTrue(LECTURE.equals(LECTURE));

        // null -> returns false
        assertFalse(LECTURE.equals(null));

        // different type -> returns false
        assertFalse(LECTURE.equals(5));

        // different calendarevent -> returns false
        assertFalse(LECTURE.equals(TUTORIAL));

        // different title -> returns false
        CalendarEvent editedLecture = new CalendarEventBuilder(LECTURE).withTitle(VALID_TITLE_TUTORIAL).build();
        assertFalse(LECTURE.equals(editedLecture));

        // different description -> returns false
        editedLecture = new CalendarEventBuilder(LECTURE).withDescription(VALID_DESCRIPTION_TUTORIAL).build();
        assertFalse(LECTURE.equals(editedLecture));

        // different venue -> returns false
        editedLecture = new CalendarEventBuilder(LECTURE).withVenue(VALID_VENUE_TUTORIAL).build();
        assertFalse(LECTURE.equals(editedLecture));

        // different tags -> returns false
        editedLecture = new CalendarEventBuilder(LECTURE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(LECTURE.equals(editedLecture));
    }
}
