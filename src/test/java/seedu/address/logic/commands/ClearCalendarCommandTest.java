package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalScheduler;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Scheduler;
import seedu.address.model.UserPrefs;

public class ClearCalendarCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyCalendar_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitScheduler();

        assertCommandSuccess(new ClearCalendarCommand(), model, commandHistory,
            ClearCalendarCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyCalendar_success() {
        Model model = new ModelManager(getTypicalScheduler(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalScheduler(), new UserPrefs());
        expectedModel.resetData(new Scheduler());
        expectedModel.commitScheduler();

        assertCommandSuccess(new ClearCalendarCommand(), model, commandHistory,
            ClearCalendarCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
