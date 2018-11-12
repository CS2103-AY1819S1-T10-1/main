package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LECTURE;
import static seedu.address.testutil.TypicalEvents.CS2103_LECTURE;
import static seedu.address.testutil.TypicalEvents.getTypicalScheduler;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.calendarevent.CalendarEvent;
import seedu.address.model.calendarevent.exceptions.DuplicateCalendarEventException;
import seedu.address.testutil.CalendarEventBuilder;

public class SchedulerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private final Scheduler scheduler = new Scheduler();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), scheduler.getCalendarEventList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        scheduler.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        Scheduler newData = getTypicalScheduler();
        scheduler.resetData(newData);
        assertEquals(newData, scheduler);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two calendarEvents with the same identity fields
        CalendarEvent editedLecture =
            new CalendarEventBuilder(CS2103_LECTURE).withTags(VALID_TAG_LECTURE)
                .build();
        List<CalendarEvent> newCalendarEvents = Arrays.asList(CS2103_LECTURE, editedLecture);
        SchedulerStub newData = new SchedulerStub(newCalendarEvents);

        thrown.expect(DuplicateCalendarEventException.class);
        scheduler.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        scheduler.hasCalendarEvent(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(scheduler.hasCalendarEvent(CS2103_LECTURE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        scheduler.addCalendarEvent(CS2103_LECTURE);
        assertTrue(scheduler.hasCalendarEvent(CS2103_LECTURE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        scheduler.addCalendarEvent(CS2103_LECTURE);
        CalendarEvent editedLecture =
            new CalendarEventBuilder(CS2103_LECTURE).withTags(VALID_TAG_LECTURE)
                .build();
        assertTrue(scheduler.hasCalendarEvent(editedLecture));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        scheduler.getCalendarEventList().remove(0);
    }

    /**
     * A stub ReadOnlyScheduler whose calendarEvents list can violate interface constraints.
     */
    private static class SchedulerStub implements ReadOnlyScheduler {
        private final ObservableList<CalendarEvent> calendarEvents = FXCollections.observableArrayList();

        SchedulerStub(Collection<CalendarEvent> calendarEvents) {
            this.calendarEvents.setAll(calendarEvents);
        }

        @Override
        public ObservableList<CalendarEvent> getCalendarEventList() {
            return calendarEvents;
        }
    }

}
