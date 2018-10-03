package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import guitests.guihandles.EventCardHandle;
import org.junit.Test;

import seedu.address.model.calendarEvent.CalendarEvent;
import seedu.address.testutil.PersonBuilder;

public class EventCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        CalendarEvent personWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        EventCard eventCard = new EventCard(personWithNoTags, 1);
        uiPartRule.setUiPart(eventCard);
        assertCardDisplay(eventCard, personWithNoTags, 1);

        // with tags
        CalendarEvent personWithTags = new PersonBuilder().build();
        eventCard = new EventCard(personWithTags, 2);
        uiPartRule.setUiPart(eventCard);
        assertCardDisplay(eventCard, personWithTags, 2);
    }

    @Test
    public void equals() {
        CalendarEvent person = new PersonBuilder().build();
        EventCard eventCard = new EventCard(person, 0);

        // same person, same index -> returns true
        EventCard copy = new EventCard(person, 0);
        assertTrue(eventCard.equals(copy));

        // same object -> returns true
        assertTrue(eventCard.equals(eventCard));

        // null -> returns false
        assertFalse(eventCard.equals(null));

        // different types -> returns false
        assertFalse(eventCard.equals(0));

        // different person, same index -> returns false
        CalendarEvent differentPerson = new PersonBuilder().withName("differentName").build();
        assertFalse(eventCard.equals(new EventCard(differentPerson, 0)));

        // same person, different index -> returns false
        assertFalse(eventCard.equals(new EventCard(person, 1)));
    }

    /**
     * Asserts that {@code eventCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(EventCard eventCard, CalendarEvent expectedPerson, int expectedId) {
        guiRobot.pauseForHuman();

        EventCardHandle eventCardHandle = new EventCardHandle(eventCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", eventCardHandle.getId());

        // verify person details are displayed correctly
        assertCardDisplaysPerson(expectedPerson, eventCardHandle);
    }
}
