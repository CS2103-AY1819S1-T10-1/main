package seedu.address.model.calendarevent;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class TextFieldTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new TextField(null));
    }

    @Test
    public void constructor_invalidTextField_throwsIllegalArgumentException() {
        String invalidTextField = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new TextField(invalidTextField));
    }

    @Test
    public void isValidTextField() {
        // null textField
        Assert.assertThrows(NullPointerException.class, () -> TextField.isValid(null));

        // invalid textField
        assertFalse(TextField.isValid("")); // empty string
        assertFalse(TextField.isValid(" ")); // spaces only
        assertFalse(TextField.isValid(" a"));

        // valid textField
        assertTrue(TextField.isValid("peter jack")); // alphabets only
        assertTrue(TextField.isValid("12345")); // numbers only
        assertTrue(TextField.isValid("peter the 2nd")); // alphanumeric characters
        assertTrue(TextField.isValid("Capital Tan")); // with capital letters
        assertTrue(TextField.isValid("David Roger Jackson Ray Jr 2nd")); // long textFields
        assertTrue(TextField.isValid("\\^")); // caret
        assertTrue(TextField.isValid("\\*")); // only non-alphanumeric characters
        assertTrue(TextField.isValid("peter*")); // contains non-alphanumeric characters
    }
}
