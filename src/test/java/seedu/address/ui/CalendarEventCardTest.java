package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import guitests.guihandles.EventCardHandle;
import org.junit.Test;

import seedu.address.model.calendarEvent.CalendarEvent;
import seedu.address.testutil.PersonBuilder;

public class CalendarEventCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        CalendarEvent calendarEventWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        EventCard personCard = new EventCard(calendarEventWithNoTags, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, calendarEventWithNoTags, 1);

        // with tags
        CalendarEvent calendarEventWithTags = new PersonBuilder().build();
        personCard = new EventCard(calendarEventWithTags, 2);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, calendarEventWithTags, 2);
    }

    @Test
    public void equals() {
        CalendarEvent calendarEvent = new PersonBuilder().build();
        EventCard personCard = new EventCard(calendarEvent, 0);

        // same calendarEvent, same index -> returns true
        EventCard copy = new EventCard(calendarEvent, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different calendarEvent, same index -> returns false
        CalendarEvent differentCalendarEvent = new PersonBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new EventCard(differentCalendarEvent, 0)));

        // same calendarEvent, different index -> returns false
        assertFalse(personCard.equals(new EventCard(calendarEvent, 1)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedCalendarEvent} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(EventCard personCard, CalendarEvent expectedCalendarEvent, int expectedId) {
        guiRobot.pauseForHuman();

        EventCardHandle eventCardHandle = new EventCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", eventCardHandle.getId());

        // verify calendarEvent details are displayed correctly
        assertCardDisplaysPerson(expectedCalendarEvent, eventCardHandle);
    }
}
