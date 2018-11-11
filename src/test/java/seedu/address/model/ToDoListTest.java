package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_LECTURE;
import static seedu.address.testutil.TypicalTodoListEvents.LECTURE;
import static seedu.address.testutil.TypicalTodoListEvents.getTypicalToDoList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.todolist.ToDoListEvent;
import seedu.address.model.todolist.exceptions.DuplicateToDoListEventException;
import seedu.address.testutil.ToDoListEventBuilder;

public class ToDoListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private final ToDoList toDoList = new ToDoList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), toDoList.getToDoList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        toDoList.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyToDoList_replacesData() {
        ToDoList newData = getTypicalToDoList();
        toDoList.resetData(newData);
        assertEquals(newData, toDoList);
    }

    @Test
    public void resetData_withDuplicateToDo_throwsDuplicateToDoException() {
        // Two toDoListEvent with the same identity fields
        ToDoListEvent editedLecture =
            new ToDoListEventBuilder(LECTURE).withDescription(VALID_DESCRIPTION_LECTURE)
                .build();
        List<ToDoListEvent> newToDoListEvents = Arrays.asList(LECTURE, editedLecture);
        ToDoListStub newData = new ToDoListStub(newToDoListEvents);

        thrown.expect(DuplicateToDoListEventException.class);
        toDoList.resetData(newData);
    }

    @Test
    public void hasToDo_nullToDo_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        toDoList.hasToDoListEvent(null);
    }

    @Test
    public void hasToDo_toDoNotInToDoList_returnsFalse() {
        assertFalse(toDoList.hasToDoListEvent(LECTURE));
    }

    @Test
    public void hasToDo_personInToDoList_returnsTrue() {
        toDoList.addToDoListEvent(LECTURE);
        assertTrue(toDoList.hasToDoListEvent(LECTURE));
    }

    @Test
    public void hasToDo_todoWithSameIdentityFieldsInToDoList_returnsTrue() {
        toDoList.addToDoListEvent(LECTURE);
        ToDoListEvent editedLecture =
            new ToDoListEventBuilder(LECTURE).withDescription(VALID_DESCRIPTION_LECTURE)
                .build();
        assertTrue(toDoList.hasToDoListEvent(editedLecture));
    }

    @Test
    public void getToDoList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        toDoList.getToDoList().remove(0);
    }

    /**
     * A stub ReadOnlyToDoList whose toDoListEvents list can violate interface constraints.
     */
    private static class ToDoListStub implements ReadOnlyToDoList {
        private final ObservableList<ToDoListEvent> toDoListEvents = FXCollections.observableArrayList();

        ToDoListStub(Collection<ToDoListEvent> toDoListEvents) {
            this.toDoListEvents.setAll(toDoListEvents);
        }

        public ObservableList<ToDoListEvent> getToDoList() {
            return toDoListEvents;
        }
    }

}
