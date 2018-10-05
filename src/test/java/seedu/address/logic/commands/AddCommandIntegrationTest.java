package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalScheduler;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.calendarevent.CalendarEvent;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalScheduler(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        CalendarEvent validCalendarEvent = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getScheduler(), new UserPrefs());
        expectedModel.addCalendarEvent(validCalendarEvent);
        expectedModel.commitScheduler();

        assertCommandSuccess(new AddCommand(validCalendarEvent), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validCalendarEvent), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        CalendarEvent calendarEventInList = model.getScheduler().getCalendarEventList().get(0);
        assertCommandFailure(new AddCommand(calendarEventInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
