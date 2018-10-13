package seedu.address.model.calendarevent;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.CalendarEventBuilder;

public class CalendarEventTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        CalendarEvent calendarEvent = new CalendarEventBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        calendarEvent.getTags().remove(0);
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSameCalendarEvent(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameCalendarEvent(null));

        // different phone, email and dateTime -> returns false
        CalendarEvent editedAlice =
            new CalendarEventBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withDateTime(VALID_DATETIME_BOB).build();
        assertFalse(ALICE.isSameCalendarEvent(editedAlice));

        // different name -> returns false
        editedAlice = new CalendarEventBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameCalendarEvent(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new CalendarEventBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withDateTime(VALID_DATETIME_BOB).withLocation(VALID_ADDRESS_BOB)
            .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameCalendarEvent(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new CalendarEventBuilder(ALICE).withPhone(VALID_PHONE_BOB).withDateTime(VALID_DATETIME_BOB).withLocation(VALID_ADDRESS_BOB)
            .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameCalendarEvent(editedAlice));

        // same name, same dateTime, different attributes -> returns true
        editedAlice = new CalendarEventBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withLocation(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameCalendarEvent(editedAlice));

        // same name, same phone, same email different attributes -> returns true
        editedAlice =
            new CalendarEventBuilder(ALICE).withDateTime(VALID_DATETIME_BOB).withLocation(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameCalendarEvent(editedAlice));

        // same name, same phone, same dateTime different attributes -> returns true
        editedAlice =
                new CalendarEventBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withLocation(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameCalendarEvent(editedAlice));

        // same name, same dateTime, same email different attributes -> returns true
        editedAlice =
                new CalendarEventBuilder(ALICE).withPhone(VALID_PHONE_BOB).withLocation(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameCalendarEvent(editedAlice));

        // same name, same phone, same email, same dateTime different attributes -> returns true
        editedAlice =
                new CalendarEventBuilder(ALICE).withLocation(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameCalendarEvent(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        CalendarEvent aliceCopy = new CalendarEventBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different calendarevent -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        CalendarEvent editedAlice = new CalendarEventBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new CalendarEventBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new CalendarEventBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different dateTime -> returns false
        editedAlice = new CalendarEventBuilder(ALICE).withDateTime(VALID_DATETIME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new CalendarEventBuilder(ALICE).withLocation(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new CalendarEventBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
