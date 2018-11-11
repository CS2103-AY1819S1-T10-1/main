package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindEventCommand;
import seedu.address.model.calendarevent.DatePredicate;
import seedu.address.model.calendarevent.FuzzySearchComparator;
import seedu.address.model.calendarevent.FuzzySearchFilterPredicate;
import seedu.address.model.calendarevent.TagsPredicate;

public class FindEventCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindEventCommand expectedFindEventCommand =
            new FindEventCommand(new FuzzySearchFilterPredicate(Arrays.asList("Alice", "Bob")),
                new FuzzySearchComparator(Arrays.asList("Alice", "Bob")),
                new DatePredicate(null, null),
                new TagsPredicate(new ArrayList<>()));
        assertParseSuccess(parser, "Alice Bob", expectedFindEventCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindEventCommand);
    }

}
