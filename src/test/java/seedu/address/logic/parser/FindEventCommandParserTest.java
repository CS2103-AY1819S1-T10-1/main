package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.junit.Test;

import seedu.address.logic.commands.FindEventCommand;
import seedu.address.model.calendarevent.DatePredicate;
import seedu.address.model.calendarevent.DateTime;
import seedu.address.model.calendarevent.FuzzySearchComparator;
import seedu.address.model.calendarevent.FuzzySearchFilterPredicate;
import seedu.address.model.calendarevent.TagsPredicate;
import seedu.address.model.tag.Tag;

public class FindEventCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidDates_throwsParseException() {
        // empty from or to date/time
        assertParseFailure(parser, "from/",
                String.format(DateTime.MESSAGE_DATETIME_INPUT_CONSTRAINTS));

        assertParseFailure(parser, "to/",
                String.format(DateTime.MESSAGE_DATETIME_INPUT_CONSTRAINTS));

        assertParseFailure(parser, "from/11 nov 8pm to/",
                String.format(DateTime.MESSAGE_DATETIME_INPUT_CONSTRAINTS));

        assertParseFailure(parser, "to/11 nov 8pm from/",
                String.format(DateTime.MESSAGE_DATETIME_INPUT_CONSTRAINTS));

        // from date/time is chronologically after the before date/time
        assertParseFailure(parser, "from/11 nov 8pm to/10/11/18 20:00",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DatePredicate.MESSAGE_DATE_PREDICATE_CONSTRAINTS));
    }

    @Test
    public void parse_invalidTags_throwsParseException() {
        assertParseFailure(parser, "tag/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, Tag.MESSAGE_TAG_CONSTRAINTS));

        assertParseFailure(parser, "from/20 nov 8pm to/21 nov 8pm tag/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, Tag.MESSAGE_TAG_CONSTRAINTS));

        assertParseFailure(parser, "some keywords tag/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, Tag.MESSAGE_TAG_CONSTRAINTS));

        assertParseFailure(parser, "some keywords tag/CS2103 tag/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, Tag.MESSAGE_TAG_CONSTRAINTS));
    }

    @Test
    public void parse_onlyValidDates_returnsFindCommand() {
        FindEventCommand expectedCommand1 =
                new FindEventCommand(new FuzzySearchFilterPredicate(Collections.emptyList()),
                        new FuzzySearchComparator(Collections.emptyList()),
                        new DatePredicate(new DateTime("2018-11-11 20:00"), null),
                        new TagsPredicate(Collections.emptySet()));

        assertParseSuccess(parser, "from/11 nov 2018 20:00", expectedCommand1);

        FindEventCommand expectedCommand2 =
                new FindEventCommand(new FuzzySearchFilterPredicate(Collections.emptyList()),
                        new FuzzySearchComparator(Collections.emptyList()),
                        new DatePredicate(null, new DateTime("2018-11-11 20:00")),
                        new TagsPredicate(Collections.emptySet()));

        assertParseSuccess(parser, "to/11 nov 20:00", expectedCommand2);

        FindEventCommand expectedCommand3 =
                new FindEventCommand(new FuzzySearchFilterPredicate(Collections.emptyList()),
                        new FuzzySearchComparator(Collections.emptyList()),
                        new DatePredicate(new DateTime("2018-11-10 20:00"), new DateTime("2018-11-11 20:00")),
                        new TagsPredicate(Collections.emptySet()));

        assertParseSuccess(parser, "from/10 nov 8pm to/11/11/18 20:00", expectedCommand3);
        assertParseSuccess(parser, "   \n\t from/10 nov 8pm    \t to/11/11/18 20:00   \n", expectedCommand3);
    }

    @Test
    public void parse_onlyValidTags_returnsFindCommand() {
        FindEventCommand expectedCommand1 =
                new FindEventCommand(new FuzzySearchFilterPredicate(Collections.emptyList()),
                        new FuzzySearchComparator(Collections.emptyList()),
                        new DatePredicate(null, null),
                        new TagsPredicate(new HashSet<>(Collections.singletonList("cs2103"))));

        assertParseSuccess(parser, "tag/cs2103", expectedCommand1);

        FindEventCommand expectedCommand2 =
                new FindEventCommand(new FuzzySearchFilterPredicate(Collections.emptyList()),
                        new FuzzySearchComparator(Collections.emptyList()),
                        new DatePredicate(null, null),
                        new TagsPredicate(new HashSet<>(Arrays.asList("cs2103", "Lecture"))));

        assertParseSuccess(parser, "tag/cs2103 tag/Lecture", expectedCommand2);

        assertParseSuccess(parser, "  \n  tag/cs2103  \n\t  tag/Lecture \t  ", expectedCommand2);
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // without dates or tags
        // no leading and trailing whitespaces
        FindEventCommand expectedFindEventCommand1 =
            new FindEventCommand(new FuzzySearchFilterPredicate(Arrays.asList("some", "keywords")),
                new FuzzySearchComparator(Arrays.asList("some", "keywords")),
                new DatePredicate(null, null),
                new TagsPredicate(Collections.emptySet()));
        assertParseSuccess(parser, "some keywords", expectedFindEventCommand1);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n some \n \t keywords \t", expectedFindEventCommand1);

        // with dates and tags
        FindEventCommand expectedFindEventCommand2 =
                new FindEventCommand(new FuzzySearchFilterPredicate(Arrays.asList("some", "keywords")),
                        new FuzzySearchComparator(Arrays.asList("some", "keywords")),
                        new DatePredicate(new DateTime("2018-11-10 20:00"), new DateTime("2018-11-11 20:00")),
                        new TagsPredicate(new HashSet<>(Arrays.asList("CS2103", "Lecture"))));
        assertParseSuccess(parser, "some keywords from/10 nov 8pm to/11 nov 8pm tag/CS2103 tag/Lecture",
                                expectedFindEventCommand2);

    }

}
