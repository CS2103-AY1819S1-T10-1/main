package seedu.address.model.calendarevent;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.DateTimeException;

import org.junit.Test;

import seedu.address.testutil.Assert;


public class DateTimeTest {

    @Test
    public void constructor_invalidDateTime_throwsIllegalArgumentException() {
        int invalidYear = -1;
        int validMonth = 1;
        int validDay = 1;
        int validHour = 1;
        int validMinute = 1;
        Assert.assertThrows(DateTimeException.class, () ->
            new DateTime(invalidYear, validMonth, validDay, validHour, validMinute));
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DateTime((String) null));
    }


    @Test
    public void isValidDateTime() {
        // invalid DateTime
        assertFalse(DateTime.isValidDateTime(-1, 1, 1, 1, 1)); // negative
        assertFalse(DateTime.isValidDateTime(2018, 13, 1, 1, 1)); // invalid month
        assertFalse(DateTime.isValidDateTime(2018, 1, 32, 1, 1)); // invalid day
        assertFalse(DateTime.isValidDateTime(2018, 4, 31, 1, 1)); // invalid day (30-day)
        assertFalse(DateTime.isValidDateTime(2018, 2, 29, 1, 1)); // non-leap year
        assertFalse(DateTime.isValidDateTime(2018, 1, 1, 25, 1)); // invalid hour
        assertFalse(DateTime.isValidDateTime(2018, 1, 1, 1, 61)); // invalid day

        // valid DateTime
        assertTrue(DateTime.isValidDateTime(2018, 1, 1, 1, 1)); // typical day
        assertTrue(DateTime.isValidDateTime(2020, 2, 29, 1, 1)); // leap year
    }
}
