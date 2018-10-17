package seedu.address.model.todolist;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PriorityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Priority(null));
    }

    @Test
    public void constructor_invalidPriority_throwsIllegalArgumentException() {
        String invalidPriority = "a";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Priority(invalidPriority));
    }

    @Test
    public void isValidPriority() {
        // null priority
        Assert.assertThrows(NullPointerException.class, () -> Priority.isValid(null));

        // invalid priority
        assertFalse(Priority.isValid("")); // empty string
        assertFalse(Priority.isValid("abcde")); // invalid string
        assertFalse(Priority.isValid("#$%")); //include symbols
        assertFalse(Priority.isValid("45678")); // include numbers

        // valid priority
        assertTrue(Priority.isValid("H")); // high priority
        assertTrue(Priority.isValid("M")); // medium priority
        assertTrue(Priority.isValid("L")); // low priority
    }
}
