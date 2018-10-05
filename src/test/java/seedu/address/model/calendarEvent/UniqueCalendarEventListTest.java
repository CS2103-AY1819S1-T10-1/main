package seedu.address.model.calendarevent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.calendarevent.exceptions.DuplicateCalendarEventException;
import seedu.address.model.calendarevent.exceptions.CalendarEventNotFoundException;
import seedu.address.testutil.PersonBuilder;

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
        assertFalse(uniqueCalendarEventList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueCalendarEventList.add(ALICE);
        assertTrue(uniqueCalendarEventList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueCalendarEventList.add(ALICE);
        CalendarEvent editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueCalendarEventList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCalendarEventList.add(null);
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueCalendarEventList.add(ALICE);
        thrown.expect(DuplicateCalendarEventException.class);
        uniqueCalendarEventList.add(ALICE);
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCalendarEventList.setCalendarEvent(null, ALICE);
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCalendarEventList.setCalendarEvent(ALICE, null);
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        thrown.expect(CalendarEventNotFoundException.class);
        uniqueCalendarEventList.setCalendarEvent(ALICE, ALICE);
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueCalendarEventList.add(ALICE);
        uniqueCalendarEventList.setCalendarEvent(ALICE, ALICE);
        UniqueCalendarEventList expectedUniqueCalendarEventList = new UniqueCalendarEventList();
        expectedUniqueCalendarEventList.add(ALICE);
        assertEquals(expectedUniqueCalendarEventList, uniqueCalendarEventList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueCalendarEventList.add(ALICE);
        CalendarEvent editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueCalendarEventList.setCalendarEvent(ALICE, editedAlice);
        UniqueCalendarEventList expectedUniqueCalendarEventList = new UniqueCalendarEventList();
        expectedUniqueCalendarEventList.add(editedAlice);
        assertEquals(expectedUniqueCalendarEventList, uniqueCalendarEventList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueCalendarEventList.add(ALICE);
        uniqueCalendarEventList.setCalendarEvent(ALICE, BOB);
        UniqueCalendarEventList expectedUniqueCalendarEventList = new UniqueCalendarEventList();
        expectedUniqueCalendarEventList.add(BOB);
        assertEquals(expectedUniqueCalendarEventList, uniqueCalendarEventList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueCalendarEventList.add(ALICE);
        uniqueCalendarEventList.add(BOB);
        thrown.expect(DuplicateCalendarEventException.class);
        uniqueCalendarEventList.setCalendarEvent(ALICE, BOB);
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCalendarEventList.remove(null);
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        thrown.expect(CalendarEventNotFoundException.class);
        uniqueCalendarEventList.remove(ALICE);
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueCalendarEventList.add(ALICE);
        uniqueCalendarEventList.remove(ALICE);
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
        uniqueCalendarEventList.add(ALICE);
        UniqueCalendarEventList expectedUniqueCalendarEventList = new UniqueCalendarEventList();
        expectedUniqueCalendarEventList.add(BOB);
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
        uniqueCalendarEventList.add(ALICE);
        List<CalendarEvent> calendarEventList = Collections.singletonList(BOB);
        uniqueCalendarEventList.setCalendarEvents(calendarEventList);
        UniqueCalendarEventList expectedUniqueCalendarEventList = new UniqueCalendarEventList();
        expectedUniqueCalendarEventList.add(BOB);
        assertEquals(expectedUniqueCalendarEventList, uniqueCalendarEventList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<CalendarEvent> listWithDuplicateCalendarEvents = Arrays.asList(ALICE, ALICE);
        thrown.expect(DuplicateCalendarEventException.class);
        uniqueCalendarEventList.setCalendarEvents(listWithDuplicateCalendarEvents);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueCalendarEventList.asUnmodifiableObservableList().remove(0);
    }
}
