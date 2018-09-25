package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VENUE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_DIFFICULT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_IMPORTANT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DIFFICULT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_IMPORTANT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.MIDTERM;
import static seedu.address.testutil.TypicalPersons.WORKSHOP;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.*;
import seedu.address.model.person.Event;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EventBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedEvent = new EventBuilder(WORKSHOP).withTags(VALID_TAG_DIFFICULT).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_WORKSHOP + DATE_DESC_WORKSHOP + TIME_DESC_WORKSHOP
                + VENUE_DESC_WORKSHOP + TAG_DESC_DIFFICULT, new AddCommand(expectedEvent));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_MIDTERM + NAME_DESC_WORKSHOP + DATE_DESC_WORKSHOP + TIME_DESC_WORKSHOP
                + VENUE_DESC_WORKSHOP + TAG_DESC_DIFFICULT, new AddCommand(expectedEvent));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_WORKSHOP + DATE_DESC_MIDTERM + DATE_DESC_WORKSHOP + TIME_DESC_WORKSHOP
                + VENUE_DESC_WORKSHOP + TAG_DESC_DIFFICULT, new AddCommand(expectedEvent));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_WORKSHOP + DATE_DESC_WORKSHOP + TIME_DESC_MIDTERM + TIME_DESC_WORKSHOP
                + VENUE_DESC_WORKSHOP + TAG_DESC_DIFFICULT, new AddCommand(expectedEvent));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_WORKSHOP + DATE_DESC_WORKSHOP + TIME_DESC_WORKSHOP + VENUE_DESC_MIDTERM
                + VENUE_DESC_WORKSHOP + TAG_DESC_DIFFICULT, new AddCommand(expectedEvent));

        // multiple tags - all accepted
        Event expectedEventMultipleTags = new EventBuilder(WORKSHOP).withTags(VALID_TAG_DIFFICULT, VALID_TAG_IMPORTANT)
                .build();
        assertParseSuccess(parser, NAME_DESC_WORKSHOP + DATE_DESC_WORKSHOP + TIME_DESC_WORKSHOP + VENUE_DESC_WORKSHOP
                + TAG_DESC_IMPORTANT + TAG_DESC_DIFFICULT, new AddCommand(expectedEventMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Event expectedEvent = new EventBuilder(MIDTERM).withTags().build();
        assertParseSuccess(parser, NAME_DESC_MIDTERM + DATE_DESC_MIDTERM + TIME_DESC_MIDTERM + VENUE_DESC_MIDTERM,
                new AddCommand(expectedEvent));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_WORKSHOP + DATE_DESC_WORKSHOP + TIME_DESC_WORKSHOP + VENUE_DESC_WORKSHOP,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_WORKSHOP + VALID_DATE_WORKSHOP + TIME_DESC_WORKSHOP + VENUE_DESC_WORKSHOP,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_WORKSHOP + DATE_DESC_WORKSHOP + VALID_TIME_WORKSHOP + VENUE_DESC_WORKSHOP,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_WORKSHOP + DATE_DESC_WORKSHOP + TIME_DESC_WORKSHOP + VALID_VENUE_WORKSHOP,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_WORKSHOP + VALID_DATE_WORKSHOP + VALID_TIME_WORKSHOP + VALID_VENUE_WORKSHOP,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + DATE_DESC_WORKSHOP + TIME_DESC_WORKSHOP + VENUE_DESC_WORKSHOP
                + TAG_DESC_IMPORTANT + TAG_DESC_DIFFICULT, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_WORKSHOP + INVALID_DATE_DESC + TIME_DESC_WORKSHOP + VENUE_DESC_WORKSHOP
                + TAG_DESC_IMPORTANT + TAG_DESC_DIFFICULT, Date.MESSAGE_DATE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_WORKSHOP + DATE_DESC_WORKSHOP + INVALID_TIME_DESC + VENUE_DESC_WORKSHOP
                + TAG_DESC_IMPORTANT + TAG_DESC_DIFFICULT, Time.MESSAGE_TIME_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_WORKSHOP + DATE_DESC_WORKSHOP + TIME_DESC_WORKSHOP + INVALID_VENUE_DESC
                + TAG_DESC_IMPORTANT + TAG_DESC_DIFFICULT, Venue.MESSAGE_VENUE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_WORKSHOP + DATE_DESC_WORKSHOP + TIME_DESC_WORKSHOP + VENUE_DESC_WORKSHOP
                + INVALID_TAG_DESC + VALID_TAG_DIFFICULT, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + DATE_DESC_WORKSHOP + TIME_DESC_WORKSHOP + INVALID_VENUE_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_WORKSHOP + DATE_DESC_WORKSHOP + TIME_DESC_WORKSHOP
                + VENUE_DESC_WORKSHOP + TAG_DESC_IMPORTANT + TAG_DESC_DIFFICULT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
