package seedu.scheduler.model.calendarEvent;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.scheduler.testutil.TypicalPersons.ALICE;
import static seedu.scheduler.testutil.TypicalPersons.BOB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.scheduler.testutil.PersonBuilder;

public class CalendarEventTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        CalendarEvent calendarEvent = new PersonBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        calendarEvent.getTags().remove(0);
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSameCalendarEvent(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameCalendarEvent(null));

        // different phone and email -> returns false
        CalendarEvent editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSameCalendarEvent(editedAlice));

        // different name -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameCalendarEvent(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameCalendarEvent(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameCalendarEvent(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameCalendarEvent(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        CalendarEvent aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different calendarEvent -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        CalendarEvent editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different scheduler -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
