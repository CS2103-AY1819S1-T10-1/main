package seedu.address.model.calendarevent;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class VenueTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Venue(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidLocation = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Venue(invalidLocation));
    }

    @Test
    public void isValidLocation() {
        // null location
        Assert.assertThrows(NullPointerException.class, () -> Venue.isValidVenue(null));

        // invalid location
        assertFalse(Venue.isValidVenue("")); // empty string
        assertFalse(Venue.isValidVenue(" ")); // spaces only

        // valid location
        assertTrue(Venue.isValidVenue("Blk 456, Den Road, #01-355"));
        assertTrue(Venue.isValidVenue("-")); // one character
        assertTrue(Venue.isValidVenue("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long
        // location
    }
}
