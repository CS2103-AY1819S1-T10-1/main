package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_LECTURE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.ToDoList;
import seedu.address.model.todolist.ToDoListEvent;

/**
 * A utility class containing a list of {@code ToDoListEvent} objects to be used in tests.
 */
public class TypicalTodoListEvents {
    public static final ToDoListEvent CS1000 = new ToDoListEventBuilder()
            .withTitle("CS1000")
            .withDescription("teach to friends")
            .withPriority("L").build();
    public static final ToDoListEvent CS2000 = new ToDoListEventBuilder()
            .withTitle("CS2000")
            .withDescription("teach to friends")
            .withPriority("M").build();
    public static final ToDoListEvent CS3000 = new ToDoListEventBuilder()
            .withTitle("CS3000")
            .withDescription("teach to friends")
            .withPriority("H").build();
    public static final ToDoListEvent CS4000 = new ToDoListEventBuilder()
            .withTitle("CS4000")
            .withDescription("seek help from prof")
            .withPriority("L").build();
    public static final ToDoListEvent CS5000 = new ToDoListEventBuilder()
            .withTitle("CS5000")
            .withDescription("seek help from prof")
            .withPriority("M").build();
    public static final ToDoListEvent CS6000 = new ToDoListEventBuilder()
            .withTitle("CS6000")
            .withDescription("seek help from prof")
            .withPriority("H").build();
    public static final ToDoListEvent CS7000 = new ToDoListEventBuilder()
            .withTitle("CS7000")
            .withDescription("extremely difficult")
            .withPriority("H").build();
    public static final ToDoListEvent CS8000 = new ToDoListEventBuilder()
            .withTitle("CS8000")
            .withDescription("extremely difficult")
            .withPriority("H").build();
    public static final ToDoListEvent MIDTERM = new ToDoListEventBuilder().withTitle("CS2103 Midterm")
            .withDescription("cover all materials from week 1 to week 7")
            .withPriority("H").build();
    public static final ToDoListEvent TUTORIAL = new ToDoListEventBuilder().withTitle("JS1011 Tutorial")
            .withDescription("Monadic parsers")
            .withPriority("L").build();
    public static final ToDoListEvent WORKSHOP = new ToDoListEventBuilder().withTitle("Interview workshop")
            .withDescription("will be helpful for interview")
            .withPriority("L").build();
    public static final ToDoListEvent ENTERTAINMENT = new ToDoListEventBuilder().withTitle("Daniel's birthday party")
            .withDescription("remember to buy gifts")
            .withPriority("M").build();

    // Manually added
    public static final ToDoListEvent CS2106LAB = new ToDoListEventBuilder().withTitle("CS2106 LAB4")
            .withDescription("write mymalloc() and myfree()")
            .withPriority("H").build();
    public static final ToDoListEvent CS3230WA = new ToDoListEventBuilder().withTitle("CS3230 Written Assignment")
            .withDescription("proof questions")
            .withPriority("M").build();

    // Manually added - ToDoListEvent's details found in {@code CommandTestUtil}
    public static final ToDoListEvent LECTURE =
            new ToDoListEventBuilder().withTitle(VALID_TITLE_LECTURE).withDescription(VALID_DESCRIPTION_LECTURE)
                    .withPriority(VALID_PRIORITY_LECTURE).build();

    public static final ToDoListEvent ASSIGNMENT =
            new ToDoListEventBuilder().withTitle(VALID_TITLE_ASSIGNMENT).withDescription(VALID_DESCRIPTION_ASSIGNMENT)
                    .withPriority(VALID_PRIORITY_ASSIGNMENT).build();

    private TypicalTodoListEvents() {
    } // prevents instantiation

    /**
     * Returns an {@code ToDoList} with all the typical persons.
     */
    public static ToDoList getTypicalToDoList() {
        ToDoList toDoList = new ToDoList();
        for (ToDoListEvent todo : getTypicalToDoListEvents()) {
            toDoList.addToDoListEvent(todo);
        }
        return toDoList;
    }

    public static List<ToDoListEvent> getTypicalToDoListEvents() {
        return new ArrayList<>(Arrays.asList(MIDTERM, TUTORIAL, WORKSHOP, ENTERTAINMENT,
                CS1000, CS2000, CS3000, CS4000, CS5000, CS6000));
    }
}
