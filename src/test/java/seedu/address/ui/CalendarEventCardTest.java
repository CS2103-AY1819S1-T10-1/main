package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.CalendarEventCardHandle;
import seedu.address.model.calendarevent.CalendarEvent;
import seedu.address.testutil.PersonBuilder;

public class CalendarEventCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        CalendarEvent calendarEventWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        CalendarEventCard calendarEventCard = new CalendarEventCard(calendarEventWithNoTags, 1);
        uiPartRule.setUiPart(calendarEventCard);
        assertCardDisplay(calendarEventCard, calendarEventWithNoTags, 1);

        // with tags
        CalendarEvent calendarEventWithTags = new PersonBuilder().build();
        calendarEventCard = new CalendarEventCard(calendarEventWithTags, 2);
        uiPartRule.setUiPart(calendarEventCard);
        assertCardDisplay(calendarEventCard, calendarEventWithTags, 2);
    }

    @Test
    public void equals() {
        CalendarEvent calendarEvent = new PersonBuilder().build();
        CalendarEventCard calendarEventCard = new CalendarEventCard(calendarEvent, 0);

        // same calendarevent, same index -> returns true
        CalendarEventCard copy = new CalendarEventCard(calendarEvent, 0);
        assertTrue(calendarEventCard.equals(copy));

        // same object -> returns true
        assertTrue(calendarEventCard.equals(calendarEventCard));

        // null -> returns false
        assertFalse(calendarEventCard.equals(null));

        // different types -> returns false
        assertFalse(calendarEventCard.equals(0));

        // different calendarevent, same index -> returns false
        CalendarEvent differentCalendarEvent = new PersonBuilder().withName("differentName").build();
        assertFalse(calendarEventCard.equals(new CalendarEventCard(differentCalendarEvent, 0)));

        // same calendarevent, different index -> returns false
        assertFalse(calendarEventCard.equals(new CalendarEventCard(calendarEvent, 1)));
    }

    /**
     * Asserts that {@code calendarEventCard} displays the details of {@code expectedCalendarEvent} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(CalendarEventCard calendarEventCard, CalendarEvent expectedCalendarEvent, int expectedId) {
        guiRobot.pauseForHuman();

        CalendarEventCardHandle calendarEventCardHandle = new CalendarEventCardHandle(calendarEventCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", calendarEventCardHandle.getId());

        // verify calendarevent details are displayed correctly
        assertCardDisplaysPerson(expectedCalendarEvent, calendarEventCardHandle);
    }
}
