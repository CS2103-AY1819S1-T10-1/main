package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TODOLIST_EVENTS_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.DeleteToDoCommand.MESSAGE_DELETE_TODOLIST_EVENT_SUCCESS;
import static seedu.address.testutil.TestUtil.getTask;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ELEMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ELEMENT;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteToDoCommand;
import seedu.address.model.ModelToDo;
import seedu.address.model.todolist.ToDoListEvent;

public class DeleteToDoCommandSystemTest extends SchedulerSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteToDoCommand.MESSAGE_USAGE);

    @Test
    public void delete() {
        /* Case: delete the first ToDoListEvent in the list, command with leading spaces and trailing spaces
         -> deleted */
        ModelToDo expectedModel = getModelToDo();
        String command = "     " + DeleteToDoCommand.COMMAND_WORD + "      "
                + INDEX_FIRST_ELEMENT.getOneBased() + "       ";
        ToDoListEvent deletedToDoListEvent = removeToDoListEvent(expectedModel, INDEX_FIRST_ELEMENT);
        String expectedResultMessage = String.format(MESSAGE_DELETE_TODOLIST_EVENT_SUCCESS, deletedToDoListEvent);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the second ToDoListEvent in the list, command with leading spaces and trailing spaces
         -> deleted */
        expectedModel = getModelToDo();
        command = "  " + DeleteToDoCommand.COMMAND_WORD + "   " + INDEX_SECOND_ELEMENT.getOneBased() + "           ";
        deletedToDoListEvent = removeToDoListEvent(expectedModel, INDEX_SECOND_ELEMENT);
        expectedResultMessage = String.format(MESSAGE_DELETE_TODOLIST_EVENT_SUCCESS, deletedToDoListEvent);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DeleteToDoCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteToDoCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromZeroBased(getModelToDo().getToDoList().getToDoList().size());
        command = DeleteToDoCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_TODOLIST_EVENTS_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteToDoCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteToDoCommand.COMMAND_WORD + " 1 abc",
                MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code ToDoListEvent} at the specified {@code index} in {@code model}'s toDoList.
     *
     * @return the removed ToDoListEvent.
     */
    private ToDoListEvent removeToDoListEvent(ModelToDo modelToDo, Index index) {
        ToDoListEvent targetToDoListEvent = getTask(modelToDo, index);
        modelToDo.deleteToDoListEvent(targetToDoListEvent);
        return targetToDoListEvent;
    }

    /**
     * Deletes the ToDoListEvent at {@code toDelete} by creating a default {@code DeleteToDoCommand} using
     * {@code toDelete} and performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     *
     * @see DeleteToDoCommandSystemTest#assertCommandSuccess(String, ModelToDo, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        ModelToDo expectedModel = getModelToDo();
        ToDoListEvent deletedToDoListEvent = removeToDoListEvent(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_TODOLIST_EVENT_SUCCESS, deletedToDoListEvent);

        assertCommandSuccess(DeleteToDoCommand.COMMAND_WORD + " " + toDelete.getOneBased(),
            expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code SchedulerSystemTest#assertApplicationDisplaysExpected(String, String, ModelToDo)}.
     *
     * @see SchedulerSystemTest#assertApplicationToDoDisplaysExpected(String, String, ModelToDo)
     */
    private void assertCommandSuccess(String command, ModelToDo expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, ModelToDo, String)}
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     *
     * @see DeleteToDoCommandSystemTest#assertCommandSuccess(String, ModelToDo, String)
     * @see SchedulerSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, ModelToDo expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationToDoDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the selected card remains unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code SchedulerSystemTest#assertApplicationToDoDisplaysExpected(String, String, ModelToDo)}.<br>
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
