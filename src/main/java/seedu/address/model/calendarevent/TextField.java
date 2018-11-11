package seedu.address.model.calendarevent;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a parent class for text fields in a Calendar Event in the scheduler.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class TextField {

    public static final String MESSAGE_FIELD_NAME = "Text field"; // default field name
    public static final String MESSAGE_CONSTRAINTS = MESSAGE_FIELD_NAME
            + " should not be blank or begin with a whitespace";

    /*
     * The first character of the text field must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    private static final String VALIDATION_REGEX = "\\S.+";

    public final String value;

    /**
     * Constructs a {@code TextField}.
     *
     * @param textField A valid textField.
     */
    public TextField(String textField) {
        requireNonNull(textField);
        checkArgument(isValid(textField), MESSAGE_CONSTRAINTS);
        value = textField;
    }

    /**
     * Returns true if a given string is a valid textField.
     */
    public static boolean isValid(String textField) {
        return textField.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof TextField // instanceof handles nulls
            && value.equals(((TextField) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
