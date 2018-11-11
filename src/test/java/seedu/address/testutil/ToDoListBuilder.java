package seedu.address.testutil;

import seedu.address.model.ToDoList;
import seedu.address.model.todolist.ToDoListEvent;

/**
 * A utility class to help with building ToDoList objects.
 * Example usage: <br>
 * {@code ToDoList ab = new ToDoListBuilder().withCalendarEvent("TUTORIAL", "LECTURE").build();}
 */
public class ToDoListBuilder {
    private ToDoList toDolist;

    public ToDoListBuilder() {
        toDolist = new ToDoList();
    }

    public ToDoListBuilder(ToDoList toDolist) {
        this.toDolist = toDolist;
    }

    /**
     * Adds a new {@code ToDoListEvent} to the {@code ToDoList} that we are building.
     */
    public ToDoListBuilder withEvent(ToDoListEvent toDoListEvent) {
        toDolist.addToDoListEvent(toDoListEvent);
        return this;
    }

    public ToDoList build() {
        return toDolist;
    }
}
