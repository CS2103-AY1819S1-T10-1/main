package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.CalendarEventCardHandle;
import guitests.guihandles.CalendarPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.calendarevent.CalendarEvent;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(CalendarEventCardHandle expectedCard, CalendarEventCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getVenue(), actualCard.getVenue());
        assertEquals(expectedCard.getTitle(), actualCard.getTitle());
        assertEquals(expectedCard.getDescription(), actualCard.getDescription());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedCalendarEvent}.
     */
    public static void assertCardDisplaysPerson(CalendarEvent expectedCalendarEvent,
                                                CalendarEventCardHandle actualCard) {
        assertEquals(expectedCalendarEvent.getTitle().value, actualCard.getTitle());
        assertEquals(expectedCalendarEvent.getDescription().value, actualCard.getDescription());
        assertEquals(expectedCalendarEvent.getVenue().value, actualCard.getVenue());
        assertEquals(expectedCalendarEvent.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
            actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code calendarPanelHandle} displays the details of {@code calendarEvents}
     * correctly and
     * in the correct order.
     */
    public static void assertListMatching(CalendarPanelHandle calendarPanelHandle,
                                          CalendarEvent... calendarEvents) {
        for (int i = 0; i < calendarEvents.length; i++) {
            calendarPanelHandle.navigateToCard(i);
            assertCardDisplaysPerson(calendarEvents[i], calendarPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code calendarPanelHandle} displays the details of {@code calendarEvents}
     * correctly and
     * in the correct order.
     */
    public static void assertListMatching(CalendarPanelHandle calendarPanelHandle,
                                          List<CalendarEvent> calendarEvents) {
        assertListMatching(calendarPanelHandle, calendarEvents.toArray(new CalendarEvent[0]));
    }

    /**
     * Asserts the size of the list in {@code calendarPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(CalendarPanelHandle calendarPanelHandle, int size) {
        int numberOfPeople = calendarPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
