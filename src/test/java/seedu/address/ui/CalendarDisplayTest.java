package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalEvents.getTypicalCalendarEvents;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.CalendarDisplayHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.KeyCode;
import seedu.address.model.calendarevent.CalendarEvent;

public class CalendarDisplayTest extends GuiUnitTest {
    private static final ObservableList<CalendarEvent> TYPICAL_CALENDAR_EVENTS =
            FXCollections.observableList(getTypicalCalendarEvents());
    private CalendarEvent typicalCalendarEvent = TYPICAL_CALENDAR_EVENTS.get(TYPICAL_CALENDAR_EVENTS.size() - 1);

    private CalendarDisplayHandle calendarDisplayHandle; // display handle is the VBox, not agenda
    private LocalDateTime initialLocalDateTime;

    @Before
    public void setUp() {
        CalendarDisplay calendarDisplay = new CalendarDisplay(TYPICAL_CALENDAR_EVENTS);
        initialLocalDateTime = LocalDateTime.now().withNano(0);
        calendarDisplay.setDisplayedDateTime(initialLocalDateTime);
        calendarDisplayHandle = new CalendarDisplayHandle(
                getChildNode(calendarDisplay.getRoot(), CalendarDisplayHandle.CALENDAR_DISPLAY_BOX_ID));
        uiPartRule.setUiPart(calendarDisplay);
    }

    /**
     * Test changing the calendar view
     */
    @Test
    public void calendarDisplay_toggleView() {
        // change from weekly to daily
        assertTrue(calendarDisplayHandle.isWeeklyView());
        guiRobot.push(KeyCode.T);
        assertTrue(calendarDisplayHandle.isDailyView());

        // change from daily to weekly
        guiRobot.push(KeyCode.T);
        assertTrue(calendarDisplayHandle.isWeeklyView());
    }

    /**
     * Test navigation
     */
    @Test
    public void calendarDisplay_displayNextAndPreviousWeek() {
        assertTrue(calendarDisplayHandle.isWeeklyView());
        // go to next week
        guiRobot.push(KeyCode.RIGHT);
        LocalDateTime expected = initialLocalDateTime.plusWeeks(1);
        LocalDateTime actual = calendarDisplayHandle.getDisplayedLocalDateTime();
        assertEquals(expected, actual);
        // go to previous week
        guiRobot.push(KeyCode.LEFT);
        expected = initialLocalDateTime;
        actual = calendarDisplayHandle.getDisplayedLocalDateTime();
        assertEquals(expected, actual);
    }

    @Test
    public void calendarDisplay_displayNextandPreviousDay() {
        guiRobot.push(KeyCode.T);
        assertTrue(calendarDisplayHandle.isDailyView());
        // go to next day
        guiRobot.push(KeyCode.RIGHT);
        LocalDateTime expected = initialLocalDateTime.plusDays(1);
        LocalDateTime actual = calendarDisplayHandle.getDisplayedLocalDateTime();
        assertEquals(expected, actual);
        // go to prev day
        guiRobot.push(KeyCode.LEFT);
        expected = initialLocalDateTime;
        actual = calendarDisplayHandle.getDisplayedLocalDateTime();
        assertEquals(expected, actual);
        // set back to weekly view
        guiRobot.push(KeyCode.T);
        assertTrue(calendarDisplayHandle.isWeeklyView());
    }
}
