package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtilToDo.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtilToDo.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ELEMENT;

import org.junit.Test;

import seedu.address.logic.commands.ShowDescriptionCommand;

/**
 * Test scope: similar to {@code DeleteToDoCommandParserTest}.
 *
 * @see DeleteToDoCommandParserTest
 */
public class ShowDescriptionCommandParserTest {

    private ShowDescriptionCommandParser parser = new ShowDescriptionCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1", new ShowDescriptionCommand(INDEX_FIRST_ELEMENT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ShowDescriptionCommand.MESSAGE_USAGE));
    }

}
