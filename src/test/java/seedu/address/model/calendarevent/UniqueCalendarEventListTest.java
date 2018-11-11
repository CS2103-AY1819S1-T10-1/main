package seedu.address.model.calendarevent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_TUTORIAL;
import static seedu.address.testutil.TypicalEvents.LECTURE;
import static seedu.address.testutil.TypicalEvents.TUTORIAL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.calendarevent.exceptions.CalendarEventNotFoundException;
import seedu.address.model.calendarevent.exceptions.DuplicateCalendarEventException;
import seedu.address.testutil.CalendarEventBuilder;

public class UniqueCalendarEventListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private final UniqueCalendarEventList uniqueCalendarEventList = new UniqueCalendarEventList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCalendarEventList.contains(null);
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueCalendarEventList.contains(LECTURE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueCalendarEventList.add(LECTURE);
        assertTrue(uniqueCalendarEventList.contains(LECTURE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueCalendarEventList.add(LECTURE);
        CalendarEvent editedLecture =
            new CalendarEventBuilder(LECTURE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueCalendarEventList.contains(editedLecture));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCalendarEventList.add(null);
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueCalendarEventList.add(LECTURE);
        thrown.expect(DuplicateCalendarEventException.class);
        uniqueCalendarEventList.add(LECTURE);
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCalendarEventList.setCalendarEvent(null, LECTURE);
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCalendarEventList.setCalendarEvent(LECTURE, null);
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        thrown.expect(CalendarEventNotFoundException.class);
        uniqueCalendarEventList.setCalendarEvent(LECTURE, LECTURE);
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueCalendarEventList.add(LECTURE);
        uniqueCalendarEventList.setCalendarEvent(LECTURE, LECTURE);
        UniqueCalendarEventList expectedUniqueCalendarEventList = new UniqueCalendarEventList();
        expectedUniqueCalendarEventList.add(LECTURE);
        assertEquals(expectedUniqueCalendarEventList, uniqueCalendarEventList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueCalendarEventList.add(LECTURE);
        CalendarEvent editedAlice =
            new CalendarEventBuilder(LECTURE).withVenue(VALID_VENUE_TUTORIAL).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueCalendarEventList.setCalendarEvent(LECTURE, editedAlice);
        UniqueCalendarEventList expectedUniqueCalendarEventList = new UniqueCalendarEventList();
        expectedUniqueCalendarEventList.add(editedAlice);
        assertEquals(expectedUniqueCalendarEventList, uniqueCalendarEventList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueCalendarEventList.add(LECTURE);
        uniqueCalendarEventList.setCalendarEvent(LECTURE, TUTORIAL);
        UniqueCalendarEventList expectedUniqueCalendarEventList = new UniqueCalendarEventList();
        expectedUniqueCalendarEventList.add(TUTORIAL);
        assertEquals(expectedUniqueCalendarEventList, uniqueCalendarEventList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueCalendarEventList.add(LECTURE);
        uniqueCalendarEventList.add(TUTORIAL);
        thrown.expect(DuplicateCalendarEventException.class);
        uniqueCalendarEventList.setCalendarEvent(LECTURE, TUTORIAL);
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCalendarEventList.remove(null);
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        thrown.expect(CalendarEventNotFoundException.class);
        uniqueCalendarEventList.remove(LECTURE);
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueCalendarEventList.add(LECTURE);
        uniqueCalendarEventList.remove(LECTURE);
        UniqueCalendarEventList expectedUniqueCalendarEventList = new UniqueCalendarEventList();
        assertEquals(expectedUniqueCalendarEventList, uniqueCalendarEventList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCalendarEventList.setCalendarEvents((UniqueCalendarEventList) null);
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueCalendarEventList.add(LECTURE);
        UniqueCalendarEventList expectedUniqueCalendarEventList = new UniqueCalendarEventList();
        expectedUniqueCalendarEventList.add(TUTORIAL);
        uniqueCalendarEventList.setCalendarEvents(expectedUniqueCalendarEventList);
        assertEquals(expectedUniqueCalendarEventList, uniqueCalendarEventList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCalendarEventList.setCalendarEvents((List<CalendarEvent>) null);
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueCalendarEventList.add(LECTURE);
        List<CalendarEvent> calendarEventList = Collections.singletonList(TUTORIAL);
        uniqueCalendarEventList.setCalendarEvents(calendarEventList);
        UniqueCalendarEventList expectedUniqueCalendarEventList = new UniqueCalendarEventList();
        expectedUniqueCalendarEventList.add(TUTORIAL);
        assertEquals(expectedUniqueCalendarEventList, uniqueCalendarEventList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<CalendarEvent> listWithDuplicateCalendarEvents = Arrays.asList(LECTURE, LECTURE);
        thrown.expect(DuplicateCalendarEventException.class);
        uniqueCalendarEventList.setCalendarEvents(listWithDuplicateCalendarEvents);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueCalendarEventList.asUnmodifiableObservableList().remove(0);
    }
}
