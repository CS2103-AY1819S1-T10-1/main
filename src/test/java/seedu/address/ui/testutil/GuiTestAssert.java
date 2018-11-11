package seedu.address.ui.testutil;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.CalendarDisplayHandle;
import guitests.guihandles.CalendarEventCardHandle;
import guitests.guihandles.CalendarEventDialogHandle;
import guitests.guihandles.CalendarPanelHandle;
import guitests.guihandles.DescriptionDisplayHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.TaskListPanelHandle;
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
    public static void assertCalendarEventCardEquals(CalendarEventCardHandle expectedCard,
                                                     CalendarEventCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getVenue(), actualCard.getVenue());
        assertEquals(expectedCard.getTitle(), actualCard.getTitle());
        assertEquals(expectedCard.getDescription(), actualCard.getDescription());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEqualsToDo(ToDoListEventCardHandle expectedCard, ToDoListEventCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getPriority(), actualCard.getPriority());
        assertEquals(expectedCard.getTitle(), actualCard.getTitle());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedCalendarEvent}.
     */
    public static void assertCardDisplaysPerson(CalendarEvent expectedCalendarEvent,
                                                CalendarEventCardHandle actualCard) {
        assertEquals(expectedCalendarEvent.getTitle().value, actualCard.getTitle());
        assertEquals(expectedCalendarEvent.getDescriptionObject().value, actualCard.getDescription());
        assertEquals(expectedCalendarEvent.getVenue().value, actualCard.getVenue());
        assertEquals(expectedCalendarEvent.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
            actualCard.getTags());
    }

    /**
     * Asserts that {@code actualDialog} displays the details of {@code expectedCalendarEvent}.
     */
    public static void assertDialogDisplaysCalendarEvent(CalendarEvent expectedCalendarEvent,
                                                         CalendarEventDialogHandle actualDialog) {
        assertEquals(expectedCalendarEvent.getTitle().value, actualDialog.getTitle());
        assertEquals(expectedCalendarEvent.getDescriptionObject().value, actualDialog.getDescription());
        assertEquals(expectedCalendarEvent.getVenue().value, actualDialog.getVenue());
        assertEquals(expectedCalendarEvent.getStartLocalDateTime().toString(), actualDialog.getDateTimeString());
    }

    /**
     * Asserts that {@code descriptionDisplayHandle} displays the details of {@code toDoListEvent}.
     */
    public static void assertDescriptionDisplayDisplaysToDoListEvent(ToDoListEvent expectedToDoListEvent,
                                                         DescriptionDisplayHandle descriptionDisplayHandle) {
        assertEquals(expectedToDoListEvent.getDescription().value, descriptionDisplayHandle.getDescription());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedToDoListEvent}.
     */
    public static void assertCardDisplaysToDo(ToDoListEvent expectedToDoListEvent,
                                              ToDoListEventCardHandle actualCard) {
        assertEquals(expectedToDoListEvent.getTitle().value, actualCard.getTitle());
        assertEquals(expectedToDoListEvent.getPriority().value, actualCard.getPriority());
    }

    /**
     * Asserts that the list in {@code calendarPanelHandle} displays the details of {@code calendarEvents}
     * correctly and
     * in the correct order.
     */
    public static void assertCalendarEventListMatching(CalendarPanelHandle calendarPanelHandle,
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
    public static void assertCalendarEventListMatching(CalendarPanelHandle calendarPanelHandle,
                                                       List<CalendarEvent> calendarEvents) {
        assertCalendarEventListMatching(calendarPanelHandle, calendarEvents.toArray(new CalendarEvent[0]));
    }

    /**
     * Asserts that the list in {@code calendarDisplayHandle} displays the details of {@code calendarEvents} correctly.
     */
    public static void assertCalendarListMatchingIgnoreOrder(CalendarDisplayHandle calendarDisplayHandle,
                                                             List<CalendarEvent> calendarEvents) {
        assertEquals(calendarDisplayHandle.getDisplayedCalendarEvents().size(), calendarEvents.size());
        assertTrue(calendarDisplayHandle.getDisplayedCalendarEvents().containsAll(calendarEvents));
    }

    /**
     * Asserts the size of the list in {@code calendarPanelHandle} equals to {@code size}.
     */
    public static void assertCalendarListSize(CalendarPanelHandle calendarPanelHandle, int size) {
        int numberOfPeople = calendarPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }

    /**
     * Asserts that the list in {@code taskListPanelHandle} displays the details of {@code toDoListEvents}
     * correctly and
     * in the correct order.
     */
    public static void assertToDoListMatching(TaskListPanelHandle taskListPanelHandle,
                                                  ToDoListEvent... toDoListEvents) {
        for (int i = 0; i < toDoListEvents.length; i++) {
            taskListPanelHandle.navigateToCard(i);
            assertCardDisplaysToDo(toDoListEvents[i], taskListPanelHandle.getToDoListCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code taskListPanelHandle} displays the details of {@code toDoListEvents}
     * correctly and
     * in the correct order.
     */
    public static void assertToDoListMatching(TaskListPanelHandle taskListPanelHandle,
                                              List<ToDoListEvent> toDoListEvents) {
        assertToDoListMatching(taskListPanelHandle, toDoListEvents.toArray(new ToDoListEvent[0]));
    }

    /**
     * Asserts that the list in {@code calendarDisplayHandle} displays the details of {@code calendarEvents}
     * correctly and
     * in the correct order.
     */
    public static void assertToDoListMatchingIgnoreOrder(CalendarDisplayHandle calendarDisplayHandle,
                                                             List<ToDoListEvent> toDoListEvents) {

        assertEquals(calendarDisplayHandle.getDisplayedCalendarEvents().size(), toDoListEvents.size());
        assertTrue(calendarDisplayHandle.getDisplayedCalendarEvents().containsAll(toDoListEvents));
    }

    /**
     * Asserts the size of the list in {@code taskListPanelHandle} equals to {@code size}.
     */
    public static void assertToDoListSize(TaskListPanelHandle taskListPanelHandle, int size) {
        int numberOfTask = taskListPanelHandle.getListSize();
        assertEquals(size, numberOfTask);
    }

}
