package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.calendarEvent.CalendarEvent;
import seedu.address.testutil.PersonBuilder;

public class CalendarEventCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        CalendarEvent calendarEventWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        PersonCard personCard = new PersonCard(calendarEventWithNoTags, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, calendarEventWithNoTags, 1);

        // with tags
        CalendarEvent calendarEventWithTags = new PersonBuilder().build();
        personCard = new PersonCard(calendarEventWithTags, 2);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, calendarEventWithTags, 2);
    }

    @Test
    public void equals() {
        CalendarEvent calendarEvent = new PersonBuilder().build();
        PersonCard personCard = new PersonCard(calendarEvent, 0);

        // same calendarEvent, same index -> returns true
        PersonCard copy = new PersonCard(calendarEvent, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different calendarEvent, same index -> returns false
        CalendarEvent differentCalendarEvent = new PersonBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new PersonCard(differentCalendarEvent, 0)));

        // same calendarEvent, different index -> returns false
        assertFalse(personCard.equals(new PersonCard(calendarEvent, 1)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedCalendarEvent} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(PersonCard personCard, CalendarEvent expectedCalendarEvent, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify calendarEvent details are displayed correctly
        assertCardDisplaysPerson(expectedCalendarEvent, personCardHandle);
    }
}
