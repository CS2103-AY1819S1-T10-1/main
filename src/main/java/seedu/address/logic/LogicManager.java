package seedu.address.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.SchedulerParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelToDo;
import seedu.address.model.calendarevent.CalendarEvent;
import seedu.address.model.todolist.ToDoListEvent;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final ModelToDo modelToDo;
    private final CommandHistory history;
    private final SchedulerParser schedulerParser;

    public LogicManager(Model model, ModelToDo modelToDo) {
        this.model = model;
        this.modelToDo = modelToDo;
        history = new CommandHistory();
        schedulerParser = new SchedulerParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = schedulerParser.parseCommand(commandText);
            if (command.isToDoCommand()) {
                return command.execute(modelToDo, history);
            } else {
                return command.execute(model, history);
            }
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<CalendarEvent> getFullCalendarEventList() {
        return model.getFullCalendarEventList();
    }

    @Override
    public ObservableList<CalendarEvent> getFilteredCalendarEventList() {
        return model.getFilteredCalendarEventList();
    }

    @Override
    public ObservableList<ToDoListEvent> getFilteredToDoListEventList() {
        return modelToDo.getFilteredToDoListEventList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
