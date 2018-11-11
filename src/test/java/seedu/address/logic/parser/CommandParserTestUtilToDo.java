package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains helper methods for testing commandToDo parsers.
 */
public class CommandParserTestUtilToDo {
    /**
     * Asserts that the parsing of {@code userInput} by {@code parserToDo} is successful and the commandToDo created
     * equals to {@code expectedCommandToDo}.
     */
    public static void assertParseSuccess(Parser parser, String userInput, Command expectedCommandToDo) {
        try {
            Command commandToDo = parser.parse(userInput);
            assertEquals(expectedCommandToDo, commandToDo);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parserToDo} is unsuccessful and the error message
     * equals to {@code expectedMessage}.
     */
    public static void assertParseFailure(Parser parser, String userInput, String expectedMessage) {
        try {
            parser.parse(userInput);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }
}

