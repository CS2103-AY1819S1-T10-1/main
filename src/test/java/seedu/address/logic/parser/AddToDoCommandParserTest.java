package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_TUTORIAL;
import static seedu.address.logic.parser.CommandParserTestUtilToDo.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtilToDo.assertParseSuccess;
import static seedu.address.testutil.TypicalTodoListEvents.TUTORIAL;

import org.junit.Test;

import seedu.address.logic.commands.AddToDoCommand;
import seedu.address.model.calendarevent.Description;
import seedu.address.model.calendarevent.Title;
import seedu.address.model.todolist.Priority;
import seedu.address.model.todolist.ToDoListEvent;
import seedu.address.testutil.ToDoListEventBuilder;

public class AddToDoCommandParserTest {

    private AddToDoCommandParser parser = new AddToDoCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        ToDoListEvent expectedToDoListEvent = new ToDoListEventBuilder(TUTORIAL).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TITLE_DESC_TUTORIAL + DESCRIPTION_DESC_TUTORIAL
            + PRIORITY_DESC_TUTORIAL, new AddToDoCommand(expectedToDoListEvent));

        // multiple titles - last title accepted
        assertParseSuccess(parser, TITLE_DESC_LECTURE + TITLE_DESC_TUTORIAL + DESCRIPTION_DESC_TUTORIAL
            + PRIORITY_DESC_TUTORIAL, new AddToDoCommand(expectedToDoListEvent));

        // multiple descriptions - last descriptions accepted
        assertParseSuccess(parser, TITLE_DESC_TUTORIAL + DESCRIPTION_DESC_LECTURE + DESCRIPTION_DESC_TUTORIAL
            + PRIORITY_DESC_TUTORIAL, new AddToDoCommand(expectedToDoListEvent));

        // multiple priorities - last priority accepted
        assertParseSuccess(parser, TITLE_DESC_TUTORIAL + DESCRIPTION_DESC_TUTORIAL + PRIORITY_DESC_LECTURE
            + PRIORITY_DESC_TUTORIAL, new AddToDoCommand(expectedToDoListEvent));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddToDoCommand.MESSAGE_USAGE);

        // missing title prefix
        assertParseFailure(parser, VALID_TITLE_TUTORIAL + DESCRIPTION_DESC_TUTORIAL
            + PRIORITY_DESC_TUTORIAL, expectedMessage);

        // missing description prefix
        assertParseFailure(parser, TITLE_DESC_TUTORIAL + VALID_DESCRIPTION_TUTORIAL
            + PRIORITY_DESC_TUTORIAL, expectedMessage);

        // missing priority prefix
        assertParseFailure(parser, TITLE_DESC_TUTORIAL + DESCRIPTION_DESC_TUTORIAL
            + VALID_PRIORITY_TUTORIAL, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TITLE_TUTORIAL + VALID_DESCRIPTION_TUTORIAL
            + VALID_PRIORITY_TUTORIAL, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid title
        assertParseFailure(parser, INVALID_TITLE_DESC + DESCRIPTION_DESC_TUTORIAL
            + PRIORITY_DESC_TUTORIAL, Title.MESSAGE_CONSTRAINTS);

        // invalid description
        assertParseFailure(parser, TITLE_DESC_TUTORIAL + INVALID_DESCRIPTION_DESC
            + PRIORITY_DESC_TUTORIAL, Description.MESSAGE_CONSTRAINTS);

        // invalid priority
        assertParseFailure(parser, TITLE_DESC_TUTORIAL + DESCRIPTION_DESC_TUTORIAL
            + INVALID_PRIORITY_DESC, Priority.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_TITLE_DESC + DESCRIPTION_DESC_TUTORIAL
            + INVALID_PRIORITY_DESC, Title.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TITLE_DESC_TUTORIAL + DESCRIPTION_DESC_TUTORIAL
                + PRIORITY_DESC_TUTORIAL,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddToDoCommand.MESSAGE_USAGE));
    }

}
