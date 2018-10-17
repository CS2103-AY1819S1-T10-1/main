package seedu.address.model.calendarevent;

/**
 * Represents a Calendar Event's location in the scheduler.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class Venue extends TextField {

    public static final String MESSAGE_FIELD_NAME = "Venue";

    /**
     * Constructs a {@code Venue}.
     *
     * @param location A valid location.
     */
    public Venue(String location) {
        super(location);
    }

}
