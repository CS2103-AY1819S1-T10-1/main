package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class TimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Time(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Time(invalidEmail));
    }

    @Test
    public void isValidEmail() {
        // null email
        Assert.assertThrows(NullPointerException.class, () -> Time.isValidTime(null));

        // blank email
        assertFalse(Time.isValidTime("")); // empty string
        assertFalse(Time.isValidTime(" ")); // spaces only

        // missing parts
        assertFalse(Time.isValidTime("@example.com")); // missing local part
        assertFalse(Time.isValidTime("peterjackexample.com")); // missing '@' symbol
        assertFalse(Time.isValidTime("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Time.isValidTime("peterjack@-")); // invalid domain name
        assertFalse(Time.isValidTime("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(Time.isValidTime("peter jack@example.com")); // spaces in local part
        assertFalse(Time.isValidTime("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(Time.isValidTime(" peterjack@example.com")); // leading space
        assertFalse(Time.isValidTime("peterjack@example.com ")); // trailing space
        assertFalse(Time.isValidTime("peterjack@@example.com")); // double '@' symbol
        assertFalse(Time.isValidTime("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(Time.isValidTime("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(Time.isValidTime("peterjack@.example.com")); // domain name starts with a period
        assertFalse(Time.isValidTime("peterjack@example.com.")); // domain name ends with a period
        assertFalse(Time.isValidTime("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(Time.isValidTime("peterjack@example.com-")); // domain name ends with a hyphen

        // valid email
        assertTrue(Time.isValidTime("PeterJack_1190@example.com"));
        assertTrue(Time.isValidTime("a@bc")); // minimal
        assertTrue(Time.isValidTime("test@localhost")); // alphabets only
        assertTrue(Time.isValidTime("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters local part
        assertTrue(Time.isValidTime("123@145")); // numeric local part and domain name
        assertTrue(Time.isValidTime("a1+be!@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(Time.isValidTime("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(Time.isValidTime("if.you.dream.it_you.can.do.it@example.com")); // long local part
    }
}
