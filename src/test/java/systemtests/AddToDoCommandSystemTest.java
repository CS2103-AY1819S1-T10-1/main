package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_1;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_1_WITH_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_2;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_2_WITH_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_3;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_3_WITH_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TODO_COMMAND_WORD;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_1;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_1_WITH_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_3;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_3_WITH_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_1;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_1_WITH_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_2;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_2_WITH_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_3;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_3_WITH_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_LECTURE;

import org.junit.Test;

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

    @Test
    public void add() {
        /* Case: add a ToDoListEvent to a non-empty toDoList, command with leading spaces and trailing spaces
        -> added */
        ToDoListEvent toAdd = new ToDoListEventBuilder()
            .withTitle(TITLE_1)
            .withDescription(DESCRIPTION_1)
            .withPriority(PRIORITY_1).build();
        String command = "   " + AddToDoCommand.COMMAND_WORD + "  " + TITLE_1_WITH_PREFIX + "  "
            + DESCRIPTION_1_WITH_PREFIX + " " + PRIORITY_1_WITH_PREFIX + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: add a ToDoListEvent with all fields same as another ToDoListEvent in the toDoList except title
        -> added */
        toAdd = new ToDoListEventBuilder(toAdd).withTitle(TITLE_2).build();
        command = "   " + AddToDoCommand.COMMAND_WORD + "  " + TITLE_2_WITH_PREFIX + "  " + DESCRIPTION_1_WITH_PREFIX
                + " " + PRIORITY_1_WITH_PREFIX + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: add a ToDoListEvent with all fields same as another ToDoListEvent in the toDoList except description
        -> added */
        toAdd = new ToDoListEventBuilder(toAdd).withDescription(DESCRIPTION_2).build();
        command = AddToDoCommand.COMMAND_WORD + "     " + TITLE_2_WITH_PREFIX + "  " + DESCRIPTION_2_WITH_PREFIX
            + " " + PRIORITY_1_WITH_PREFIX + "    ";
        assertCommandSuccess(command, toAdd);

        /* Case: add a ToDoListEvent, command having parameters in random order -> added */
        toAdd = new ToDoListEventBuilder()
            .withTitle(TITLE_3)
            .withDescription(DESCRIPTION_3)
            .withPriority(PRIORITY_3).build();
        command = "   " + AddToDoCommand.COMMAND_WORD + "  " + DESCRIPTION_3_WITH_PREFIX
                + " " + PRIORITY_3_WITH_PREFIX + "          " + TITLE_3_WITH_PREFIX;
        assertCommandSuccess(command, toAdd);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate ToDoListEvent -> rejected */
        command = ToDoListEventUtil.getAddToDoCommand(toAdd);
        assertCommandFailure(command, AddToDoCommand.MESSAGE_DUPLICATE_TODO_EVENT);

        /* Case: command missing name -> rejected */
        command = AddToDoCommand.COMMAND_WORD + DESCRIPTION_DESC_ASSIGNMENT + PRIORITY_DESC_ASSIGNMENT;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddToDoCommand.MESSAGE_USAGE));

        /* Case: command missing description -> rejected */
        command = AddToDoCommand.COMMAND_WORD + TITLE_DESC_ASSIGNMENT + PRIORITY_DESC_ASSIGNMENT;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddToDoCommand.MESSAGE_USAGE));

        /* Case: command missing priority -> rejected */
        command = AddToDoCommand.COMMAND_WORD + TITLE_DESC_ASSIGNMENT + DESCRIPTION_DESC_ASSIGNMENT;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddToDoCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = INVALID_TODO_COMMAND_WORD + ToDoListEventUtil.getToDoListEventDetails(toAdd);
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
