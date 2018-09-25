package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalSchedule;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Schedule;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptySchedule_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitSchedule();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptySchedule_success() {
        Model model = new ModelManager(getTypicalSchedule(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalSchedule(), new UserPrefs());
        expectedModel.resetData(new Schedule());
        expectedModel.commitSchedule();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
