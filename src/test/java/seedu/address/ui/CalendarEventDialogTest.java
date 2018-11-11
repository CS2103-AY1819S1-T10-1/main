package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertDialogDisplaysCalendarEvent;

import org.junit.Test;

import guitests.guihandles.CalendarEventDialogHandle;
import seedu.address.model.calendarevent.CalendarEvent;
import seedu.address.testutil.CalendarEventBuilder;

public class CalendarEventDialogTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        CalendarEvent calendarEventWithNoTags = new CalendarEventBuilder().withTags(new String[0]).build();
        CalendarEventDialog calendarEventDialog = new CalendarEventDialog(calendarEventWithNoTags);
        uiPartRule.setUiPart(calendarEventDialog);
        assertCardDisplay(calendarEventDialog, calendarEventWithNoTags);

        // with tags
        CalendarEvent calendarEventWithTags = new CalendarEventBuilder().build();
        calendarEventDialog = new CalendarEventDialog(calendarEventWithTags);
        uiPartRule.setUiPart(calendarEventDialog);
        assertCardDisplay(calendarEventDialog, calendarEventWithTags);
    }

    @Test
    public void equals() {
        CalendarEvent calendarEvent = new CalendarEventBuilder().build();
        CalendarEventDialog calendarEventDialog = new CalendarEventDialog(calendarEvent);

        // same calendar event -> returns true
        CalendarEventDialog copy = new CalendarEventDialog(calendarEvent);
        assertTrue(calendarEventDialog.equals(copy));

        // same object -> returns true
        assertTrue(calendarEventDialog.equals(calendarEventDialog));

        // null -> returns false
        assertFalse(calendarEventDialog.equals(null));

        // different types -> returns false
        assertFalse(calendarEventDialog.equals(0));
    }

    /**
     * Asserts that {@code calendarEventDialog} displays the details of {@code expectedCalendarEvent} correctly and
     * matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(CalendarEventDialog calendarEventDialog, CalendarEvent expectedCalendarEvent) {
        guiRobot.pauseForHuman();
        CalendarEventDialogHandle calendarEventDialogHandle =
            new CalendarEventDialogHandle(calendarEventDialog.getRoot());
        assertDialogDisplaysCalendarEvent(expectedCalendarEvent, calendarEventDialogHandle);
    }
}

