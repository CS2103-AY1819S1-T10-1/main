package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyScheduler;
import seedu.address.model.Scheduler;
import seedu.address.model.Model;
import seedu.address.model.calendarevent.CalendarEvent;
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        CalendarEvent validCalendarEvent = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validCalendarEvent).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validCalendarEvent), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validCalendarEvent), modelStub.personsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        CalendarEvent validCalendarEvent = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validCalendarEvent);
        ModelStub modelStub = new ModelStubWithPerson(validCalendarEvent);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        CalendarEvent alice = new PersonBuilder().withName("Alice").build();
        CalendarEvent bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different calendarevent -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
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
        public ObservableList<CalendarEvent> getFilteredCalendarEventList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCalendarEventList(Predicate<CalendarEvent> predicate) {
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
     * A Model stub that contains a single calendarevent.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final CalendarEvent calendarEvent;

        ModelStubWithPerson(CalendarEvent calendarEvent) {
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
     * A Model stub that always accept the calendarevent being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<CalendarEvent> personsAdded = new ArrayList<>();

        @Override
        public boolean hasCalendarEvent(CalendarEvent calendarEvent) {
            requireNonNull(calendarEvent);
            return personsAdded.stream().anyMatch(calendarEvent::isSameCalendarEvent);
        }

        @Override
        public void addCalendarEvent(CalendarEvent calendarEvent) {
            requireNonNull(calendarEvent);
            personsAdded.add(calendarEvent);
        }

        @Override
        public void commitScheduler() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyScheduler getScheduler() {
            return new Scheduler();
        }
    }

}
