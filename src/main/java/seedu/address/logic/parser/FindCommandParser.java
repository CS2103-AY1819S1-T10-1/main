package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FindEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.calendarevent.DatePredicate;
import seedu.address.model.calendarevent.DateTime;
import seedu.address.model.calendarevent.FuzzySearchComparator;
import seedu.address.model.calendarevent.FuzzySearchFilterPredicate;
import seedu.address.model.calendarevent.TagsPredicate;

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

        String keywords = argMultimap.getPreamble();

        String trimmedKeywords = keywords.trim();
        if (trimmedKeywords.isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedKeywords.split("\\s+");

        List<String> tagList = argMultimap.getAllValues(PREFIX_TAG);

        DateTime dateFrom = null;
        DateTime dateTo = null;
        String dateFromString = argMultimap.getValue(PREFIX_FROM).orElse("");
        if (!dateFromString.isEmpty()) {
            dateFrom = ParserUtil.parseDateTime(dateFromString);
        }

        String dateToString = argMultimap.getValue(PREFIX_TO).orElse("");
        if (!dateToString.isEmpty()) {
            dateTo = ParserUtil.parseDateTime(dateToString);
        }

        return new FindEventCommand(new FuzzySearchFilterPredicate(Arrays.asList(nameKeywords)),
            new FuzzySearchComparator(Arrays.asList(nameKeywords)),
            new DatePredicate(dateFrom, dateTo),
            new TagsPredicate(tagList));
    }

}
