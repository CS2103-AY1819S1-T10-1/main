package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ShowDescriptionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ShowDescriptionCommand object
 */
public class ShowDescriptionCommandParser implements Parser<ShowDescriptionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ShowDescriptionCommand
     * and returns an ShowDescriptionCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowDescriptionCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ShowDescriptionCommand(index);
        } catch (ParseException pe) {
            throw new ParseException (
                    String.format (MESSAGE_INVALID_COMMAND_FORMAT, ShowDescriptionCommand.MESSAGE_USAGE), pe);
        }
    }
}
