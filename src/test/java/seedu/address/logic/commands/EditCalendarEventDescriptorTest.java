package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_TUTORIAL;

import org.junit.Test;

import seedu.address.testutil.EditCalendarEventDescriptorBuilder;

public class EditCalendarEventDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditEventCommand.EditCalendarEventDescriptor descriptorWithSameValues =
            new EditEventCommand.EditCalendarEventDescriptor(DESC_LECTURE);
        assertTrue(DESC_LECTURE.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_LECTURE.equals(DESC_LECTURE));

        // null -> returns false
        assertFalse(DESC_LECTURE.equals(null));

        // different types -> returns false
        assertFalse(DESC_LECTURE.equals(5));

        // different values -> returns false
        assertFalse(DESC_LECTURE.equals(DESC_TUTORIAL));

        // different title -> returns false
        EditEventCommand.EditCalendarEventDescriptor editedAmy =
            new EditCalendarEventDescriptorBuilder(DESC_LECTURE).withTitle(VALID_TITLE_TUTORIAL).build();
        assertFalse(DESC_LECTURE.equals(editedAmy));

        // different description -> returns false
        editedAmy = new EditCalendarEventDescriptorBuilder(DESC_LECTURE)
            .withDescription(VALID_DESCRIPTION_TUTORIAL).build();
        assertFalse(DESC_LECTURE.equals(editedAmy));

        // different venue -> returns false
        editedAmy = new EditCalendarEventDescriptorBuilder(DESC_LECTURE).withVenue(VALID_VENUE_TUTORIAL).build();
        assertFalse(DESC_LECTURE.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditCalendarEventDescriptorBuilder(DESC_LECTURE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_LECTURE.equals(editedAmy));
    }
}
