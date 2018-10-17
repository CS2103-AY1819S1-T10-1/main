package seedu.address.model.calendarevent;

/**
 * Represents a Calendar Event's start and end date/time in the scheduler.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartAndEnd(DateTime, DateTime)}
 */
public class DateTimeInfo {
    public static final String MESSAGE_STARTEND_CONSTRAINTS = "Start date & time must be before end date & time";
    public final DateTime start;
    public final DateTime end;

    /**
     * Constructs a {@code DateTime} from input year, month, day, hour and minute
     * Wrapper class for LocalDateTime
     *
     * @param start, end A valid start and end datetime
     *
     */
    public DateTimeInfo(DateTime start, DateTime end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Returns if a given start and end datetime are valid
     */
    public static boolean isValidStartAndEnd(DateTime start, DateTime end) {
        return !start.isAfter(end);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTimeInfo // instanceof handles nulls
                && start.equals(((DateTimeInfo) other).start)
                && end.equals(((DateTimeInfo) other).end)); // state check
    }
}
