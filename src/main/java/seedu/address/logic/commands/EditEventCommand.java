package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelToDo;
import seedu.address.model.calendarevent.CalendarEvent;
import seedu.address.model.calendarevent.DateTime;
import seedu.address.model.calendarevent.DateTimeInfo;
import seedu.address.model.calendarevent.Description;
import seedu.address.model.calendarevent.Title;
import seedu.address.model.calendarevent.Venue;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing calendar event in the calendar of the scheduler.
 */
public class EditEventCommand extends Command {

    public static final String COMMAND_WORD = "edit event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the event identified "
        + "by the index number used in the displayed event list. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: INDEX (must be a positive integer) "
        + "[" + PREFIX_TITLE + "TITLE] "
        + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
        + "[" + PREFIX_START + "START DATE & TIME] "
        + "[" + PREFIX_END + "END DATE & TIME] "
        + "[" + PREFIX_VENUE + "VENUE] "
        + "[" + PREFIX_TAG + "TAG]...\n"
        + "Example: " + COMMAND_WORD + " 1 "
        + PREFIX_DESCRIPTION + "91234567 ";

    public static final String MESSAGE_EDIT_CALENDAR_EVENT_SUCCESS = "Edited Event: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CALENDAR_EVENT = "This event already exists in the calendar.";

    private final Index index;
    private final EditCalendarEventDescriptor editCalendarEventDescriptor;

    /**
     * @param index                       of the calendar event in the filtered calendar event list to edit
     * @param editCalendarEventDescriptor details to edit the calendar event with
     */
    public EditEventCommand(Index index, EditCalendarEventDescriptor editCalendarEventDescriptor) {
        requireNonNull(index);
        requireNonNull(editCalendarEventDescriptor);

        this.index = index;
        this.editCalendarEventDescriptor = new EditCalendarEventDescriptor(editCalendarEventDescriptor);
    }

    /**
     * Creates and returns a {@code CalendarEvent} with the details of {@code calendarEventToEdit}
     * edited with {@code editCalendarEventDescriptor}.
     */
    private static CalendarEvent createEditedCalendarEvent(CalendarEvent calendarEventToEdit,
                                                           EditCalendarEventDescriptor editCalendarEventDescriptor)
        throws CommandException {
        assert calendarEventToEdit != null;

        Title updatedName = editCalendarEventDescriptor.getTitle().orElse(calendarEventToEdit.getTitle());
        Description updatedDescription =
            editCalendarEventDescriptor.getDescription().orElse(calendarEventToEdit.getDescriptionObject());

        DateTime updatedStart = editCalendarEventDescriptor.getStart().orElse(calendarEventToEdit.getStart());
        DateTime updatedEnd = editCalendarEventDescriptor.getEnd().orElse(calendarEventToEdit.getEnd());
        if (!DateTimeInfo.isValidStartAndEnd(updatedStart, updatedEnd)) {
            throw new CommandException(DateTimeInfo.MESSAGE_DATETIMEINFO_CONSTRAINTS);
        }

        Venue updatedVenue = editCalendarEventDescriptor.getVenue().orElse(calendarEventToEdit.getVenue());
        Set<Tag> updatedTags = editCalendarEventDescriptor.getTags().orElse(calendarEventToEdit.getTags());

        return new CalendarEvent(updatedName, updatedDescription,
            new DateTimeInfo(updatedStart, updatedEnd), updatedVenue, updatedTags);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<CalendarEvent> lastShownList = model.getFilteredCalendarEventList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CALENDAR_EVENTS_DISPLAYED_INDEX);
        }

        CalendarEvent calendarEventToEdit = lastShownList.get(index.getZeroBased());
        CalendarEvent editedCalendarEvent = createEditedCalendarEvent(calendarEventToEdit, editCalendarEventDescriptor);

        if (!calendarEventToEdit.isSameCalendarEvent(editedCalendarEvent)
            && model.hasCalendarEvent(editedCalendarEvent)) {
            throw new CommandException(MESSAGE_DUPLICATE_CALENDAR_EVENT);
        }

        model.updateCalendarEvent(calendarEventToEdit, editedCalendarEvent);
        model.commitScheduler();
        return new CommandResult(String.format(MESSAGE_EDIT_CALENDAR_EVENT_SUCCESS, editedCalendarEvent));
    }

    @Override
    public CommandResult execute(ModelToDo modelToDo, CommandHistory history) throws CommandException {
        throw new CommandException(MESSAGE_INCORRECT_MODEL_CALENDAR);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditEventCommand)) {
            return false;
        }

        // state check
        EditEventCommand e = (EditEventCommand) other;
        return index.equals(e.index)
            && editCalendarEventDescriptor.equals(e.editCalendarEventDescriptor);
    }

    /**
     * Stores the details to edit the calendar event with. Each non-empty field value will replace the
     * corresponding field value of the calendar event.
     */
    public static class EditCalendarEventDescriptor {
        private Title title;
        private Description description;
        private DateTime start;
        private DateTime end;
        private Venue venue;
        private Set<Tag> tags;

        public EditCalendarEventDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditCalendarEventDescriptor(EditCalendarEventDescriptor toCopy) {
            setTitle(toCopy.title);
            setDescription(toCopy.description);
            setStart(toCopy.start);
            setEnd(toCopy.end);
            setVenue(toCopy.venue);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title, description, start, end, venue, tags);
        }

        public Optional<Title> getTitle() {
            return Optional.ofNullable(title);
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<DateTime> getStart() {
            return Optional.ofNullable(start);
        }

        public void setStart(DateTime start) {
            this.start = start;
        }

        public Optional<DateTime> getEnd() {
            return Optional.ofNullable(end);
        }

        public void setEnd(DateTime end) {
            this.end = end;
        }

        public Optional<Venue> getVenue() {
            return Optional.ofNullable(venue);
        }

        public void setVenue(Venue venue) {
            this.venue = venue;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCalendarEventDescriptor)) {
                return false;
            }

            // state check
            EditCalendarEventDescriptor e = (EditCalendarEventDescriptor) other;

            return getTitle().equals(e.getTitle())
                && getDescription().equals(e.getDescription())
                && getStart().equals(e.getStart())
                && getEnd().equals(e.getEnd())
                && getVenue().equals(e.getVenue())
                && getTags().equals(e.getTags());
        }
    }
}
