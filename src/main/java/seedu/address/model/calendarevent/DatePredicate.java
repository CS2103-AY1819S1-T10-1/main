package seedu.address.model.calendarevent;

import java.util.function.Predicate;

/**
 * Tests that at least some portion of the {@code CalendarEvent} falls after the {@code dateFrom} (if any)
 * and before the {@code dateTo} (if any)
 */
public class DatePredicate implements Predicate<CalendarEvent> {
    private final DateTime dateFrom;
    private final DateTime dateTo;

    public DatePredicate(DateTime dateFrom, DateTime dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    @Override
    public boolean test(CalendarEvent calendarEvent) {
        return (!hasDateTo() && !hasDateFrom())
               || (hasDateTo() && !calendarEvent.getStart().isAfter(dateTo))
               || (hasDateFrom() && !calendarEvent.getEnd().isBefore(dateFrom));
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
                    || ((hasDateFrom() && dateFrom.equals(((DatePredicate) other).dateFrom))
                        || (hasDateTo() && dateTo.equals(((DatePredicate) other).dateTo))))); // state check
    }

}
