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
import seedu.address.model.Schedule;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlySchedule;
import seedu.address.model.person.Event;
import seedu.address.testutil.EventBuilder;

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
    public void execute_eventAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        Event validEvent = new EventBuilder().build();

        CommandResult commandResult = new AddCommand(validEvent).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validEvent), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validEvent), modelStub.eventsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() throws Exception {
        Event validEvent = new EventBuilder().build();
        AddCommand addCommand = new AddCommand(validEvent);
        ModelStub modelStub = new ModelStubWithEvent(validEvent);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_EVENT);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Event midterm = new EventBuilder().withName("Midterm").build();
        Event workshop = new EventBuilder().withName("Workshop").build();
        AddCommand addMidtermCommand = new AddCommand(midterm);
        AddCommand addWorkshopCommand = new AddCommand(workshop);

        // same object -> returns true
        assertTrue(addMidtermCommand.equals(addMidtermCommand));

        // same values -> returns true
        AddCommand addMidtermCommandCopy = new AddCommand(midterm);
        assertTrue(addMidtermCommand.equals(addMidtermCommandCopy));

        // different types -> returns false
        assertFalse(addMidtermCommand.equals(1));

        // null -> returns false
        assertFalse(addMidtermCommand.equals(null));

        // different event -> returns false
        assertFalse(addMidtermCommand.equals(addWorkshopCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlySchedule newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlySchedule getSchedule() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEvent(Event target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateEvent(Event target, Event editedEvent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Event> getFilteredEventList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredEventList(Predicate<Event> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoSchedule() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoSchedule() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoSchedule() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoSchedule() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitSchedule() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single event.
     */
    private class ModelStubWithEvent extends ModelStub {
        private final Event event;

        ModelStubWithEvent(Event event) {
            requireNonNull(event);
            this.event = event;
        }

        @Override
        public boolean hasEvent(Event event) {
            requireNonNull(event);
            return this.event.isSameEvent(event);
        }
    }

    /**
     * A Model stub that always accept the event being added.
     */
    private class ModelStubAcceptingEventAdded extends ModelStub {
        final ArrayList<Event> eventsAdded = new ArrayList<>();

        @Override
        public boolean hasEvent(Event event) {
            requireNonNull(event);
            return eventsAdded.stream().anyMatch(event::isSameEvent);
        }

        @Override
        public void addEvent(Event event) {
            requireNonNull(event);
            eventsAdded.add(event);
        }

        @Override
        public void commitSchedule() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlySchedule getSchedule() {
            return new Schedule();
        }
    }

}
