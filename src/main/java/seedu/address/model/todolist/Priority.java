package seedu.address.model.todolist;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the priority of a todoList event.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 * Setting Rule: user can set H for High; M for Medium; L for Low
 */

public class Priority {

    public static final String DEFAULT_PRIORITY = "1"; // default field name
    public static final String MESSAGE_CONSTRAINTS =
        "The valid user input for priority is p/[priority]."
            + "While [priority] is H/M/L,"
            + "and H for high priority, M for medium priority, L for low priority";

    /*
     * There should be only one integer, and the integer should be 1, 2 or 3.
     */
    public static final String VALIDATION_REGEX_HIGH = "H";
    public static final String VALIDATION_REGEX_MEDIUM = "M";
    public static final String VALIDATION_REGEX_LOW = "L";

    public final String value;

    /**
     * Constructs a {@code Priority}.
     *
     * @param priority A valid priority.
     */
    public Priority(String priority) {
        requireNonNull(priority);
        checkArgument(isValid(priority), MESSAGE_CONSTRAINTS);
        value = priority;
    }

    /**
     * Returns true if a given string is a valid priority.
     */
    public static boolean isValid(String priority) {
        return (priority.matches(VALIDATION_REGEX_HIGH)
            || priority.matches(VALIDATION_REGEX_MEDIUM)
            || priority.matches(VALIDATION_REGEX_LOW));
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Priority // instanceof handles nulls
            && value.equals(((Priority) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
