package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.AddToDoCommand;
import seedu.address.logic.commands.ClearCalendarCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.DeleteToDoCommand;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindEventCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListEventCommand;
import seedu.address.logic.commands.ListToDoCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectEventCommand;
import seedu.address.logic.commands.ShowDescriptionCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 * Both Scheduler and ToDoList Parser
 */
public class SchedulerParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>(\\w+\\s?(?!\\w/|[0-9])){1,2})"
        + "(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord").trim();
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddEventCommand.COMMAND_WORD:
            return new AddEventCommandParser().parse(arguments);

        case EditEventCommand.COMMAND_WORD:
            return new EditEventCommandParser().parse(arguments);

        case AddToDoCommand.COMMAND_WORD:
            return new AddToDoCommandParser().parse(arguments);

        case SelectEventCommand.COMMAND_WORD:
            return new SelectEventCommandParser().parse(arguments);

        case DeleteEventCommand.COMMAND_WORD:
            return new DeleteEventCommandParser().parse(arguments);

        case DeleteToDoCommand.COMMAND_WORD:
            return new DeleteToDoCommandParser().parse(arguments);

        case ClearCalendarCommand.COMMAND_WORD:
            return new ClearCalendarCommand();

        case FindEventCommand.COMMAND_WORD:
            return new FindEventCommandParser().parse(arguments);

        case ShowDescriptionCommand.COMMAND_WORD:
            return new ShowDescriptionCommandParser().parse(arguments);

        case ListEventCommand.COMMAND_WORD:
            return new ListEventCommand();

        case ListToDoCommand.COMMAND_WORD:
            return new ListToDoCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
