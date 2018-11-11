package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertDescriptionDisplayDisplaysToDoListEvent;

import org.junit.Test;

import guitests.guihandles.DescriptionDisplayHandle;
import seedu.address.model.todolist.ToDoListEvent;
import seedu.address.testutil.ToDoListEventBuilder;

public class DescriptionDisplayTest extends GuiUnitTest {

    @Test
    public void display() {
        ToDoListEvent toDoListEvent = new ToDoListEventBuilder().build();

        // id equals 1
        DescriptionDisplay descriptionDisplay = new DescriptionDisplay(toDoListEvent, 0);
        uiPartRule.setUiPart(descriptionDisplay);
        assertDescriptionDisplayDisplays(descriptionDisplay, toDoListEvent, 1);

        // id equals 2
        descriptionDisplay = new DescriptionDisplay(toDoListEvent, 1);
        uiPartRule.setUiPart(descriptionDisplay);
        assertDescriptionDisplayDisplays(descriptionDisplay, toDoListEvent, 2);
    }

    @Test
    public void equals() {
        ToDoListEvent toDoListEvent = new ToDoListEventBuilder().build();
        DescriptionDisplay descriptionDisplay = new DescriptionDisplay(toDoListEvent, 0);

        // same toDoListEvent, same index -> returns true
        DescriptionDisplay copy = new DescriptionDisplay(toDoListEvent, 0);
        assertTrue(descriptionDisplay.equals(copy));

        // same object -> returns true
        assertTrue(descriptionDisplay.equals(descriptionDisplay));

        // null -> returns false
        assertFalse(descriptionDisplay.equals(null));

        // different types -> returns false
        assertFalse(descriptionDisplay.equals(0));

        // different toDoListEvent, same index -> returns false
        ToDoListEvent differentToDoListEvent =
            new ToDoListEventBuilder().withDescription("differentDescription").build();
        assertFalse(descriptionDisplay.equals(new DescriptionDisplay(differentToDoListEvent, 0)));

        // same toDoListEvent, different index -> returns false
        assertFalse(descriptionDisplay.equals(new DescriptionDisplay(toDoListEvent, 1)));
    }

    /**
     * Asserts that {@code descriptionDisplay} displays the details of {@code expectedToDoListEvent} correctly and
     * matches
     * {@code expectedId}.
     */
    private void assertDescriptionDisplayDisplays(DescriptionDisplay descriptionDisplay,
                                                  ToDoListEvent expectedToDoListEvent,
                                                  int expectedId) {
        guiRobot.pauseForHuman();

        DescriptionDisplayHandle descriptionDisplayHandle = new DescriptionDisplayHandle(descriptionDisplay.getRoot());

        assertEquals(Integer.toString(expectedId) + ". ", descriptionDisplayHandle.getId());
        assertDescriptionDisplayDisplaysToDoListEvent(expectedToDoListEvent, descriptionDisplayHandle);
    }
}

