package seedu.address.model.calendarevent;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Wrapper class for LocalDateTime.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateTime(int, int, int, int, int)}
 */
public class DateTime {
    public static final String MESSAGE_DATETIMEINPUT_CONSTRAINTS
            = "Format for date and time input should be YYYY-MM-DD HH:MM";
    public static final String DATETIMEINPUT_VALIDATION_REGEX = "(\\d{4})-(\\d{1,2})-(\\d{1,2}) (\\d{2}):(\\d{2})";
    public static final String MESSAGE_DATETIME_CONSTRAINTS
            = "Ensure that the input year, month, day, hour and minute correspond to a valid date and time";

    public final LocalDateTime localDateTime;

    /**
     * Constructs a {@code DateTime} from input year, month, day, hour and minute
     * Wrapper class for LocalDateTime
     *
     * @param year      A valid int year
     * @param month     A valid int month
     * @param day       A valid int day
     * @param hour      A valid int hour
     * @param minute    A valid int minute
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
     * @param dateTimeInput     valid date time input string
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

    public String toInputFormat() { return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); }

    @Override
    public String toString() {
        return localDateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a"));
    }

    /**
     * Returns true if a given string is a valid date time input string.
     */
    public static boolean isValidDateTimeInput(String test) {
        return test.matches(DATETIMEINPUT_VALIDATION_REGEX);
    }

    /**
     * Returns if a given year, month, day, hour and minute correspond to a valid datetime.
     */
    public static boolean isValidDateTime(int year, int month, int day, int hour, int minute) {
        if (year <= 0) {
            return false;
        } else if (month <= 0 || month > 12) {
            return false;
        } else if (day <= 0 || day > 31) {
            return false;
        } else if (month == 2) {
            if (!isLeapYear(year) && day > 28) {
                return false;
            } else if (day > 29) {
                return false;
            }
        } else if (day > 30 && (month == 4 || month == 6 || month == 9 || month == 11)) {
            return false;
        } else if (hour < 0 || hour > 23) {
            return false;
        } else if (minute < 0 || minute > 59) {
            return false;
        }
        return true;
    }

    public static boolean isLeapYear(int year) {
        if (year % 4 != 0) {
            return false;
        } else if (year % 400 == 0) {
            return true;
        } else if (year % 100 == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isAfter(DateTime other) {
        return this.localDateTime.isAfter(other.localDateTime);
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
