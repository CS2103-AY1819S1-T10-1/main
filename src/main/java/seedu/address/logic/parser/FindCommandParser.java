package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FindEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.calendarevent.FuzzySearchComparator;
import seedu.address.model.calendarevent.TagsPredicate;
import seedu.address.model.calendarevent.TitleContainsKeywordsPredicate;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        String keywords = argMultimap.getPreamble();

        String trimmedKeywords = keywords.trim();
        if (trimmedKeywords.isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedKeywords.split("\\s+");

        List<String> tagList = argMultimap.getAllValues(PREFIX_TAG);

        return new FindEventCommand(new TitleContainsKeywordsPredicate(Arrays.asList(nameKeywords)),
                                    new FuzzySearchComparator(Arrays.asList(nameKeywords)),
                                    new TagsPredicate(tagList));
    }

}
