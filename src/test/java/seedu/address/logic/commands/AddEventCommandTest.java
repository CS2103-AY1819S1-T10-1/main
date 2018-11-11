package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyScheduler;
import seedu.address.model.Scheduler;
import seedu.address.model.calendarevent.CalendarEvent;
import seedu.address.model.calendarevent.FsList;
import seedu.address.testutil.CalendarEventBuilder;

public class AddEventCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullCalendarEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddEventCommand(null);
    }

    @Test
    public void execute_calendarEventAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingCalendarEventAdded modelStub = new ModelStubAcceptingCalendarEventAdded();
        CalendarEvent validCalendarEvent = new CalendarEventBuilder().build();

        CommandResult commandResult = new AddEventCommand(validCalendarEvent).execute(modelStub, commandHistory);

        assertEquals(String.format(AddEventCommand.MESSAGE_SUCCESS, validCalendarEvent), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validCalendarEvent), modelStub.calendarEventsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateCalendarEvent_throwsCommandException() throws Exception {
        CalendarEvent validCalendarEvent = new CalendarEventBuilder().build();
        AddEventCommand addEventCommand = new AddEventCommand(validCalendarEvent);
        ModelStub modelStub = new ModelStubWithEvent(validCalendarEvent);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddEventCommand.MESSAGE_DUPLICATE_CALENDAR_EVENT);
        addEventCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        CalendarEvent lecture = new CalendarEventBuilder().withTitle("Lecture").build();
        CalendarEvent tutorial = new CalendarEventBuilder().withTitle("Tutorial").build();
        AddEventCommand addLectureCommand = new AddEventCommand(lecture);
        AddEventCommand addTutorialCommand = new AddEventCommand(tutorial);

        // same object -> returns true
        assertTrue(addLectureCommand.equals(addLectureCommand));

        // same values -> returns true
        AddEventCommand addAliceCommandCopy = new AddEventCommand(lecture);
        assertTrue(addLectureCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addLectureCommand.equals(1));

        // null -> returns false
        assertFalse(addLectureCommand.equals(null));

        // different calendarevent -> returns false
        assertFalse(addLectureCommand.equals(addTutorialCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addCalendarEvent(CalendarEvent calendarEvent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyScheduler newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyScheduler getScheduler() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCalendarEvent(CalendarEvent calendarEvent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCalendarEvent(CalendarEvent target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateCalendarEvent(CalendarEvent target, CalendarEvent editedCalendarEvent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<CalendarEvent> getFullCalendarEventList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<CalendarEvent> getFilteredCalendarEventList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPredicate(Predicate<CalendarEvent> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCalendarEventList(Predicate<CalendarEvent> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortFilteredCalendarEventList(Comparator<CalendarEvent> comparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void clearAllPredicatesAndComparators() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public FsList getFsList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoScheduler() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoScheduler() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoScheduler() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoScheduler() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitScheduler() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single calendar event.
     */
    private class ModelStubWithEvent extends ModelStub {
        private final CalendarEvent calendarEvent;

        ModelStubWithEvent(CalendarEvent calendarEvent) {
            requireNonNull(calendarEvent);
            this.calendarEvent = calendarEvent;
        }

        @Override
        public boolean hasCalendarEvent(CalendarEvent calendarEvent) {
            requireNonNull(calendarEvent);
            return this.calendarEvent.isSameCalendarEvent(calendarEvent);
        }
    }

    /**
     * A Model stub that always accepts the calendar event being added.
     */
    private class ModelStubAcceptingCalendarEventAdded extends ModelStub {
        final ArrayList<CalendarEvent> calendarEventsAdded = new ArrayList<>();

        @Override
        public boolean hasCalendarEvent(CalendarEvent calendarEvent) {
            requireNonNull(calendarEvent);
            return calendarEventsAdded.stream().anyMatch(calendarEvent::isSameCalendarEvent);
        }

        @Override
        public void addCalendarEvent(CalendarEvent calendarEvent) {
            requireNonNull(calendarEvent);
            calendarEventsAdded.add(calendarEvent);
        }

        @Override
        public void commitScheduler() {
            // called by {@code AddEventCommand#execute()}
        }

        @Override
        public ReadOnlyScheduler getScheduler() {
            return new Scheduler();
        }
    }

}
