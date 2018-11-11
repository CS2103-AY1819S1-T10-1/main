package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_ASSIGNMENT;
import static seedu.address.testutil.TypicalTodoListEvents.ASSIGNMENT;
import static seedu.address.testutil.TypicalTodoListEvents.MIDTERM;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddToDoCommand;
import seedu.address.model.ModelToDo;
import seedu.address.model.calendarevent.Description;
import seedu.address.model.calendarevent.Title;
import seedu.address.model.todolist.Priority;
import seedu.address.model.todolist.ToDoListEvent;
import seedu.address.testutil.ToDoListEventBuilder;
import seedu.address.testutil.ToDoListEventUtil;

public class AddToDoCommandSystemTest extends SchedulerSystemTest {

    //@Test

    /**
     * TODO to pass it
     */
    public void add() {
        ModelToDo modelToDo = getModelToDo();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a todolist event to a non-empty todolist, command with leading spaces and trailing
         spaces
         * -> added
         */
        ToDoListEvent toAdd = ASSIGNMENT;
        String command =
                "   " + AddToDoCommand.COMMAND_WORD + "  " + TITLE_DESC_ASSIGNMENT + "  " + DESCRIPTION_DESC_ASSIGNMENT
                        + " " + PRIORITY_DESC_ASSIGNMENT + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: add a todolist event with all fields same as another todolist event in the todolist except name ->
        added */
        toAdd = new ToDoListEventBuilder(ASSIGNMENT).withTitle(VALID_TITLE_ASSIGNMENT).build();
        command = AddToDoCommand.COMMAND_WORD + TITLE_DESC_TUTORIAL + DESCRIPTION_DESC_ASSIGNMENT
                + PRIORITY_DESC_ASSIGNMENT;
        assertCommandSuccess(command, toAdd);

        /* Case: add a todolistevent, command with parameters in random order -> added */
        toAdd = ASSIGNMENT;
        command =
                AddToDoCommand.COMMAND_WORD + TITLE_DESC_TUTORIAL + PRIORITY_DESC_ASSIGNMENT
                        + DESCRIPTION_DESC_ASSIGNMENT;
        assertCommandSuccess(command, toAdd);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate todolistevent -> rejected */
        command = ToDoListEventUtil.getAddToDoCommand(MIDTERM);
        assertCommandFailure(command, AddToDoCommand.MESSAGE_DUPLICATE_TODO_EVENT);

        /* Case: missing name -> rejected */
        command = AddToDoCommand.COMMAND_WORD + DESCRIPTION_DESC_ASSIGNMENT + PRIORITY_DESC_ASSIGNMENT;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddToDoCommand.MESSAGE_USAGE));

        /* Case: missing description -> rejected */
        command = AddToDoCommand.COMMAND_WORD + TITLE_DESC_ASSIGNMENT + PRIORITY_DESC_ASSIGNMENT;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddToDoCommand.MESSAGE_USAGE));

        /* Case: missing priority -> rejected */
        command = AddToDoCommand.COMMAND_WORD + TITLE_DESC_ASSIGNMENT + DESCRIPTION_DESC_ASSIGNMENT;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddToDoCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + ToDoListEventUtil.getToDoListEventDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddToDoCommand.COMMAND_WORD + INVALID_TITLE_DESC + DESCRIPTION_DESC_ASSIGNMENT
                + PRIORITY_DESC_ASSIGNMENT;
        assertCommandFailure(command, Title.MESSAGE_CONSTRAINTS);

        /* Case: invalid description -> rejected */
        command = AddToDoCommand.COMMAND_WORD + TITLE_DESC_LECTURE + INVALID_DESCRIPTION_DESC + PRIORITY_DESC_LECTURE;
        assertCommandFailure(command, Description.MESSAGE_CONSTRAINTS);

        /* Case: invalid priority -> rejected */
        command = AddToDoCommand.COMMAND_WORD + TITLE_DESC_ASSIGNMENT + DESCRIPTION_DESC_ASSIGNMENT
                + INVALID_PRIORITY_DESC;
        assertCommandFailure(command, Priority.MESSAGE_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddToDoCommand} that adds {@code toAdd} to the modelToDo and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddToDoCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code TaskListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code SchedulerSystemTest#assertApplicationToDoDisplaysExpected(String, String, Model)}.<br>
     *
     * @see SchedulerSystemTest#assertApplicationToDoDisplaysExpected(String, String, ModelToDo)
     */
    private void assertCommandSuccess(ToDoListEvent toAdd) {
        assertCommandSuccess(ToDoListEventUtil.getAddToDoCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(ToDoListEvent)}. Executes {@code command}
     * instead.
     *
     * @see AddToDoCommandSystemTest#assertCommandSuccess(ToDoListEvent)
     */
    private void assertCommandSuccess(String command, ToDoListEvent toAdd) {
        ModelToDo expectedModel = getModelToDo();
        expectedModel.addToDoListEvent(toAdd);
        String expectedResultMessage = String.format(AddToDoCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, ToDoListEvent)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code TaskListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     *
     * @see AddToDoCommandSystemTest#assertCommandSuccess(String, ToDoListEvent)
     */
    private void assertCommandSuccess(String command, ModelToDo expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationToDoDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code TaskListPanel} remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code SchedulerSystemTest#assertApplicationToDoDisplaysExpected(String, String, Model)}.<br>
     *
     * @see SchedulerSystemTest#assertApplicationToDoDisplaysExpected(String, String, ModelToDo)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        ModelToDo expectedModel = getModelToDo();

        executeCommand(command);
        assertApplicationToDoDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
    }
}
