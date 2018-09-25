package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.DESC_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_IMPORTANT;

import org.junit.Test;

import seedu.address.testutil.EditEventDescriptorBuilder;

public class EditEventDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditEventDescriptor descriptorWithSameValues = new EditCommand.EditEventDescriptor(DESC_MIDTERM);
        assertTrue(DESC_MIDTERM.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_MIDTERM.equals(DESC_MIDTERM));

        // null -> returns false
        assertFalse(DESC_MIDTERM.equals(null));

        // different types -> returns false
        assertFalse(DESC_MIDTERM.equals(5));

        // different values -> returns false
        assertFalse(DESC_MIDTERM.equals(DESC_WORKSHOP));

        // different name -> returns false
        EditCommand.EditEventDescriptor editedAmy = new EditEventDescriptorBuilder(DESC_MIDTERM).withName(VALID_NAME_WORKSHOP).build();
        assertFalse(DESC_MIDTERM.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditEventDescriptorBuilder(DESC_MIDTERM).withDate(VALID_DATE_WORKSHOP).build();
        assertFalse(DESC_MIDTERM.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditEventDescriptorBuilder(DESC_MIDTERM).withTime(VALID_TIME_WORKSHOP).build();
        assertFalse(DESC_MIDTERM.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditEventDescriptorBuilder(DESC_MIDTERM).withVenue(VALID_VENUE_WORKSHOP).build();
        assertFalse(DESC_MIDTERM.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditEventDescriptorBuilder(DESC_MIDTERM).withTags(VALID_TAG_IMPORTANT).build();
        assertFalse(DESC_MIDTERM.equals(editedAmy));
    }
}
