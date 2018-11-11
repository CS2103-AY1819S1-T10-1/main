package seedu.address.model.todolist;

import java.util.Objects;

import seedu.address.model.calendarevent.Description;
import seedu.address.model.calendarevent.Title;

/**
 * Represents an event in todoList.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */

public class ToDoListEvent {

    // Identity fields
    private final Title title;
    private final Description description;

    //Data fields
    private final Priority priority;

    /**
     * Every field must be present and not null.
     */
    public ToDoListEvent(Title title, Description description, Priority priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public Title getTitle() {
        return title;
    }

    public Description getDescription() {
        return description;
    }

    public Priority getPriority() {
        return priority;
    }

    /**
     * Returns true if both todoList events of the same title have one other identity field that is the same.
     * This defines a weaker notion of equality between two calendar events.
     */
    public boolean isSameToDoListEvent(ToDoListEvent otherToDoListEvent) {
        if (otherToDoListEvent == this) {
            return true;
        }

        return otherToDoListEvent != null
            && otherToDoListEvent.getTitle().equals(getTitle())
            && otherToDoListEvent.getDescription().equals(getDescription());
    }

    /**
     * Returns true if both todoList events have the same identity fields and data fields.
     * This defines a stronger notion of equality between two todoList events.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ToDoListEvent)) {
            return false;
        }

        ToDoListEvent otherToDoListEvent = (ToDoListEvent) other;
        return otherToDoListEvent.getTitle().equals(getTitle())
            && otherToDoListEvent.getDescription().equals(getDescription())
            && otherToDoListEvent.getPriority().equals(getPriority());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, description, priority);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Title: ")
            .append(getTitle())
            .append(" Description: ")
            .append(getDescription())
            .append(" Priority: ")
            .append(getPriority());
        return builder.toString();
    }
}
