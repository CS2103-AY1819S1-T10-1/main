package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.calendarevent.DateTime;
import seedu.address.model.calendarevent.Description;
import seedu.address.model.calendarevent.Title;
import seedu.address.model.calendarevent.Venue;
import seedu.address.model.tag.Tag;
import seedu.address.model.todolist.Priority;


/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final int ROUND_MINUTES_TO = 15;

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String title} into a {@code Title}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Title parseTitle(String title) throws ParseException {
        requireNonNull(title);
        String trimmedTitle = title.trim();
        if (!Title.isValid(trimmedTitle)) {
            throw new ParseException(Title.MESSAGE_CONSTRAINTS);
        }
        return new Title(trimmedTitle);
    }

    /**
     * Parses a {@code String description} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Description.isValid(trimmedDescription)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(trimmedDescription);
    }

    /**
     * Parses a {@code String priority} into a {@code Priority}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code priority} is invalid.
     */
    public static Priority parsePriority(String priority) throws ParseException {
        requireNonNull(priority);
        String trimmedPriority = priority.trim();
        if (!Priority.isValid(trimmedPriority)) {
            throw new ParseException(Priority.MESSAGE_CONSTRAINTS);
        }
        return new Priority(trimmedPriority);
    }

    /**
     * Parses a {@code String dateTimeInput} into a {@code DateTime}.
     * Leading and trailing whitespaces will be trimmed
     * If multiple dates are supplied, only the first will be parsed
     * Rounds to the nearest 15 minutes
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static DateTime parseDateTime(String dateAndTime) throws ParseException {
        requireNonNull(dateAndTime);
        Parser parser = new Parser();
        List<DateGroup> groups = parser.parse(dateAndTime.trim());

        // If 0 groups found, date is invalid
        if (groups.size() == 0) {
            throw new ParseException(DateTime.MESSAGE_DATETIME_INPUT_CONSTRAINTS);
            // TODO: Change the message to something less specific
            // TODO: Stronger condition to detect invalid date
        } else {
            List<Date> dates = groups.get(0).getDates();
            //List<LocalDateTime> datetimes = dates.stream()
            // .map(date -> dateToLocalDateTime(date)).collect(Collectors.toList());
            LocalDateTime firstDate = dateToLocalDateTime(dates.get(0));
            LocalDateTime rounded = firstDate.withMinute(
                roundToNearestMultiple(firstDate.getMinute(), ROUND_MINUTES_TO));
            return new DateTime(rounded);
        }
    }

    /**
     * Rounds {@code number} to the nearest multiple of {@code multiple}
     *
     * @param number   the number to be rounded
     * @param multiple the multiple to round to
     * @return a rounded number
     */
    public static int roundToNearestMultiple(int number, int multiple) {
        int epsilon = (number % multiple) / ((multiple + 1) / 2);
        return (number / multiple + epsilon) * multiple;
    }

    /**
     * Converts a Date to a LocalDateTime
     *
     * @param date a Date Object
     * @return A LocalDateTime with TImezone set to system Timezone
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * Parses a {@code String venue} into an {@code Venue}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code venue} is invalid.
     */
    public static Venue parseVenue(String venue) throws ParseException {
        requireNonNull(venue);
        String trimmedAddress = venue.trim();
        if (!Venue.isValid(trimmedAddress)) {
            throw new ParseException(Venue.MESSAGE_CONSTRAINTS);
        }
        return new Venue(trimmedAddress);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
