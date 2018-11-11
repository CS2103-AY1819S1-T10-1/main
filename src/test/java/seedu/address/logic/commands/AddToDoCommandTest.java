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
import seedu.address.model.ModelToDo;
import seedu.address.model.ReadOnlyToDoList;
import seedu.address.model.ToDoList;
import seedu.address.model.todolist.ToDoListEvent;
import seedu.address.testutil.ToDoListEventBuilder;

public class AddToDoCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullToDoListEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddToDoCommand(null);
    }

    @Test
    public void execute_toDoListEventAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingToDoAdded modelStub = new ModelStubAcceptingToDoAdded();
        ToDoListEvent validToDoListEvent = new ToDoListEventBuilder().build();

        CommandResult commandResult = new AddToDoCommand(validToDoListEvent).execute(modelStub, commandHistory);

        assertEquals(String.format(AddToDoCommand.MESSAGE_SUCCESS, validToDoListEvent), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validToDoListEvent), modelStub.toDoListEventsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateToDoListEvent_throwsCommandException() throws Exception {
        ToDoListEvent validToDoListEvent = new ToDoListEventBuilder().build();
        AddToDoCommand addToDoCommand = new AddToDoCommand(validToDoListEvent);
        ModelStub modelStub = new ModelStubWithToDoEvent(validToDoListEvent);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddToDoCommand.MESSAGE_DUPLICATE_TODO_EVENT);
        addToDoCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        ToDoListEvent project = new ToDoListEventBuilder().withTitle("CS2103 Project").build();
        ToDoListEvent demo = new ToDoListEventBuilder().withTitle("CS2103 demo").build();
        AddToDoCommand addProjectCommand = new AddToDoCommand(project);
        AddToDoCommand addDemoCommand = new AddToDoCommand(demo);

        // same object -> returns true
        assertTrue(addProjectCommand.equals(addProjectCommand));

        // same values -> returns true
        AddToDoCommand addProjectCommandCopy = new AddToDoCommand(project);
        assertTrue(addProjectCommand.equals(addProjectCommandCopy));

        // different types -> returns false
        assertFalse(addProjectCommand.equals(1));

        // null -> returns false
        assertFalse(addProjectCommand.equals(null));

        // different todolistevent -> returns false
        assertFalse(addProjectCommand.equals(addDemoCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements ModelToDo {
        @Override
        public void addToDoListEvent(ToDoListEvent toDoListEvent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyToDoList newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyToDoList getToDoList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasToDoListEvent(ToDoListEvent toDoListEvent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteToDoListEvent(ToDoListEvent target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateToDoListEvent(ToDoListEvent target, ToDoListEvent editedToDoListEvent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<ToDoListEvent> getFilteredToDoListEventList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredToDoListEventList(Predicate<ToDoListEvent> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitToDoList() {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that contains a single todolistevent.
     */
    private class ModelStubWithToDoEvent extends ModelStub {
        private final ToDoListEvent toDoListEvent;

        ModelStubWithToDoEvent(ToDoListEvent toDoListEvent) {
            requireNonNull(toDoListEvent);
            this.toDoListEvent = toDoListEvent;
        }

        @Override
        public boolean hasToDoListEvent(ToDoListEvent toDoListEvent) {
            requireNonNull(toDoListEvent);
            return this.toDoListEvent.isSameToDoListEvent(toDoListEvent);
        }
    }

    /**
     * A Model stub that always accept the todolistevent being added.
     */
    private class ModelStubAcceptingToDoAdded extends ModelStub {
        final ArrayList<ToDoListEvent> toDoListEventsAdded = new ArrayList<>();

        @Override
        public boolean hasToDoListEvent(ToDoListEvent toDoListEvent) {
            requireNonNull(toDoListEvent);
            return toDoListEventsAdded.stream().anyMatch(toDoListEvent::isSameToDoListEvent);
        }

        @Override
        public void addToDoListEvent(ToDoListEvent toDoListEvent) {
            requireNonNull(toDoListEvent);
            toDoListEventsAdded.add(toDoListEvent);
        }

        @Override
        public ReadOnlyToDoList getToDoList() {
            return new ToDoList();
        }
    }

}
