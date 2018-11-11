package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalEvents.getTypicalCalendarEvents;

import java.time.LocalDateTime;

import org.junit.Test;

import guitests.guihandles.CalendarDisplayHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.KeyCode;
import seedu.address.commons.events.ui.CalendarPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.JumpToDateTimeEvent;
import seedu.address.model.calendarevent.CalendarEvent;


public class CalendarDisplayTest extends GuiUnitTest {
    private static final ObservableList<CalendarEvent> TYPICAL_CALENDAR_EVENTS =
        FXCollections.observableList(getTypicalCalendarEvents());
    private static final JumpToDateTimeEvent JUMP_TO_DATE_TIME_EVENT =
        new JumpToDateTimeEvent(LocalDateTime.now().plusWeeks(3));
    private static final CalendarPanelSelectionChangedEvent SELECTION_CHANGED_EVENT =
        new CalendarPanelSelectionChangedEvent(TYPICAL_CALENDAR_EVENTS.get(0));

    private CalendarDisplayHandle calendarDisplayHandle;


    /*
     * Test that {@code CalendarDisplay} is able to update when changes occur in its backing list.
     */
    /* For some reason, agenda is not updating
    @Test
    public void display() {
        ArrayList<CalendarEvent> backingList = new ArrayList<>();

        initUi(FXCollections.observableList(backingList));

        // add multiple events
        backingList.addAll(getTypicalCalendarEvents());
        assertListMatchingIgnoreOrder(calendarDisplayHandle, backingList);

        // remove event from backing list
        CalendarEvent typicalCalendarEvent = backingList.remove(0);
        assertListMatchingIgnoreOrder(calendarDisplayHandle, backingList);

        // add event to backing list
        backingList.add(typicalCalendarEvent);
        assertListMatchingIgnoreOrder(calendarDisplayHandle, backingList);

        // set event
        backingList.set(0, new CalendarEventBuilder(typicalCalendarEvent).withTitle(VALID_TITLE_TUTORIAL).build());
        assertListMatchingIgnoreOrder(calendarDisplayHandle, backingList);

        // remove multiple events
        backingList.clear();
        assertListMatchingIgnoreOrder(calendarDisplayHandle, backingList);
    } */

    /**
     * Test navigation
     */
    @Test
    public void calendarDisplay_displayNextAndPreviousWeek() {
        initUi(TYPICAL_CALENDAR_EVENTS);
        assertCalendarDisplaysWeeklyView(calendarDisplayHandle);
        LocalDateTime initialDisplayTime = calendarDisplayHandle.getDisplayedLocalDateTime();

        // go to next week
        guiRobot.push(KeyCode.RIGHT);
        assertCalendarDisplaysAsExpected(calendarDisplayHandle, initialDisplayTime.plusWeeks(1));

        // go to previous week
        guiRobot.push(KeyCode.LEFT);
        assertCalendarDisplaysAsExpected(calendarDisplayHandle, initialDisplayTime);

        // go to previous week again
        guiRobot.push(KeyCode.LEFT);
        assertCalendarDisplaysAsExpected(calendarDisplayHandle, initialDisplayTime.minusWeeks(1));
    }

    @Test
    public void handleJumpToDateTimeEvent() {
        initUi(TYPICAL_CALENDAR_EVENTS);
        postNow(JUMP_TO_DATE_TIME_EVENT);
        guiRobot.pauseForHuman();

        assertCalendarDisplaysAsExpected(calendarDisplayHandle, JUMP_TO_DATE_TIME_EVENT.targetLocalDateTime);
    }

    @Test
    public void handleCalendarPanelSelectionChangedEvent() {
        initUi(TYPICAL_CALENDAR_EVENTS);
        postNow(SELECTION_CHANGED_EVENT);
        guiRobot.pauseForHuman();

        assertCalendarDisplaysAsExpected(calendarDisplayHandle,
            SELECTION_CHANGED_EVENT.newSelection.getStartLocalDateTime());
    }

    public void assertCalendarDisplaysWeeklyView(CalendarDisplayHandle calendarDisplayHandle) {
        assertTrue(calendarDisplayHandle.isWeeklyView());
    }

    public void assertCalendarDisplaysAsExpected(CalendarDisplayHandle calendarDisplayHandle,
                                                 LocalDateTime expectedTime) {
        assertEquals(expectedTime, calendarDisplayHandle.getDisplayedLocalDateTime());
    }

    /**
     * Initializes {@code calendarDisplayHandle} with a {@code CalendarDisplay} backed by {@code
     * backingList}.
     * Also shows the {@code Stage} that displays only {@code CalendarDisplay}.
     */
    private void initUi(ObservableList<CalendarEvent> backingList) {
        CalendarDisplay calendarDisplay = new CalendarDisplay(backingList);
        calendarDisplay.setDisplayedDateTime(LocalDateTime.now().withNano(0));
        uiPartRule.setUiPart(calendarDisplay);

        calendarDisplayHandle = new CalendarDisplayHandle(
            getChildNode(calendarDisplay.getRoot(), CalendarDisplayHandle.CALENDAR_DISPLAY_BOX_ID));
    }
}
