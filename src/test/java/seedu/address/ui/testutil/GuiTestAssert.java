package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.EventCardHandle;
import guitests.guihandles.EventListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.calendarEvent.CalendarEvent;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(EventCardHandle expectedCard, EventCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedCalendarEvent}.
     */
    public static void assertCardDisplaysPerson(CalendarEvent expectedCalendarEvent, EventCardHandle actualCard) {
        assertEquals(expectedCalendarEvent.getName().fullName, actualCard.getName());
        assertEquals(expectedCalendarEvent.getPhone().value, actualCard.getPhone());
        assertEquals(expectedCalendarEvent.getEmail().value, actualCard.getEmail());
        assertEquals(expectedCalendarEvent.getAddress().value, actualCard.getAddress());
        assertEquals(expectedCalendarEvent.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code eventListPanelHandle} displays the details of {@code calendarEvents} correctly and
     * in the correct order.
     */
    public static void assertListMatching(EventListPanelHandle eventListPanelHandle, CalendarEvent... calendarEvents) {
        for (int i = 0; i < calendarEvents.length; i++) {
            eventListPanelHandle.navigateToCard(i);
            assertCardDisplaysPerson(calendarEvents[i], eventListPanelHandle.getEventCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code eventListPanelHandle} displays the details of {@code calendarEvents} correctly and
     * in the correct order.
     */
    public static void assertListMatching(EventListPanelHandle eventListPanelHandle, List<CalendarEvent> calendarEvents) {
        assertListMatching(eventListPanelHandle, calendarEvents.toArray(new CalendarEvent[0]));
    }

    /**
     * Asserts the size of the list in {@code eventListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(EventListPanelHandle eventListPanelHandle, int size) {
        int numberOfPeople = eventListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
