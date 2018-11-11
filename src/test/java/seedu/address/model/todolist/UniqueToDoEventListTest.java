package seedu.address.model.todolist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_MIDTERM;
import static seedu.address.testutil.TypicalTodoListEvents.MIDTERM;
import static seedu.address.testutil.TypicalTodoListEvents.TUTORIAL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.todolist.exceptions.DuplicateToDoListEventException;
import seedu.address.model.todolist.exceptions.ToDoListEventNotFoundException;
import seedu.address.testutil.ToDoListEventBuilder;

public class UniqueToDoEventListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private final UniqueToDoEventList uniqueToDoEventList = new UniqueToDoEventList();

    @Test
    public void contains_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueToDoEventList.contains(null);
    }

    @Test
    public void contains_eventNotInList_returnsFalse() {
        assertFalse(uniqueToDoEventList.contains(MIDTERM));
    }

    @Test
    public void contains_eventInList_returnsTrue() {
        uniqueToDoEventList.add(MIDTERM);
        assertTrue(uniqueToDoEventList.contains(MIDTERM));
    }

    @Test
    public void contains_eventWithSameIdentityFieldsInList_returnsTrue() {
        uniqueToDoEventList.add(MIDTERM);
        ToDoListEvent editedMidterm =
            new ToDoListEventBuilder(MIDTERM).withDescription(VALID_DESCRIPTION_MIDTERM)
                .build();
        assertTrue(uniqueToDoEventList.contains(editedMidterm));
    }

    @Test
    public void add_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueToDoEventList.add(null);
    }

    @Test
    public void add_duplicateEvent_throwsDuplicatePersonException() {
        uniqueToDoEventList.add(MIDTERM);
        thrown.expect(DuplicateToDoListEventException.class);
        uniqueToDoEventList.add(MIDTERM);
    }

    @Test
    public void setEvent_nullTargetEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueToDoEventList.setToDoListEvent(null, MIDTERM);
    }

    @Test
    public void setEvent_nullEditedEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueToDoEventList.setToDoListEvent(MIDTERM, null);
    }

    @Test
    public void setEvent_targetEventNotInList_throwsEventNotFoundException() {
        thrown.expect(ToDoListEventNotFoundException.class);
        uniqueToDoEventList.setToDoListEvent(MIDTERM, MIDTERM);
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueToDoEventList.add(MIDTERM);
        uniqueToDoEventList.setToDoListEvent(MIDTERM, MIDTERM);
        UniqueToDoEventList expectedUniqueToDoListEventList = new UniqueToDoEventList();
        expectedUniqueToDoListEventList.add(MIDTERM);
        assertEquals(expectedUniqueToDoListEventList, uniqueToDoEventList);
    }

    @Test
    public void setEvent_editedEventHasSameIdentity_success() {
        uniqueToDoEventList.add(MIDTERM);
        ToDoListEvent editedMidterm =
            new ToDoListEventBuilder(MIDTERM).withDescription(VALID_DESCRIPTION_MIDTERM).build();
        uniqueToDoEventList.setToDoListEvent(MIDTERM, editedMidterm);
        UniqueToDoEventList expectedUniqueToDoListEventList = new UniqueToDoEventList();
        expectedUniqueToDoListEventList.add(editedMidterm);
        assertEquals(expectedUniqueToDoListEventList, uniqueToDoEventList);
    }

    @Test
    public void setEvent_editedEventHasDifferentIdentity_success() {
        uniqueToDoEventList.add(MIDTERM);
        uniqueToDoEventList.setToDoListEvent(MIDTERM, TUTORIAL);
        UniqueToDoEventList expectedUniqueToDoListEventList = new UniqueToDoEventList();
        expectedUniqueToDoListEventList.add(TUTORIAL);
        assertEquals(expectedUniqueToDoListEventList, uniqueToDoEventList);
    }

    @Test
    public void setEvent_editedEventHasNonUniqueIdentity_throwsDuplicateEventException() {
        uniqueToDoEventList.add(MIDTERM);
        uniqueToDoEventList.add(TUTORIAL);
        thrown.expect(DuplicateToDoListEventException.class);
        uniqueToDoEventList.setToDoListEvent(MIDTERM, TUTORIAL);
    }

    @Test
    public void remove_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueToDoEventList.remove(null);
    }

    @Test
    public void remove_eventDoesNotExist_throwsPersonNotFoundException() {
        thrown.expect(ToDoListEventNotFoundException.class);
        uniqueToDoEventList.remove(MIDTERM);
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueToDoEventList.add(MIDTERM);
        uniqueToDoEventList.remove(MIDTERM);
        UniqueToDoEventList expectedUniqueToDoListEventList = new UniqueToDoEventList();
        assertEquals(expectedUniqueToDoListEventList, uniqueToDoEventList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueToDoEventList.setToDoListEvents((UniqueToDoEventList) null);
    }

    @Test
    public void setEvents_uniqueEventsList_replacesOwnListWithProvidedUniqueEventList() {
        uniqueToDoEventList.add(MIDTERM);
        UniqueToDoEventList expectedUniqueToDoListEventList = new UniqueToDoEventList();
        expectedUniqueToDoListEventList.add(TUTORIAL);
        uniqueToDoEventList.setToDoListEvents(expectedUniqueToDoListEventList);
        assertEquals(expectedUniqueToDoListEventList, uniqueToDoEventList);
    }

    @Test
    public void setEvents_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueToDoEventList.setToDoListEvents((List<ToDoListEvent>) null);
    }

    @Test
    public void setEvents_list_replacesOwnListWithProvidedList() {
        uniqueToDoEventList.add(MIDTERM);
        List<ToDoListEvent> toDoListEventList = Collections.singletonList(TUTORIAL);
        uniqueToDoEventList.setToDoListEvents(toDoListEventList);
        UniqueToDoEventList expectedUniqueToDoListEventList = new UniqueToDoEventList();
        expectedUniqueToDoListEventList.add(TUTORIAL);
        assertEquals(expectedUniqueToDoListEventList, uniqueToDoEventList);
    }

    @Test
    public void setEvents_listWithDuplicateEvents_throwsDuplicateEventException() {
        List<ToDoListEvent> listWithDuplicateToDoListEvents = Arrays.asList(MIDTERM, MIDTERM);
        thrown.expect(DuplicateToDoListEventException.class);
        uniqueToDoEventList.setToDoListEvents(listWithDuplicateToDoListEvents);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueToDoEventList.asUnmodifiableObservableList().remove(0);
    }
}
