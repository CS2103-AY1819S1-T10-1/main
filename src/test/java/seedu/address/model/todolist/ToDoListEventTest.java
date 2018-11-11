package seedu.address.model.todolist;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_TUTORIAL;
import static seedu.address.testutil.TypicalTodoListEvents.MIDTERM;
import static seedu.address.testutil.TypicalTodoListEvents.TUTORIAL;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.ToDoListEventBuilder;

public class ToDoListEventTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameTodoListEvent() {
        // same object -> returns true
        assertTrue(MIDTERM.isSameToDoListEvent(MIDTERM));

        // null -> returns false
        assertFalse(MIDTERM.isSameToDoListEvent(null));

        // different description -> returns false
        ToDoListEvent editedMidterm =
            new ToDoListEventBuilder(MIDTERM).withDescription(VALID_DESCRIPTION_TUTORIAL).build();
        assertFalse(MIDTERM.isSameToDoListEvent(editedMidterm));

        // different title -> returns false
        editedMidterm = new ToDoListEventBuilder(MIDTERM).withTitle(VALID_TITLE_TUTORIAL).build();
        assertFalse(MIDTERM.isSameToDoListEvent(editedMidterm));

        // same title, different description, different priority -> returns false
        editedMidterm =
            new ToDoListEventBuilder(MIDTERM)
                .withDescription(VALID_DESCRIPTION_TUTORIAL)
                .withPriority(VALID_PRIORITY_TUTORIAL)
                .build();
        assertFalse(MIDTERM.isSameToDoListEvent(editedMidterm));

        // same title, same description, different priority -> returns true
        editedMidterm =
            new ToDoListEventBuilder(MIDTERM).withPriority(VALID_PRIORITY_TUTORIAL).build();
        assertTrue(MIDTERM.isSameToDoListEvent(editedMidterm));
    }

    @Test
    public void equals() {
        // same values -> returns true
        ToDoListEvent midtermCopy = new ToDoListEventBuilder(MIDTERM).build();
        assertTrue(MIDTERM.equals(midtermCopy));

        // same object -> returns true
        assertTrue(MIDTERM.equals(MIDTERM));

        // null -> returns false
        assertFalse(MIDTERM.equals(null));

        // different type -> returns false
        assertFalse(MIDTERM.equals(5));

        // different todoListEvents -> returns false
        assertFalse(MIDTERM.equals(TUTORIAL));

        // different title -> returns false
        ToDoListEvent editedMidterm = new ToDoListEventBuilder(MIDTERM).withTitle(VALID_TITLE_TUTORIAL).build();
        assertFalse(MIDTERM.equals(editedMidterm));

        // different description -> returns false
        editedMidterm = new ToDoListEventBuilder(MIDTERM).withDescription(VALID_DESCRIPTION_TUTORIAL).build();
        assertFalse(MIDTERM.equals(editedMidterm));

        // different priority -> returns false
        editedMidterm = new ToDoListEventBuilder(MIDTERM).withPriority(VALID_PRIORITY_TUTORIAL).build();
        assertFalse(MIDTERM.equals(editedMidterm));

    }
}
