package seedu.address.model.calendarevent;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Wrapper class for LocalDateTime.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateTime(int, int, int, int, int)}
 */
public class DateTime implements Comparable<DateTime> {
    public static final String MESSAGE_DATETIME_INPUT_CONSTRAINTS =
        "Format for date and time input should be YYYY-MM-DD HH:MM";
    public static final String DATETIME_INPUT_VALIDATION_REGEX = "(\\d{4})-(\\d{1,2})-(\\d{1,2}) (\\d{2}):(\\d{2})";
    public static final String MESSAGE_DATETIME_CONSTRAINTS =
        "Ensure that the input year, month, day, hour and minute correspond to a valid date and time";

    public final LocalDateTime localDateTime;

    /**
     * Constructs a {@code DateTime} from LocalDateTime object
     *
     * @param dateTimeInput a LocalDateTime object
     */
    public DateTime(LocalDateTime dateTimeInput) {
        this.localDateTime = dateTimeInput;
    }

    /**
     * Constructs a {@code DateTime} from input year, month, day, hour and minute
     * Wrapper class for LocalDateTime
     *
     * @param year   A valid int year
     * @param month  A valid int month
     * @param day    A valid int day
     * @param hour   A valid int hour
     * @param minute A valid int minute
     */
    public DateTime(int year, int month, int day, int hour, int minute) {
        if (!DateTime.isValidDateTime(year, month, day, hour, minute)) {
            throw new DateTimeException(DateTime.MESSAGE_DATETIME_CONSTRAINTS);
        }

        this.localDateTime = LocalDateTime.of(year, month, day, hour, minute);
    }

    /**
     * Constructs a {@code DateTime} from date time input string
     * Wrapper class for LocalDateTime
     *
     * @param dateTimeInput valid date time input string
     */
    public DateTime(String dateTimeInput) {
        String[] dateTimeInputNumbers = dateTimeInput.split("[- :]");

        int year = Integer.parseInt(dateTimeInputNumbers[0]);
        int month = Integer.parseInt(dateTimeInputNumbers[1]);
        int day = Integer.parseInt(dateTimeInputNumbers[2]);
        int hour = Integer.parseInt(dateTimeInputNumbers[3]);
        int minute = Integer.parseInt(dateTimeInputNumbers[4]);

        if (!DateTime.isValidDateTime(year, month, day, hour, minute)) {
            throw new DateTimeException(DateTime.MESSAGE_DATETIME_CONSTRAINTS);
        }

        this.localDateTime = LocalDateTime.of(year, month, day, hour, minute);
    }

    /**
     * Returns true if a given string is a valid date time input string.
     */
    public static boolean isValidDateTimeInput(String test) {
        return test.matches(DATETIME_INPUT_VALIDATION_REGEX);
    }

    /**
     * Returns if a given year, month, day, hour and minute correspond to a valid datetime.
     */
    public static boolean isValidDateTime(int year, int month, int day, int hour, int minute) {
        if (year <= 0) {
            return false;
        }

        try {
            LocalDateTime.of(year, month, day, hour, minute);
        } catch (DateTimeException E) {
            return false;
        }

        return true;
    }

    /**
     * Converts date to the input format.
     */
    public String toInputFormat() {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public boolean isBefore(DateTime other) {
        return this.localDateTime.isBefore(other.localDateTime);
    }

    public boolean isAfter(DateTime other) {
        return this.localDateTime.isAfter(other.localDateTime);
    }

    @Override
    public String toString() {
        return localDateTime.format(DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a"));
    }

    @Override
    public int compareTo(DateTime other) {
        // assert (other != null);
        return localDateTime.compareTo((other).localDateTime);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DateTime // instanceof handles nulls
            && localDateTime.isEqual(((DateTime) other).localDateTime)); // state check
    }

    @Override
    public int hashCode() {
        return localDateTime.hashCode();
    }
}
