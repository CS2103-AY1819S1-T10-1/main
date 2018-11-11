package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.END_DESC_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.END_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VENUE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.START_DESC_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.START_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATETIME_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATETIME_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATETIME_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATETIME_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_TUTORIAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ELEMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ELEMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_ELEMENT;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.commands.EditEventCommand.EditCalendarEventDescriptor;
import seedu.address.model.calendarevent.DateTime;
import seedu.address.model.calendarevent.Description;
import seedu.address.model.calendarevent.Title;
import seedu.address.model.calendarevent.Venue;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditCalendarEventDescriptorBuilder;

public class EditEventCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_TITLE_LECTURE, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditEventCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + TITLE_DESC_LECTURE, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + TITLE_DESC_LECTURE, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_TITLE_DESC, Title.MESSAGE_CONSTRAINTS); // invalid title
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION_DESC,
            Description.MESSAGE_CONSTRAINTS); // invalid description
        assertParseFailure(parser, "1" + INVALID_START_DESC,
            DateTime.MESSAGE_DATETIME_INPUT_CONSTRAINTS); // invalid start
        assertParseFailure(parser, "1" + INVALID_END_DESC,
            DateTime.MESSAGE_DATETIME_INPUT_CONSTRAINTS); // invalid end
        assertParseFailure(parser, "1" + INVALID_VENUE_DESC, Venue.MESSAGE_CONSTRAINTS); // invalid venue
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS); // invalid tag

        // invalid description followed by valid title
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION_DESC + TITLE_DESC_LECTURE,
            Description.MESSAGE_CONSTRAINTS);

        // valid description followed by invalid description. The test case for invalid description followed by valid
        // description
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + DESCRIPTION_DESC_TUTORIAL + INVALID_DESCRIPTION_DESC,
            Description.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code CalendarEvent} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY,
            Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND,
            Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND,
            Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_ELEMENT;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_LECTURE + TAG_DESC_HUSBAND
            + START_DESC_LECTURE + END_DESC_LECTURE + VENUE_DESC_LECTURE + TITLE_DESC_LECTURE + TAG_DESC_FRIEND;

        EditCalendarEventDescriptor descriptor = new EditCalendarEventDescriptorBuilder().withTitle(VALID_TITLE_LECTURE)
            .withDescription(VALID_DESCRIPTION_LECTURE).withStart(VALID_START_DATETIME_LECTURE)
            .withEnd(VALID_END_DATETIME_LECTURE).withVenue(VALID_VENUE_LECTURE)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_ELEMENT;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_TUTORIAL;

        EditCalendarEventDescriptor descriptor =
            new EditCalendarEventDescriptorBuilder().withDescription(VALID_DESCRIPTION_TUTORIAL)
                .build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // title
        Index targetIndex = INDEX_THIRD_ELEMENT;
        String userInput = targetIndex.getOneBased() + TITLE_DESC_LECTURE;
        EditCalendarEventDescriptor descriptor =
            new EditCalendarEventDescriptorBuilder().withTitle(VALID_TITLE_LECTURE).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // description
        userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_LECTURE;
        descriptor = new EditCalendarEventDescriptorBuilder().withDescription(VALID_DESCRIPTION_LECTURE).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // start date/time
        userInput = targetIndex.getOneBased() + START_DESC_LECTURE;
        descriptor = new EditCalendarEventDescriptorBuilder().withStart(VALID_START_DATETIME_LECTURE).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // end date/time
        userInput = targetIndex.getOneBased() + END_DESC_LECTURE;
        descriptor = new EditCalendarEventDescriptorBuilder().withEnd(VALID_END_DATETIME_LECTURE).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // venue
        userInput = targetIndex.getOneBased() + VENUE_DESC_LECTURE;
        descriptor = new EditCalendarEventDescriptorBuilder().withVenue(VALID_VENUE_LECTURE).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditCalendarEventDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_ELEMENT;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_LECTURE + VENUE_DESC_LECTURE
            + TAG_DESC_FRIEND + DESCRIPTION_DESC_LECTURE + START_DESC_LECTURE + END_DESC_LECTURE
            + VENUE_DESC_LECTURE + START_DESC_TUTORIAL + DESCRIPTION_DESC_TUTORIAL + VENUE_DESC_TUTORIAL
            + TAG_DESC_HUSBAND + END_DESC_TUTORIAL;

        EditCalendarEventDescriptor descriptor =
            new EditCalendarEventDescriptorBuilder().withDescription(VALID_DESCRIPTION_TUTORIAL)
                .withStart(VALID_START_DATETIME_TUTORIAL).withEnd(VALID_END_DATETIME_TUTORIAL)
                .withVenue(VALID_VENUE_TUTORIAL).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_ELEMENT;
        String userInput = targetIndex.getOneBased() + INVALID_DESCRIPTION_DESC + DESCRIPTION_DESC_TUTORIAL;
        EditEventCommand.EditCalendarEventDescriptor descriptor =
            new EditCalendarEventDescriptorBuilder().withDescription(VALID_DESCRIPTION_TUTORIAL).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INVALID_DESCRIPTION_DESC + VENUE_DESC_TUTORIAL
            + DESCRIPTION_DESC_TUTORIAL;
        descriptor = new EditCalendarEventDescriptorBuilder().withDescription(VALID_DESCRIPTION_TUTORIAL)
            .withVenue(VALID_VENUE_TUTORIAL).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_ELEMENT;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditCalendarEventDescriptor descriptor = new EditCalendarEventDescriptorBuilder().withTags().build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
