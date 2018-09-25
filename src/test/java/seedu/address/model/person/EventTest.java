package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_IMPORTANT;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.WORKSHOP;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.EventBuilder;

public class EventTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Event event = new EventBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        event.getTags().remove(0);
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSameEvent(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameEvent(null));

        // different phone and email -> returns false
        Event editedAlice = new EventBuilder(ALICE).withDate(VALID_DATE_WORKSHOP).withEmail(VALID_TIME_WORKSHOP).build();
        assertFalse(ALICE.isSameEvent(editedAlice));

        // different name -> returns false
        editedAlice = new EventBuilder(ALICE).withName(VALID_NAME_WORKSHOP).build();
        assertFalse(ALICE.isSameEvent(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new EventBuilder(ALICE).withEmail(VALID_TIME_WORKSHOP).withAddress(VALID_VENUE_WORKSHOP)
                .withTags(VALID_TAG_IMPORTANT).build();
        assertTrue(ALICE.isSameEvent(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new EventBuilder(ALICE).withDate(VALID_DATE_WORKSHOP).withAddress(VALID_VENUE_WORKSHOP)
                .withTags(VALID_TAG_IMPORTANT).build();
        assertTrue(ALICE.isSameEvent(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new EventBuilder(ALICE).withAddress(VALID_VENUE_WORKSHOP).withTags(VALID_TAG_IMPORTANT).build();
        assertTrue(ALICE.isSameEvent(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Event aliceCopy = new EventBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different event -> returns false
        assertFalse(ALICE.equals(WORKSHOP));

        // different name -> returns false
        Event editedAlice = new EventBuilder(ALICE).withName(VALID_NAME_WORKSHOP).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new EventBuilder(ALICE).withDate(VALID_DATE_WORKSHOP).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new EventBuilder(ALICE).withEmail(VALID_TIME_WORKSHOP).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new EventBuilder(ALICE).withAddress(VALID_VENUE_WORKSHOP).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new EventBuilder(ALICE).withTags(VALID_TAG_IMPORTANT).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
