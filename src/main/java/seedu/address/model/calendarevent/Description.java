package seedu.address.model.calendarevent;

/**
 * Represents a Calendar Event's description in the scheduler.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class Description extends TextField {

    public static final String MESSAGE_FIELD_NAME = "Description";

    /**
     * Constructs a {@code Description}.
     *
     * @param description A valid description.
     */
    public Description(String description) {
        super(description);
    }

}
