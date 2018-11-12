package seedu.address.model.calendarevent;

import java.util.function.Predicate;

/**
 * Tests that at least some portion of the {@code CalendarEvent} falls after the {@code dateFrom} (if any)
 * and before the {@code dateTo} (if any)
 */
public class DatePredicate implements Predicate<CalendarEvent> {
    public static final String MESSAGE_DATE_PREDICATE_CONSTRAINTS =
            "'From' date & time must be chronologically earlier than 'To' date & time";
    private final DateTime dateFrom;
    private final DateTime dateTo;

    public DatePredicate(DateTime dateFrom, DateTime dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    @Override
    public boolean test(CalendarEvent calendarEvent) {
        return (!hasDateTo() && !hasDateFrom()) // If both dates are null, always accepts
                || (hasDateTo() && hasDateFrom() // If both dates are present, then only accept if start date is not
                    && !calendarEvent.getStart().isAfter(dateTo) // after 'from' and end date is not before 'to'
                    && !calendarEvent.getEnd().isBefore(dateFrom))
                || (!hasDateFrom() && !calendarEvent.getStart().isAfter(dateTo)) // If only 1 date is present, apply
                || (!hasDateTo() && !calendarEvent.getEnd().isBefore(dateFrom)); // 1-sided variant of above
    }

    /**
     * Returns whether {@code DatePredicate} has an {@code dateFrom}
     */
    public boolean hasDateFrom() {
        return dateFrom != null;
    }

    /**
     * Returns whether {@code DatePredicate} has an {@code dateTo}
     */
    public boolean hasDateTo() {
        return dateTo != null;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DatePredicate // instanceof handles nulls
                && ((!hasDateFrom() && !hasDateTo())
                    || (hasDateFrom() && hasDateTo() && dateFrom.equals(((DatePredicate) other).dateFrom)
                        && dateTo.equals(((DatePredicate) other).dateTo))
                    || (!hasDateTo() && dateFrom.equals(((DatePredicate) other).dateFrom))
                    || (!hasDateFrom() && dateTo.equals(((DatePredicate) other).dateTo)))); // state check
    }

}
