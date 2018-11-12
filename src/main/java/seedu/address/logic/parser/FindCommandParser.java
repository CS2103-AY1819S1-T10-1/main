package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.FindEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.calendarevent.DatePredicate;
import seedu.address.model.calendarevent.DateTime;
import seedu.address.model.calendarevent.FuzzySearchComparator;
import seedu.address.model.calendarevent.FuzzySearchFilterPredicate;
import seedu.address.model.calendarevent.TagsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FindEventCommand object
 */
public class FindCommandParser implements Parser<FindEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindEventCommand
     * and returns an FindEventCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FROM, PREFIX_TO, PREFIX_TAG);

        String trimmedKeywords = argMultimap.getPreamble().trim();

        // Throws parse exception if no keywords are provided and there are no other prefixes
        if (trimmedKeywords.isEmpty() && !argMultimap.getValue(PREFIX_TAG).isPresent()
                && !argMultimap.getValue(PREFIX_FROM).isPresent() && !argMultimap.getValue(PREFIX_TO).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE));
        }

        // Parse list of keywords (if any)
        List<String> keywordList;
        if (trimmedKeywords.isEmpty()) {
            keywordList = Collections.emptyList();
        } else {
            keywordList = Arrays.stream(trimmedKeywords.split("\\s+")).collect(Collectors.toList());
        }

        // Parses date/time from and date/time to (if any)
        DateTime dateFrom = null;
        if (argMultimap.getValue(PREFIX_FROM).isPresent()) {
            String dateFromString = argMultimap.getValue(PREFIX_FROM).get();
            dateFrom = ParserUtil.parseDateTime(dateFromString);
        }
        DateTime dateTo = null;
        if (argMultimap.getValue(PREFIX_TO).isPresent()) {
            String dateToString = argMultimap.getValue(PREFIX_TO).get();
            dateTo = ParserUtil.parseDateTime(dateToString);
        }
        if (dateTo != null && dateFrom != null && dateFrom.isAfter(dateTo)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                    DatePredicate.MESSAGE_DATE_PREDICATE_CONSTRAINTS));
        }

        // Parse set of tag strings (if any)
        Set<String> tagSet = argMultimap.getAllValues(PREFIX_TAG).stream()
                                                                .map(String::trim)
                                                                .collect(Collectors.toSet());
        if (tagSet.stream().anyMatch(String::isEmpty)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, Tag.MESSAGE_TAG_CONSTRAINTS));
        }

        return new FindEventCommand(new FuzzySearchFilterPredicate(keywordList),
            new FuzzySearchComparator(keywordList),
            new DatePredicate(dateFrom, dateTo),
            new TagsPredicate(tagSet));
    }
}
