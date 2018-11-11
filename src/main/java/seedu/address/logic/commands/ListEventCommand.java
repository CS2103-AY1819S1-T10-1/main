package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.RefreshCalendarPanelEvent;
import seedu.address.commons.events.ui.SwitchToSearchTabEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelToDo;

/**
 * Lists all events in the calendar of the scheduler to the user.
 */
public class ListEventCommand extends Command {

    public static final String COMMAND_WORD = "list event";

    public static final String MESSAGE_SUCCESS = "Listed all events";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        model.clearAllPredicatesAndComparators();

        EventsCenter.getInstance().post(new RefreshCalendarPanelEvent());
        EventsCenter.getInstance().post(new SwitchToSearchTabEvent());

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public CommandResult execute(ModelToDo modelToDo, CommandHistory history) throws CommandException {
        throw new CommandException(MESSAGE_INCORRECT_MODEL_CALENDAR);
    }
}
