package seedu.address.model.calendarevent;

/**
 * Represents a Calendar Event's title in the scheduler.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class Title extends TextField {

    public static final String MESSAGE_FIELD_NAME = "Title";

    /**
     * Constructs a {@code Title}.
     *
     * @param title A valid title.
     */
    public Title(String title) {
        super(title);
    }

}
