package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.CalendarEventCardHandle;
import guitests.guihandles.CalendarEventListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.ToDoListEventCardHandle;
import seedu.address.model.calendarevent.CalendarEvent;
import seedu.address.model.todolist.ToDoListEvent;

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
     * Asserts that {@code actualToDoListCard} displays the same values as {@code expectedToDoListCard}.
     */
    public static void assertToDoListCardEquals(ToDoListEventCardHandle expectedCard, ToDoListEventCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getPriority(), actualCard.getPriority());
        assertEquals(expectedCard.getTitle(), actualCard.getTitle());
        assertEquals(expectedCard.getDescription(), actualCard.getDescription());
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
     * Asserts that {@code actualCard} displays the details of {@code expectedToDoListEvent}.
     */
    public static void assertCardDisplaysToDoListEvent(ToDoListEvent expectedToDoListEvent,
                                                       ToDoListEventCardHandle actualCard) {
        assertEquals(expectedToDoListEvent.getTitle().value, actualCard.getTitle());
        assertEquals(expectedToDoListEvent.getDescription().value, actualCard.getDescription());
        assertEquals(expectedToDoListEvent.getPriority().value, actualCard.getPriority());
    }

    /**
     * Asserts that the list in {@code calendarEventListPanelHandle} displays the details of {@code calendarEvents}
     * correctly and
     * in the correct order.
     */
    public static void assertListMatching(CalendarEventListPanelHandle calendarEventListPanelHandle,
                                          CalendarEvent... calendarEvents) {
        for (int i = 0; i < calendarEvents.length; i++) {
            calendarEventListPanelHandle.navigateToCard(i);
            assertCardDisplaysPerson(calendarEvents[i], calendarEventListPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code calendarEventListPanelHandle} displays the details of {@code calendarEvents}
     * correctly and
     * in the correct order.
     */
    public static void assertListMatching(CalendarEventListPanelHandle calendarEventListPanelHandle,
                                          List<CalendarEvent> calendarEvents) {
        assertListMatching(calendarEventListPanelHandle, calendarEvents.toArray(new CalendarEvent[0]));
    }

    /**
     * Asserts the size of the list in {@code calendarEventListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(CalendarEventListPanelHandle calendarEventListPanelHandle, int size) {
        int numberOfPeople = calendarEventListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
