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
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_DIFFICULT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_IMPORTANT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DIFFICULT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_IMPORTANT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_EVENT;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditEventDescriptor;
import seedu.address.model.person.Venue;
import seedu.address.model.person.Time;
import seedu.address.model.person.Name;
import seedu.address.model.person.Date;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditEventDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_MIDTERM, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_MIDTERM, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_MIDTERM, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_NAME_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_DATE_DESC, Date.MESSAGE_DATE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_TIME_DESC, Time.MESSAGE_TIME_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_VENUE_DESC, Venue.MESSAGE_VENUE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_DATE_DESC + TIME_DESC_MIDTERM, Date.MESSAGE_DATE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + DATE_DESC_WORKSHOP + INVALID_DATE_DESC, Date.MESSAGE_DATE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Event} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_DIFFICULT + TAG_DESC_IMPORTANT + TAG_EMPTY, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_DIFFICULT + TAG_EMPTY + TAG_DESC_IMPORTANT, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_DIFFICULT + TAG_DESC_IMPORTANT, Tag.MESSAGE_TAG_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_TIME_DESC + VALID_VENUE_MIDTERM + VALID_DATE_MIDTERM,
                Name.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_EVENT;
        String userInput = targetIndex.getOneBased() + DATE_DESC_WORKSHOP + TAG_DESC_IMPORTANT
                + TIME_DESC_MIDTERM + VENUE_DESC_MIDTERM + NAME_DESC_MIDTERM + TAG_DESC_DIFFICULT;

        EditCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withName(VALID_NAME_MIDTERM)
                .withDate(VALID_DATE_WORKSHOP).withTime(VALID_TIME_MIDTERM).withVenue(VALID_VENUE_MIDTERM)
                .withTags(VALID_TAG_IMPORTANT, VALID_TAG_DIFFICULT).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + DATE_DESC_WORKSHOP + TIME_DESC_MIDTERM;

        EditCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withDate(VALID_DATE_WORKSHOP)
                .withTime(VALID_TIME_MIDTERM).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_EVENT;
        String userInput = targetIndex.getOneBased() + NAME_DESC_MIDTERM;
        EditCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withName(VALID_NAME_MIDTERM).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + DATE_DESC_MIDTERM;
        descriptor = new EditEventDescriptorBuilder().withDate(VALID_DATE_MIDTERM).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + TIME_DESC_MIDTERM;
        descriptor = new EditEventDescriptorBuilder().withTime(VALID_TIME_MIDTERM).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + VENUE_DESC_MIDTERM;
        descriptor = new EditEventDescriptorBuilder().withVenue(VALID_VENUE_MIDTERM).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_DIFFICULT;
        descriptor = new EditEventDescriptorBuilder().withTags(VALID_TAG_DIFFICULT).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + DATE_DESC_MIDTERM + VENUE_DESC_MIDTERM + TIME_DESC_MIDTERM
                + TAG_DESC_DIFFICULT + DATE_DESC_MIDTERM + VENUE_DESC_MIDTERM + TIME_DESC_MIDTERM + TAG_DESC_DIFFICULT
                + DATE_DESC_WORKSHOP + VENUE_DESC_WORKSHOP + TIME_DESC_WORKSHOP + TAG_DESC_IMPORTANT;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withDate(VALID_DATE_WORKSHOP)
                .withTime(VALID_TIME_WORKSHOP).withVenue(VALID_VENUE_WORKSHOP).withTags(VALID_TAG_DIFFICULT, VALID_TAG_IMPORTANT)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + INVALID_DATE_DESC + DATE_DESC_WORKSHOP;
        EditCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withDate(VALID_DATE_WORKSHOP).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + TIME_DESC_WORKSHOP + INVALID_DATE_DESC + VENUE_DESC_WORKSHOP
                + DATE_DESC_WORKSHOP;
        descriptor = new EditEventDescriptorBuilder().withDate(VALID_DATE_WORKSHOP).withTime(VALID_TIME_WORKSHOP)
                .withVenue(VALID_VENUE_WORKSHOP).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_EVENT;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
